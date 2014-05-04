package view;

import javafx.scene.paint.Color;
import simulador.Endereco;

public class PaginaView extends Circulo{	
	
	private Endereco endereco;
	
	public PaginaView(Endereco endereco){
		super(CorProcessoEnum.getEnum(endereco.getIdProcesso()).getCor());
		
		this.endereco = endereco;		
		setLabel(Character.toString(endereco.getIdPagina()));
		this.setBorder(Color.BLACK);
		setCorBorda(Color.BLACK);
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
