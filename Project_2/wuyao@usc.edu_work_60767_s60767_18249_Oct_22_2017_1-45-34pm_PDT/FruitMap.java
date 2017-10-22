class FruitMap{
	private int kinds;
	private int side;
	private double remainingTime;
	private char[][] map;
	int starNum = 0;
	String nextMove = "";

	
		public FruitMap(int kinds, int side, double remainingTime, char[][] map) {
			// TODO Auto-generated constructor stub
			this.kinds = kinds;
			this.side = side;
			this.remainingTime = remainingTime;
			this.map = map;
		}

		public int getKinds() {
			return kinds;
		}

		public void setKinds(int kinds) {
			this.kinds = kinds;
		}

		public int getSide() {
			return side;
		}

		public void setSide(int side) {
			this.side = side;
		}

		public double getRemainingTime() {
			return remainingTime;
		}

		public void setRemainingTime(double remainingTime) {
			this.remainingTime = remainingTime;
		}

		public char[][] getMap() {
			return map;
		}

		public void setMap(char[][] map) {
			this.map = map;
		}
		
		
}