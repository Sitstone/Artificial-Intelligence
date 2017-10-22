import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;

public class ResultCheck {
	public int isValid(char[][] map, Queue<Point> pointsQ){
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
}
