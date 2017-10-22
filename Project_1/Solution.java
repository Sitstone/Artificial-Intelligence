import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.ietf.jgss.Oid;
import org.omg.PortableServer.POA;

import java.io.*;

public class Solution {
	public static final char TREE = '2';
	public static final char LIZARD = '1';
	public static final char EMPTY = '0';
	
	public static void main(String[] args) throws IOException{
		
		        Zoo zoo = readZooFile("zoo3");
		        printZoo(zoo);
		        
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
	        	
		        		
		        		
		        System.out.println("DFS Soluion: ");
		        DFS dfssolution = new DFS(zoo);
		        Queue<Point> lizards= printResult(dfssolution.dfsSearch());
		        
//		        BFS
		        System.out.println("BFS Solution: ");
		        BFS bfssolution = new BFS(zoo);
		        printBFS(bfssolution.bfsSearch());
		   
		        
		        //SA
		        System.out.println("SA Solution");
		        SA sa = new SA(20, zoo);
		        Queue<Point> queueforSA = sa.simulatedAnnealing();
		        printSA(queueforSA, zoo.getZoo());
		        
		        //Valid checking 
		        //DFS
		        ResultCheck resultCheck = new ResultCheck();
		        
		        System.out.println(resultCheck.isValid(zoo.getZoo(), queueforSA));
		        System.out.println(resultCheck.isValid(zoo.getZoo(), lizards));
		        
		        
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
	
	//print the original zoo 
	private static void printZoo(Zoo zoo) {
		System.out.println("Method: " + zoo.getMethod());
		System.out.println("Sides: " + zoo.getSide());
		System.out.println("# of Lizards: " + zoo.getNumLizards());
		System.out.println("Maze: ");
		for(char[] row: zoo.getZoo()){
			for(char spot: row){
				System.out.print(spot + " ");
			}
			System.out.println();
		}
	}
	//print dfs result
	private static Queue<Point> printResult(List<List<String>> res) {
		if(res.size() == 0) {
			System.out.println("Fail");
			return null;
		}
		//store lizard points in a queue
		Queue<Point> lizards = new LinkedList<>();
		int i = 0, j = 0;
		
		
		System.out.println("OK");
		for(List<String> solutions: res){
			for(String s: solutions){
				for(char c: s.toCharArray()){
					System.out.print(c + " ");
					if(c == '1')
						lizards.offer(new Point(i, j));
					j++;
				}
				
				System.out.println();
				i++;j = 0;
			}
			System.out.println();
		}
		return lizards;
	}
	//print bfs result
	private static void printBFS(char[][] map) {
		if(map == null) System.out.println("Fail");
		else{
			System.out.println("OK");
			for(char[] row: map){
				for(char c: row) System.out.print(c + " ");
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
				for(char c: row) System.out.print(c + " ");
				System.out.println();
			}
		}
	}
}

