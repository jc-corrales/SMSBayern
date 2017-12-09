package vos;

//import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Clase que contiene la informaci�n de un Restaurante.
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
	 * Atributo que contiene la URL de la p�gina web del Restaurante.
	 */
	@JsonProperty(value="pagina")
	private String pagina;

	/**
	 * Productos del restaurante.
	 */
	@JsonProperty(value = "productos")
	private List<ProductoLocal> productos;
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
	 * Atributo que indica si el Restaurante a�n est� en operaci�n o no.
	 */
	@JsonProperty(value = "estadoOperacion")
	private Boolean estadoOperacion;
	/**
	 * M�todo constructor de la clase Restaurante.
	 * @param id Long, ID del Restaurante.
	 * @param name String, Nombre del Restaurante.
	 * @param pagina String, Direcci�n de la P�gina web del Restaurante.
	 * @param productos List<Producto>, Lista de Productos del Restaurante.
	 * @param tipo TipoComida, Tipo de Comida del Restaurante.
	 * @param precio Double, precio base del Restaurante.
	 * @param enOperacion, Booleano que indica si el Restaurante a�n est� en operaci�n o no.
	 */
	public Restaurante(@JsonProperty(value="id") Long id, 
			@JsonProperty(value="name") String name, 
			@JsonProperty(value="pagina") String pagina,
			@JsonProperty(value = "productos")List<ProductoLocal> productos,
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
	 * M�todo que obtiene el ID de un Restaurante.
	 * @return Long, ID del restaurante.
	 */
	public Long getId() {
		return id;
	}
	/**
	 * M�todo que establece el ID de un Restaurante.
	 * @param id Long, nuevo ID del Restaurante.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * M�todo que obtiene el nombre de un Restaurante.
	 * @return String, nombre del Restaurante.
	 */
	public String getName() {
		return name;
	}
	/**
	 * M�todo que establece el nombre de un Restaurante.
	 * @param name String, nuevo nombre del Restaurante.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * M�todo que obtiene la URL de la p�gina web del Restaurante.
	 * @return String, URL de la p�gina web del Restaurante.
	 */
	public String getPagina() {
		return pagina;
	}
	/**
	 * M�todo que establece la URL de la p�gina web del Restaurante.
	 * @param pagina String, URL de la p�gina web del Restaurante.
	 */
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
//	/**
//	 * M�todo que obtiene las Entradas que ofrece este Restaurante.
//	 * @return Collection<Entrada>, Entradas que ofrece este Restaurante.
//	 */
//	public Collection<Entrada> getEntradas(){
//		return entradas;
//	}
//	/**
//	 * M�todo que establece las Entradas que ofrece este Restaurante.
//	 * @param entradas Collection<Entrada>, nuevas Entradas que ofrece este Restaurante-
//	 */
//	public void SetEntradas(Collection<Entrada> entradas) {
//		this.entradas = entradas;
//	}
//	/**
//	 * M�todo que obtiene los Platos Fuertes que ofrece este Restaurante.
//	 * @return Collection<PlatoFuerte>, Platos Fuertes que ofrece este Restaurante.
//	 */
//	public Collection<PlatoFuerte> getPlatosFuertes(){
//		return platosFuertes;
//	}
//	/**
//	 * M�todo que establece los Platos Fuertes que ofrece este Restaurante.
//	 * @param platosFuertes Collection<PlatoFuerte>, nuevos Platos Fuertes que ofrece este Restaurante.
//	 */
//	public void SetPlatosFuertes(Collection<PlatoFuerte> platosFuertes) {
//		this.platosFuertes = platosFuertes;
//	}
//	/**
//	 * M�todo que obtiene las Bebidas que ofrece este Restaurante.
//	 * @return Collection<Bebida>, Bebidas que ofrece este Restaurante.
//	 */
//	public Collection<Bebida> getBebidas(){
//		return bebidas;
//	}
//	/**
//	 * M�todo que establece las Bebidas que ofrece este Restaurante.
//	 * @param bebidas Collection<Bebida>, nuevas Bebidas que ofrece este Restaurante.
//	 */
//	public void SetBebidas(Collection<Bebida> bebidas) {
//		this.bebidas= bebidas;
//	}
//	/**
//	 * M�todo que obtiene los Postres que ofrece este Restaurante.
//	 * @return Collection<Postre>, Postres que ofrece el Restaurante.
//	 */
//	public Collection<Postre> getPostres(){
//		return postres;
//	}
//	/**
//	 * M�todo que establece los Postres que ofrece este Restaurante.
//	 * @param postres Collection<Postre>, nuevos Postres que ofrece este Restaurante.
//	 */
//	public void SetPostres(Collection<Postre> postres) {
//		this.postres= postres;
//	}
	/**
	 * M�todo que obtiene los productos de un restaurante.
	 * @return List<Producto>, Lista de productos.
	 */
	public List<ProductoLocal> getProductos()
	{
		return productos;
	}
	/**
	 * M�todo que establece una nueva lista de productos de este restaurante.
	 * @param productos List<Producto>, nueva lista de productos.
	 */
	public void setProductos(List<ProductoLocal> productos)
	{
		this.productos = productos;
	}
	/**
	 * M�todo que obtiene el tipo del restaurante.
	 * @return String tipo del Restaurante.
	 */
	public TipoComida getTipoRestaurante()
	{
		return tipo;
	}
	/**
	 * M�todo que establece el tipo del restaurante.
	 * @param tipo String, nuevo tipo del restaurante.
	 */
	public void setTipoRestaurante(TipoComida tipo)
	{
		this.tipo = tipo;
	}
	/**
	 * M�todo que obtiene el precio del Restaurante.
	 * @return Double, Precio del Restaurante.
	 */
	public Double getPrecio()
	{
		return precio;
	}
	/**
	 * M�todo que establece el Precio del Restaurante.
	 * @param precio Double, nuevo precio del Restaurante.
	 */
	public void setPrecio(Double precio)
	{
		this.precio = precio;
	}
	/**
	 * M�todo que obtiene el Estado de Operaci�n del Restaurante.
	 * @return Boolean, Booleano que indica si el Restaurane est� en Operaci�n o no.
	 */
	public Boolean getEstadoOperacion() {
		return estadoOperacion;
	}
	/**
	 * M�todo que establece el Estado de Operaci�n del Restaurante.
	 * @param enOperacion Boolean, nuevo Booleano que determina el estado de Operaci�n del Restaurante.
	 */
	public void setEstadoOperacion(Boolean estadoOperacion) {
		this.estadoOperacion = estadoOperacion;
	}
}