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
import vos.Orden;
import vos.Pedido;
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
				ordenes.add( new Orden(idOrden, costoTotalOrden, pedidos ,null, esConfirmada));
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
				ordenes.add( new Orden(idOrden, costoTotalOrden, null ,null, esConfirmada));
			}
			
			
			cliente = new Cliente(id2, mesaClientePorId, nameclientePorId, ordenes);
		}
		return cliente;
	}
	/**
	 * M�todo que da un Cliente.
	 * @param id Long, ID del cliente a buscar.
	 * @return Cliente, informaci�n b�sica del cliente, sin �rdenes.
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
			ordenes.add( new Orden(idOrden, costoTotalOrden, pedidos ,null, esConfirmada));
		}
		clientePorId.setOrdenes(ordenes);
		return clientePorId;
	}
	/**
	 * M�todo que obtiene los Pedidos hechos por un cliente, por orden.
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
//	/**
//	 * M�todo que obtiene el ID del sigueinte Cliente.
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
//	 * M�todo que agrega un cliente a RotondAndes.
//	 * @param cliente Cliente, Informaci�n del cliente a agregar a RotondAndes.
//	 * @return Cliente, informaci�n del cliente agregado.
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
