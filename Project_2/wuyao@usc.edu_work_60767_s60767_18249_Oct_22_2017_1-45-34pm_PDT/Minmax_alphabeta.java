import java.util.ArrayList;
import java.util.List;


public class Minmax_alphabeta {
	FruitMap fruitMap;
	private double remainTime;
	private int side;
	private final int[] direction = {0, 1, 0, -1, 0};
	
	
	public Minmax_alphabeta(FruitMap fruitMap) {
		// TODO Auto-generated constructor stub
		this.fruitMap = fruitMap;
		this.remainTime = fruitMap.getRemainingTime();
		this.side = fruitMap.getSide();
	}
	
	public char[][] runMin_max(int depth){
		char[][] map = fruitMap.getMap();
		char[][] state = deepCopyState(map);
	
     if(remainTime > 5) depth = decideDepth();
		
		
		int[] res = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, 0);
		int points = dfsforSection(map, res[0], res[1], map[res[0]][res[1]]);
		fallDown(map);
		char col = (char)(res[1] + 'A');
		fruitMap.nextMove = "" + col + (res[0] + 1);
		return map;
	}
	
	private int decideDepth(){
		int depth = 1;
		//base case
		if(side <= 10) depth = 5;
		else if(side < 16) depth = 4;
		else depth = 3;
		//star number take half of the board then add 1 depth, total depth < 8
		int star = fruitMap.starNum;
		if(star >= side * side / 2) depth++;
		if(star >= side * side / 4 * 3) depth++;
		if(star >= side * side / 8 * 7) depth++;	
		return depth;
	}

	/**
	 * run max recursion
	 * @param alpha
	 * @param beta
	 * @return
	 */
	private int[] maxValue(char[][] state, int alpha, int beta, int depth, int sum){		
		int[] res = new int[3];
		//generate successors
		List<int[]> section = sectionDiv(state);
		if(section.size() == 0){
			res[2] = sum;
			return res;
		}
		if(depth - 1 == 0){
			int max = Integer.MIN_VALUE;
			for(int[] values: section)
				if(values[2] + sum > max){
					max = values[2] + sum;
					res[0] = values[0]; res[1] = values[1];
				}
			res[2] = max;
			return res;
		}	
		//traverse successors
		for(int[] point: section){
			char[][] newState = deepCopyState(state);	
			//generate next state map				
			dfsforSection(newState, point[0], point[1], newState[point[0]][point[1]]);
			fallDown(newState);	
			//result array from min
			int sucRes = minValue(newState, alpha, beta, depth - 1, sum + point[2]);
			if(alpha < sucRes){
				alpha = sucRes; 
				res[2] = sucRes; res[0] = point[0]; res[1] = point[1];
			}
			if(alpha >= beta){			
				res[2] = beta;
				return res;
			}		
		}	
		return res;
	}
	/**
	 * run min recursion
	 * @param alpha
	 * @param beta
	 * @return
	 */
	private int minValue(char[][] state, int alpha, int beta, int depth, int sum){
	
		List<int[]> section = sectionDiv(state);
		
		if(section.size() == 0) return sum;
			
		if(depth - 1 == 0){
			int min = Integer.MAX_VALUE;
			for(int[] values: section)
				min = Math.min(sum - values[2], min);
			return min;
		}
		//successor
		for(int[] point: section){
			char[][] newState = deepCopyState(state);
			dfsforSection(newState, point[0], point[1], newState[point[0]][point[1]]);
			fallDown(newState);
			//result array from max
			int[] sucRes = maxValue(newState, alpha, beta, depth - 1, sum - point[2]);
			if(beta > sucRes[2]){
				beta = sucRes[2];
			}
			if(beta <= alpha){
				return beta;
			}
		}
		return beta;
	}
	/**
	 * divide map to several sections. each section will lead same result in next state
	 * @param map
	 */
	private List<int[]> sectionDiv(char[][] map){
		ArrayList<int[]> sections = new ArrayList<>();
		char[][] tmp = deepCopyState(map);
		
		for(int i = 0; i < side; i++){
			for(int j = 0; j < side; j++){
				if(tmp[i][j] == '*') continue;
				int points = dfsforSection(tmp, i, j, tmp[i][j]);
				int[] cell = {i, j, points * points};
				sections.add(cell);
			}
		}
		return sections;
	}
	private int dfsforSection(char[][] map, int x, int y, char kind){
		if(map[x][y] != kind || map[x][y] == '*') return 0;
		map[x][y] = '*';
		
		int sum = 1;
		for(int i = 0; i < direction.length - 1; i++){
			int row = x + direction[i];
			int col = y + direction[i + 1];
			if(row < side && row >= 0 && col < side && col >= 0){
				sum += dfsforSection(map, row, col, kind);
			}
		}
		return sum;
	}
	private void fallDown(char[][] map){
		
		for(int j = 0; j < side; j++){
			int index = side - 1;
			for(int i = side - 1; i >= 0; i--){
				if(map[i][j] == '*') continue;
				if(index == i) index--;
				else{
					char tmpc = map[i][j];
					map[i][j] = map[index][j];
					map[index--][j] = tmpc;
				}
			}
		}
	}
	
	//state deep copy
	private char[][] deepCopyState(char[][] state){
		char[][] newState = new char[state.length][state[0].length];
		for(int i = 0; i < state.length; i++){
			newState[i] = state[i].clone();
		}
		return newState;
	}
	

}
