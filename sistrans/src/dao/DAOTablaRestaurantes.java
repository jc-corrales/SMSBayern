package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Restaurante;

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


	
	

}
