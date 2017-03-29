package application;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Dice extends Rectangle {

	public Dice(int row, int column) {
		super();
		
		this.setHeight(50);
		this.setWidth(50);
		this.setArcHeight(5);
		this.setArcWidth(5);
		this.setFill(javafx.scene.paint.Color.DODGERBLUE);
		this.setStroke(javafx.scene.paint.Color.BLACK);
		this.setStrokeType(StrokeType.INSIDE);
		GridPane.setRowIndex(this, row);
		GridPane.setColumnIndex(this, column);
	}
	
}
