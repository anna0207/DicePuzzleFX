package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class DiceController implements Initializable {

	@FXML
	private GridPane diceGridPane;
	@FXML
	private JFXButton goButton, resetButton;

	private List<Rectangle> chosenRecs;
	private AIPlayer ai;
	
	public DiceController() {
		chosenRecs = new ArrayList<>();
		ai = new AIPlayer();
	}
	
	private void nextMove() {
		int current = thisMove();
		int next = ai.nextMove(current); 
		
		for (int row = 3; row >= 0; row--) {
			int number = 0;
			for (Node node : diceGridPane.getChildren()) {
				Dice dice = (Dice) node;
				if (dice.getIndexRow() == row) {
					number++;
					if (number > ((next / Math.pow(10, row)) % 10)) {
						chosenRecs.add(dice);
					}
				}
			}
		}
		
		diceGridPane.getChildren().removeAll(chosenRecs);
		chosenRecs.clear();
	}
	
	private int thisMove() {
		int move = 0;
		List<Node> dices = diceGridPane.getChildren();
		for (Node node : dices) {
			Dice dice = (Dice) node;
			move += Math.pow(10, dice.getIndexRow());
		}
		return move;
		
	}
	
	private void clear(Rectangle rec) {
		chosenRecs.remove(rec);
		rec.setFill(javafx.scene.paint.Color.DODGERBLUE);
	}
	
	private void select(Rectangle rec) {
		chosenRecs.add(rec);
		rec.setFill(javafx.scene.paint.Color.DARKBLUE);
	}
	
	@FXML
	private void handleSelect(MouseEvent event) {
		Rectangle rec = (Rectangle)event.getSource();
		if (chosenRecs.contains(rec)) {
			clear(rec);
		} else {
			select(rec);
		}
		
		int row = GridPane.getRowIndex(rec);
		for (Node otherRec : diceGridPane.getChildren()) {
			if (GridPane.getRowIndex(otherRec) != row) {
				clear((Rectangle)otherRec);
			}
		}
	}
	
	@FXML
	private void handleContinue(ActionEvent event) {
		diceGridPane.getChildren().removeAll(chosenRecs);
		chosenRecs.clear();
		
		nextMove();
	}
	
	@FXML
	private void resetDices() {
		diceGridPane.getChildren().clear();
		int offset = 1;
		for (int i = 7; i > 0; i -= 2) {
			for (int j = offset; j < 15 - offset; j += 2) {
				diceGridPane.getChildren().add(new Dice(i, j));
			}
			offset += 2;
		}
		
		for (Node node : diceGridPane.getChildren()) {
			Rectangle rec = (Rectangle)node;
			rec.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					handleSelect(event);
				}
			});
		}
		chosenRecs.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		resetDices();
	
		goButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleContinue(event);
			}
		});

		resetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				resetDices();
			}
		});
		
	}
	
}
