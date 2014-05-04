package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import simulador.Endereco;
import simulador.Pagina;
import simulador.Processo;

public class ProcessoTeste {
	
	@Test
	public void testeInicializar() {
		Processo processo = new Processo('1', 2, 5);
		List<Pagina> paginas = getPaginas('1');
		
		Iterator<Pagina> iteratorPagina = processo.createIterator();
		
		for(Pagina paginaEsperada : paginas){
			assertTrue(iteratorPagina.hasNext());
			
			if(iteratorPagina.hasNext()){
				Pagina paginaGerada = iteratorPagina.next();
				assertTrue(paginaEsperada.equals(paginaGerada));
			}else{
				Assert.fail();
			}
		}
	}
	
	private List<Pagina> getPaginas(char idProcesso){
		List<Pagina> paginas = new ArrayList<Pagina>();
		Endereco end1 = new Endereco('A', idProcesso);
		Endereco end2 = new Endereco('B', idProcesso);
		Endereco end3 = new Endereco('C', idProcesso);
		Endereco end4 = new Endereco('D', idProcesso);
		Endereco end5 = new Endereco('E', idProcesso);
		
		Pagina p1 = new Pagina(end1);
		Pagina p2 = new Pagina(end2);
		Pagina p3 = new Pagina(end3);
		Pagina p4 = new Pagina(end4);
		Pagina p5 = new Pagina(end5);
		
		paginas.add(p1);
		paginas.add(p2);
		paginas.add(p3);
		paginas.add(p4);
		paginas.add(p5);
		
		return paginas;
		
	}
}
