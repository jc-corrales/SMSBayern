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

import org.codehaus.jackson.annotate.*;

public class ListaOrdenesVos
{
	/**
	 * Atributo que contiene los productos.
	 */
	@JsonProperty(value = "ordenes")
	private List<Orden> ordenes;
	/**
	 * MÈtodo constructor de la clase ListaProductosVos
	 * @param productos
	 */
	public ListaOrdenesVos(@JsonProperty(value = "ordenes") List<Orden> ordenes)
	{
		this.ordenes = ordenes;
	}
	/**
	 * MÈtodo para obtener los productos.
	 * @return List<OrdenVos>, lista de productos.
	 */
	public List<Orden> getOrdenes()
	{
		return this.ordenes;
	}
	/**
	 * MÈtodo que establece la lista de productos.
	 * @param productos List<OrdenVos>, nueva lista de productos.
	 */
	public void setOrdenes(List<Orden> ordenes)
	{
		this.ordenes = ordenes;
	}
}