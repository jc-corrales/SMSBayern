package vos;

import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Clase que describe los datos Básicos de un Ingrediente.
 * @author dj.bautista10
 *
 */
public class IngredienteBase 
{
	/**
	 * Atributo que contiene el ID del Ingrediente.
	 */
	private Long id;
	/**
	 * Atributo que contiene el nombre del Ingrediente.
	 */
	private String name;
	/**
	 * Método constructor de la clase IngredienteBase
	 * @param id Long
	 * @param name String
	 */
	public IngredienteBase(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	/**
	 * Método que obtiene el ID del Ingrediente.
	 * @return Long, ID del Ingrediente.
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Método que establece el ID del Ingrediente.
	 * @param id Long, nuevo ID del Ingrediente.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Método que obtiene el nombre del Ingrediente.
	 * @return String, nombre del Ingrediente.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Método que establece el nombre del Ingrediente.
	 * @param name String, nuevo nombre del Ingrediente.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
