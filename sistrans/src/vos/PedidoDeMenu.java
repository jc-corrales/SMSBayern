package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoDeMenu 
{
	/**
	 * Atributo que contiene el ID de este pedido de Men�.
	 */
	@JsonProperty(value = "id")
	private Long id;
	/**
	 * Atributo que contiene el Men� de este pedido.
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
	 * M�todo que devuelve el ID de este pedido de men�.
	 * @return Long, ID del Pedido de Men�.
	 */
	public Long getId()
	{
		return id;
	}
	/**
	 * M�todo que establece el ID de este Pedido de Men�.
	 * @param id Long, nuevo ID de este Pedido de Men�.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	/**
	 * M�todo que obtiene el Men� de este Pedido de Men�.
	 * @return Menu, Men� de este pedido.
	 */
	public Menu getMenu()
	{
		return menu;
	}
	/**
	 * M�todo que establece el Men� de este Pedido de Men�.
	 * @param menu Menu, nuevo Men� de este pedido.
	 */
	public void setMenu(Menu menu)
	{
		this.menu = menu;
	}
}
