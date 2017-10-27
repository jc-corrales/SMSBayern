package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import vos.Cliente;
import vos.EstadisticasPedidos;
import vos.Pedido;
import vos.Producto;
import vos.Restaurante;

public class DAOTablaPedidos {
	/**
	 * Atributo que contienen la lista de los Recursos.
	 */
	private ArrayList<Object> recursos;
	/**
	 * Atributo que contiene los datos de la conexión.
	 */
	private Connection conn;

	/**
	 * Método constructor de la Clase DAOTablaPedidos.
	 */
	public DAOTablaPedidos() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra los recursos de este Dao.
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
	 * @param conn
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 
	 * @return Long
	 * @throws SQLException
	 * @throws Exception
	 */
	public Long darIdMax() throws SQLException, Exception {
		String sql = "SELECT COUNT(*) AS CONT FROM PEDIDOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
			return rs.getLong("CONT") + 1;
		return 0L;
	}

	/**
	 * Método que registra un Pedido
	 * @param cliente Cliente, Cliente a nombre de quién está el Pedido.
	 * @param producto Producto, Producto Pedido.
	 * @return Pedido, Pedido con toda su información.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Pedido registrarPedido(Cliente cliente, Producto producto) throws SQLException, Exception{
		Long id =  darIdMax();	
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate = LocalDateTime.now();
		
		String sqlRestaurante = "SELECT * FROM PRODUCTO_RESTAURANTE WHERE ID_PROD = " + producto.getId();
		PreparedStatement prepStmtRestaurante = conn.prepareStatement(sqlRestaurante);
		recursos.add(prepStmtRestaurante);
		ResultSet restaurante = prepStmtRestaurante.executeQuery();
		
		Restaurante restauranteTemp = new Restaurante(restaurante.getLong("ID"), null, null, null, null);
		
		String sql = "INSERT INTO PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO, ID_ORDEN, ID_RESTAURANTE) VALUES (";
		sql += id + ", ";
		sql += cliente.getId() + ", ";
		sql += producto.getId() + ", ";
		sql += "TIMESTAMP '" + dtf.format(localDate) + "', 0, ";
		sql += cliente.getOrdenes().get(cliente.getOrdenes().size()-1).getId() + ", ";
		sql += restauranteTemp.getId() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		return new Pedido(id, cliente, producto, localDate, false);
	}

	/**
	 * Método que despacha un pedido.
	 * @param idPed ID del Pedido a despachar.
	 * @throws SQLException
	 * @throws Exception
	 */
	public void despacharPedido(Long idPed) throws SQLException, Exception{
		String sql = "UPDATE PEDIDOS SET SERVIDO = 1 WHERE ID = " + idPed;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Método que obtiene una lista de pedidos.
	 * @return List<Pedido>
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<EstadisticasPedidos> darEstadisticasPedidos()throws SQLException, Exception
	{
		System.out.println("ENTRO A METODO DAO");
		List<EstadisticasPedidos> respuesta = new ArrayList<EstadisticasPedidos>();
		
		String sql = "SELECT REST.NAME AS RESTAURANTE, PROD.NAME AS PRODUCTO, PEDIDO.SERVIDO AS SERVIDO, COUNT(PEDIDO.ID) AS ORDENADOS, SUM(PRODUCTO.PRECIO) AS GANANCIAS\r\n" + 
				"    FROM PEDIDOS PEDIDO, PRODUCTO_RESTAURANTE PRODUCTO, RESTAURANTES REST, PRODUCTOS PROD\r\n" + 
				"    WHERE Producto.Id_Prod = Pedido.Id_Producto AND Producto.Id_Rest = Pedido.Id_Restaurante AND REST.ID = PEDIDO.ID_RESTAURANTE AND PROD.ID = PEDIDO.ID_PRODUCTO\r\n" + 
				"    GROUP BY REST.NAME, PROD.NAME, PEDIDO.SERVIDO\r\n" + 
				"    ORDER BY RESTAURANTE";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet datosPedidos = prepStmt.executeQuery();
		while(datosPedidos.next())
		{
			String restaurante = datosPedidos.getString("RESTAURANTE");
			String producto = datosPedidos.getString("PRODUCTO");
			Boolean servido = true;
			if(datosPedidos.getInt("SERVIDO") == 0)
			{
				servido = false;
			}
			Integer numOrdenados = datosPedidos.getInt("ORDENADOS");
			Double ganancias = datosPedidos.getDouble("GANANCIAS");
			
			EstadisticasPedidos temp = new EstadisticasPedidos(restaurante, producto, servido, numOrdenados, ganancias);
			respuesta.add(temp);
		}
		
		return respuesta;
	}
}