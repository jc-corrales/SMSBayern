package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Cliente;
import vos.Producto;

@Path("admin")
public class RotondAndesResource {
	
	@Context
	private ServletContext context;
	

	@GET
	@Path("productos/{identificador: \\d+}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getProductosPor(@PathParam("identificador") Integer ident, Object param) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Producto> productos;
		try {
			productos = tm.darProductosPor(ident, param);
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
	
	
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	
	
	
}
