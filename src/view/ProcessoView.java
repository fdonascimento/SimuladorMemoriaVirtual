package view;

public class ProcessoView extends Circulo {
	
	public static double RAIO = 20;
	private char idProcesso;
	private boolean adicionado; 
	
	public ProcessoView(char idProcesso){
		super(CorProcessoEnum.getEnum(idProcesso).getCor());
		
		setLabel(idProcesso+"");
		this.idProcesso = idProcesso;
		setRaio(RAIO);
	}

	public char getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(char idProcesso) {
		this.idProcesso = idProcesso;
	}

	public boolean isAdicionado() {
		return adicionado;
	}

	public void setAdicionado(boolean adicionado) {
		this.adicionado = adicionado;
	}
}
