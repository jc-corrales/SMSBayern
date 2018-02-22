package source;

public class Mensaje <T>
{
	private T contenido;
	
	private String titulo;
	
	public Mensaje(T contenido, String titulo)
	{
		this.contenido = contenido;
		this.titulo = titulo;
	}

	public T getContenido() {
		return contenido;
	}

	public void setContenido(T contenido) {
		this.contenido = contenido;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
