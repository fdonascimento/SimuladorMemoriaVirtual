package estruturas;

import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;

import simulador.Endereco;
import simulador.EstruturaDados;

public class Fila implements EstruturaDados{
	
	private Queue<Endereco> enderecos;

	public Fila(){
		enderecos = new LinkedList<Endereco>();
	}
	
	public void adicionarReferencia(Endereco endereco){
	    if(!enderecos.contains(endereco))
	        enderecos.add(endereco);
	}

	public void removerReferencia(Endereco endereco){
	    enderecos.remove(endereco);
	}

	public Endereco paginaParaSubstituir(){
	    return enderecos.remove();
	}

	public void removerReferencias(int idProcesso){
		List<Endereco> clone = new LinkedList<Endereco>(); 
		for(Endereco endereco : enderecos){
			clone.add(endereco);
		}
		
		
	    for(Endereco endereco : clone){
	        if(endereco.getIdProcesso() == idProcesso)
	            enderecos.remove(endereco);
	    }	    
	}

	public Iterator<Endereco> createIterator(){
	    return enderecos.iterator();
	}

}
