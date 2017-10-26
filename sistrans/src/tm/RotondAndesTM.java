/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
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
import java.util.List;
import java.util.Properties;
import dao.DAOTablaClientes;
import dao.DAOTablaClientesFrecuentes;
import dao.DAOTablaIngredientes;
import dao.DAOTablaPedidos;
import dao.DAOTablaProductos;
import dao.DAOTablaRestaurantes;
import dao.DAOTablaZonas;
import vos.Cliente;
import vos.ClienteFrecuente;
import vos.Pedido;
import vos.Producto;
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



	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////

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

	public Cliente darCliente(Long id) throws SQLException {
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

	public ClienteFrecuente agregarPreferenciaClienteFrecuente(Long id, String password, Long idProd) throws SQLException, Exception {
		ClienteFrecuente cliente = null;
		DAOTablaClientesFrecuentes dao = new DAOTablaClientesFrecuentes();
		DAOTablaProductos daoPref = new DAOTablaProductos();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			
			//Verificar Cliente
			if(!dao.verficarCliente(id, password)) 
				throw new Exception("Clave invalida");
			//fin Verificacion		
			
			dao.registrarPreferencia(id, idProd);
			
			//INICIO AGREGAR PREFERENCIAS A ENIDAD CLIENTEFRECUENTE
			cliente = dao.darClienteFrecuente(id);
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
				dao.cerrarRecursos();
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

	public Pedido agregarPedido(Long id, Long idProd, Long idRestProd) throws SQLException, Exception {
		Pedido res = null;
		DAOTablaPedidos dao = new DAOTablaPedidos();
		try {
			this.conn = darConexion();
			dao.setConn(conn);
			Cliente cliente = darCliente(id);
			Producto producto = darProducto(idProd, idRestProd);
			res = dao.registrarPedido(cliente, producto);
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
		return res;

	}

	public void despacharPedido(Long idPed) throws SQLException, Exception {
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

	public List<Producto> darProductosPor(Integer filtro, Object parametro)  throws SQLException, Exception {
		List<Producto> productos; 
		DAOTablaProductos dao = new DAOTablaProductos();

		try {
			this.conn = darConexion();
			dao.setConn(conn);
			productos = dao.darProductosPor(filtro, parametro);
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
	
	public Zona agregarZona(Long id, String nombre, Boolean esEspacioAbierto, Integer capacidad, Boolean esIncluyente, List<String> condiciones, List<String> restaurantes)throws SQLException, Exception
	{
		Zona nuevaZona = new Zona(id, nombre, esEspacioAbierto, capacidad, esIncluyente, condiciones, null);
		DAOTablaZonas dao = new DAOTablaZonas();
		try {
			this.conn = darConexion();
			dao.setConn(conn);	
			
			dao.addZona(nuevaZona);
			
			//INICIO AGREGAR PREFERENCIAS A ENIDAD CLIENTEFRECUENTE
			
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
		return nuevaZona;
	}
	
	
	public List<Zona> darZonasSinParametro() throws SQLException, Exception {
		List<Zona> zonas; 
		DAOTablaZonas dao = new DAOTablaZonas();

		try {
			this.conn = darConexion();
			dao.setConn(conn);
			zonas = dao.getZonasSinParametros();
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
		return zonas; 
	}
}
