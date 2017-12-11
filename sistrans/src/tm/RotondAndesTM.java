/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (BogotÃƒÆ’Ã‚Â¡ - Colombia)
 * Departamento de IngenierÃƒÆ’Ã‚Â­a de Sistemas y ComputaciÃƒÆ’Ã‚Â³n
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: RotondAndes 
 * Autor: David Bauista - dj.bautista10@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;

import dao.DAOConsumoClientes;
import dao.DAOTablaClientes;
import dao.DAOTablaClientesFrecuentes;
import dao.DAOTablaIngredientes;
import dao.DAOTablaPedidos;
import dao.DAOTablaProductos;
import dao.DAOTablaRestaurantes;
import dao.DAOTablaUsuarios;
import dao.DAOTablaZonas;
import dtm.RotondAndesDistributed;
import jms.NonReplyException;
import vos.Cliente;
import vos.ClienteFrecuente;
import vos.ConsumoCliente;
import vos.EstadisticasPedidos;
import vos.Ingrediente;

import vos.IngredientesSimilares;
import vos.ListaConfirmaciones;
import vos.ListaPedidosConexion;
import vos.ListaProductos;
import vos.ListaRentabilidad;
import vos.Menu;
import vos.Orden;
import vos.Pedido;
import vos.PedidoConexion;
import vos.PedidoDeMenu;
import vos.ProductoLocal;
import vos.Producto;
import vos.RegistroVentas;
import vos.RentabilidadRestaurante;
import vos.Representante;
import vos.Restaurante;
import vos.Zona;

/**
 * Transaction Manager de la aplicacion (TM)
 * Fachada en patron singleton de la aplicacion
 * @author David Bautista
 */
public class RotondAndesTM {


	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * conexion a la base de datos
	 */
	private Connection conn;
	/**
	 * Atributo que contiene el DTM.
	 */
	private RotondAndesDistributed dtm;
	/**
	 * Metodo constructor de la clase RotondAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto RotondAndesMaster, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexion a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
		System.out.println("Instancing DTM...");
		dtm = RotondAndesDistributed.getInstance(this);
		System.out.println("Done!");
	}

	/**
	 * Metodo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}



	//--------------------------------------------------
	//Transacciones-------------------------------------
	//--------------------------------------------------

	public List<Cliente> darClientes() throws Exception {
		List<Cliente> clientes; 
		DAOTablaClientes daoCliente = new DAOTablaClientes();

		try {
			this.conn = darConexion();
			daoCliente.setConn(conn);
			clientes = daoCliente.darClientes();
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes; 
	}

	public Cliente darCliente(Long id) throws SQLException, Exception {
		Cliente res;
		DAOTablaClientes dao = new DAOTablaClientes(); 
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			res = dao.darCliente(id);
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return res;
	}

	public ProductoLocal darProducto(Long id, Long idRest) throws SQLException, Exception {
		ProductoLocal res;
		DAOTablaProductos daoProd = new DAOTablaProductos(); 
		DAOTablaIngredientes daoIng = new DAOTablaIngredientes();
		try {
			this.conn = darConexion();
			daoProd.setConn(conn);
			res = daoProd.darProducto(id, idRest);

			//INICIO DE LA SEGUNDA PARTE DE LA TRANSACCION
			daoIng.setConn(conn);
			res.setIngredientes(daoIng.darIngredientesProducto(id));
			System.out.println(" POST-SETINGREDIENTES ingredientes: " + res.getIngredientes().size());

		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally {
			try {
				daoProd.cerrarRecursos();
				daoIng.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return res;
	}
	/**
	 * Método que obtiene un MenÃº segÃºn su ID y el ID del Restaurante dueÃ±o.
	 * @param id Long, ID del Menú.
	 * @param idRest Long, ID del restaurante.
	 * @return Menu
	 * @throws SQLException
	 * @throws Exception
	 */
	public Menu darMenu(Long id, Long idRest) throws SQLException, Exception {
		Menu res;
		DAOTablaProductos daoProd = new DAOTablaProductos(); 
		DAOTablaIngredientes daoIng = new DAOTablaIngredientes();
		try {
			this.conn = darConexion();
			daoProd.setConn(conn);
			res = daoProd.darMenu(id, idRest);

			//INICIO DE LA SEGUNDA PARTE DE LA TRANSACCION
			daoIng.setConn(conn);
			if(res.getEntrada() != null)
			{
				ProductoLocal entrada = res.getAcompaniamiento();
				entrada.setIngredientes(daoIng.darIngredientesProducto(entrada.getId()));
				res.setEntrada(entrada);
			}
			if(res.getPlatoFuerte() != null)
			{
				ProductoLocal platoFuerte = res.getAcompaniamiento();
				platoFuerte.setIngredientes(daoIng.darIngredientesProducto(platoFuerte.getId()));
				res.setPlatoFuerte(platoFuerte);
			}
			if(res.getPostre() != null)
			{
				ProductoLocal postre = res.getAcompaniamiento();
				postre.setIngredientes(daoIng.darIngredientesProducto(postre.getId()));
				res.setPostre(postre);
			}
			if(res.getBebida() != null)
			{
				ProductoLocal bebida = res.getAcompaniamiento();
				bebida.setIngredientes(daoIng.darIngredientesProducto(bebida.getId()));
				res.setBebida(bebida);
			}
			if(res.getAcompaniamiento() != null)
			{
				ProductoLocal acompaniamiento = res.getAcompaniamiento();
				acompaniamiento.setIngredientes(daoIng.darIngredientesProducto(acompaniamiento.getId()));
				res.setAcompaniamiento(acompaniamiento);
			}
			System.out.println("POST Agregar Ingredientes a Productos de Menu.");

		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally {
			try {
				daoProd.cerrarRecursos();
				daoIng.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return res;
	}

	/**
	 * RF11
	 * Manda los ids de ing1, ing2 y restaurante para crear un nuevo ingrediente equivalente(base)
	 * @param filtro
	 * @param parametro
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public IngredientesSimilares registrar2IngredientesEquivalentes(Long idIng1, Long idIng2, Long idRest) throws SQLException, Exception{
		IngredientesSimilares ingS;
		DAOTablaIngredientes daoIng = new DAOTablaIngredientes();
		try {
			this.conn = darConexion();
			daoIng.setConn(conn);

			ingS = daoIng.agregarIngredientesSimilares(idIng1, idIng2, idRest);

		}catch (SQLException e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIng.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch (SQLException exception)
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingS;
	}

	/**
	 * RF13 Surtir restaurantes
	 */
	public void registrarCantidadProductosDisponibles(Long idRest, Long idProd, int pCantidad)throws SQLException, Exception
	{
		DAOTablaProductos daoPro = new DAOTablaProductos();
		DAOTablaIngredientes daoIng = new DAOTablaIngredientes();
		try 
		{
			this.conn = darConexion();
			daoPro.setConn(conn);
			daoIng.setConn(conn);
			List<Ingrediente> ingredientes = daoIng.darIngredientesProducto(idProd);
			//Ciclo que verifica si hay suficientes ingredientes, para todos los ingredientes.
			for(int i = 0; i < ingredientes.size(); i++)
			{
				Ingrediente ingrediente = ingredientes.get(i);
				Integer cantidadRequerida = daoIng.darIngredientesRequeridosPorProducto(idProd, ingrediente.getId());
				if(ingrediente.getCantidadDisponible() < (pCantidad*cantidadRequerida))
				{
					throw new Exception ("No hay suficiente cantidad de Ingrediente con ID: " + ingrediente.getId());
				}

			}
			//Ciclo que reduce los Ingredientes totales.
			for(int j = 0; j < ingredientes.size(); j++)
			{
				Ingrediente ingrediente = ingredientes.get(j);
				Integer cantidadRequerida = daoIng.darIngredientesRequeridosPorProducto(idProd, ingrediente.getId());
				daoIng.reducirCantidadIngredientesProducto(idProd, pCantidad*cantidadRequerida);
			}
			//			List<Long> info = daoPro.darIngredientesDeProducto(idProd);
			//			daoIng.reducirCantidadIngredientesProducto(idProd, info);
			daoPro.registrarCantidadProductosDisponibles(pCantidad, idProd, idRest);
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				daoPro.cerrarRecursos();
				daoIng.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	//	/**
	//	 * 
	//	 * @param id
	//	 * @param idProd
	//	 * @param idRestProd
	//	 * @return
	//	 * @throws SQLException
	//	 * @throws Exception
	//	 */
	//	public Pedido agregarPedido(Long id, Long idProd, Long idRestProd) throws SQLException, Exception {
	//		Pedido res = null;
	//		DAOTablaPedidos dao = new DAOTablaPedidos();
	//		try {
	//			this.conn = darConexion();
	//			dao.setConn(conn);
	//			Cliente cliente = darCliente(id);
	//			Producto producto = darProducto(idProd, idRestProd);
	//			res = dao.registrarPedido(cliente, producto, (long) 1, idRestProd);
	//			//TODO ARREGLAR PARA PROCESAR ORDENES
	//		}catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		}finally {
	//			try {
	//				dao.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return res;
	//
	//	}



	public void borrarPreferenciaClienteFrecuente(Long id, String password, Long idProd) throws SQLException, Exception {
		DAOTablaClientesFrecuentes dao = new DAOTablaClientesFrecuentes();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			//Verificar Cliente
			if(!dao.verficarCliente(id, password)) 
				throw new Exception("Clave invalida");
			//fin Verificacion		

			dao.borrarPreferencia(id, idProd);
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	//---------------------------------------------------	
	//	Requerimiento: RF8
	//---------------------------------------------------
	/**
	 * Método que Registra un Producto como preferido por un cliente.
	 * @param id Long, ID del cliente.
	 * @param password String, ContraseÃ±a del cliente.
	 * @param idProd Long, ID del producto a registrar.
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ClienteFrecuente agregarPreferenciaClienteFrecuente(Long id, String password, Long idProd) throws SQLException, Exception {
		ClienteFrecuente cliente = null;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		DAOTablaClientesFrecuentes dao = new DAOTablaClientesFrecuentes();
		DAOTablaProductos daoPref = new DAOTablaProductos();
		try {
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			dao.setConn(conn);
			System.out.println(id);
			System.out.println(idProd);
			System.out.println(password);
			//Verificar Cliente
			if(!daoUsuarios.verficarUsuarioClienteFrecuente(id, password)) 
				throw new Exception("Clave invalida");
			//fin Verificacion		

			dao.registrarPreferencia(id, idProd);

			//INICIO AGREGAR PREFERENCIAS A ENIDAD CLIENTEFRECUENTE
			cliente = daoUsuarios.darClienteFrecuente(id);
			daoPref.setConn(conn);
			cliente.setPreferencias(daoPref.darPreferencias(id));

		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				daoPref.cerrarRecursos();
				dao.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cliente;
	}
	//	/**
	//	 * MÃƒÂ©todo que emite una Orden con un sÃƒÂ³lo Pedido.
	//	 * @param id Long, ID del cliente que hace el pedido.
	//	 * @param idProd Long, ID del producto que se pide.
	//	 * @param idRestProd Long, ID del restaurante dueÃƒÂ±o del producto que se pide.
	//	 * @return Orden, Orden con toda la informaciÃƒÂ³n del Pedido.
	//	 * @throws SQLException
	//	 * @throws Exception
	//	 */
	//	public Orden agregarUnaOrdenDeUnPedido(Long id, Long idProd, Long idRestProd) throws SQLException, Exception {
	//		Orden res = null;
	//		DAOTablaPedidos dao = new DAOTablaPedidos();
	//		try {
	//			this.conn = darConexion();
	//			dao.setConn(conn);
	//			Cliente cliente = darCliente(id);
	//			Producto producto = darProducto(idProd, idRestProd);
	//			res = dao.registrarUnPedido(cliente, producto, idRestProd);
	//		}catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		}finally {
	//			try {
	//				dao.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return res;
	//
	//	}
	//---------------------------------------------------	
	//	Requerimiento: RF9 Parte 1
	//---------------------------------------------------
	/**
	 * Método que crea una Orden a nombre de un Cliente o Cliente Frecuente.
	 * @param orden Orden, InformaciÃ³n de la Orden a crear.
	 * @return Orden, La Orden recientemente creada.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Orden registrarNuevaOrden(Orden orden)throws SQLException, Exception
	{
		DAOTablaPedidos dao = new DAOTablaPedidos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
			dao.setConn(conn);
			daoUsuarios.setConn(conn);
			//TODO Verificar Cliente
			daoUsuarios.verficarUsuarioCliente(orden.getCliente().getId());
			orden = dao.registrarNuevaOrden(orden);
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				conn.setAutoCommit(true);
				daoUsuarios.cerrarRecursos();
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return orden;
	}
	//---------------------------------------------------	
	//	Requerimiento: RF9 Parte 2A
	//---------------------------------------------------
	/**
	 * Método para registrar un Pedido a una Orden.
	 * @param id Long, ID del Cliente.
	 * @param idProd Long, ID del Producto a Ordenar.
	 * @param idRestProd Long, ID del Restaurante al cual ordenar el Producto.
	 * @param idOrden Long, ID de la Orden que contendrá el Pedido.
	 * @return Pedido, toda la informaciÃ³n del Pedido.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Pedido registrarPedido(Long id, Long idProd, Long idRestProd, Long idOrden) throws SQLException, Exception {
		Pedido res = null;
		DAOTablaPedidos dao = new DAOTablaPedidos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			daoUsuarios.setConn(conn);
			daoRestaurantes.setConn(conn);
			if(!daoRestaurantes.darEstadoOperacionRestaurante(idRestProd))
			{
				throw new Exception("El Restaurante actualmente no está en servicio.");
			}
			//			conn.setAutoCommit(false);
			if(dao.getEstatusOrden(idOrden))
			{
				throw new Exception("La Orden con ID: " + idOrden + " ya ha sido confirmada y no puede recibir nuevos Pedidos.");
			}
			Orden orden = dao.obtenerOrden(idOrden);
			if(!orden.getCliente().getId().equals(id))
			{
				throw new Exception("La Orden con ID: " + idOrden + " no está a nombre de este cliente.");
			}

			if(!daoUsuarios.verficarUsuarioCliente(id))
			{
				throw new Exception ("Informacion de Cliente invalida.");
			}
			ProductoLocal producto = darProducto(idProd, idRestProd);
			res = dao.registrarPedido(producto, idOrden, idRestProd);
			dao.updateCostoTotalOrden(idOrden, res.getProducto().getPrecio());
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				//				conn.setAutoCommit(true);
				daoUsuarios.cerrarRecursos();
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return res;
	}
	//---------------------------------------------------	
	//	Requerimiento: RF9 Parte 2B
	//---------------------------------------------------
	/**
	 * Método que Registra el Pedido de un MenÃº.
	 * @param id Long, ID del Cliente.
	 * @param idPedido Long, ID del pedido.
	 * @param idMenu Long, ID del MenÃº a registar.
	 * @param idRestMenu Long, ID del Restaurante dueÃ±o del menÃº.
	 * @param idOrden Long, ID de la Orden a la cual se va a asignar el Pedido.
	 * @return PedidoDeMenu
	 * @throws SQLException
	 * @throws Exception
	 */
	public PedidoDeMenu registrarPedidoMenu(Long idCliente, Long idMenu, Long idRestMenu, Long idOrden) throws SQLException, Exception {
		PedidoDeMenu res = null;
		DAOTablaPedidos dao = new DAOTablaPedidos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			daoUsuarios.setConn(conn);
			daoRestaurantes.setConn(conn);
			if(!daoRestaurantes.darEstadoOperacionRestaurante(idRestMenu))
			{
				throw new Exception("El Restaurante actualmente no está en servicio.");
			}
			//			conn.setAutoCommit(false);
			if(dao.getEstatusOrden(idOrden))
			{
				throw new Exception("La Orden con ID: " + idOrden + " ya ha sido confirmada y no puede recibir nuevos Pedidos.");
			}
			Orden orden = dao.obtenerOrden(idOrden);
			if(!orden.getCliente().getId().equals(idCliente))
			{
				throw new Exception("La Orden con ID: " + idOrden + " no está a nombre de este cliente.");
			}

			if(!daoUsuarios.verficarUsuarioCliente(idCliente))
			{
				throw new Exception ("Informacion de Cliente invalida.");
			}
			Menu menu = darMenu(idOrden, idRestMenu);
			res = dao.registrarPedidoMenu(menu, idOrden, idRestMenu);
			dao.updateCostoTotalOrden(idOrden, menu.getPrecio());
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				//				conn.setAutoCommit(true);
				daoRestaurantes.cerrarRecursos();
				daoUsuarios.cerrarRecursos();
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return res;
	}
	//---------------------------------------------------	
	//	Requerimiento: RF9 Parte 3
	//---------------------------------------------------
	/**
	 * Método para Confirmar una Orden.
	 * @param idOrden Long, ID de la Orden a confirmar.
	 * @param idCliente Long, ID del cliente dueÃ±o de la Orden.
	 * @return Boolean, Booleano que determina si la transacciÃ³n fue exitosa o no.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean confirmarOrden(Long idOrden, Long idCliente) throws SQLException, Exception
	{
		Boolean respuesta = false;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		DAOTablaPedidos dao = new DAOTablaPedidos();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			daoUsuarios.setConn(conn);
			if(!daoUsuarios.verficarUsuarioCliente(idCliente))
			{
				throw new Exception("El Cliente es incorrecto");
			}
			Orden orden = dao.obtenerOrden(idOrden);
			if(!orden.getCliente().getId().equals(idCliente)) {
				throw new Exception("El Cliente con ID: " + idCliente + " no es dueÃ±o de la orden con ID: " + idOrden);
			}
			respuesta = dao.confirmarOrden(idOrden);
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				daoUsuarios.cerrarRecursos();
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}


	//---------------------------------------------------	
	//	Requerimiento: RF10
	//---------------------------------------------------
	/**
	 * Método que marca un Pedido como entregado.
	 * @param idPed Long, ID del Pedido a marcar como Entregado.
	 * @throws SQLException
	 * @throws Exception
	 */
	public void despacharPedido(Long idPed) throws SQLException, Exception
	{
		DAOTablaPedidos dao = new DAOTablaPedidos();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			//TODO Verificar Cliente
			dao.despacharPedido(idPed);
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public List<ProductoLocal> darProductos() throws SQLException, Exception {
		List<ProductoLocal> productos; 
		DAOTablaProductos dao = new DAOTablaProductos();

		try {
			this.conn = darConexion();
			dao.setConn(conn);
			productos = dao.darProductos();
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos; 
	}

	
	public List<Producto> darProductosSinCantidad() throws SQLException, Exception {
		List<Producto> productos; 
		DAOTablaProductos dao = new DAOTablaProductos();

		try {
			this.conn = darConexion();
			dao.setConn(conn);
			productos = dao.darProductosSinCantidad();
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos; 
	}
	public List<ProductoLocal> darProductosPor(Integer filtro, String parametro)  throws SQLException, Exception {
		List<ProductoLocal> productos; 
		DAOTablaProductos dao = new DAOTablaProductos();
		DAOTablaIngredientes daoIng = new DAOTablaIngredientes();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			productos = dao.darProductosPor(filtro, parametro);

			daoIng.setConn(conn);
			int a = 0;
			for(ProductoLocal prod : productos) {
				List<Ingrediente> ingredientes = daoIng.darIngredientesProducto(prod.getId());
				prod.setIngredientes(ingredientes);
				productos.set(a, prod);
				a++;
			}

		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIng.cerrarRecursos();
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos; 
	}
	
	public List<Producto> darProductosPorSinCantidad(Integer filtro, String parametro)  throws SQLException, Exception {
		List<Producto> productos; 
		DAOTablaProductos dao = new DAOTablaProductos();
		DAOTablaIngredientes daoIng = new DAOTablaIngredientes();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			productos = dao.darProductosPorSinCantidad(filtro, parametro);

			daoIng.setConn(conn);
			int a = 0;
			for(Producto prod : productos) {
				List<Ingrediente> ingredientes = daoIng.darIngredientesProducto(prod.getId());
				prod.setIngredientes(ingredientes);
				productos.set(a, prod);
				a++;
			}

		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIng.cerrarRecursos();
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos; 
	}
	//---------------------------------------------------	
	//	Requerimiento: RF7
	//---------------------------------------------------
	/**
	 * MÃƒÂ©todo para agregar una Zona a RotondAndes
	 * @param zona Zona, toda la informaciÃƒÂ³n de la zona a agregar.
	 * @return Zona.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Zona agregarZona(Zona zona)throws SQLException, Exception{

		DAOTablaZonas dao = new DAOTablaZonas();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
			dao.setConn(conn);	

			dao.addZona(zona);

		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				conn.setAutoCommit(true);
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zona;
	}

	public Zona darZona(Long id) throws SQLException, Exception
	{

		Zona zona = null;

		DAOTablaZonas daoZona = new DAOTablaZonas();
		DAOTablaRestaurantes daoRes = new DAOTablaRestaurantes();
		DAOTablaProductos daoProd = new DAOTablaProductos();

		try {
			this.conn = darConexion();
			daoZona.setConn(conn);
			zona = daoZona.darZona(id);
			System.out.println("after dao ------> " + zona.getId() + " || " + zona.getNombre());
			//AGREGACIÃƒâ€œN DE RESTAURANTES A ZONA

			daoRes.setConn(conn);
			List<Restaurante> restaurantes = daoRes.darRestaurantesDeZona(id);

			//AGREGACIÃƒâ€œN DE PRODUCTOS A RESTAURANTES
			if(restaurantes != null && !restaurantes.isEmpty())
			{
				daoProd.setConn(conn);
				for(Restaurante rest : restaurantes) {
					rest.setProductos(daoProd.darProductosPor(DAOTablaProductos.RESTAURANTE, rest.getId().toString()));
				}
				zona.setRestaurantes(restaurantes);
			}


		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {

				daoZona.cerrarRecursos();
				daoRes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zona;
	}
	/**
	 * MÃƒÂ©todo que obtiene las estadÃƒÂ­sticas de los Pedidos de un Restaurante.
	 * @param id Long, ID del Representante del Restaurante cuyas estadÃƒÂ­sticas se van a pedir.
	 * @return List<EstadisticasPedidos>
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<EstadisticasPedidos> darEstadisticasPedidos()throws SQLException, Exception
	{
		//		Zona zona = null;
		//
		//		DAOTablaZonas daoZona = new DAOTablaZonas();
		//		DAOTablaRestaurantes daoRes = new DAOTablaRestaurantes();
		//		DAOTablaProductos daoProd = new DAOTablaProductos();
		DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
		List<EstadisticasPedidos> respuesta;
		try {
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			respuesta = daoPedidos.darEstadisticasPedidos();
			//			System.out.println("after dao ------> " + zona.getId() + " || " + zona.getNombre());
			if(respuesta.size() == 0) {
				throw new Exception("No existe el Restaurante.");
			}		
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {

				daoPedidos.cerrarRecursos();
				//				daoRes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}

	//	/**
	//	 * MÃƒÂ©todo que obtiene las estadÃƒÂ­sticas de todos los pedidos del restaurante asociado al Representante, cuyos datos entran por parÃƒÂ¡metro.
	//	 * SÃƒÂ³lo el Representante tiene autorizaciÃƒÂ³n para ver los datos de su Restaurante.
	//	 * @param idRepresentante Long, ID del representante.
	//	 * @param password String, contraseÃƒÂ±a del Representante.
	//	 * @return List<EstadisticasPedidos> lista con las estadÃƒÂ­sticas del restaurante.
	//	 * @throws SQLException
	//	 * @throws Exception
	//	 */
	//	public List<EstadisticasPedidos> darEstadisticasPedidosPorRestaurante(Long idRepresentante, String password)throws SQLException, Exception {
	//
	////		Zona zona = null;
	////
	////		DAOTablaZonas daoZona = new DAOTablaZonas();
	////		DAOTablaRestaurantes daoRes = new DAOTablaRestaurantes();
	////		DAOTablaProductos daoProd = new DAOTablaProductos();
	//		System.out.println("ENTRO A METODO TM");
	//		DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
	//		System.out.println("CREO DAOPEDIDOS");
	//		List<EstadisticasPedidos> respuesta;
	//		try {
	//			System.out.println("ENTRO A TRY");
	//			this.conn = darConexion();
	//			daoPedidos.setConn(conn);
	//			respuesta = daoPedidos.darEstadisticasPedidosPorRestaurante(idRepresentante, password);
	////			System.out.println("after dao ------> " + zona.getId() + " || " + zona.getNombre());
	//			if(respuesta.size() == 0) {
	//				throw new Exception("NO EXISTE LA ZONA");
	//			}		
	//		}catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		}finally {
	//			try {
	//
	//				daoPedidos.cerrarRecursos();
	////				daoRes.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return respuesta;
	//	}

	/**
	 * MÃƒÂ©todo que obtiene las estadÃƒÂ­sticas de todos los pedidos del restaurante asociado al Representante, cuyos datos entran por parÃƒÂ¡metro.
	 * SÃƒÂ³lo el Representante tiene autorizaciÃƒÂ³n para ver los datos de su Restaurante.
	 * @param idRepresentante Long, ID del restaurante.
	 * @return List<EstadisticasPedidos> lista con las estadÃƒÂ­sticas del restaurante.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<EstadisticasPedidos> darEstadisticasPedidosPorRestaurante(Long idRepresentante)throws SQLException, Exception
	{

		//		Zona zona = null;
		//
		//		DAOTablaZonas daoZona = new DAOTablaZonas();
		//		DAOTablaRestaurantes daoRes = new DAOTablaRestaurantes();
		//		DAOTablaProductos daoProd = new DAOTablaProductos();
		DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
		List<EstadisticasPedidos> respuesta;
		try {
			this.conn = darConexion();
			daoPedidos.setConn(conn);
			respuesta = daoPedidos.darEstadisticasPedidosPorRestaurante(idRepresentante);
			//			System.out.println("after dao ------> " + zona.getId() + " || " + zona.getNombre());
			if(respuesta.size() == 0) {
				throw new Exception("No existe el Restaurante.");
			}		
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {

				daoPedidos.cerrarRecursos();
				//				daoRes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}
	//---------------------------------------------------	
	//	Requerimiento: RF17
	//---------------------------------------------------
	/**
	 * MÃƒÂ©todo que cancela un Pedido ordenado. El Pedido debe no estar servido para que sea vÃƒÂ¡lido.
	 * @param idPedido ID del pedido a cancelar.
	 * @throws SQLException
	 * @throws Exception
	 */
	public void cancelarPedido(Long idPedido) throws SQLException, Exception
	{
		DAOTablaPedidos daoPedidos = new DAOTablaPedidos();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoPedidos.setConn(conn);
			daoPedidos.cancelarPedido(idPedido);
			conn.commit();
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				conn.setAutoCommit(true);
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	//---------------------------------------------------	
	//	Requerimiento: RF4
	//---------------------------------------------------
	/**
	 * MÃƒÂ©todo para agregar un nuevo producto sin sus equivalencias.
	 * @param idRestaurante Long, ID del restaurante dueÃƒÂ±o de este producto.
	 * @param producto 
	 * @throws SQLException
	 * @throws Exception
	 */
	public ProductoLocal agregarProducto(Long idRestaurante, ProductoLocal producto) throws SQLException, Exception
	{
		ProductoLocal respuesta;
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoRestaurantes.setConn(conn);
			if(!daoRestaurantes.darEstadoOperacionRestaurante(idRestaurante))
			{
				throw new Exception("El Restaurante no se encuentra en operación.");
			}
			daoProductos.setConn(conn);
			respuesta = daoProductos.agregarProductoSinEquivalencias(idRestaurante, producto);
			conn.commit();
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				conn.setAutoCommit(true);
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}
	/**
	 * MÃƒÂ©todo que registra que dos Productos son equivalentes entre sÃƒÂ­.
	 * @param idRestaurante Long, ID del Restaurante dueÃƒÂ±o de los dos Productos.
	 * @param idProducto1 Long, ID del Producto 1 a relacionar.
	 * @param idProducto2 Long, ID del producto 2 a relacionar.
	 * @throws SQLException
	 * @throws Exception
	 */
	public void registrarProductosEquivalentes(Long idRestaurante, Long idProducto1, Long idProducto2, Long idRepresentante, String password)throws SQLException, Exception
	{
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try {
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoUsuarios.setConn(conn);
			daoRestaurantes.setConn(conn);
			if(!daoRestaurantes.darEstadoOperacionRestaurante(idRestaurante))
			{
				throw new Exception("El Restaurante no se encuentra en operación.");
			}
			conn.setAutoCommit(false);
			if(!daoUsuarios.verficarUsuarioRepresentante(idRepresentante, password, idRestaurante))
			{
				throw new Exception("Error en la verificaciÃ³n del Representante");
			}
			ProductoLocal producto1 = daoProductos.darProducto(idProducto1, idRestaurante);
			ProductoLocal producto2 = daoProductos.darProducto(idProducto2, idRestaurante);
			if(producto1 == null)
			{
				throw new Exception("El Restaurante con ID: " + idRestaurante + " no ofrece el producto con ID: " + idProducto1);
			}
			if(producto2 == null)
			{
				throw new Exception("El Restaurante con ID: " + idRestaurante + " no ofrece el producto con ID: " + idProducto2);
			}
			if(!producto1.getCategoria().equals(producto2.getCategoria()))
			{
				throw new Exception("Los productos son incompatibles para ser equivalentes, categorÃ­a incorrecta.");
			}
			conn.setAutoCommit(false);
			System.out.println("Isolation level: " + conn.getTransactionIsolation());;
			daoProductos.registrarEquivalenciaDeProductos(idRestaurante, idProducto1, idProducto2);
			conn.setAutoCommit(true);
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				conn.setAutoCommit(true);
				daoUsuarios.cerrarRecursos();
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	//---------------------------------------------------	
	//	Requerimiento: RF5
	//---------------------------------------------------
	/**
	 * MÃƒÂ©todo que agrega un nuevo Ingrediente a RotondAndes. Los ingredientes se comparten entre los restaurantes.
	 * @param ingrediente Ingrediente, toda la informaciÃƒÂ³n respecto al Ingrediente.
	 * @return Ingrediente, Ingrediente que se ha agregado.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Ingrediente agregarIngrediente(Ingrediente ingrediente) throws SQLException, Exception
	{
		Ingrediente respuesta;
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		try {
			this.conn = darConexion();
			daoIngredientes.setConn(conn);
			conn.setAutoCommit(false);
			respuesta = daoIngredientes.agregarIngredienteSinEquivalentes(ingrediente);
			conn.commit();
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				conn.setAutoCommit(true);
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}

	/**
	 * MÃƒÂ©todo que obtiene toda la informaciÃƒÂ³n de consulta de todos los clientes.
	 * @return List<ConsumoCliente>, Lista de consumos de los clientes.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<ConsumoCliente> darEstadisticasConsumoClientes()throws SQLException, Exception
	{
		DAOConsumoClientes daoConsumo = new DAOConsumoClientes();
		List<ConsumoCliente> respuesta;
		try {
			this.conn = darConexion();
			daoConsumo.setConn(conn);
			respuesta = daoConsumo.getConsumoClientes();
			for(int i = 0; i < respuesta.size(); i++)
			{
				Long idCliente = respuesta.get(i).getCliente().getId();
				respuesta.get(i).setCliente(darCliente(idCliente));
			}
			//			System.out.println("after dao ------> " + zona.getId() + " || " + zona.getNombre());
			if(respuesta.size() == 0) {
				throw new Exception("No existe el Restaurante.");
			}		
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {

				daoConsumo.cerrarRecursos();
				//				daoRes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}
	/**
	 * MÃƒÂ©todo que obtiene el consumo de un cliente especÃƒÂ­fico.
	 * @param idCliente Long, ID del cliente a consultar.
	 * @return List<ConsumoCliente>, Lista de consumos del cliente consultado.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<ConsumoCliente> darEstadisticasConsumoDeUnCliente(Long idCliente)throws SQLException, Exception
	{
		DAOConsumoClientes daoConsumo = new DAOConsumoClientes();
		List<ConsumoCliente> respuesta;
		try {
			this.conn = darConexion();
			daoConsumo.setConn(conn);
			respuesta = daoConsumo.getConsumoUnCliente(idCliente);
			for(int i = 0; i < respuesta.size(); i++)
			{
				respuesta.get(i).setCliente(darCliente(idCliente));
			}
			//			System.out.println("after dao ------> " + zona.getId() + " || " + zona.getNombre());
			if(respuesta.size() == 0) {
				throw new Exception("No existe el Restaurante.");
			}		
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {

				daoConsumo.cerrarRecursos();
				//				daoRes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}  	
	///**
	// * RF14
	// */

	//public 

	//---------------------------------------------------	
	//	Requerimiento: RF6
	//---------------------------------------------------
	/**
	 * MÃƒÂ©todo que agregar un MenÃƒÂº para un Restaurante.
	 * Al menos uno de los parÃƒÂ¡metros debe ser no nulo y el restaurante debe ofrecer los productos
	 * cuyos IDs estÃƒÂ¡ introduciendo por parÃƒÂ¡metro.
	 * @param idRestaurante Long, ID del Restaurante.
	 * @param idEntrada Long, ID de la Entrada.
	 * @param idPlatoFuerte Long, ID del Plato Fuerte.
	 * @param idPostre Long, ID del Postre.
	 * @param idBebida Long, ID de la Bebida.
	 * @param idAcompaniamiento Long, ID del AcompaÃƒÂ±amiento.
	 * @return Boolean, booleano que indica si el procedimiento fue exitoso.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Menu registrarMenu (Long idRestaurante, Menu menu) throws SQLException, Exception
	{
		Menu respuesta;
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try {
			this.conn = darConexion();
			//			conn.setAutoCommit(false);
			daoRestaurantes.setConn(conn);
			if(!daoRestaurantes.darEstadoOperacionRestaurante(idRestaurante))
			{
				throw new Exception("El Restaurante actualmente no está en servicio.");
			}
			ProductoLocal entrada;
			ProductoLocal platoFuerte;
			ProductoLocal postre;
			ProductoLocal bebida;
			ProductoLocal acompaniamiento;
			if(menu.getEntrada() != null)
			{
				menu.setEntrada(darProducto(menu.getEntrada().getId(), idRestaurante));
				entrada = darProducto(menu.getEntrada().getId(), idRestaurante);
				if(entrada == null)
				{
					throw new Exception("El Restaurante con ID: " + idRestaurante + " no sirve la Entrada con ID: " + menu.getEntrada().getId());
				}
				menu.setEntrada(entrada);
			}
			if(menu.getPlatoFuerte() != null)
			{
				menu.setPlatoFuerte(darProducto(menu.getPlatoFuerte().getId(), idRestaurante));
				platoFuerte = darProducto(menu.getPlatoFuerte().getId(), idRestaurante);
				if(platoFuerte == null)
				{
					throw new Exception("El Restaurante con ID: " + idRestaurante + " no sirve el Plato Fuerte con ID: " + menu.getPlatoFuerte().getId());
				}
				menu.setPlatoFuerte(platoFuerte);
			}
			if(menu.getPostre()!= null)
			{
				menu.setPostre(darProducto(menu.getPostre().getId(), idRestaurante));
				postre = darProducto(menu.getPostre().getId(), idRestaurante);
				if(postre == null)
				{
					throw new Exception("El Restaurante con ID: " + idRestaurante + " no sirve el Postre con ID: " + menu.getPostre().getId());
				}
				menu.setPostre(postre);
			}
			if(menu.getBebida() != null)
			{
				menu.setBebida(darProducto(menu.getBebida().getId(), idRestaurante));
				bebida = darProducto(menu.getBebida().getId(), idRestaurante);
				if(bebida == null)
				{
					throw new Exception("El Restaurante con ID: " + idRestaurante + " no sirve la Bebida con ID: " + menu.getBebida().getId());
				}
				menu.setBebida(bebida);
			}
			if(menu.getAcompaniamiento() != null)
			{
				menu.setAcompaniamiento(darProducto(menu.getAcompaniamiento().getId(), idRestaurante));
				acompaniamiento = darProducto(menu.getAcompaniamiento().getId(), idRestaurante);
				if(acompaniamiento == null)
				{
					throw new Exception("El Restaurante con ID: " + idRestaurante + " no sirve el acompaÃ±amiento con ID: " + menu.getAcompaniamiento().getId());
				}
				menu.setAcompaniamiento(acompaniamiento);
			}
			respuesta = daoRestaurantes.registrarMenu(idRestaurante, menu);
			//			conn.commit();
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				//				conn.setAutoCommit(true);
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}
	//---------------------------------------------------	
	//	Requerimiento: RF3
	//---------------------------------------------------
	/**
	 * MÃƒÂ©todo que regitra un Restaurante en la base de datos.
	 * @param restaurante Restaurante, informaciÃƒÂ³n del Restaurante.
	 * @param representante Representante, informaciÃƒÂ³n del Representante.
	 * @param precio
	 * @param idZona
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Restaurante registrarRestaurante(Restaurante restaurante, Representante representante, Long idZona) throws SQLException, Exception
	{
		Restaurante respuesta;
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			//			conn.setAutoCommit(false);
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoUsuarios.setConn(conn);
			Zona zona = darZona(idZona);
			if(zona == null)
			{
				throw new Exception("Zona no existe");
			}
			respuesta = daoRestaurantes.registrarRestaurante(restaurante, representante, zona.getId());

			daoUsuarios.registrarRepresentante(representante);
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				//				conn.setAutoCommit(true);
				daoUsuarios.cerrarRecursos();
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}
	//---------------------------------------------------	
	//	Requerimiento: RF2
	//---------------------------------------------------
	/**
	 * MÃƒÂ©todo que agrega un Cliente Frecuente.
	 * @param cliente ClienteFrecuente, informaciÃƒÂ³n del cliente a agregar.
	 * @return ClienteFrecuente, informaciÃƒÂ³n del cliente agregado.
	 * @throws SQLException
	 * @throws Exception
	 */
	public ClienteFrecuente registrarClienteFrecuente(ClienteFrecuente cliente) throws SQLException, Exception
	{
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoUsuarios.setConn(conn);
			cliente = daoUsuarios.registrarClienteFrecuente(cliente);
			conn.commit();
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				conn.setAutoCommit(true);
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cliente;
	}
	//---------------------------------------------------	
	//	Requerimiento: RF1
	//---------------------------------------------------
	/**
	 * Método que agrega un Cliente.
	 * @param cliente Cliente, informaciÃ³n del cliente a agregar.
	 * @return ClienteFrecuente, informaciÃƒÂ³n del cliente agregado.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Cliente registrarCliente(Cliente cliente) throws SQLException, Exception
	{
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoUsuarios.setConn(conn);
			cliente = daoUsuarios.registrarCliente(cliente);
			conn.commit();
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				daoUsuarios.cerrarRecursos();
				conn.setAutoCommit(true);
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cliente;
	}

	/**
	 * Método que agrega un Administrador.
	 * @param id Long, ID del Administrador.
	 * @param password String, contraseÃ±a del Administrador.
	 * @return Boolean, Booleano que determina si se agregÃ³ exitosamente el Administrador o no.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean registrarAdministrador(Long id, String password) throws SQLException, Exception
	{
		Boolean respuesta = false;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoUsuarios.setConn(conn);
			respuesta = daoUsuarios.registrarAdministrador(id, password);
			conn.commit();
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				conn.setAutoCommit(true);
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}
	/**
	 * Método que da el o los Productos más ofrecidos en RotondAndes.
	 * @return List<Producto>, Lista de Productos más ofrecidos.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<ProductoLocal> darProductoMasOfrecido() throws SQLException, Exception
	{
		List<ProductoLocal> productos;
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			this.conn = darConexion();
			daoProductos.setConn(conn);
			productos = daoProductos.darProductosMasOfrecidos();

		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos;
	}
	/**
	 * Método que obtiene los productos más vendidos en RotondAndes.
	 * @return List<Producto>, Lista de Productos más Vendidos.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<ProductoLocal> darProductoMasVendido() throws SQLException, Exception
	{
		List<ProductoLocal> productos = new ArrayList<ProductoLocal>();
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			this.conn = darConexion();
			daoProductos.setConn(conn);
			List<Long[]> idProductos = daoProductos.darProductosMasVendido();
			for(int i = 0; i < idProductos.size(); i++)
			{
				Long [] ids = idProductos.get(i);
				productos.add(darProducto( ids[0], ids[1]));
			}

		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				daoProductos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return productos;
	}
	//---------------------------------------------------	
	//	Requerimiento: RFC11A
	//---------------------------------------------------
	/**
	 * Método que da el o los Productos más ofrecidos en RotondAndes.
	 * @return List<Producto>, Lista de Productos más ofrecidos.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean establecerRegistroVentas(Long id, String password, String fecha, String dia) throws SQLException, Exception
	{
		DAOConsumoClientes dao = new DAOConsumoClientes();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			if(!daoUsuarios.verficarUsuarioAdministrador(id, password))
			{
				throw new Exception("Error en validar las credenciales del usuario con ID: " + id);
			}
			daoUsuarios.cerrarRecursos();
			dao.setConn(conn);
			dao.setRegistroVentas(fecha, dia);
			conn.commit();
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				daoUsuarios.cerrarRecursos();
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return true;
	}
	//---------------------------------------------------	
	//	Requerimiento: RFC11B
	//---------------------------------------------------
	/**
	 * Método que da el Registro de Ventas de la Semana.
	 * @return List<RegistroVentas> Lista de Registro de Ventas.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<RegistroVentas> obtenerRegistroVentas() throws SQLException, Exception
	{
		List<RegistroVentas> lista = null;
		DAOConsumoClientes dao = new DAOConsumoClientes();
		//		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			//			daoUsuarios.setConn(conn);
			//			if(!daoUsuarios.verficarUsuarioAdministrador(id, password))
			//			{
			//				throw new Exception("Error en validar las credenciales del usuario con ID: " + id);
			//			}
			//			daoUsuarios.cerrarRecursos();
			dao.setConn(conn);
			lista = dao.getRegistroVentas();
			for(int i = 0; i < lista.size(); i++)
			{
				Long idRestMas = lista.get(i).getRestauranteMasFrecuentado().getId();
				Long idRestMenos = lista.get(i).getRestauranteMenosFrecuentado().getId();
				Restaurante restauranteMasFrecuentado = darRestaurante(idRestMas, false);
				Restaurante restauranteMenosFrecuentado = darRestaurante(idRestMenos, false);
				lista.get(i).setRestauranteMasFrecuentado(restauranteMasFrecuentado);
				lista.get(i).setRestauranteMenosFrecuentado(restauranteMenosFrecuentado);
			}
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				//				daoUsuarios.cerrarRecursos();
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return lista;
	}

	/**
	 * Método que obtiene la informaciÃ³n de un Restaurante.
	 * @param idRestaurante Long, ID del Restaurante
	 * @return Restaurante, Toda la informaciÃ³n de un Restaurante.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Restaurante darRestaurante(Long idRestaurante, Boolean deseaProductos) throws SQLException, Exception
	{
		Restaurante respuesta = null;
		DAOTablaRestaurantes dao = new DAOTablaRestaurantes();
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			daoProductos.setConn(conn);
			respuesta = dao.obtenerRestaurante(idRestaurante);
			if(deseaProductos)
			{
				Integer integer = 1;
				String idRest = idRestaurante + "";
				respuesta.setProductos(daoProductos.darProductosPor(integer, idRest));
				
			}
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}
	//---------------------------------------------------	
	//	Requerimiento: RFC9
	//---------------------------------------------------

	/**
	 *Métoodo que llama al RFC9 que retorna los clientes que al menos hayan pedido un producto de un restaurante dado con un rango de fechas dado. 
	 * RFC9 Consultar consumo en Rotondandes
	 * @param idRestaurante id del restaurante determinado
	 * @param fecha1 fecha inicial
	 * @param fecha2 fecha final
	 * @param orderBy como decea el usuario organizar los resultados
	 * @param groupBy como decea el usuario agrupar los resultados
	 * @param idUsuario el idUsuario para autorizaciÃ³n
	 * @param contraseniaa la contraseÃ±a del usuario para autorizaciÃ³n
	 * @return Clientes
	 * @throws Exception 
	 */
	public List<Cliente> getClientesConMinUnConsumoEnRangoFechasPorRestaurante(Long idRestaurante, String fecha1, String fecha2, Integer criterio, String orderBy) throws Exception
	{
		DAOTablaClientes dao = new DAOTablaClientes(); 
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			clientes = dao.getClientesConMinUnConsumoEnRangoFechasPorRestaurante(idRestaurante, fecha1, fecha2, criterio, orderBy);
			for(int i = 0; i < clientes.size(); i++)
			{
				Long idCliente = clientes.get(i).getId();
				Cliente clienteCompleto = dao.darClienteSinOrdenes(idCliente);
				clientes.set(i, clienteCompleto);
			}
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes;
	}

	/**
	 * Método que obtiene si un cliente ha consumido un producto de un Restaurante dado por ID
	 * en un rango de fechas.
	 * @param idCliente Long, ID del Cliente.
	 * @param idRestaurante Long, ID del Restaurante.
	 * @param fecha1 String, Cadena de la Fecha 1.
	 * @param fecha2 String, Cadena de la Fecha 2.
	 * @param criterio Integer, Entero con el tipo de Criterio, se traduce en el DAO.
	 * @param orderBy String, valor según el cual ordenar.
	 * @return List<Cliente>, Lista con el Cliente que no ha consumido.
	 * @throws Exception
	 */
	public List<Cliente> getClienteConMinUnConsumoEnRangoFechasPorRestaurante(Long idCliente, Long idRestaurante, String fecha1, String fecha2, Integer criterio, String orderBy) throws Exception
	{
		DAOTablaClientes dao = new DAOTablaClientes(); 
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			clientes = dao.getClienteConMinUnConsumoEnRangoFechasPorRestaurante(idCliente, idRestaurante, fecha1, fecha2, criterio, orderBy);
			for(int i = 0; i < clientes.size(); i++)
			{
				Long idClienteTemp = clientes.get(i).getId();
				Cliente clienteCompleto = dao.darClienteSinOrdenes(idClienteTemp);
				clientes.set(i, clienteCompleto);
			}
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes;
	}
	//---------------------------------------------------	
	//	Requerimiento: RFC10A
	//---------------------------------------------------

	/**
	 * Métoodo que llama al RFC9 que retorna los clientes que al menos hayan pedido un producto de un restaurante dado con un rango de fechas dado. 
	 * RFC10 Consultar consumo en Rotondandes
	 * @param idRestaurante id del restaurante determinado
	 * @param fecha1 fecha inicial
	 * @param fecha2 fecha final
	 * @param orderBy como decea el usuario organizar los resultados
	 * @param groupBy como decea el usuario agrupar los resultados
	 * @param idUsuario el idUsuario para autorizaciÃ³n
	 * @param contraseniaa la contraseÃ±a del usuario para autorizaciÃ³n
	 * @return Clientes
	 * @throws Exception 
	 */

	public List<Cliente> getClientesSinMinUnConsumoEnRangoFechasEnRestaurante(Long idRestaurante, String fecha1, String fecha2, Integer criterio, String orderBy) throws Exception
	{
		DAOTablaClientes dao = new DAOTablaClientes(); 
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			clientes = dao.getClientesSinMinUnConsumoEnRangoFechasEnRestaurante(idRestaurante, fecha1, fecha2, criterio, orderBy);
			for(int i = 0; i < clientes.size(); i++)
			{
				Long idCliente = clientes.get(i).getId();
				Cliente clienteCompleto = dao.darClienteSinOrdenes(idCliente);
				clientes.set(i, clienteCompleto);
			}
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes;
	}
	//---------------------------------------------------	
	//	Requerimiento: RFC10B
	//---------------------------------------------------
	/**
	 * Método que obtiene si un cliente NO ha consumido un producto de un Restaurante dado por ID
	 * en un rango de fechas.
	 * @param idCliente Long, ID del Cliente.
	 * @param idRestaurante Long, ID del Restaurante.
	 * @param fecha1 String, Cadena de la Fecha 1.
	 * @param fecha2 String, Cadena de la Fecha 2.
	 * @param criterio Integer, Entero con el tipo de Criterio, se traduce en el DAO.
	 * @param orderBy String, valor según el cual ordenar.
	 * @return List<Cliente>, Lista con el Cliente que no ha consumido.
	 * @throws Exception
	 */
	public List<Cliente> getClienteSinMinUnConsumoEnRangoFechasPorRestaurante(Long idCliente, Long idRestaurante, String fecha1, String fecha2, Integer criterio, String orderBy) throws Exception
	{
		DAOTablaClientes dao = new DAOTablaClientes(); 
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			clientes = dao.getClienteSinMinUnConsumoEnRangoFechasPorRestaurante(idCliente, idRestaurante, fecha1, fecha2, criterio, orderBy);
			for(int i = 0; i < clientes.size(); i++)
			{
				Long idClienteTemp = clientes.get(i).getId();
				Cliente clienteCompleto = dao.darClienteSinOrdenes(idClienteTemp);
				clientes.set(i, clienteCompleto);
			}
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes;
	}
	//---------------------------------------------------	
	//	Requerimiento: RF12
	//---------------------------------------------------

	/**
	 * RFC12 Consultar los buenos clientes
	 * @param String tipo
	 * @param Long idUsuario
	 * @param String contrasenia
	 * @resp List<Cliente> de un tipo dado
	 * 1.5 SMDLV = 1.5 * 737.717 = 1106.5755
	 */

	public List<Cliente> getBuenosClientesTipo(String tipo, Long idUsuario, String contrasenia) throws Exception
	{
		DAOTablaClientes daoCli = new DAOTablaClientes();
		return daoCli.getBuenosClientesTipo(tipo, idUsuario, contrasenia);
	}
	//---------------------------------------------------	
	//	Requerimiento: RF19
	//---------------------------------------------------
	/**
	 * Método que retira un Restaurante del Servicio.
	 * @param idRestaurante Long, ID del Restaurante a Retirar.
	 * @return Restaurante, información del Restaurante retirado.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean retirarRestauranteDelServicio(Long idRestaurante)throws SQLException, Exception
	{
		DAOTablaRestaurantes dao = new DAOTablaRestaurantes();
//		Restaurante respuesta = null;
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			if(!dao.darEstadoOrdenesRestaurante(idRestaurante))
			{
				throw new Exception("El Restaurante aún tiene Pedidos pendientes por Despachar.");
			}
			dao.retirarRestaurante(idRestaurante);
			System.out.println("POST RETIRAR RESTAURANTE");
//			respuesta = dao.obtenerRestaurante(idRestaurante);
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		System.out.println("PRE RETURN");
		return true;
	}
	//---------------------------------------------------	
	//	Requerimiento: RFC5A
	//---------------------------------------------------
	/**
	 * Método que obtiene la Rentabilidad de los Restaurantes según unos parámetros de búsqueda.
	 * @param fecha1 String, cadena de la primera fecha del rango de consulta.
	 * @param fecha2 String, cadena de la segunda fecha del rango de consulta.
	 * @param criterio Integer, Criterio según el cual se va a realizar la consulta.
	 * @param idProducto Long, ID del Producto según el cual se va a realizar la consulta, si aplica.
	 * @return List<RentabilidadRestaurante>, Lista con las Rentabilidades de todos los Restaurantes en el Rango de búsqueda.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<RentabilidadRestaurante> darRentabilidadRestaurantes(String fecha1, String fecha2, Integer criterio, Long idProducto)throws SQLException, Exception
	{
		DAOTablaRestaurantes dao = new DAOTablaRestaurantes();
		List<RentabilidadRestaurante> respuesta = null;
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			respuesta = dao.darRentabilidadDeRestaurantes(fecha1, fecha2, criterio, idProducto);
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}
	//---------------------------------------------------	
	//	Requerimiento: RFC5B
	//---------------------------------------------------
	/**
	 * Método que obtiene la Rentabilidad de un Restaurante específico.
	 * @param fecha1 String, cadena de la primera fecha del rango de consulta.
	 * @param fecha2 String, cadena de la segunda fecha del rango de consulta.
	 * @param criterio Integer, Criterio según el cual se va a realizar la consulta.
	 * @param idProducto Long, ID del Producto según el cual se va a realizar la consulta, si aplica.
	 * @param idRestaurante Long, ID del Restaurante a consultar.
	 * @return List<RentabilidadRestaurante>, Lista con las Rentabilidades del Restaurante consultado en el rango de búsqueda.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<RentabilidadRestaurante> darRentabilidadRestaurante(String fecha1, String fecha2, Integer criterio, Long idProducto, Long idRestaurante)throws SQLException, Exception
	{
		DAOTablaRestaurantes dao = new DAOTablaRestaurantes();
		List<RentabilidadRestaurante> respuesta = null;
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			respuesta = dao.darRentabilidadDeRestaurante(fecha1, fecha2, criterio, idProducto, idRestaurante);
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}
	//---------------------------------------------------	
	//	Requerimiento: RFC14
	//---------------------------------------------------
	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaRentabilidad darRentabilidadRestaurantesUniversal(String fecha1, String fecha2, Integer criterio, Long idProducto) throws Exception {
		List<RentabilidadRestaurante> lista = darRentabilidadRestaurantes(fecha1, fecha2, criterio, idProducto);
		ListaRentabilidad remL = new ListaRentabilidad(lista);
		try
		{
			String parametrosUnidos = fecha1 + "," + fecha2 + "," + criterio + "," + idProducto;
			System.out.println("PARÁMETROS UNIDOS: " + parametrosUnidos);
			ListaRentabilidad resp = dtm.getRemoteRentabilidades(parametrosUnidos);
			System.out.println(resp.getRentabilidades().size());
			remL.getRentabilidades().addAll(resp.getRentabilidades());
		}
		catch(NonReplyException e)
		{
			
		}
		return remL;
	}
	//---------------------------------------------------	
	//	Requerimiento: RFC13
	//---------------------------------------------------
	public ListaProductos darTodosLosProductosUniversal()throws Exception
	{
		List<Producto> lista = darProductosSinCantidad();
		ListaProductos remL = new ListaProductos(lista);
		try
		{
			ListaProductos resp = dtm.getRemoteTodosLosProductos();
			System.out.println(resp.getProductos().size());
			remL.getProductos().addAll(resp.getProductos());
			ObjectMapper mapper = new ObjectMapper();
			String respuesta = mapper.writeValueAsString(remL);
			System.out.println("Respuesta: " + respuesta);
		}
		catch(NonReplyException e)
		{
			System.out.println("EXCEPCIÓN: " + e);
		}
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			String respuesta = mapper.writeValueAsString(remL);
			System.out.println("Respuesta: " + respuesta);
			
			
			Producto producto = lista.get(0);
//			System.out.println("PARTE 1:");
//			String valor1 = mapper.writeValueAsString(producto);
//			ProductoGranConexion productoFromMapper = mapper.readValue(valor1, ProductoGranConexion.class);
//			System.out.println("Cadena 1: " + valor1);
//			System.out.println(productoFromMapper.getId());
//			System.out.println(productoFromMapper.getCategoria());
//			System.out.println(productoFromMapper.getDescripcionEspaniol());
//			System.out.println(productoFromMapper.getDescripcionIngles());
//			System.out.println(productoFromMapper.getCostoDeProduccion());
			
			System.out.println("PARTE 2:");
			Producto subProducto = new Producto(producto.getId(), producto.getNombre(), producto.getDescripcionEspaniol(), producto.getDescripcionIngles(), producto.getCostoDeProduccion(), producto.getProductosEquivalentes(), producto.getPrecio(), null, producto.getCategoria(), producto.getIngredientes());
			String valor2 = mapper.writeValueAsString(subProducto);
			System.out.println("Cadena 2: " + valor2);
			ProductoLocal productoFromMapper2 = mapper.readValue(valor2, ProductoLocal.class);
			
			System.out.println(productoFromMapper2.getId());
			System.out.println(productoFromMapper2.getCategoria());
			System.out.println(productoFromMapper2.getDescripcionEspaniol());
			System.out.println(productoFromMapper2.getDescripcionIngles());
			System.out.println(productoFromMapper2.getCostoDeProduccion());
			System.out.println(productoFromMapper2.getCantidad());
			
//			System.out.println("PARTE 3:");
//			producto.setCantidad(null);
//			String valor3 = mapper.writeValueAsString(producto);
//			ProductoGranConexion productoFromMapper = mapper.readValue(valor3, ProductoGranConexion.class);
//			System.out.println("Cadena 1: " + valor3);
//			System.out.println(productoFromMapper.getId());
//			System.out.println(productoFromMapper.getCategoria());
//			System.out.println(productoFromMapper.getDescripcionEspaniol());
//			System.out.println(productoFromMapper.getDescripcionIngles());
//			System.out.println(productoFromMapper.getCostoDeProduccion());
		}
		catch(Exception e)
		{
			System.out.println("ERROR EN GENERACIÓN: " + e.getMessage());
		}
		return remL;
	}
	//---------------------------------------------------	
	//	Requerimiento: RFC13
	//---------------------------------------------------
	/**
	 * Método que obtiene los Productos según parametros de búsqueda
	 * @param filtro
	 * @param parametro
	 * @return
	 * @throws Exception
	 */
	public ListaProductos darProductosPorUniversal(Integer filtro, String parametro)throws Exception
	{
		List<Producto> lista = darProductosPorSinCantidad(filtro, parametro);
		ListaProductos remL = new ListaProductos(lista);
		try
		{
			String parametrosUnidos = filtro + "," + parametro;
			System.out.println("PARÁMETROS UNIDOS: " + parametrosUnidos);
			ListaProductos resp = dtm.getRemoteProductos(parametrosUnidos);
			System.out.println(resp.getProductos().size());
			remL.getProductos().addAll(resp.getProductos());
		}
		catch(NonReplyException e)
		{
			
		}
		return remL;
	}
	/**
	 * Método que confirma las Credenciales de un administrador para los métodos universales.
	 * @param idAdmin Long, ID del Administrador.
	 * @param passwordAdmin String, contraseña del Administrador.
	 * @return Boolean.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean confirmarCredencialesAdministrador(Long idAdmin, String passwordAdmin)throws SQLException, Exception
	{
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			daoUsuarios.setConn(conn);
			if(!daoUsuarios.verficarUsuarioAdministrador(idAdmin, passwordAdmin))
			{
				throw new Exception("Credenciales de administrador inválidas.");
			}
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		System.out.println("PRE RETURN");
		return true;
	}
	//---------------------------------------------------	
	//	Requerimiento: RF19
	//---------------------------------------------------
	/**
	 * Método que retira un Restaurante aquí, y en otras bases de Datos.
	 * @param idAdmin Long, ID del Administrador Local.
	 * @param passwordAdmin String, contraseña del Administrador Local.
	 * @param idRestaurante Long, ID del Restaurante a retirar.
	 * @return ListaConfirmaciones
	 * @throws Exception
	 */
	public ListaConfirmaciones retirarRestauranteUniversal(Long idAdmin, String passwordAdmin, Long idRestaurante)throws Exception
	{
		List<Boolean> lista = new ArrayList<Boolean>();
		lista.add(retirarRestauranteDelServicio(idRestaurante));
		ListaConfirmaciones remL = new ListaConfirmaciones(lista);
		confirmarCredencialesAdministrador(idAdmin, passwordAdmin);
		try
		{
			String parametrosUnidos = "" + idRestaurante;
			System.out.println("PARÁMETROS UNIDOS: " + parametrosUnidos);
			ListaConfirmaciones resp = dtm.retirarRemoteRestaurantes(parametrosUnidos);
			System.out.println(resp.getConfirmaciones().size());
			remL.getConfirmaciones().addAll(resp.getConfirmaciones());
		}
		catch(NonReplyException e)
		{
			
		}
		return remL;
	}
	//---------------------------------------------------	
	//	Requerimiento: RF18
	//---------------------------------------------------
	/**
	 * Método que Registra un Pedido aquí, y en otras bases de Dato.
	 * @param idPedido Long, ID del pedido a ordenar.
	 * @param idRestaurante Long, ID del Restaurante al cual solicitar el Pedido.
	 * @param idOrden Long, ID de la Orden a nombre de la cual se asigna el Pedido (funcionamiento local).
	 * @param idCliente Long, ID del Cliente que realiza el pedido.
	 * @param idProducto Long, ID del Producto a Ordenar.
	 * @param grupo1 Boolean, Booleano que indica si este Pedido se está ordenando a la base de datos 1 (local en este caso).
	 * @param grupo2 Boolean, Booleano que indica si este Pedido se está ordenando a la base de datos 2 (C - 03).
	 * @param grupo3 Boolean, Booleano que indica si este Pedido se está ordenando a la base de datos 3 (B - 09).
	 * @return
	 * @throws Exception
	 */
	public ListaPedidosConexion registrarPedidoProductoUniversal(Long idPedido, Long idRestaurante, Long idOrden, Long idCliente, Long idProducto, Boolean grupo1, Boolean grupo2, Boolean grupo3)throws Exception
	{
		List<PedidoConexion> lista = new ArrayList<PedidoConexion>();
		ListaPedidosConexion remL = new ListaPedidosConexion(lista);
		try
		{
			Cliente cliente = darCliente(idCliente);
			if(grupo1)
			{
				PedidoConexion pedidoLocal = registrarPedido(idCliente, idProducto, idRestaurante, idOrden).toPedidoConexion(null, idCliente, cliente.getNombre(), idOrden, cliente.getMesa(), PedidoConexion.BASEDEDATOS1);
				pedidoLocal.setGrupo1(true);
				remL.getPedidosConexion().add(pedidoLocal);
			}
			if(grupo2 || grupo3)
			{
				PedidoConexion pedidoRemoto = new PedidoConexion(idPedido, null, idProducto, null, idRestaurante, null, idCliente, cliente.getNombre(), idOrden, false, null, null, cliente.getMesa(), null, null, null, null, null, PedidoConexion.BASEDEDATOS1);
				if(grupo2)
				{
					pedidoRemoto.setGrupo2(true);
				}
				if(grupo3)
				{
					pedidoRemoto.setGrupo3(true);
				}
				ListaPedidosConexion resp = dtm.registrarPedidoRemoto(pedidoRemoto);
				System.out.println(resp.getPedidosConexion().size());
				remL.getPedidosConexion().addAll(resp.getPedidosConexion());
			}
		}
		catch(NonReplyException e)
		{
			
		}
		return remL;
	}
	
	//---------------------------------------------------	
	//	Requerimiento: RF9 Parte 1
	//---------------------------------------------------
	
//	/**
//	 * Método que crea una Orden a nombre de un Cliente o Cliente Frecuente.
//	 * @param orden Orden, InformaciÃ³n de la Orden a crear.
//	 * @return Orden, La Orden recientemente creada.
//	 * @throws SQLException
//	 * @throws Exception
//	 */
//	public Orden registrarNuevaOrdenRemoto(Orden orden)throws SQLException, Exception
//	{
//		DAOTablaPedidos dao = new DAOTablaPedidos();
//		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
//		try {
//			this.conn = darConexion();
//			conn.setAutoCommit(false);
//			dao.setConn(conn);
//			daoUsuarios.setConn(conn);
//			//TODO Verificar Cliente
//			daoUsuarios.verficarUsuarioCliente(orden.getCliente().getId());
//			orden = dao.registrarNuevaOrden(orden);
//		}catch (SQLException e) {
//			System.err.println("SQLException:" + e.getMessage());
//			e.printStackTrace();
//			throw e;
//		} catch (Exception e) {
//			System.err.println("GeneralException:" + e.getMessage());
//			e.printStackTrace();
//			throw e;
//		}finally {
//			try {
//				conn.setAutoCommit(true);
//				daoUsuarios.cerrarRecursos();
//				dao.cerrarRecursos();
//				if(this.conn!=null)
//					this.conn.close();
//			} catch (SQLException exception) {
//				System.err.println("SQLException closing resources:" + exception.getMessage());
//				exception.printStackTrace();
//				throw exception;
//			}
//		}
//		return orden;
//	}
	//---------------------------------------------------	
	//	Requerimiento: RF18A, Caso en el que se recibe un Pedido de otra base de Datos.
	//---------------------------------------------------
	/**
	 * Método para registrar un Pedido a una Orden.
	 * @param idCliente Long, ID del Cliente.
	 * @param origen Integer, número de la base de datos de procedencia de la orden.
	 * @param idProd Long, ID del Producto a Ordenar.
	 * @param idRestProd Long, ID del Restaurante al cual ordenar el Producto.
	 * @return PedidoConexion, toda la información del Pedido, apta para la comunicación.
	 * @throws SQLException
	 * @throws Exception
	 */
	public PedidoConexion registrarPedidoExterno(PedidoConexion pedido) throws SQLException, Exception {
		PedidoConexion res = null;
		DAOTablaPedidos dao = new DAOTablaPedidos();
		DAOTablaClientes daoClientes = new DAOTablaClientes();
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			daoClientes.setConn(conn);
			daoRestaurantes.setConn(conn);
			if(!daoRestaurantes.darEstadoOperacionRestaurante(pedido.getIdRestaurante()))
			{
				throw new Exception("El Restaurante actualmente no está en servicio.");
			}
			//			conn.setAutoCommit(false);
			if(daoClientes.darClienteExterno(pedido.getIdCliente(), pedido.getOrigenPedido()) == null)
			{
				daoClientes.crearClienteExterno(pedido.getIdCliente(), pedido.getNombreCliente(), pedido.getIdMesa(), pedido.getOrigenPedido());
			}
			Restaurante rest = daoRestaurantes.obtenerRestaurante(pedido.getIdRestaurante());
			ProductoLocal producto = daoProductos.darProducto(pedido.getIdProducto(), pedido.getIdRestaurante());
			PedidoConexion pedidoLocal = dao.registrarNuevoPedidoExterno(pedido.getIdCliente(), pedido.getOrigenPedido(), producto, pedido.getIdRestaurante());
			pedidoLocal.setNombreCliente(pedido.getNombreCliente());
			pedidoLocal.setIdMesa(pedido.getIdMesa());
			pedidoLocal.setNombreRestaurante(rest.getName());
			dao.updateCostoTotalOrdenExterna(pedidoLocal.getIdOrden(), pedidoLocal.getPrecio());
			res = pedidoLocal;
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				//				conn.setAutoCommit(true);
				daoClientes.cerrarRecursos();
				daoRestaurantes.cerrarRecursos();
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return res;
	}
	//---------------------------------------------------	
	//	Requerimiento: RF18B
	//---------------------------------------------------
	/**
	 * Método que Registra el Pedido de un MenÃº.
	 * @param id Long, ID del Cliente.
	 * @param idPedido Long, ID del pedido.
	 * @param idMenu Long, ID del MenÃº a registar.
	 * @param idRestMenu Long, ID del Restaurante dueÃ±o del menÃº.
	 * @param idOrden Long, ID de la Orden a la cual se va a asignar el Pedido.
	 * @return PedidoDeMenu
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<PedidoConexion> registrarPedidoMenuExterno(PedidoConexion pedido) throws SQLException, Exception {
		List<PedidoConexion> lista = new ArrayList<PedidoConexion>();
		DAOTablaPedidos dao = new DAOTablaPedidos();
		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
		DAOTablaClientes daoClientes = new DAOTablaClientes();
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			daoRestaurantes.setConn(conn);
			daoClientes.setConn(conn);
			daoProductos.setConn(conn);
			if(!daoRestaurantes.darEstadoOperacionRestaurante(pedido.getIdRestaurante()))
			{
				throw new Exception("El Restaurante actualmente no está en servicio.");
			}
			if(daoClientes.darClienteExterno(pedido.getIdCliente(), pedido.getOrigenPedido()) == null)
			{
				daoClientes.crearClienteExterno(pedido.getIdCliente(), pedido.getNombreCliente(), pedido.getIdMesa(), pedido.getOrigenPedido());
			}
			//			conn.setAutoCommit(false);
			Menu menu = daoProductos.darMenu(pedido.getIdMenu(), pedido.getIdRestaurante());
			lista = dao.registrarNuevoPedidoMenuExterno(pedido.getIdCliente(), pedido.getOrigenPedido(), menu, pedido.getIdRestaurante());
			Restaurante restauranteTemp = daoRestaurantes.obtenerRestaurante(pedido.getIdRestaurante());
			for(int i = 0; i < lista.size(); i++)
			{
				lista.get(i).setNombreCliente(pedido.getNombreCliente());
				lista.get(i).setIdMesa(pedido.getIdMesa());
				lista.get(i).setNombreRestaurante(restauranteTemp.getName());
			}
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			try {
				//				conn.setAutoCommit(true);
				dao.cerrarRecursos();
				daoProductos.cerrarRecursos();
				daoClientes.cerrarRecursos();
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return lista;
	}
	
	//---------------------------------------------------	
	//	Requerimiento: RF9 Parte 2B
	//---------------------------------------------------
	/**
	 * Método que Registra el Pedido de un MenÃº.
	 * @param id Long, ID del Cliente.
	 * @param idPedido Long, ID del pedido.
	 * @param idMenu Long, ID del MenÃº a registar.
	 * @param idRestMenu Long, ID del Restaurante dueÃ±o del menÃº.
	 * @param idOrden Long, ID de la Orden a la cual se va a asignar el Pedido.
	 * @return PedidoDeMenu
	 * @throws SQLException
	 * @throws Exception
	 */
//	public PedidoDeMenu registrarPedidoMenu(Long idCliente, Long idMenu, Long idRestMenu, Long idOrden) throws SQLException, Exception {
//		PedidoDeMenu res = null;
//		DAOTablaPedidos dao = new DAOTablaPedidos();
//		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
//		DAOTablaRestaurantes daoRestaurantes = new DAOTablaRestaurantes();
//		try {
//			this.conn = darConexion();
//			dao.setConn(conn);
//			daoUsuarios.setConn(conn);
//			daoRestaurantes.setConn(conn);
//			if(!daoRestaurantes.darEstadoOperacionRestaurante(idRestMenu))
//			{
//				throw new Exception("El Restaurante actualmente no está en servicio.");
//			}
//			//			conn.setAutoCommit(false);
//			if(dao.getEstatusOrden(idOrden))
//			{
//				throw new Exception("La Orden con ID: " + idOrden + " ya ha sido confirmada y no puede recibir nuevos Pedidos.");
//			}
//			Orden orden = dao.obtenerOrden(idOrden);
//			if(!orden.getCliente().getId().equals(idCliente))
//			{
//				throw new Exception("La Orden con ID: " + idOrden + " no está a nombre de este cliente.");
//			}
//
//			if(!daoUsuarios.verficarUsuarioCliente(idCliente))
//			{
//				throw new Exception ("Informacion de Cliente invalida.");
//			}
//			Menu menu = darMenu(idOrden, idRestMenu);
//			res = dao.registrarPedidoMenu(menu, idOrden, idRestMenu);
//			dao.updateCostoTotalOrden(idOrden, menu.getPrecio());
//		}catch (SQLException e) {
//			System.err.println("SQLException:" + e.getMessage());
//			e.printStackTrace();
//			throw e;
//		} catch (Exception e) {
//			System.err.println("GeneralException:" + e.getMessage());
//			e.printStackTrace();
//			throw e;
//		}finally {
//			try {
//				//				conn.setAutoCommit(true);
//				daoUsuarios.cerrarRecursos();
//				dao.cerrarRecursos();
//				if(this.conn!=null)
//					this.conn.close();
//			} catch (SQLException exception) {
//				System.err.println("SQLException closing resources:" + exception.getMessage());
//				exception.printStackTrace();
//				throw exception;
//			}
//		}
//		return res;
//	}
}
