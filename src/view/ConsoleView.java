package view;

import java.util.ArrayList;
import java.util.List;

import util.ObserverMensagem;
import util.SeveridadeEnum;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ConsoleView extends StackPane implements ObserverMensagem{
	
	private Rectangle background;
	private Label header;
	private List<Label> mensagens;
	
	public ConsoleView(){
		init();
	}
	
	private void init(){
		mensagens = new ArrayList<Label>();
		background = new Rectangle(ProcessoView.RAIO*12, ProcessoView.RAIO*5.4);
		background.setFill(Color.WHITE);
		background.setStroke(Color.BLUE);
		background.setStrokeWidth(MenuProcessosView.BORDA);
		
		header = new Label("Console");
		header.setTextFill(Color.BLACK.darker());
		header.setTranslateY(5);
		header.setTranslateX(85);
		
		VBox vbox = new VBox(1);
		vbox.getChildren().addAll(header);
		vbox.setTranslateX(10);
		
		this.getChildren().addAll(background, vbox);
	}
	
	private void updateMensagens(){
		VBox vbox = new VBox(1);
		for(Label l : mensagens){
			vbox.getChildren().add(l);
		}
		
		this.getChildren().clear();
		VBox v2 = new VBox(1);
		v2.getChildren().addAll(header, vbox);
		vbox.setTranslateX(10);
		
		this.getChildren().addAll(background, v2);
	}

	@Override
	public void update(String mensagem, SeveridadeEnum severidade) {
		adicionarMensagem(mensagem, severidade);
		updateMensagens();
	}
	
	private void adicionarMensagem(String mensagem, SeveridadeEnum severidade){
		Label label = new Label(mensagem);
		label.setTextFill(severidade.getColor());
		if(mensagens.size() < 4){
			mensagens.add(label);
		}else{
			mensagens.remove(0);
			mensagens.add(label);
		}
		updateMensagens();
	}
}
