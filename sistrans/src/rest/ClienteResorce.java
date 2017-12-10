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

import rest.RotondAndesResource.RequestBodyUnConsumo;
import tm.RotondAndesTM;
import vos.Cliente;
import vos.ConsumoCliente;
import vos.ListaPedidosConexion;
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
	    @XmlElement public String criterioDeBusqueda;
	    @XmlElement public String ordenDeBusqueda;
	    @XmlElement public String fecha1;
	    @XmlElement public String fecha2;
	    @XmlElement public Long idPedido;
	    @XmlElement public Boolean grupo1;
	    @XmlElement public Boolean grupo2;
	    @XmlElement public Boolean grupo3;
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
			ListaPedidosConexion pedidos = tm.registrarPedidoProductoUniversal(request.idPedido, request.idRestaurante, idOrden, id, request.idProd, request.grupo1, request.grupo2, request.grupo3);
			return Response.status( 200 ).entity( pedidos ).build();	
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
	
	//---------------------------------------------------	
		//	Requerimiento: RFC9
		//---------------------------------------------------
		
//		/**
//		 * Métdodo que recibe una lista de clientes de RotondAndes
//		 * @param idRestaurante id del restaurante determinado
//		 * @param fecha1 fecha inicial
//		 * @param fecha2 fecha final
//		 * @param orderBy como decea el usuario organizar los resultados
//		 * @param groupBy como decea el usuario agrupar los resultados
//		 * @param idUsuario el idUsuario para autorización
//		 * @param contraseniaa la contraseña del usuario para autorización
//		 * @return Clientes
//		 * @throws Exception 
//		 */
//		
//		@GET
//		@Path("{id: \\d+}/{fecha1: \\d+}/{fecha2: \\d+}/{orderBy: \\d+}/{groupBy: \\d+}/{idUsuario: \\d+}/{contrasenia: \\d+}")
//		@Produces( { MediaType.APPLICATION_JSON } )
//		public Response getClientesConsumoMinimoRestFechas(@PathParam("id") Long idRestaurante, RequestBody request) 
//		{
//			RotondAndesTM tm = new RotondAndesTM(getPath());
//			try {
//				List<Cliente> respuesta = tm.getClientesConMinUnConsumoEnRangoFechasPorRestaurante(idRestaurante, fecha1, fecha2, criterio, orderBy)
//				return Response.status( 200 ).entity( respuesta ).build( );		
//			}catch( Exception e )
//			{
//				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
//			}
//		}
		
		/**
		 * Método que obtiene los Clientes que han realizado al menos un consumo en un Restaurante dado.
		 * @param request RequestBodyUnConsumo
		 * @return
		 */
		@POST
		@Path("{idCliente: \\d+}/consumoMinimoClientes/")
		@Produces( { MediaType.APPLICATION_JSON } )
		public Response getClientesConsumoMinimoRestFechas(@PathParam("idCliente") Long clienteId, RequestBodyUnConsumo request) 
		{
			RotondAndesTM tm = new RotondAndesTM(getPath());
			try {
				List<Cliente> respuesta = tm.getClienteConMinUnConsumoEnRangoFechasPorRestaurante(clienteId, request.idRestaurante, request.fecha1, request.fecha2, request.criterioDeBusqueda, request.ordenDeBusqueda);
				return Response.status( 200 ).entity( respuesta ).build( );		
			}catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}
		
		
		//---------------------------------------------------	
		//	Requerimiento: RFC10
		//---------------------------------------------------
		
		/**
		 * Método que obtiene los Clientes que NO han realizado al menos un consumo en un Restaurante dado.
		 * @param request RequestBodyUnConsumo
		 * @return
		 */
		@POST
		@Path("{idCliente: \\d+}/noConsumoMinimoClientes")
		@Produces( { MediaType.APPLICATION_JSON } )
		public Response getClienteNoConsumoMinimoRestFechas(@PathParam("idCliente") Long clienteId, RequestBodyUnConsumo request) 
		{
			RotondAndesTM tm = new RotondAndesTM(getPath());
			try {
				List<Cliente> respuesta = tm.getClienteSinMinUnConsumoEnRangoFechasPorRestaurante(clienteId, request.idRestaurante, request.fecha1, request.fecha2, request.criterioDeBusqueda, request.ordenDeBusqueda);
				return Response.status( 200 ).entity( respuesta ).build( );		
			}catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}
		
		//---------------------------------------------------	
		//	Requerimiento: RF12
		//---------------------------------------------------

		/**
		 * RFC12 Consultar los buenos clientes
		 * @param String tipo
		 * @param Long idUsuario
		 * @param String contrasenia
		 * @resp List<Cliente> de un tipo dado
		 * 1.5 SMDLV = 1.5 * 737.717 = 1106.5755
		 */
		@GET
		@Path("{tipo: \\\\d+}/{idUsuario: \\\\d+}/{contrasenia: \\\\d+}/")
		@Produces( { MediaType.APPLICATION_JSON } )
		public Response getBuenosClientesTipo(@PathParam("tipo") String tipo, @PathParam("idUsuario") Long idUsuario, @PathParam("contrasenia") String contrasenia) 
		{
			RotondAndesTM tm = new RotondAndesTM(getPath());
			try {
				List<Cliente> respuesta = tm.getBuenosClientesTipo(tipo, idUsuario, contrasenia);
				return Response.status( 200 ).entity( respuesta ).build( );		
			}catch( Exception e )
			{
				return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
			}
		}
}