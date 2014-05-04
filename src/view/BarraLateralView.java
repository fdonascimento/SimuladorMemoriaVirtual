package view;

import util.Observable;
import util.ObserverMensagem;
import util.ObserverNode;
import util.SeveridadeEnum;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class BarraLateralView extends VBox implements Observable, ObserverNode, ObserverMensagem{

	private MenuProcessosView menuProcessos;
	private AdicionarProcessoView adicionarProcesso;
	private EscolherEstrategiaView escolherEstrategia;
	private ProximaPaginaSubstituirView proximaPagina;
	private UltimaPaginaReferenciadaView ultimaPagina;
	private ConsoleView console;
	
	public BarraLateralView(){
		setId("barraLateral");
		initLayout();
		menuProcessos.attach(adicionarProcesso);
		escolherEstrategia.attach((ObserverMensagem)console);
		adicionarProcesso.attach((ObserverMensagem)console);
	}
	
	private void initLayout(){
		menuProcessos = new MenuProcessosView();
		adicionarProcesso = new AdicionarProcessoView();
		escolherEstrategia = new EscolherEstrategiaView();
		proximaPagina = new ProximaPaginaSubstituirView();
		ultimaPagina = new UltimaPaginaReferenciadaView();
		console = new ConsoleView();
		getChildren().addAll(menuProcessos, adicionarProcesso, escolherEstrategia, proximaPagina, ultimaPagina, console);
	}

	@Override
	public void attach(ObserverNode o) {
		adicionarProcesso.attach(o);
		escolherEstrategia.attach(o);
	}

	@Override
	public void detach(ObserverNode o) {
		adicionarProcesso.detach(o);
		escolherEstrategia.detach(o);
	}

	@Override
	public void notity() {
		adicionarProcesso.notity();
	}

	@Override
	public void update(Node node) {
		proximaPagina.update(node);
		ultimaPagina.update(node);
	}

	@Override
	public void update(String mensagem, SeveridadeEnum severidade) {
		console.update(mensagem, severidade);
	}
}
