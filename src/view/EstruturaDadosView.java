package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import simulador.Endereco;
import simulador.EstruturaDados;
import simulador.MemoriaRAM;
import util.Observable;
import util.ObserverNode;

public class EstruturaDadosView extends StackPane implements ObserverNode, Observable{

	private final double RAIO = 30;
	private final double PADDING_TOP = 5;
	private final double PADDING_RIGHT = 5;
	private final double PADDING_LEFT = 5;
	private final double PADDING_BOTTOM = 5;
	private final double SPACING = 1;
	private final double LARGURA_FUNDO = 66;
	private final double ALTURA_FUNDO = 82;
	private final double DIFERENCA_POSICIONAMENTO_LISTA = 20;
	private final double POSICAO_X_LISTA = 10;
	private final double POSICAO_Y_LISTA = 10;
	private final double BORDA_MOLDURA = 5;
	
	private Rectangle fundo;
	private List<Circle> referencias;
	private HBox listaMolduras;
	private EstruturaDados estruturaDados;
	private List<ObserverNode> observers;
	private Label label;
	
	public EstruturaDadosView(EstruturaDados estruturaDados){
		this.estruturaDados = estruturaDados;
		observers = new ArrayList<ObserverNode>();
		referencias = new ArrayList<Circle>();
		fundo = new Rectangle((LARGURA_FUNDO * MemoriaRAM.NUMERO_MOLDURAS) + DIFERENCA_POSICIONAMENTO_LISTA, ALTURA_FUNDO);

		listaMolduras = new HBox(SPACING);

		this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
		initMolduras(MemoriaRAM.NUMERO_MOLDURAS);
		initLayout();
	}
	
	/**
	 * 1 - Instancia os circulos, que são as molduras;
	 * 2 - Seta o preenchimento, que é branco;
	 * 3 - Adiciona os Circle a lista de Circle, que nesse caso são as molduras de páginas.
	 * @param numMolduras
	 */
	private void initMolduras(int numMolduras){		
		for(int i = 0; i < numMolduras; ++i){
			Circle moldura = getMoldura();
			referencias.add(moldura);
		}
	}
	
	private Circle getMoldura(){
		Circle moldura = new Circle(RAIO);
		moldura.setFill(Color.WHITE);
		moldura.setStroke(Color.BLACK);
		moldura.setStrokeWidth(BORDA_MOLDURA);
		return moldura;
	}
	
	private void initLayout(){
		fundo.setFill(Color.ANTIQUEWHITE);
		
		listaMolduras.getChildren().addAll(referencias);
		listaMolduras.setTranslateY(POSICAO_Y_LISTA);
		listaMolduras.setTranslateX(POSICAO_X_LISTA);
		
		Group grupo = new Group();
		grupo.getChildren().addAll(fundo, listaMolduras);
		
		VBox vbox = new VBox(1);
		label = new Label("Estrutura de Dados");
		vbox.getChildren().addAll(label, grupo);
		
		this.getChildren().addAll(vbox);
		this.setAlignment(Pos.BASELINE_LEFT);
		this.setTranslateY(100);
	}
	
	public void update(){
		listaMolduras.getChildren().clear();
		listaMolduras.getChildren().addAll(referencias);
		Iterator<Endereco> enderecos = estruturaDados.createIterator();
		int i = 0;
		while(enderecos.hasNext()){
			Endereco endereco = enderecos.next();
			PaginaView pv = new PaginaView(endereco);
			pv.setOnMouseClicked(null);
			pv.setOnMouseEntered(null);
			pv.setOnMouseExited(null);
			listaMolduras.getChildren().set(i, pv);
			++i;
		}
		notity();
	}

	public EstruturaDados getEstruturaDados() {
		return estruturaDados;
	}

	public void setEstruturaDados(EstruturaDados estruturaDados) {
		this.estruturaDados = estruturaDados;
	}

	public Node getProximaPaginaSubstituir(){
		return listaMolduras.getChildren().get(0);
	}
	
	@Override
	public void update(Node node) {
		update();
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
}
