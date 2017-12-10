package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaPedidosConexion
{
	@JsonProperty(value = "pedidosConexion")
	private List<PedidoConexion> pedidosConexion;

	public ListaPedidosConexion(
			@JsonProperty(value = "pedidosConexion")List<PedidoConexion> pedidosConexion
			)
	{
		this.pedidosConexion = pedidosConexion;
	}
	/**
	 * Método que obtiene la Lista de Rentabilidades.
	 * @return List<Producto>, Lista de las Rentabilidades de los Restaurantes.
	 */
	public List<PedidoConexion> getPedidosConexion() {
		return pedidosConexion;
	}
	/**
	 * Método que establece la Lista de Rentabilidades que entra por parámetro.
	 * @param productos List<Producto>, nueva Lista de Rentabilidades de los Restaurantes.
	 */
	public void setPedidosConexion(List<PedidoConexion> pedidosConexion) {
		this.pedidosConexion = pedidosConexion;
	}
}
