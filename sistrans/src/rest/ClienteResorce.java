package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
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
import vos.Cliente;
import vos.ClienteFrecuente;
import vos.ConsumoCliente;
import vos.Orden;
import vos.Pedido;
import vos.PedidoDeMenu;



/**
 * Clase que expone servicios REST con ruta base: http://<ip o nombre del host>:8080/RotondAndes/rest/clientes/...
 * @author David Bautista
 */

@Path("clientes")
public class ClienteResorce {

	@XmlRootElement
	public static class RequestBody {
	    @XmlElement public Long idProd;
	    @XmlElement public Long idRestaurante;
	    @XmlElement public Long idMenu;
	}
	
	
	@Context
	private ServletContext context;
	
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	/**
	 * Método que ordena un Pedido, asignándolo a una Orden previamente existente.
	 * @param id Long, ID del cliente.
	 * @param idOrden Long, ID de la Orden a la cual asignar el Pedido.
	 * @param request RequestBody
	 * @return Pedido, información del Pedido recientemente creado.
	 */
	@POST
	@Path("{id: \\d+}/ordenes/{idOrden: \\d+}/pedidos")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response agregarPedidoProducto(@PathParam("id") Long id, @PathParam("idOrden")Long idOrden, RequestBody request) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Pedido pedido = tm.registrarPedido(id, request.idProd, request.idRestaurante, idOrden);
			 
			return Response.status( 200 ).entity( pedido ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	/**
	 * Método que ordena un Pedido de Menú, asignándolo a una Orden previamente existente.
	 * @param id Long, ID del cliente.
	 * @param idOrden Long, ID de la Orden a la cual asignar el Pedido.
	 * @param request RequestBody
	 * @return Pedido, información del Pedido recientemente creado.
	 */
	@POST
	@Path("{id: \\d+}/ordenes/{idOrden: \\d+}/menus")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response agregarPedidoMenu(@PathParam("id") Long id, @PathParam("idOrden")Long idOrden, RequestBody request) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			PedidoDeMenu pedido = tm.registrarPedidoMenu(id, request.idMenu, request.idRestaurante, idOrden);
			 
			return Response.status( 200 ).entity( pedido ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	/**
	 * Método que registra una nueva Orden vacía en el sistema.
	 * @param id Long, ID del CLiente.
	 * @param orden Orden, información de la Orden.
	 * @return Response.
	 */
	@POST
	@Path("{id: \\d+}/ordenes")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response agregarOrden(@PathParam("id") Long id, Orden orden) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Cliente cliente = new Cliente();
			cliente.setId(id);
			orden.setCliente(cliente);
			orden.setCostoTotal((double) 0);
			Orden respuesta = tm.registrarNuevaOrden(orden);
			 
			return Response.status( 200 ).entity( respuesta ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Método que confirma una Orden.
	 * @param idCliente Long, ID del cliente dueño de la Orden.
	 * @param idOrden Long, ID de la Orden a confirmar.
	 * @return Boolean, booleano que indica si la transacción fue exitosa o no.
	 */
	@POST
	@Path("{id: \\d+}/ordenes/{idOrden: \\d+}")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response confirmarOrden(@PathParam("id") Long idCliente, @PathParam("idOrden") Long idOrden) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Boolean respuesta = tm.confirmarOrden(idOrden, idCliente);
			 
			return Response.status( 200 ).entity( respuesta ).build();	
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
	/**
	 * Método que agrega un Cliente a RotondAndes.
	 * @param cliente ClienteFrecuente, información del Cliente.
	 * @return Respone.
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response agregarCliente(Cliente cliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Cliente res = tm.registrarCliente(cliente);
			return Response.status( 200 ).entity( res ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
}
