package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import vos.Menu;
import vos.Producto;
import vos.ProductoLocal;
import vos.ProductoBase;
import vos.RentabilidadRestaurante;
import vos.Representante;
import vos.Restaurante;
import vos.TipoComida;
import vos.Zona;

public class DAOTablaRestaurantes {
	
	public final static Integer NINGUNO = 0;
	public final static Integer CATEGORIA = 1;
	public final static Integer PRODUCTO = 2;
	public final static Integer ZONA = 3;
	
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
			Restaurante res = new Restaurante(rs.getLong("ID"), rs.getString("NAME"), rs.getString("PAGINA_WEB"), new ArrayList<ProductoLocal>(), new TipoComida(0L, new String("hola")), rs.getDouble("PRECIO"), rs.getBoolean("EN_OPERACION"));
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
		Long idEntrada = null;
		if(menu.getEntrada() != null)
		{
			idEntrada = menu.getEntrada().getId();
		}
		Long idPlatoFuerte = null;
		if(menu.getPlatoFuerte() != null)
		{
			menu.getPlatoFuerte().getId();
		}
		Long idPostre = null;
		if(menu.getPostre() != null)
		{
			idPostre = menu.getPostre().getId();
		}
		Long idBebida = null;
		if(menu.getBebida() != null)
		{
			idBebida = menu.getBebida().getId();
		}
		Long idAcompaniamiento = null;
		if(menu.getAcompaniamiento() != null)
		{
			idAcompaniamiento = menu.getAcompaniamiento().getId();
		}
		Double precio = menu.getPrecio();
		if((idEntrada == null) && (idPlatoFuerte == null) && (idAcompaniamiento == null) && (idPostre == null) && (idBebida == null))
		{
			throw new Exception("Todos los valores están vacíos, por favor revisar la petición.");
		}
//		System.out.println("Inicio comprobar Entrada");
//		if(idEntrada != null)
//		{
//			String sql = "SELECT * \r\n" + 
//					"    FROM PRODUCTOS, PRODUCTO_RESTAURANTE\r\n" + 
//					"    WHERE PRODUCTOS.ID = PRODUCTO_RESTAURANTE.ID_PROD AND PRODUCTOS.ID = " + idEntrada +" AND PRODUCTO_RESTAURANTE.ID_REST = " + idRestaurante;
//			PreparedStatement st = conn.prepareStatement(sql);
//			recursos.add(st);
//			ResultSet rs = st.executeQuery();
//			if(rs.next())
//			{
//				if(!rs.getString("CATEGORIA").equals(Producto.ENTRADA))
//				{
//					throw new Exception("El producto en el campo de Entrada NO es una entrada, intentar con otro producto.");
//				}
//			}
//			else
//			{
//				throw new Exception("La entrada que se introdujo por parámetro no existe.");
//			}
//		}
//		System.out.println("Inicio fin comprobar Entrada");
//		System.out.println("Inicio comprobar Plato Fuerte");
//		if(idPlatoFuerte != null)
//		{
//			String sql = "SELECT * \r\n" + 
//					"    FROM PRODUCTOS, PRODUCTO_RESTAURANTE\r\n" + 
//					"    WHERE PRODUCTOS.ID = PRODUCTO_RESTAURANTE.ID_PROD AND PRODUCTOS.ID = " + idPlatoFuerte +" AND PRODUCTO_RESTAURANTE.ID_REST = " + idRestaurante;
//			PreparedStatement st = conn.prepareStatement(sql);
//			recursos.add(st);
//			ResultSet rs = st.executeQuery();
//			if(rs.next())
//			{
//				if(!rs.getString("CATEGORIA").equals(Producto.PLATOFUERTE))
//				{
//					throw new Exception("El producto en el campo de Plato Fuerte NO es un Plato Fuerte, intentar con otro producto.");
//				}
//			}
//			else
//			{
//				throw new Exception("El Plato Fuerte que se introdujo por parámetro no existe.");
//			}
//		}
//		System.out.println("Fin comprobar Plato Fuerte");
//		System.out.println("Inicio comprobar Postre");
//		if(idPostre != null)
//		{
//			String sql = "SELECT * \r\n" + 
//					"    FROM PRODUCTOS, PRODUCTO_RESTAURANTE\r\n" + 
//					"    WHERE PRODUCTOS.ID = PRODUCTO_RESTAURANTE.ID_PROD AND PRODUCTOS.ID = " + idPostre +" AND PRODUCTO_RESTAURANTE.ID_REST = " + idRestaurante;
//			PreparedStatement st = conn.prepareStatement(sql);
//			recursos.add(st);
//			ResultSet rs = st.executeQuery();
//			if(rs.next())
//			{
//				if(!rs.getString("CATEGORIA").equals(Producto.POSTRE))
//				{
//					throw new Exception("El producto en el campo de Postre NO es un Postre, intentar con otro producto.");
//				}
//			}
//			else
//			{
//				throw new Exception("El Postre que se introdujo por parámetro no existe.");
//			}
//		}
//		System.out.println("Fin comprobar Postre");
//		System.out.println("Inicio comprobar Bebida");
//		if(idBebida != null)
//		{
//			String sql = "SELECT * \r\n" + 
//					"    FROM PRODUCTOS, PRODUCTO_RESTAURANTE\r\n" + 
//					"    WHERE PRODUCTOS.ID = PRODUCTO_RESTAURANTE.ID_PROD AND PRODUCTOS.ID = " + idBebida +" AND PRODUCTO_RESTAURANTE.ID_REST = " + idRestaurante;
//			PreparedStatement st = conn.prepareStatement(sql);
//			recursos.add(st);
//			ResultSet rs = st.executeQuery();
//			if(rs.next())
//			{
//				if(!rs.getString("CATEGORIA").equals(Producto.BEBIDA))
//				{
//					throw new Exception("El producto en el campo de Bebida NO es una Bebida, intentar con otro producto.");
//				}
//			}
//			else
//			{
//				throw new Exception("La Bebida que se introdujo por parámetro no existe.");
//			}
//		}
//		System.out.println("Fin comprobar Bebida");
//		System.out.println("Inicio comprobar Acompañamiento");
//		if(idAcompaniamiento != null)
//		{
//			String sql = "SELECT * \r\n" + 
//					"    FROM PRODUCTOS, PRODUCTO_RESTAURANTE\r\n" + 
//					"    WHERE PRODUCTOS.ID = PRODUCTO_RESTAURANTE.ID_PROD AND PRODUCTOS.ID = " + idAcompaniamiento +" AND PRODUCTO_RESTAURANTE.ID_REST = " + idRestaurante;
//			PreparedStatement st = conn.prepareStatement(sql);
//			recursos.add(st);
//			ResultSet rs = st.executeQuery();
//			if(rs.next())
//			{
//				if(!rs.getString("CATEGORIA").equals(Producto.ACOMPANIAMIENTO))
//				{
//					throw new Exception("El producto en el campo de Acompañamiento NO es un Acompañamiento, intentar con otro producto.");
//				}
//			}
//			else
//			{
//				throw new Exception("El Acompañamieno que se introdujo por parámetro no existe.");
//			}
//		}
//		System.out.println("Fin comprobar Acompañamiento.");
		System.out.println("Generando SQL");
//		Long idMenu = getSiguienteIdMenu();
		Long idMenu = menu.getId();
		String sqlInsertarMenu = "INSERT INTO MENUS (ID, NAME, ID_RESTAURANTE, PRECIO, ID_ENTRADA, ID_PLATOFUERTE, ID_POSTRE, ID_BEBIDA, ID_ACOMPANIAMIENTO)\r\n" + 
				"    VALUES(" + idMenu + ", '" + menu.getName() + "', " + idRestaurante + ", " + precio + ", " + idEntrada + ", " + idPlatoFuerte + ", " + idBebida + ", " + idPostre + ", " + idAcompaniamiento + ")";
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
//		Long idRestaurante = getSiguienteIdRestaurante();
		Long idRestaurante = restaurante.getId();
//		Long idRepresentante = getSiguienteIdRepresentante();
		//AGREGAR RESTAURANTE.
		Integer estadoOperacion = 1;
		if(!restaurante.getEstadoOperacion())
		{
			estadoOperacion = 0;
		}
		String sqlRestaurante = "INSERT INTO RESTAURANTES (ID, NAME, PRECIO, ID_TIPO, ID_ZONA, PAGINA_WEB, EN_OPERACION)\r\n" + 
				"    VALUES (" + idRestaurante + ", '"+ restaurante.getName() + "', " + restaurante.getPrecio() + ", " + restaurante.getTipoRestaurante().getId()+ ", " + idZona + " , '" + restaurante.getPagina() + "', " + estadoOperacion + ")";
		System.out.println("SQL Restaurante: "+sqlRestaurante);
		PreparedStatement prepStmtRestaurante= conn.prepareStatement(sqlRestaurante);
		recursos.add(prepStmtRestaurante);
		prepStmtRestaurante.executeQuery();
		System.out.println("POST INSERT RESTAURANTES");
		//AGREGAR REPRESENTANTE.
//		String sqlRepresentante = "INSERT INTO REPRESENTANTES (ID, NAME, PASSWORD) VALUES (" + idRepresentante + ", '" +representante.getName() + "', '" + representante.getContrasenia() + "')";
//		PreparedStatement prepStmtRepresentante= conn.prepareStatement(sqlRepresentante);
//		recursos.add(prepStmtRepresentante);
//		prepStmtRepresentante.executeQuery();
//		
//		String sqlRelacion = "INSERT INTO REPRESENTA (ID_RESTAURANTE, ID_REPRESENTANTE )VALUES (" + idRestaurante + ", " + idRepresentante + ")";
//		PreparedStatement prepStmtRelacion= conn.prepareStatement(sqlRelacion);
//		recursos.add(prepStmtRelacion);
//		prepStmtRelacion.executeQuery();
//		
//		System.out.println("POST INSERT REPRESENTANTES");
		restaurante.setId(idRestaurante);
		restaurante.setTipoRestaurante(tipo);
		return restaurante;
	}
	/**
	 * Método que obtiene la información básica de un Restaurante dado un ID.
	 * @param id Long, ID del Restaurante
	 * @return Restaurante, Información básica de un Restaurante.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Restaurante obtenerRestaurante(Long id) throws SQLException, Exception
	{
		String sql = "SELECT R.ID, R.NAME, R.PRECIO, R.PAGINA_WEB, R.EN_OPERACION AS EN_OPERACION, T.ID AS IDTIPO, T.NAME AS NAMETIPO\r\n" + 
				"    FROM RESTAURANTES R, TIPOS T WHERE T.ID = R.ID_TIPO AND R.ID = " + id;
		PreparedStatement st = conn.prepareStatement(sql);
		recursos.add(st);
		ResultSet rs = st.executeQuery();
		Restaurante restaurante = null;
		if (rs.next())
		{
			String name = rs.getString("NAME");
			String pagina = rs.getString("PAGINA_WEB");
			Double precio = rs.getDouble("PRECIO");
			TipoComida tipo = new TipoComida(rs.getLong("IDTIPO"), rs.getString("NAMETIPO"));
			List<ProductoLocal> productos = new ArrayList<ProductoLocal>();
			Boolean enOperacion = true;
			if(rs.getInt("EN_OPERACION") == 0)
			{
				enOperacion = false;
			}
			restaurante = new Restaurante(id, name, pagina, productos, tipo, precio, enOperacion);
		}
		
		return restaurante;
	}
	/**
	 * Método que indica si el Restaurante está en operación o no.
	 * @param idRestaurante Long, ID del Restaurante a consultar.
	 * @return Boolean, Booleano que indica si el Restaurante está en Operación o no.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean darEstadoOperacionRestaurante(Long idRestaurante)throws SQLException, Exception
	{
		String sql = "SELECT EN_OPERACION\r\n" + 
				"    FROM RESTAURANTES WHERE ID = " + idRestaurante;
		PreparedStatement st = conn.prepareStatement(sql);
		recursos.add(st);
		ResultSet rs = st.executeQuery();
		if(rs.next())
		{
			return rs.getBoolean("EN_OPERACION");
		}
		else
		{
			throw new Exception("Restaurante no existe");
		}
	}
	/**
	 * Método que indica si el Restaurante tiene algún Pedido pendiente por entregar.
	 * Si el Restaurante tiene algún Pedido pendiente, todavía no se puede retirar.
	 * Si el Restaurante no tiene ningún Pedido pendiente, se puede retirar.
	 * @param idRestaurante Long, ID del Restaurante.
	 * @return Boolean, Booleano que determina si el Restaurante tiene Pedidos pendientes por entregar o no.
	 * @throws SQLException
	 * @throws Exception
	 */
	public Boolean darEstadoOrdenesRestaurante(Long idRestaurante)throws SQLException, Exception
	{
		String sql = "SELECT * FROM PEDIDOS WHERE SERVIDO = 0 AND ID_RESTAURANTE =" + idRestaurante;
		PreparedStatement st = conn.prepareStatement(sql);
		recursos.add(st);
		ResultSet rs = st.executeQuery();
		if(rs.next())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/**
	 * Método que establece que un Restaurante ya no está en servicio.
	 * @param idRestaurante Long, ID del Restaurante a retirar del Servicio.
	 * @throws SQLException
	 * @throws Exception
	 */
	public void retirarRestaurante(Long idRestaurante)throws SQLException, Exception
	{
		String sql = "UPDATE RESTAURANTES SET EN_OPERACION = 0 WHERE ID = " + idRestaurante;
		System.out.println(sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	/**
	 * Método que obtiene la Rentabilidad de los Restaurantes, puede incluir un criterio de búsqueda.
	 * @param fecha1 String, cadena de la primera fecha del rango de consulta.
	 * @param fecha2 String, cadena de la segunda fecha del rango de consulta.
	 * @param criterio Integer, Criterio según el cual se va a realizar la consulta.
	 * @param idProducto Long, ID del Producto según el cual se va a realizar la consulta, si aplica.
	 * @return List<RentabilidadRestaurante>, Lista con las Rentabilidades de todos los Restaurantes en el Rango de búsqueda.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<RentabilidadRestaurante> darRentabilidadDeRestaurantes(String fecha1, String fecha2, Integer criterio, Long idProducto)throws SQLException, Exception
	{
		List<RentabilidadRestaurante> respuesta = new ArrayList<RentabilidadRestaurante>();
		String sql = "";
		if(criterio.equals(CATEGORIA))
		{
			sql = "SELECT ID_REST, CATEGORIA, SUM(PRECIO) AS INGRESOS, SUM(COSTO_PRODUCCION) AS GASTOS, COUNT(PED.ID) AS NUMPEDIDOSTOTAL\r\n" + 
					"    FROM PEDIDOS PED, PRODUCTO_RESTAURANTE PRODREST\r\n" + 
					"    INNER JOIN PRODUCTOS PROD ON (PRODREST.ID_PROD = PROD.ID)\r\n" + 
					"    WHERE (PED.ID_PRODUCTO = PRODREST.ID_PROD AND PED.ID_RESTAURANTE = PRODREST.ID_REST) AND FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND FECHA <= '" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"    GROUP BY ID_REST, CATEGORIA\r\n" + 
					"    ORDER BY INGRESOS DESC";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				
				Double rentabilidad = (rs.getDouble("INGRESOS") - rs.getDouble("GASTOS"));
				Restaurante restaurante = obtenerRestaurante(rs.getLong("ID_REST"));
				String categoria = rs.getString("CATEGORIA");
				Integer cantidadPedidos = rs.getInt("NUMPEDIDOSTOTAL");
				Double ingresos = rs.getDouble("INGRESOS");
				Double gastos = rs.getDouble("GASTOS");
				respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, restaurante, categoria, null, null));
			}
		}
		else if(criterio.equals(PRODUCTO))
		{
			if(idProducto == null)
			{
				sql = "SELECT ID_REST, PRODREST.ID_PROD, SUM(PRECIO) AS INGRESOS, SUM(COSTO_PRODUCCION) AS GASTOS, COUNT(PED.ID) AS NUMPEDIDOSTOTAL\r\n" + 
						"    FROM PEDIDOS PED, PRODUCTO_RESTAURANTE PRODREST\r\n" + 
						"    INNER JOIN PRODUCTOS PROD ON (PRODREST.ID_PROD = PROD.ID)\r\n" + 
						"    WHERE (PED.ID_PRODUCTO = PRODREST.ID_PROD AND PED.ID_RESTAURANTE = PRODREST.ID_REST) AND FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND FECHA <= '" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
						"    GROUP BY ID_REST, PRODREST.ID_PROD\r\n" + 
						"    ORDER BY INGRESOS DESC";
				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				ResultSet rs = prepStmt.executeQuery();
				while(rs.next())
				{
					
					Double rentabilidad = (rs.getDouble("INGRESOS") - rs.getDouble("GASTOS"));
					Restaurante restaurante = obtenerRestaurante(rs.getLong("ID_REST"));
					Producto producto = darProducto(rs.getLong("ID_PROD"), rs.getLong("ID_REST"));
					Integer cantidadPedidos = rs.getInt("NUMPEDIDOSTOTAL");
					Double ingresos = rs.getDouble("INGRESOS");
					Double gastos = rs.getDouble("GASTOS");
					respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, restaurante, null, producto, null));
				}
			}
			else
			{
				sql = "SELECT ID_REST, PRODREST.ID_PROD, SUM(PRECIO) AS INGRESOS, SUM(COSTO_PRODUCCION) AS GASTOS, COUNT(PED.ID) AS NUMPEDIDOSTOTAL\r\n" + 
						"    FROM PEDIDOS PED, PRODUCTO_RESTAURANTE PRODREST\r\n" + 
						"    INNER JOIN PRODUCTOS PROD ON (PRODREST.ID_PROD = PROD.ID)\r\n" + 
						"    WHERE (PED.ID_PRODUCTO = PRODREST.ID_PROD AND PED.ID_RESTAURANTE = PRODREST.ID_REST) AND FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND FECHA <= '" + fecha2 + " 12:59:59, 000000000 PM' AND PROD.ID = " + idProducto + "\r\n" + 
						"    GROUP BY ID_REST, PRODREST.ID_PROD\r\n" + 
						"    ORDER BY INGRESOS DESC";
				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				ResultSet rs = prepStmt.executeQuery();
				while(rs.next())
				{
					
					Double rentabilidad = (rs.getDouble("INGRESOS") - rs.getDouble("GASTOS"));
					Restaurante restaurante = obtenerRestaurante(rs.getLong("ID_REST"));
					Producto producto = darProducto(rs.getLong("ID_PROD"), rs.getLong("ID_REST"));
					Integer cantidadPedidos = rs.getInt("NUMPEDIDOSTOTAL");
					Double ingresos = rs.getDouble("INGRESOS");
					Double gastos = rs.getDouble("GASTOS");
					respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, restaurante, null, producto, null));
				}
			}
		}
		else if(criterio.equals(ZONA))
		{
			sql = "SELECT RESTAURANTE.ID_ZONA AS ZONA, SUM(PRODREST.PRECIO) AS INGRESOS, SUM(COSTO_PRODUCCION) AS GASTOS, COUNT(PED.ID) AS NUMPEDIDOSTOTAL\r\n" + 
					"    FROM PEDIDOS PED, RESTAURANTES RESTAURANTE, PRODUCTO_RESTAURANTE PRODREST\r\n" + 
					"    INNER JOIN PRODUCTOS PROD ON (PRODREST.ID_PROD = PROD.ID)\r\n" + 
					"    WHERE (RESTAURANTE.ID = PED.ID_RESTAURANTE AND RESTAURANTE.ID = PRODREST.ID_REST AND PED.ID_PRODUCTO = PRODREST.ID_PROD AND PED.ID_RESTAURANTE = PRODREST.ID_REST) AND FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND FECHA <= '" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"    GROUP BY RESTAURANTE.ID_ZONA\r\n" + 
					"    ORDER BY INGRESOS DESC";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				
				Double rentabilidad = (rs.getDouble("INGRESOS") - rs.getDouble("GASTOS"));
				
				Integer cantidadPedidos = rs.getInt("NUMPEDIDOSTOTAL");
				Double ingresos = rs.getDouble("INGRESOS");
				Double gastos = rs.getDouble("GASTOS");
				Zona zona = darZona(rs.getLong("ZONA"));
				respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, null, null, null, zona));
			}
		}
		else
		{
			sql = "SELECT ID_REST, SUM(PRECIO) AS INGRESOS, SUM(COSTO_PRODUCCION) AS GASTOS, COUNT(PED.ID) AS NUMPEDIDOSTOTAL\r\n" + 
					"    FROM PEDIDOS PED, PRODUCTO_RESTAURANTE PRODREST\r\n" + 
					"    INNER JOIN PRODUCTOS PROD ON (PRODREST.ID_PROD = PROD.ID)\r\n" + 
					"    WHERE (PED.ID_PRODUCTO = PRODREST.ID_PROD AND PED.ID_RESTAURANTE = PRODREST.ID_REST) AND FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND FECHA <= '" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"    GROUP BY ID_REST\r\n" + 
					"    ORDER BY INGRESOS DESC";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				
				Double rentabilidad = (rs.getDouble("INGRESOS") - rs.getDouble("GASTOS"));
				Restaurante restaurante = obtenerRestaurante(rs.getLong("ID_REST"));
				Integer cantidadPedidos = rs.getInt("NUMPEDIDOSTOTAL");
				Double ingresos = rs.getDouble("INGRESOS");
				Double gastos = rs.getDouble("GASTOS");
				respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, restaurante, null, null, null));
			}
		}
		return respuesta;
	}
	
	/**
	 * Método que obtiene la Rentabilidad de un Restaurante específico.
	 * @param fecha1 String, cadena de la primera fecha del rango de consulta.
	 * @param fecha2 String, cadena de la segunda fecha del rango de consulta.
	 * @param criterio Integer, Criterio según el cual se va a realizar la consulta.
	 * @param idProducto Long, ID del Producto según el cual se va a realizar la consulta, si aplica.
	 * @param idRestaurante Long, ID del Restaurante a consultar.
	 * @return List<RentabilidadRestaurante>, Lista con las Rentabilidades del Restaurante consultado en el rango de búsqueda.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<RentabilidadRestaurante> darRentabilidadDeRestaurante(String fecha1, String fecha2, Integer criterio, Long idProducto, Long idRestaurante)throws SQLException, Exception
	{
		List<RentabilidadRestaurante> respuesta = new ArrayList<RentabilidadRestaurante>();
		String sql = "";
		if(criterio.equals(CATEGORIA))
		{
			sql = "SELECT ID_REST, CATEGORIA, SUM(PRECIO) AS INGRESOS, SUM(COSTO_PRODUCCION) AS GASTOS, COUNT(PED.ID) AS NUMPEDIDOSTOTAL\r\n" + 
					"    FROM PEDIDOS PED, PRODUCTO_RESTAURANTE PRODREST\r\n" + 
					"    INNER JOIN PRODUCTOS PROD ON (PRODREST.ID_PROD = PROD.ID)\r\n" + 
					"    WHERE (PED.ID_PRODUCTO = PRODREST.ID_PROD AND PED.ID_RESTAURANTE = PRODREST.ID_REST) AND PED.ID_RESTAURANTE = " + idRestaurante + " AND FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND FECHA <= '" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"    GROUP BY ID_REST, CATEGORIA\r\n" + 
					"    ORDER BY INGRESOS DESC";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				
				Double rentabilidad = (rs.getDouble("INGRESOS") - rs.getDouble("GASTOS"));
				Restaurante restaurante = obtenerRestaurante(rs.getLong("ID_REST"));
				String categoria = rs.getString("CATEGORIA");
				Integer cantidadPedidos = rs.getInt("NUMPEDIDOSTOTAL");
				Double ingresos = rs.getDouble("INGRESOS");
				Double gastos = rs.getDouble("GASTOS");
				respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, restaurante, categoria, null, null));
			}
		}
		else if(criterio.equals(PRODUCTO))
		{
			if(idProducto == null)
			{
				sql = "SELECT ID_REST, PRODREST.ID_PROD, SUM(PRECIO) AS INGRESOS, SUM(COSTO_PRODUCCION) AS GASTOS, COUNT(PED.ID) AS NUMPEDIDOSTOTAL\r\n" + 
						"    FROM PEDIDOS PED, PRODUCTO_RESTAURANTE PRODREST\r\n" + 
						"    INNER JOIN PRODUCTOS PROD ON (PRODREST.ID_PROD = PROD.ID)\r\n" + 
						"    WHERE (PED.ID_PRODUCTO = PRODREST.ID_PROD AND PED.ID_RESTAURANTE = PRODREST.ID_REST) AND PED.ID_RESTAURANTE = " + idRestaurante + " AND FECHA >= '" + fecha1 + "01:00:00, 000000000 AM' AND FECHA <= '" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
						"    GROUP BY ID_REST, PRODREST.ID_PROD\r\n" + 
						"    ORDER BY INGRESOS DESC";
				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				ResultSet rs = prepStmt.executeQuery();
				while(rs.next())
				{
					
					Double rentabilidad = (rs.getDouble("INGRESOS") - rs.getDouble("GASTOS"));
					Restaurante restaurante = obtenerRestaurante(rs.getLong("ID_REST"));
					Producto producto = darProducto(rs.getLong("ID_PROD"), rs.getLong("ID_REST"));
					Integer cantidadPedidos = rs.getInt("NUMPEDIDOSTOTAL");
					Double ingresos = rs.getDouble("INGRESOS");
					Double gastos = rs.getDouble("GASTOS");
					respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, restaurante, null, producto, null));
				}
			}
			else
			{
				sql = "SELECT ID_REST, PRODREST.ID_PROD, SUM(PRECIO) AS INGRESOS, SUM(COSTO_PRODUCCION) AS GASTOS, COUNT(PED.ID) AS NUMPEDIDOSTOTAL\r\n" + 
						"    FROM PEDIDOS PED, PRODUCTO_RESTAURANTE PRODREST\r\n" + 
						"    INNER JOIN PRODUCTOS PROD ON (PRODREST.ID_PROD = PROD.ID)\r\n" + 
						"    WHERE (PED.ID_PRODUCTO = PRODREST.ID_PROD AND PED.ID_RESTAURANTE = PRODREST.ID_REST) AND PED.ID_RESTAURANTE = " + idRestaurante + " AND FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND FECHA <= '" + fecha2 + " 12:59:59, 000000000 PM' AND PROD.ID = " + idProducto + "\r\n" + 
						"    GROUP BY ID_REST, PRODREST.ID_PROD\r\n" + 
						"    ORDER BY INGRESOS DESC";
				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				ResultSet rs = prepStmt.executeQuery();
				while(rs.next())
				{
					
					Double rentabilidad = (rs.getDouble("INGRESOS") - rs.getDouble("GASTOS"));
					Restaurante restaurante = obtenerRestaurante(rs.getLong("ID_REST"));
					Producto producto = darProducto(rs.getLong("ID_PROD"), rs.getLong("ID_REST"));
					Integer cantidadPedidos = rs.getInt("NUMPEDIDOSTOTAL");
					Double ingresos = rs.getDouble("INGRESOS");
					Double gastos = rs.getDouble("GASTOS");
					respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, restaurante, null, producto, null));
				}
			}
		}
		else if(criterio.equals(ZONA))
		{
			
		}
		else
		{
			sql = "SELECT ID_REST, SUM(PRECIO) AS INGRESOS, SUM(COSTO_PRODUCCION) AS GASTOS, COUNT(PED.ID) AS NUMPEDIDOSTOTAL\r\n" + 
					"    FROM PEDIDOS PED, PRODUCTO_RESTAURANTE PRODREST\r\n" + 
					"    INNER JOIN PRODUCTOS PROD ON (PRODREST.ID_PROD = PROD.ID)\r\n" + 
					"    WHERE (PED.ID_PRODUCTO = PRODREST.ID_PROD AND PED.ID_RESTAURANTE = PRODREST.ID_REST) AND PED.ID_RESTAURANTE = " + idRestaurante + " AND FECHA >= '" + fecha1 + " 01:00:00, 000000000 AM' AND FECHA <= '" + fecha2 + " 12:59:59, 000000000 PM'\r\n" + 
					"    GROUP BY ID_REST\r\n" + 
					"    ORDER BY INGRESOS DESC";
//			System.out.println(sql);
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				
				Double rentabilidad = (rs.getDouble("INGRESOS") - rs.getDouble("GASTOS"));
				Restaurante restaurante = obtenerRestaurante(rs.getLong("ID_REST"));
				Integer cantidadPedidos = rs.getInt("NUMPEDIDOSTOTAL");
				Double ingresos = rs.getDouble("INGRESOS");
				Double gastos = rs.getDouble("GASTOS");
				respuesta.add(new RentabilidadRestaurante(ingresos, gastos, rentabilidad, cantidadPedidos, restaurante, null, null, null));
			}
		}
		System.out.println(sql);
		return respuesta;
	}
	
	public Zona darZona(Long id)  throws SQLException, Exception{

		Zona zona = null;

		String sql = "SELECT * FROM ZONAS WHERE ID = " + id;
		PreparedStatement st = conn.prepareStatement(sql);
		recursos.add(st);

		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			zona = new Zona();

			zona.setId(rs.getLong("ID"));
			zona.setNombre(rs.getString("NAME"));
			zona.setEsEspacioAbierto(rs.getBoolean("ESPACIO_ABIERTO"));
			zona.setCapacidad(rs.getInt("CAPACIDAD"));;
			zona.setEsIncluyente(rs.getBoolean("INCLUYENTE"));
		}
		return zona;
	}
	
	
	public ProductoLocal darProductoLocal(Long id, Long idRest) throws SQLException, Exception {
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
			producto.setProductosEquivalentes(darProductosEquivalentes(producto.getId(), idRest));
			producto.setCantidad(rs.getInt("CANTIDAD"));
			producto.setTiposComida(tipos);
			return producto;
		}
		return null;
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
}
