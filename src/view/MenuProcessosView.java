package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.Observable;
import util.ObserverNode;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MenuProcessosView extends StackPane implements Observable{

	public static final double BORDA = 5;
	
	private List<ProcessoView> processos;
	private GridPane panel;
	private Rectangle background;
	
	public MenuProcessosView(){
		setId("menuProcessos");
		processos = new ArrayList<ProcessoView>();
		panel = new GridPane();		
		
		init();
		initLayout();
	}
	
	private void init(){
		for(int i = 0; i < 10; ++i){
			processos.add(new ProcessoView(String.valueOf(i).charAt(0)));
		}
		
		Iterator<ProcessoView> iterator = processos.iterator();
		for(int i = 0; i < 2; ++i){
			for(int j = 0; j < 5; ++j){
				panel.add(iterator.next(), j, i);
			}
		}
	}
	
	private void initLayout(){
		background = new Rectangle(ProcessoView.RAIO*12, ProcessoView.RAIO*5.4);
		background.setFill(Color.LIGHTBLUE);
		background.setStroke(Color.BLUE);
		background.setStrokeWidth(BORDA);
		
		this.getChildren().addAll(background, panel);
		this.setAlignment(Pos.TOP_CENTER);
		panel.setTranslateY(10);
		panel.setTranslateX(10);
	}

	@Override
	public void attach(ObserverNode o) {
		for(ProcessoView p : processos){
			p.attach(o);
		}
	}

	@Override
	public void detach(ObserverNode o) {
		for(ProcessoView p : processos){
			p.detach(o);
		}
	}

	@Override
	public void notity() {
		for(ProcessoView p : processos){
			p.notify();
		}
		
	}
}
