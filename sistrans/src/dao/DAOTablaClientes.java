package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vos.Cliente;
import vos.Menu;
import vos.Orden;
import vos.Pedido;
import vos.PedidoDeMenu;
import vos.ProductoLocal;
import vos.TipoComida;

public class DAOTablaClientes {

	
	public final static Integer NINGUNO = 0;
	public final static Integer CATEGORIA = 1;
	public final static Integer PRODUCTO = 2;
	
	private ArrayList<Object> recursos;

	private Connection conn;

	private DAOTablaUsuarios tablaUsuarios;

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

		String sentencia = "SELECT * FROM CLIENTES\r\n" + 
				"    NATURAL JOIN USUARIOS";
		PreparedStatement stamnt = conn.prepareStatement(sentencia);
		recursos.add(stamnt);
		ResultSet rs = stamnt.executeQuery();

		while(rs.next()) {
			String name = rs.getString("NAME");
			Long id = rs.getLong("ID");
			Long mesa = rs.getLong("IDMESA"); 
			List<Orden> ordenes = new ArrayList<Orden>();
			String sentenciaOrden = "SELECT * FROM ORDENES WHERE ID_CLIENTE = " + id;
			PreparedStatement stamntOrden = conn.prepareStatement(sentenciaOrden);
			recursos.add(stamntOrden);
			ResultSet rsOrden = stamntOrden.executeQuery();
			while(rsOrden.next())
			{
				Long idOrden = rsOrden.getLong("ID");
				Double costoTotalOrden = rsOrden.getDouble("COSTOTOTAL");
				Boolean esConfirmada = rsOrden.getBoolean("ES_CONFIRMADA");
				List<Pedido> pedidos = getPedidosPorClienteSegunOrden(idOrden);
				List<PedidoDeMenu> menusPedidos = obtenerMenusPedidosDeOrden(idOrden);
				ordenes.add( new Orden(idOrden, costoTotalOrden, pedidos ,null, esConfirmada, menusPedidos));
			}

			clientes.add(new Cliente(id, mesa, name, ordenes));
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
			Long mesaClientePorId = rs.getLong("MESA");

			List<Orden> ordenes = new ArrayList<Orden>();
			String sentenciaOrden = "SELECT * FROM ORDENES WHERE ID_CLIENTE = " + id2;
			PreparedStatement stamntOrden = conn.prepareStatement(sentenciaOrden);
			recursos.add(stamntOrden);
			ResultSet rsOrden = stamntOrden.executeQuery();
			while(rsOrden.next())
			{
				Long idOrden = rsOrden.getLong("ID");
				Double costoTotalOrden = rsOrden.getDouble("COSTOTOTAL");
				Boolean esConfirmada = rsOrden.getBoolean("ES_CONFIRMADA");
				ordenes.add( new Orden(idOrden, costoTotalOrden, null ,null, esConfirmada, null));
			}


			cliente = new Cliente(id2, mesaClientePorId, nameclientePorId, ordenes);
		}
		return cliente;
	}
	/**
	 * Método que da un Cliente.
	 * @param id Long, ID del cliente a buscar.
	 * @return Cliente, información básica del cliente, sin órdenes.
	 * @throws SQLException
	 */
	public Cliente darCliente(Long id) throws SQLException, Exception {
		Cliente clientePorId = null;

		String sqlClientePorId = "SELECT * FROM CLIENTES\r\n" + 
				"    NATURAL JOIN USUARIOS\r\n" + 
				"    WHERE ID = " + id; 
		PreparedStatement stClientePorId = conn.prepareStatement(sqlClientePorId);
		recursos.add(stClientePorId);
		ResultSet rsClientePorId = stClientePorId.executeQuery();

		if (rsClientePorId.next()) {
			Long id2 = rsClientePorId.getLong("ID");
			String nameclientePorId = rsClientePorId.getString("NAME");
			Long mesaClientePorId = rsClientePorId.getLong("IDMESA");
			clientePorId = new Cliente(id2, mesaClientePorId, nameclientePorId, null);
		}
		List<Orden> ordenes = new ArrayList<Orden>();
		String sentenciaOrden = "SELECT * FROM ORDENES WHERE ID_CLIENTE = " + id;
		PreparedStatement stamntOrden = conn.prepareStatement(sentenciaOrden);
		recursos.add(stamntOrden);
		ResultSet rsOrden = stamntOrden.executeQuery();
		while(rsOrden.next())
		{
			Long idOrden = rsOrden.getLong("ID");
			Double costoTotalOrden = rsOrden.getDouble("COSTOTOTAL");
			List<Pedido> pedidos = getPedidosPorClienteSegunOrden(idOrden);
			Boolean esConfirmada = rsOrden.getBoolean("ES_CONFIRMADA");
			List<PedidoDeMenu> menusPedidos = obtenerMenusPedidosDeOrden(idOrden);
			ordenes.add( new Orden(idOrden, costoTotalOrden, pedidos ,null, esConfirmada, menusPedidos));
		}
		clientePorId.setOrdenes(ordenes);
		return clientePorId;
	}
	
	/**
	 * Método que da un Cliente sin sus órdenes.
	 * @param id Long, ID del cliente a buscar.
	 * @return Cliente, información básica del cliente, sin órdenes.
	 * @throws SQLException
	 */
	public Cliente darClienteSinOrdenes(Long id) throws SQLException, Exception {
		Cliente clientePorId = null;

		String sqlClientePorId = "SELECT * FROM CLIENTES\r\n" + 
				"    NATURAL JOIN USUARIOS\r\n" + 
				"    WHERE ID = " + id; 
		PreparedStatement stClientePorId = conn.prepareStatement(sqlClientePorId);
		recursos.add(stClientePorId);
		ResultSet rsClientePorId = stClientePorId.executeQuery();

		if (rsClientePorId.next()) {
			Long id2 = rsClientePorId.getLong("ID");
			String nameclientePorId = rsClientePorId.getString("NAME");
			Long mesaClientePorId = rsClientePorId.getLong("IDMESA");
			clientePorId = new Cliente(id2, mesaClientePorId, nameclientePorId, null);
		}
		return clientePorId;
	}
	/**
	 * Método que obtiene los Pedidos hechos por un cliente, por orden.
	 * @param idCliente
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	private List<Pedido> getPedidosPorClienteSegunOrden(Long idOrden)throws SQLException, Exception
	{
		String sql = "SELECT * FROM PEDIDOS WHERE ID_ORDEN = " + idOrden; 
		PreparedStatement stmt= conn.prepareStatement(sql);
		recursos.add(stmt);
		ResultSet rs = stmt.executeQuery();
		List<Pedido> respuesta = new ArrayList<Pedido>();
		while(rs.next())
		{
			Long id = rs.getLong("ID");
			Date fecha = rs.getDate("FECHA");
			Long idProducto = rs.getLong("ID_PRODUCTO");
			Boolean servido = true;
			Long idRestaurante = rs.getLong("ID_RESTAURANTE");
			if(rs.getInt("SERVIDO") == 0)
			{
				servido = false;
			}
			//PROCESAR PRODUCTO DE ORDEN
			String sqlProducto = "SELECT * FROM PRODUCTO_RESTAURANTE PRODREST, PRODUCTOS\r\n" + 
					"    WHERE PRODUCTOS.ID = Prodrest.Id_Prod AND PRODUCTOS.ID = " + idProducto;
			PreparedStatement stmtProd= conn.prepareStatement(sqlProducto);
			recursos.add(stmtProd);
			ResultSet rsProd = stmtProd.executeQuery();
			ProductoLocal producto = new ProductoLocal();
			if(rsProd.next())
			{
				String nombre = rsProd.getString("NAME");
				String descripcionEspaniol = rsProd.getString("DESCRIPCION");
				String descripcionIngles = rsProd.getString("DESCRIPTION");
				Double costoDeProduccion = rsProd.getDouble("COSTO_PRODUCCION");
				Double precio = rsProd.getDouble("PRECIO");

				String categoria = rsProd.getString("CATEGORIA");

				producto = new ProductoLocal(idProducto, nombre, descripcionEspaniol, descripcionIngles, costoDeProduccion, null, precio, null, categoria, null, null);
			}
			Pedido pedido = new Pedido(id, producto, null, servido, idRestaurante);
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
	private List<PedidoDeMenu> obtenerMenusPedidosDeOrden(Long idOrden)throws SQLException, Exception
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

	private ProductoLocal darProducto(Long id, Long idRest) throws SQLException, Exception {
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

//	public List<Cliente> getClientesConMin1ConsumoEnRangoFechasEnRestaurante(Long idRestaurante, Date fecha1, Date fecha2, String orderBy, String groupBy, Long idUsuario, String contrasenia) throws Exception
//	{
//		List<Cliente> resp = new ArrayList<Cliente>();
//		if(idUsuario == idRestaurante || !tablaUsuarios.verficarUsuarioAdministrador(idUsuario, contrasenia))
//		{
//			resp = null;
//		}
//		else
//		{
//			String sql = "SELECT CLIENTES.ID, CLIENTES.NAME, CLIENTES.IDMESA FROM (SELECT * FROM CLIENTES, (SELECT * FROM PEDIDOS, ORDENES WHERE PEDIDOS.ID_ORDEN = ORDENES.ID) WHERE CLIENTES.ID = ORDENES.ID_CLIENTE), (SELECT * FROM PRODUCTO_RESTAURANTE, (SELECT * FROM RESTAURANTES WHERE RESTAURANTES.ID =" + idRestaurante +  ") WHERE RESTAURANTES.ID = PRODUCTO_RESTAURANTE.ID_REST) WHERE PEDIDOS.ID_PRODUCTO = PRODUCTO_RESTAURANTE.ID_PROD AND ORDENES.FECHA BETWEEN" + fecha1 +"AND" +fecha2+ " ORDER BY"+orderBy+" GROUP BY"+groupBy+";";
//			PreparedStatement stmt= conn.prepareStatement(sql);
//			recursos.add(stmt);
//			ResultSet rs = stmt.executeQuery();
//			List<Cliente> resp2 = new ArrayList<Cliente>();
//			while(rs.next())
//			{
//				Long id = rs.getLong("ID");
//				String nombre = rs.getString("NAME");
//				Long idMesa = rs.getLong("IDMESA");
//
//				resp.add(new Cliente(id, idMesa,nombre, null));
//				resp = resp2;
//			}
//		}
//		return resp;
//	}
	
	/**
	 * Método que obtiene una lista de clientes que han pedido al menos una vez
	 * un producto de un Restaurante específico.
	 * @param idRestaurante Long, ID Restaurante.
	 * @param fecha1 String, Fecha, formato: DD/MM/AA
	 * @param fecha2 String, Fecha, formato: DD/MM/AA
	 * @param criterio Integer, número que indica el criterio de búsqueda
	 * @param orderBy String, Criterio de Orden.
	 * @return List<Cliente>, Lista de Clientes que entran en el rango de búsqueda
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Cliente> getClientesConMinUnConsumoEnRangoFechasPorRestaurante(Long idRestaurante, String fecha1, String fecha2, Integer criterio, String orderBy)throws SQLException, Exception
	{
		List<Cliente> clientes = new ArrayList<Cliente>();
		String sql = "";
		if(criterio == null)
		{
			criterio = 0;
		}
		if(criterio.equals(CATEGORIA))
		{
			sql = "SELECT ID_CLIENTE, CATEGORIA, COUNT(PEDIDOS.ID) AS NUMPEDIDOS\r\n" + 
					"    FROM ORDENES, PEDIDOS\r\n" + 
					"    INNER JOIN PRODUCTOS ON PEDIDOS.ID_PRODUCTO = PRODUCTOS.ID\r\n" + 
					"    WHERE ORDENES.ID = PEDIDOS.ID_ORDEN AND PEDIDOS.ID_RESTAURANTE = " + idRestaurante + " AND PEDIDOS.FECHA >= '"+ fecha1 +" 01:00:00, 000000000 AM' AND  PEDIDOS.FECHA <='" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"    GROUP BY CATEGORIA, ID_CLIENTE";
			if(orderBy != null && (orderBy.equals("ID_CLIENTE") || orderBy.equals("NUMPEDIDOS")|| orderBy.equals("CATEGORIA")) )
			{
				sql += "\n ORDER BY " + orderBy;
			}
		}
		else if(criterio.equals(PRODUCTO))
		{
			sql = "SELECT PRODUCTOS.ID AS IDPRODUCTO, ID_CLIENTE, COUNT(PEDIDOS.ID) AS NUMPEDIDOS\r\n" + 
					"    FROM ORDENES, PEDIDOS\r\n" + 
					"    INNER JOIN PRODUCTOS ON PEDIDOS.ID_PRODUCTO = PRODUCTOS.ID\r\n" + 
					"    WHERE ORDENES.ID = PEDIDOS.ID_ORDEN AND PEDIDOS.ID_RESTAURANTE = " + idRestaurante + " AND PEDIDOS.FECHA >= '"+ fecha1 +" 01:00:00, 000000000 AM' AND  PEDIDOS.FECHA <='" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"    GROUP BY PRODUCTOS.ID, ID_CLIENTE";
			if(orderBy != null && (orderBy.equals("ID_CLIENTE") || orderBy.equals("NUMPEDIDOS")|| orderBy.equals("IDPRODUCTO")) )
			{
				sql += "\n ORDER BY " + orderBy;
			}
		}
		else
		{
			sql = "SELECT ID_CLIENTE, COUNT(PEDIDOS.ID) AS NUMPEDIDOS\r\n" + 
					"    FROM ORDENES, PEDIDOS\r\n" + 
					"    INNER JOIN PRODUCTOS ON PEDIDOS.ID_PRODUCTO = PRODUCTOS.ID\r\n" + 
					"    WHERE ORDENES.ID = PEDIDOS.ID_ORDEN AND PEDIDOS.ID_RESTAURANTE = " + idRestaurante + " AND PEDIDOS.FECHA >= '"+ fecha1 +" 01:00:00, 000000000 AM' AND  PEDIDOS.FECHA <='"+ fecha2 +" 12:59:59, 000000000 PM'\r\n" + 
					"    GROUP BY ID_CLIENTE";
			if(orderBy != null && (orderBy.equals("ID_CLIENTE") || orderBy.equals("NUMPEDIDOS")) )
			{
				sql += "\n ORDER BY " + orderBy;
			}
		}
		System.out.println(sql);
		PreparedStatement stmt= conn.prepareStatement(sql);
		recursos.add(stmt);
		ResultSet rs = stmt.executeQuery();
		while(rs.next())
		{
			
			Cliente cliente = new Cliente(rs.getLong("ID_CLIENTE"), null, null, null);
			clientes.add(cliente);
		}
		return clientes;
	}
	
	/**
	 * Método que obtiene una lista de un Cliente que ha pedido al menos una vez
	 * un producto de un Restaurante específico.
	 * @param idCliente Long, ID cliente.
	 * @param idRestaurante Long, ID Restaurante.
	 * @param fecha1 String, Fecha, formato: DD/MM/AA
	 * @param fecha2 String, Fecha, formato: DD/MM/AA
	 * @param criterio Integer, número que indica el criterio de búsqueda
	 * @param orderBy String, Criterio de Orden.
	 * @return List<Cliente>, Lista de Clientes que entran en el rango de búsqueda
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Cliente> getClienteConMinUnConsumoEnRangoFechasPorRestaurante(Long idCliente, Long idRestaurante, String fecha1, String fecha2, Integer criterio, String orderBy)throws SQLException, Exception
	{
		List<Cliente> clientes = new ArrayList<Cliente>();
		String sql = "";
		if(criterio == null)
		{
			criterio = 0;
		}
		if(criterio.equals(CATEGORIA))
		{
			sql = "SELECT ID_CLIENTE, CATEGORIA, COUNT(PEDIDOS.ID) AS NUMPEDIDOS\r\n" + 
					"    FROM ORDENES, PEDIDOS\r\n" + 
					"    INNER JOIN PRODUCTOS ON PEDIDOS.ID_PRODUCTO = PRODUCTOS.ID\r\n" + 
					"    WHERE ORDENES.ID = PEDIDOS.ID_ORDEN AND ORDENES.ID_CLIENTE = " + idCliente + " AND PEDIDOS.ID_RESTAURANTE = " + idRestaurante + " AND PEDIDOS.FECHA >= '"+ fecha1 +" 01:00:00, 000000000 AM' AND  PEDIDOS.FECHA <='" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"    GROUP BY CATEGORIA, ID_CLIENTE";
			if(orderBy != null && (orderBy.equals("ID_CLIENTE") || orderBy.equals("NUMPEDIDOS")|| orderBy.equals("CATEGORIA")) )
			{
				sql += "\n ORDER BY " + orderBy;
			}
					
		}
		else if(criterio.equals(PRODUCTO))
		{
			sql = "SELECT PRODUCTOS.ID AS IDPRODUCTO, ID_CLIENTE, COUNT(PEDIDOS.ID) AS NUMPEDIDOS\r\n" + 
					"    FROM ORDENES, PEDIDOS\r\n" + 
					"    INNER JOIN PRODUCTOS ON PEDIDOS.ID_PRODUCTO = PRODUCTOS.ID\r\n" + 
					"    WHERE ORDENES.ID = PEDIDOS.ID_ORDEN AND ORDENES.ID_CLIENTE = " + idCliente + " AND PEDIDOS.ID_RESTAURANTE = " + idRestaurante + " AND PEDIDOS.FECHA >= '"+ fecha1 +" 01:00:00, 000000000 AM' AND  PEDIDOS.FECHA <='" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"    GROUP BY PRODUCTOS.ID, ID_CLIENTE";
			if(orderBy != null && (orderBy.equals("ID_CLIENTE") || orderBy.equals("NUMPEDIDOS")|| orderBy.equals("IDPRODUCTO")) )
			{
				sql += "\n ORDER BY " + orderBy;
			}
		}
		else
		{
			sql = "SELECT ID_CLIENTE, COUNT(PEDIDOS.ID) AS NUMPEDIDOS\r\n" + 
					"    FROM ORDENES, PEDIDOS\r\n" + 
					"    INNER JOIN PRODUCTOS ON PEDIDOS.ID_PRODUCTO = PRODUCTOS.ID\r\n" + 
					"    WHERE ORDENES.ID = PEDIDOS.ID_ORDEN AND ORDENES.ID_CLIENTE = " + idCliente + " AND PEDIDOS.ID_RESTAURANTE = " + idRestaurante + " AND PEDIDOS.FECHA >= '"+ fecha1 +" 01:00:00, 000000000 AM' AND  PEDIDOS.FECHA <='"+ fecha2 +" 12:59:59, 000000000 PM'\r\n" + 
					"    GROUP BY ID_CLIENTE";
			if(orderBy != null && (orderBy.equals("ID_CLIENTE") || orderBy.equals("NUMPEDIDOS")) )
			{
				sql += "\n ORDER BY " + orderBy;
			}
		}
		PreparedStatement stmt= conn.prepareStatement(sql);
		recursos.add(stmt);
		ResultSet rs = stmt.executeQuery();
		while(rs.next())
		{
			
			Cliente cliente = new Cliente(rs.getLong("ID_CLIENTE"), null, null, null);
			clientes.add(cliente);
		}
		return clientes;
	}
	
	/**
	 * Método que obtiene una lista de clientes que NO han pedido al menos una vez
	 * un producto de un Restaurante específico.
	 * @param idRestaurante Long, ID Restaurante.
	 * @param fecha1 String, Fecha, formato: DD/MM/AA
	 * @param fecha2 String, Fecha, formato: DD/MM/AA
	 * @param criterio Integer, número que indica el criterio de búsqueda
	 * @param orderBy String, Criterio de Orden.
	 * @return List<Cliente>, Lista de Clientes que entran en el rango de búsqueda
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Cliente> getClientesSinMinUnConsumoEnRangoFechasEnRestaurante(Long idRestaurante, String fecha1, String fecha2, Integer criterio, String orderBy)throws SQLException, Exception
	{
		List<Cliente> clientes = new ArrayList<Cliente>();
		String sql = "";
		if(criterio == null)
		{
			criterio = 0;
		}
		if(criterio.equals(CATEGORIA))
		{
			sql = "SELECT * \r\n" + 
					"    FROM CLIENTES\r\n" + 
					"    WHERE NOT CLIENTES.ID = ANY (SELECT ID_CLIENTE\r\n" + 
					"        FROM ORDENES, PEDIDOS\r\n" + 
					"        INNER JOIN PRODUCTOS ON PEDIDOS.ID_PRODUCTO = PRODUCTOS.ID \r\n" + 
					"        WHERE ORDENES.ID = PEDIDOS.ID_ORDEN AND PEDIDOS.ID_RESTAURANTE = " + idRestaurante + " AND PEDIDOS.FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND  PEDIDOS.FECHA <='" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"        GROUP BY CATEGORIA, ID_CLIENTE";
//			if(orderBy != null && (orderBy.equals("ID_CLIENTE") || orderBy.equals("NUMPEDIDOS")|| orderBy.equals("CATEGORIA")) )
//			{
//				sql += "\n ORDER BY " + orderBy;
//			}
			sql += ")";
		}
		else if(criterio.equals(PRODUCTO))
		{
			sql = "SELECT * \r\n" + 
					"    FROM CLIENTES\r\n" + 
					"    WHERE NOT CLIENTES.ID = ANY (SELECT ID_CLIENTE\r\n" + 
					"        FROM ORDENES, PEDIDOS\r\n" + 
					"        INNER JOIN PRODUCTOS ON PEDIDOS.ID_PRODUCTO = PRODUCTOS.ID \r\n" + 
					"        WHERE ORDENES.ID = PEDIDOS.ID_ORDEN AND PEDIDOS.ID_RESTAURANTE = " + idRestaurante + " AND PEDIDOS.FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND  PEDIDOS.FECHA <='" + fecha2 + "12:59:59, 000000000 PM'\r\n" + 
					"        GROUP BY PRODUCTOS.ID, ID_CLIENTE";
//			if(orderBy != null && (orderBy.equals("ID_CLIENTE") || orderBy.equals("NUMPEDIDOS")|| orderBy.equals("IDPRODUCTO")) )
//			{
//				sql += "\n ORDER BY " + orderBy;
//			}
			sql += ")";
		}
		else
		{
			sql = "SELECT * \r\n" + 
					"    FROM CLIENTES\r\n" + 
					"    WHERE NOT CLIENTES.ID = ANY (SELECT ID_CLIENTE\r\n" + 
					"        FROM ORDENES, PEDIDOS\r\n" + 
					"        INNER JOIN PRODUCTOS ON PEDIDOS.ID_PRODUCTO = PRODUCTOS.ID \r\n" + 
					"        WHERE ORDENES.ID = PEDIDOS.ID_ORDEN AND PEDIDOS.ID_RESTAURANTE = " + idRestaurante + " AND PEDIDOS.FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND  PEDIDOS.FECHA <='" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"        GROUP BY ID_CLIENTE";
//			if(orderBy != null && (orderBy.equals("ID_CLIENTE") || orderBy.equals("NUMPEDIDOS")) )
//			{
//				sql += "\n ORDER BY " + orderBy;
//			}
			sql += ")";
		}
		System.out.println(sql);
		PreparedStatement stmt= conn.prepareStatement(sql);
		recursos.add(stmt);
		ResultSet rs = stmt.executeQuery();
		while(rs.next())
		{
			
			Cliente cliente = new Cliente(rs.getLong("ID"), null, null, null);
			clientes.add(cliente);
		}
		return clientes;
	}
	
	
	/**
	 * Método que obtiene una lista de un Cliente que NO ha pedido al menos una vez
	 * un producto de un Restaurante específico.
	 * @param idCliente Long, ID cliente.
	 * @param idRestaurante Long, ID Restaurante.
	 * @param fecha1 String, Fecha, formato: DD/MM/AA
	 * @param fecha2 String, Fecha, formato: DD/MM/AA
	 * @param criterio Integer, número que indica el criterio de búsqueda
	 * @param orderBy String, Criterio de Orden.
	 * @return List<Cliente>, Lista de Clientes que entran en el rango de búsqueda
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Cliente> getClienteSinMinUnConsumoEnRangoFechasPorRestaurante(Long idCliente, Long idRestaurante, String fecha1, String fecha2, Integer criterio, String orderBy)throws SQLException, Exception
	{
		List<Cliente> clientes = new ArrayList<Cliente>();
		String sql = "";
		if(criterio == null)
		{
			criterio = 0;
		}
		if(criterio.equals(CATEGORIA))
		{
			sql = "SELECT * \r\n" + 
					"    FROM CLIENTES\r\n" + 
					"    WHERE CLIENTES.ID = " + idCliente + " AND NOT CLIENTES.ID = ANY \r\n" + 
					"    (SELECT ID_CLIENTE\r\n" + 
					"        FROM ORDENES, PEDIDOS\r\n" + 
					"        INNER JOIN PRODUCTOS ON PEDIDOS.ID_PRODUCTO = PRODUCTOS.ID \r\n" + 
					"        WHERE ORDENES.ID = PEDIDOS.ID_ORDEN AND PEDIDOS.ID_RESTAURANTE = " + idRestaurante + " AND PEDIDOS.FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND  PEDIDOS.FECHA <='" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"        GROUP BY CATEGORIA, ID_CLIENTE";
//			if(orderBy != null && (orderBy.equals("ID_CLIENTE") || orderBy.equals("NUMPEDIDOS")|| orderBy.equals("CATEGORIA")) )
//			{
//				sql += "\n ORDER BY " + orderBy;
//			}
			sql += ")";
		}
		else if(criterio.equals(PRODUCTO))
		{
			sql = "SELECT * \r\n" + 
					"    FROM CLIENTES\r\n" + 
					"    WHERE CLIENTES.ID = " + idCliente + " AND NOT CLIENTES.ID = ANY \r\n" + 
					"    (SELECT ID_CLIENTE\r\n" + 
					"        FROM ORDENES, PEDIDOS\r\n" + 
					"        INNER JOIN PRODUCTOS ON PEDIDOS.ID_PRODUCTO = PRODUCTOS.ID \r\n" + 
					"        WHERE ORDENES.ID = PEDIDOS.ID_ORDEN AND PEDIDOS.ID_RESTAURANTE = " + idRestaurante + " AND PEDIDOS.FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND  PEDIDOS.FECHA <='" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"        GROUP BY PRODUCTOS.ID, ID_CLIENTE";
//			if(orderBy != null && (orderBy.equals("ID_CLIENTE") || orderBy.equals("NUMPEDIDOS")|| orderBy.equals("IDPRODUCTO")) )
//			{
//				sql += "\n ORDER BY " + orderBy;
//			}
			sql += ")";
		}
		else
		{
			sql = "SELECT * \r\n" + 
					"	 FROM CLIENTES\r\n" + 
					"	 WHERE CLIENTES.ID = " + idCliente + " AND NOT CLIENTES.ID = ANY\r\n" + 
					"     	(SELECT ID_CLIENTE\r\n" + 
					"       FROM ORDENES, PEDIDOS\r\n" + 
					"       INNER JOIN PRODUCTOS ON PEDIDOS.ID_PRODUCTO = PRODUCTOS.ID \r\n" + 
					"		WHERE ORDENES.ID = PEDIDOS.ID_ORDEN AND PEDIDOS.ID_RESTAURANTE = " + idRestaurante + " AND PEDIDOS.FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND  PEDIDOS.FECHA <='" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"		GROUP BY ID_CLIENTE";
//			if(orderBy != null && (orderBy.equals("ID_CLIENTE") || orderBy.equals("NUMPEDIDOS")) )
//			{
//				sql += "\n ORDER BY " + orderBy;
//			}
			sql += ")";
		}
		System.out.println(sql);
		PreparedStatement stmt= conn.prepareStatement(sql);
		recursos.add(stmt);
		ResultSet rs = stmt.executeQuery();
		while(rs.next())
		{
			
			Cliente cliente = new Cliente(rs.getLong("ID"), null, null, null);
			clientes.add(cliente);
		}
		return clientes;
	}
	//---------------------------------------------------	
	//	Requerimiento: RF12
	//---------------------------------------------------

	/**
	 * RFC12 Consultar los buenos clientes
	 * @param String tipo
	 * @param Long idUsuario
	 * @param String contrasenia
	 * @resp List<Cliente> de un tipo dado
	 * 1.5 SMDLV = 1.5 * 737.717 = 1106.5755
	 */

	public List<Cliente> getBuenosClientesTipo(String tipo, Long idUsuario, String contrasenia) throws Exception
	{
		List<Cliente> resp = new ArrayList<Cliente>();
		if(!tablaUsuarios.verficarUsuarioAdministrador(idUsuario, contrasenia))
		{
			resp = null;
		}
		else if(tipo.equalsIgnoreCase("FRECUENTES"))
		{
			String sql = "SELECT * FROM (SELECT CLIENTES.ID AS ID1, NAME AS NAME1, IDMESA AS IDMESA1, ORDENES.FECHA AS FECHA1 FROM CLIENTES, ORDENES WHERE CLIENTES.ID = ORDENES.ID_CLIENTE) T1 WHERE NOT EXISTS (SELECT * FROM (SELECT CLIENTES.ID AS ID2, NAME AS NAME2, IDMESA AS IDMESA2, ORDENES.FECHA AS FECHA2 FROM CLIENTES, ORDENES WHERE CLIENTES.ID = ORDENES.ID_CLIENTE) T2 WHERE T1.FECHA1 <= T2.FECHA2 + 7);";
			PreparedStatement stmt= conn.prepareStatement(sql);
			recursos.add(stmt);
			ResultSet rs = stmt.executeQuery();
			List<Cliente> resp2 = new ArrayList<Cliente>();
			while(rs.next())
			{
				Long id = rs.getLong("ID");
				String nombre = rs.getString("NAME");
				Long idMesa = rs.getLong("IDMESA");

				resp.add(new Cliente(id, idMesa,nombre, null));
				resp = resp2;
			}
		}
		else if(tipo.equalsIgnoreCase("RICO"))
		{
			String sql = "SELECT * FROM CLIENTES, ORDENES WHERE CLIENTES.ID = ORDENES.ID_CLIENTE AND COSTOTOTAL > 11065755;";
			PreparedStatement stmt= conn.prepareStatement(sql);
			recursos.add(stmt);
			ResultSet rs = stmt.executeQuery();
			List<Cliente> resp2 = new ArrayList<Cliente>();
			while(rs.next())
			{
				Long id = rs.getLong("ID");
				String nombre = rs.getString("NAME");
				Long idMesa = rs.getLong("IDMESA");

				resp.add(new Cliente(id, idMesa,nombre, null));
				resp = resp2;
			}
		}
		else if(tipo.equalsIgnoreCase("NOMENU"))
		{
			String sql = "SELECT * FROM ORDENES, MENUS_PEDIDOS, CLIENTES WHERE ORDENES.ID_CLIENTE = CLIENTES.ID AND NOT (ORDENES.ID = MENUS_PEDIDOS.IDORDEN);";
			PreparedStatement stmt= conn.prepareStatement(sql);
			recursos.add(stmt);
			ResultSet rs = stmt.executeQuery();
			List<Cliente> resp2 = new ArrayList<Cliente>();
			while(rs.next())
			{
				Long id = rs.getLong("ID");
				String nombre = rs.getString("NAME");
				Long idMesa = rs.getLong("IDMESA");

				resp.add(new Cliente(id, idMesa,nombre, null));
				resp = resp2;
			}
		}
		else
		{
			resp = null;
		}
		return resp;
	}


	//	/**
	//	 * Método que obtiene el ID del sigueinte Cliente.
	//	 * @return Long, siguiente ID de Clientes en la base de datos.
	//	 * @throws SQLException
	//	 * @throws Exception
	//	 */
	//	public Long getSiguienteIdCliente()throws SQLException, Exception
	//	{
	//		String sql = "SELECT * FROM CLIENTES\r\n" + 
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
	//	/**
	//	 * Método que agrega un cliente a RotondAndes.
	//	 * @param cliente Cliente, Información del cliente a agregar a RotondAndes.
	//	 * @return Cliente, información del cliente agregado.
	//	 * @throws SQLException
	//	 * @throws Exception
	//	 */
	//	public Cliente agregarCliente(Cliente cliente)throws SQLException, Exception
	//	{
	//		Long idCliente = getSiguienteIdCliente();
	//		String sql1 = "INSERT INTO CLIENTES (ID, NAME, MESA)\r\n" + 
	//				"    VALUES (" + idCliente + ", '" + cliente.getNombre() + "', " + cliente.getMesa() + ")";
	//		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
	//		recursos.add(prepStmt1);
	//		prepStmt1.executeQuery();
	//		
	//		cliente.setId(idCliente);
	//		return cliente;
	//	}
	
	
	public Cliente darClientePorNombre(String nombre, Integer origen) throws SQLException, Exception {
		Cliente clientePorId = null;

		String sqlClientePorId = "SELECT * FROM CLIENTES\r\n" + 
				"    NATURAL JOIN USUARIOS\r\n" + 
				"    WHERE NAME = " + nombre; 
		PreparedStatement stClientePorId = conn.prepareStatement(sqlClientePorId);
		recursos.add(stClientePorId);
		ResultSet rsClientePorId = stClientePorId.executeQuery();

		if (rsClientePorId.next()) {
			Long id2 = rsClientePorId.getLong("ID");
			String nameclientePorId = rsClientePorId.getString("NAME");
			Long mesaClientePorId = rsClientePorId.getLong("IDMESA");
			clientePorId = new Cliente(id2, mesaClientePorId, nameclientePorId, null);
		}
		List<Orden> ordenes = new ArrayList<Orden>();
		String sentenciaOrden = "SELECT * FROM ORDENES WHERE ID_CLIENTE = " + clientePorId.getId();
		PreparedStatement stamntOrden = conn.prepareStatement(sentenciaOrden);
		recursos.add(stamntOrden);
		ResultSet rsOrden = stamntOrden.executeQuery();
		while(rsOrden.next())
		{
			Long idOrden = rsOrden.getLong("ID");
			Double costoTotalOrden = rsOrden.getDouble("COSTOTOTAL");
			List<Pedido> pedidos = getPedidosPorClienteSegunOrden(idOrden);
			Boolean esConfirmada = rsOrden.getBoolean("ES_CONFIRMADA");
			List<PedidoDeMenu> menusPedidos = obtenerMenusPedidosDeOrden(idOrden);
			ordenes.add( new Orden(idOrden, costoTotalOrden, pedidos ,null, esConfirmada, menusPedidos));
		}
		clientePorId.setOrdenes(ordenes);
		return clientePorId;
	}
	/**
	 * Método que obtiene un cliente externo.
	 * @param idCliente Long, ID del Cliente.
	 * @param origen Integer, número de la base de datos de procedencia.
	 * @return Cliente, Información del Cliente.
	 * @throws SQLExcepcion
	 * @throws Exception
	 */
	public Cliente darClienteExterno(Long idCliente, Integer origen)throws SQLException, Exception
	{
		String sql = "SELECT * FROM CLIENTESEXTERNOS WHERE ID = " + idCliente + " AND ORIGEN = " + origen; 
		PreparedStatement st = conn.prepareStatement(sql);
		recursos.add(st);
		ResultSet rs= st.executeQuery();
		Cliente respuesta = null;
		if(rs.next())
		{
			respuesta = new Cliente(rs.getLong("ID"), rs.getLong("IDMESA"), rs.getString("NAME"), null);
		}
		return respuesta;
	}
	/**
	 * Método que crea un nuevo cliente externo.
	 * @param idCliente Long, Id del cliente.
	 * @param nombre String, nombre del Cliente.
	 * @param idMesa Long, ID de la Mesa.
	 * @param origen Integer, número de la base de datos de la que proviene.
	 * @return Cliente, nuevo cliente externo creado.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Cliente crearClienteExterno(Long idCliente, String nombre, Long idMesa, Integer origen)throws SQLException, Exception
	{
		String sql = "INSERT INTO CLIENTESEXTERNOS(ID, NAME, IDMESA, ORIGEN) VALUES (" + idCliente + ", " + nombre + ", " + idMesa + ", " + origen +  ")";
		PreparedStatement st = conn.prepareStatement(sql);
		recursos.add(st);
		st.executeQuery();
		return new Cliente(idCliente, idMesa, nombre, null);
	}
}
