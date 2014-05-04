package view;

import javafx.scene.paint.Color;

public enum CorProcessoEnum {
	
	BLUEVIOLET	('0', Color.BLUEVIOLET),
	VERMELHO	('1', Color.RED),
	VERDE		('2', Color.GREEN),
	LARANJA		('3', Color.ORANGE),
	ROSA		('4', Color.PINK),
	ROXO		('5', Color.PURPLE),
	AZUL		('6', Color.BLUE),
	CHOCOLATE	('7', Color.CHOCOLATE),
	MARROM		('8', Color.BROWN),
	CIANO		('9', Color.CYAN);
	
	
	private char idProcesso;
	private Color cor;
	
	CorProcessoEnum(char idProcesso, Color cor){
		this.idProcesso = idProcesso;
		this.cor = cor;
	}

	public char getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(char idProcesso) {
		this.idProcesso = idProcesso;
	}

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}
	
	public static CorProcessoEnum getEnum(char idProcesso){
		for(CorProcessoEnum cpe : CorProcessoEnum.values()){
			if(cpe.getIdProcesso() == idProcesso){
				return cpe;
			}
		}
		
		return null;
	}
}
