package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaProductosLocal
{
	@JsonProperty(value = "productos")
	private List<ProductoLocal> productos;

	public ListaProductosLocal(
			@JsonProperty(value = "productos")List<ProductoLocal> productos
			)
	{
		this.productos = productos;
	}
	/**
	 * M�todo que obtiene la Lista de Rentabilidades.
	 * @return List<Producto>, Lista de las Rentabilidades de los Restaurantes.
	 */
	public List<ProductoLocal> getProductos() {
		return productos;
	}
	/**
	 * M�todo que establece la Lista de Rentabilidades que entra por par�metro.
	 * @param productos List<Producto>, nueva Lista de Rentabilidades de los Restaurantes.
	 */
	public void setProductos(List<ProductoLocal> productos) {
		this.productos = productos;
	}
}
