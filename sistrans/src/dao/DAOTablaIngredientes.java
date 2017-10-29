package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Ingrediente;
import vos.IngredienteBase;

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
		conn.setAutoCommit(false);
		String sql = "INSERT INTO INGREDIENTES (ID, NAME, DESCRIPCION, DESCRIPTION, CANTIDAD_DISPONIBLE)\r\n" + 
				"    VALUES(" + ingrediente.getId()+ ", '" + ingrediente.getName() + "', '" + ingrediente.getDescripcion() + "', '" + ingrediente.getdescription() + "', " + ingrediente.getCantidadDisponible() + ")";
		PreparedStatement st = conn.prepareStatement(sql);
		recursos.add(st);
		st.executeQuery();
		
		conn.setAutoCommit(true);
		return ingrediente;
	}

}
