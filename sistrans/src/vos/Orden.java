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

public class Orden
{
	/**
	 * Id de la orden.
	 */
	@JsonProperty(value="id")
	private Long id;
	/**
	 * Atributo que contiene el costo total de esta orden.
	 */
	@JsonProperty(value = "costoTotal")
	private Double costoTotal;
	/**
	 * Atributo que contiene los productos Ordenados.
	 */
	@JsonProperty(value = "pedidosOrdenados")
	private List<Pedido> pedidosOrdenados;
	/**
	 * Atributo que contiene el cliente dueño de esta orden.
	 */
	@JsonProperty(value = "cliente")
	private Cliente cliente;
	/**
	 * Atributo que determina si la orden ha sido confirmada o no.
	 */
	@JsonProperty(value = "esConfirmada")
	private Boolean esConfirmada;
	/**
	 * Método constructor de la clase OrdenVos.
	 * @param id
	 * @param costoTotal
	 * @param productosOrdenados
	 * @param cliente
	 */
	public Orden(@JsonProperty(value = "id")Long id,
			@JsonProperty(value = "costoTotal") Double costoTotal,
			@JsonProperty(value = "productosOrdenados")List<Pedido> pedidosOrdenados,
			@JsonProperty(value = "cliente")Cliente cliente,
			@JsonProperty(value = "esConfirmada")Boolean esConfirmada)
	{
		this.id = id;
		this.costoTotal = costoTotal;
		this.pedidosOrdenados = pedidosOrdenados;
		this.cliente = cliente;
		this.esConfirmada = esConfirmada;
	}
	/**
	 * Método que obtiene el ID de este cliente frecuente.
	 * @return Long, ID de este cliente.
	 */
	public Long getId()
	{
		return this.id;
	}
	/**
	 * Método que establece el ID de esta orden.
	 * @param id Long, ID de esta orden.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	/**
	 * Método que obtiene el costo total de esta orden.
	 * @return Double, costo total de esta orden.
	 */
	public Double getCostoTotal()
	{
		return this.costoTotal;
	}
	/**
	 * Método que establece el costo total de esta orden.
	 * @param costoTotal Double, costo total de esta orden.
	 */
	public void setCostoTotal(Double costoTotal)
	{
		this.costoTotal = costoTotal;
	}
	/**
	 * Método que obtiene la lista de productos ordenados de esta orden.
	 * @return List<ProductoVos>, Lista de productos Ordenados en esta orden.
	 */
	public List<Pedido> getPedidosOrdenados()
	{
		return this.pedidosOrdenados;
	}
	/**
	 * Método que establece la lista de productos ordenados de esta orden.
	 * @param productosOrdenados List<ProductoVos>, Nueva lista de productos ordenados en esta orden.
	 */
	public void setPedidosOrdenados(List<Pedido> pedidosOrdenados)
	{
		this.pedidosOrdenados = pedidosOrdenados;
	}
	/**
	 * Método que obtiene el cliente a nombre de quien está esta orden.
	 * @return ClienteVos, Cliente de esta orden.
	 */
	public Cliente getCliente()
	{
		return this.cliente;
	}
	/**
	 * Método que establece el cliente a nombre de quien está esta orden.
	 * @param cliente ClienteVos, nuevo cliente de esta orden.
	 */
	public void setCliente(Cliente cliente)
	{
		this.cliente = cliente;
	}
	/**
	 * Método que devuelve el estado de confirmación de la Orden.
	 * @return Boolean, Booleano que determina si la Orden ha sido confirmada o no.
	 */
	public Boolean getEsConfirmada()
	{
		return this.esConfirmada;
	}
	/**
	 * Método que establece el estado de confirmación de la Orden.
	 * @param esConfirmada Boolean, Booleano que determina si la Orden ha sido confirmada o no.
	 */
	public void setEsConfirmada(Boolean esConfirmada)
	{
		this.esConfirmada = esConfirmada;
	}
}
