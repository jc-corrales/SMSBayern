package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import vos.ClienteFrecuente;
import vos.ConsumoCliente;
import vos.EstadisticasPedidos;
import vos.ListaRentabilidad;
import vos.Producto;
import vos.RegistroVentas;
import vos.RentabilidadRestaurante;
import vos.Representante;
import vos.Restaurante;

@Path("admin")
public class RotondAndesResource {
	
	@XmlRootElement
	public static class RequestBody {
	    @XmlElement public Object parametro;
	    @XmlElement public String nombreAdmin;
	    @XmlElement public Long idAdmin;
	    @XmlElement public String passwordAdmin;
	    @XmlElement public String fecha;
	    @XmlElement public String dia;
	}
	public static class RequestBodyUnConsumo{
		@XmlElement public Long idRestaurante;
	    @XmlElement public Integer criterioDeBusqueda;
	    @XmlElement public String ordenDeBusqueda;
	    @XmlElement public String fecha1;
	    @XmlElement public String fecha2;
	    @XmlElement public Long idCliente;
	    @XmlElement public Long idProducto;
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
	
	
	/**
	 * Método que agrega un nuevo Restaurante.
	 * @param restaurante Restaurante, Información del nuevo Restaurante.
	 * @param representante Representante, Información del nuevo Restaurante.
	 * @param idZona Long, ID de la Zona asignada al Restaurante.
	 * @return Restaurante, toda la información del restaurante.
	 */
	@POST
	@Path("restaurantes/idZona/{idZona: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response agregarRestaurante(Representante representante,@PathParam("idZona") Long idZona) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Restaurante res = tm.registrarRestaurante(representante.getRestaurante(), representante, idZona);
			return Response.status( 200 ).entity( res ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Método que agrega un Cliente Frecuente a RotondAndes.
	 * @param cliente ClienteFrecuente, información del Cliente Frecuente.
	 * @return Respone.
	 */
	@POST
	@Path("clientesFrecuentes")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response agregarClienteFrecuente(ClienteFrecuente cliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			ClienteFrecuente res = tm.registrarClienteFrecuente(cliente);
			return Response.status( 200 ).entity( res ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	
	/**
	 * Método que agrega un Cliente Frecuente a RotondAndes.
	 * @param cliente ClienteFrecuente, información del Cliente Frecuente.
	 * @return Respone.
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response agregarAdministrador(RequestBody entrada) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Boolean res = tm.registrarAdministrador(entrada.idAdmin, entrada.passwordAdmin);
			return Response.status( 200 ).entity( res ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Método que establece el Registro de Ventas para un día específico.
	 * @param entrada
	 * @return
	 */
	@POST
	@Path("registroVentas")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response establecerRegistroVentas(RequestBody entrada) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Boolean res = tm.establecerRegistroVentas(entrada.idAdmin, entrada.passwordAdmin, entrada.fecha, entrada.dia);
			return Response.status( 200 ).entity( res ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	/**
	 * Método que los Registros de Ventas de la semana
	 * @return Response.
	 */
	@GET
	@Path("registroVentas")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getRegistrosVentas() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			List<RegistroVentas> respuesta = tm.obtenerRegistroVentas();
			return Response.status( 200 ).entity( respuesta ).build( );		
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Método que obtiene los Clientes que han realizado al menos un consumo en un Restaurante dado.
	 * @param request RequestBodyUnConsumo
	 * @return
	 */
	@POST
	@Path("consumoMinimoClientes")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getClientesConsumoMinimoRestFechas(RequestBodyUnConsumo request) 
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			List<Cliente> respuesta = tm.getClientesConMinUnConsumoEnRangoFechasPorRestaurante(request.idRestaurante, request.fecha1, request.fecha2, request.criterioDeBusqueda, request.ordenDeBusqueda);
			return Response.status( 200 ).entity( respuesta ).build( );		
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	/**
	 * Método que obtiene los Clientes que han realizado al menos un consumo en un Restaurante dado.
	 * @param request RequestBodyUnConsumo
	 * @return
	 */
	@POST
	@Path("consumoMinimoClientes/{idCliente: \\d+}")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getClienteConsumoMinimoRestFechas(@PathParam("idCliente") Long clienteId, RequestBodyUnConsumo request) 
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

	/**
	 * Método que obtiene los Clientes que NO han realizado al menos un consumo en un Restaurante dado.
	 * @param request RequestBodyUnConsumo
	 * @return
	 */
	@POST
	@Path("noConsumoMinimoClientes")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getClientesNoConsumoMinimoRestFechas(RequestBodyUnConsumo request) 
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			List<Cliente> respuesta = tm.getClientesSinMinUnConsumoEnRangoFechasEnRestaurante(request.idRestaurante, request.fecha1, request.fecha2, request.criterioDeBusqueda, request.ordenDeBusqueda);
			return Response.status( 200 ).entity( respuesta ).build( );		
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	/**
	 * Método que obtiene los Clientes que NO han realizado al menos un consumo en un Restaurante dado.
	 * @param request RequestBodyUnConsumo
	 * @return
	 */
	@POST
	@Path("noConsumoMinimoClientes/{idCliente: \\d+}")
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
	/**
	 * Método que retira un Restaurante del Servicio.
	 * @param idRestaurante Long, ID del Restaurante.
	 * @param entrada RequestBody, entrada que contiene la información de las credenciales del Administrador.
	 * @return Response.
	 */
	@POST
	@Path("restaurantes/{idRestaurante: \\d+}/retirarDeServicio")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response retirarRestauranteDelServicio(@PathParam("idRestaurante") Long idRestaurante, RequestBody entrada) 
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Boolean respuesta = tm.retirarRestauranteDelServicio(entrada.idAdmin, entrada.passwordAdmin, idRestaurante);
			return Response.status( 200 ).entity( respuesta ).build( );		
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	/**
	 * Método que obtiene la Rentabilidad de un Restaurante según unos parámetros de consulta.
	 * @param entrada RequesBodyUnConsumo.
	 * @return Response.
	 */
	@POST
	@Path("rentabilidadRestaurantes")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response darRentabilidadRestaurantes(RequestBodyUnConsumo entrada) 
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			ListaRentabilidad respuesta = tm.darRentabilidadRestaurantesUniversal(entrada.fecha1, entrada.fecha2, entrada.criterioDeBusqueda, entrada.idProducto);
			return Response.status( 200 ).entity( respuesta ).build( );		
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Método que consulta la Rentabilidad de un Restaurante.
	 * @param idRestaurante Long, ID del Restaurante a Consultar.
	 * @param entrada RequestBodyUnConsumo.
	 * @return Response.
	 */
	@POST
	@Path("rentabilidadRestaurantes/restaurantes/{idRestaurante: \\d+}")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response darRentabilidadRestaurante(@PathParam("idRestaurante") Long idRestaurante, RequestBodyUnConsumo entrada) 
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			List<RentabilidadRestaurante> respuesta = tm.darRentabilidadRestaurante(entrada.fecha1, entrada.fecha2, entrada.criterioDeBusqueda, entrada.idProducto, idRestaurante);
			return Response.status( 200 ).entity( respuesta ).build( );		
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
}
