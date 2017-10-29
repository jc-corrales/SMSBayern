package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import vos.Ingrediente;
import vos.Menu;
import vos.Producto;
import vos.ProductoBase;
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
	/**
	 * Método dinámico, obtiene una lista de Productos según unos parámetros de búsqueda.
	 * @param filtro Integer, tipo de filtro.
	 * @param parametro String, parámetro según el cual realizar la búsqueda.
	 * @return List<Producto>, lista de Productos.
	 * @throws SQLException
	 * @throws Exception
	 */
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
	/**
	 * Método que da los productos preferidos de un Cliente Frecuente. 
	 * @param id Long, ID del cliente Frecuente.
	 * @return List<ProductoBase>, Lista de Productos preferidos del Cliente Frecuente.
	 * @throws SQLException
	 * @throws Exception
	 */
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
//		List<ProductoBase> equivalentes = producto.getProductosEquivalentes();
//		if(equivalentes != null)
//		{
//			for(int i = 0; i < equivalentes.size(); i++)
//			{
//				String sql4 = "INSERT INTO PRODUCTOSSIMILARES (ID_RESTAURANTE, ID_PROD1, ID_PROD2) VALUES (" +idRestaurante+ ", " +producto.getId()+ ", " + equivalentes.get(i).getId()+ ")";	
//				PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
//				recursos.add(prepStmt4);
//				prepStmt4.executeQuery();
//			}
//		}
		
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
  	/**
	 * RF 14
	 * Registrar Pedido de un producto (generalmente un menú) con equivalencias.
	 * 
	 */
	public Menu registrarPedidoProductoEquivalencias(long pidp1, long pidp2, long pidr)
	{
		String sqlExisteOtroMenu = "SELECT ID_PROD2 FROM PRODUCTOSSIMILARES WHERE" + pidp1 + "= PRODUCTOSSIMILARES.ID_PROD1 AND" + pidp2 + "= PRODUCTOSSIMILARES.ID_PROD2" ; 
		PreparedStatement st = conn.prepareStatement(sqlExisteOtroMenu);
		recursos.add(st);
		ResultSet rs = st.executeQuery();
		Menu resp = new Menu(null, null, null, null, null, null, null, null, null, null);

		if(rs != null)
		{
		long idNuevoMenu = 0;
		
			System.out.println("Si está el otro menú disponible");
			{
				idNuevoMenu = rs.getLong("ID_PROD2");
				
				String sqlprod = "SELECT * FROM MENUS WHERE ID = " + idNuevoMenu;
				PreparedStatement st2 = conn.prepareStatement(sqlprod);
				recursos.add(st2);
				ResultSet rs2 = st2.executeQuery();
				
				resp.setId(rs2.getLong("ID"));
				resp.setBebida(rs2.getString("BEBIDA"));
				resp.setCostoProduccion(rs2.getDouble("COSTO_PRODUCCION"));
				
				
			}
		}
		
		while(rs.next()) {
			ProductoBase prod = new ProductoBase();
			prod.setId(rs.getLong("ID"));
			prod.setNombre(rs.getString("NAME"));
			prod.setDescripcionEspaniol(rs.getString("DESCRIPCION"));
			prod.setDescripcionIngles(rs.getString("DESCRIPTION"));
			prod.setCategoria(rs.getString("CATEGORIA"));
			
			preferencias.add(prod);
		}
		
		/**
		 * RF13 SURTIR RESTAURANTE 
		 * @throws SQLException 
		 */
		public void registrarCantidadProductosDisponibles(int pCantidad, long idProd, long idRest) throws SQLException
		{
			String sqlInsertar = "INSERT INTO PRODUCTO_RESTAURANTE(CANTIDAD) VALUES"+ pCantidad + " WHERE PRODUCTO_RESTAURANTE.ID_PROD =" + idProd + "AND PRODUCTO_RESTAURANTE.ID_REST =" + idRest;
			PreparedStatement prepStmt = conn.prepareStatement(sqlInsertar);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			conn.commit();
			conn.setAutoCommit(true);
		}
		
		/**
		 * Dar los ids de los ingredientes de un producto
		 * @throws SQLException 
		 */
		public List<Long> darIngredientesDeProducto(Long idProd) throws SQLException
		{
			List<Long> resp = new ArrayList<>();
			
			String sql = "SELECT ID_INGREDIENTE FROM INGREDIENTES_PRODUCTO WHERE ID_PRODUCTO = " + idProd;
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			
			while(rs.next())
			{
				resp.add(rs.getLong("ID_INGREDIENTE"));
			}
			
			return resp;
		}
	/**
	 * Método que obtiene los productos más ofrecidos por Restaurantes en RotondAndes.
	 * @return List<Producto>, lista de Productos.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Producto> darProductosMasOfrecidos()throws SQLException, Exception
	{
		List<Producto> productos = new ArrayList<Producto>();
		String sql = "SELECT PRODUCTO.ID, PRODUCTO.NAME, PRODUCTO.DESCRIPCION, PRODUCTO.DESCRIPTION, PRODUCTO.CATEGORIA, COUNT(COMPL.ID_REST) AS NUMVECESOFRECIDOS\r\n" + 
				"    FROM PRODUCTOS PRODUCTO, PRODUCTO_RESTAURANTE COMPL\r\n" + 
				"    WHERE PRODUCTO.ID = COMPL.ID_PROD\r\n" + 
				"    GROUP BY PRODUCTO.ID, PRODUCTO.DESCRIPCION, PRODUCTO.DESCRIPTION, PRODUCTO.CATEGORIA, PRODUCTO.NAME\r\n" + 
				"    ORDER BY NUMVECESOFRECIDOS DESC";
		
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs1 = prepStmt.executeQuery();
		Integer numMax = 0;
		if(rs1.next())
		{
			Long id =rs1.getLong("ID");
			String nombre = rs1.getString("NAME");
			String descripcionEspaniol = rs1.getString("DESCRIPCION");
			String descripcionIngles = rs1.getString("DESCRIPTION");
			String categoria = rs1.getString("CATEGORIA");
			numMax = rs1.getInt("CANTIDAD");
		
			Producto temp = new Producto(id, nombre, descripcionEspaniol, descripcionIngles, null, null, null, null, categoria, null, numMax);
			productos.add(temp);
		}
		while(rs1.next())
		{
			if(rs1.getInt("CANTIDAD") == numMax)
			{
				Long id =rs1.getLong("ID");
				String nombre = rs1.getString("NAME");
				String descripcionEspaniol = rs1.getString("DESCRIPCION");
				String descripcionIngles = rs1.getString("DESCRIPTION");
				String categoria = rs1.getString("CATEGORIA");
				Producto temp = new Producto(id, nombre, descripcionEspaniol, descripcionIngles, null, null, null, null, categoria, null, numMax);
				productos.add(temp);
			}
		}
		return productos;
	}
}
