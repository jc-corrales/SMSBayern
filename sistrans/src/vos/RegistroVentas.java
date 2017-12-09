package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RegistroVentas
{
	/**
	 * Atributo que contiene el nombre del día de la semana.
	 */
	@JsonProperty(value = "dia")
	private String dia;
	/**
	 * Atributo que contiene el Restaurante más frecuentado.
	 */
	@JsonProperty(value="restauranteMasFrecuentado")
	private Restaurante restauranteMasFrecuentado;
	/**
	 * Atributo que contiene le Restaurante menos frecuentado.
	 */
	@JsonProperty(value="restauranteMenosFrecuentado")
	private Restaurante restauranteMenosFrecuentado;
	/**
	 * Atributo que contiene el Producto más consumido.
	 */
	@JsonProperty(value="productoMasConsumido")
	private ProductoLocal productoMasConsumido;
	/**
	 * Atributo que contiene el producto menos consumido.
	 */
	@JsonProperty(value="productoMenosConsumido")
	private ProductoLocal productoMenosConsumido;
	/**
	 * Método constructor de la Clase.
	 * @param restauranteMasFrecuentado Restaurante Más Frecuentado.
	 * @param restauranteMenosFrecuentado Restaurante Menos Frecuentado.
	 * @param productoMasConsumido Producto Más Consumido.
	 * @param productoMenosConsumido Producto Menos Consumido.
	 */
	public RegistroVentas(
			@JsonProperty(value = "dia") String dia,
			@JsonProperty(value="restauranteMasFrecuentado")Restaurante restauranteMasFrecuentado,
			@JsonProperty(value="restauranteMenosFrecuentado")Restaurante restauranteMenosFrecuentado,
			@JsonProperty(value="productoMasFrecuentado")ProductoLocal productoMasConsumido,
			@JsonProperty(value="productoMenosFrecuentado")ProductoLocal productoMenosConsumido)
	{
		this.dia = dia;
		this.restauranteMasFrecuentado = restauranteMasFrecuentado;
		this.restauranteMenosFrecuentado = restauranteMenosFrecuentado;
		this.productoMasConsumido = productoMasConsumido;
		this.productoMenosConsumido = productoMenosConsumido;
	}
	
	public Restaurante getRestauranteMasFrecuentado() {
		return restauranteMasFrecuentado;
	}

	public void setRestauranteMasFrecuentado(Restaurante restauranteMasFrecuentado) {
		this.restauranteMasFrecuentado = restauranteMasFrecuentado;
	}

	public Restaurante getRestauranteMenosFrecuentado() {
		return restauranteMenosFrecuentado;
	}

	public void setRestauranteMenosFrecuentado(Restaurante restauranteMenosFrecuentado) {
		this.restauranteMenosFrecuentado = restauranteMenosFrecuentado;
	}

	public ProductoLocal getProductoMasConsumido() {
		return productoMasConsumido;
	}

	public void setProductoMasConsumido(ProductoLocal productoMasConsumido) {
		this.productoMasConsumido = productoMasConsumido;
	}

	public ProductoLocal getProductoMenosConsumido() {
		return productoMenosConsumido;
	}

	public void setProductoMenosConsumido(ProductoLocal productoMenosConsumido) {
		this.productoMenosConsumido = productoMenosConsumido;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}
}
