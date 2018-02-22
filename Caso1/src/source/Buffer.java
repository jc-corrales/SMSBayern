package source;

//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Buffer
{
	private Queue<Object> buffer;
	private int tamanio;
	private Object lleno, vacio;
	
	public Buffer (int tamanio)
	{
		this.setTamanio(tamanio);
		buffer = new LinkedList<Object>();
		setLleno(new Object());
		setVacio(new Object());
	}
	
	public Queue<Object> getBuffer() {
		return buffer;
	}

	public void setBuffer(Queue<Object> buffer) {
		this.buffer = buffer;
	}

	public int getTamanio() {
		return tamanio;
	}

	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}

	public Object getLleno() {
		return lleno;
	}

	public void setLleno(Object lleno) {
		this.lleno = lleno;
	}

	public Object getVacio() {
		return vacio;
	}

	public void setVacio(Object vacio) {
		this.vacio = vacio;
	}
	
	public void almacenar (Integer i)throws ExceptionFullBuffer
	{
		synchronized (lleno)
		{
			if(buffer.size() == tamanio)
			{
//				Espera Pasiva
//				try
//				{
//					lleno.wait();
//				}
//				catch(InterruptedException e)
//				{
//					
//				}
//				Espera Activa
				throw new ExceptionFullBuffer("Buffer Lleno.");
			}
		}
		synchronized (this) 
		{
			buffer.add(i);
		}
		synchronized (vacio) 
		{
			vacio.notify();
		}
	}
	
	public Integer retirar()throws Exception
	{
		synchronized (vacio) {
			while(buffer.size() == 0)
			{
				try
				{
					vacio.wait();
				}
				catch(InterruptedException e)
				{
					;
				}
			}
		}
		Integer i;
		synchronized (this)
		{
			if(buffer.peek() != null)
			{
				throw new Exception("Buffer vacio");
			}
		}
		synchronized (this) {i = (Integer)buffer.poll();}
		synchronized (lleno) {lleno.notify();}
		return i;
	}
}
