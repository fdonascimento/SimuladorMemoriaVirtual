package util;

public interface Observable {

	public void attach(ObserverNode o);
	public void detach(ObserverNode o);
	public void notity();
}
