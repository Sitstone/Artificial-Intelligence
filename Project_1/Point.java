
public class Point {
	int row, col;
	
	public Point(int row, int col) {
		// TODO Auto-generated constructor stub
		this.row = row;
		this.col = col;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return new Integer(row).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Point point = (Point) obj;
		return this.row == point.row && this.col == point.col;
	}
	
}
