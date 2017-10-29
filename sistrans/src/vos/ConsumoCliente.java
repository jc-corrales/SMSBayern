package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsumoCliente
{
	/**
	 * Atributo que contiene la información del Cliente.
	 */
	@JsonProperty(value = "cliente")
	private Cliente cliente;
	/**
	 * Atributo que contiene la información del Producto.
	 */
	@JsonProperty(value = "producto")
	private Producto producto;
	/**
	 * Atributo que contiene la cantidad de Producto consumida por este cliente.
	 */
	@JsonProperty(value = "cantidadConsumida")
	private Integer cantidadConsumida;
	/**
	 * Método constructor de la clase Consumo Cliente.
	 * @param cliente
	 * @param producto
	 * @param cantidadConsumida
	 */
	public ConsumoCliente(@JsonProperty(value = "cliente") Cliente cliente,
			@JsonProperty(value = "producto")Producto producto,
			@JsonProperty(value = "cantidadConsumida")Integer cantidadConsumida)
	{
		this.cliente = cliente;
		this.producto = producto;
		this.cantidadConsumida = cantidadConsumida;
	}
	/**
	 * Método que obtiene el cliente.
	 * @return Cliente, cliente.
	 */
	public Cliente getCliente()
	{
		return cliente;
	}
	/**
	 * Método que establece el cliente.
	 * @param cliente Cliente, nuevo Cliente.
	 */
	public void setCliente(Cliente cliente)
	{
		this.cliente = cliente;
	}
	/**
	 * Método que obtiene el Producto.
	 * @return Producto, producto.
	 */
	public Producto getProducto()
	{
		return producto;
	}
	/**
	 * Método que establece el Producto.
	 * @param producto Producto, nuevo Producto.
	 */
	public void setProducto(Producto producto)
	{
		this.producto = producto;
	}
	/**
	 * Método que obtiene la cantidad de Producto consumido por el Cliente.
	 * @return Integer, cantidad de producto consumido.
	 */
	public Integer getCantidadConsumida()
	{
		return cantidadConsumida;
	}
	/**
	 * Método que establece la cantidad de producto consumido por el cliente.
	 * @param cantidadConsumida Integer, nueva cantidad de producto consumido.
	 */
	public void setCantidadConsumida(Integer cantidadConsumida)
	{
		this.cantidadConsumida = cantidadConsumida;
	}
}
