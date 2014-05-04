package view;

import java.util.ArrayList;
import java.util.List;

import util.Observable;
import util.ObserverNode;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Circulo extends StackPane implements Observable{

	protected final double RAIO = 30;
	protected final int LARGURA_BORDA = 5;
	protected final int DURATION = 300;
	
	private Label label;
	private Circle body;
	private Color preenchimento;
	private Color corBorda;
	
	private List<ObserverNode> observers;
	
	public Circulo(Color preenchimento){
		
		observers = new ArrayList<ObserverNode>();
		
		body = new Circle(RAIO);
		body.setStrokeWidth(LARGURA_BORDA);
		this.preenchimento = preenchimento;
		this.corBorda = preenchimento;
		body.setFill(preenchimento);
		
		label = new Label();
		label.setScaleX(2);
		label.setScaleY(2);
		
		init();
	}
	
	protected void init(){
		getChildren().addAll(body, label);
		setOnMouseClicked(getAnimationMouseClicked());
		setOnMouseEntered(borderColorMouseEntered);
		setOnMouseExited(borderColorMouseExited);
		setBorder(preenchimento);
	}
	
	public void setBorder(Color color){
		body.setStroke(color);
	}
	
	public Color getBorder(){
		return (Color)body.getStroke();
	}
	
	public void setFill(Color color){
		body.setFill(color);
	}
	
	public Color getFill(){
		return (Color) body.getFill();
	}
	
	public String getLabel() {
		return label.getText();
	}

	public void setLabel(String label) {
		this.label.setText(label);
	}
	
	public double getRaio() {
		return body.getRadius();
	}

	public void setRaio(double raio) {
		body.setRadius(raio);
	}
	
	public Color getPreenchimento(){
		return preenchimento;
	}
	
	public void disable(){
		setOnMouseClicked(null);
		setOnMouseEntered(null);
		setOnMouseExited(null);
		setFill(getFill().grayscale());
		setBorder(getBorder().grayscale());
	}
	
	public void enable(){
		setOnMouseClicked(getAnimationMouseClicked());
		setOnMouseEntered(borderColorMouseEntered);
		setOnMouseExited(borderColorMouseExited);
		setBorder(corBorda);
		setFill(preenchimento);
	}
	
	public EventHandler<Event> getAnimationMouseClicked(){
		EventHandler<Event> animationMouseClicked = new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				ScaleTransition scaleTransition = new ScaleTransition();
				scaleTransition.setFromX(1);
				scaleTransition.setFromY(1);
				scaleTransition.setToX(2);
				scaleTransition.setToY(2);
				scaleTransition.setDuration(new Duration(DURATION));
				scaleTransition.setNode((Node)event.getSource());
				Transition transition = scaleTransition;
				transition.setAutoReverse(true);
				transition.setCycleCount(2);
				transition.play();
				Circulo c = (Circulo)event.getSource();
				c.notity();
			}
		};
		
		return animationMouseClicked;
	}
	
	EventHandler<Event> borderColorMouseEntered = new EventHandler<Event>(){
		
		@Override
		public void handle(Event event) {
			Circulo circulo = (Circulo)event.getSource();
			circulo.setBorder(Color.YELLOW);
		}
	};
	
	EventHandler<Event> borderColorMouseExited = new EventHandler<Event>(){
		
		@Override
		public void handle(Event event) {
			Circulo circulo= (Circulo)event.getSource();
			circulo.setBorder(corBorda);
		}
	};
	
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

	public Color getCorBorda() {
		return corBorda;
	}

	public void setCorBorda(Color corBorda) {
		this.corBorda = corBorda;
	}

}
