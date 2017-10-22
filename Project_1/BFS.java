import java.util.Deque;
import java.util.LinkedList;


public class BFS {
	private Zoo zoo;
	public BFS(Zoo zoo) {
		// TODO Auto-generated constructor stub
		this.zoo = zoo;
	}
	public char[][] bfsSearch(){
		char[][] map = zoo.getZoo();
		int numLizards = zoo.getNumLizards();
		int side = zoo.getSide();
		
		Deque<Point> deque = new LinkedList<>();
		Deque<char[][]> states = new LinkedList<>();
		//initialize every point can be the start in the bfs tree in first row
		
		for(int j = 0; j < side; j++){
			for(int i = 0; i < side; i++){
				if(map[i][j] == '2') continue;
				deque.offer(new Point(i, j));
				map[i][j] = '1';
				states.offer(deepCopyState(map));
				map[i][j] = '0';
			}
		}
		
		while(!deque.isEmpty()){
			numLizards--;
			if(numLizards == 0 && !states.isEmpty()) return states.poll();
			for(int i = deque.size(); i > 0; i--){
				Point cell = deque.poll();
				char[][] statemap = states.poll();
				//check each cell's validation, if it can be placed next lizard,
				//it will put in the queue as well as its state
				
				//find every possible node point in map
				for(int col = cell.col; col < map.length ; col++){
					int row = col == cell.col ? cell.row + 1: 0;
					if(row == map.length) continue;
					for(; row < side; row++){
						if(statemap[row][col] == '2') continue;
						if(validate(statemap, row, col)){
							//put new point and its state in queue
							statemap[row][col] = '1';
							deque.offer(new Point(row, col));
							states.offer(deepCopyState(statemap));
							statemap[row][col] = '0';
						}
					}
					
				}
				
			}
		}
		return null;
	}
	
	//state deep copy
	private char[][] deepCopyState(char[][] state){
		char[][] newState = new char[state.length][state[0].length];
		for(int i = 0; i < state.length; i++){
			newState[i] = state[i].clone();
		}
		
		return newState;
	}
	//check validation
	private boolean validate(char[][] map, int x, int y){
		if(map[x][y] == '1') return false;
		//check col
		for(int i = y - 1; i >= 0; i--){
			if(map[x][i] == '2') break;
			else if(map[x][i] == '1') return false;
		}
		//check 45
		for(int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--){
			if(map[i][j] == '2') break;
			else if(map[i][j] == '1') return false;
		}
		//check 135
		for(int i = x + 1, j = y - 1; i < map.length && j >= 0; i++, j--){
			if(map[i][j] == '2') break;
			else if(map[i][j] == '1') return false;
		}
		//check row
		for(int i = x - 1; i >= 0; i--){
			if(map[i][y] == '2') break;
			else if(map[i][y] == '1') return false;
		}
		return true;			
	}
}
