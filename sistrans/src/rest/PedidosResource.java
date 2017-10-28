package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tm.RotondAndesTM;
import vos.EstadisticasPedidos;

@Path("pedidos")
@Produces("application/json")
@Consumes("application/json")
public class PedidosResource
{
	/**
	 * Clase que contiene la información del cuerpo de entrada.
	 * @author Juan Carlos Corrales
	 *
	 */
	@XmlRootElement
	public static class RequestBody {
	    @XmlElement Long id;
	    @XmlElement String nombre;
	    @XmlElement Boolean esEspacioAbierto;
	    @XmlElement Integer capacidad;
	    @XmlElement Boolean esIncluyente;
	    @XmlElement List<String> condiciones;
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
	
//	/**
//	 * Método que obtiene las estadísticas de los Pedidos de un restaurante mediante el identificador de su Representante.
//	 * @param id Long, ID del Representante del restaurante
//	 * @return Response, toda la información de la Zona.
//	 */
//	@GET
//	@Path("\"{representanteId:\\\\d+}/{password:\\\\d+}\"")
//	@Produces( { MediaType.APPLICATION_JSON } )
//	public Response getZona(@PathParam("representanteId") Long id, @PathParam("password") String contrasenia) {
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		try {
//			System.out.println("ENTRO A METODO RESOURCE");
//			List<EstadisticasPedidos> respuesta = tm.darEstadisticasPedidosPorRestaurante(id, contrasenia);
//			return Response.status( 200 ).entity( respuesta ).build( );		
//		}catch( Exception e )
//		{
//			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
//		}
//	}
	
	/**
	 * Método que obtiene las estadísticas de los Pedidos de un restaurante mediante el identificador de su Representante.
	 * @param id Long, ID del Representante del restaurante
	 * @return Response, toda la información de la Zona.
	 */
	@GET
	@Path("{restauranteId: \\d+}")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getEstadisticasPedidosPorRestaurante(@PathParam("restauranteId") Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			List<EstadisticasPedidos> respuesta = tm.darEstadisticasPedidosPorRestaurante(id);
			return Response.status( 200 ).entity( respuesta ).build( );		
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	@DELETE
	@Path("{pedidoId: \\d+}")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response cancelarPedido(@PathParam("pedidoId") Long idPedido)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.cancelarPedido(idPedido);
//			List<EstadisticasPedidos> respuesta = tm.darEstadisticasPedidosPorRestaurante(id);
			return Response.status( 200 ).entity( null ).build( );		
		}catch( Exception e )
		{
			return Response.status( 412 ).entity( doErrorMessage( e ) ).build( );
		}
	}
}
