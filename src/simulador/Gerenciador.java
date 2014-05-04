package simulador;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Iterator;

import estruturas.Fila;
import exceptions.MemoriaVirtualCheiaException;
import strategies.DefaultStrategy;
import util.ObserverMensagem;
import util.SeveridadeEnum;

public class Gerenciador extends Observable implements util.ObservableMensagem{

	private Disco disco;
    private MemoriaVirtual memoriaVirtual;
    private TabelaPagina tabelaPagina;
    private MemoriaRAM memoriaRam;
    private EstruturaDados estruturaDados;
    private SubstituicaoPaginaStrategy strategy;
    private static Gerenciador gerenciador;
    private List<ObserverMensagem> observers;
    
    private Gerenciador(){
        disco = new Disco();
        memoriaVirtual = new MemoriaVirtual();
        tabelaPagina = new TabelaPagina();
        memoriaRam = new MemoriaRAM();
        estruturaDados = new Fila();
        strategy = new DefaultStrategy(this);
        observers = new ArrayList<ObserverMensagem>();
    }
    
    public static Gerenciador getInstance(){
    	if(gerenciador == null){
    		gerenciador = new Gerenciador();
    	}
    	return gerenciador;
    }
    
    public void adicionarProcesso(Processo processo) throws MemoriaVirtualCheiaException{
		try {
			memoriaVirtual.adicionarProcesso(processo);
			disco.adicionarProcesso(processo);

	        Iterator<Pagina> iterator = processo.createIterator();
	        Pagina pagina = iterator.next();
	        carregarPagina(pagina);
	        estruturaDados.adicionarReferencia(pagina.getEndereco());
	        tabelaPagina.mapearPaginas(processo);

			notity("Processo "+processo+" carregado", SeveridadeEnum.INFO);
		} catch (MemoriaVirtualCheiaException e) {
			notity("Espaço Insuficiente de Memória", SeveridadeEnum.FATAL);
			throw new MemoriaVirtualCheiaException();
		}catch(NoSuchElementException nse){
			notity("Digite um número maior que 0", SeveridadeEnum.FATAL);
			throw new NoSuchElementException();
		}
     }

    public void removerProcesso(char idProcesso){
    	memoriaVirtual.removerProcesso(idProcesso);
        disco.removerProcesso(idProcesso);    
        memoriaRam.removerPaginas(idProcesso);
        estruturaDados.removerReferencias(idProcesso);
        tabelaPagina.removerMapeamento(idProcesso);
        notity("Processo "+idProcesso+" removido", SeveridadeEnum.INFO);
    }

    public void referenciarPagina(Endereco endereco){
    	notity("Página "+endereco+" referenciada", SeveridadeEnum.INFO);
        if(!tabelaPagina.pageIsLoad(endereco)){
            notifyObservers();
            Pagina pagina = disco.buscarPagina(endereco);
            carregarPagina(pagina);
            tabelaPagina.atualizarMapeamento(pagina);
        }
        estruturaDados.adicionarReferencia(endereco);

    }

    public void carregarPagina(Pagina pagina){
        if(!memoriaRam.isFull()){
            memoriaRam.adicionarPagina(pagina);
        }else{
        	notity("PAGE FAULT", SeveridadeEnum.FATAL);
            substituirPagina(pagina);
        }
        
        pagina.load();
    }

    public void substituirPagina(Pagina pagina){
        Pagina paginaSubstituida = strategy.substituirPagina(pagina);
        notity("Página "+paginaSubstituida+" substituída", SeveridadeEnum.INFO);
        notity("Página "+pagina+" carregada", SeveridadeEnum.INFO);
        paginaSubstituida.unload();
        tabelaPagina.atualizarMapeamento(paginaSubstituida);
    }

    public void setStrategy(SubstituicaoPaginaStrategy strategy){
        this.strategy = strategy;
    }
    
    public Disco getDisco() {
        return disco;
    }

    public MemoriaVirtual getMemoriaVirtual(){
        return memoriaVirtual;
    }

    public TabelaPagina getTabelaPagina(){
        return tabelaPagina;
    }

    public MemoriaRAM getMemoriaRam(){
        return memoriaRam;
    }

    public EstruturaDados getEstruturaDados(){
        return estruturaDados;
    }
    
    public void setEstruturaDados(EstruturaDados estruturaDados){
    	this.estruturaDados = estruturaDados;
    }

    @Override
	public void attach(ObserverMensagem o) {
		observers.add(o);
	}

	@Override
	public void detach(ObserverMensagem o) {
		observers.remove(o);		
	}

	@Override
	public void notity(String mensagem, SeveridadeEnum severidade) {
		for(ObserverMensagem o : observers){
			o.update(mensagem, severidade);
		}
	}
}
