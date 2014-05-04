package simulador;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import util.Pair;

public class MemoriaRAM {
	
	public static final int NUMERO_MOLDURAS = 7;
	private Pagina[] moldurasPaginas;
	private List<Pair> intervalosLivres;
	
	public MemoriaRAM(){
		moldurasPaginas = new Pagina[NUMERO_MOLDURAS];
		intervalosLivres = new ArrayList<Pair>();
	    inicializarMoldurasPaginas();
	    initIntervalosAddressFree();
	}

	protected void inicializarMoldurasPaginas(){
	    for(int i = 0; i < NUMERO_MOLDURAS; ++i){
	        moldurasPaginas[i] = null;
	    }
	}

	protected void initIntervalosAddressFree(){
	    int high = NUMERO_MOLDURAS - 1;
	    Pair firstIntervalo = new Pair(0, high);
	    intervalosLivres.add(firstIntervalo);
	}

	public void adicionarPagina(Pagina pagina){
	    if(!isFull()){
	        int moldura = getAddress();
	        moldurasPaginas[moldura] = pagina;
	    }

	}

	public Pagina buscarPagina(Endereco endereco){
	    for(int i = 0; i < NUMERO_MOLDURAS; ++i){
	        if((moldurasPaginas[i] != null) && (moldurasPaginas[i].getEndereco().equals(endereco)))
	            return moldurasPaginas[i];
	    }
	    return null;
	}

	public boolean isFull(){
	    for(int i = 0; i < NUMERO_MOLDURAS; ++i){
	        if(moldurasPaginas[i] == null)
	            return false;
	    }

	    return true;
	}

	public void removerPagina(Endereco endereco){
	    for(int i = 0; i < NUMERO_MOLDURAS; ++i){
	        if((moldurasPaginas[i] != null) && (moldurasPaginas[i].getEndereco().equals(endereco))){
	            novosIntervalosByRemoval(i);
	            moldurasPaginas[i] = null;
	            break;
	        }
	    }
	}

	public void removerPaginas(char idProcesso){
	    for(int i = 0; i < NUMERO_MOLDURAS; ++i){
	        if((moldurasPaginas[i] != null) && (moldurasPaginas[i].getEndereco().getIdProcesso() == idProcesso)){
	            novosIntervalosByRemoval(i);
	            moldurasPaginas[i] = null;
	        }
	    }
	}

	public void substituirPagina(Endereco paginaSubstituida, Pagina novaPagina){
	    for(int i = 0; i < NUMERO_MOLDURAS; ++i){
	        if((moldurasPaginas[i] != null) && (moldurasPaginas[i].getEndereco().equals(paginaSubstituida))){
	            moldurasPaginas[i] = novaPagina;
	            break;
	        }
	    }
	}

	public Iterator<Pagina> createIterator(){
	    List<Pagina> paginas = new ArrayList<Pagina>();
	    Endereco endereco = new Endereco('0', '0');
	    Pagina pagina = new Pagina(endereco);
	    for(int i = 0; i < NUMERO_MOLDURAS; ++i){
	        if(moldurasPaginas[i] == null)
	            paginas.add(pagina);
	        else
	            paginas.add(moldurasPaginas[i]);
	    }

	    return paginas.iterator();
	}

	protected int getAddress(){
	    Pair intervalo = maiorIntervaloDisponivel();
	    int high = intervalo.getSecond();
	    int low = intervalo.getFirst();

	    int randomAddress;
	    if(high == low){
	    	randomAddress = high;
	    }else{
	    	randomAddress = low + (int)(Math.random() * ((high - low) + 1));
	    }
	    
	    novosIntervalosByAddition(randomAddress);
	    return randomAddress;
	}

	protected void novosIntervalosByAddition(int address){	    
	    for(Pair intervalo : intervalosLivres){
	    	int low = intervalo.getFirst();
	    	int high = intervalo.getSecond();
	    	if((address >= low) && (address <= high)){
	            intervalosLivres.remove(intervalo);
	            if(address != low){
	                Pair novoIntervalo1 = new Pair(low, address-1);
	                intervalosLivres.add(novoIntervalo1);
	            }
	            if(address != high){
	                Pair novoIntervalo2 = new Pair(address+1, high);
	                intervalosLivres.add(novoIntervalo2);
	            }
	            break;
	        }
	    }
	}

	protected void novosIntervalosByRemoval(int address){
	    if(isFull())
	        intervalosLivres.add(new Pair(address, address));
	    else{
	        boolean flag = false;
	        for(Pair intervalo : intervalosLivres){
	            int low = intervalo.getFirst();
	            int high = intervalo.getSecond();
	            if(((address + 1) == low) || ((address - 1) == high)){
	                flag = true;
	                intervalosLivres.remove(intervalo);
	                if((address + 1) == low){
	                    Pair novoIntervalo = new Pair(address, high);
	                    intervalosLivres.add(novoIntervalo);
	                }else{
	                    Pair novoIntervalo = new Pair(low, address);
	                    intervalosLivres.add(novoIntervalo);
	                }
	                break;
	            }
	        }
	        if(flag == false)
	            intervalosLivres.add(new Pair(address, address));
	    }
	}

	protected Pair maiorIntervaloDisponivel(){
	    Pair maiorIntervalo = intervalosLivres.get(0);
	    int maiorRange = maiorIntervalo.getSecond() - maiorIntervalo.getFirst();

	    if(!intervalosLivres.get(intervalosLivres.size() - 1).equals(maiorIntervalo)){	        
	        for(Pair intervalo : intervalosLivres){
	        	int range = intervalo.getSecond() - intervalo.getFirst();
	        	if(range > maiorRange){
	        		maiorIntervalo = intervalo;
	                maiorRange = range;
	        	}
	        }
	    }
	    return maiorIntervalo;
	}
	
}
