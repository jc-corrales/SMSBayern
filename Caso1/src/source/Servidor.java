package source;


public class Servidor
{
	private Consumidor[] consumidores;
	
	private Buffer buffer;
	
	public Servidor(Integer nConsumidores, Buffer pBuffer)
	{
		consumidores = new Consumidor[nConsumidores];
		this.buffer = pBuffer;
		for(int i = 0; i < consumidores.length; i++)
		{
			consumidores[i] = new Consumidor(buffer);
		}
	}
	
	
	public Consumidor[] getConsumidores() {
		return consumidores;
	}

	public void setConsumidores(Consumidor[] consumidores) {
		this.consumidores = consumidores;
	}


	public Buffer getBuffer() {
		return buffer;
	}


	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}
	
	
}
