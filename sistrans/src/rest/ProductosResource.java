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
import vos.Ingrediente;
import vos.Producto;
import vos.ProductoBase;
@Path("productos")
@Produces("application/json")
@Consumes("application/json")
public class ProductosResource
{
	/**
	 * Clase que contiene la información del cuerpo de entrada.
	 * @author ASUS
	 *
	 */
	@XmlRootElement
	public static class RequestBody {
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
	 * Método que registra una nueva Zona.
	 * @param zona Zona, datos de la Zona.
	 * @return Response, Zona con toda la información proporcionada.
	 */
	@POST
	@Path("{idRestaurante: \\d+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response agregarProducto(@PathParam("idRestaurante") Long idRestaurante, Producto producto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Producto res = tm.agregarProducto(idRestaurante, producto);;
			return Response.status( 200 ).entity( res ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Método que obtiene el, o los, Productos más ofrecidos en RotonAndes.
	 * @param idRestaurante
	 * @param producto
	 * @return
	 */
	@GET
	@Path("masOfrecidos")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response obtenerProductoMasOfrecido() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			List<Producto> res = tm.darProductoMasOfrecido();
			return Response.status( 200 ).entity( res ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	/**
	 * Método que obtiene los Productos más vendidos.
	 * @return Response
	 */
	@GET
	@Path("masVendidos")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response obtenerProductoMasVendido() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			List<Producto> res = tm.darProductoMasVendido();
			return Response.status( 200 ).entity( res ).build();	
		}catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
}
