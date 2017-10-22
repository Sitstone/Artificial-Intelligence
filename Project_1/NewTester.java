import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NewTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        ZooGenerator zooGenerator = new ZooGenerator();
        char[][] map = zooGenerator.generate(1000, 5000);
        Zoo zoo = new Zoo(map, 5000, 1000, "DFS");
        		
        		
        System.out.println("DFS Soluion: ");
        DFS dfssolution = new DFS(zoo);
        Queue<Point> lizards= printResult(dfssolution.dfsSearch());
        
//        BFS
        System.out.println("BFS Solution: ");
        BFS bfssolution = new BFS(zoo);
        //printBFS(bfssolution.bfsSearch());
   
        
        //SA
        System.out.println("SA Solution");
        SA sa = new SA(20, zoo);
       // Queue<Point> queueforSA = sa.simulatedAnnealing();
        //printSA(queueforSA, zoo.getZoo());
        
        //Valid checking 
        //DFS
        ResultCheck resultCheck = new ResultCheck();
        
        System.out.println(resultCheck.isValid(zoo.getZoo(), lizards));
        //System.out.println(resultCheck.isValid(zoo.getZoo(), queueforSA));
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
			System.out.println(lizards.size());
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
