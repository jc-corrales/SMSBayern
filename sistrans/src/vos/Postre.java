/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogot√° - Colombia)
 * Departamento de Ingenier√≠a de Sistemas y Computaci√≥n
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: postreAndes
 * Autor: Juan Felipe Garc√≠a - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa una Bebida
 * @author dj.bautista10
 */
public class Postre extends Producto{

	//// Atributos

	/**
	 * ColecciÛn que define si una bebida es caliente
	 */
	@JsonProperty(value="sabores")
	private Collection<Sabor> sabores;
	

	/**
	 * MÈtodo constructor de la clase Postre
	 * <b>post: </b> Crea el postre con los valores que entran como parametro
	 * @param id - Id del postre.
	 * @param name - Nombre del postre. name != null
	 * @param sabores - lista de String de sabores en minutos del postre.
	 */
	public Postre(@JsonProperty(value="id")Long id, 
			@JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="costoDeProduccion")Double costoDeProduccion, 
			@JsonProperty(value="descripcionEspaniol")String descripcionEspaniol, 
			@JsonProperty(value = "descripcionIngles")String descripcionIngles,
			@JsonProperty(value = "productosEquivalentes")List<ProductoBase> productosEquivalentes,
			@JsonProperty(value = "precio")Double precio,
			@JsonProperty(value = "tiposComida")List<TipoComida> tiposComida,
			@JsonProperty(value="sabores")Collection<Sabor>sabores)
	{
		
		super(id, nombre, costoDeProduccion, descripcionEspaniol, descripcionIngles, productosEquivalentes, precio, tiposComida);
		this.sabores = sabores;
	}
	

	/**
	 * MÈtodo getter del atributo sabores
	 * @return colecciÛn de sabores del postre 
	 */
	public Collection<Sabor> getSabores() {
		return sabores;
	}
	
	/**
	 * MÈtodo setter del atributo esCaliente <b> post: </b> El valor condicional de la bebida ha sido cambiado
	 * por el valor que entra como par·metro.
	 */
	public void setEsCaliente(Collection<Sabor> sabores) {
		this.sabores = sabores;
	}	
}
