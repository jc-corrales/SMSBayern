package rest;

import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tm.RotondAndesTM;
import vos.Ingrediente;
import vos.IngredienteBase;

/**
 * Clase que administra los ingredientes, los cuales se comparten en todo RotondAndes
 * @author ASUS
 *
 */
@Path("ingredientes")
@Produces("application/json")
@Consumes("application/json")
public class IngredientesResource
{
	/**
	 * Clase que contiene la información del cuerpo de entrada.
	 * @author ASUS
	 *
	 */
	@XmlRootElement
	public static class RequestBody {
	    @XmlElement Long id;
	    @XmlElement String name;
	    @XmlElement String descripcion;
	    @XmlElement String description;
	    @XmlElement List<IngredienteBase> ingredientesEquivalentes;
	    @XmlElement Integer cantidadDisponible;
	}
	
	/**
	 * Atributo que contiene el contexto.
	 */
	@Context
	private ServletContext context;
	/**
	 * Método que obtiene el path.
	 * @return
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	/**
	 * Método que imrpime mensajes de error.
	 * @param e
	 * @return
	 */
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	/**
	 * Método que agrega un nuevo ingrediente a RotondAdnes
	 * @param ingrediente Ingrediente, toda la información respecto al Ingrediente.
	 * @return
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response agregarIngrediente(Ingrediente ingrediente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			
			Ingrediente res = tm.agregarIngrediente(ingrediente);
			return Response.status( 200 ).entity( res ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
}
