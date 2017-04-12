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
	
	public AIPlayer() {
		moves = new ArrayList<Move>();
		fillList();
	}
	
	public Move nextMove(Move move) {
		Random rand = new Random();
		List<Move> moves = move.getNextMoves();
		int index = rand.nextInt(moves.size());
		return moves.get(index);
	}	
	
	public boolean addCheck(int index, int number) {
		boolean check = true;
		for (int a : edges[index]) {
			if (a == number) {
				check = false;
			} else if (index == 346 && number == 0) {
				check = false;
			} else if (index == 1246 && number == 0) {
				check = false;
			} else if (index == 256 && number == 0) {
				check = false;
			} else if (index == 247 && number == 0) {
				check = false;
			}
		}
		return check;
	}
	
	public int findWinningIndex(int seq1000, int seq100, int seq10, int seq1) {
		
		int[] array = {0,11,101,1001,1100,1010,110,22,202,2002,2200,2020,220,33,303,
				3003,3300,3030,330,44,404,4004,4400,4040,440,55,505,5005,5500,5050,550,
				1230,1203,1023,1320,1302,1032,2130,2103,2013,2310,2301,2031,
				3120,3102,3012,3210,3201,3021,123,132,213,231,321,312,
				1450,1405,1045,1540,1504,1054,4150,4015,4510,4501,4051,5140,
				5104,5014,5410,5401,5041,145,154,415,451,541,514,1111,
				1122,1212,2112,2211,2121,1221,1133,1313,3113,3311,3131,1331,1144,1414,
				4114,4411,4141,1441,1155,1515,5115,5511,5151,1551};
		
		int index = 0;
		int temp = seq1000;
		while (temp > 0) {
			temp -= 1;
			index = temp*1000;
			index += seq100*100;
			index += seq10*10;
			index += seq1;
			for (int a : array) {
				if (index == a) {
					return a;
				}
			}
		}
		temp = seq100;
		while (temp > 0) {
			temp -= 1;
			index = seq1000*1000;
			index += temp*100;
			index += seq10*10;
			index += seq1;
			for (int a : array) {
				if (index == a) {
					return a;
				}
			}
		}
		temp = seq10;
		while (temp > 0) {
			temp -= 1;
			index = seq1000*1000;
			index += seq100*100;
			index += temp*10;
			index += seq1; 
			for (int a : array) {
				if (index == a) {
					return a;
				}
			}
		}
		temp = seq1;
		while (temp > 0) {
			temp -= 1;
			index = seq1000*1000;
			index += seq100*100;
			index += seq10*10;
			index += temp;
			for (int a : array) {
				if (index == a) {
					return a;
				}
			}
		}
		
		return temp;
	}
	
	public void addWinningMoves(int seq1000, int seq100, int seq10, int seq1) {
		
		int index = 0;
		int temp = seq1000;
		int res = 0;
		while (temp > 0) {
			temp -= 1;
			index = temp*1000;
			index += seq100*100;
			index += seq10*10;
			index += seq1;
			res = findWinningIndex(temp, seq100, seq10, seq1);
			if (addCheck(index, res)) {
				edges[index].add(res);
				//System.out.println(index + " " + res);
			}
			if (index > 0) {
				addWinningMoves(res/1000, res%1000/100, res%100/10, res%10);
			}
		}
		temp = seq100;
		while (temp > 0) {
			temp -= 1;
			index = seq1000*1000;
			index += temp*100;
			index += seq10*10;
			index += seq1;
			res = findWinningIndex(seq1000, temp, seq10, seq1);
			if (addCheck(index, res)) {
				edges[index].add(res);
				//System.out.println(index + " " + res);
			}
			if (res > 0) {
				addWinningMoves(res/1000, res%1000/100, res%100/10, res%10);
			}	
		}
		temp = seq10;
		while (temp > 0) {
			temp -= 1;
			index = seq1000*1000;
			index += seq100*100;
			index += temp*10;
			index += seq1;
			res = findWinningIndex(seq1000, seq100, temp, seq1);
			if (addCheck(index, res)) {
				edges[index].add(res);
				//System.out.println(index + " " + res);
			}
			if (res > 0) {
				addWinningMoves(res/1000, res%1000/100, res%100/10, res%10);
			}
		}
		temp = seq1;
		while (temp > 0) {
			temp -= 1;
			index = seq1000*1000;
			index += seq100*100;
			index += seq10*10;
			index += temp;
			res = findWinningIndex(seq1000, seq100, seq10, temp);
			if (addCheck(index, res)) {
				edges[index].add(res);
				//System.out.println(index + " " +  res);
			}
			if (res > 0) {
				addWinningMoves(res/1000, res%1000/100, res%100/10, res%10);
			}
		}
		
	}
	
	// winning moves (opponents turn)
	// 2-2, 3-3, 4-4, 5-5
	// 1-2-3, 1-4-5
	// 1-1-2-2, 1-1-3-3, 1-1-4-4, 1-1-5-5
	
	public void fillList() {
		
		addWinningMoves(0,2,4,6);  
		
		addWinningMoves(0,3,5,6);
		addWinningMoves(0,3,4,7);
		addWinningMoves(0,2,5,7);
		
		addWinningMoves(1,2,5,6);
		addWinningMoves(1,2,4,7);
		
		addWinningMoves(1,3,4,6);
		
		///////////////////////////
		// BAD MOVES
		///////////////////////////
			
		addWinningMoves(1,1,5,5);
		addWinningMoves(1,0,5,4);
		addWinningMoves(1,3,3,1);
		addWinningMoves(1,3,2,0);
		addWinningMoves(1,3,1,3);
		addWinningMoves(1,3,0,2);
		addWinningMoves(1,1,5,5);
		addWinningMoves(1,0,5,4);
		addWinningMoves(1,3,1,3);
		addWinningMoves(1,3,0,2);
		addWinningMoves(1,3,3,1);
		addWinningMoves(1,3,2,0);

		readMoves();
		
	}
	
	private void readMoves() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("moves.txt"));
			String line = reader.readLine();
			while (line != null) {
				String[] edge = line.split(" ");
				int from = Integer.parseInt(edge[0]);
				int to = Integer.parseInt(edge[1]);
				
				Move fromMove = new Move(from/1000, (from/100)%10, (from/10)%10, from%10);
				Move toMove = new Move(to/1000, (to/100)%10, (to/10)%10, to%10);
				
				fromMove.addNext(toMove);
				
				line = reader.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
