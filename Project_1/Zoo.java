
public class Zoo {
	private char[][] zoo;
	private int numLizards;
	private int side;
	private String method;
	
	public Zoo(char[][] zoo, int numLizards, int side, String method){
		this.zoo = zoo;
		this.numLizards = numLizards;
		this.side = side;
		this.method = method;
	}
	
	public void setZoo(char[][] zoo) {
		this.zoo = zoo;
	}

	public void setNumLizards(int numLizards) {
		this.numLizards = numLizards;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public char[][] getZoo() {
		return zoo;
	}

	public int getNumLizards() {
		return numLizards;
	}

	public int getSide() {
		return side;
	}

	public String getMethod() {
		return method;
	}
	
}
