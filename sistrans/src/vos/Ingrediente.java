package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Ingrediente extends IngredienteBase{
	
	@JsonProperty(value="descripcion")
	private String descripcion;

	@JsonProperty(value="description")
	private String description;
	@JsonProperty(value="ingredientesEquivalentes")
	private List<IngredienteBase> ingredientesEquivalentes;
	/**
	 * Atributo que contiene la cantidad disponible de este ingrediente en bodega.
	 */
	@JsonProperty(value = "cantidadDisponible")
	private Integer cantidadDisponible;
	
	/**
	 * M�todo constructor de la clase menu
	 * <b>post: </b> Crea el menu con los valores que entran como parametro
	 * @param id - Id del menu.
	 * @param name - Nombre del menu. name != null
	 * @param sabores - lista de String de sabores en minutos del menu.
	 */
	public Ingrediente(@JsonProperty(value="id")Long id, 
			@JsonProperty(value="name")String name,
			@JsonProperty(value="descripcion")String descripcion, 
			@JsonProperty(value = "description") String description, 
			@JsonProperty(value = "ingredientesEquivalentes") List<IngredienteBase> ingredientesEquivalentes,
			@JsonProperty(value = "cantidadDisponible")Integer cantidadDisponible){
		super(id, name);
		this.descripcion = descripcion;
		this.description = description;
		this.ingredientesEquivalentes = ingredientesEquivalentes;
		this.cantidadDisponible = cantidadDisponible;
	}
	
	/**
	 * M�todo getter del atributo sabores
	 * @return colecci�n de sabores del menu 
	 */
	public  String getDescripcion() {
		return descripcion;
	}

	/**
	 * M�todo setter del atributo esCaliente <b> post: </b> El valor condicional de la bebida ha sido cambiado
	 * por el valor que entra como par�metro.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion= descripcion;
	}



	/**
	 * M�todo getter del atributo sabores
	 * @return colecci�n de sabores del menu 
	 */
	public  String getdescription() {
		return description;
	}

	/**
	 * M�todo setter del atributo esCaliente <b> post: </b> El valor condicional de la bebida ha sido cambiado
	 * por el valor que entra como par�metro.
	 */
	public void setdescription(String description) {
		this.description= description;
	}
	/**
	 * M�todo que obtiene los Ingredientes equivalentes a este.
	 * @return Collection<IngredienteBase>, Ingredientes equivalentes.
	 */
	public List<IngredienteBase> getIngredientesEquivalentes(){
		return ingredientesEquivalentes;
	}
	/**
	 * M�todo que establece los Ingredientes equivalentes a este.
	 * @param ingredientes Collection<IngredienteBase>, nuevos Ingredientes equivalentes a este.
	 */
	public void setIngredientesEquivalentes(List<IngredienteBase> ingredientes) {
		this.ingredientesEquivalentes = ingredientes;
	}
	/**
	 * M�todo que obtiene la cantidad disponible de este ingrediente en bodega.
	 * @return Integer, cantidad disponible de este Ingrediente en bodega.
	 */
	public Integer getCantidadDisponible()
	{
		return cantidadDisponible;
	}
	/**
	 * M�todo que establece la cantidad disponible de este ingrediente en bodega.
	 * @param cantidadDisponible Integer, nueva cantidad disponible de este ingrediente en bodega.
	 */
	public void setCantidadDisponible(Integer cantidadDisponible)
	{
		this.cantidadDisponible = cantidadDisponible;
	}
}
