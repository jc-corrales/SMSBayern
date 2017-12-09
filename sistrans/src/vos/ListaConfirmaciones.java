package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaConfirmaciones
{
	@JsonProperty(value = "confirmaciones")
	private List<Boolean> confirmaciones;

	public ListaConfirmaciones(
			@JsonProperty(value = "confirmaciones")List<Boolean> confirmaciones
			)
	{
		this.confirmaciones = confirmaciones;
	}
	/**
	 * Método que obtiene la Lista de Rentabilidades.
	 * @return List<RentabilidadRestaurante>, Lista de las Rentabilidades de los Restaurantes.
	 */
	public List<Boolean> getConfirmaciones() {
		return confirmaciones;
	}
	/**
	 * Método que establece la Lista de Rentabilidades que entra por parámetro.
	 * @param rentabilidades List<RentabilidadRestaurante>, nueva Lista de Rentabilidades de los Restaurantes.
	 */
	public void setConfirmaciones(List<Boolean> confirmaciones) {
		this.confirmaciones= confirmaciones;
	}
}
