import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class SA {
private Zoo zoo;
private double InitiaTem;
private Random random;

	public SA(double InitiaTem, Zoo zoo) {
		// TODO Auto-generated constructor stub
		this.zoo = zoo;
		this.InitiaTem = InitiaTem;
		random = new Random();
	}
	/**
	 * 2 termination conditions, 1. T is small enough 2. Time exceeds 5 minutes
	 */
	public Queue<Point> simulatedAnnealing(){
		
		int numLizards = zoo.getNumLizards();
		int side = zoo.getSide();
		char[][] map = zoo.getZoo();
		//every randomly placement is a state
		Queue<Point> curState = new LinkedList<>();
		//Initialize state
		HashSet<Point> set = new HashSet<>();
		int tmpNum = numLizards;
		while(tmpNum != 0){
			int row = random.nextInt(side);
			int col = random.nextInt(side);
			Point point = new Point(row, col);
			if(map[row][col] != '2' && set.add(point)){
				curState.offer(point);
				tmpNum--;
			}
		}
		
		double T = InitiaTem;
		//To avoid no way to generate another state
		int curEnergy = isValid(map, curState);
		if(curEnergy == 0) return curState;
		int time = 1;
		//timer
		long startTime = System.currentTimeMillis();
    	long elapsedTime = 0L;
    	long intervals = 300 * 1000;
    	
		
		while (elapsedTime < intervals) {
			
			T = -Math.log(time++) + InitiaTem;
			Queue<Point> nextState = stateGenerateor(curState, map, side);
			int nextEnegy = isValid(map, nextState);
			if(nextEnegy == 0) return nextState;
			int deltaE = nextEnegy - curEnergy;
			
			if(deltaE > 0){
				double probability = probabilityAcceptCal(-deltaE, T);
				if(probability < 0.001) return null;
				//System.out.println(probability);
				if(random.nextDouble() <= probability){
					curState = nextState; //accept bad moves
					curEnergy = nextEnegy;
				}
			}else{		
				curState = nextState;
				curEnergy = nextEnegy;
			}
			//System.out.println(T);
    	    elapsedTime = (new Date()).getTime() - startTime;
		}
		return null;
	}
	
	/**
	 * Generate a new state by randomly place a lizard
	 * @param map
	 * @param side
	 * @param numLizard
	 * @return points queue
	 */
	private Queue<Point> stateGenerateor(Queue<Point> curState, char[][] map, int side){
		//new point can't in the tree point or be original points
		HashSet<Point> set = new HashSet<>(curState);
		Point tmp = curState.poll();
		
		//check each point in the map, to find a no conflict one
		HashSet<Point> set2 = new HashSet<>(curState);
		for(int i = 0; i < side; i++){
			for(int j = 0; j < side; j++){
				if(map[i][j] == '2' || set2.contains(new Point(i, j))) continue;
				int conflic = Validhelper(set2, map, i, j);
				if(conflic == 0){
					Queue<Point> newState = new LinkedList<>(curState);
					newState.offer(new Point(i, j)); curState.offer(tmp);
					return newState;
				}
			}
		}
		Point newPoint = new Point(random.nextInt(side), random.nextInt(side));
		//can't find that point so randomly get one
		while(map[newPoint.row][newPoint.col] == '2' || set.contains(newPoint)){
			newPoint.row = random.nextInt(side);
			newPoint.col = random.nextInt(side);
		}
		Queue<Point> newState = new LinkedList<>(curState);
		newState.offer(newPoint); curState.offer(tmp);
		return newState;
	}
	
	/**
	 * Check the arrangement is valid or not
	 * @param map
	 * @param pointsQ
	 * @return
	 */
	private int isValid(char[][] map, Queue<Point> pointsQ){
		//store curstate points in a set for following check
		HashSet<Point> pointsSet = new HashSet<>(pointsQ);
		Iterator<Point> iterator = pointsQ.iterator();
		int enery = 0;
		while(iterator.hasNext()){
			Point point = iterator.next();
			enery += Validhelper(pointsSet, map, point.row, point.col);
		}
		return enery;
	}
	
	private int Validhelper(HashSet<Point> setQ, char[][] map, int x, int y){
		int[][] dir = {{0, 1},{0, -1},{1, 0},{-1, 0},{1, 1},{1, -1},{-1, 1},{-1, -1}};
		
		int attacked = 0;
		//create existed points set		
		for(int[] oneDir: dir){
			int row = x, col = y;
			while(row >= 0 && col >= 0 && row < map.length && col < map.length){
				row += oneDir[0];
				col += oneDir[1];
				if(row >= 0 && col >= 0 && row < map.length && col < map.length){
					if(setQ.contains(new Point(row, col))) attacked++;// meet a lizard
					else if(map[row][col] == '2') break;
				}
			}
		}
		return attacked;
	}
	
	private double probabilityAcceptCal(int deltaE, double T){
		return Math.exp(deltaE / T);
	}
}
