package vos;

import java.util.Date;
import org.codehaus.jackson.annotate.*;

public class Pedido {
	/**
	 * Atributo que contiene el ID de este pedido.
	 */
	@JsonProperty(value = "id")
	private Long id;
//	/**
//	 * Atributo que contiene el cliente dueño de este pedido.
//	 */
//	@JsonProperty(value = "cliente")
//	private Cliente cliente;
	/**
	 * Atributo que contiene el producto pedido.
	 */
	@JsonProperty(value = "producto")
	private ProductoLocal producto; 
	/**
	 * Atributo que contiene la fecha de este pedido.
	 */
	@JsonProperty(value = "fecha")
	private Date fecha;
	
	/**
	 * Atributo que contiene si este pedido ya ha sido servido o no.
	 */
	@JsonProperty(value = "servido")
	private Boolean servido;
	/**
	 * Atributo que contiene el ID del Restaurante al que está asignado el Pedido.
	 */
	@JsonProperty(value = "restaurante")
	private Long idRestaurante;

	/**
	 * Método constructor de la clase Pedido.
	 * @param id Long, ID del pedido.
	 * @param cliente Cliente, Cliente dueño del pedido.
	 * @param producto Producto, producto ordenado.
	 * @param fecha LocalDateTime, fecha en que se hizo el pedido.
	 * @param servido Boolean, Booleano que determina si el pedido ya ha sido entregado o no.
	 * @param restaurante Restaurante, restaurante dueño del pedido.
	 */
	public Pedido(@JsonProperty(value = "id") Long id, 
//			@JsonProperty(value = "cliente") Cliente cliente, 
			@JsonProperty(value = "producto") ProductoLocal producto,
			@JsonProperty(value = "fecha") Date fecha,
			@JsonProperty(value = "servido") Boolean servido,
			@JsonProperty(value = "idRestaurante") Long idRestaurante
			) {
		this.id = id;
//		this.cliente = cliente;
		this.producto = producto;
		this.fecha = fecha; 
		this.servido = servido;
		this.idRestaurante = idRestaurante;
	}
	/**
	 * Método que obtiene el ID del pedido.
	 * @return
	 */
	public Long getId() {
		return this.id;
	}
	/**
	 * Método que establece el ID del pedido.
	 * @param id Long, nuevo ID del pedido.
	 */
	public void setId(Long id) {
		this.id = id;
	}
//	/**
//	 * Método que obtiene el cliente dueño de este pedido.
//	 * @return Cliente, cliente del Pedido.
//	 */
//	public Cliente getCliente() {
//		return this.cliente;
//	}
	/**
	 * Método que obtiene el Producto pedido en este Pedido.
	 * @return Producto, producto del pedido.
	 */
	public ProductoLocal getProducto() {
		return this.producto;
	}
	/**
	 * Método que obtiene la fecha de este Pedido.
	 * @return LocalDateTime, fecha del Pedido.
	 */
	public Date getFecha() {
		return this.fecha;
	}
	/**
	 * Método que obtiene un booleano que determina si el pedido ha sido entregado o no.
	 * @return Boolean, booleano que determina si el pedido ha sido entregado o no.
	 */
	public Boolean getServido() {
		return this.servido;
	}
//	/**
//	 * Método que establece el Cliente de este Pedido.
//	 * @param cliente Cliente, nuevo cliente dueño de este Pedido.
//	 */
//	public void setCliente(Cliente cliente) {
//		this.cliente = cliente; 
//	}
	/**
	 * Método que establece el Producto en este Pedido.
	 * @param producto Producto, nuevo Producto del Pedido.
	 */
	public void setProducto(ProductoLocal producto) {
		this.producto = producto; 
	}
	/**
	 * Método que establece la fecha de este Pedido.
	 * @param fecha LocalDateTime, nueva fecha de este Pedido.
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha; 
	}
	/**
	 * Método que establece si este pedido ha sido servido o no.
	 * @param servido Boolean, 
	 */
	public void setServido(Boolean servido) {
		this.servido = servido; 
	}
	/**
	 * Método que entrega el ID del Restaurante Dueño de este Pedido.
	 * @return Long, ID del Restaurante dueño de este Pedido.
	 */
	public Long getIdRestaurante()
	{
		return this.idRestaurante;
	}
	/**
	 * Método que establece el ID del Restaurante dueño de este Pedido.
	 * @param idRestaurante Long, ID del Restaurante dueño de este Pedido.
	 */
	public void setIdRestaurante(Long idRestaurante)
	{
		this.idRestaurante = idRestaurante;
	}
}
