package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Cliente;
import vos.ClienteFrecuente;
import vos.Orden;
import vos.ProductoBase;
import vos.Representante;

public class DAOTablaUsuarios
{
		
	public final static String CLIENTE = "CLIENTE";
	public final static String CLIENTEFRECUENTE = "CLIENTEFRECUENTE";
	public final static String REPRESENTANTE = "REPRESENTANTE";
	public final static String ADMIN = "ADMIN";
	private ArrayList<Object> recursos;
	private Connection conn;
	/**
	 * Método Constructor de la Clase.
	 */
	public DAOTablaUsuarios() {
		recursos = new ArrayList<Object>();
	}
	
	/**
	 * Método que cierra los recursos.
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}
	/**
	 * Método que establece la conexión.
	 * @param conn Connection.
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	/**
	 * Método que registra un Cliente no Frecuente en RotondAndes.
	 * @param id Long, ID del cliente.
	 * @param nombre String, nombre del Cliente.
	 * @param idMesa Long, ID de la Mesa asignada.
	 * @return Cliente, cliente que se acaba de registrar en el sistema.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Cliente registrarCliente(Cliente cliente) throws SQLException, Exception
	{
		String nombre = cliente.getNombre();
		Long id = cliente.getId();
		Long idMesa = cliente.getMesa();
		if(nombre == null ||nombre.equals(""))
		{
			throw new Exception("El nombre suministrado es incorrecto.");
		}
		String sql = "insert into Usuarios (ID, PASSWORD, ROL)\r\n" + 
				"    values (" + id + ", null, '" + CLIENTE + "')";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		String sql2 = "insert into CLIENTES (ID, NAME, IDMESA, ESCLIENTEFRECUENTE)\r\n" + 
				"    values (" + id + ", '" + cliente.getNombre() + "', " + idMesa + ", 0)";
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();
		
		return cliente;
	}
	
	/**
	 * Método que registra un Cliente Frecuente en RotondAndes.
	 * @param id Long, ID del cliente Frecuente.
	 * @param nombre String, nombre del Cliente Frecuente.
	 * @param idMesa Long, ID de la Mesa asignada.
	 * @return ClienteFrecuente, Cliente Frecuente que se acaba de registrar en el sistema.
	 * @throws SQLException
	 * @throws Exception
	 */
	public ClienteFrecuente registrarClienteFrecuente(ClienteFrecuente cliente) throws SQLException, Exception
	{
		List<Orden> ordenes = new ArrayList<Orden>();
		List<ProductoBase> preferencias = new ArrayList<ProductoBase>();
		ClienteFrecuente respuesta = new ClienteFrecuente(preferencias, cliente.getContrasenia(), cliente.getId(), cliente.getMesa(), cliente.getNombre(), ordenes);
		String nombre = cliente.getNombre();
		if(nombre == null ||nombre.equals(""))
		{
			throw new Exception("El nombre suministrado es incorrecto.");
		}
		String sql = "insert into Usuarios (ID, PASSWORD, ROL)\r\n" + 
				"    values (" + cliente.getId() + ", '" + cliente.getContrasenia() + "', '" + CLIENTE + "')";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		String sql2 = "insert into CLIENTES (ID, NAME, IDMESA, ESCLIENTEFRECUENTE)\r\n" + 
				"    values (" + cliente.getId() + ", '" + cliente.getNombre() + "', " + cliente.getMesa() + ", 1)";
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();
		
		return respuesta;
	}
	
	/**
	 * Método que registra un Administrador en RotondAndes.
	 * @param id Long, ID del Administrador
	 * @param password String, contraseña del Administrador.
	 * @return Boolean, booleano que indica si el Administrador fue registrado exitosamente o no.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean registrarAdministrador(Long id, String password) throws SQLException, Exception
	{
		String sql = "insert into Usuarios (ID, PASSWORD, ROL)\r\n" + 
				"    values (" + id + ", '" + password + "', '" + ADMIN + "')";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		return true;
	}
	
	/**
	 * Método que registra un Representante en RotondAndes.
	 * @param id Long, ID del Representante.
	 * @return Representante, representante del Restaurante recientemente agregado
	 * @throws SQLException
	 * @throws Exception
	 */
	public Representante registrarRepresentante(Representante representante) throws SQLException, Exception
	{
		String sql = "insert into Usuarios (ID, PASSWORD, ROL)\r\n" + 
				"    values (" +  representante.getId() + ", '" + representante.getContrasenia() + "', '" + REPRESENTANTE + "')";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		String sql2 = "insert into Representantes (ID, NAME, ID_RESTAURANTE)\r\n" + 
				"    values (" +  representante.getId() + ", '" +  representante.getName() + "', " +  representante.getRestaurante().getId() + ")";
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();
		
		return representante;
	}
	
	/**
	 * Método que verifica que las credenciales del cliente sean válidas.
	 * @param id Long, ID del cliente.
	 * @return Boolean, booleano que indica si el usuario cliente es válido o no.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean verficarUsuarioCliente(Long id) throws SQLException, Exception {
		String sql = "SELECT * FROM USUARIOS WHERE ID = " + id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		Boolean respuesta = false;
		if(!rs.next())
		{
			throw new Exception("El Cliente no existe.");
		}
		if(!rs.getString("ROL").equals(CLIENTE) && !rs.getString("ROL").equals(CLIENTEFRECUENTE))
		{
			throw new Exception("El usuario no es un Cliente.");
		}
		else
		{
			respuesta = true;
		}
		return respuesta;
	}
	/**
	 * Método que verifica que las credenciales del Cliente Frecuente sean correctas
	 * @param id Long, ID del Cliente Frecuente.
	 * @param password String, contraseña del Cliente Frecuente.
	 * @return Boolean, booleano que indica si el usuario Cliente es válido o no.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean verficarUsuarioClienteFrecuente(Long id, String password) throws SQLException, Exception {
		String sql = "SELECT * FROM USUARIOS WHERE ID = " + id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		Boolean respuesta = false;
		if(rs.next()) {
			if(!rs.getString("ROL").equals(CLIENTEFRECUENTE))
			{
				throw new Exception("El usuario no es un Cliente Frecuente.");
			}
			String pass = rs.getString("PASSWORD");
			System.out.println("ConstraseñaRESOURCE: " +password);
			System.out.println("ConstraseñaORACLE: " +pass);
			if(pass.equals(password))
				respuesta = true;
				return respuesta;
		}
		return respuesta;
	}
	
	/**
	 * Método que verifica que las credenciales del Representante sean correctas
	 * @param id Long, ID del Representante.
	 * @param password String, contraseña del Cliente Frecuente.
	 * @return Boolean, booleano que indica si el usuario Cliente Frecuente es válido o no.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean verficarUsuarioRepresentante(Long id, String password, Long idRestaurante) throws SQLException, Exception {
		String sql = "SELECT * FROM USUARIOS NATURAL JOIN REPRESENTANTES WHERE ID = " + id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		Boolean respuesta = false;
		
		if(rs.next()) {
			if(!rs.getString("ROL").equals(REPRESENTANTE))
			{
				throw new Exception("El usuario no es un Representante.");
			}
			String pass = rs.getString("PASSWORD");	
			System.out.println("ConstraseñaRESOURCE: " +password);
			System.out.println("ConstraseñaORACLE: " +pass);
			Long idRestauranteBaseDeDatos = rs.getLong("ID_RESTAURANTE");
			if(!idRestauranteBaseDeDatos.equals(idRestaurante))
			{
				throw new Exception("El Representante no representa al Restaurante con ID: " + idRestaurante);
			}
			if(pass.equals(password))
				respuesta = true;
				return respuesta;
		}
		return respuesta;
	}
	
	/**
	 * Método que verifica que las credenciales del Administrador sean correctas
	 * @param id Long, ID del Administrador.
	 * @param password String, contraseña del Administrador.
	 * @return Boolean, booleano que indica si el usuario Administrador es válido o no.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean verficarUsuarioAdministrador	(Long id, String password) throws SQLException, Exception {
		String sql = "SELECT * FROM USUARIOS WHERE ID = " + id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		Boolean respuesta = false;
		
		if(rs.next()) {
			if(!rs.getString("ROL").equals(ADMIN))
			{
				throw new Exception("El usuario no es un Administrador.");
			}
			String pass = rs.getString("PASSWORD");
			System.out.println("ConstraseñaRESOURCE: " +password);
			System.out.println("ConstraseñaORACLE: " +pass);
			if(pass.equals(password))
				respuesta = true;
				return respuesta;
		}
		return respuesta;
	}
	
	
	/**
	 * Método que obtiene un Cliente Frecuente.
	 * @param id Long, ID del cliente a buscar.
	 * @return ClienteFrecuente, Cliente Frecuente de RotondAndes.
	 * @throws SQLException
	 * @throws Exception
	 */
	public ClienteFrecuente darClienteFrecuente(Long id) throws SQLException, Exception{
		ClienteFrecuente cliente = null;
		String sql = "SELECT * FROM USUARIOS, CLIENTES";  
		sql += " WHERE USUARIOS.ID = CLIENTES.ID AND USUARIOS.ROL = 'CLIENTEFRECUENTE' AND CLIENTES.ESCLIENTEFRECUENTE = 1";
		sql += " AND CLIENTES.ID = " + id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(rs.next()) {
			
			cliente = new ClienteFrecuente();
			cliente.setId(rs.getLong("ID"));
			cliente.setNombre(rs.getString("NAME"));
			cliente.setMesa(rs.getLong("IDMESA"));
			cliente.setContrasenia(rs.getString("PASSWORD"));
			
		}
		return cliente;
	}
}
