package estruturas;

import java.util.ArrayList;
import java.util.List;

public enum EstrategiaEnum {

	FIFO		("FIFO", "Fila", "DefaultStrategy"),
	MRU			("MRU", "ListaMRU", "DefaultStrategy");
	
	private String nome;
	private String estrutura;
	private String estrategia;
	
	private EstrategiaEnum(String nome, String estrutura, String estrategia){
		this.nome = nome;
		this.estrutura = estrutura;
		this.estrategia = estrategia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstrutura() {
		return estrutura;
	}

	public void setEstrutura(String estrutura) {
		this.estrutura = estrutura;
	}

	public String getEstrategia() {
		return estrategia;
	}

	public void setEstrategia(String estrategia) {
		this.estrategia = estrategia;
	}
	
	public static List<EstrategiaEnum> getEstrategias() {
		List<EstrategiaEnum> lista = new ArrayList<EstrategiaEnum>();
		for(EstrategiaEnum e : values()){
			lista.add(e);
		}
		return lista;
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
}
