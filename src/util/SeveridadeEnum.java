package util;

import javafx.scene.paint.Color;

public enum SeveridadeEnum {

	FATAL	(0, Color.RED.darker()),
	INFO	(1, Color.GREEN.darker()),
	WARN	(2, Color.YELLOW.darker());
	
	private int id;
	private Color color;
	
	private SeveridadeEnum(int id, Color color){
		this.id = id;
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
