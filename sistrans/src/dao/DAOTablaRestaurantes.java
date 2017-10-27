package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import vos.Producto;
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
			Restaurante res = new Restaurante(rs.getLong("ID"), rs.getString("NAME"), rs.getString("PAGINA_WEB"), new ArrayList<Producto>(), new TipoComida(0L, new String("hola")));
			restaurantes.add(res);
		}
		
		return restaurantes;
	}


	
	

}
