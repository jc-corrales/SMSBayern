package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


import tm.RotondAndesTM;
import vos.ConsumoCliente;
import vos.Orden;



/**
 * Clase que expone servicios REST con ruta base: http://<ip o nombre del host>:8080/RotondAndes/rest/clientes/...
 * @author David Bautista
 */

@Path("clientes")
public class ClienteResorce {

	@XmlRootElement
	public static class RequestBody {
	    @XmlElement public Long idProd;
	    @XmlElement public Long idRestProd;
	}
	
	
	@Context
	private ServletContext context;
	
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	

	@POST
	@Path("{id: \\d+}/pedidos")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response agregarPedido(@PathParam("id") Long id, RequestBody request) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Orden orden = tm.agregarUnaOrdenDeUnPedido(id, request.idProd, request.idRestProd);
			 
			return Response.status( 200 ).entity( orden ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	/**
	 * Método que obtiene el consumo de todos los Clientes de RotondAdnes.
	 * @return Response.
	 */
	@GET
	@Path("{id: \\d+}/consumo")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getConsumosClientes(@PathParam("id") Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			List<ConsumoCliente> respuesta = tm.darEstadisticasConsumoDeUnCliente(id);
			return Response.status( 200 ).entity( respuesta ).build( );		
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
}
