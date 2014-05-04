package strategies;

import simulador.Disco;
import simulador.Endereco;
import simulador.EstruturaDados;
import simulador.Gerenciador;
import simulador.MemoriaRAM;
import simulador.Pagina;
import simulador.SubstituicaoPaginaStrategy;

public class DefaultStrategy implements SubstituicaoPaginaStrategy{

	private Gerenciador gerenciador;
	
	public DefaultStrategy(Gerenciador gerenciador){
	    this.gerenciador = gerenciador;
	}
	
	public DefaultStrategy(){
		gerenciador = Gerenciador.getInstance();
	}
	
	@Override
	public Pagina substituirPagina(Pagina pagina) {
		EstruturaDados estruturaDados = gerenciador.getEstruturaDados();
	    Endereco endereco = estruturaDados.paginaParaSubstituir();

	    MemoriaRAM memoriaRam = gerenciador.getMemoriaRam();
	    Pagina paginaSubstituida = memoriaRam.buscarPagina(endereco);

	    Disco disco = gerenciador.getDisco();
	    disco.atualizarPagina(paginaSubstituida);

	    memoriaRam.substituirPagina(endereco, pagina);
	    estruturaDados.adicionarReferencia(pagina.getEndereco());

	    return paginaSubstituida;
	}
}
