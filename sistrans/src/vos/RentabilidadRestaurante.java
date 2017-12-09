package vos;

import org.codehaus.jackson.annotate.*;
/**
 * Clase que contiene la Rentabilidad de un Restaurante.
 * @author jc.corrales
 *
 */
public class RentabilidadRestaurante
{
	/**
	 * Atributo que contiene los Ingresos.
	 */
	@JsonProperty(value = "ingresos")
	private Double ingresos;
	/**
	 * Atributo que contiene los Gastos.
	 */
	@JsonProperty(value = "gastos")
	private Double gastos;
	/**
	 * Atributo que contiene el valor de la Rentabilidad del Restaurante.
	 */
	@JsonProperty(value = "rentabilidad")
	private Double rentabilidad;
	/**
	 * Atributo que contiene la Cantidad de Pedidos realizados.
	 */
	@JsonProperty(value = "cantidadPedidos")
	private Integer cantidadPedidos;
	/**
	 * Atributo que contiene la Categoría de comidas.
	 */
	@JsonProperty(value = "categoria")
	private String categoria;
	/**
	 * Atributo que contiene el producto.
	 */
	@JsonProperty(value = "producto")
	private Producto producto;
	/**
	 * Atributo que contiene la información del Restaurante.
	 */
	@JsonProperty(value = "restaurante")
	private Restaurante restaurante;
	/**
	 * Atributo que contiene la información de la Zona.
	 */
	@JsonProperty(value = "zona")
	private Zona zona;
	/**
	 * Método constructor de la clase Rentabilidad Restaurante.
	 * @param ingresos
	 * @param gastos
	 * @param rentabilidad Double, Valor de la Rentabilidad del Restaurante.
	 * @param cantidadPedidos
	 * @param restaurante Restaurante, Información del Restaurante.
	 * @param String categoria, Categoría de comidas de la consulta.
	 * @param Producto, Producto en base al cual se hace la consulta.
	 */
	public RentabilidadRestaurante(
			@JsonProperty(value = "ingresos")Double ingresos,
			@JsonProperty(value = "gastos")Double gastos,
			@JsonProperty(value = "rentabilidad")Double rentabilidad,
			@JsonProperty(value = "cantidadPedidos")Integer cantidadPedidos,
			@JsonProperty(value = "restaurante")Restaurante restaurante,
			@JsonProperty(value = "categoria")String categoria,
			@JsonProperty(value = "producto")Producto producto,
			@JsonProperty(value = "zona")Zona zona
			)
	{
		this.rentabilidad = rentabilidad;
		this.restaurante = restaurante;
		this.categoria = categoria;
		this.producto = producto;
		this.cantidadPedidos = cantidadPedidos;
		this.ingresos = ingresos;
		this.gastos = gastos;
		this.zona = zona;
	}
	/**
	 * Método que obtiene los ingresos.
	 * @return Double, ingresos.
	 */
	public Double getIngresos()
	{
		return ingresos;
	}
	/**
	 * Método que establece los ingresos.
	 * @param ingresos Double, nuevos ingresos.
	 */
	public void setIngresos(Double ingresos) 
	{
		this.ingresos = ingresos;
	}
	/**
	 * Método que obtiene los Gastos.
	 * @return Double, Gastos.
	 */
	public Double getGastos()
	{
		return gastos;
	}
	/**
	 * Método que establece los Gastos.
	 * @param gastos Double, nuevos Gastos.
	 */
	public void setGastos(Double gastos)
	{
		this.gastos = gastos;
	}
	/**
	 * Método que obtiene la Rentabilidad del Restaurante.
	 * @return Double, Valor de la Rentabilidad del Restaurante.
	 */
	public Double getRentabilidad()
	{
		return rentabilidad;
	}
	/**
	 * Método que establece la Rentabilidad del Restaruante.
	 * @param rentabilidad Double, nueva Rentabilidad del Restaurante.
	 */
	public void setRentabilidad(Double rentabilidad)
	{
		this.rentabilidad = rentabilidad;
	}
	/**
	 * Método que obtiene la cantidad de Pedidos realizados.
	 * @return Integer, cantidad de Pedidos realizados.
	 */
	public Integer getCantidadPedidos()
	{
		return cantidadPedidos;
	}
	/**
	 * Método que establece la cantidad de Pedidos realizados.
	 * @param cantidadPedidos Integer, nueva cantidad de Pedidos relizados.
	 */
	public void setCantidadPedidos(Integer cantidadPedidos)
	{
		this.cantidadPedidos = cantidadPedidos;
	}
	/**
	 * Método que obtiene la Categoría de comidas en base a la cual se hace esta consulta.
	 * @return String, nombre de la Categoría de comidas.
	 */
	public String getCategoria() {
		return categoria;
	}
	/**
	 * Método que establece la Categoría de comidas en base a la cual se hace esta consulta.
	 * @param categoria String, nueva categoría de comidas.
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	/**
	 * Método que obtiene el Producto de referencia de la Consulta.
	 * @return Producto, toda la información del Producto.
	 */
	public Producto getProducto() {
		return producto;
	}
	/**
	 * Método que establece el Producto de referencia de la Consulta.
	 * @param producto Producto, nuevo Producto de referencia de la Consulta.
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	/**
	 * Método que obtiene el Restaurante.
	 * @return Restaurante, Información del Restaurante cuya rentabilidad se consulta.
	 */
	public Restaurante getRestaurante()
	{
		return restaurante;
	}
	/**
	 * Método que establece la información del Restaurante.
	 * @param restaurante Restaurante, nueva información del Restaurante cuya rentabilidad se consulta.
	 */
	public void setRestaurante(Restaurante restaurante)
	{
		this.restaurante = restaurante;
	}
	/**
	 * Método que obtiene la información de la Zona.
	 * @return Zona, información de la Zona.
	 */
	public Zona getZona()
	{
		return zona;
	}
	/**
	 * Método que establece la información de la Zona.
	 * @param zona Zona, nueva Información de la Zona.
	 */
	public void setZona(Zona zona)
	{
		this.zona = zona;
	}
}
