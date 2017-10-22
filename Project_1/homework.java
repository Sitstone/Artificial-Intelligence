import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.io.*;

public class homework {
	
	public static void main(String[] args){
		try {
			String fileName = "input.txt";
	        //read file
	        Zoo zoo = readZooFile(fileName);
	        String method = zoo.getMethod();
	        //output file
	        PrintStream out = new PrintStream(new FileOutputStream("output.txt", false));
	        System.setOut(out);
	        //if it cannot be initialized
	        long numTree = 0;
	        for(char[] row: zoo.getZoo()){
	        	for(char c: row){
	        		if(c == '2') numTree++;
	        	}
	        }
	        long blankCells = zoo.getSide() * zoo.getSide();
	        if(zoo.getNumLizards() > blankCells - numTree){
	        	System.out.println("FAIL");
	        	return;
	        }
	        
	        if(method.equals("DFS")){
		        DFS dfssolution = new DFS(zoo);
		        printDFS(dfssolution.dfsSearch());
	        }else if(method.equals("BFS")){
		        BFS bfssolution = new BFS(zoo);
		        printBFS(bfssolution.bfsSearch());
	        }else {
	        	int InitialTem = 15;
	        	Queue<Point> ans = null;
	        	for(int i = 1; i <= 5; i++){
			        SA sa = new SA(InitialTem + i, zoo);
			         ans = sa.simulatedAnnealing();
			        if(ans != null) break;	
	        	}
	        	printSA(ans, zoo.getZoo());
			} 
	        out.close();
		     
	  }catch (FileNotFoundException exc) {
	     System.out.println("File not found: input.txt");
	  }catch (IOException exc) {
	     exc.printStackTrace();
	  }
		   
	}

	private static Zoo readZooFile(String fileName) throws IOException {
		   BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		   String line;
		   //read the row ad column of maze
		   String method = bufferedReader.readLine();
		   int side = Integer.parseInt(bufferedReader.readLine());
		   int numLizards = Integer.parseInt(bufferedReader.readLine());
		   
		   char[][] zoo = new char[side][side];
		  //read maze
		   for(int i = 0; i < side; i++){
			   line = bufferedReader.readLine();
			   zoo[i] = Arrays.copyOf(line.toCharArray(), side);
		   }
		   bufferedReader.close();
		   return new Zoo(zoo, numLizards, side, method);
	}
	
	//print dfs result
	private static void printDFS(List<List<String>> res) {
		if(res.size() == 0) {
			System.out.println("Fail");
			return;
		}
		System.out.println("OK");
		for(List<String> solutions: res){
			for(String s: solutions){
				for(char c: s.toCharArray())
					System.out.print(c);
				System.out.println();
			}
			System.out.println();
		}
	}
	//print bfs result
	private static void printBFS(char[][] map) {
		if(map == null) System.out.println("Fail");
		else{
			System.out.println("OK");
			for(char[] row: map){
				for(char c: row) System.out.print(c);
				System.out.println();
			}
		}
	}
	
	private static void printSA(Queue<Point> pointsQ, char[][] map){
		if(pointsQ == null) System.out.println("Fail");
		else {
			System.out.println("OK");
			while(!pointsQ.isEmpty()){
				Point point = pointsQ.poll();
				map[point.row][point.col] = '1';
			}
			for(char[] row: map){
				for(char c: row) System.out.print(c);
				System.out.println();
			}
		}
	}
}

