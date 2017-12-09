package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaProductos
{
	@JsonProperty(value = "productos")
	private List<Producto> productos;

	public ListaProductos(
			@JsonProperty(value = "productos")List<Producto> productos
			)
	{
		this.productos = productos;
	}
	/**
	 * Método que obtiene la Lista de Rentabilidades.
	 * @return List<Producto>, Lista de las Rentabilidades de los Restaurantes.
	 */
	public List<Producto> getProductos() {
		return productos;
	}
	/**
	 * Método que establece la Lista de Rentabilidades que entra por parámetro.
	 * @param productos List<Producto>, nueva Lista de Rentabilidades de los Restaurantes.
	 */
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
}
