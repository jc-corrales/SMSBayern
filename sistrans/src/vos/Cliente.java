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

public class Cliente
{
	/**
	 * Id del Cliente.
	 */
	@JsonProperty(value="id")
	private Long id;
	/**
	 * Mesa asignada del cliente;
	 */
	@JsonProperty(value = "mesa")
	private Integer mesa;
	/**
	 * Nombre del cliente.
	 */
	@JsonProperty(value = "nombre")
	private String nombre;

	@JsonProperty(value = "ordenes")
	private List<Orden> ordenes;
	
	
	public Cliente() {
		
	}
	/**
	 * MÈtodo constructor de la clase ClienteVos.
	 * @param id
	 * @param mesa
	 * @param nombre
	 */
	public Cliente(@JsonProperty(value="id")Long id, 
			@JsonProperty(value = "mesa")Integer mesa, 
			@JsonProperty(value = "nombre")String nombre,
			@JsonProperty(value = "ordenes") List<Orden> ordenes)
	{
		this.id = id;
		this.mesa = mesa;
		this.nombre = nombre;
		this.ordenes = ordenes;
	}
	/**
	 * MÈtodo que obtiene el ID de este cliente-
	 * @return Long, ID del cliente.
	 */
	public Long getId()
	{
		return this.id;
	}
	/**
	 * MÈtodo que establece la ID de este cliente.
	 * @param id Long, ID del cliente.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	/**
	 * MÈtodo que obtiene la mesa asignada a este cliente.
	 * @return String, mesa del cliente.
	 */
	public Integer getMesa()
	{
		return this.mesa;
	}
	/**
	 * MÈtodo que establece la mesa de este cliente.
	 * @param mesa
	 */
	public void setMesa(Integer mesa)
	{
		this.mesa = mesa;
	}
	/**
	 * MÈtodo que obtiene el nombre de este cliente.
	 * @return String, nombre del cliente.
	 */
	public String getNombre()
	{
		return this.nombre;
	}
	/**
	 * MÈtodo que establece el nombre de este cliente.
	 * @param nombre String, nuevo nombre del cliente.
	 */
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	
	/**
	 * MÈtodo que obtiene el nombre de este cliente.
	 * @return String, nombre del cliente.
	 */
	public List<Orden> getOrdenes()
	{
		return this.ordenes;
	}
	
	/**
	 * MÈtodo que establece el nombre de este cliente.
	 * @param nombre String, nuevo nombre del cliente.
	 */
	public void setOrdenes(List<Orden> ordenes)
	{
		this.ordenes = ordenes;
	}


}
