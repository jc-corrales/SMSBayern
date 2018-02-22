package source;

public class Productor extends Thread
{
	private Buffer buffer;
	
	private Integer nIteraciones;
	
	private Integer iteracionActual;
	
	private Mensaje<Integer> mensaje;
	
	public Productor(Buffer buffer, Integer nIteraciones)
	{
		this.buffer = buffer;
		this.setnIteraciones(nIteraciones);
		this.iteracionActual = 0;
	}
	
	
	@Override
	public void run()
	{
		;
	}




	public Buffer getBuffer() {
		return buffer;
	}




	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}


	public Integer getnIteraciones() {
		return nIteraciones;
	}


	public void setnIteraciones(Integer nIteraciones) {
		this.nIteraciones = nIteraciones;
	}


	public Integer getIteracionActual() {
		return iteracionActual;
	}


	public void setIteracionActual(Integer iteracionActual) {
		this.iteracionActual = iteracionActual;
	}


	public Mensaje<Integer> getMensaje() {
		return mensaje;
	}


	public void setMensaje(Mensaje<Integer> mensaje) {
		this.mensaje = mensaje;
	}
}
