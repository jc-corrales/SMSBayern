package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import vos.Ingrediente;
import vos.IngredienteBase;
import vos.IngredientesSimilares;

public class DAOTablaIngredientes {
	private ArrayList<Object> recursos;
	private Connection conn;



	public DAOTablaIngredientes() {
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


	public List<Ingrediente> darIngredientesProducto(Long id) throws SQLException, Exception{

		String sql = "SELECT * FROM INGREDIENTES, INGREDIENTES_PRODUCTO WHERE ID = ID_INGREDIENTE AND ID_PRODUCTO = " + id;
		PreparedStatement st = conn.prepareStatement(sql);
		recursos.add(st);
		ResultSet rs = st.executeQuery();

		List<Ingrediente> ings = new ArrayList<>();

		while(rs.next()) {
			System.out.println("encontró los ingredientes");
			Long idIngrediente = rs.getLong("ID");
			Ingrediente ing = new Ingrediente(idIngrediente, 
					rs.getString("NAME"), 
					rs.getString("DESCRIPCION"), 
					rs.getString("DESCRIPTION"),
					darIngredientesEquivalentes(idIngrediente),
					rs.getInt("CANTIDAD_DISPONIBLE"));
			ings.add(ing);
		}
		System.out.println("ings dentro de metodo: " + ings.size());
		return ings;
	}

	public Integer darIngredientesRequeridosPorProducto(Long idProducto, Long idIngrediente) throws SQLException, Exception{
		Integer respuesta = 0;
		String sql = "SELECT * FROM INGREDIENTES, INGREDIENTES_PRODUCTO WHERE ID = ID_INGREDIENTE AND ID_PRODUCTO = " + idProducto + " AND ID = " + idIngrediente;
		PreparedStatement st = conn.prepareStatement(sql);
		recursos.add(st);
		ResultSet rs = st.executeQuery();

		if(rs.next())
		{
			rs.getInt("INGREDIENTES_PRODUCTO.CANTIDAD");
		}
		return respuesta;
	}
	
	private List<IngredienteBase> darIngredientesEquivalentes(Long idIngrediente) throws SQLException, Exception{
		String sql = "SELECT * FROM INGREDIENTES, INGREDIENTESSIMILARES WHERE ID_INGREDIENTE2 = ID AND ID_INGREDIENTE1 = " + idIngrediente;
		PreparedStatement st = conn.prepareStatement(sql);
		recursos.add(st);
		ResultSet rs = st.executeQuery();

		List<IngredienteBase> ings = new ArrayList<>();

		while(rs.next()) {
			IngredienteBase ingB = new IngredienteBase(rs.getLong("ID"), rs.getString("NAME"));
			ings.add(ingB);
		}
		return ings;
	}
	
	public Ingrediente agregarIngredienteSinEquivalentes(Ingrediente ingrediente)throws SQLException, Exception
	{
		String sql = "INSERT INTO INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION, CANTIDAD_DISPONIBLE)\r\n" + 
				"    VALUES(" + ingrediente.getId()+ ", '" + ingrediente.getName() + "', '" + ingrediente.getDescripcion() + "', '" + ingrediente.getdescription() + "', " + ingrediente.getCantidadDisponible() + ")";
		PreparedStatement st = conn.prepareStatement(sql);
		recursos.add(st);
		st.executeQuery();
		
		return ingrediente;
	}
	
	/**
	 * Método que agrega un nuevo ingrediente equivalente con los ids de los ingredientes y el id del restaurante.
	 * @param idIngrediente1
	 * @param idIngrediente2
	 * @param idRestaurante
	 * @throws SQLException
	 * @throws Exception
	 */
	
	public IngredientesSimilares agregarIngredientesSimilares(Long idIngrediente1, Long idIngrediente2, Long idRestaurante) throws SQLException, Exception{
		String sql = "INSERT INTO INGREDIENTESSIMILARES VALUES" + idIngrediente1 + "," + idIngrediente2 + "," + idRestaurante;
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.executeUpdate();
		
		IngredientesSimilares resp = new IngredientesSimilares(idIngrediente1, idIngrediente2, idRestaurante);
		return resp;
	}
	
	
	/**
	 * Método de consulta de los los ingredientes similares de la base de datos
	 * Para consultar que el método de agregarIngredientesSimilares funciona bien
	 */
	public List<IngredientesSimilares> darIngredientesSimilares() throws SQLException, Exception
	{
		String sql = "SELECT * FROM INGREDIENTESSIMILARES";
		PreparedStatement pst = conn.prepareStatement(sql);
		recursos.add(pst);
		ResultSet rs = pst.executeQuery();
		
		List<IngredientesSimilares> ingsS = new ArrayList<>();
		
		while(rs.next())
		{
			IngredientesSimilares ingS = new IngredientesSimilares(rs.getLong("ID_INGREDIENTE1"), rs.getLong("ID_INGREDIENTE2"), rs.getLong("ID_REST"));
			ingsS.add(ingS);
		}
		return ingsS;
		
	}
	
	/**
	 * Método para decrementar el número de ingredientes utilizado por un producto
	 * No se adiciona un parámetro de cantidad debido a que se supone que se resta por uno
	 * y aquellas cosas que se restan mas veces, se espera que se resten repetidas veces
	 * @throws SQLException 
	 */
	
	public void reducirCantidadIngredientesProducto(Long idIngrediente, Integer cantidad) throws SQLException
	{

		String sql = "UPDATE INGREDIENTES SET CANTIDAD_DISPONIBLE = CANTIDAD_DISPONIBLE - " + cantidad + " WHERE ID =" + idIngrediente;
		PreparedStatement pst = conn.prepareStatement(sql);
		recursos.add(pst);
		pst.executeUpdate();
	}
	
}


