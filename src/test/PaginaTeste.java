package test;

import static org.junit.Assert.*;

import org.junit.Test;

import simulador.Endereco;
import simulador.Pagina;

public class PaginaTeste {

	@Test
	public void testcompareTo() {
		Endereco e1 = new Endereco('A', '1');
		Endereco e2 = new Endereco('A', '2');
		Endereco e3 = new Endereco('B', '1');
		Endereco e4 = new Endereco('B', '2');
		Pagina p1 = new Pagina(e1);
		Pagina p2 = new Pagina(e1);
		Pagina p3 = new Pagina(e2);
		Pagina p4 = new Pagina(e3);
		Pagina p5 = new Pagina(e4);
		
		assertEquals(0, p1.compareTo(p2));
		assertEquals(-1, p1.compareTo(p4));
		assertEquals(1, p5.compareTo(p1));
		assertEquals(-1, p2.compareTo(p3));
	}

}
