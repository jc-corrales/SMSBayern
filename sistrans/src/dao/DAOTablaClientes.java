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
import vos.ClienteFrecuente;
import vos.Menu;
import vos.Orden;
import vos.Pedido;
import vos.PedidoDeMenu;
import vos.Producto;
import vos.TipoComida;

public class DAOTablaClientes {

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
			Producto producto = new Producto();
			if(rsProd.next())
			{
				String nombre = rsProd.getString("NAME");
				String descripcionEspaniol = rsProd.getString("DESCRIPCION");
				String descripcionIngles = rsProd.getString("DESCRIPTION");
				Double costoDeProduccion = rsProd.getDouble("COSTO_PRODUCCION");
				Double precio = rsProd.getDouble("PRECIO");

				String categoria = rsProd.getString("CATEGORIA");

				producto = new Producto(idProducto, nombre, descripcionEspaniol, descripcionIngles, costoDeProduccion, null, precio, null, categoria, null, null);
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
			Producto entrada = darProducto(rs.getLong("ID_ENTRADA"), idRestaurante);
			Producto platoFuerte = darProducto(rs.getLong("ID_PLATOFUERTE"), idRestaurante);
			Producto postre = darProducto(rs.getLong("ID_POSTRE"), idRestaurante);
			Producto bebida = darProducto(rs.getLong("ID_BEBIDA"), idRestaurante);
			Producto acompaniamiento = darProducto(rs.getLong("ID_ACOMPANIAMIENTO"), idRestaurante);
			Double costoProduccion = (entrada.getCostoDeProduccion() + platoFuerte.getCostoDeProduccion() + postre.getCostoDeProduccion() + bebida.getCostoDeProduccion() + acompaniamiento.getCostoDeProduccion());
			Menu menu = new Menu(idRestaurante, name, costoProduccion, descripcion, description, precio, entrada, platoFuerte, bebida, postre, acompaniamiento);
			PedidoDeMenu pedido = new PedidoDeMenu(id, menu);
			respuesta.add(pedido);
		}
		return respuesta;
	}

	private Producto darProducto(Long id, Long idRest) throws SQLException, Exception {
		Producto producto = new Producto();

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

	public List<Cliente> getClientesConMin1ConsumoEnRangoFechasEnRestaurante(Long idRestaurante, Date fecha1, Date fecha2, String orderBy, String groupBy, Long idUsuario, String contrasenia) throws Exception
	{
		List<Cliente> resp = new ArrayList<Cliente>();
		if(idUsuario == idRestaurante || !tablaUsuarios.verficarUsuarioAdministrador(idUsuario, contrasenia))
		{
			resp = null;
		}
		else
		{
			String sql = "SELECT CLIENTES.ID, CLIENTES.NAME, CLIENTES.IDMESA FROM (SELECT * FROM CLIENTES, (SELECT * FROM PEDIDOS, ORDENES WHERE PEDIDOS.ID_ORDEN = ORDENES.ID) WHERE CLIENTES.ID = ORDENES.ID_CLIENTE), (SELECT * FROM PRODUCTO_RESTAURANTE, (SELECT * FROM RESTAURANTES WHERE RESTAURANTES.ID =" + idRestaurante +  ") WHERE RESTAURANTES.ID = PRODUCTO_RESTAURANTE.ID_REST) WHERE PEDIDOS.ID_PRODUCTO = PRODUCTO_RESTAURANTE.ID_PROD AND ORDENES.FECHA BETWEEN" + fecha1 +"AND" +fecha2+ " ORDER BY"+orderBy+" GROUP BY"+groupBy+";";
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
		return resp;
	}

	/**
	 * RFC10 Consultar consumo en Rotondandes
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

	public List<Cliente> getClientesConNOMin1ConsumoEnRangoFechasEnRestaurante(Long idRestaurante, Date fecha1, Date fecha2, String orderBy, String groupBy, Long idUsuario, String contrasenia) throws Exception
	{
		List<Cliente> resp = new ArrayList<Cliente>();
		if(idUsuario == idRestaurante || !tablaUsuarios.verficarUsuarioAdministrador(idUsuario, contrasenia))
		{
			resp = null;
		}
		else
		{
			String sql = "SELECT CLIENTES.ID, CLIENTES.NAME, CLIENTES.IDMESA FROM (SELECT * FROM CLIENTES, (SELECT * FROM PEDIDOS, ORDENES WHERE PEDIDOS.ID_ORDEN = ORDENES.ID) WHERE CLIENTES.ID = ORDENES.ID_CLIENTE), (SELECT * FROM PRODUCTO_RESTAURANTE, (SELECT * FROM RESTAURANTES WHERE RESTAURANTES.ID =" + idRestaurante +  ") WHERE RESTAURANTES.ID = PRODUCTO_RESTAURANTE.ID_REST) WHERE PEDIDOS.ID_PRODUCTO <> PRODUCTO_RESTAURANTE.ID_PROD AND ORDENES.FECHA BETWEEN" + fecha1 +"AND" +fecha2+ " ORDER BY"+orderBy+" GROUP BY"+groupBy+";";
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
		return resp;
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
}
