package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.ClienteFrecuente;
import vos.ProductoBase;

public class DAOTablaClientesFrecuentes {
	
	private ArrayList<Object> recursos;
	private Connection conn;
	
	

	public DAOTablaClientesFrecuentes() {
		recursos = new ArrayList<Object>();
	}


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

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public void borrarPreferencia(Long idCliente, Long idProd) throws SQLException, Exception {
		String sql = "DELETE FROM PREFERENCIAS WHERE ID_CLIENTEFRECUENTE = ";
		sql += idCliente + "AND ID_PRODUCTO = " + idProd;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void registrarPreferencia(Long idCliente, Long idProd) throws SQLException, Exception {
		String sql = "INSERT INTO PREFERENCIAS (ID_CLIENTEFRECUENTE, ID_PRODUCTO) VALUES (";
		sql += idCliente + ", ";
		sql += idProd + ")";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}


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
}
