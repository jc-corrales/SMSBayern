package vos;

public class IngredientesSimilares
{
	private long idIngrediente1;
	
	private long idIngrediente2;
	
	private long idRestaurante;
	
	public IngredientesSimilares(long pIdIngrediente1, long pIdIngrediente2, long pIdRestaurante)
	{
		this.setIdIngrediente1(pIdIngrediente1);
		this.setIdIngrediente2(pIdIngrediente2);
		this.setIdRestaurante(pIdRestaurante);
	}

	public long getIdIngrediente1() {
		return idIngrediente1;
	}

	public void setIdIngrediente1(long idIngrediente1) {
		this.idIngrediente1 = idIngrediente1;
	}

	public long getIdIngrediente2() {
		return idIngrediente2;
	}

	public void setIdIngrediente2(long idIngrediente2) {
		this.idIngrediente2 = idIngrediente2;
	}

	public long getIdRestaurante() {
		return idRestaurante;
	}

	public void setIdRestaurante(long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}
	
}
