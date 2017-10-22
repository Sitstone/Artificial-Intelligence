import java.util.ArrayList;
import java.util.List;

public class DFS {
	
	private Zoo zoo;
	public DFS(Zoo zoo){
		this.zoo = zoo;
	}
	
	public List<List<String>> dfsSearch() {
		char[][] map = zoo.getZoo();
		int numLizard = zoo.getNumLizards();
		
		List<List<String>> res = new ArrayList<>();
		helper(map, res, 0, 0, numLizard);
		return res;
	}
	
	private boolean helper(char[][] map, List<List<String>> res, int row, int col, int num) {
		if(res.size() == 1) return true;
		if(num == 0){
			res.add(construct(map));
			return true;
		}
		if(col == map.length) return false;
		boolean search = false;
		for(int i = row; i < map.length; i++){
			if(map[i][col] == '2') continue;
			if(validate(map, i, col)){
				map[i][col] = '1';	
				if(i < map.length - 1)
					search = helper(map, res, i + 1, col, num - 1);
				else
					search = helper(map, res, 0, col + 1, num - 1);
				map[i][col] = '0';
			}
		}	
		
		//search false use next col
		if(!search) 
			search = helper(map, res, 0, col + 1, num);
		return search;
	}
	//check validation
	private boolean validate(char[][] map, int x, int y){
		if(map[x][y] == '1') return false;
		//check row
		for(int i = y - 1; i >= 0; i--){
			if(map[x][i] == '2') break;
			else if(map[x][i] == '1') return false;
		}
		//check 135
		for(int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--){
			if(map[i][j] == '2') break;
			else if(map[i][j] == '1') return false;
		}
		//check 225
		for(int i = x + 1, j = y - 1; i < map.length && j >= 0; i++, j--){
			if(map[i][j] == '2') break;
			else if(map[i][j] == '1') return false;
		}
		//check col
		for(int i = x - 1; i >= 0; i--){
			if(map[i][y] == '2') break;
			else if(map[i][y] == '1') return false;
		}
		return true;			
	}
	private List<String> construct(char[][] map){
		List<String> ans = new ArrayList<>();
		for(int i = 0; i < map.length; i++) ans.add(new String(map[i]));
		return ans;
	}
}
