import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.io.*;

public class homework {
	
	public static void main(String[] args){
		try {
			String fileName = "input.txt";
	        //read file
	        FruitMap fruitMap = readZooFile(fileName);
	        fruitMap.starNum = starNumber(fruitMap.getMap());
	        //output file
	        PrintStream out = new PrintStream(new FileOutputStream("output.txt", false));
	        System.setOut(out);
	        //if it cannot be initialized
	        Minmax_alphabeta alphabeta = new Minmax_alphabeta(fruitMap);
	        alphabeta.runMin_max(3);
	        printInput(fruitMap);
	        out.close();
		     
	  }catch (FileNotFoundException exc) {
	     System.out.println("File not found: input.txt");
	  }catch (IOException exc) {
	     exc.printStackTrace();
	  }
		   
	}
	public static int starNumber(char[][] map) {
		int star = 0;
		for(char[] row: map){
			for(char c: row){
				if(c == '*') star++;
			}
		}
		return star;
	}
	public static FruitMap readZooFile(String fileName) throws IOException {
		   BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		   String line;
		   //read the row ad column of maze
		   int side = Integer.parseInt(bufferedReader.readLine());
		   int kinds = Integer.parseInt(bufferedReader.readLine());
		   double remainingTime = Double.parseDouble(bufferedReader.readLine());
		   
		   char[][] map = new char[side][side];
		  //read maze
		   for(int i = 0; i < side; i++){
			   line = bufferedReader.readLine();
			   map[i] = Arrays.copyOf(line.toCharArray(), side);
		   }
		   bufferedReader.close();
		   return new FruitMap(kinds, side, remainingTime, map);
	}
	
	public static void printInput(FruitMap fruitMap){
		System.out.println(fruitMap.nextMove);
		for(char row[]: fruitMap.getMap()){
			for(char c: row)
				System.out.print(c);
			System.out.println();
		}
	}
	
	
	
}

