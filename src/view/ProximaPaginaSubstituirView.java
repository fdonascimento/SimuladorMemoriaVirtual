package view;

import simulador.Endereco;
import util.ObserverNode;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ProximaPaginaSubstituirView extends StackPane implements ObserverNode{

	private Rectangle background;
	private Label header;
	
	public ProximaPaginaSubstituirView() {
		init();
	}
	
	private void init(){
		header = new Label("Próxima página a ser substituída");
		header.setTextFill(Color.BLACK.darker());
		background = new Rectangle(ProcessoView.RAIO*12, ProcessoView.RAIO*5.4);
		background.setFill(Color.LIGHTBLUE);
		background.setStroke(Color.BLUE);
		background.setStrokeWidth(MenuProcessosView.BORDA);

		
		getChildren().addAll(background, header);
	}

	@Override
	public void update(Node node) {
		if(node instanceof EstruturaDadosView){
			EstruturaDadosView edv = (EstruturaDadosView)node;
			Node p = edv.getProximaPaginaSubstituir();
			if(p instanceof PaginaView){				
				createPagina((PaginaView)p);
			}else{
				this.getChildren().clear();
				getChildren().addAll(background, header);
			}
		}
		
	}
	
	private void createPagina(PaginaView paginaView){
		Endereco endereco = new Endereco(paginaView.getEndereco().getIdPagina(), paginaView.getEndereco().getIdProcesso());
		PaginaView pagina = new PaginaView(endereco);
		pagina.setOnMouseClicked(null);
		pagina.setOnMouseEntered(null);
		pagina.setOnMouseExited(null);
		
		VBox vbox = new VBox(1);
		vbox.getChildren().addAll(header, pagina);
		vbox.setTranslateX(10);
		vbox.setTranslateY(10);
		pagina.setTranslateX(-10);
		
		this.getChildren().clear();
		this.getChildren().addAll(background, vbox);
	}
}
