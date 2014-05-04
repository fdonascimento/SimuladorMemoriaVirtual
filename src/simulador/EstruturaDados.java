package simulador;

import java.util.Iterator;

public interface EstruturaDados {
	
    public void adicionarReferencia(Endereco endereco);
    public void removerReferencia(Endereco endereco);
    public Endereco paginaParaSubstituir();
    public void removerReferencias(int idProcesso);
    public Iterator<Endereco> createIterator();

}
