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


public class Producto extends ProductoBase
{
	

	/**
	 * Costo de Producciódel producto.
	 */
	@JsonProperty(value="costoDeProduccion")
	private Double costoDeProduccion;

	/**
	 * Atributo que contiene  los productos equivalentes.
	 */
	@JsonProperty(value = "productosEquivalentes")
	private List<ProductoBase> productosEquivalentes;
	/**
	 * Atributo que contiene el precio.
	 */
	@JsonProperty(value = "precio")
	private Double precio;
	/**
	 * Atributo que contiene los tipos de comida.
	 */
	@JsonProperty(value = "tiposComida")
	private List<TipoComida> tiposComida;
	/**
	 * Atributo que contiene la cantidad de producto disponible.
	 */
	@JsonProperty(value = "cantidad")
	private Integer cantidad;
	/**
	 * Método constructor de la clase Producto.
	 * @param id
	 * @param nombre
	 * @param costoDeProduccion
	 * @param descripcionEspaniol
	 * @param descripcionIngles
	 * @param ProductosEquivalentes
	 * @param precio
	 * @param tiposComida
	 */
	
	public Producto() {
		super();
	}
	
	public Producto(@JsonProperty(value = "id")Long id, 
			@JsonProperty(value = "nombre") String nombre, 
			@JsonProperty(value = "descripcionEspaniol")String descripcionEspaniol,
			@JsonProperty(value = "descripcionIngles")String descripcionIngles,
			@JsonProperty(value = "costo_prod") Double costoDeProduccion, 
			@JsonProperty(value = "productos_equivalentes") List<ProductoBase> productosEquivalentes, 
			@JsonProperty(value = "precio") Double precio, 
			@JsonProperty(value = "tipos") List<TipoComida> tiposComida,
			@JsonProperty(value = "categoria") String categoria,
			@JsonProperty(value = "ingredientes") List<Ingrediente> ingredientes,
			@JsonProperty(value = "cantidad")Integer cantidad){
		
		super(id, nombre, descripcionEspaniol, descripcionIngles, categoria, ingredientes);
		this.costoDeProduccion = costoDeProduccion;
		this.productosEquivalentes = productosEquivalentes;
		this.precio = precio;
		this.tiposComida = tiposComida;
		this.cantidad = cantidad;
	}
	
	/**
	 * Método que obtiene el costo de producción.
	 * @return Double, Costo de producción de este Producto.
	 */
	public Double getCostoDeProduccion()
	{
		return this.costoDeProduccion;
	}
	/**
	 * Método que establece el costo de producción de este producto.
	 * @param costoDeProduccion Double, Nuevo costo de producción.
	 */
	public void setCostoDeProduccion(Double costoDeProduccion)
	{
		this.costoDeProduccion = costoDeProduccion;
	}
	/**
	 * Método que obtiene el precio de este producto.
	 * @return
	 */
	public Double getPrecio()
	{
		return this.precio;
	}
	/**
	 * Método que establece el precio de este producto.
	 * @param precio Double, Precio nuevo del producto.
	 */
	public void setPrecio(Double precio)
	{
		this.precio = precio;
	}
	/**
	 * Método que obtiene los IDs de los productos equivalentes a este.
	 * @return List<ProductoBase>, Lista de productos Equivalentes.
	 */
	public List<ProductoBase> getProductosEquivalentes()
	{
		return productosEquivalentes;
	}
	/**
	 * Método que establece una lista de IDs de productos equivalentes a este.
	 * @param ProductosEquivalentes List<ProductoBase>, Lista de productos equivalentes.
	 */
	public void setProductosEquivalentes(List<ProductoBase> productosEquivalentes)
	{
		this.productosEquivalentes = productosEquivalentes;
	}
	/**
	 * Método que obtiene los tipos de comida de este producto.
	 * @return List<String>, Tipos de comida de este producto.
	 */
	public List<TipoComida> getTiposComida()
	{
		return this.tiposComida;
	}
	/**
	 * Método que obtiene los tipos de comida.
	 * @param tiposComida
	 */
	public void setTiposComida(List<TipoComida> tiposComida)
	{
		this.tiposComida = tiposComida;
	}
	/**
	 * Método que obtiene la cantidad de este Producto disponible.
	 * @return Integer, Cantidad de producto disponible.
	 */
	public Integer getCantidad()
	{
		return cantidad;
	}
	/**
	 * Método que establece la cantidad de producto disponible.
	 * @param cantidad Integer, nueva cantidad de Producto disponible.
	 */
	public void setCantidad(Integer cantidad)
	{
		this.cantidad = cantidad;
	}
}
