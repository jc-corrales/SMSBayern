package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tm.RotondAndesTM;
import vos.Cliente;
import vos.ConsumoCliente;
import vos.EstadisticasPedidos;
import vos.Producto;
//import vos.ProductoBase;

@Path("admin")
public class RotondAndesResource {
	
	@XmlRootElement
	public static class RequestBody {
	    @XmlElement Object parametro;
	}
	
	
	@Context
	private ServletContext context;
	

	@GET
	@Path("productos")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({MediaType.APPLICATION_JSON})
	public Response getProductosPor(@QueryParam("filtro") Integer ident, @QueryParam("parametro") String parametro) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Producto> productos;
		try {
			productos = tm.darProductosPor(ident, parametro);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productos).build();
		
	}
	
	@GET
	@Path("clientes")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getClientes() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Cliente> clientes;
		try {
			clientes = tm.darClientes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(clientes).build();
		
	}
	
	@GET
	@Path("clientes/{id: \\d+}")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getCliente(@PathParam("id") Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Cliente cliente = tm.darCliente(id);
			return Response.status( 200 ).entity( cliente ).build( );		
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	/**
	 * Método que obtiene las estadísticas de los Pedidos de un restaurante mediante el identificador de su Representante.
	 * @param id Long, ID del Representante del restaurante
	 * @return Response, toda la información de la Zona.
	 */
	@GET
	@Path("pedidos")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getEstadisticasProductos() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			List<EstadisticasPedidos> respuesta = tm.darEstadisticasPedidos();
			return Response.status( 200 ).entity( respuesta ).build( );		
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
	@Path("consumo")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getConsumosClientes() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			List<ConsumoCliente> respuesta = tm.darEstadisticasConsumoClientes();
			return Response.status( 200 ).entity( respuesta ).build( );		
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Método que obtiene el consumo un cliente específico de RotondAndes.
	 * @return Response.
	 */
	@GET
	@Path("consumo/{id: \\d+}")
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
	
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	
	
	
}
