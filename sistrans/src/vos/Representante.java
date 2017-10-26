package vos;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que contiene la información de un Representante.
 * @author dj.bautista10
 *
 */
public class Representante {
	/**
	 * Id del representante
	 */
	@JsonProperty(value="id")
	private Long id;

	/**
	 * Nombre del representante
	 */
	@JsonProperty(value="name")
	private String name;
	/**
	 * Atributo que contiene la contraseña de este representante.
	 */
	@JsonProperty(value = "contrasenia")
	private String contrasenia;
	/**
	 * Atributo que contiene el Restaurante.
	 */
	@JsonProperty(value = "restaurante")
	private Restaurante restaurante;
	/**
	 * Método constructor de la Clase Representante.
	 * @param id Long
	 * @param name String
	 */
	public Representante(
			@JsonProperty(value="id")Long id,
			@JsonProperty(value="name")String name,
			@JsonProperty(value="contrasenia")String contrasenia
			)
	{
		super();
		this.id = id;
		this.name = name;
		this.contrasenia = contrasenia;
	}
	/**
	 * Método que obtiene el ID de un Representante.
	 * @return Long, ID del representante.
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Método que establece el ID de un representante.
	 * @param id Long, nuevo ID del representate.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Método que obtiene el nombre de un Representante.
	 * @return String, nombre del Representante.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Método que establece el nombre de un Representante.
	 * @param name String, nuevo nombre del Representante.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Método que obtiene la contraseña de este Representante.
	 * @return String, contraseña del representante.
	 */
	public String getContrasenia()
	{
		return contrasenia;
	}
	/**
	 * Método que establece la contraseña de este representante.
	 * @param contrasenia, nueva contraseña del representante.
	 */
	public void setContrasenia(String contrasenia)
	{
		this.contrasenia = contrasenia;
	}
	/**
	 * Método que obtiene el Restaurante que este Representante representa.
	 * @return Restaurante, Restaurante que este Representante representa.
	 */
	public Restaurante getRestaurante()
	{
		return restaurante;
	}
	/**
	 * Método que establece el Restaurante que este Representante representa.
	 * @param restaurante Restaurante, nuevo Restaurante que este Representante representa.
	 */
	public void setRestaurante(Restaurante restaurante)
	{
		this.restaurante = restaurante;
	}
}
