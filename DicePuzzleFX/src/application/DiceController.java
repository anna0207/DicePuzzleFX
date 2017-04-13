package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class DiceController implements Initializable {

	@FXML
	private GridPane diceGridPane;
	@FXML
	private JFXButton goButton, resetButton, easyButton, difficultButton;
	@FXML
	private Label displayLabel;

	private ObservableList<Rectangle> chosenRecs;
	private AIPlayer ai;
	
	public DiceController() {
		chosenRecs = FXCollections.observableArrayList();
		ai = new AIPlayer();
	}
	
	private void nextMove() {
		try {
			Move current = thisMove();
			Move next = ai.nextMove(current); 
			System.out.println(current.toString() + " + " + next.toString());
			for (int row = 3; row >= 0; row--) {
				int number = 0;
				for (Node node : diceGridPane.getChildren()) {
					Dice dice = (Dice) node;
					if (dice.getIndexRow() == row) {
						number++;
						if (number > next.getByRow(row + 1)) {
							chosenRecs.add(dice);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
			
		diceGridPane.getChildren().removeAll(chosenRecs);
		chosenRecs.clear();
		if (diceGridPane.getChildren().isEmpty()) {
			displayLabel.setText("Sorry. You lost! Try again");
		}
	}
	
	private Move thisMove() {
		Move move = new Move(0,0,0,0);
		List<Node> dices = diceGridPane.getChildren();
		for (Node node : dices) {
			try {
				Dice dice = (Dice) node;
				int rowDices = move.getByRow(dice.getIndexRow() + 1);
				move.setRow(dice.getIndexRow() + 1, rowDices + 1);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
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
		
		if (diceGridPane.getChildren().isEmpty()) {
			displayLabel.setText("Wuhu You Won");
		} else {
			nextMove();
		}
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
		easyButton.setDisable(false);
		difficultButton.setDisable(false);
		displayLabel.setText("Try beat the computer");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		resetDices();
		
		displayLabel.setTextFill(javafx.scene.paint.Color.DARKGREY);
		
		goButton.disableProperty().bind(Bindings.isEmpty(chosenRecs));
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
		
		easyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ai.setDifficulty(Difficulty.EASY);
				nextMove();
				easyButton.setDisable(true);
				difficultButton.setDisable(true);
			}
		});
		
		difficultButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ai.setDifficulty(Difficulty.DIFFICULT);
				nextMove();
				easyButton.setDisable(true);
				difficultButton.setDisable(true);
			}
		});
		
	}
	
}
