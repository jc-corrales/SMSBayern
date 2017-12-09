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
import jms.RentabilidadRestaurantesMDB;
import jms.RetirarRestauranteMDB;
import jms.TodosLosProductosMDB;
import tm.RotondAndesTM;
import vos.ListaConfirmaciones;
import vos.ListaProductos;
import vos.ListaRentabilidad;
import vos.Producto;
import vos.RentabilidadRestaurante;

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
	private static String path;
	
	private RotondAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		rentabilidadRestaurantesMQ = new RentabilidadRestaurantesMDB(factory, ctx);
		todosLosProductosMQ = new TodosLosProductosMDB(factory, ctx);
		productosMQ = new ProductosMDB(factory, ctx);
		retirarRestauranteMQ = new RetirarRestauranteMDB(factory, ctx);
		todosLosProductosMQ.start();
		rentabilidadRestaurantesMQ.start();
		productosMQ.start();
		retirarRestauranteMQ.start();;
		
	}
	
	public void stop() throws JMSException
	{
		productosMQ.close();
		todosLosProductosMQ.close();
		rentabilidadRestaurantesMQ.close();
		rentabilidadRestaurantesMQ.close();
	}
	
	/**
	 * M�todo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
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
	 * M�todo que obtiene las Rentabilidades Locales.
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
	
	public ListaRentabilidad getRemoteRentabilidades(String parametrosUnidos)throws Exception
	{
		return rentabilidadRestaurantesMQ.getRemoteRentabilidades(parametrosUnidos);
	}
	
	public ListaProductos getLocalTodosLosProductos()throws Exception
	{
		return new ListaProductos(tm.darProductosSinCantidad());
	}
	
	public ListaProductos getRemoteTodosLosProductos()throws Exception
	{
		return todosLosProductosMQ.getRemoteProductos();
	}
	
	public ListaProductos getLocalProductos(String parametrosUnidos)throws Exception
	{
		String [] parametros = parametrosUnidos.split(",");
		Integer filtro = Integer.parseInt(parametros[0]);
		String parametro = parametros[1];			
		List<Producto> lista = tm.darProductosPorSinCantidad(filtro, parametro);
		return new ListaProductos(lista);
	}
	
	public ListaProductos getRemoteProductos(String parametrosUnidos)throws Exception
	{
		return productosMQ.getRemoteProductos(parametrosUnidos);
	}
	
	public ListaConfirmaciones retirarLocalRestaurantes(String parametrosUnidos)throws Exception
	{
		Long idRestaurante = Long.parseLong(parametrosUnidos);
		Boolean resultado = tm.retirarRestauranteDelServicio(idRestaurante);
		List<Boolean> lista = new ArrayList<Boolean>();
		lista.add(resultado);
		return new ListaConfirmaciones(lista);
	}
	
	public ListaConfirmaciones retirarRemoteRestaurantes(String parametrosUnidos)throws Exception
	{
		return retirarRestauranteMQ.retirarRemoteRestaurantes(parametrosUnidos);
	}
}
