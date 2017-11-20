package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoDeMenu 
{
	/**
	 * Atributo que contiene el ID de este pedido de Menú.
	 */
	@JsonProperty(value = "id")
	private Long id;
	/**
	 * Atributo que contiene el Menú de este pedido.
	 */
	@JsonProperty(value = "menu")
	private Menu menu;
	
	public PedidoDeMenu(@JsonProperty(value = "id") Long id,
			@JsonProperty(value = "menu")Menu menu)
	{
		this.id = id;
		this.menu = menu;
	}
	/**
	 * Método que devuelve el ID de este pedido de menú.
	 * @return Long, ID del Pedido de Menú.
	 */
	public Long getId()
	{
		return id;
	}
	/**
	 * Método que establece el ID de este Pedido de Menú.
	 * @param id Long, nuevo ID de este Pedido de Menú.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	/**
	 * Método que obtiene el Menú de este Pedido de Menú.
	 * @return Menu, Menú de este pedido.
	 */
	public Menu getMenu()
	{
		return menu;
	}
	/**
	 * Método que establece el Menú de este Pedido de Menú.
	 * @param menu Menu, nuevo Menú de este pedido.
	 */
	public void setMenu(Menu menu)
	{
		this.menu = menu;
	}
}
