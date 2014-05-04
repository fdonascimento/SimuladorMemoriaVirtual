package simulador;

public class Endereco {
	
	private char idPagina;
	private char idProcesso;
	
	public Endereco(char idPagina, char idProcesso){
		this.idPagina = idPagina;
		this.idProcesso = idProcesso;
	}
	
	public Endereco(Endereco endereco){
		this.idPagina = endereco.idPagina;
		this.idProcesso = endereco.idProcesso;
	}
	
	public boolean equals(Endereco endereco){
		if(this.idPagina == endereco.getIdPagina() && this.idProcesso == endereco.getIdProcesso())
			return true;
		else
			return false;
	}

	public char getIdPagina() {
		return idPagina;
	}

	public void setIdPagina(char idPagina) {
		this.idPagina = idPagina;
	}

	public char getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(char idProcesso) {
		this.idProcesso = idProcesso;
	}

	@Override
	public String toString() {
		return idProcesso + "" + idPagina;
	}
}