package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Cliente;
import vos.ConsumoCliente;
import vos.Producto;
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
			Producto producto = new Producto(rs.getLong("ID_PRODUCTO"), null, null, null, null, null, null, null, null, null, null);
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
			Producto producto = new Producto(rs.getLong("ID_PRODUCTO"), null, null, null, null, null, null, null, null, null, null);
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
}
