package application;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Dice extends Rectangle {

	private int row;
	
	public Dice(int row, int column) {
		super();
		this.row = row;
		
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
	
	public int getIndexRow() {
		return 4 - ((row + 1) / 2);
	}
	
}
