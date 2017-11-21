/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (BogotÃƒÂ¡ - Colombia)
 * Departamento de IngenierÃƒÂ­a de Sistemas y ComputaciÃƒÂ³n
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
import java.util.Date;
import java.util.List;
import java.util.Properties;

import dao.DAOConsumoClientes;
import dao.DAOTablaClientes;
import dao.DAOTablaClientesFrecuentes;
import dao.DAOTablaIngredientes;
import dao.DAOTablaPedidos;
import dao.DAOTablaProductos;
import dao.DAOTablaRestaurantes;
import dao.DAOTablaUsuarios;
import dao.DAOTablaZonas;
import vos.Cliente;
import vos.ClienteFrecuente;
import vos.ConsumoCliente;
import vos.EstadisticasPedidos;
import vos.Ingrediente;

import vos.IngredientesSimilares;
import vos.Menu;
import vos.Orden;
import vos.Pedido;
import vos.PedidoDeMenu;
import vos.Producto;
import vos.RegistroVentas;
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
	 * Metodo constructor de la clase RotondAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto RotondAndesMaster, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexion a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
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

	public Producto darProducto(Long id, Long idRest) throws SQLException, Exception {
		Producto res;
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
	 * Método que obtiene un Menú según su ID y el ID del Restaurante dueño.
	 * @param id Long, ID del producto.
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
				Producto entrada = res.getAcompaniamiento();
				entrada.setIngredientes(daoIng.darIngredientesProducto(entrada.getId()));
				res.setEntrada(entrada);
			}
			if(res.getPlatoFuerte() != null)
			{
				Producto platoFuerte = res.getAcompaniamiento();
				platoFuerte.setIngredientes(daoIng.darIngredientesProducto(platoFuerte.getId()));
				res.setPlatoFuerte(platoFuerte);
			}
			if(res.getPostre() != null)
			{
				Producto postre = res.getAcompaniamiento();
				postre.setIngredientes(daoIng.darIngredientesProducto(postre.getId()));
				res.setPostre(postre);
			}
			if(res.getBebida() != null)
			{
				Producto bebida = res.getAcompaniamiento();
				bebida.setIngredientes(daoIng.darIngredientesProducto(bebida.getId()));
				res.setBebida(bebida);
			}
			if(res.getAcompaniamiento() != null)
			{
				Producto acompaniamiento = res.getAcompaniamiento();
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
	 * @param password String, Contraseña del cliente.
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
//	 * MÃ©todo que emite una Orden con un sÃ³lo Pedido.
//	 * @param id Long, ID del cliente que hace el pedido.
//	 * @param idProd Long, ID del producto que se pide.
//	 * @param idRestProd Long, ID del restaurante dueÃ±o del producto que se pide.
//	 * @return Orden, Orden con toda la informaciÃ³n del Pedido.
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
	 * @param orden Orden, Información de la Orden a crear.
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
	 * @return Pedido, toda la información del Pedido.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Pedido registrarPedido(Long id, Long idProd, Long idRestProd, Long idOrden) throws SQLException, Exception {
		Pedido res = null;
		DAOTablaPedidos dao = new DAOTablaPedidos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			daoUsuarios.setConn(conn);
//			conn.setAutoCommit(false);
			if(!dao.getEstatusOrden(idOrden))
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
			Producto producto = darProducto(idProd, idRestProd);
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
	 * Método que Registra el Pedido de un Menú.
	 * @param id Long, ID del Cliente.
	 * @param idPedido Long, ID del pedido.
	 * @param idMenu Long, ID del Menú a registar.
	 * @param idRestMenu Long, ID del Restaurante dueño del menú.
	 * @param idOrden Long, ID de la Orden a la cual se va a asignar el Pedido.
	 * @return PedidoDeMenu
	 * @throws SQLException
	 * @throws Exception
	 */
	public PedidoDeMenu registrarPedidoMenu(Long idCliente, Long idMenu, Long idRestMenu, Long idOrden) throws SQLException, Exception {
		PedidoDeMenu res = null;
		DAOTablaPedidos dao = new DAOTablaPedidos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			daoUsuarios.setConn(conn);
//			conn.setAutoCommit(false);
			if(!dao.getEstatusOrden(idOrden))
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
	 * @param idCliente Long, ID del cliente dueño de la Orden.
	 * @return Boolean, Booleano que determina si la transacción fue exitosa o no.
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
				throw new Exception("El Cliente con ID: " + idCliente + " no es dueño de la orden con ID: " + idOrden);
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

	public List<Producto> darProductos() throws SQLException, Exception {
		List<Producto> productos; 
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

	public List<Producto> darProductosPor(Integer filtro, String parametro)  throws SQLException, Exception {
		List<Producto> productos; 
		DAOTablaProductos dao = new DAOTablaProductos();
		DAOTablaIngredientes daoIng = new DAOTablaIngredientes();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			productos = dao.darProductosPor(filtro, parametro);

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
	 * MÃ©todo para agregar una Zona a RotondAndes
	 * @param zona Zona, toda la informaciÃ³n de la zona a agregar.
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
			//AGREGACIÃ“N DE RESTAURANTES A ZONA

			daoRes.setConn(conn);
			List<Restaurante> restaurantes = daoRes.darRestaurantesDeZona(id);

			//AGREGACIÃ“N DE PRODUCTOS A RESTAURANTES
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
	 * MÃ©todo que obtiene las estadÃ­sticas de los Pedidos de un Restaurante.
	 * @param id Long, ID del Representante del Restaurante cuyas estadÃ­sticas se van a pedir.
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
//	 * MÃ©todo que obtiene las estadÃ­sticas de todos los pedidos del restaurante asociado al Representante, cuyos datos entran por parÃ¡metro.
//	 * SÃ³lo el Representante tiene autorizaciÃ³n para ver los datos de su Restaurante.
//	 * @param idRepresentante Long, ID del representante.
//	 * @param password String, contraseÃ±a del Representante.
//	 * @return List<EstadisticasPedidos> lista con las estadÃ­sticas del restaurante.
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
	 * MÃ©todo que obtiene las estadÃ­sticas de todos los pedidos del restaurante asociado al Representante, cuyos datos entran por parÃ¡metro.
	 * SÃ³lo el Representante tiene autorizaciÃ³n para ver los datos de su Restaurante.
	 * @param idRepresentante Long, ID del restaurante.
	 * @return List<EstadisticasPedidos> lista con las estadÃ­sticas del restaurante.
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
	 * MÃ©todo que cancela un Pedido ordenado. El Pedido debe no estar servido para que sea vÃ¡lido.
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
	 * MÃ©todo para agregar un nuevo producto sin sus equivalencias.
	 * @param idRestaurante Long, ID del restaurante dueÃ±o de este producto.
	 * @param producto 
	 * @throws SQLException
	 * @throws Exception
	 */
	public Producto agregarProducto(Long idRestaurante, Producto producto) throws SQLException, Exception
	{
		Producto respuesta;
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		try {
			this.conn = darConexion();
			conn.setAutoCommit(false);
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
	 * MÃ©todo que registra que dos Productos son equivalentes entre sÃ­.
	 * @param idRestaurante Long, ID del Restaurante dueÃ±o de los dos Productos.
	 * @param idProducto1 Long, ID del Producto 1 a relacionar.
	 * @param idProducto2 Long, ID del producto 2 a relacionar.
	 * @throws SQLException
	 * @throws Exception
	 */
	public void registrarProductosEquivalentes(Long idRestaurante, Long idProducto1, Long idProducto2, Long idRepresentante, String password)throws SQLException, Exception
	{
		DAOTablaProductos daoProductos = new DAOTablaProductos();
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			daoProductos.setConn(conn);
			daoUsuarios.setConn(conn);
			conn.setAutoCommit(false);
			if(!daoUsuarios.verficarUsuarioRepresentante(idRepresentante, password, idRestaurante))
			{
				throw new Exception("Error en la verificación del Representante");
			}
			Producto producto1 = daoProductos.darProducto(idProducto1, idRestaurante);
			Producto producto2 = daoProductos.darProducto(idProducto2, idRestaurante);
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
				throw new Exception("Los productos son incompatibles para ser equivalentes, categoría incorrecta.");
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
	 * MÃ©todo que agrega un nuevo Ingrediente a RotondAndes. Los ingredientes se comparten entre los restaurantes.
	 * @param ingrediente Ingrediente, toda la informaciÃ³n respecto al Ingrediente.
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
	 * MÃ©todo que obtiene toda la informaciÃ³n de consulta de todos los clientes.
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
	 * MÃ©todo que obtiene el consumo de un cliente especÃ­fico.
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
	 * MÃ©todo que agregar un MenÃº para un Restaurante.
	 * Al menos uno de los parÃ¡metros debe ser no nulo y el restaurante debe ofrecer los productos
	 * cuyos IDs estÃ¡ introduciendo por parÃ¡metro.
	 * @param idRestaurante Long, ID del Restaurante.
	 * @param idEntrada Long, ID de la Entrada.
	 * @param idPlatoFuerte Long, ID del Plato Fuerte.
	 * @param idPostre Long, ID del Postre.
	 * @param idBebida Long, ID de la Bebida.
	 * @param idAcompaniamiento Long, ID del AcompaÃ±amiento.
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
			Producto entrada;
			Producto platoFuerte;
			Producto postre;
			Producto bebida;
			Producto acompaniamiento;
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
					throw new Exception("El Restaurante con ID: " + idRestaurante + " no sirve el acompañamiento con ID: " + menu.getAcompaniamiento().getId());
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
	 * MÃ©todo que regitra un Restaurante en la base de datos.
	 * @param restaurante Restaurante, informaciÃ³n del Restaurante.
	 * @param representante Representante, informaciÃ³n del Representante.
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
	 * MÃ©todo que agrega un Cliente Frecuente.
	 * @param cliente ClienteFrecuente, informaciÃ³n del cliente a agregar.
	 * @return ClienteFrecuente, informaciÃ³n del cliente agregado.
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
	 * @param cliente Cliente, información del cliente a agregar.
	 * @return ClienteFrecuente, informaciÃ³n del cliente agregado.
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
	 * @param password String, contraseña del Administrador.
	 * @return Boolean, Booleano que determina si se agregó exitosamente el Administrador o no.
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
	public List<Producto> darProductoMasOfrecido() throws SQLException, Exception
	{
		List<Producto> productos;
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
	public List<Producto> darProductoMasVendido() throws SQLException, Exception
	{
		List<Producto> productos = new ArrayList<Producto>();
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
	 * Método que obtiene la información de un Restaurante.
	 * @param idRestaurante Long, ID del Restaurante
	 * @return Restaurante, Toda la información de un Restaurante.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Restaurante darRestaurante(Long idRestaurante, Boolean deseaProductos) throws SQLException, Exception
	{
		Restaurante respuesta = null;
		DAOTablaRestaurantes dao = new DAOTablaRestaurantes();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			
			respuesta = dao.obtenerRestaurante(idRestaurante);
			if(deseaProductos)
			{
				Integer integer = 1;
				String idRest = idRestaurante + "";
				respuesta.setProductos(darProductosPor(integer, idRest));
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
		 * @param idUsuario el idUsuario para autorización
		 * @param contraseniaa la contraseña del usuario para autorización
		 * @return Clientes
		 * @throws Exception 
		 */

		public List<Cliente> getClientesConMin1ConsumoEnRangoFechasEnRestaurante(Long idRestaurante, Date fecha1, Date fecha2, String orderBy, String groupBy, Long idUsuario, String contrasenia) throws Exception
		{
			DAOTablaClientes daoCli = new DAOTablaClientes(); 
			return daoCli.getClientesConMin1ConsumoEnRangoFechasEnRestaurante(idRestaurante, fecha1, fecha2, orderBy, groupBy, idUsuario, contrasenia);
		}

		//---------------------------------------------------	
		//	Requerimiento: RFC10
		//---------------------------------------------------

		/**
		 *Métoodo que llama al RFC9 que retorna los clientes que al menos hayan pedido un producto de un restaurante dado con un rango de fechas dado. 
		 * RFC10 Consultar consumo en Rotondandes
		 * @param idRestaurante id del restaurante determinado
		 * @param fecha1 fecha inicial
		 * @param fecha2 fecha final
		 * @param orderBy como decea el usuario organizar los resultados
		 * @param groupBy como decea el usuario agrupar los resultados
		 * @param idUsuario el idUsuario para autorización
		 * @param contraseniaa la contraseña del usuario para autorización
		 * @return Clientes
		 * @throws Exception 
		 */

		public List<Cliente> getClientesConNOMin1ConsumoEnRangoFechasEnRestaurante(Long idRestaurante, Date fecha1, Date fecha2, String orderBy, String groupBy, Long idUsuario, String contrasenia) throws Exception
		{
			DAOTablaClientes daoCli = new DAOTablaClientes(); 
			return daoCli.getClientesConNOMin1ConsumoEnRangoFechasEnRestaurante(idRestaurante, fecha1, fecha2, orderBy, groupBy, idUsuario, contrasenia);
		}
}
