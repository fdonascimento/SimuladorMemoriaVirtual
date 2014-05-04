package estruturas;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import simulador.Endereco;
import simulador.EstruturaDados;

public class ListaMRU implements EstruturaDados{

	private List<Endereco> enderecos; 
	
	public ListaMRU() {
		enderecos = new LinkedList<Endereco>();
	}
	
	@Override
	public void adicionarReferencia(Endereco endereco) {
		if(enderecos.contains(endereco)){
			enderecos.remove(endereco);
			enderecos.add(endereco);
		}else{
	        enderecos.add(endereco);
		}
	}

	@Override
	public void removerReferencia(Endereco endereco) {
		 enderecos.remove(endereco);
	}

	@Override
	public Endereco paginaParaSubstituir() {
		return enderecos.remove(0);
	}

	@Override
	public void removerReferencias(int idProcesso) {
		List<Endereco> clone = new LinkedList<Endereco>(); 
		for(Endereco endereco : enderecos){
			clone.add(endereco);
		}
		
	    for(Endereco endereco : clone){
	        if(endereco.getIdProcesso() == idProcesso)
	            enderecos.remove(endereco);
	    }	
	}

	@Override
	public Iterator<Endereco> createIterator() {
		 return enderecos.iterator();
	}

}
