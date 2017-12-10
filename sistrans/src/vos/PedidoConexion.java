package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoConexion
{
	/**
	 * Atributo que indica si este pedido va destinado al grupo 1:
	 * B - 10
	 */
	private Boolean grupo1;
	/**
	 * Atributo que indica si este pedido va destinado al grupo 2:
	 * B - 
	 */
	private Boolean grupo2;
	/**
	 * Atributo que indica si este pedido va destinado al grupo 3:
	 * C - 03
	 */
	private Boolean grupo3;
	/**
	 * Atributo que contiene el ID del Pedido.
	 */
	@JsonProperty(value = "id")
	private Long id;
	/**
	 * Atributo que contiene la Fecha del Pedido.
	 */
	@JsonProperty(value = "fecha")
	private Date fecha;
	/**
	 * Atributo que contiene el ID del Producto del Pedido.
	 */
	@JsonProperty(value = "idProducto")
	private Long idProducto;
	/**
	 * Atributo que contiene el nombre del Producto del Pedido.
	 */
	@JsonProperty(value = "nombreProducto")
	private String nombreProducto;
	/**
	 * Atributo que contiene el ID del Restaurante.
	 */
	@JsonProperty(value = "idRestaurante")
	private Long idRestaurante;
	/**
	 * Atributo que contiene el Nombre del Restaurante.
	 */
	@JsonProperty(value = "nombreRestaurante")
	private String nombreRestaurante;
	/**
	 * Atributo que contiene el ID del Cliente del Restaurante.
	 */
	@JsonProperty(value = "idCliente")
	private Long idCliente;
	/**
	 * Atributo que contiene el Nombre del Cliente.
	 */
	@JsonProperty(value = "nombreCliente")
	private String nombreCliente;
	/**
	 * Atributo que contiene el ID de la Orden de este Pedido.
	 */
	@JsonProperty(value = "idOrden")
	private Long idOrden;
	/**
	 * Atributo que contiene el hecho de si este pedido ha sido servido o no.
	 */
	@JsonProperty(value = "servido")
	private Boolean servido;
	/**
	 * Atributo que contiene el ID del Menú de este Pedido (si aplica).
	 */
	@JsonProperty(value = "idMenu")
	private Long idMenu;
	/**
	 * Atributo que contiene el nombre del Menú de este Pedido (si aplica).
	 */
	@JsonProperty(value = "nombreMenu")
	private String nombreMenu;
	/**
	 * Atributo que contiene el ID de la Mesa.
	 */
	@JsonProperty(value = "idMesa")
	private Long idMesa;
	/**
	 * Atibuto que contiene el costo de Producción.
	 */
	@JsonProperty(value = "costoProduccion")
	private Double costoProduccion;
	/**
	 * Atributo que contiene el Precio.
	 */
	@JsonProperty(value = "precio")
	private Double precio;
	/**
	 * Atributo que contiene el nombre de la Categoría.
	 */
	@JsonProperty(value = "categoria")
	private String categoria;
	/**
	 * Atributo que contiene la descripción del Producto, o Menú, en español.
	 */
	@JsonProperty(value = "descripcionEspaniol")
	private String descripcionEspaniol;
	/**
	 * Atributo que contiene la descripción del Producto, o Menú, en Inglés.
	 */
	@JsonProperty(value = "descripcionIngles")
	private String descripcionIngles;
//	/**
//	 * Atributo que contiene la información de la cantidad Disponible del Producto o Menú en stock.
//	 */
//	@JsonProperty(value = "cantidadDisponible")
//	private Integer cantidadDisponible;
	/**
	  * Método cosntructor de la clase.
	 * Nota: Si hay un idProducto, no debería haber un idMenu.
	 * Así como también: Si hay un idMenu, no debería haber un idProducto.
	 * @param id Long, ID del Pedido.
	 * @param fecha Date, fecha del Pedido.
	 * @param idProducto Long, ID del Producto pedido.
	 * @param nombreProducto String, nombre del Producto pedido.
	 * @param idRestaurante Long, ID del Restaurante al cual el Pedido fue pedido.
	 * @param nombreRestaurante String, nombre del Restaurante al cual el Pedido fue pedido.
	 * @param idCliente Long, ID del Cliente dueño del Pedido.
	 * @param nombreCliente String, nombre del Cliente dueño del Pedido.
	 * @param idOrden Long, Id de la Orden a la que pertenece el Pedido(si aplica).
	 * @param servido Boolean, Booleano que indica si el Pedido ha sido servido o no.
	 * @param idMenu Long, ID del Menú que fue Pedido, si aplica.
	 * @param idMesa Long, Id de la Mesa del Pedido.
	 * @param costoProduccion Double, valor del costo de producción.
	 * @param precio Double, valor del Precio.
	 * @param categoria String, Categoría del Producto (si aplica).
	 * @param descripcionEspaniol String, descripción en español del Producto o Menú.
	 * @param descripcionIngles String, descrición en inglés del Producto o Menú.
	 * @param cantidadDisponible Integer, cantidad de Producto o Menú disponible.
	 */
	public PedidoConexion(
			@JsonProperty(value = "id")
			Long id,
			@JsonProperty(value = "fecha")
			Date fecha,
			@JsonProperty(value = "idProducto")
			Long idProducto,
			@JsonProperty(value = "nombreProducto")
			String nombreProducto,
			@JsonProperty(value = "idRestaurante")
			Long idRestaurante,
			@JsonProperty(value = "nombreRestaurante")
			String nombreRestaurante,
			@JsonProperty(value = "idCliente")
			Long idCliente,
			@JsonProperty(value = "nombreCliente")
			String nombreCliente,
			@JsonProperty(value = "idOrden")
			Long idOrden,
			@JsonProperty(value = "servido")
			Boolean servido,
			@JsonProperty(value = "idMenu")
			Long idMenu,
			@JsonProperty(value = "nombreMenu")
			String nombreMenu,
			@JsonProperty(value = "idMesa")
			Long idMesa,
			@JsonProperty(value = "costoProduccion")
			Double costoProduccion,
			@JsonProperty(value = "precio")
			Double precio,
			@JsonProperty(value = "categoria")
			String categoria,
			@JsonProperty(value = "descripcionEspaniol")
			String descripcionEspaniol,
			@JsonProperty(value = "descripcionIngles")
			String descripcionIngles
//			@JsonProperty(value = "cantidadDisponible")
//			Integer cantidadDisponible
			)
	{
		this.id = id;
		this.fecha = fecha;
		this.idProducto = idProducto;
		this.nombreProducto = nombreProducto;
		this.idRestaurante = idRestaurante;
		this.nombreRestaurante = nombreRestaurante;
		this.idCliente = idCliente;
		this.nombreCliente = nombreCliente;
		this.idOrden = idOrden;
		this.servido = servido;
		this.idMenu = idMenu;
		this.nombreMenu = nombreMenu;
		this.idMesa = idMesa;
		this.costoProduccion = costoProduccion;
		this.precio = precio;
		this.categoria = categoria;
		this.descripcionEspaniol = descripcionEspaniol;
		this.descripcionIngles = descripcionIngles;
//		this.setCantidadDisponible(cantidadDisponible);
		setGrupo1(false);
		setGrupo2(false);
		setGrupo3(false);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Long getIdRestaurante() {
		return idRestaurante;
	}

	public void setIdRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

	public String getNombreRestaurante() {
		return nombreRestaurante;
	}

	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public Long getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(Long idOrden) {
		this.idOrden = idOrden;
	}

	public Boolean getServido() {
		return servido;
	}

	public void setServido(Boolean servido) {
		this.servido = servido;
	}

	public Long getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(Long idMenu) {
		this.idMenu = idMenu;
	}

	public String getNombreMenu() {
		return nombreMenu;
	}

	public void setNombreMenu(String nombreMenu) {
		this.nombreMenu = nombreMenu;
	}

	public Long getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(Long idMesa) {
		this.idMesa = idMesa;
	}

	public Double getCostoProduccion() {
		return costoProduccion;
	}

	public void setCostoProduccion(Double costoProduccion) {
		this.costoProduccion = costoProduccion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescripcionEspaniol() {
		return descripcionEspaniol;
	}

	public void setDescripcionEspaniol(String descripcionEspaniol) {
		this.descripcionEspaniol = descripcionEspaniol;
	}

	public String getDescripcionIngles() {
		return descripcionIngles;
	}

	public void setDescripcionIngles(String descripcionIngles) {
		this.descripcionIngles = descripcionIngles;
	}

//	public Integer getCantidadDisponible() {
//		return cantidadDisponible;
//	}
//
//	public void setCantidadDisponible(Integer cantidadDisponible) {
//		this.cantidadDisponible = cantidadDisponible;
//	}

	public Boolean getGrupo1() {
		return grupo1;
	}

	public void setGrupo1(Boolean grupo1) {
		this.grupo1 = grupo1;
	}

	public Boolean getGrupo2() {
		return grupo2;
	}

	public void setGrupo2(Boolean grupo2) {
		this.grupo2 = grupo2;
	}

	public Boolean getGrupo3() {
		return grupo3;
	}

	public void setGrupo3(Boolean grupo3) {
		this.grupo3 = grupo3;
	}
}