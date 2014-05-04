package simulador;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Disco {
	
	private HashMap<Endereco, Pagina> paginas;

	public Disco(){
		paginas = new HashMap<Endereco, Pagina>();
	}
	
	public void adicionarProcesso(Processo processo){
	    Iterator<Pagina> iterator = processo.createIterator();
	    while(iterator.hasNext()){
	        Pagina pagina = iterator.next();
	        Endereco endereco = pagina.getEndereco();
	        paginas.put(endereco, pagina);
	    }
	}

	public void removerProcesso(char idProcesso){
	    List<Endereco> enderecos = new LinkedList<Endereco>();
	    for(Endereco endereco : paginas.keySet()){
	    	enderecos.add(endereco);
	    }

	    for(Endereco endereco : enderecos){
	        if(endereco.getIdProcesso() == idProcesso)
	        	paginas.remove(endereco);
	    } 
	}

	public void atualizarPagina(Pagina pagina){
	    Pagina newPagina = pagina;
	    Endereco endereco = newPagina.getEndereco();

	    paginas.remove(endereco);
	    paginas.put(endereco, newPagina);
	}

	public Pagina buscarPagina(Endereco endereco){
	    return paginas.get(endereco);
	}

	public Iterator<Pagina> createIterator(){
	    return paginas.values().iterator();
	}

}
