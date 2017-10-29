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
import vos.Orden;
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
		String sql = "SELECT * FROM PEDIDOS WHERE ROWNUM = 1 ORDER BY ID DESC";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
			return rs.getLong("ID") + 1;
		return 0L;
	}

	/**
	 * Método que registra un Pedido
	 * @param cliente Cliente, Cliente a nombre de quién está el Pedido.
	 * @param producto Producto, Producto Pedido.
	 * @param idOrden Long, ID de la orden a la que este producto está asociado.
	 * @param idRest Long, ID del Restaurante.
	 * @return Pedido, Pedido con toda su información.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Pedido registrarPedido(Cliente cliente, Producto producto, Long idOrden, Long idRest) throws SQLException, Exception{
//		System.out.println("Entro metodo registrarPedido");
		Long id =  darIdMax();	
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate = LocalDateTime.now();
//		System.out.println("pre sql 1");
		String sqlRestaurante = "SELECT * FROM PRODUCTO_RESTAURANTE WHERE ID_PROD = " + producto.getId() + " AND ID_REST = " + idRest;
//		System.out.println(sqlRestaurante);
		PreparedStatement prepStmtRestaurante = conn.prepareStatement(sqlRestaurante);
//		System.out.println(sqlRestaurante);
		recursos.add(prepStmtRestaurante);
//		System.out.println(sqlRestaurante);
		ResultSet restaurante = prepStmtRestaurante.executeQuery();
//		System.out.println("post sql 1");
		Restaurante restauranteTemp = null;
		if(restaurante.next())
		{
			restauranteTemp = new Restaurante(restaurante.getLong("ID_REST"), null, null, null, null, null);
		}
		if(restaurante.getInt("CANTIDAD") <= 0)
		{
			throw new Exception("No Hay Cantidad Suficiente.");
		}
		
		String sql2 = "UPDATE PRODUCTO_RESTAURANTE SET CANTIDAD = (CANTIDAD - 1) WHERE ID_PROD = " + producto.getId() + " AND ID_REST = " + idRest;
//		System.out.println(sqlRestaurante);
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
//		System.out.println(sqlRestaurante);
		recursos.add(prepStmt2);
//		System.out.println(sqlRestaurante);
		prepStmt2.executeQuery();
		

		System.out.println("Pre SQL");
		String sql = "INSERT INTO PEDIDOS (ID, ID_CLIENTE, ID_PRODUCTO, FECHA, SERVIDO, ID_ORDEN, ID_RESTAURANTE) VALUES (";
		sql += id + ", ";
		sql += cliente.getId() + ", ";
		sql += producto.getId() + ", ";
		sql += "TIMESTAMP '" + dtf.format(localDate) + "', 0, ";
		sql += idOrden + ", ";
		sql += restauranteTemp.getId() + ")";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		System.out.println("post ejecucción sql 2");
		
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
	
	/**
	 * Método que obtiene una lista de pedidos.
	 * @param idRestaurante ID del Restaurante cuyas estadísticas de Pedidos se van a pedir.
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<EstadisticasPedidos> darEstadisticasPedidosPorRestaurante(Long idRestaurante)throws SQLException, Exception
	{
		System.out.println("ENTRO A METODO DAO");
		List<EstadisticasPedidos> respuesta = new ArrayList<EstadisticasPedidos>();
		
//		String sqlRepresentante = "";		
//		PreparedStatement prepStmtRepresentante = conn.prepareStatement(sqlRepresentante);
//		recursos.add(prepStmtRepresentante);
//		ResultSet datosRepresentante = prepStmtRepresentante.executeQuery();
//		
//		Long idRestaurante =
		
		String sql = "SELECT REST.NAME AS RESTAURANTE, PROD.NAME AS PRODUCTO, PEDIDO.SERVIDO AS SERVIDO, COUNT(PEDIDO.ID) AS ORDENADOS, SUM(PRODUCTO.PRECIO) AS GANANCIAS\r\n" + 
				"    FROM PEDIDOS PEDIDO, PRODUCTO_RESTAURANTE PRODUCTO, RESTAURANTES REST, PRODUCTOS PROD\r\n" + 
				"    WHERE Producto.Id_Prod = Pedido.Id_Producto AND Producto.Id_Rest = Pedido.Id_Restaurante AND REST.ID = PEDIDO.ID_RESTAURANTE AND PROD.ID = PEDIDO.ID_PRODUCTO AND REST.ID = "+ idRestaurante + "\r\n" + 
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
	
	/**
	 * Método que cancela un Pedido. El Pedido debe no haber sido servido para que la transacción sea válida.
	 * @param id Long, ID del Pedido.
	 * @throws SQLException
	 * @throws Exception
	 */
	public void cancelarPedido(Long id)throws SQLException, Exception
	{
		
		String sql = "SELECT *\r\n" + 
				"    FROM PEDIDOS\r\n" + 
				"    WHERE ID = " + id;
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		
		conn.setAutoCommit(false);
//		System.out.println();
//		System.out.println("ID:" + rs.getLong("ID"));
		if(!rs.next())
		{
			throw new Exception("No existe el Pedido con ID: " + id);
		}
		if(rs.getInt("SERVIDO") == 1)
		{
			throw new Exception("No se puede cancelar el Pedido con ID: " + id + " debido a que ya ha sido entregado.");
		}		
		
		String sqlEliminador = "DELETE \r\n" + 
				"    FROM PEDIDOS \r\n" + 
				"    WHERE ID = " + id;
		PreparedStatement prepStmtDeleter = conn.prepareStatement(sqlEliminador);
		recursos.add(prepStmtDeleter);
		prepStmtDeleter.executeQuery();
		
		conn.setSavepoint();
		String sqlUpdater = "UPDATE PRODUCTO_RESTAURANTE\r\n" + 
				"    SET CANTIDAD = (CANTIDAD + 1)\r\n" + 
				"    WHERE ID_PROD = " + rs.getLong("ID_PRODUCTO") + " AND ID_REST = " + rs.getLong("ID_RESTAURANTE");
		PreparedStatement prepStmtUpdater= conn.prepareStatement(sqlUpdater);
		recursos.add(prepStmtUpdater);
		prepStmtUpdater.executeQuery();
		conn.setAutoCommit(true);
	}
	/**
	 * Método que obtiene el ID de la siguiente Orden.
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Long getSiguienteIdOrden()throws SQLException, Exception
	{
		String sql = "SELECT * FROM ORDENES\r\n" + 
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
	
	public Orden registrarUnPedido(Cliente cliente, Producto producto, Long idRestProd)throws SQLException, Exception
	{
		List<Pedido> pedidos = new ArrayList<Pedido>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate = LocalDateTime.now();
		Orden orden = new Orden(getSiguienteIdOrden(), producto.getPrecio(), null, cliente);
		
		String sql = "INSERT INTO ORDENES (ID, ID_CLIENTE, COSTOTOTAL, FECHA)\r\n" + 
				" VALUES (" + orden.getId() + ", " + orden.getCliente().getId() + ", " + orden.getCostoTotal() + ", TIMESTAMP '" + dtf.format(localDate) + "')";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		Pedido pedido = registrarPedido(cliente, producto, orden.getId(), idRestProd);
		pedidos.add(pedido);
		orden.setPedidosOrdenados(pedidos);
		return orden;
	}
	
}