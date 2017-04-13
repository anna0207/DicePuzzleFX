package application;

import java.util.ArrayList;
import java.util.List;

public class Move {

	private int[] dices;
	private List<Move> next;
	
	public Move(int first, int second, int third, int fourth) {
		dices = new int[4];
		dices[0] = first;
		dices[1] = second;
		dices[2] = third;
		dices[3] = fourth;
		
		next = new ArrayList<>();
	}
	
	public int getByRow(int index) {
		return dices[index - 1];
	}
	
	public void setRow(int index, int value) throws Exception {
		if (index > 0 && index < 5) {
			dices[index - 1] = value;
		} else {
			throw new Exception("Index out of bounds");
		}
	}
	
	public void addNext(Move move) {
		next.add(move);
	}
	
	public List<Move> getNextMoves() {
		return next;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Move) {
			Move move = (Move)object;
			return move.getByRow(1) == dices[0] &&
				move.getByRow(2) == dices[1] &&
				move.getByRow(3) == dices[2] &&
				move.getByRow(4) == dices[3];
		} else {
			return false;
		}
		
	}
	
	public boolean isNotZero() {
		return (dices[0] + dices[1] + dices[2] + dices[3]) > 0;
	}
	
	@Override
	public String toString() {
		return "" + dices[0] + dices[1] + dices[2] + dices[3];
	}
	
	public Move copy() {
		return new Move(dices[0], dices[1], dices[2], dices[3]);
	}
}
