import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class ZooGenerator {
	public char[][] generate(int side, int tree){
		char[][] zoo = new char[side][side];
		HashSet<Point> set = new HashSet<>();
		
		Random random = new Random();
		for(char[] row: zoo)
			Arrays.fill(row, '0');
		for(int i = 0; i < tree; i++){
			set.add(new Point(random.nextInt(side), random.nextInt(side)));
		}
		for(Point p: set){
			zoo[p.row][p.col] = '2';
		}
		return zoo;
	}
}
