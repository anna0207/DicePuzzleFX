package application;

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
	}
	
	public int getByRow(int index) {
		return dices[index - 1];
	}
	
	public void addNext(Move move) {
		next.add(move);
	}
	
	public List<Move> getNextMoves() {
		return next;
	}

	public boolean equals(Move move) {
		return move.getByRow(1) == dices[0] &&
				move.getByRow(2) == dices[1] &&
				move.getByRow(3) == dices[2] &&
				move.getByRow(4) == dices[3];
	}
}
