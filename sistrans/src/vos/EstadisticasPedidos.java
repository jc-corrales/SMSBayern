package vos;

import org.codehaus.jackson.annotate.*;

public class EstadisticasPedidos
{
	/**
	 * Atributo que contiene el Restaurante.
	 */
	@JsonProperty(value = "restaurante")
	private String restaurante;
	/**
	 * Atributo que contiene el Producto.
	 */
	@JsonProperty(value = "producto")
	private String producto;
	
	/**
	 * Atributo que contiene si el Pedido ha sido servido o no.
	 */
	@JsonProperty(value = "servido")
	private Boolean servido;
	/**
	 * Atributo que contiene el número de veces que este producto ha sido pedido.
	 */
	@JsonProperty(value = "numOrdenados")
	private Integer numOrdenados;
	/**
	 * Atributo que contiene las ganancias totales de este producto.
	 */
	@JsonProperty(value = "ganancias")
	private Double ganancias;
	/**
	 * Método constructor de la clase EstadísticasPedidos.
	 * @param restaurante String, nombre del Restaurante dueño de este Producto.
	 * @param producto String, nombre del Producto ofrecido.
	 * @param servido Boolean, Si el pedido ha sido entregado o no.
	 * @param numOrdenados Integer, número de pedidos Ordenados.
	 * @param ganancias Double, ganancias totales de la venta del producto.
	 */
	public EstadisticasPedidos(@JsonProperty (value = "restaurante")String restaurante,
			@JsonProperty(value = "producto")String producto,
			@JsonProperty(value = "servido")Boolean servido,
			@JsonProperty(value="numOrdenados")Integer numOrdenados,
			@JsonProperty(value = "ganancias")Double ganancias)
	{
		this.restaurante = restaurante;
		this.producto = producto;
		this.servido = servido;
		this.numOrdenados = numOrdenados;
		this.ganancias = ganancias;
	}
	/**
	 * Método que entrega el nombre de Restaurante.
	 * @return String, nombre del Restaurante.
	 */
	public String getRestaurante()
	{
		return restaurante;
	}
	/**
	 * Método que establece el nombre del Restaurante.
	 * @param restaurante
	 */
	public void setRestaurante(String restaurante)
	{
		this.restaurante = restaurante;
	}
	/**
	 * Método que obtiene el nombre del Producto.
	 * @return String, nombre del Producto.
	 */
	public String getProducto()
	{
		return producto;
	}
	/**
	 * Método que establece el nombre del Producto.
	 * @param producto String, nombre del Producto.
	 */
	public void setProdcuto(String producto)
	{
		this.producto = producto;
	}
	/**
	 * Método que obtiene si el producto ha sido servido o no.
	 * @return Boolean, Booleano que determina si el producto ha sido servido o no.
	 */
	public Boolean getEsServido()
	{
		return servido;
	}
	/**
	 * Método que establece si el producto ha sido servido o no.
	 * @param servido Boolean, booleano que determinia si el producto ha sido servido o no.
	 */
	public void setServido(Boolean servido)
	{
		this.servido = servido;
	}
	/**
	 * Método que obtiene el número de este producto ordenados.
	 * @return Integer, número de veces que el producto ha sido ordenado.
	 */
	public Integer getNumOrdenados()
	{
		return numOrdenados;
	}
	/**
	 * Método que establece la cantidad de veces que este producto ha sido ordenado.
	 * @param numOrdenados Integer, número de veces que este producto ha sido ordenado.
	 */
	public void setNumOrdenados(Integer numOrdenados)
	{
		this.numOrdenados = numOrdenados;
	}
	/**
	 * Método que obtiene las ganancias de este producto.
	 * @return Double, las ganancias de este Producto.
	 */
	public Double getGanancias()
	{
		return ganancias;
	}
	/**
	 * Método que establece las ganancias de este producto.
	 * @param ganancias Double, nuevas ganancias de este producto.
	 */
	public void setGanancias(Double ganancias)
	{
		this.ganancias = ganancias;
	}
}
