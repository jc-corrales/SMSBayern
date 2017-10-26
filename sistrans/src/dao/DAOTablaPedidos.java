package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import vos.Cliente;
import vos.Pedido;
import vos.Producto;

public class DAOTablaPedidos {
	private ArrayList<Object> recursos;
	private Connection conn;


	public DAOTablaPedidos() {
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


	public Long darIdMax() throws SQLException, Exception {
		String sql = "SELECT COUNT(*) AS CONT FROM PEDIDOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
			return rs.getLong("CONT") + 1;
		return 0L;
	}

	
	
	

	public Pedido registrarPedido(Cliente cliente, Producto producto) throws SQLException, Exception{
		Long id =  darIdMax();	
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate = LocalDateTime.now();
		String sql = "INSERT INTO PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO, ID_ORDEN, ID_RESTAURANTE) VALUES (";
		sql += id + ", ";
		sql += cliente.getId() + ", ";
		sql += producto.getId() + ", ";
		sql += "TIMESTAMP '" + dtf.format(localDate) + "', 0, ";
		sql += cliente.getOrdenes().get(cliente.getOrdenes().size()-1).getId() + ", ";
		sql += producto.getRestaurante().getId() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		return new Pedido(id, cliente, producto, localDate, false);
	}


	public void despacharPedido(Long idPed) throws SQLException, Exception{
		String sql = "UPDATE PEDIDOS SET SERVIDO = 1 WHERE ID = " + idPed;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
	}
}