package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import vos.Ingrediente;
import vos.Producto;
import vos.ProductoBase;
import vos.RotondAndes;
import vos.TipoComida;

public class DAOTablaProductos {
	private ArrayList<Object> recursos;
	private Connection conn;

	public static final int RESTAURANTE = 1;
	public static final int CATEGORIA = 2;
	public static final int RANGO_PRECIOS= 3;
	public static final int TIPO = 4;


	public DAOTablaProductos() {
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

	public List<Producto> darProductos() throws SQLException, Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sentencia = "SELECT * FROM PRODUCTOS";
		PreparedStatement stamnt = conn.prepareStatement(sentencia);
		recursos.add(stamnt);
		ResultSet rs = stamnt.executeQuery();
		int a = 1;
		while(rs.next()) {
			Producto producto = new Producto();
			producto.setId(rs.getLong("ID"));
			producto.setNombre("Producto" + a);
			producto.setCostoDeProduccion(rs.getDouble("COSTO_PRODUCCION"));
			producto.setDescripcionEspaniol(rs.getString("DESCRIPCION"));
			producto.setDescripcionIngles(rs.getString("DESCRIPTION"));
			productos.add(producto);
			a++;
		}
		return productos;
	}


	public Producto darProducto(Long id, Long idRest) throws SQLException, Exception {
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
			producto.setProductosEquivalentes(darProductosEquivalentes(producto.getId(), idRest));
			producto.setCantidad(rs.getInt("CANTIDAD"));
			producto.setTiposComida(tipos);
			return producto;
		}
		return null;
	}


	private List<ProductoBase> darProductosEquivalentes(Long id, Long idRest)  throws SQLException, Exception {
		String sql = "SELECT * FROM PRODUCTOS, PRODUCTO_RESTAURANTE, PRODUCTOSSIMILARES WHERE ID = ID_PROD AND ID_PROD2 = ID_PROD AND ID_REST = "; 
		sql += idRest + " AND ID_PROD1 = "  + id; 
		System.out.println(sql);
		PreparedStatement st = conn.prepareStatement(sql);
		recursos.add(st);
		ResultSet rs = st.executeQuery();
		
		List<ProductoBase> prods = new ArrayList<>();
		
		while (rs.next()) {
			ProductoBase prod = new ProductoBase();
			prod.setId(rs.getLong("ID"));
			prod.setNombre(rs.getString("NAME"));
			prod.setDescripcionEspaniol(rs.getString("DESCRIPCION"));
			prod.setDescripcionIngles(rs.getString("DESCRIPTION"));
			prod.setCategoria(rs.getString("CATEGORIA"));
			prods.add(prod);
		}
		return prods;
	}



	public List<Producto> darProductosPor(Integer filtro, String parametro) throws SQLException, Exception{
		ObjectMapper om = new ObjectMapper();
		ArrayList<Producto> productos = new ArrayList<Producto>();
		String sentencia = "SELECT * FROM PRODUCTOS, PRODUCTO_RESTAURANTE WHERE ID = ID_PROD ";

		switch(filtro) {

		case RESTAURANTE:
			sentencia +=  "AND ID_REST = " + Integer.parseInt(parametro);
			break;

		case CATEGORIA: 
			sentencia += "AND CATEGORIA = '" + ((String) parametro) + "'";
			System.out.println("sentencia -> " + sentencia);
			break;
			
		case RANGO_PRECIOS:
			String[] precios = parametro.split(",");
			sentencia += "AND PRECIO >= " + Integer.parseInt(precios[0]) + " AND  PRECIO <= " + Integer.parseInt(precios[1]);
		default:
			break;
		}

		PreparedStatement stamnt = conn.prepareStatement(sentencia);
		recursos.add(stamnt);
		ResultSet rs = stamnt.executeQuery();
		while(rs.next()) {
			Producto producto = new Producto();
			producto.setId(rs.getLong("ID"));
			producto.setNombre(rs.getString("NAME"));
			producto.setDescripcionEspaniol(rs.getString("DESCRIPCION"));
			producto.setDescripcionIngles(rs.getString("DESCRIPTION"));
			producto.setCategoria(rs.getString("CATEGORIA"));
			producto.setPrecio(rs.getDouble("PRECIO"));
			producto.setCostoDeProduccion(rs.getDouble("COSTO_PRODUCCION"));
			producto.setProductosEquivalentes(darProductosEquivalentes(producto.getId(), rs.getLong("ID_REST")));
			producto.setCantidad(rs.getInt("CANTIDAD"));
			productos.add(producto);
		}
		return productos;
	}



	public List<ProductoBase> darPreferencias(Long id)  throws SQLException, Exception{
		List<ProductoBase> preferencias = new ArrayList<>();
		
		String sql = "SELECT * FROM PRODUCTOS, PREFERENCIAS WHERE ID_PRODUCTO = ID AND ID_CLIENTEFRECUENTE = " + id;
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while(rs.next()) {
			ProductoBase prod = new ProductoBase();
			prod.setId(rs.getLong("ID"));
			prod.setNombre(rs.getString("NAME"));
			prod.setDescripcionEspaniol(rs.getString("DESCRIPCION"));
			prod.setDescripcionIngles(rs.getString("DESCRIPTION"));
			prod.setCategoria(rs.getString("CATEGORIA"));
			
			preferencias.add(prod);
		}
		return preferencias;
	}
	/**
	 * Método que crea un Producto general en la Base de Datos.
	 * @param producto
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Producto agregarProductoSinEquivalencias(Long idRestaurante, Producto producto)throws SQLException, Exception
	{
		conn.setAutoCommit(false);
		//Agregar información para el Producto base.
		String sqlComprobar = "SELECT * FROM PRODUCTOS WHERE ID = " + producto.getId();
		
		PreparedStatement prepComprobar = conn.prepareStatement(sqlComprobar);
		recursos.add(prepComprobar);
		ResultSet rs1 = prepComprobar.executeQuery();
		
		if(!rs1.next())
		{
			System.out.println("El Producto Base no existe Previamente, creando nuevo producto Base.");
			String sql = "INSERT INTO PRODUCTOS (ID, DESCRIPCION, DESCRIPTION, CATEGORIA, NAME)\r\n" + 
					"    VALUES ("+producto.getId()+", '"+producto.getDescripcionEspaniol()+"', '" + producto.getDescripcionIngles() + "', '" +producto.getCategoria()+"', '" + producto.getNombre()+"')";
			System.out.println(sql);
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}

		//Agregar información para Restaurante_Producto (Lo específico para el Restaurante)
		String sql2 = "INSERT INTO PRODUCTO_RESTAURANTE (ID_PROD, ID_REST, PRECIO, COSTO_PRODUCCION, CANTIDAD)\r\n" + 
				"    VALUES (" + producto.getId()+", "+ idRestaurante +", "+ producto.getPrecio()+", "+producto.getCostoDeProduccion()+", "+producto.getCantidad()+")\r\n";
		
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();
		
		List<TipoComida> tipos = producto.getTiposComida();
		
		for(int i = 0; i < tipos.size(); i++)
		{
			String sql3 = "INSERT INTO TIPOPRODUCTO (ID_PROD, ID_TIPO) VALUES (" +producto.getId()+ ", " +tipos.get(i).getId()+ ")";	
			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			prepStmt3.executeQuery();
		}
		
		
		conn.commit();
		conn.setAutoCommit(true);
		return producto;
	}

	/**
	 * Método que registra la equivalencia entre dos productos.
	 * @param idRestaurante Long, ID del restaurante dueño de los dos productos.
	 * @param idProducto1 Long, ID del producto 1.
	 * @param idProducto2 Long, ID del producto 2.
	 * @throws SQLException
	 * @throws Exception
	 */
	public void registrarEquivalenciaDeProductos(Long idRestaurante, Long idProducto1, Long idProducto2)throws SQLException, Exception
	{
		
		String sqlComprobar1 = "SELECT * FROM PRODUCTOS WHERE ID = " + idProducto1;
		
		PreparedStatement prepComprobar1 = conn.prepareStatement(sqlComprobar1);
		recursos.add(prepComprobar1);
		ResultSet rs1 = prepComprobar1.executeQuery();
		
		String sqlComprobar2 = "SELECT * FROM PRODUCTOS WHERE ID = " + idProducto2;
		
		PreparedStatement prepComprobar2 = conn.prepareStatement(sqlComprobar2);
		recursos.add(prepComprobar2);
		ResultSet rs2 = prepComprobar2.executeQuery();
		
		if(!rs1.next())
		{
			throw new Exception("El producto con ID: " + idProducto1 + " no existe.");
		}
		if(!rs2.next())
		{
			throw new Exception("El producto con ID: " + idProducto2 + " no existe.");
		}
		if((rs1.getLong("ID_RESTAURANTE") != (rs2.getLong("ID_RESTAURANTE"))) || ((rs1.getLong("ID_RESTAURANTE") != idRestaurante)) || ((rs2.getLong("ID_RESTAURANTE")!= idRestaurante)))
		{
			throw new Exception("Los Productos no pertenecen al mismo restaurante.");
		}
		conn.setAutoCommit(false);
		
		String sqlInsertar = "INSERT INTO ISIS2304B121720.PRODUCTOSSIMILARES (ID_RESTAURANTE, ID_PROD1, ID_PROD2)\r\n" + 
				"    VALUES("+idRestaurante + ", " + idProducto1 + ", " + idProducto2+ ")";
		PreparedStatement prepStmt = conn.prepareStatement(sqlInsertar);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		conn.commit();
		conn.setAutoCommit(true);
	}
}
