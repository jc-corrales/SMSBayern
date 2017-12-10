package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import vos.Cliente;
import vos.EstadisticasPedidos;
import vos.Menu;
import vos.Orden;
import vos.Pedido;
import vos.PedidoConexion;
import vos.PedidoDeMenu;
import vos.Producto;
import vos.ProductoLocal;
import vos.Restaurante;
import vos.TipoComida;

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
	 * Método que obtiene el siguiente ID en la tabla Pedidos.
	 * @return Long ID siguiente disponible.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Long darIdPedidosMax() throws SQLException, Exception {
		String sql = "SELECT * FROM PEDIDOS WHERE ROWNUM = 1 ORDER BY ID DESC";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
			return rs.getLong("ID") + 1;
		return 0L;
	}
	/**
	 * Método que obtiene el siguiente ID en la tabla Menus_Pedidos.
	 * @return Long, ID siguiente disponible.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Long darIdPedidosMenuMax() throws SQLException, Exception {
		String sql = "SELECT * FROM MENUS_PEDIDOS WHERE ROWNUM = 1 ORDER BY ID DESC";

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
	public Pedido registrarPedido(ProductoLocal producto, Long idOrden, Long idRest) throws SQLException, Exception{
//		System.out.println("Entro metodo registrarPedido");
		Long id =  darIdPedidosMax();	
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate = LocalDateTime.now();
		Date fecha = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
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
			restauranteTemp = new Restaurante(restaurante.getLong("ID_REST"), null, null, null, null, null, null);
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
		String sql = "INSERT INTO PEDIDOS (ID, ID_PRODUCTO, FECHA, SERVIDO, ID_ORDEN, ID_RESTAURANTE) VALUES (";
		sql += id + ", ";
		sql += producto.getId() + ", ";
		sql += "TIMESTAMP '" + dtf.format(localDate) + "', 0, ";
		sql += idOrden + ", ";
		sql += restauranteTemp.getId() + ")";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		System.out.println("post ejecucción sql 2");
		
		return new Pedido(id, producto, fecha, false, idRest);
	}
	/**
	 * Método que registra el Pedido de un Menú
	 * @param id Long, ID del Pedido de Menú a registrar.
	 * @param menu Menu, Información del Menú a registrar.
	 * @param idOrden Long, ID de la Orden bajo la cual va a estar asignado este Pedido.
	 * @return PedidoDeMenu, Información del Pedido de Menu efectuado.
	 * @throws SQLException
	 * @throws Exception
	 */
	public PedidoDeMenu registrarPedidoMenu(Menu menu, Long idOrden, Long idRestaurante) throws SQLException, Exception
	{
		String sql = "INSERT INTO MENUS_PEDIDOS (ID, IDMENU, IDORDEN) VALUES (" + darIdPedidosMenuMax() + ", " + menu.getId() + ", " + idOrden + ")";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		System.out.println("SQL: " +sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		PedidoDeMenu pedido = new PedidoDeMenu(idOrden, menu);
		if(menu.getAcompaniamiento() != null)
		{
			registrarPedido(menu.getAcompaniamiento(), idOrden, idRestaurante);
		}
		if(menu.getEntrada() != null)
		{
			registrarPedido(menu.getEntrada(), idOrden, idRestaurante);
		}
		if(menu.getPlatoFuerte() != null)
		{
			registrarPedido(menu.getPlatoFuerte(), idOrden, idRestaurante);
		}
		if(menu.getPostre() != null)
		{
			registrarPedido(menu.getPostre(), idOrden, idRestaurante);
		}
		if(menu.getBebida() != null)
		{
			registrarPedido(menu.getBebida(), idOrden, idRestaurante);
		}
		return pedido;
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
		
		Long idOrden =rs.getLong("ID_ORDEN");
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
		System.out.println("PRE SQL");
		String sqlProductoPrice = "SELECT PRECIO FROM PRODUCTO_RESTAURANTE WHERE ID_PROD = " + rs.getLong("ID_PRODUCTO") + " AND ID_REST = " + rs.getLong("ID_RESTAURANTE");
		PreparedStatement prepStmtProductoPrice= conn.prepareStatement(sqlProductoPrice);
		recursos.add(prepStmtProductoPrice);
		ResultSet rsPrice = prepStmtProductoPrice.executeQuery();
		System.out.println("POST SQL");
		rsPrice.next();
		Double valor = -rsPrice.getDouble("PRECIO");
		updateCostoTotalOrden(idOrden, valor);
		System.out.println("POST UPDATE");
		conn.setAutoCommit(true);
	}
//	/**
//	 * Método que obtiene el ID de la siguiente Orden.
//	 * @return
//	 * @throws SQLException
//	 * @throws Exception
//	 */
//	public Long getSiguienteIdOrden()throws SQLException, Exception
//	{
//		String sql = "SELECT * FROM ORDENES\r\n" + 
//				"    WHERE ROWNUM = 1\r\n" + 
//				"    ORDER BY ID DESC";
//		PreparedStatement prepStmt= conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//		if(rs.next())
//		{
//			return rs.getLong("ID") + 1;
//		}
//		else
//		{
//			return (long) 1;
//		}
//	}
	
//	public Orden registrarUnPedido(Cliente cliente, Producto producto, Long idRestProd, Long idOrden)throws SQLException, Exception
//	{
//		List<Pedido> pedidos = new ArrayList<Pedido>();
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		LocalDateTime localDate = LocalDateTime.now();
//		Orden orden = new Orden(idOrden, producto.getPrecio(), null, cliente);
//		
//		String sql = "INSERT INTO ORDENES (ID, ID_CLIENTE, COSTOTOTAL, FECHA)\r\n" + 
//				" VALUES (" + orden.getId() + ", " + orden.getCliente().getId() + ", " + orden.getCostoTotal() + ", TIMESTAMP '" + dtf.format(localDate) + "')";
//		PreparedStatement prepStmt= conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
//		
//		Pedido pedido = registrarPedido(cliente, producto, orden.getId(), idRestProd);
//		pedidos.add(pedido);
//		orden.setPedidosOrdenados(pedidos);
//		return orden;
//	}
	/**
	 * Método que indica si a una orden se le pueden agregar más Pedidos o no.
	 * Si la orden no ha sido confirmada, se pueden agregar Pedidos
	 * Si la orden ha sido confirmada, no se pueden agregar pedidos.
	 * @param idOrden Long, ID de la Orden.
	 * @return Boolean, Booleano que determina si la orden puede recibir pedidos o no.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean getEstatusOrden(Long idOrden) throws SQLException, Exception
	{
		Boolean respuesta = false;
		String sql = "SELECT * FROM ORDENES WHERE ID = " + idOrden;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
		{
			if(rs.getBoolean("ES_CONFIRMADA"))
			{
				respuesta = true;
			}
		}
		else
		{
			throw new Exception("La Orden con ID: " + idOrden + " no existe");
		}
		return respuesta;
	}
	
	/**
	 * Método que Registra una nueva Orden vacía en el sistema.
	 * @param orden Orden, información de la Orden a agregar.
	 * @return Orden, Orden agregada.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Orden registrarNuevaOrden(Orden orden) throws SQLException, Exception
	{
		System.out.println("Entro a metodo registrar orden");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate = LocalDateTime.now();		
		String sql = "INSERT INTO ORDENES (ID, ID_CLIENTE, COSTOTOTAL, FECHA, ES_CONFIRMADA) VALUES (" + orden.getId() + ", " + orden.getCliente().getId() +", " + 0 + ", TIMESTAMP '" + dtf.format(localDate) + "', " + 0 + ")";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		System.out.println("SQL: " +sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		orden.setEsConfirmada(false);
		orden.setPedidosOrdenados(new ArrayList<Pedido>());
		return orden;
	}
	/**
	 * Método que entrega toda la información de una Orden según su ID.
	 * @param idOrden Long, ID de la Orden a obtener.
	 * @return Orden, toda la información de la Orden con sus Pedidos respectivos.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Orden obtenerOrden(Long idOrden)throws SQLException, Exception
	{
		String sql = "SELECT * FROM ORDENES WHERE ID = " + idOrden;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(!rs.next())
		{
			throw new Exception("No existe la Orden con ID: " + idOrden);
		}
		Long id = rs.getLong("ID");
		
		Cliente cliente = new Cliente();
		cliente.setId(rs.getLong("ID_CLIENTE"));
		Boolean esConfirmada = rs.getBoolean("ES_CONFIRMADA");
		Double costoTotal = rs.getDouble("COSTOTOTAL");
		Orden orden = new Orden(id, costoTotal, obtenerProductosPedidosDeOrden(idOrden), cliente, esConfirmada, obtenerMenusPedidosDeOrden(idOrden));
		return orden;
	}
	
	/**
	 * Método que devuelve toda la lista de Pedidos para una Orden.
	 * @param idOrden Long, ID de la Orden cuyos pedidos se quieren obtener.
	 * @return List<Pedido>, Lista de Pedidos de una Orden.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Pedido> obtenerProductosPedidosDeOrden(Long idOrden)throws SQLException, Exception
	{
		List<Pedido> respuesta = new ArrayList<Pedido>();
		String sql = "SELECT * FROM PEDIDOS WHERE ID_ORDEN = " + idOrden;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while(rs.next())
		{
			Long id = rs.getLong("ID");
			Date fecha = rs.getDate("FECHA");
			ProductoLocal producto = new ProductoLocal();
			producto.setId(rs.getLong("ID_PRODUCTO"));
			Boolean servido = rs.getBoolean("SERVIDO");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			Pedido pedido = new Pedido(id, producto, fecha, servido, idRestaurante);
			respuesta.add(pedido);
		}
		return respuesta;
	}
	
	/**
	 * Método que obtiene los Menús Pedidos en una Orden dado el ID de la ORden.
	 * @param idOrden Long, ID de la Orden.
	 * @return List<PedidoDeMenu>, Lista con los menús Pedidos en esta Orden.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<PedidoDeMenu> obtenerMenusPedidosDeOrden(Long idOrden)throws SQLException, Exception
	{
		List<PedidoDeMenu> respuesta = new ArrayList<PedidoDeMenu>();
		String sql = "SELECT * \r\n" + 
				"    FROM MENUS, MENUS_PEDIDOS\r\n" + 
				"    WHERE MENUS.ID = Menus_Pedidos.Idmenu AND MENUS_PEDIDOS.IDORDEN = " + idOrden;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while(rs.next())
		{
			Long id = rs.getLong("ID");
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			String name = rs.getString("NAME");
			String descripcion = rs.getString("Descripcion");
			String description = rs.getString("Description");
			Double precio = rs.getDouble("PRECIO");
			ProductoLocal entrada = darProducto(rs.getLong("ID_ENTRADA"), idRestaurante);
			ProductoLocal platoFuerte = darProducto(rs.getLong("ID_PLATOFUERTE"), idRestaurante);
			ProductoLocal postre = darProducto(rs.getLong("ID_POSTRE"), idRestaurante);
			ProductoLocal bebida = darProducto(rs.getLong("ID_BEBIDA"), idRestaurante);
			ProductoLocal acompaniamiento = darProducto(rs.getLong("ID_ACOMPANIAMIENTO"), idRestaurante);
			Double costoProduccion = (entrada.getCostoDeProduccion() + platoFuerte.getCostoDeProduccion() + postre.getCostoDeProduccion() + bebida.getCostoDeProduccion() + acompaniamiento.getCostoDeProduccion());
			Menu menu = new Menu(idRestaurante, name, costoProduccion, descripcion, description, precio, entrada, platoFuerte, bebida, postre, acompaniamiento);
			PedidoDeMenu pedido = new PedidoDeMenu(id, menu);
			respuesta.add(pedido);
		}
		return respuesta;
	}
	
	
	/**
	 * Método que confirma una Orden, haciendo que ya no se le puedan agregar más pedidos. Aunque si se pueden cancelar.
	 * @param idOrden Long, ID de la Orden a confirmar.
	 * @return Boolean, Booleano que indica si el procedimiento fue exitoso o no.	
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean confirmarOrden(Long idOrden) throws SQLException, Exception{
		String sql = "UPDATE ORDENES SET ES_CONFIRMADA = 1 WHERE ID = " + idOrden;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		return true;
	}
	
	/**
	 * Método para actualizar el costo de una Orden según los cambios que se presenten.
	 * Nuevo Pedido.
	 * Cancelación de Pedido.
	 * @param idOrden Long, ID de la Orden.
	 * @param valor Double, valor a Modificar la orden, puede ser negativo.
	 * @throws SQLException
	 * @throws Exception
	 */
	public void updateCostoTotalOrden(Long idOrden, Double valor)throws SQLException, Exception
	{		
		String sql2 = "UPDATE ORDENES SET COSTOTOTAL = COSTOTOTAL + " + valor + " WHERE ID = " + idOrden;
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();
	}
	
	public ProductoLocal darProducto(Long id, Long idRest) throws SQLException, Exception {
		ProductoLocal producto = new ProductoLocal();

		String sqlProductoPorId = "SELECT * FROM PRODUCTOS, PRODUCTO_RESTAURANTE WHERE ID_PROD = ID AND ID_PROD = " + id + " AND ID_REST =" + idRest; 
		PreparedStatement stProductoPorId = conn.prepareStatement(sqlProductoPorId);
		recursos.add(stProductoPorId);
		ResultSet rs = stProductoPorId.executeQuery();
		
		String sqlTipos= "SELECT * FROM TIPOS TIPOS, TIPOPRODUCTO RELACION\r\n" + 
				"    WHERE Relacion.Id_Prod = " + id + " AND RELACION.ID_TIPO = TIPOS.ID"; 
		PreparedStatement stTipos = conn.prepareStatement(sqlTipos);
		recursos.add(stTipos);
		ResultSet rsTipos = stTipos.executeQuery();
		List<TipoComida> tipos = new ArrayList<TipoComida>();
		while(rsTipos.next())
		{
			tipos.add(new TipoComida(rsTipos.getLong("ID"), rsTipos.getString("NAME")));
		}
		
		if (rs.next()) {
			producto.setId(rs.getLong("ID"));
			producto.setNombre(rs.getString("NAME"));
			producto.setDescripcionEspaniol(rs.getString("DESCRIPCION"));
			producto.setDescripcionIngles(rs.getString("DESCRIPTION"));
			producto.setCategoria(rs.getString("CATEGORIA"));
			producto.setCostoDeProduccion(rs.getDouble("COSTO_PRODUCCION"));
			producto.setPrecio(rs.getDouble("PRECIO"));
			producto.setProductosEquivalentes(null);
			producto.setCantidad(rs.getInt("CANTIDAD"));
			producto.setTiposComida(tipos);
			return producto;
		}
		return null;
	}
	
	
	/**
	 * Método que confirma una Orden Externa, haciendo que ya no se le puedan agregar más pedidos. Aunque si se pueden cancelar.
	 * @param idOrden Long, ID de la Orden Externa a confirmar.
	 * @return Boolean, Booleano que indica si el procedimiento fue exitoso o no.	
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean confirmarOrdenExterna(Long idOrden) throws SQLException, Exception{
		String sql = "UPDATE ORDENESEXTERNAS SET ES_CONFIRMADA = 1 WHERE ID = " + idOrden;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		return true;
	}
	/**
	 * Método que registra un nuevo pedido de un cliente externo.
	 * @param idCliente Long, ID del Cliente.
	 * @param origen Integer, Base de datos de Origen.
	 * @param producto Long, ID del Producto a registrar.
	 * @param idOrden Long, ID de la Orden Externa bajo la cual se asigna el Pedido.
	 * @param idRestaurante Long, ID del Restaurante al cual se realiza el Pedido.
	 * @return Pedido, nuevo Pedido creado.
	 * @throws SQLException
	 * @throws Exception
	 */
	public PedidoConexion registrarNuevoPedidoExterno(Long idCliente, Integer origen, ProductoLocal producto, Long idRestaurante)throws SQLException, Exception
	{
		System.out.println("Entro metodo registrarPedido");
		Long id =  darIdPedidosMax();	
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate = LocalDateTime.now();
		Date fecha = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
//		System.out.println("pre sql 1");
		String sqlRestaurante = "SELECT * FROM PRODUCTO_RESTAURANTE WHERE ID_PROD = " + producto.getId() + " AND ID_REST = " + idRestaurante;
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
			restauranteTemp = new Restaurante(restaurante.getLong("ID_REST"), null, null, null, null, null, null);
		}
		if(restaurante.getInt("CANTIDAD") <= 0)
		{
			throw new Exception("No Hay Cantidad Suficiente.");
		}
		
		String sql2 = "UPDATE PRODUCTO_RESTAURANTE SET CANTIDAD = (CANTIDAD - 1) WHERE ID_PROD = " + producto.getId() + " AND ID_REST = " + idRestaurante;
//		System.out.println(sqlRestaurante);
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
//		System.out.println(sqlRestaurante);
		recursos.add(prepStmt2);
//		System.out.println(sqlRestaurante);
		prepStmt2.executeQuery();
		Long idOrden = darSiguienteIdOrdenExterna();
		String sqlOrden = "INSERT INTO ORDENESEXTERNAS(ID, ID_CLIENTE, COSTOTOTAL, FECHA, ES_CONFIRMADA, ORIGEN) VALUES( " + idOrden + ", " + idCliente + ", " + 0 + ", " + "TIMESTAMP '" + dtf.format(localDate) + "', 1, " + origen;
		PreparedStatement prepStmtOrden = conn.prepareStatement(sqlOrden);
		recursos.add(prepStmtOrden);
		prepStmtOrden.executeQuery();
		

		System.out.println("Pre SQL");
		String sql = "INSERT INTO PEDIDOSEXTERNOS (ID, ID_PRODUCTO, FECHA, SERVIDO, ID_ORDEN, ID_RESTAURANTE) VALUES (";
		sql += id + ", ";
		sql += producto.getId() + ", ";
		sql += "TIMESTAMP '" + dtf.format(localDate) + "', 0, ";
		sql += idOrden + ", ";
		sql += restauranteTemp.getId() + ")";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		System.out.println("post ejecucción sql 2");
		
		return (new Pedido(id, producto, fecha, false, idRestaurante).toPedidoConexion(null, idCliente, null, idOrden, null, origen));
	}
	/**
	 * Método que obtiene el siguiente ID de las órdenes Externas.
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Long darSiguienteIdOrdenExterna()throws SQLException, Exception
	{
		String sql = "SELECT *\r\n" + 
				"    FROM ORDENESEXTERNAS\r\n" + 
				"    WHERE ROWNUM = 1\r\n" + 
				"    ORDER BY ID DESC";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		Long respuesta = (long) 0;
		if(rs.next())
		{
			respuesta = (rs.getLong("ID") + 1);
		}
		return respuesta;
	}
}