package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import simulador.Endereco;
import simulador.MemoriaVirtual;
import simulador.TabelaPagina;
import util.Observable;
import util.ObserverNode;

import javafx.scene.Group;
import javafx.scene.Node;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MemoriaVirtualView extends StackPane implements ObserverNode, Observable{

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
	private List<Circle> molduraPaginas;
	private HBox listaMolduras;
	private MemoriaVirtual memoriaVirtual;
	private TabelaPagina tabelaPagina;
	private List<ObserverNode> observers;
	private Label label;
	
	public MemoriaVirtualView(MemoriaVirtual memoriaVirtual, TabelaPagina tabelaPagina){
		this.memoriaVirtual = memoriaVirtual;
		this.tabelaPagina = tabelaPagina;
		
		observers = new ArrayList<ObserverNode>();
		molduraPaginas = new ArrayList<Circle>();
		fundo = new Rectangle((LARGURA_FUNDO * memoriaVirtual.getTamanhoMaximo()) + DIFERENCA_POSICIONAMENTO_LISTA, ALTURA_FUNDO);

		listaMolduras = new HBox(SPACING);

		this.setPadding(new Insets(PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM, PADDING_LEFT));
		initMolduras(memoriaVirtual.getTamanhoMaximo());
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
			Circle moldura = new Circle(RAIO);
			moldura.setFill(Color.WHITE);
			moldura.setStroke(Color.BLACK);
			moldura.setStrokeWidth(BORDA_MOLDURA);
			molduraPaginas.add(moldura);
		}
	}
	
	/**
	 * 1 - Seta a cor de fundo da memória virtual, que é cinza;
	 * 2 - Adiciona as molduras ao gerenciador de layout HBox (listaMolduras);
	 * 3 - Seta as posições X e Y da lista de molduras para poder ficar no centro do retangulo.
	 * 4 - O fundo e a listaMolduras são agrupados;
	 * 5 - O grupo é adicionado ao gerenciador de layout da memória virtual;
	 */
	private void initLayout(){
		fundo.setFill(Color.GRAY);
		
		listaMolduras.getChildren().addAll(molduraPaginas);
		listaMolduras.setTranslateY(POSICAO_X_LISTA);
		listaMolduras.setTranslateX(POSICAO_Y_LISTA);
		
		Group grupo = new Group();
		grupo.getChildren().addAll(fundo, listaMolduras);

		VBox vbox = new VBox(1);
		label = new Label("Memória Virtual");
		vbox.getChildren().addAll(label, grupo);
		
		this.getChildren().addAll(vbox);
		this.setAlignment(Pos.TOP_CENTER);
	}
	
	public void update(){
		listaMolduras.getChildren().clear();
		listaMolduras.getChildren().addAll(molduraPaginas);
		Iterator<Endereco> enderecos = memoriaVirtual.createIterator();
		int i = 0;
		while(enderecos.hasNext()){
			Endereco endereco = enderecos.next();
			PaginaView pv = new PaginaView(endereco);
			StackPane bitLocalizacao = createBit(pv);
			StackPane sp = new StackPane();
			sp.getChildren().addAll(pv, bitLocalizacao);
			pv.attach(this);
			listaMolduras.getChildren().set(i, sp);
			++i;
		}
	}
	
	/**
	 * Cria o circulo menor na memoriaVirtualView, informa se a pagina está na memória ou não.
	 * @param paginaView
	 * @return
	 */
	private StackPane createBit(PaginaView paginaView){
		Circle bit = new Circle(12);
		bit.setFill(paginaView.getFill());
		bit.setStroke(paginaView.getCorBorda());

		Label label;
		if(tabelaPagina.pageIsLoad(paginaView.getEndereco())){
			label = new Label("1");
		}else{
			label = new Label("0");
		}

		StackPane sp = new StackPane();
		sp.getChildren().addAll(bit, label);
		sp.setTranslateY(-25);
		sp.setTranslateX(22);
		sp.setMaxHeight(5);
		sp.setMaxWidth(5);
		return sp;
	}

	public MemoriaVirtual getMemoriaVirtual() {
		return memoriaVirtual;
	}

	public void setMemoriaVirtual(MemoriaVirtual memoriaVirtual) {
		this.memoriaVirtual = memoriaVirtual;
	}

	@Override
	public void update(Node node) {
		if(node instanceof PaginaView){
			for(ObserverNode o : observers){
				o.update(node);
			}
		}
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
