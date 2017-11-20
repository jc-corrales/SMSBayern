/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
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
	 * Atributo que contiene los men�s ordenados.
	 * Nota: Este atributo solo contiene el hecho de que el men�
	 * designado fue ordenado, pero no tiene en cuenta los productos
	 * del menu y eso, cuando se ordena un men�, se registra que el
	 * men� fue ordenado, y luego se emiten pedidos para cada
	 * uno de los productos del men�.
	 */
	@JsonProperty(value = "menusOrdenados")
	private List<PedidoDeMenu> menusOrdenados;
	/**
	 * Atributo que contiene el cliente due�o de esta orden.
	 */
	@JsonProperty(value = "cliente")
	private Cliente cliente;
	/**
	 * Atributo que determina si la orden ha sido confirmada o no.
	 */
	@JsonProperty(value = "esConfirmada")
	private Boolean esConfirmada;
	/**
	 * M�todo constructor de la clase OrdenVos.
	 * @param id
	 * @param costoTotal
	 * @param productosOrdenados
	 * @param cliente
	 */
	public Orden(@JsonProperty(value = "id")Long id,
			@JsonProperty(value = "costoTotal") Double costoTotal,
			@JsonProperty(value = "productosOrdenados")List<Pedido> pedidosOrdenados,
			@JsonProperty(value = "cliente")Cliente cliente,
			@JsonProperty(value = "esConfirmada")Boolean esConfirmada,
			@JsonProperty(value = "menusOrdenados")List<PedidoDeMenu> menusOrdenados )
	{
		this.id = id;
		this.costoTotal = costoTotal;
		this.pedidosOrdenados = pedidosOrdenados;
		this.cliente = cliente;
		this.esConfirmada = esConfirmada;
		this.menusOrdenados = menusOrdenados;
	}
	/**
	 * M�todo que obtiene el ID de este cliente frecuente.
	 * @return Long, ID de este cliente.
	 */
	public Long getId()
	{
		return this.id;
	}
	/**
	 * M�todo que establece el ID de esta orden.
	 * @param id Long, ID de esta orden.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	/**
	 * M�todo que obtiene el costo total de esta orden.
	 * @return Double, costo total de esta orden.
	 */
	public Double getCostoTotal()
	{
		return this.costoTotal;
	}
	/**
	 * M�todo que establece el costo total de esta orden.
	 * @param costoTotal Double, costo total de esta orden.
	 */
	public void setCostoTotal(Double costoTotal)
	{
		this.costoTotal = costoTotal;
	}
	/**
	 * M�todo que obtiene la lista de productos ordenados de esta orden.
	 * @return List<ProductoVos>, Lista de productos Ordenados en esta orden.
	 */
	public List<Pedido> getPedidosOrdenados()
	{
		return this.pedidosOrdenados;
	}
	/**
	 * M�todo que establece la lista de productos ordenados de esta orden.
	 * @param productosOrdenados List<ProductoVos>, Nueva lista de productos ordenados en esta orden.
	 */
	public void setPedidosOrdenados(List<Pedido> pedidosOrdenados)
	{
		this.pedidosOrdenados = pedidosOrdenados;
	}
	/**
	 * M�todo que obtiene el cliente a nombre de quien est� esta orden.
	 * @return ClienteVos, Cliente de esta orden.
	 */
	public Cliente getCliente()
	{
		return this.cliente;
	}
	/**
	 * M�todo que establece el cliente a nombre de quien est� esta orden.
	 * @param cliente ClienteVos, nuevo cliente de esta orden.
	 */
	public void setCliente(Cliente cliente)
	{
		this.cliente = cliente;
	}
	/**
	 * M�todo que devuelve el estado de confirmaci�n de la Orden.
	 * @return Boolean, Booleano que determina si la Orden ha sido confirmada o no.
	 */
	public Boolean getEsConfirmada()
	{
		return this.esConfirmada;
	}
	/**
	 * M�todo que establece el estado de confirmaci�n de la Orden.
	 * @param esConfirmada Boolean, Booleano que determina si la Orden ha sido confirmada o no.
	 */
	public void setEsConfirmada(Boolean esConfirmada)
	{
		this.esConfirmada = esConfirmada;
	}
	/**
	 * M�todo que obtiene los men�s que han sido ordenados.
	 * @return List<PedidoDeMenu>, lista de pedidos de men�s ordenados.
	 */
	public List<PedidoDeMenu> getMenusOrdenados()
	{
		return menusOrdenados;
	}
	/**
	 * M�todo que establece los men�s que han sido ordenados.
	 * @param menusOrdenados List<PedidoDeMenu>, nueva lista de pedidos de men�s ordenados.
	 */
	public void setMenusOrdenados(List<PedidoDeMenu> menusOrdenados)
	{
		this.menusOrdenados = menusOrdenados;
	}
}
