package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaClientesVos 
{
	/**
	 * Atributo que contiene los productos.
	 */
	@JsonProperty(value = "clientes")
	private List<Cliente> clientes;
	/**
	 * Método constructor de la clase ListaClientesVos
	 * @param productos
	 */
	public ListaClientesVos(@JsonProperty(value = "clientes") List<Cliente> clientes)
	{
		this.clientes = clientes;
	}
	/**
	 * Método para obtener los productos.
	 * @return List<ClienteVos>, lista de productos.
	 */
	public List<Cliente> getClientes()
	{
		return this.clientes;
	}
	/**
	 * Método que establece la lista de productos.
	 * @param productos List<ClienteVos>, nueva lista de productos.
	 */
	public void setClientes(List<Cliente> clientes)
	{
		this.clientes = clientes;
	}
}
