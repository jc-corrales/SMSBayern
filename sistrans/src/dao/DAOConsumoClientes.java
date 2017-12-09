package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vos.Cliente;
import vos.ConsumoCliente;
import vos.ProductoLocal;
import vos.RegistroVentas;
import vos.Restaurante;
/**
 * 
 * @author Juan Carlos Corrales
 *
 */
public class DAOConsumoClientes
{
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
	public DAOConsumoClientes() {
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

	/**
	 * Método que obtiene la información básica del consumo de los clientes.
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<ConsumoCliente> getConsumoClientes()throws SQLException, Exception
	{
		System.out.println("ENTRO A METODO CONSUMO");
		String sql = "SELECT ID_CLIENTE, ID_PRODUCTO, COUNT(PEDIDOS.ID) AS CANTIDADORDENADOS\r\n" + 
				"FROM PEDIDOS JOIN ORDENES ON Pedidos.Id_Orden = ORDENES.ID\r\n" + 
				"WHERE SERVIDO = 1\r\n" + 
				"GROUP BY ID_CLIENTE, ID_PRODUCTO";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println("POST SQL1");
		List<ConsumoCliente> lista = new ArrayList<ConsumoCliente>();
		while(rs.next())
		{
			System.out.println("Entro a while.");
			Cliente cliente = new Cliente(rs.getLong("ID_CLIENTE"), null, null, null);
			ProductoLocal producto = new ProductoLocal(rs.getLong("ID_PRODUCTO"), null, null, null, null, null, null, null, null, null, null);
			Integer cantidad = rs.getInt("CANTIDADORDENADOS");
			
			String sql2 = "SELECT * FROM PRODUCTOS WHERE ID = " +producto.getId();
			System.out.println(sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			System.out.println("POST SQL2");
			if(rs2.next())
			{
				producto.setCategoria(rs2.getString("CATEGORIA"));
				System.out.println("CATEGORIA GOOD");
				producto.setNombre(rs2.getString("NAME"));
				System.out.println("NAME GOOD");
				producto.setDescripcionEspaniol(rs2.getString("DESCRIPCION"));
				System.out.println("DESCRIPCION GOOD");
				producto.setDescripcionIngles(rs2.getString("DESCRIPTION"));
				System.out.println("DESCRIPTION GOOD");
			}
			
			lista.add(new ConsumoCliente(cliente, producto, cantidad));
		}
		return lista;
	}
	/**
	 * Método que obtiene toda la información de consulta de un Cliente.
	 * @param idCliente Long, ID del cliente a consultar.
	 * @return List<ConsumoCliente>, consumos hechos por el cliente.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<ConsumoCliente> getConsumoUnCliente(Long idCliente)throws SQLException, Exception
	{
		System.out.println("ENTRO A METODO CONSUMO");
		String sql = "SELECT ID_CLIENTE, ID_PRODUCTO, COUNT(PEDIDOS.ID) AS CANTIDADORDENADOS\r\n" + 
				"FROM PEDIDOS JOIN ORDENES ON Pedidos.Id_Orden = ORDENES.ID\r\n" + 
				"WHERE SERVIDO = 1 AND ID_CLIENTE = " + idCliente + "\r\n" + 
				"GROUP BY ID_CLIENTE, ID_PRODUCTO";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		System.out.println("POST SQL1");
		List<ConsumoCliente> lista = new ArrayList<ConsumoCliente>();
		while(rs.next())
		{
			System.out.println("Entro a while.");
			Cliente cliente = new Cliente(rs.getLong("ID_CLIENTE"), null, null, null);
			ProductoLocal producto = new ProductoLocal(rs.getLong("ID_PRODUCTO"), null, null, null, null, null, null, null, null, null, null);
			Integer cantidad = rs.getInt("CANTIDADORDENADOS");
			
			String sql2 = "SELECT * FROM PRODUCTOS WHERE ID = " +producto.getId();
			System.out.println(sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			System.out.println("POST SQL2");
			if(rs2.next())
			{
				producto.setCategoria(rs2.getString("CATEGORIA"));
				System.out.println("CATEGORIA GOOD");
				producto.setNombre(rs2.getString("NAME"));
				System.out.println("NAME GOOD");
				producto.setDescripcionEspaniol(rs2.getString("DESCRIPCION"));
				System.out.println("DESCRIPCION GOOD");
				producto.setDescripcionIngles(rs2.getString("DESCRIPTION"));
				System.out.println("DESCRIPTION GOOD");
			}
			
			lista.add(new ConsumoCliente(cliente, producto, cantidad));
		}
		return lista;
	}
	/**
	 * Método que establecece el Registro de Ventas para un día específico.
	 * @param fecha String, Fecha, formato: DD/MM/AA
	 * @param dia String, nombre del día.
	 * @throws SQLException
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void setRegistroVentas(String fechaReferencia, String lol)throws SQLException, Exception
	{
		System.out.println("ENTRO A METODO CONSUMO");
		String sqlBorrado = "DELETE FROM REGISTROVENTAS";
		System.out.println(sqlBorrado);
		PreparedStatement prepStmtBorrado = conn.prepareStatement(sqlBorrado);
		recursos.add(prepStmtBorrado);
		prepStmtBorrado.executeQuery();
		
		
		System.out.println("ENTRO A METODO RegistroVentas");
		System.out.println("Fecha Referencia: "+fechaReferencia);
		String[] datos = fechaReferencia.split("/");
		int nDia = Integer.parseInt(datos[0]);
		int nMes = Integer.parseInt(datos[1]);
		int nAnio = Integer.parseInt(datos[2]);
		boolean alerta = false;
		if(nMes == 12 && (nDia + 7) > 31)
		{
			alerta = true;
		}
		for(int i = 0; i < 7; i++)
		{
			Date fechaTemp = new Date((100 + nAnio), (nMes-1), (nDia + i));
			int tipoDia = fechaTemp.getDay();
			System.out.println("TIPO DIA:"+tipoDia);
			String dia = null;
			if(tipoDia == 0)
			{
				dia = "DOMINGO";
			}
			else if(tipoDia == 1)
			{
				dia = "LUNES";
			}
			else if(tipoDia == 2)
			{
				dia = "MARTES";
			}
			else if(tipoDia == 3)
			{
				dia = "MIERCOLES";
			}
			else if(tipoDia == 4)
			{
				dia = "JUEVES";
			}
			else if(tipoDia == 5)
			{
				dia = "VIERNES";
			}
			else if(tipoDia == 6)
			{
				dia="SABADO";
			}
			System.out.println("Fecha Sistema: " +fechaTemp);
			String fecha = fechaTemp.getDate() + "/" + (fechaTemp.getMonth()+1) + "/" + nAnio;
			if(alerta && (nDia + i) > 31)
			{
				fecha = fechaTemp.getDate() + "/" + (fechaTemp.getMonth()+1) + "/" + (nAnio + 1);
			}
			System.out.println("fecha: " + fecha);
			String sql1 = "SELECT *\r\n" + 
					"    FROM(\r\n" + 
					"    SELECT ID_RESTAURANTE, COUNT (PEDIDOS.ID) AS NPEDIDOS\r\n" + 
					"        FROM PEDIDOS\r\n" + 
					"        WHERE FECHA >= '" + fecha + " 01:00:00,000000000 AM' AND FECHA <= '" + fecha + " 11:59:59,000000000 PM'\r\n" + 
					"        GROUP BY ID_RESTAURANTE\r\n" + 
					"        ORDER BY NPEDIDOS DESC)\r\n" + 
					"     WHERE ROWNUM = 1";
			System.out.println(sql1);
			PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
			recursos.add(prepStmt1);
			ResultSet rs1 = prepStmt1.executeQuery();
			
			//SQL RESTAURANTE MENOS FRECUENTADO		
			String sql2 = "SELECT *\r\n" + 
							"    FROM(\r\n" + 
							"    SELECT ID_RESTAURANTE, COUNT (PEDIDOS.ID) AS NPEDIDOS\r\n" + 
							"        FROM PEDIDOS\r\n" + 
							"        WHERE FECHA >= '" + fecha + " 01:00:00,000000000 AM' AND FECHA <= '" + fecha + " 11:59:59,000000000 PM'\r\n" + 
							"        GROUP BY ID_RESTAURANTE\r\n" + 
							"        ORDER BY NPEDIDOS ASC)\r\n" + 
							"     WHERE ROWNUM = 1";
			System.out.println(sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			
			//SQL PRODUCTO MÁS CONSUMIDO
			String sql3 = "SELECT *\r\n" + 
					"    FROM(\r\n" + 
					"    SELECT ID_PRODUCTO, COUNT (PEDIDOS.ID) AS NPEDIDOS\r\n" + 
					"        FROM PEDIDOS\r\n" + 
					"        WHERE FECHA >= '" + fecha + " 01:00:00,000000000 AM' AND FECHA <= '" + fecha + " 11:59:59,000000000 PM'\r\n" + 
					"        GROUP BY ID_PRODUCTO\r\n" + 
					"        ORDER BY NPEDIDOS DESC)\r\n" + 
					"     WHERE ROWNUM = 1";
			System.out.println(sql3);
			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();
			
			//SQL PRODUCTO MÁS CONSUMIDO
			String sql4 = "SELECT *\r\n" + 
					"    FROM(\r\n" + 
					"    SELECT ID_PRODUCTO, COUNT (PEDIDOS.ID) AS NPEDIDOS\r\n" + 
					"        FROM PEDIDOS\r\n" + 
					"        WHERE FECHA >= '" + fecha + " 01:00:00,000000000 AM' AND FECHA <= '" + fecha + " 11:59:59,000000000 PM'\r\n" + 
					"        GROUP BY ID_PRODUCTO\r\n" + 
					"        ORDER BY NPEDIDOS DESC)\r\n" + 
					"     WHERE ROWNUM = 1";
			System.out.println(sql4);
			PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
			recursos.add(prepStmt4);
			ResultSet rs4 = prepStmt4.executeQuery();
			
			Long productoMasConsumido = null;
			Long productoMenosConsumido = null;
			Long restauranteMasFrecuentado = null;
			Long restauranteMenosFrecuentado = null;
			try
			{
				rs1.next();
				restauranteMasFrecuentado = rs1.getLong("ID_RESTAURANTE");
			}
			catch(Exception e)
			{
				continue;
			}
			try
			{
				rs2.next();
				restauranteMenosFrecuentado = rs2.getLong("ID_RESTAURANTE");
			}
			catch(Exception e)
			{
				continue;
			}
			try
			{
				rs3.next();
				productoMasConsumido = rs3.getLong("ID_PRODUCTO");
			}
			catch(Exception e)
			{
				continue;
			}
			try
			{
				rs4.next();
				productoMenosConsumido = rs4.getLong("ID_PRODUCTO");
			}
			catch(Exception e)
			{
				continue;
			}
			System.out.println(productoMenosConsumido.toString());
			//SQL DE GUARDAR
			String sql5 = "INSERT INTO REGISTROVENTAS(DIA, IDPRODUCTOMASVENDIDO, IDPRODUCTOMENOSVENDIDO, IDRESTAURANTEMASFRECUENTADO, IDRESTAURANTEMENOSFRECUENTADO) VALUES ('" + dia + "', " + productoMasConsumido + ", " + productoMenosConsumido + ", " + restauranteMasFrecuentado + ", " + restauranteMenosFrecuentado + ")";
			System.out.println(sql5);
			PreparedStatement prepStmt5 = conn.prepareStatement(sql5);
			recursos.add(prepStmt5);
			prepStmt5.executeQuery();
		}
		
		
	}
	/**
	 * Método que entrega el registro de Ventas de la semana.
	 * @return List<RegistroVentas>, Lista de Registro de Ventas.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<RegistroVentas> getRegistroVentas()throws SQLException, Exception
	{
		List<RegistroVentas> listaRegistros = new ArrayList<RegistroVentas>();
		System.out.println("ENTRO A METODO OBTENER REGISTRO VENTAS");
		String sql = "SELECT * FROM REGISTROVENTAS";
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{
			String dia = rs.getString("DIA");
			Long prodMasVendido = rs.getLong("IDPRODUCTOMASVENDIDO");
			Long prodMenosVendido = rs.getLong("IDPRODUCTOMENOSVENDIDO");
			Long restMasVendido = rs.getLong("IDRESTAURANTEMASFRECUENTADO");
			Long restMenosVendido = rs.getLong("IDRESTAURANTEMENOSFRECUENTADO");
			ProductoLocal productoMasConsumido = getProducto(prodMasVendido);
			ProductoLocal productoMenosConsumido = getProducto(prodMenosVendido);
			Restaurante restauranteMasFrecuentado= new Restaurante(restMasVendido, null, null, null, null, null, null);
			Restaurante restauranteMenosFrecuentado = new Restaurante(restMenosVendido, null, null, null, null, null, null);
			RegistroVentas registro = new RegistroVentas(dia, restauranteMasFrecuentado, restauranteMenosFrecuentado, productoMasConsumido, productoMenosConsumido);
			listaRegistros.add(registro);
		}
		System.out.println("POST OBTENER REGISTROVENTAS DAO");
		return listaRegistros;
	}
	public ProductoLocal getProducto(Long id)throws SQLException, Exception
	{
		String sql = "SELECT * FROM PRODUCTOS WHERE ID = " + id;
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
		{
			
		}
		String nombre = rs.getString("NAME");
		String descripcionEspaniol = rs.getString("DESCRIPCION");
		String descripcionIngles = rs.getString("DESCRIPTION");
		String categoria = rs.getString("CATEGORIA");
		return new ProductoLocal(id, nombre, descripcionEspaniol, descripcionIngles, null, null, null, null, categoria, null, null);
	}
}
