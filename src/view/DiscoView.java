package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import simulador.Disco;
import simulador.MemoriaRAM;
import simulador.Pagina;
import util.ObserverNode;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class DiscoView extends StackPane implements ObserverNode{

	private final double RAIO = 30;
	private final double SPACING = 1;
	private final double LARGURA_FUNDO = 66;
	private final double ALTURA_FUNDO = 72;
	private final double DIFERENCA_POSICIONAMENTO_LISTA = 20;
	private final double POSICAO_X_LISTA = 10;
	private final double POSICAO_Y_LISTA = 10;
	private final double BORDA_MOLDURA = 5;
	private final int MAX_PAGINAS_LINHA = 4;
	
	private Rectangle retangulo;
	private Disco disco;
	private VBox trilhasPagina;
	private List<HBox> paginas;
	private Label label;
	
	public DiscoView(Disco disco){
		this.disco = disco;
		trilhasPagina = new VBox(SPACING);
		paginas = new ArrayList<HBox>();
		
		initLayout();
	}
	
	private void initLayout(){
		createAlocacoesPagina();
		
		StackPane fundo = new StackPane();
		retangulo.setFill(Color.GRAY);
		Circle circuloMaior = new Circle(100);
		circuloMaior.setFill(Color.GRAY.darker());
		Circle circuloMedio = new Circle(50);
		circuloMedio.setFill(Color.GRAY);
		Circle circuloMenor = new Circle(25);
		circuloMenor.setFill(Color.GRAY.darker());

		trilhasPagina.setTranslateY(POSICAO_X_LISTA);
		trilhasPagina.setTranslateX(POSICAO_Y_LISTA);
		
		fundo.getChildren().addAll(retangulo, circuloMaior, circuloMedio, circuloMenor);
		
		Group grupo = new Group();
		grupo.getChildren().addAll(fundo, trilhasPagina);
		
		VBox vbox = new VBox(1);
		label = new Label("Disco");
		vbox.getChildren().addAll(label, grupo);
		
		this.getChildren().add(vbox);
	}
	
	private void createAlocacoesPagina(){
		int size = MemoriaRAM.NUMERO_MOLDURAS * 2;
		int numeroAlocacoes = 0;
		int numeroPaginas = 0;
		while(numeroAlocacoes < size){
			List<Circle> fileira = new ArrayList<Circle>();
			for(int i = 0; i < MAX_PAGINAS_LINHA; ++i){
				fileira.add(getMoldura());
			}
			HBox listaPaginas = new HBox(SPACING);
			listaPaginas.getChildren().addAll(fileira);
			trilhasPagina.getChildren().add(listaPaginas);
			paginas.add(listaPaginas);
			numeroAlocacoes+=4;
			++numeroPaginas;
		}
		retangulo = new Rectangle((LARGURA_FUNDO * numeroPaginas) + DIFERENCA_POSICIONAMENTO_LISTA, (ALTURA_FUNDO*numeroPaginas));
		retangulo.setArcHeight(50);
		retangulo.setArcWidth(50);
		this.getChildren().add(trilhasPagina);
	}
	
	private Circle getMoldura(){
		Circle moldura = new Circle(RAIO);
		moldura.setFill(Color.WHITE);
		moldura.setStroke(Color.BLACK);
		moldura.setStrokeWidth(BORDA_MOLDURA);
		return moldura;
	}
	
	public void update(){
		paginas.clear();
		trilhasPagina.getChildren().clear();
		initLayout();
		Iterator<Pagina> paginasIterator = disco.createIterator();
		int i = 0;
		int y = 0;
		while(paginasIterator.hasNext()){
			Pagina pagina = paginasIterator.next();
			PaginaView pv = new PaginaView(pagina.getEndereco());
			pv.setOnMouseClicked(null);
			pv.setOnMouseEntered(null);
			pv.setOnMouseExited(null);
			
			paginas.get(i).getChildren().set(y, pv);
			if(y == (MAX_PAGINAS_LINHA-1)){
				y = 0;
				++i;
			}else{
				++y;
			}
		}
	}

	@Override
	public void update(Node node) {
		update();		
	}
}
