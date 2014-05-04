package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import exceptions.MemoriaVirtualCheiaException;

import simulador.Endereco;
import simulador.MemoriaVirtual;
import simulador.Pagina;
import simulador.Processo;

public class TesteMemoriaVirtual {

	@Test
	public void testeAdicionarProcesso() throws MemoriaVirtualCheiaException {
		MemoriaVirtual memoriaVirtual = new MemoriaVirtual();
		
		List<Processo> processos = getProcessos();
		int size = processos.size();
		for(int i = 0; i < size; ++i){
			Processo processo = processos.get(i);
			memoriaVirtual.adicionarProcesso(processo);
			
			Iterator<Endereco> iteratorEndereco = memoriaVirtual.createIterator();
			assertEquals(true, iteratorEndereco.hasNext());
			
			List<Endereco> enderecosRetornados = new ArrayList<Endereco>();
			while(iteratorEndereco.hasNext()){
				enderecosRetornados.add(iteratorEndereco.next());
			}
			
			Iterator<Pagina> iteratorPagina = processo.createIterator();
			while(iteratorPagina.hasNext()){
				Pagina pagina = iteratorPagina.next();
				assertEquals(true, enderecosRetornados.contains(pagina.getEndereco()));
			}
		}
	}

	public List<Processo> getProcessos(){
		List<Processo> processos = new ArrayList<Processo>();
		for(int i = 0; i < 5; ++i){
			Processo processo = new Processo(String.valueOf(i).charAt(0), 2, 2);
			processos.add(processo);
		}
		
		return processos;
	}
	
	public MemoriaVirtual getMemoriaVirtual() throws MemoriaVirtualCheiaException{
		MemoriaVirtual memoriaVirtual = new MemoriaVirtual();
		List<Processo> processos = getProcessos();
		for(Processo processo : processos){
			memoriaVirtual.adicionarProcesso(processo);
		}
		
		return memoriaVirtual;
	}
	
	@Test
	public void testeRemoverProcesso() throws MemoriaVirtualCheiaException{
		MemoriaVirtual memoriaVirtual = getMemoriaVirtual();
		
		List<Processo> processos = getProcessos();
		for(Processo processo : processos){
			memoriaVirtual.removerProcesso(processo.getId());
			
			Iterator<Endereco> iteratorEndereco = memoriaVirtual.createIterator();
			List<Endereco> enderecosMemoria = new ArrayList<Endereco>();
			while(iteratorEndereco.hasNext()){
				enderecosMemoria.add(iteratorEndereco.next());
			}
			
			Iterator<Pagina> iteratorPagina = processo.createIterator();
			while(iteratorPagina.hasNext()){
				Pagina pagina = iteratorPagina.next();
				assertFalse(enderecosMemoria.contains(pagina.getEndereco()));
			}
		}
	}
}
