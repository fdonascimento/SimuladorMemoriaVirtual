package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

import simulador.MemoriaRAM;
import simulador.Pagina;
import simulador.Processo;

public class MemoriaRAMTeste {

	@Test
	public void testeIsFull() {
		MemoriaRAM memoriaRAM = new MemoriaRAM();
		
		int numMolduras = MemoriaRAM.NUMERO_MOLDURAS;
		
		Processo processo = new Processo('1', 1, numMolduras);
		Iterator<Pagina> iteratorPagina = processo.createIterator();
		while(iteratorPagina.hasNext()){
			assertFalse(memoriaRAM.isFull());
			memoriaRAM.adicionarPagina(iteratorPagina.next());
		}
		
		assertTrue(memoriaRAM.isFull());
	}
	
	@Test
	public void testeInicializarMoldurasPaginas() {
		MemoriaRAM memoriaRAM = new MemoriaRAM();
		
		int numMolduras = MemoriaRAM.NUMERO_MOLDURAS;
		
		Processo processo = new Processo('1', 1, numMolduras);
		Iterator<Pagina> iteratorPagina = processo.createIterator();
		while(iteratorPagina.hasNext()){
			memoriaRAM.adicionarPagina(iteratorPagina.next());
		}
		
		try {
			Method inicializarMoldurasPaginas = memoriaRAM.getClass().getDeclaredMethod("inicializarMoldurasPaginas");
			inicializarMoldurasPaginas.setAccessible(true);
			
			inicializarMoldurasPaginas.invoke(memoriaRAM);
			
			Field field = memoriaRAM.getClass().getDeclaredField("moldurasPaginas");
			field.setAccessible(true);
			Pagina[] molduras = (Pagina[]) field.get(memoriaRAM);
			
			for(Pagina pagina : molduras){
				assertNull(pagina);
			}
			
		} catch (NoSuchMethodException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (SecurityException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

