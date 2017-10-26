/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (BogotÃ¡ - Colombia)
 * Departamento de IngenierÃ­a de Sistemas y ComputaciÃ³n
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe GarcÃ­a - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import java.util.List;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa una Bebida
 * @author dj.bautista10
 */
public class Bebida extends Producto{

	//// Atributos

	/**
	 * Booleano que define si una bebida es caliente
	 */
	@JsonProperty(value="esBebidaCaliente")
	private Boolean esCaliente;
	
	/**
	 * Booleano que define si una bebida ha sido producida naturalmente
	 */
	@JsonProperty(value="esNatural")
	private Boolean esNatural; 
	
	/**
	 * Boolean que define si una bebida va a acompañada de hielo
	 */
	@JsonProperty(value="conHielo")
	private Boolean conHielo;
	
	/**
	 * Descripción breve del sabor de la bebida
	 */
	@JsonProperty(value="sabor")
	private Sabor sabor;

	/**
	 * Metodo constructor de la clase Bebida
	 * <b>post: </b> Crea el video con los valores que entran como parametro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duracion en minutos del video.
	 */
	public Bebida(@JsonProperty(value="id")Long id,
			@JsonProperty(value="name")String name,
			@JsonProperty(value="costoDeProduccion")Double costoDeProduccion,
			@JsonProperty(value="descripcionEspaniol")String descripcionEspaniol, 
			@JsonProperty(value = "descripcionIngles")String descripcionIngles,
			@JsonProperty(value = "productosEquivalentes")List<ProductoBase> productosEquivalentes,
			@JsonProperty(value = "precio")Double precio,
			@JsonProperty(value = "tiposComida") List<TipoComida> tiposComida,
			@JsonProperty(value="esCaliente")Boolean esCaliente, 
			@JsonProperty(value="esNatural")Boolean esNatural, 
			@JsonProperty(value="conHielo")Boolean conHielo,
			@JsonProperty(value="sabor")Sabor sabor) {
		super(id, name, costoDeProduccion, descripcionEspaniol, descripcionIngles, productosEquivalentes, precio, tiposComida);
		this.esCaliente = esCaliente;
		this.esNatural = esNatural;
		this.conHielo = conHielo;
		this.esCaliente = esCaliente;
		this.sabor = sabor;
		
	}
	

	
	
	/**
	 * Método getter del atributo esCaliente 
	 * @return condición esCaliente de la bebida
	 */
	public Boolean getEsCaliente() {
		return esCaliente;
	}
	
	/**
	 * Método setter del atributo esCaliente <b> post: </b> El valor condicional de la bebida ha sido cambiado
	 * por el valor que entra como parámetro.
	 */
	public void setEsCaliente(Boolean esCaliente) {
		this.esCaliente = esCaliente;
	}
	
	/**
	 * Método getter del atributo esNatural 
	 * @return condición esNatural de la bebida
	 */
	public Boolean getEsNatural() {
		return esNatural;
	}
	
	
	/**
	 * Método setter del atributo esNatural <b> post: </b> El valor condicional de la bebida ha sido cambiado
	 * por el valor que entra como parámetro.
	 */
	public void setEsNatural(Boolean esNatural) {
		this.esNatural = esNatural;
	}
	
	/**
	 * Método getter del atributo helado 
	 * @return condición helado de la bebida
	 */
	public Boolean getConHielo() {
		return conHielo;
	}
	
	
	/**
	 * Método setter del atributo helado <b> post: </b> El valor condicional de la bebida ha sido cambiado
	 * por el valor que entra como parámetro.
	 */
	public void setConHielo(Boolean conHielo) {
		this.conHielo = conHielo;
	}
	
	/**
	 * Metodo getter del atributo sabor
	 * @return sabor de la bebida
	 */
	public Sabor getSabor() {
		return sabor;
	}

	/**
	 * Metodo setter del atributo sabor <b>post: </b> El sabor de la bebida ha sido
	 * cambiado con el valor que entra como parametro
	 * @param sabor de la bebida
	 */
	public void set(Sabor sabor) {
		this.sabor = sabor;
	}
	
}
