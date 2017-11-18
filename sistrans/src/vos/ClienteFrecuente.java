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


public class ClienteFrecuente extends Cliente
{
	/**
	 * Atributo que contiene la contrasenia de este cliente.
	 */
	@JsonProperty(value="contrasenia")
	private String contrasenia;
	/**
	 * Atributo que contiene los productos pre seleccionados de este cliente.
	 */
	@JsonProperty(value = "preferencias")
	private List<ProductoBase> preferencias;
	
	public ClienteFrecuente() {
		super();
	}
	
	
	
	/**
	 * Método Constructor de la clase ClienteFrecuente.
	 * @param productosPreSeleccionados List<ProductoBase> Lista de productos preseleccionados de este cliente.
	 * @param contrasenia String, contraseña del cliente.
	 * @param id Long, ID del cliente.
	 * @param mesa Integer, número de la mesa de este cliente.
	 * @param nombre String, nombre del cliente.
	 */
	public ClienteFrecuente(@JsonProperty(value = "preferencias")List<ProductoBase> preferencias, 
			@JsonProperty(value="contrasenia")String contrasenia,
			@JsonProperty(value="id")Long id, 
			@JsonProperty(value = "mesa")Long mesa, 
			@JsonProperty(value = "nombre")String nombre,
			@JsonProperty(value = "ordenes") List<Orden> ordenes)
	{
		super(id, mesa, nombre, ordenes);
		this.contrasenia = contrasenia;
		this.preferencias = preferencias;
	}

	/**
	 * Método que obtiene la contrasenia de este cliente frecuente registrado.
	 * @return String, contrasenia de este cliente.
	 */
	public String getContrasenia()
	{
		return this.contrasenia;
	}
	/**
	 * Método que establece la contrasenia de este cliente frecuente.
	 * @param contrasenia String, nueva contrasenia de este cliente frecuente.
	 */
	public void setContrasenia(String contrasenia)
	{
		this.contrasenia = contrasenia;
	}
	/**
	 * Método que obtiene los productos pre seleccionados de este cliente frecuente.
	 * @return List<ProductoBaseVos>, lista de productos pre seleccionados de este cliente.
	 */
	public List<ProductoBase> getPreferencias()
	{
		return preferencias;
	}
	/**
	 * Método que establece la lista de productos pre seleccionados de este cliente frecuente.
	 * @param productosPreSeleccionados List<ProductoBaseVos>, nueva lista de productos pre seleccionados de este cliente.
	 */
	public void setPreferencias(List<ProductoBase> preferencias)
	{
		this.preferencias = preferencias;
	}
}
