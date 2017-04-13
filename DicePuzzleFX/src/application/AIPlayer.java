package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AIPlayer {
 
	private List<Move> moves;
	private List<Move> wins;
	private Move goal;
	private Difficulty difficulty;
	private boolean firstMove;
	
	public AIPlayer() {
		moves = new ArrayList<Move>();
		wins = new ArrayList<Move>();
		goal = new Move(0,0,0,0);
		moves.add(goal);
		difficulty = Difficulty.EASY;
		firstMove = true;
		
		readWins();
		readMoves();
	}
	
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
		this.firstMove = true;
	}
	
	public Move nextMove(Move move) throws Exception {
		Random rand = new Random();
		Move next = null;
		if (difficulty == Difficulty.DIFFICULT && firstMove) {
			int row = rand.nextInt(4) + 1;
			int value = move.getByRow(row);
			Move ranMove = move.copy();
			ranMove.setRow(row, --value);
			firstMove = false;
			return ranMove;
		}
		firstMove = false;
		
		for (Move temp : moves) {
			if (temp.equals(move)) {
				next = temp;
			}
		}
		if (next == null) {
			return getNextMove(move);
		}
		List<Move> nextMoves = next.getNextMoves();
		if (nextMoves.size() > 0) {
			int index = rand.nextInt(nextMoves.size());
			return nextMoves.get(index);
		} else {
			return getNextMove(next);
		}
	}	
	
	public Move getNextMove(Move move) {
		Move randomMove = null;
		Random random = new Random();
		int rCount = random.nextInt(move.getByRow(1) + move.getByRow(2) + move.getByRow(3) + move.getByRow(4));
		int count = 0;
		Move tempMove = move.copy();
		for (int i = 1; i <= 4; i++) {
			int temp = tempMove.getByRow(i);
			while (temp > 0) {
				temp--;
				try {
					tempMove.setRow(i, temp);
					for (Move win : wins) {
						if (tempMove.equals(win)) {
							move.addNext(tempMove);
							return tempMove;
						}
					}
					if (count == rCount) {
						randomMove = tempMove;
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				count++;
			}
			tempMove = move.copy();
		}
		
		return randomMove;
	}
	
	private void readWins() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("wins.txt"));
			String line = reader.readLine();
			while (line != null) {
				int move = Integer.parseInt(line);
				wins.add(new Move(move/1000, (move/100)%10, (move/10)%10, move%10));
				line = reader.readLine();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage()); 
			e.printStackTrace();
		}
		
	}
	
	// winning moves (opponents turn)
	// 2-2, 3-3, 4-4, 5-5
	// 1-2-3, 1-4-5
	// 1-1-2-2, 1-1-3-3, 1-1-4-4, 1-1-5-5
	
	private void readMoves() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("moves.txt"));
			String line = reader.readLine();
			while (line != null) {
				String[] edge = line.split(" ");
				int from = Integer.parseInt(edge[0]);
				int to = Integer.parseInt(edge[1]);
				
				Move toMove = new Move(to/1000, (to/100)%10, (to/10)%10, to%10);
				Move fromMove = new Move(from/1000, (from/100)%10, (from/10)%10, from%10);
				boolean fromExists = false;
				boolean toExists = false;
				for (Move move : moves) {
					if (move.equals(fromMove)) {
						fromExists = true;
						fromMove = move;
					} else if (move.equals(toMove)) {
						toExists = true;
						toMove = move;
					}
				}
				fromMove.addNext(toMove);
				
				if (!fromExists) {
					moves.add(fromMove);
				}
				if (!toExists) {
					moves.add(toMove);
				}
				line = reader.readLine();
			}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}
