/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (BogotÃ¡ - Colombia)
 * Departamento de IngenierÃ­a de Sistemas y ComputaciÃ³n
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: RotondAndes
 * Autor: Juan Carlos Corrales - jc.corrales@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import java.util.List;
import org.codehaus.jackson.annotate.*;
/**
 * Clase que contiene la información de un Plato Fuerte.
 * @author jc.corrales
 *
 */
public class PlatoFuerte extends Producto
{
	/**
	 * Atributo que contiene la lista de acompañamientos de este plato fuerte.
	 */
	@JsonProperty(value = "acompaniamientos")
	private List<Entrada> acompaniamientos;


	/**
	 * Método constructo de la clase PlatoFuerteVos.
	 * @param acompaniamientosList<Entrada>
	 * @param id Long
	 * @param nombre String
	 * @param costoDeProduccion Double
	 * @param descripcionEspaniol String
	 * @param descripcionIngles String
	 * @param idProductosEquivalentes List<ProductoBase>
	 * @param precio Double
	 * @param tiposComida List<TipoComida>
	 */
	public PlatoFuerte(@JsonProperty(value="acompaniamientos")List<Entrada> acompaniamientos,
			@JsonProperty(value="id")Long id, @JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="costoDeProduccion")Double costoDeProduccion, 
			@JsonProperty(value="descripcionEspaniol")String descripcionEspaniol, 
			@JsonProperty(value = "descripcionIngles")String descripcionIngles,
			@JsonProperty(value = "productosEquivalentes")List<ProductoBase> productosEquivalentes,
			@JsonProperty(value = "precio")Double precio,
			@JsonProperty(value = "tiposComida")List<TipoComida> tiposComida)
	{
		super(id, nombre, costoDeProduccion, descripcionEspaniol, descripcionIngles, productosEquivalentes, precio, tiposComida);
		this.acompaniamientos = acompaniamientos;

	}
	/**
	 * Método que obtiene los acompañamientos de este plato fuerte.
	 * @return List<EntradaVos>, lista de acompañamientos.
	 */
	public List<Entrada> getAcompaniamientos()
	{
		return this.acompaniamientos;
	}
	/**
	 * Método que establece los acompañamientos de este plato fuerte.
	 * @param acompaniamientos List<EntradaVos>, lista de acompañamientos nuevos.
	 */
	public void setAcompaniamientos(List<Entrada> acompaniamientos)
	{
		this.acompaniamientos = acompaniamientos;
	}
}