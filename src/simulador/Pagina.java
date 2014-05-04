package simulador;

public class Pagina implements Comparable<Pagina>{
	
	private Endereco endereco;
	private boolean load;
	
	public Pagina(Endereco endereco){
		this.endereco = endereco;
		load = false;
	}
	
	public Pagina(Pagina pagina){
		this.endereco = pagina.endereco;
		this.load = pagina.load;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public boolean isLoad(){
		return load;
	}
	
	public void load(){
		load = true;
	}
	
	public void unload(){
		load = false;
	}
	
	public boolean equals(Pagina pagina){
		return this.endereco.equals(pagina.endereco);
	}
	
	@Override
	public String toString() {
		return endereco.toString();
	}

	@Override
	public int compareTo(Pagina p) {
		if (this.endereco.getIdProcesso() == p.endereco.getIdProcesso()) {
			if (this.endereco.getIdPagina() > p.endereco.getIdPagina())
				return 1;
			else if (this.endereco.getIdPagina() < p.endereco.getIdPagina())
				return -1;
			else
				return 0;
		}else{
			if(this.endereco.getIdProcesso() > p.endereco.getIdProcesso())
				return 1;
			else if(this.endereco.getIdProcesso() < p.endereco.getIdProcesso())
				return -1;
			else
				return 0;
		}
	}
}