/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogot√° - Colombia)
 * Departamento de Ingenier√≠a de Sistemas y Computaci√≥n
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: RotondAndes
 * Autor: Juan Carlos Corrales - jc.corrales@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Entrada extends Producto
{
	/**
	 * Atributo que contiene las unidades que componen a este producto.
	 */
	@JsonProperty(value = "unidades")
	private Integer unidades;
	/**
	 * MÈtodo constructor de la clase EntradaVos.
	 * @param id
	 * @param nombre
	 * @param costoDeProduccion
	 * @param descripcionEspaniol
	 * @param descripcionIngles
	 * @param idProductosEquivalentes
	 * @param precio
	 * @param tiposComida
	 * @param unidades
	 */
	public Entrada(@JsonProperty(value="id")Long id, 
			@JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="costoDeProduccion")Double costoDeProduccion, 
			@JsonProperty(value="descripcionEspaniol")String descripcionEspaniol, 
			@JsonProperty(value = "descripcionIngles")String descripcionIngles,
			@JsonProperty(value = "productosEquivalentes")List<ProductoBase> productosEquivalentes,
			@JsonProperty(value = "precio")Double precio,
			@JsonProperty(value = "tiposComida")List<TipoComida> tiposComida,
			@JsonProperty(value="unidades")Integer unidades)
	{
		super(id, descripcionEspaniol, costoDeProduccion, descripcionEspaniol, descripcionEspaniol, productosEquivalentes, costoDeProduccion, tiposComida);
		this.unidades = unidades;
	}
	/**
	 * MÈtodo que obtiene las unidades que componen a este producto.
	 * @return Intger, unidades de este producto.
	 */
	public Integer getUnidades()
	{
		return this.unidades;
	}
	/**
	 * MÈtodo que establece las unidades de este producto.
	 * @param unidades Integer, nuevas unidades de este producto.
	 */
	public void setUnidades(Integer unidades)
	{
		this.unidades = unidades;
	}
}
