package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que contiene una Zona.
 * @author Usuario
 *
 */
public class Zona
{	
	/**
	 * Id de la Zona.
	 */
	@JsonProperty(value="id")
	private Long id;
	/**
	 * Nombre de la zona.
	 */
	@JsonProperty(value = "nombre")
	private String nombre;
	/**
	 * Booleano que determina si la zona es espacio abierto o no.
	 */
	@JsonProperty(value = "esEspacioAbierto")
	private Boolean esEspacioAbierto;
	/**
	 * Capacidad de la zona.
	 */
	@JsonProperty(value = "capacidad")
	private Integer capacidad;
	/**
	 * Booleano que determina si la zona es incluyente o no.
	 * Esto significa si la zona es accesible para personas inválidas o no.
	 */
	@JsonProperty(value = "esIncluyente")
	private Boolean esIncluyente;
	/**
	 * Lista que contiene las condiciones de la zona.
	 */
	@JsonProperty(value = "condiciones")
	private List<Condicion> condiciones;
	/**
	 * Atributo que contiene los restaurantes de esta zona.
	 */
	@JsonProperty(value = "restaurantes")
	private List<Restaurante> restaurantes;
	
	public Zona(){
		
	}
	/**
	 * Método constructor de la zona.
	 * @param id Long, id de la zona.
	 * @param nombre String, nombre de la zona.
	 * @param esEspacioAbierto boolean, booleano que determina si es espacio abierto o no.
	 * @param capacidad Integer, capacidad de la zona.
	 * @param esIncluyente boolean, booleano que determina si la zona es incluyente o no.
	 * @param condiciones List<String>, lista de condiciones de la zona.
	 */
	public Zona(@JsonProperty(value="id")Long id, 
			@JsonProperty(value = "nombre")String nombre,
			@JsonProperty(value = "esEspacioAbierto")Boolean esEspacioAbierto,
			@JsonProperty(value = "capacidad")Integer capacidad,
			@JsonProperty(value = "esIncluyente")Boolean esIncluyente,
			@JsonProperty(value = "condiciones")List<Condicion> condiciones,
			@JsonProperty(value = "restaurantes") List<Restaurante> restaurantes)
			
	{
		this.id = id;
		this.nombre = nombre;
		this.esEspacioAbierto = esEspacioAbierto;
		this.capacidad = capacidad;
		this.esIncluyente = esIncluyente;
		this.condiciones = condiciones;
		this.restaurantes = restaurantes;
	}
	
	
	/**
	 * Método que obtiene el ID de esta zona.
	 * @return Long, ID de la zona.
	 */
	public Long getId()
	{
		return id;
	}
	/**
	 * Método que establece el ID de esta zona.
	 * @param id Long, nuevo ID de la zona.
	 */
	public void setId(Long id)
	{
		this.id =id;
	}
	/**
	 * Método que obtiene el nombre de la zona.
	 * @return String, nombre de la zona.
	 */
	public String getNombre()
	{
		return nombre;
	}
	/**
	 * Método que establece el nombre de la zona.
	 * @param nombre String, nuevo nombre de la zona.
	 */
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	/**
	 * Método que obtiene si la zona es espacio abierto o no.
	 * @return boolean, si la zona es espacio abierto o no.
	 */
	public Boolean getEsEspacioAbierto()
	{
		return esEspacioAbierto;
	}
	/**
	 * Método que establece si la zona es espacio abierto o no.
	 * @param esEspacioAbierto boolean, si la zona es espacio abierto o no. 
	 */
	public void setEsEspacioAbierto(boolean esEspacioAbierto)
	{
		this.esEspacioAbierto = esEspacioAbierto;
	}
	/**
	 * Método que obtiene la capacidad de la zona.
	 * @return Integer, capacidad de la zona.
	 */
	public Integer getCapacidad()
	{
		return capacidad;
	}
	/**
	 * Método que establece la capacidad de la zona.
	 * @param capacidad Integer, nueva capacidad de la zona.
	 */
	public void setCapacidad(Integer capacidad)
	{
		this.capacidad = capacidad;
	}
	/**
	 * Método que obtiene si la zona es incluyente o no.
	 * @return boolean, si es incluyente.
	 */
	public Boolean getEsIncluyente()
	{
		return esIncluyente;
	}
	/**
	 * Método que establece si la zona es incluyente o no.
	 * @param esIncluyente boolean, si es incluyente o no.
	 */
	public void setEsIncluyente(boolean esIncluyente)
	{
		this.esIncluyente = esIncluyente;
	}
	/**
	 * Método que obtiene las condiciones de una zona.
	 * @return List<String>, condiciones de la zona.
	 */
	public List<Condicion> getCondiciones()
	{
		return condiciones;
	}
	/**
	 * Método que establece las condiciones de la zona.
	 * @param condiciones List<String>, nuevas condiciones de la zona.
	 */
	public void setCondiciones(List<Condicion> condiciones)
	{
		this.condiciones = condiciones;
	}
	/**
	 * Método que obtiene los restaurantes de esta zona.
	 * @return List<Restaurante> restaurantes de la zona.
	 */
	public List<Restaurante> getRestaurantes()
	{
		return restaurantes;
	}
	/**
	 * Atributo que establece los Restaurantes de esta zona.
	 * @param restaurantes
	 */
	public void setRestaurantes(List<Restaurante> restaurantes)
	{
		this.restaurantes = restaurantes;
	}
	
	
}
