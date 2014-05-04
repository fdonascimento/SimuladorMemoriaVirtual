package simulador;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Processo implements Comparable<Processo>{
	
	private char id;
	private int custo;
	private int tamanho;
	private List<Pagina> paginas;
	
	public Processo(char id, int custo, int tamanho){
		this.id = id;
		this.custo = custo;
		this.tamanho = tamanho;
		paginas = new ArrayList<Pagina>();
		inicializar(tamanho);
	}
	
	private void inicializar(int tamanho){
	    char idPagina = 'A';
	    for(int i = 0; i < tamanho; ++i){
	        Endereco endereco = new Endereco(idPagina, id);
	        Pagina pagina = new Pagina(endereco);
	        paginas.add(pagina);
	        ++idPagina;
	    }
	}
	
	public Iterator<Pagina> createIterator(){
		return paginas.iterator();
	}
	
	public void kill(){
		
	}
	
	public void execute(){
		
	}
	
	public boolean equals(Processo processo){
		return this.id == processo.id;
	}

	public char getId() {
		return id;
	}

	public void setId(char id) {
		this.id = id;
	}

	public int getCusto() {
		return custo;
	}

	public void setCusto(int custo) {
		this.custo = custo;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	
	@Override
	public String toString() {
		return id + "";
	}

	@Override
	public int compareTo(Processo p) {
		if(this.id > p.id)
			return 1;
		else if(this.id < p.id)
			return -1;
		else
			return 0;
	}
	
	
}
