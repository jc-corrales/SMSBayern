package vos;

import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Clase que describe un Sabor.
 * @author dj.bautista10
 *
 */
public class Sabor {
	/**
	 * Atributo que contiene el ID de este Sabor.
	 */
	@JsonProperty(value="id")
	private Long id; 
	/**
	 * Nombre de este Sabor.
	 */
	@JsonProperty(value="name")
	private String name; 
	/**
	 * Método constructor de la clase Sabor.
	 * @param id Long
	 * @param name String
	 */
	public Sabor(@JsonProperty(value="id") Long id, @JsonProperty(value="name") String name) {
		this.id = id;
		this.name = name;
	}
	/**
	 * Método que obtiene el ID de este Sabor.
	 * @return Long, ID del Sabor.
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Método que establece el ID de este Sabor.
	 * @param id Long, nuevo ID del Sabor.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Método que obtiene el nombre de este Sabor.
	 * @return String, nombre del Sabor.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Método que establece el nombre de este Sabor.
	 * @param name String, nuevo nombre de este Sabor.
	 */
	public void setName(String name) {
		this.name = name; 
	}
	
}