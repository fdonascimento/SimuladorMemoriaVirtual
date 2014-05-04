package view;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import exceptions.MemoriaVirtualCheiaException;

import simulador.Gerenciador;
import simulador.Processo;
import util.Observable;
import util.ObservableMensagem;
import util.ObserverMensagem;
import util.ObserverNode;
import util.SeveridadeEnum;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AdicionarProcessoView extends StackPane implements ObserverNode, Observable, ObservableMensagem{

	private Rectangle background;
	private GridPane panel;
	private Processo processo;
	private char idProcesso;
	private List<ObserverNode> observers;
	private List<ObserverMensagem> observersMensagens;
	//private TextField txtCusto;
	private TextField txtTamanho;
	private ProcessoView processoView;
	
	public AdicionarProcessoView(){
		observers = new ArrayList<ObserverNode>();
		observersMensagens = new ArrayList<ObserverMensagem>();
		panel = new GridPane();
		initLayout();
	}
	
	private void initLayout(){
		background = new Rectangle(ProcessoView.RAIO*12, ProcessoView.RAIO*5.4);
		background.setFill(Color.LIGHTBLUE);
		background.setStroke(Color.BLUE);
		background.setStrokeWidth(MenuProcessosView.BORDA);
		
		getChildren().addAll(background, panel);
	}

	private void displayPanel(){
		panel.setVisible(true);
		panel.setAlignment(Pos.CENTER);
		Label lblTamanho = new Label("Tamanho: ");
		lblTamanho.setTextFill(Color.BLACK.darker());
		txtTamanho = new TextField();
		//Label lblCusto = new Label("Custo: ");
		//txtCusto = new TextField();
		Button btn;
		panel.getChildren().clear();
		if(processoView.isAdicionado()){
			
			btn = createBotaoRemover();
			btn.setScaleX(2);
			btn.setScaleY(2);
			panel.add(btn, 1, 1);
		}else{			
			btn = createBotaoAdicionar();
			panel.add(lblTamanho, 0, 0);
			panel.add(txtTamanho, 1, 0);
			//panel.add(lblCusto, 0, 1);
			//panel.add(txtCusto, 1, 1);
			panel.add(btn, 1, 2);
		}
	}
	
	private Button createBotaoAdicionar(){
		Button btnAdicionar = new Button("Adicionar");		
		btnAdicionar.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				Gerenciador gerenciador = Gerenciador.getInstance();
				//if(txtCusto != null && txtTamanho != null){
				if(txtTamanho != null){
					//if(!txtCusto.getText().isEmpty() && !txtTamanho.getText().isEmpty()){
					if(!txtTamanho.getText().isEmpty()){
						try {
							//int custo = Integer.parseInt(txtCusto.getText());
							int tamanho = Integer.parseInt(txtTamanho.getText());
							processo = new Processo(idProcesso, 0, tamanho);
							gerenciador.adicionarProcesso(processo);
							processoView.setAdicionado(true);
							hidePanel();
							notity();
						} catch (MemoriaVirtualCheiaException e) {
							Logger.getAnonymousLogger().info("Memoria Virtual Cheia!");
						} catch (NumberFormatException nfe){
							notity("Digite um número inteiro", SeveridadeEnum.FATAL);
						} catch (NoSuchElementException nse) {

						}
					}
				}
			}
		});
		
		return btnAdicionar;
	}

	
	private Button createBotaoRemover(){
		Button btnRemover = new Button("Remover");		
		btnRemover.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				Gerenciador gerenciador = Gerenciador.getInstance();
				gerenciador.removerProcesso(processoView.getIdProcesso());
				processoView.setAdicionado(false);
				hidePanel();
				notity();
			}
		});
		
		return btnRemover;

	}
	
	private void hidePanel(){
		background.setFill(Color.LIGHTBLUE);
		panel.setVisible(false);
	}
	
	@Override
	public void update(Node node) {
		processoView = (ProcessoView)node;
		Color color = processoView.getPreenchimento();
		background.setStroke(Color.BLUE);
		background.setFill(color);
		idProcesso = processoView.getIdProcesso();
		displayPanel();
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	@Override
	public void attach(ObserverNode o) {
		observers.add(o);
	}

	@Override
	public void detach(ObserverNode o) {
		observers.remove(o);		
	}

	@Override
	public void notity() {
		for(ObserverNode o : observers){
			o.update(this);
		}
	}
	
	@Override
	public void attach(ObserverMensagem o) {
		observersMensagens.add(o);
	}

	@Override
	public void detach(ObserverMensagem o) {
		observersMensagens.remove(o);		
	}

	@Override
	public void notity(String mensagem, SeveridadeEnum severidade) {
		for(ObserverMensagem o : observersMensagens){
			o.update(mensagem, severidade);
		}
	}
}
