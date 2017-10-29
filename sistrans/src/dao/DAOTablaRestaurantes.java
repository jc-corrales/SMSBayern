package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import vos.Menu;
import vos.Producto;
import vos.Representante;
import vos.Restaurante;
import vos.TipoComida;

public class DAOTablaRestaurantes {
	private ArrayList<Object> recursos;
	private Connection conn;
	
	public DAOTablaRestaurantes() {
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


	public List<Restaurante> darRestaurantesDeZona(Long id) throws SQLException, Exception {
		List<Restaurante> restaurantes = new ArrayList<>();
		String sql = "SELECT * FROM RESTAURANTES  WHERE ID_ZONA = " + id;
		PreparedStatement st = conn.prepareStatement(sql);
		recursos.add(st);
		ResultSet rs = st.executeQuery();
		
		while (rs.next()) {
			
			/*
			 * Long id, 
			@JsonProperty(value="name") String name, 
			@JsonProperty(value="paginaWeb") String pagina,
			@JsonProperty(value = "productos")List<Producto> productos,
			@JsonProperty(value = "tipo")TipoComida tipo
			 */
			Restaurante res = new Restaurante(rs.getLong("ID"), rs.getString("NAME"), rs.getString("PAGINA_WEB"), new ArrayList<Producto>(), new TipoComida(0L, new String("hola")), rs.getDouble("PRECIO"));
			restaurantes.add(res);
		}
		
		return restaurantes;
	}

	
	/**
	 * Método que obtiene el ID del siguiente Menú.
	 * @return Long, siguiente ID de Menus en la base de datos.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Long getSiguienteIdMenu()throws SQLException, Exception
	{
		String sql = "SELECT * FROM MENUS\r\n" + 
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
	

	public Menu registrarMenu(Long idRestaurante, Menu menu) throws SQLException, Exception
	{
		conn.setAutoCommit(false);
		System.out.println("ENTRO A METODO MENU DAO");
		Long idEntrada = menu.getEntrada().getId();
		Long idPlatoFuerte = menu.getPlatoFuerte().getId();
		Long idPostre = menu.getPostre().getId();
		Long idBebida = menu.getBebida().getId();
		Long idAcompaniamiento = menu.getAcompaniamiento().getId();
		if((idEntrada == null) && (idPlatoFuerte == null) && (idAcompaniamiento == null) && (idPostre == null) && (idBebida == null))
		{
			throw new Exception("Todos los valores están vacíos, por favor revisar la petición.");
		}
		System.out.println("Inicio comprobar Entrada");
		if(idEntrada != null)
		{
			String sql = "SELECT * \r\n" + 
					"    FROM PRODUCTOS, PRODUCTO_RESTAURANTE\r\n" + 
					"    WHERE PRODUCTOS.ID = PRODUCTO_RESTAURANTE.ID_PROD AND PRODUCTOS.ID = " + idEntrada +" AND PRODUCTO_RESTAURANTE.ID_REST = " + idRestaurante;
			PreparedStatement st = conn.prepareStatement(sql);
			recursos.add(st);
			ResultSet rs = st.executeQuery();
			if(rs.next())
			{
				if(!rs.getString("CATEGORIA").equals(Producto.ENTRADA))
				{
					throw new Exception("El producto en el campo de Entrada NO es una entrada, intentar con otro producto.");
				}
			}
			else
			{
				throw new Exception("La entrada que se introdujo por parámetro no existe.");
			}
		}
		System.out.println("Inicio fin comprobar Entrada");
		System.out.println("Inicio comprobar Plato Fuerte");
		if(idPlatoFuerte != null)
		{
			String sql = "SELECT * \r\n" + 
					"    FROM PRODUCTOS, PRODUCTO_RESTAURANTE\r\n" + 
					"    WHERE PRODUCTOS.ID = PRODUCTO_RESTAURANTE.ID_PROD AND PRODUCTOS.ID = " + idPlatoFuerte +" AND PRODUCTO_RESTAURANTE.ID_REST = " + idRestaurante;
			PreparedStatement st = conn.prepareStatement(sql);
			recursos.add(st);
			ResultSet rs = st.executeQuery();
			if(rs.next())
			{
				if(!rs.getString("CATEGORIA").equals(Producto.PLATOFUERTE))
				{
					throw new Exception("El producto en el campo de Plato Fuerte NO es un Plato Fuerte, intentar con otro producto.");
				}
			}
			else
			{
				throw new Exception("El Plato Fuerte que se introdujo por parámetro no existe.");
			}
		}
		System.out.println("Fin comprobar Plato Fuerte");
		System.out.println("Inicio comprobar Postre");
		if(idPostre != null)
		{
			String sql = "SELECT * \r\n" + 
					"    FROM PRODUCTOS, PRODUCTO_RESTAURANTE\r\n" + 
					"    WHERE PRODUCTOS.ID = PRODUCTO_RESTAURANTE.ID_PROD AND PRODUCTOS.ID = " + idPostre +" AND PRODUCTO_RESTAURANTE.ID_REST = " + idRestaurante;
			PreparedStatement st = conn.prepareStatement(sql);
			recursos.add(st);
			ResultSet rs = st.executeQuery();
			if(rs.next())
			{
				if(!rs.getString("CATEGORIA").equals(Producto.POSTRE))
				{
					throw new Exception("El producto en el campo de Postre NO es un Postre, intentar con otro producto.");
				}
			}
			else
			{
				throw new Exception("El Postre que se introdujo por parámetro no existe.");
			}
		}
		System.out.println("Fin comprobar Postre");
		System.out.println("Inicio comprobar Bebida");
		if(idBebida != null)
		{
			String sql = "SELECT * \r\n" + 
					"    FROM PRODUCTOS, PRODUCTO_RESTAURANTE\r\n" + 
					"    WHERE PRODUCTOS.ID = PRODUCTO_RESTAURANTE.ID_PROD AND PRODUCTOS.ID = " + idBebida +" AND PRODUCTO_RESTAURANTE.ID_REST = " + idRestaurante;
			PreparedStatement st = conn.prepareStatement(sql);
			recursos.add(st);
			ResultSet rs = st.executeQuery();
			if(rs.next())
			{
				if(!rs.getString("CATEGORIA").equals(Producto.BEBIDA))
				{
					throw new Exception("El producto en el campo de Bebida NO es una Bebida, intentar con otro producto.");
				}
			}
			else
			{
				throw new Exception("La Bebida que se introdujo por parámetro no existe.");
			}
		}
		System.out.println("Fin comprobar Bebida");
		System.out.println("Inicio comprobar Acompañamiento");
		if(idAcompaniamiento != null)
		{
			String sql = "SELECT * \r\n" + 
					"    FROM PRODUCTOS, PRODUCTO_RESTAURANTE\r\n" + 
					"    WHERE PRODUCTOS.ID = PRODUCTO_RESTAURANTE.ID_PROD AND PRODUCTOS.ID = " + idAcompaniamiento +" AND PRODUCTO_RESTAURANTE.ID_REST = " + idRestaurante;
			PreparedStatement st = conn.prepareStatement(sql);
			recursos.add(st);
			ResultSet rs = st.executeQuery();
			if(rs.next())
			{
				if(!rs.getString("CATEGORIA").equals(Producto.ACOMPANIAMIENTO))
				{
					throw new Exception("El producto en el campo de Acompañamiento NO es un Acompañamiento, intentar con otro producto.");
				}
			}
			else
			{
				throw new Exception("El Acompañamieno que se introdujo por parámetro no existe.");
			}
		}
		System.out.println("Fin comprobar Acompañamiento.");
		System.out.println("Generando SQL");
		Long idMenu = getSiguienteIdMenu();
		String sqlInsertarMenu = "INSERT INTO MENUS (ID, NAME, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_RESTAURANTE, ID_ACOMPANIAMIENTO)\r\n" + 
				"    VALUES(" + idMenu + ", '" + menu.getName() + "', " + idEntrada + ", " + idPlatoFuerte + ", " + idBebida + ", " + idPostre + ", " + idRestaurante + ", " + idAcompaniamiento + ")";
		System.out.println("SQL: " + sqlInsertarMenu);
		PreparedStatement stmtMenu = conn.prepareStatement(sqlInsertarMenu);
		recursos.add(stmtMenu);
		stmtMenu.executeQuery();
		System.out.println("FIN EJECUCCIÓN");
		menu.setId(idMenu);
		conn.setAutoCommit(true);
		return menu;
	}
	
	/**
	 * Método que obtiene el ID del sigueinte Restaurante.
	 * @return Long, siguiente ID de Restaurantes en la base de datos.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Long getSiguienteIdRestaurante()throws SQLException, Exception
	{
		String sql = "SELECT * FROM RESTAURANTES\r\n" + 
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
	
	/**
	 * Método que obtiene el ID del sigueinte Represenante.
	 * @return Long, siguiente ID de Representantes en la base de datos.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Long getSiguienteIdRepresentante()throws SQLException, Exception
	{
		String sql = "SELECT * FROM REPRESENTANTES\r\n" + 
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
	/**
	 * Método que agrega un restaurante. Para estas alturas, la existencia del Tipo del Restaurante y su zona ya deben haber sido comprobados.
	 * @param restaurante
	 * @param representante
	 * @param precio
	 * @param idZona
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Restaurante registrarRestaurante(Restaurante restaurante, Representante representante, Long idZona)throws SQLException, Exception
	{
		System.out.println("ENTRO METODO DAO REGISTAR RESTAURANTE.");
		//VERIFICAR VALIDEZ DE TIPO DE RESTAURANTE.
		String sqlTipo = "SELECT * FROM TIPOS WHERE ID = " + restaurante.getTipoRestaurante().getId();
		PreparedStatement prepStmtTipo= conn.prepareStatement(sqlTipo);
		recursos.add(prepStmtTipo);
		ResultSet rsTipos = prepStmtTipo.executeQuery();
		System.out.println("POST VERIFICACION TIPOS");
		if(!rsTipos.next())
		{
			throw new Exception("El Tipo con ID: " + rsTipos.getLong("ID") + " no existe.");
		}
		TipoComida tipo = new TipoComida(rsTipos.getLong("ID"), rsTipos.getString("NAME"));
		//OBTENER SIGUIENTES ID'S DISPONIBLES.
		Long idRestaurante = getSiguienteIdRestaurante();
		Long idRepresentante = getSiguienteIdRepresentante();
		//AGREGAR RESTAURANTE.
		String sqlRestaurante = "INSERT INTO RESTAURANTES (ID, NAME, PRECIO, ID_TIPO, ID_ZONA, PAGINA_WEB)\r\n" + 
				"    VALUES (" + idRestaurante + ", '"+ restaurante.getName() + "', " + restaurante.getPrecio() + ", " + restaurante.getTipoRestaurante().getId()+ ", " + idZona + " , '" + restaurante.getPagina() + "')";
		System.out.println("SQL Restaurante: "+sqlRestaurante);
		PreparedStatement prepStmtRestaurante= conn.prepareStatement(sqlRestaurante);
		recursos.add(prepStmtRestaurante);
		prepStmtRestaurante.executeQuery();
		System.out.println("POST INSERT RESTAURANTES");
		//AGREGAR REPRESENTANTE.
		String sqlRepresentante = "INSERT INTO REPRESENTANTES (ID, NAME, PASSWORD) VALUES (" + idRepresentante + ", '" +representante.getName() + "', '" + representante.getContrasenia() + "')";
		PreparedStatement prepStmtRepresentante= conn.prepareStatement(sqlRepresentante);
		recursos.add(prepStmtRepresentante);
		prepStmtRepresentante.executeQuery();
		
		String sqlRelacion = "INSERT INTO REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE )VALUES (" + idRestaurante + ", " + idRepresentante + ")";
		PreparedStatement prepStmtRelacion= conn.prepareStatement(sqlRelacion);
		recursos.add(prepStmtRelacion);
		prepStmtRelacion.executeQuery();
		
		System.out.println("POST INSERT REPRESENTANTES");
		restaurante.setId(idRestaurante);
		restaurante.setTipoRestaurante(tipo);
		return restaurante;
	}
}
