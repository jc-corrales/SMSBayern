package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ClienteFrecuente;
/**
 * Clase que Administra los Clientes Frecuentes.
 * @author ASUS
 *
 */
public class DAOTablaClientesFrecuentes {
	
	private ArrayList<Object> recursos;
	private Connection conn;
	/**
	 * Método Constructor de la Clase.
	 */
	public DAOTablaClientesFrecuentes() {
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
	 * Método que Borra una preferencia de un Cliente Frecuente.
	 * @param idCliente Long, ID del Cliente Frecuente cuya preferencia se va a agregar.
	 * @param idProd Long, ID del Producto a agregar en las Preferencias del Cliente.
	 * @throws SQLException
	 * @throws Exception
	 */
	public void borrarPreferencia(Long idCliente, Long idProd) throws SQLException, Exception {
		String sql = "DELETE FROM PREFERENCIAS WHERE ID_CLIENTEFRECUENTE = ";
		sql += idCliente + "AND ID_PRODUCTO = " + idProd;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	/**
	 * Método que registra la preferencia de un Cliente.
	 * @param idCliente Long, ID del cliente.
	 * @param idProd Long, ID del Producto a registrar.
	 * @throws SQLException
	 * @throws Exception
	 */
	public void registrarPreferencia(Long idCliente, Long idProd) throws SQLException, Exception {
		String sql = "INSERT INTO PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) VALUES (";
		sql += idCliente + ", ";
		sql += idProd + ")";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que verifica si las credenciales del Cliente Frecuente son válidas.
	 * @param id Long, ID del Cliente Frecuente a verificar.
	 * @param password String, Contraseña del Cliente Frecuente a verificar.
	 * @return Boolean, Booleano que determina si las credenciales son válidas o no.
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean verficarCliente(Long id, String password) throws SQLException, Exception {
		String sql = "SELECT * FROM CLIENTESFRECUENTES WHERE ID = " + id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next()) {
			String pass = rs.getString("PASSWORD");
			if(pass.equals(password))
				return true;
		}
		return false;
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
		String sql = "SELECT * FROM CLIENTES, CLIENTESFRECUENTES";  
		sql += " WHERE CLIENTES.ID = CLIENTESFRECUENTES.ID ";
		sql += " AND CLIENTESFRECUENTES.ID = " + id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(rs.next()) {
			
			cliente = new ClienteFrecuente();
			cliente.setId(rs.getLong("ID"));
			cliente.setNombre(rs.getString("NAME"));
			cliente.setMesa(rs.getInt("MESA"));
			cliente.setContrasenia(rs.getString("PASSWORD"));
			
		}
		return cliente;
	}
	/**
	 * Método que agrega un Cliente Frecuente a la Base de Datos.
	 * @param cliente ClienteFrecuente, Toda la información del cliente frecuente a agregar.
	 * @return ClienteFrecuente, Información del Cliente Frecuente agregado.
	 * @throws SQLException
	 * @throws Exception
	 */
	public ClienteFrecuente agregarClienteFrecuente(ClienteFrecuente cliente)throws SQLException, Exception
	{
		Long idCliente = getSiguienteIdCliente();
		String sql1 = "INSERT INTO CLIENTES (ID, NAME, MESA)\r\n" + 
				"    VALUES (" + idCliente + ", '" + cliente.getNombre() + "', " + cliente.getMesa() + ")";
		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);
		prepStmt1.executeQuery();
		
		String sql2 = "INSERT INTO CLIENTESFRECUENTES (ID, PASSWORD )\r\n" + 
				"    VALUES (" + idCliente + ", '" + cliente.getContrasenia() + "')";
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();
		cliente.setId(idCliente);
		return cliente;
	}
	
	/**
	 * Método que obtiene el ID del sigueinte Cliente.
	 * @return Long, siguiente ID de Clientes en la base de datos.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Long getSiguienteIdCliente()throws SQLException, Exception
	{
		String sql = "SELECT * FROM CLIENTES\r\n" + 
				"    WHERE ROWNUM = 1\r\n" + 
				"    ORDER BY ID DESC";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
		{
			return rs.getLong("ID") + 1;
		}
		else
		{
			return (long) 1;
		}
	}
}
