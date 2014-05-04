package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import estruturas.EstrategiaEnum;
import simulador.Endereco;
import simulador.EstruturaDados;
import simulador.Gerenciador;
import simulador.SubstituicaoPaginaStrategy;
import util.Observable;
import util.ObservableMensagem;
import util.ObserverMensagem;
import util.ObserverNode;
import util.SeveridadeEnum;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EscolherEstrategiaView extends StackPane implements Observable, ObservableMensagem{
	
	private Rectangle background;
	private Label label;
	private Label estrategiaSelecionada;
	private ComboBox<EstrategiaEnum> estrategias;
	private Button btnTrocar;
	private List<ObserverNode> observers;
	private List<ObserverMensagem> observersMensagens;
	
	public EscolherEstrategiaView() {
		init();
		initLayout();
	}
	
	private void init(){
		observers = new ArrayList<ObserverNode>();
		observersMensagens = new ArrayList<ObserverMensagem>();
		estrategias = new ComboBox<EstrategiaEnum>();
		label = new Label("Estratégia:");
		btnTrocar = new Button("Trocar");
		estrategiaSelecionada = new Label(EstrategiaEnum.FIFO.getNome());
		
		btnTrocar.setOnMouseClicked(getEstrategiaSelectedEvent());
	}
	
	private void initLayout(){
		estrategias.setItems(FXCollections.observableArrayList(EstrategiaEnum.getEstrategias()));
		estrategias.setPromptText("Selecione...");
		estrategias.setMinWidth(100);
		background = new Rectangle(ProcessoView.RAIO*12, ProcessoView.RAIO*5.4);
		background.setFill(Color.LIGHTBLUE);
		background.setStroke(Color.BLUE);
		background.setStrokeWidth(MenuProcessosView.BORDA);
		
		label.setTextFill(Color.BLACK.darker());
		
		HBox hbox = new HBox(2);
		hbox.getChildren().addAll(estrategias, btnTrocar);
		
		HBox linhaLabel = new HBox(2);
		linhaLabel.getChildren().addAll(label, estrategiaSelecionada);
		
		VBox vbox = new VBox(1);
		vbox.getChildren().addAll(linhaLabel, hbox);
		vbox.setTranslateX(10);
		vbox.setTranslateY(30);
		
		this.getChildren().addAll(background, vbox);
	}
	
	public EventHandler<Event> getEstrategiaSelectedEvent(){
		EventHandler<Event> estrategiaSelected = new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				EstrategiaEnum estrategia = estrategias.getValue();
				try {
					EstruturaDados estrutura = (EstruturaDados)Class.forName("estruturas."+estrategia.getEstrutura()).newInstance();
					SubstituicaoPaginaStrategy strategy = (SubstituicaoPaginaStrategy )Class.forName("strategies."+estrategia.getEstrategia()).newInstance();
					Gerenciador gerenciador = Gerenciador.getInstance();
					gerenciador.setStrategy(strategy);
					
					Iterator<Endereco> enderecos = Gerenciador.getInstance().getEstruturaDados().createIterator();
					while(enderecos.hasNext()){
						Endereco endereco = enderecos.next();
						estrutura.adicionarReferencia(endereco);
					}
					
					gerenciador.setEstruturaDados(estrutura);
					
					estrategiaSelecionada.setText(estrategia.getNome());
					notity("Estratégia "+estrategia.getNome()+" selecionada", SeveridadeEnum.INFO);
					notity();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		return estrategiaSelected;
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
