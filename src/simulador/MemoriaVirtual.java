package simulador;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

import exceptions.MemoriaVirtualCheiaException;

public class MemoriaVirtual {
	
	private int tamanhoMaximo;
	private List<Endereco> enderecos;
	
	public MemoriaVirtual(){
		enderecos = new LinkedList<Endereco>();
		tamanhoMaximo = MemoriaRAM.NUMERO_MOLDURAS * 2;
	}
	
	public void adicionarProcesso(Processo processo) throws MemoriaVirtualCheiaException{
		if((enderecos.size() == tamanhoMaximo) || (processo.getTamanho() > (tamanhoMaximo - enderecos.size()))){
			throw new MemoriaVirtualCheiaException();
		}			
	    Iterator<Pagina> iterator = processo.createIterator();
    	while(iterator.hasNext()){
    		Pagina pagina = iterator.next();
    		enderecos.add(pagina.getEndereco());
    	}
	}
	
	public void removerProcesso(char idProcesso){ 
		List<Endereco> clone = new LinkedList<Endereco>();
		for(Endereco endereco : enderecos){
			clone.add(endereco);
		}
		
	    for(Endereco endereco : clone){
	    	if(endereco.getIdProcesso() == idProcesso){
	    		enderecos.remove(endereco);
	    	}
	    }
	}
	
	public Endereco enderecoAt(int i){
		return enderecos.get(i);
	}
	
	public Iterator<Endereco> createIterator(){
		return enderecos.iterator();
	}

	public int getTamanhoMaximo() {
		return tamanhoMaximo;
	}

	public void setTamanhoMaximo(int tamanhoMaximo) {
		this.tamanhoMaximo = tamanhoMaximo;
	}
}
