package simulador;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class TabelaPagina {

	private HashMap<Endereco, Boolean> mapeamentoPaginas;
	
	public TabelaPagina(){
		mapeamentoPaginas = new HashMap<Endereco, Boolean>();
	}
	
	public void mapearPaginas(Processo processo) {
		Iterator<Pagina> iterator = processo.createIterator();
		while (iterator.hasNext()) {
			Pagina pagina = iterator.next();
			Endereco endereco = pagina.getEndereco();
			mapeamentoPaginas.put(endereco, pagina.isLoad());
		}
	}
	
	public void removerMapeamento(char idProcesso) {
		List<Endereco> enderecos = new LinkedList<Endereco>();
		for(Endereco endereco : mapeamentoPaginas.keySet()){
			enderecos.add(endereco);
		}
		
		Iterator<Endereco> iterator = enderecos.iterator();
		while (iterator.hasNext()) {
			Endereco endereco = iterator.next();
			if (endereco.getIdProcesso() == idProcesso)
				mapeamentoPaginas.remove(endereco);
		}
	}
	
	public boolean pageIsLoad(Endereco endereco) {
		return mapeamentoPaginas.get(endereco);
	}
	
	public void atualizarMapeamento(Pagina pagina) {
		Endereco endereco = pagina.getEndereco();
		mapeamentoPaginas.remove(endereco);
		mapeamentoPaginas.put(endereco, pagina.isLoad());
	}
	
	public Iterator<Entry<Endereco, Boolean>> createIterator(){
		return mapeamentoPaginas.entrySet().iterator();
	}
}
