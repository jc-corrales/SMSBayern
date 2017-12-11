package dtm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

import jms.NonReplyException;
import jms.ProductosMDB;
import jms.RegistrarPedidoMDB;
import jms.RentabilidadRestaurantesMDB;
import jms.RetirarRestauranteMDB;
import jms.TodosLosProductosMDB;
import tm.RotondAndesTM;
import vos.ListaConfirmaciones;
import vos.ListaPedidosConexion;
import vos.ListaProductos;
import vos.ListaRentabilidad;
import vos.Pedido;
import vos.PedidoConexion;
import vos.Producto;
import vos.RentabilidadRestaurante;
import vos.Restaurante;

public class RotondAndesDistributed {
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	private static RotondAndesDistributed instance;
	
	private RotondAndesTM tm;
	
	private QueueConnectionFactory queueFactory;
	
	private TopicConnectionFactory factory;
	
	private RentabilidadRestaurantesMDB rentabilidadRestaurantesMQ;
	
	private TodosLosProductosMDB todosLosProductosMQ;
	
	private ProductosMDB productosMQ;
	
	private RetirarRestauranteMDB retirarRestauranteMQ;
	
	private RegistrarPedidoMDB registrarPedidoMQ;
	
	private static String path;
	
	private RotondAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		rentabilidadRestaurantesMQ = new RentabilidadRestaurantesMDB(factory, ctx);
		todosLosProductosMQ = new TodosLosProductosMDB(factory, ctx);
		productosMQ = new ProductosMDB(factory, ctx);
		retirarRestauranteMQ = new RetirarRestauranteMDB(factory, ctx);
		registrarPedidoMQ = new RegistrarPedidoMDB(factory, ctx);
		todosLosProductosMQ.start();
		rentabilidadRestaurantesMQ.start();
		productosMQ.start();
		retirarRestauranteMQ.start();;
		registrarPedidoMQ.start();
	}
	
	public void stop() throws JMSException
	{
		productosMQ.close();
		todosLosProductosMQ.close();
		rentabilidadRestaurantesMQ.close();
		retirarRestauranteMQ.close();
		registrarPedidoMQ.close();
	}
	
	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	public static void setPath(String p) {
		path = p;
	}
	
	public void setUpTransactionManager(RotondAndesTM tm)
	{
	   this.tm = tm;
	}
	
	private static RotondAndesDistributed getInst()
	{
		return instance;
	}
	
	public static RotondAndesDistributed getInstance(RotondAndesTM tm)
	{
		if(instance == null)
		{
			try {
				instance = new RotondAndesDistributed();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(tm);
		return instance;
	}
	
	public static RotondAndesDistributed getInstance()
	{
		if(instance == null)
		{
			RotondAndesTM tm = new RotondAndesTM(path);
			return getInstance(tm);
		}
		if(instance.tm != null)
		{
			return instance;
		}
		RotondAndesTM tm = new RotondAndesTM(path);
		return getInstance(tm);
	}
	/**
	 * Método que obtiene las Rentabilidades Locales.
	 * @param parametrosUnidos
	 * @return
	 * @throws Exception
	 */
	public ListaRentabilidad getLocalRentabilidades(String parametrosUnidos)throws Exception
	{
		String [] parametros = parametrosUnidos.split(",");
		String fecha1 = parametros[0];
		String fecha2 = parametros[1];
		Integer criterio = Integer.parseInt(parametros[2]);
		Long idProducto = null;
		if(!parametros[3].equals("null"))
		{
			idProducto = Long.parseLong(parametros[3]);
		}
			
		List<RentabilidadRestaurante> lista = tm.darRentabilidadRestaurantes(fecha1, fecha2, criterio, idProducto);
		return new ListaRentabilidad(lista);
	}
	/**
	 * Método que obtiene las Rentabilidades de las otras bases de Datos.
	 * @param parametrosUnidos
	 * @return
	 * @throws Exception
	 */
	public ListaRentabilidad getRemoteRentabilidades(String parametrosUnidos)throws Exception
	{
		return rentabilidadRestaurantesMQ.getRemoteRentabilidades(parametrosUnidos);
	}
	/**
	 * Método que obtiene todos los Productos locales.
	 * @return
	 * @throws Exception
	 */
	public ListaProductos getLocalTodosLosProductos()throws Exception
	{
		return new ListaProductos(tm.darProductosSinCantidad());
	}
	/**
	 * Método que obtiene todos los Productos de las otras bases de datos.
	 * @return
	 * @throws Exception
	 */
	public ListaProductos getRemoteTodosLosProductos()throws Exception
	{
		return todosLosProductosMQ.getRemoteProductos();
	}
	/**
	 * Método que obtiene Productos locales según parámetros de búsqueda.
	 * @param parametrosUnidos
	 * @return
	 * @throws Exception
	 */
	public ListaProductos getLocalProductos(String parametrosUnidos)throws Exception
	{
		String [] parametros = parametrosUnidos.split(",");
		Integer filtro = Integer.parseInt(parametros[0]);
		String parametro = parametros[1];	
		List<Producto> lista = tm.darProductosPorSinCantidad(filtro, parametro);
		return new ListaProductos(lista);
	}
	/**
	 * Método que obtiene Productos de las otras bases de datos según unos parámetros de consulta.
	 * @param parametrosUnidos
	 * @return
	 * @throws Exception
	 */
	public ListaProductos getRemoteProductos(String parametrosUnidos)throws Exception
	{
		return productosMQ.getRemoteProductos(parametrosUnidos);
	}
	/**
	 * Método que retira un Restaurante Local.
	 * @param parametrosUnidos
	 * @return
	 * @throws Exception
	 */
	public ListaConfirmaciones retirarLocalRestaurantes(String parametrosUnidos)throws Exception
	{
		Long idRestaurante = Long.parseLong(parametrosUnidos);
		Boolean resultado = tm.retirarRestauranteDelServicio(idRestaurante);
		List<Boolean> lista = new ArrayList<Boolean>();
		lista.add(resultado);
		return new ListaConfirmaciones(lista);
	}
	/**
	 * Método que ordena retirar restaurantes con el ID dado.
	 * @param parametrosUnidos
	 * @return
	 * @throws Exception
	 */
	public ListaConfirmaciones retirarRemoteRestaurantes(String parametrosUnidos)throws Exception
	{
		return retirarRestauranteMQ.retirarRemoteRestaurantes(parametrosUnidos);
	}
	/**
	 * Método que registra un Pedido Local.
	 * @param pedido
	 * @return
	 * @throws Exception
	 */
	public ListaPedidosConexion registrarPedidoLocal(PedidoConexion pedido)throws Exception
	{
		List<PedidoConexion> lista = new ArrayList<PedidoConexion>();
		ListaPedidosConexion temp = new ListaPedidosConexion(lista);
		if(pedido.getIdProducto() != null)
		{
			try
			{
				temp.getPedidosConexion().add(tm.registrarPedidoExterno(pedido));
			}
			catch(Exception e)
			{
				System.out.println("Error ordenando el Producto Remoto.");
			}
		}
		else if(pedido.getIdMenu() != null)
		{
			try
			{
				temp.getPedidosConexion().addAll(tm.registrarPedidoMenuExterno(pedido));
			}
			catch(Exception e)
			{
				System.out.println("Error ordenando el Menú Remoto.");
			}
		}
		return temp;
	}
	/**
	 * Método que envía Pedidos a otras bases de Datos.
	 * @param pedido
	 * @return
	 * @throws Exception
	 */
	public ListaPedidosConexion registrarPedidoRemoto(PedidoConexion pedido)throws Exception
	{
		return registrarPedidoMQ.sendPedidos(pedido);
	}
}
