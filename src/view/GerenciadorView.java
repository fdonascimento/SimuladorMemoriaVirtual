package view;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import simulador.Endereco;
import simulador.Gerenciador;
import util.ObserverNode;

public class GerenciadorView extends Application implements ObserverNode{

	private Gerenciador gerenciador;
	private MemoriaVirtualView memoriaVirtual;
	private MemoriaRAMView memoriaRAM;
	private BarraLateralView barraLateral;
	private EstruturaDadosView estruturaDados;
	private DiscoView disco;
	
	@Override
	public void start(Stage primaryStage) {
		gerenciador = Gerenciador.getInstance();
		
		primaryStage.setTitle("Simulador");
		disco = new DiscoView(gerenciador.getDisco());
		memoriaVirtual = new MemoriaVirtualView(gerenciador.getMemoriaVirtual(), gerenciador.getTabelaPagina());
		memoriaRAM = new MemoriaRAMView(gerenciador.getMemoriaRam());
		memoriaRAM.setTranslateX(230);
		memoriaRAM.setTranslateY(150);
		memoriaVirtual.attach(memoriaRAM);
		memoriaVirtual.attach(this);
		memoriaVirtual.attach(disco);
		
		estruturaDados = new EstruturaDadosView(gerenciador.getEstruturaDados());
		
		
		HBox hbox = new HBox();
		barraLateral = new BarraLateralView();
		barraLateral.attach(this);
		
		estruturaDados.attach(barraLateral);
		memoriaVirtual.attach(barraLateral);
		gerenciador.attach(barraLateral);
		
		HBox discoEstrutura = new HBox();
		discoEstrutura.getChildren().addAll(estruturaDados, disco);
		discoEstrutura.setTranslateX(70);
		
		VBox vbox = new VBox();
		vbox.getChildren().addAll(memoriaVirtual, discoEstrutura, memoriaRAM);
		hbox.getChildren().addAll(barraLateral, vbox);
		Scene scene = new Scene(hbox, 1200,700);
		scene.setFill(Color.LIGHTGRAY);

		scene.getStylesheets().add(GerenciadorView.class.getResource("Main.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void update(Node node) {
		if(node instanceof PaginaView) {
			Endereco endereco = ((PaginaView) node).getEndereco();
			Gerenciador.getInstance().referenciarPagina(endereco);
		}else if(node instanceof EscolherEstrategiaView){
			estruturaDados.setEstruturaDados(Gerenciador.getInstance().getEstruturaDados());
		}
		memoriaVirtual.update();
		memoriaRAM.update();
		estruturaDados.update();
		disco.update();
	}
}
