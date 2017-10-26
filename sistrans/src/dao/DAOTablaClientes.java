package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Cliente;
import vos.Producto;

public class DAOTablaClientes {

	private ArrayList<Object> recursos;

	private Connection conn;

	public DAOTablaClientes() {
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


	public List<Cliente> darClientes() throws SQLException, Exception {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		String sentencia = "SELECT * FROM CLIENTES";
		PreparedStatement stamnt = conn.prepareStatement(sentencia);
		recursos.add(stamnt);
		ResultSet rs = stamnt.executeQuery();

		while(rs.next()) {
			String name = rs.getString("NAME");
			Long id = rs.getLong("ID");
			Integer mesa = rs.getInt("MESA"); 
			
			clientes.add(new Cliente(id, mesa, name));
		}
		return clientes;
	}


	public  Cliente getClienteQueMasHaPedido() throws SQLException {
		Cliente cliente = null;

		String sent = "SELECT * FROM CLIENTES WHERE ID IN (SELECT ID_CLIENTE FROM (SELECT ID_CLIENTE, MAX(COUNT(ID_PRODUCTO)) FROM CLIENTES LEFT OUTER JOIN PEDIDOS ON ID = ID_CLIENTE GROUP BY ID_PRODUCTO))";
		PreparedStatement st = conn.prepareStatement(sent);
		recursos.add(st);
		ResultSet rs = st.executeQuery();	
		
		if(rs.next()) {
			Long id2 = rs.getLong("ID");
			String nameclientePorId = rs.getString("NAME");
			Integer mesaClientePorId = rs.getInt("MESA");
			cliente = new Cliente(id2, mesaClientePorId, nameclientePorId);
		}
		return cliente;
	}



	public Cliente darCliente(Long id) throws SQLException {
		Cliente clientePorId = null;

		String sqlClientePorId = "SELECT * FROM CLIENTES WHERE ID =" + id; 
		PreparedStatement stClientePorId = conn.prepareStatement(sqlClientePorId);
		recursos.add(stClientePorId);
		ResultSet rsClientePorId = stClientePorId.executeQuery();

		if (rsClientePorId.next()) {
			Long id2 = rsClientePorId.getLong("ID");
			String nameclientePorId = rsClientePorId.getString("NAME");
			Integer mesaClientePorId = rsClientePorId.getInt("MESA");
			clientePorId = new Cliente(id2, mesaClientePorId, nameclientePorId);
		}

		return clientePorId;
	}

}
