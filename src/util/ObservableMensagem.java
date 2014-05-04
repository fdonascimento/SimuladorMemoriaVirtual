package util;

public interface ObservableMensagem {

	public void attach(ObserverMensagem o);
	public void detach(ObserverMensagem o);
	public void notity(String mensagem, SeveridadeEnum severidade);
}
