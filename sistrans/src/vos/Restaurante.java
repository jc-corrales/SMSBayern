package vos;

//import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Clase que contiene la información de un Restaurante.
 * @author dj.bautista.
 *
 */
public class Restaurante {
	/**
	 * Atributo que contiene el ID del Restaurante.
	 */
	@JsonProperty(value="id")
	private Long id;
	/**
	 * Atributo que contiene el nombre del Restaurante.
	 */
	@JsonProperty(value="name")
	private String name;
	/**
	 * Atributo que contiene la URL de la página web del Restaurante.
	 */
	@JsonProperty(value="pagina")
	private String pagina;

	/**
	 * Productos del restaurante.
	 */
	@JsonProperty(value = "productos")
	private List<Producto> productos;
	/**
	 * Atributo que contiene el tipo de Restaurante.
	 */
	@JsonProperty(value = "tipo")
	private TipoComida tipo;
	/**
	 * Atributo que contiene el precio promedio de este Restaurante.
	 */
	@JsonProperty(value = "precio")
	private Double precio;
	/**
	 * Atributo que indica si el Restaurante aún está en operación o no.
	 */
	@JsonProperty(value = "estadoOperacion")
	private Boolean estadoOperacion;
	/**
	 * Método constructor de la clase Restaurante.
	 * @param id Long, ID del Restaurante.
	 * @param name String, Nombre del Restaurante.
	 * @param pagina String, Dirección de la Página web del Restaurante.
	 * @param productos List<Producto>, Lista de Productos del Restaurante.
	 * @param tipo TipoComida, Tipo de Comida del Restaurante.
	 * @param precio Double, precio base del Restaurante.
	 * @param enOperacion, Booleano que indica si el Restaurante aún está en operación o no.
	 */
	public Restaurante(@JsonProperty(value="id") Long id, 
			@JsonProperty(value="name") String name, 
			@JsonProperty(value="pagina") String pagina,
			@JsonProperty(value = "productos")List<Producto> productos,
			@JsonProperty(value = "tipo")TipoComida tipo,
			@JsonProperty(value = "precio")Double precio,
			@JsonProperty(value = "estadoOperacion") Boolean estadoOperacion
			)
			{
		
		this.id = id;
		this.name = name;
		this.pagina = pagina;
		this.productos = productos;
		this.tipo = tipo;
//		this.bebidas = bebidas;
//		this.entradas = entradas;
//		this.platosFuertes = platosFuertes;
//		this.postres = postres;
		this.estadoOperacion = estadoOperacion;
	}
	/**
	 * Método que obtiene el ID de un Restaurante.
	 * @return Long, ID del restaurante.
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Método que establece el ID de un Restaurante.
	 * @param id Long, nuevo ID del Restaurante.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Método que obtiene el nombre de un Restaurante.
	 * @return String, nombre del Restaurante.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Método que establece el nombre de un Restaurante.
	 * @param name String, nuevo nombre del Restaurante.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Método que obtiene la URL de la página web del Restaurante.
	 * @return String, URL de la página web del Restaurante.
	 */
	public String getPagina() {
		return pagina;
	}
	/**
	 * Método que establece la URL de la página web del Restaurante.
	 * @param pagina String, URL de la página web del Restaurante.
	 */
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
//	/**
//	 * Método que obtiene las Entradas que ofrece este Restaurante.
//	 * @return Collection<Entrada>, Entradas que ofrece este Restaurante.
//	 */
//	public Collection<Entrada> getEntradas(){
//		return entradas;
//	}
//	/**
//	 * Método que establece las Entradas que ofrece este Restaurante.
//	 * @param entradas Collection<Entrada>, nuevas Entradas que ofrece este Restaurante-
//	 */
//	public void SetEntradas(Collection<Entrada> entradas) {
//		this.entradas = entradas;
//	}
//	/**
//	 * Método que obtiene los Platos Fuertes que ofrece este Restaurante.
//	 * @return Collection<PlatoFuerte>, Platos Fuertes que ofrece este Restaurante.
//	 */
//	public Collection<PlatoFuerte> getPlatosFuertes(){
//		return platosFuertes;
//	}
//	/**
//	 * Método que establece los Platos Fuertes que ofrece este Restaurante.
//	 * @param platosFuertes Collection<PlatoFuerte>, nuevos Platos Fuertes que ofrece este Restaurante.
//	 */
//	public void SetPlatosFuertes(Collection<PlatoFuerte> platosFuertes) {
//		this.platosFuertes = platosFuertes;
//	}
//	/**
//	 * Método que obtiene las Bebidas que ofrece este Restaurante.
//	 * @return Collection<Bebida>, Bebidas que ofrece este Restaurante.
//	 */
//	public Collection<Bebida> getBebidas(){
//		return bebidas;
//	}
//	/**
//	 * Método que establece las Bebidas que ofrece este Restaurante.
//	 * @param bebidas Collection<Bebida>, nuevas Bebidas que ofrece este Restaurante.
//	 */
//	public void SetBebidas(Collection<Bebida> bebidas) {
//		this.bebidas= bebidas;
//	}
//	/**
//	 * Método que obtiene los Postres que ofrece este Restaurante.
//	 * @return Collection<Postre>, Postres que ofrece el Restaurante.
//	 */
//	public Collection<Postre> getPostres(){
//		return postres;
//	}
//	/**
//	 * Método que establece los Postres que ofrece este Restaurante.
//	 * @param postres Collection<Postre>, nuevos Postres que ofrece este Restaurante.
//	 */
//	public void SetPostres(Collection<Postre> postres) {
//		this.postres= postres;
//	}
	/**
	 * Método que obtiene los productos de un restaurante.
	 * @return List<Producto>, Lista de productos.
	 */
	public List<Producto> getProductos()
	{
		return productos;
	}
	/**
	 * Método que establece una nueva lista de productos de este restaurante.
	 * @param productos List<Producto>, nueva lista de productos.
	 */
	public void setProductos(List<Producto> productos)
	{
		this.productos = productos;
	}
	/**
	 * Método que obtiene el tipo del restaurante.
	 * @return String tipo del Restaurante.
	 */
	public TipoComida getTipoRestaurante()
	{
		return tipo;
	}
	/**
	 * Método que establece el tipo del restaurante.
	 * @param tipo String, nuevo tipo del restaurante.
	 */
	public void setTipoRestaurante(TipoComida tipo)
	{
		this.tipo = tipo;
	}
	/**
	 * Método que obtiene el precio del Restaurante.
	 * @return Double, Precio del Restaurante.
	 */
	public Double getPrecio()
	{
		return precio;
	}
	/**
	 * Método que establece el Precio del Restaurante.
	 * @param precio Double, nuevo precio del Restaurante.
	 */
	public void setPrecio(Double precio)
	{
		this.precio = precio;
	}
	/**
	 * Método que obtiene el Estado de Operación del Restaurante.
	 * @return Boolean, Booleano que indica si el Restaurane está en Operación o no.
	 */
	public Boolean getEstadoOperacion() {
		return estadoOperacion;
	}
	/**
	 * Método que establece el Estado de Operación del Restaurante.
	 * @param enOperacion Boolean, nuevo Booleano que determina el estado de Operación del Restaurante.
	 */
	public void setEstadoOperacion(Boolean estadoOperacion) {
		this.estadoOperacion = estadoOperacion;
	}
}