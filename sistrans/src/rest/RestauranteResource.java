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
import vos.Ingrediente;
import vos.Menu;
import vos.ProductoLocal;
import vos.ProductoBase;
import vos.RentabilidadRestaurante;

@Path("restaurantes")
public class RestauranteResource {

	/**
	 * Clase que contiene la información del cuerpo de entrada.
	 * @author ASUS
	 *
	 */
	@XmlRootElement
	public static class RequestBodyProducto {
	    @XmlElement Long id;
	    @XmlElement String nombre;
	    @XmlElement String descripcionEspaniol;
	    @XmlElement String descripcionIngles;
	    @XmlElement String Categoria;
	    @XmlElement List<Ingrediente> ingredientes;
	    @XmlElement Double costoDeProduccion;
	    @XmlElement List<ProductoBase> productosEquivalentes;
	    @XmlElement Double precio;
	    @XmlElement List<String> tiposComida;
	    @XmlElement Integer cantidad;
	    @XmlElement Long idRepresentante;
	    @XmlElement String passwordRepresentante;
	}
	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}

	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}



	@GET
	@Path("{idRest: \\d+}/productos/{idProd: \\d+}")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getProducto(@PathParam("idRest") Long idRest, @PathParam("idProd") Long idProd) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			ProductoLocal producto = tm.darProducto(idProd, idRest);
			System.out.println("FINAL ingredientes: " + producto.getIngredientes().size());
			return Response.status( 200 ).entity( producto ).build( );		
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}



	@POST
	@Path("{id: \\d+}/pedidos/{idPedido: \\d+}")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response despacharPedido(@PathParam("id") Long idRest, @PathParam("idPedido") Long idPed) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {	 
			tm.despacharPedido(idPed);
			return Response.status( 200 ).entity( "{ \"RESPUESTA\": \" Pedido despachado \"}" ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Método que registra las equivalencias entre dos productos para un restaurante.
	 * @param idRestaurante Long, ID del Restaurante dueño de los dos productos.
	 * @param idProducto1 Long, ID del primer producto a relacionar.
	 * @param idProducto2 Long, ID del segundo producto a relacionar.
	 * @return
	 */
	@POST
	@Path("{idRestaurante: \\d+}/producto1/{idProducto1: \\d+}/producto2/{idProducto2: \\d+}")
	public Response registrarProductosEquivalentes(@PathParam("idRestaurante")Long idRestaurante, @PathParam("idProducto1")Long idProducto1, @PathParam("idProducto2")Long idProducto2, RequestBodyProducto entrada)
	{
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {	 
			tm.registrarProductosEquivalentes(idRestaurante, idProducto1, idProducto2, entrada.idRepresentante, entrada.passwordRepresentante);
			return Response.status( 200 ).entity( "{ \"RESPUESTA\": \" Equivalentes registrados \"}" ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	
	/**
	 * Método que registra un nuevo Producto.
	 * @param zona Zona, datos del nuevo Producto a agregar.
	 * @return Response, Producto con toda la información proporcionada.
	 */
	@POST
	@Path("{idRestaurante: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response agregarProducto(@PathParam("idRestaurante") Long idRestaurante, ProductoLocal producto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			ProductoLocal res = tm.agregarProducto(idRestaurante, producto);;
			return Response.status( 200 ).entity( res ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	
	/**
	 * Método que registra una nueva Zona.
	 * @param zona Zona, datos de la Zona.
	 * @return Response, Zona con toda la información proporcionada.
	 */
	@POST
	@Path("{idRestaurante: \\d+}/menus")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response agregarMenu(@PathParam("idRestaurante") Long idRestaurante, Menu menu) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Menu res = tm.registrarMenu(idRestaurante, menu);
			return Response.status( 200 ).entity( res ).build();	
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
	@Path("{idRestaurante: \\d+}/rentabilidadRestaurantes")
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
