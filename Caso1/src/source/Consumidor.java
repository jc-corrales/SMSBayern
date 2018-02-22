package source;

public class Consumidor extends Thread
{
	private Buffer buffer;
	
	private Integer iteracionActual;
	
	private Mensaje<Integer> mensaje;
	
	public Consumidor(Buffer buffer)
	{
		this.buffer = buffer;
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
