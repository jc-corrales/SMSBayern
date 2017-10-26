/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (BogotÃ¡ - Colombia)
 * Departamento de IngenierÃ­a de Sistemas y ComputaciÃ³n
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: menuAndes
 * Autor: Juan Felipe GarcÃ­a - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import java.util.Collection;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa un menú
 * @author dj.bautista10
 */
public class Menu{

	//// Atributos

	/**
	 * Id del menu
	 */
	@JsonProperty(value="id")
	private Long id;

	/**
	 * Nombre del menú
	 */
	@JsonProperty(value="name")
	private String name;

	/**
	 * Costo de producción
	 */
	@JsonProperty(value="costoProduccion")
	private Double costoProduccion;
	/**
	 * Atributo que contiene la descripción en español de este Menú.
	 */
	@JsonProperty(value="descripcion")
	private String descripcion;
	/**
	 * Atributo que contiene la descripción en inglés de este Menú.
	 */
	@JsonProperty(value="description")
	private String description;
	/**
	 * Atributo que contiene el precio de este Menú.
	 */
	@JsonProperty(value="precio")
	private Double precio;
	/**
	 * Atributo que contiene la Entrada de este Menú.
	 */
	@JsonProperty(value="entrada")
	private Producto entrada; 
	/**
	 * Atributo que contiene el Plato Fuerte de este Menú.
	 */
	@JsonProperty(value="platoFuerte")
	private Producto platoFuerte;
	/**
	 * Atributo que contiene el Postre de este Menú.
	 */
	@JsonProperty(value="postre")
	private Producto postre;
	/**
	 * Atributo que contiene la Bebida de este Menú.
	 */
	@JsonProperty(value="bebida")
	private Producto bebida;


	/**
	 * Método constructor de la clase menu
	 * <b>post: </b> Crea el menu con los valores que entran como parametro
	 * @param id - Id del menu.
	 * @param name - Nombre del menu. name != null
	 * @param sabores - lista de String de sabores en minutos del menu.
	 */
	public Menu(@JsonProperty(value="id") Long id, 
			@JsonProperty(value="name") String name, 
			@JsonProperty(value = "costoProduccion") Double costoProduccion, 
			@JsonProperty(value="descripcion") String descripcion, 
			@JsonProperty(value = "description") String description, 
			@JsonProperty(value = "precio") Double precio,
			@JsonProperty(value="entrada") Producto entrada,
			@JsonProperty(value="platoFuerte") Producto platoFuerte,
			@JsonProperty(value="bebida") Producto bebida,
			@JsonProperty(value="postre") Producto postre){
		super();
		this.id = id;
		this.name = name;
		this.descripcion = descripcion;
		this.description = description;
		this.precio = precio;
		this.costoProduccion = costoProduccion;
		this.entrada = entrada;
		this.platoFuerte = platoFuerte;
		this.bebida = bebida;
		this.postre = postre;
	}


	/**
	 * Metodo getter del atributo id
	 * @return id del menu
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Metodo setter del atributo id <b>post: </b> El id del menu ha sido
	 * cambiado con el valor que entra como parametro
	 * @param id - Id del menu
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Metodo getter del atributo name
	 * @return nombre del menu
	 */
	public String getName() {
		return name;
	}

	/**
	 * Metodo setter del atributo name <b>post: </b> El nombre del menu ha sido
	 * cambiado con el valor que entra como parametro
	 * @param name - Id del menu
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Método getter del atributo sabores
	 * @return colección de sabores del menu 
	 */
	public  String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método setter del atributo esCaliente <b> post: </b> El valor condicional de la bebida ha sido cambiado
	 * por el valor que entra como parámetro.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion= descripcion;
	}



	/**
	 * Método getter del atributo sabores
	 * @return colección de sabores del menu 
	 */
	public  String getdescription() {
		return description;
	}

	/**
	 * Método setter del atributo esCaliente <b> post: </b> El valor condicional de la bebida ha sido cambiado
	 * por el valor que entra como parámetro.
	 */
	public void setdescription(String description) {
		this.description= description;
	}
	/**
	 * Método que obtiene el Precio de este Menú.
	 * @return Double, Precio de este Menú.
	 */
	public Double getPrecio() {
		return precio;
	}
	/**
	 * Método que establece el Precio de este Menú.
	 * @param precio Double, nuevo Precio de este Menú.
	 */
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	/**
	 * Método que obtiene el Costo de Producción de este Menú.
	 * @return Double, Costo de Producción de este Menú.
	 */
	public Double getCostoProduccion() {
		return costoProduccion;
	}
	/**
	 * Método que establece el costo de producción de este Menú.
	 * @param costoProduccion Double, nuevo costo de producción de este Menú.
	 */
	public void setCostoProduccion(Double costoProduccion) {
		this.costoProduccion = costoProduccion;
	}
	/**
	 * Método que obtiene la Entrada de este Menú.
	 * @return Entrada, Entrada de este Menú.
	 */
	public Producto getEntrada() {
		return entrada;
	}
	/**
	 * Método que establece la Entrada de este Menú.
	 * @param entrada Entrada, nueva Entrada de este Menú.
	 */
	public void setEntrada(Producto entrada) {
		this.entrada = entrada;
	}
	/**
	 * Método que obtiene el Plato Fuerte de este Menú.
	 * @return PlatoFuerte, Plato Fuerte de este Menú.
	 */
	public Producto getPlatoFuerte() {
		return platoFuerte;
	}
	/**
	 * Método que establece el Plato Fuerte de este Menú.
	 * @param platoFuerte PlatoFuerte, nuevo PlatoFuerte de este Menú.
	 */
	public void setPlatoFuerte(Producto platoFuerte) {
		this.platoFuerte = platoFuerte;
	}
	/**
	 * Método que obtiene el Postre de este Menú.
	 * @return Postre, Postre de este Menú.
	 */
	public Producto getPostre() {
		return postre;
	}
	/**
	 * Método que establece el Postre de este Menú.
	 * @param postre Postre, nuevo postre de este Menú.
	 */
	public void setPostre(Producto postre) {
		this.postre = postre;
	}
	/**
	 * Método que obtiene la Bebida de este Menú.
	 * @return Bebida, Bebida de este Menú.
	 */
	public Producto getBebida() {
		return bebida;
	}
	/**
	 * Método que establece la Bebida de este Menú.
	 * @param bebida Bebida, nueva Bebida de este Menú.
	 */
	public void setBebida(Producto bebida) {
		this.bebida = bebida;
	}
}
