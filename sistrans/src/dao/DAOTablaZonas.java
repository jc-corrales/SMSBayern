package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Zona;
import vos.Producto;
import vos.Restaurante;

/**
 * Clase que administra las reglas de funcionamiento de una Zona.
 * @author Usuario
 *
 */
public class DAOTablaZonas
{
	/**
	 * Constante que contiene si la solicitud es respecto a una condición técnica.
	 */
	public final static Integer CONDICIONTECNICA = 0;
	/**
	 * Constante que contiene si la solicitud es respecto a un Restaurante.
	 */
	public final static Integer CONTAINSRESTAURANTE = 1 ;
	/**
	 * Constante que contiene si la solicitud es respecto a un Producto en la zona.
	 */
	public final static Integer CONTAINSPRODUCTO = 2;
	/**
	 * Constante que contiene si la solicitud es respecto al nombre de la zona.
	 */
	public final static Integer ZONA = 3;
	/**
	 * Constante que contiene si la solicitud respecto a si es espacio abierto o no
	 */
	public final static Integer ESESPACIOABIERTO = 4;
	/**
	 * Constante que contiene si la solicitud respecto a si es incluyente o no
	 */
	public final static Integer ESINCLUYENTE = 5;
	/**
	 * Constante que contiene si la solicitud es alrededor de la capacidad. Puede haber capacidad, o rango de capacidades.
	 */
	public final static Integer CAPACIDAD = 6;
	
	/**
	 * Arraylits de recursos que se usan para la ejecuciÃ³n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexiÃ³n a la base de datos
	 */
	private Connection conn;

	/**
	 * MÃ©todo constructor que crea DAOTablaReservasCarga
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public  DAOTablaZonas()
	{
		recursos = new ArrayList<Object>();
	}

	/**
	 * MÃ©todo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos()
	{
		for (Object ob : recursos)
		{
			if (ob instanceof PreparedStatement)
				try
				{
					((PreparedStatement) ob).close();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
		}
	}
	

	/**
	 * MÃ©todo que inicializa la connection del DAO a la base de datos con la conexiÃ³n que entra como parÃ¡metro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con)
	{
		this.conn = con;
	}
	/**
	 * Método que agrega una zona.
	 * @param zona Zona con una lista de Restaurantes, los cuales todos están en la base de datos.
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addZona(Zona zona) throws SQLException, Exception
	{
//		List<Restaurante> restaurantes = zona.getRestaurantes();
//		//ZONA DE VALIDACIÓN DE REGLAS DE NEGOCIO
//		for(int i = 0; i < restaurantes.size(); i++)
//		{
//			String sqlValidacionRestaurantesNombre = "SELECT ID, NAME FROM RESTAURANTES WHERE NAME = " + restaurantes.get(i).getName();
//			String sqlValidacionRestaurantesId = "SELECT ID, NAME FROM RESTAURANTES WHERE ID = " + restaurantes.get(i).getId();
//			
//			PreparedStatement prepStmtNombre = conn.prepareStatement(sqlValidacionRestaurantesNombre);
//			recursos.add(prepStmtNombre);
//			ResultSet rsNombre = prepStmtNombre.executeQuery();
//			
//			PreparedStatement prepStmtIds = conn.prepareStatement(sqlValidacionRestaurantesId);
//			recursos.add(prepStmtIds);
//			ResultSet rsIds = prepStmtIds.executeQuery();
//			if(rsIds.getFetchSize()==0)
//			{
//				throw new Exception("El restaurante con ID: " + restaurantes.get(i).getId() + " no existe." );
//			}
//			if(rsNombre.getFetchSize()==0)
//			{
//				throw new Exception("El restaurante con Nombre: " + restaurantes.get(i).getName() + " no existe." );
//			}
////			while(rsNombre.next() && rsIds.next())
////			{
////				if(!rsNombre.getString("NAME").equals(rsIds.getString("NAME")) || (rsNombre.getInt("ID") != rsIds.getInt("ID")) )
////				{
////					throw new Exception("El restaurante con ID: " + restaurantes.get(i).getId() + " y nombre: " + restaurantes.get(i).getName() + " tiene información que no concuerda.");
////				}
////			}
//			
//		}
		String sqlZonaConID = "SELECT * FROM ZONAS WHERE ID = " + zona.getId();
		PreparedStatement prepStmtID= conn.prepareStatement(sqlZonaConID);
		recursos.add(prepStmtID);
		ResultSet rsID = prepStmtID.executeQuery();
		
		if(rsID.getFetchSize() > 0)
		{
			throw new Exception ("Zona con ID dado ya existe.");
		}
		
		
		//ZONA EN QUE SE AGREGA LA ZONA
		String sql = "INSERT INTO ZONAS VALUES (";
		sql += zona.getId() + ",'";
		sql += zona.getNombre() + "',";
		if(zona.getEsEspacioAbierto())
		{
			sql += 1 + ",";
		}
		else
		{
			sql +=0 + ",";
		}
		sql += zona.getCapacidad()+ ",";
		if(zona.getEsIncluyente())
		{
			sql += 1 + ",";
		}
		else
		{
			sql +=0 + ",";
		}
		sql += ");";

		
		if(zona.getCondiciones() != null)
		{
			String sqlCondiciones = "SELECT * FROM CONDICIONESTECNICAS";
			
			PreparedStatement prepStmtCondiciones = conn.prepareStatement(sqlCondiciones);
			recursos.add(prepStmtCondiciones);
			ResultSet rsCondiciones = prepStmtCondiciones.executeQuery();
			List<String> condiciones = zona.getCondiciones();
			
			for(int i = 0; i < zona.getCondiciones().size(); i++)
			{
				while(rsCondiciones.next())
				{
					Long idCondicion = rsCondiciones.getLong("ID");
					String condicion = rsCondiciones.getString("NAME");
					if(condiciones.get(i).equals(condicion))
					{
						sql += ("\n" + "INSERT INTO CONDICIONZONA VALUES (" + zona.getId() +", "+ condicion +");" );
					}
				}
			}
		}

//		if(restaurantes != null)
//		{
//			for(int i = 0; i < restaurantes.size(); i++)
//			{
//				sql += "\n" + "UPDATE RESTAURANTES SET ID_ZONA = " + zona.getId() + "WHERE ID = " + restaurantes.get(i).getId() + ";";
//			}
//		}
		
		System.out.println("SQL statement:" + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);

		prepStmt.executeQuery();
	}
	
	/**
	 * Método que obtiene la lista de zonas sin ningún parámetro en específico.
	 * @return List<Zona> lista de Zonas.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Zona> getZonasSinParametros()throws SQLException, Exception
	{
		String sqlZonasMain = "SELECT * FROM ZONAS";
		
		PreparedStatement prepStmtZonasMain= conn.prepareStatement(sqlZonasMain);
		recursos.add(prepStmtZonasMain);
		ResultSet rsMain = prepStmtZonasMain.executeQuery();
		
		List<Zona> zonas = new ArrayList<Zona>();
		
		while(rsMain.next())
		{
			Long id = rsMain.getLong("ID");
			String nombre = rsMain.getString("NAME");
			Boolean esEspacioAbierto = rsMain.getBoolean("ESPACIO_ABIERTO");
			Integer capacidad = rsMain.getInt("CAPACIDAD");
			Boolean esIncluyente = rsMain.getBoolean("INCLUYENTE");
			
			String sqlCondiciones = "SELECT * FROM CONDICIONESTECNICAS , CONDICIONZONA  WHERE ID_ZONA = " + id + "AND ID_CONDICION = ID ORDER BY ID ASC";
			System.out.println(sqlCondiciones);
			PreparedStatement prepStmtCondiciones= conn.prepareStatement(sqlCondiciones);
			recursos.add(prepStmtCondiciones);
			ResultSet rsCondiciones = prepStmtCondiciones.executeQuery();
			
			List<String> condiciones = new ArrayList<String>();
			while(rsCondiciones.next())
			{
				String condicion = rsCondiciones.getString("NAME");
				condiciones.add(condicion);
			}
			Zona newZona = new Zona(id, nombre, esEspacioAbierto, capacidad, esIncluyente, condiciones, null);
			
			
//			String sqlRestaurantes = "SELECT * FROM RESTAURANTES WHERE ID_ZONA = " + id + " ";
//			PreparedStatement prepStmtRestaurantes= conn.prepareStatement(sqlRestaurantes);
//			recursos.add(prepStmtRestaurantes);
//			ResultSet rsRestaurantes = prepStmtRestaurantes.executeQuery();
//			
//			List<Restaurante> restuarantes = new ArrayList<Restaurante>();
//			while(rsRestaurantes.next())
//			{
//				Long idRestaurante = rsRestaurantes.getLong("ID");
//				String nameRestaurante = rsRestaurantes.getString("NAME");
//				String paginaRestaurante = rsRestaurantes.getString("PAGINA_WEB");
//				
//				String sqlTipoRestaurantes = "SELECT T.NAME FROM RESTAURANTES R, TIPOS T WHERE R.ID_TIPO = T.ID AND R.ID =" + idRestaurante + " ";
//				PreparedStatement prepStmtTipoRestaurantes= conn.prepareStatement(sqlTipoRestaurantes);
//				recursos.add(prepStmtTipoRestaurantes);
//				ResultSet rsTipoRestaurantes = prepStmtTipoRestaurantes.executeQuery();
//				
//				String tipoRestaurante = rsTipoRestaurantes.getString("NAME");
//				
//				//TODO INICIO PARTE DE PROCESAR LOS PRODUCTOS DEL RESTAURANTE.
//				List<Producto> productosRestaurantes = null;
//				
//				
//				//FIN PARTE DE PROCESAR LOS PRODUCTOS DEL RESTAURANTE.
//				Restaurante newRestaurante = new Restaurante(idRestaurante, nameRestaurante, paginaRestaurante, productosRestaurantes, tipoRestaurante);
//				restuarantes.add(newRestaurante);
//			}
			zonas.add(newZona);
		}
		return zonas;
	}	
	/**
	 * Método que obtiene las zonas según un parámetro dado.
	 * @param filtro Tipo de Parámetro a recibir.
	 * @param parametroBusqueda Información del parámetrode búsqueda, cambia según los casos
	 * Caso 0: CONDICIONTECNICA. parametroBusqueda debe ser de tipo String o Long.
	 * Caso 1: CONTAINSRESTAURANTE. parametroBusqueda debe ser de tipo String o Long.
	 * Caso 2: CONTAINSPRODUCTO. parametroBusqueda debe ser de tipo String o Long.
	 * Caso 3: ZONA. parametroBusqueda debe ser de tipo String o Long.
	 * Caso 4: ESESPACIOABIERTO. parametroBusqueda debe ser de tipo Long.
	 * Caso 5: ESINCLUYENTE. parametroBusqueda debe ser de tipo Long.
	 * Caso 6: CAPACIDAD.parametroBusqueda debe ser de tipo String, y debe contener el valor, y el tipo de comparación (<=, = >=).
	 * @param parametroOrden. Parámetro que determina bajo qué criterio se ordena la respuesta, y si es ascendente o descendente. Ejemplo: "NAME DESC".
	 * @return List<Zona> lista de zonas
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Zona> getZonasConParametroNombreOCapacidad(Integer filtro, Object parametroBusqueda1, String parametroOrden)throws SQLException, Exception
	{
		String sqlZonasMain = "SELECT * FROM ZONAS ";

		if(filtro.equals(CONDICIONTECNICA))
		{
			if(parametroBusqueda1.getClass().equals(String.class))
			{
				sqlZonasMain =  "SELECT Z.ID, Z.NAME, Z.ESPACIO_ABIERTO, Z.CAPACIDAD, Z.INCLUYENTE\r\n" + 
						"    FROM ZONAS Z, CONDICIONZONA CZ, CONDICIONESTECNICAS C\r\n" + 
						"    WHERE C.NAME = '"+ parametroBusqueda1 +"' AND CZ.ID_ZONA = Z.ID AND CZ.ID_CONDICION = C.ID" ;
			}
			else if(parametroBusqueda1.getClass().equals(Long.class))
			{
				sqlZonasMain =  "SELECT Z.ID, Z.NAME, Z.ESPACIO_ABIERTO, Z.CAPACIDAD, Z.INCLUYENTE\r\n" + 
						"    FROM ZONAS Z, CONDICIONZONA CZ, CONDICIONESTECNICAS C\r\n" + 
						"    WHERE C.ID = "+ parametroBusqueda1 +" AND CZ.ID_ZONA = Z.ID AND CZ.ID_CONDICION = C.ID" ;
			}
		}
		else if (filtro.equals(CONTAINSRESTAURANTE))
		{
			if(parametroBusqueda1.getClass().equals(Long.class))
			{
				//PARÁMETRO DEBE SER EL id DEL RESTAURANTE.
				sqlZonasMain = "SELECT Z.ID, Z.NAME, Z.ESPACIO_ABIERTO, Z.CAPACIDAD, Z.INCLUYENTE FROM ZONAS Z, RESTAURANTES R WHERE R.ID = " + parametroBusqueda1 + " AND R.ID_ZONA = Z.ID";
			}
			else if(parametroBusqueda1.getClass().equals(String.class))
			{
				//PARÁMETRO DEBE SER EL nombre DEL RESTAURANTE.
				sqlZonasMain = "SELECT Z.ID, Z.NAME, Z.ESPACIO_ABIERTO, Z.CAPACIDAD, Z.INCLUYENTE FROM ZONAS Z, RESTAURANTES R WHERE R.NAME = '" + parametroBusqueda1 + "' AND R.ID_ZONA = Z.ID";
			}
		}
		else if(filtro.equals(CONTAINSPRODUCTO))
		{
			//TODO TERMINAR ESTE CASO.
		}
		else if(filtro.equals(ZONA))
		{
			if(parametroBusqueda1.getClass().equals(String.class))
			{
				sqlZonasMain = "SELECT Z.ID, Z.NAME, Z.ESPACIO_ABIERTO, Z.CAPACIDAD, Z.INCLUYENTE\r\n" + 
						"    FROM ZONAS Z\r\n" + 
						"    WHERE Z.NAME = '"+ parametroBusqueda1;
			}
			else if(parametroBusqueda1.getClass().equals(Long.class))
			{
				sqlZonasMain = "SELECT Z.ID, Z.NAME, Z.ESPACIO_ABIERTO, Z.CAPACIDAD, Z.INCLUYENTE\r\n" + 
						"    FROM ZONAS Z\r\n" + 
						"    WHERE Z.ID = '"+ parametroBusqueda1;
			}
		}
		else if(filtro.equals(ESESPACIOABIERTO))
		{
			//PARÁMETRO DEBE SER DE TIPO LONG NECESARIAMENTE.
			sqlZonasMain = "SELECT Z.ID, Z.NAME, Z.ESPACIO_ABIERTO, Z.CAPACIDAD, Z.INCLUYENTE\r\n" + 
					"    FROM ZONAS Z\r\n" + 
					"    WHERE Z.ESPACIO_ABIERTO = "+ parametroBusqueda1;
		}
		else if(filtro.equals(ESESPACIOABIERTO))
		{
			//PARÁMETRO DEBE SER DE TIPO LONG NECESARIAMENTE.
			sqlZonasMain = "SELECT Z.ID, Z.NAME, Z.ESPACIO_ABIERTO, Z.CAPACIDAD, Z.INCLUYENTE\r\n" + 
					"    FROM ZONAS Z\r\n" + 
					"    WHERE Z.INCLUYENTE = "+ parametroBusqueda1;
		}
		else if(filtro.equals(CAPACIDAD))
		{

			//PARÁMETRO DEBE SER DE TIPO STRING NECESARIAMENTE, Y DEBE CONTENER: <=, = Ó >=
			sqlZonasMain = "SELECT Z.ID, Z.NAME, Z.ESPACIO_ABIERTO, Z.CAPACIDAD, Z.INCLUYENTE\r\n" + 
					"    FROM ZONAS Z\r\n" + 
					"    WHERE Z.CAPACIDAD "+ parametroBusqueda1;
		}
		else
		{
			sqlZonasMain = "SELECT * FROM ZONAS ";
			throw new Exception ("Parámetros inválidos");
		}
		
		
		if(parametroOrden != null)
		{
			sqlZonasMain += "ORDER BY " +parametroOrden;
		}
		sqlZonasMain += ";";
		PreparedStatement prepStmtZonasMain= conn.prepareStatement(sqlZonasMain);
		recursos.add(prepStmtZonasMain);
		ResultSet rsMain = prepStmtZonasMain.executeQuery();
		
		List<Zona> zonas = new ArrayList<Zona>();
		
		while(rsMain.next())
		{
			Long id = rsMain.getLong("ID");
			String nombre = rsMain.getString("NAME");
			Boolean esEspacioAbierto = rsMain.getBoolean("ESPACIO_ABIERTO");
			Integer capacidad = rsMain.getInt("CAPACIDAD");
			Boolean esIncluyente = rsMain.getBoolean("INCLUYENTE");
			
			String sqlCondiciones = "SELECT C.ID AS ID, Z.NAME AS NAME FROM CONDICIONESTECNICAS C, CONDICIONZONA Z WHERE Z.ID_ZONA = " + id + " AND Z.ID_CONDICION = C.ID ORDER BY ID DESC;";
			PreparedStatement prepStmtCondiciones= conn.prepareStatement(sqlCondiciones);
			recursos.add(prepStmtCondiciones);
			ResultSet rsCondiciones = prepStmtCondiciones.executeQuery();
			
			List<String> condiciones = new ArrayList<String>();
			while(rsCondiciones.next())
			{
				String condicion = rsCondiciones.getString("NAME");
				condiciones.add(condicion);
			}
			Zona newZona = new Zona(id, nombre, esEspacioAbierto, capacidad, esIncluyente, condiciones, null);
			
			
			String sqlRestaurantes = "SELECT * FROM RESTAURANTES WHERE ID_ZONA = " + id + ";";
			PreparedStatement prepStmtRestaurantes= conn.prepareStatement(sqlRestaurantes);
			recursos.add(prepStmtRestaurantes);
			ResultSet rsRestaurantes = prepStmtRestaurantes.executeQuery();
			
			List<Restaurante> restaurantes = new ArrayList<Restaurante>();
			while(rsRestaurantes.next())
			{
				Long idRestaurante = rsRestaurantes.getLong("ID");
				String nameRestaurante = rsRestaurantes.getString("NAME");
				String paginaRestaurante = rsRestaurantes.getString("PAGINA_WEB");
				
				String sqlTipoRestaurantes = "SELECT T.NAME FROM RESTAURANTES R, TIPOS T WHERE R.ID_TIPO = T.ID AND R.ID =" + idRestaurante + ";";
				PreparedStatement prepStmtTipoRestaurantes= conn.prepareStatement(sqlTipoRestaurantes);
				recursos.add(prepStmtTipoRestaurantes);
				ResultSet rsTipoRestaurantes = prepStmtTipoRestaurantes.executeQuery();
				
				String tipoRestaurante = rsTipoRestaurantes.getString("NAME");
				
				//TODO INICIO PARTE DE PROCESAR LOS PRODUCTOS DEL RESTAURANTE.
				List<Producto> productosRestaurantes = null;
				
				
				//FIN PARTE DE PROCESAR LOS PRODUCTOS DEL RESTAURANTE.
				Restaurante newRestaurante = new Restaurante(idRestaurante, nameRestaurante, paginaRestaurante, productosRestaurantes, tipoRestaurante);
				restaurantes.add(newRestaurante);
			}
			newZona.setRestaurantes(restaurantes);
			zonas.add(newZona);
		}
		return zonas;
	}
}
