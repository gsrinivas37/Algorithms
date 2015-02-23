import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class GraphProblem {
	private Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();
	private Map<Integer, List<Integer>> revGraph = new HashMap<Integer, List<Integer>>();
	
	private Set<Integer> explored = new HashSet<Integer>();
	private List<Integer> finishTime = new ArrayList<Integer>();
	private List<Integer> sizeSCC = new ArrayList<Integer>();
	private int size = 0;
	
	public static void main(String[] args) {
		GraphProblem problem = new GraphProblem();
		problem.readInput();
		problem.runFirstDFS();
		problem.runSecondDFS();
		
		
		System.out.println("");
	}

	private void runSecondDFS() {
		explored.clear();
		for(int i=finishTime.size()-1; i>= 0; i--){
			System.out.println("Starting with node:"+finishTime.get(i));
			runDFS(graph, finishTime.get(i), false);
		}
		sizeSCC.sort(new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o2-o1;
			}
		});
	}

	private void readInput() {
		try {
			FileReader fileReader = new FileReader("/Users/gsrinivas37/Downloads/SCC.txt");
//			FileReader fileReader = new FileReader("/Users/gsrinivas37/Downloads/testcase.txt");
			BufferedReader reader = new BufferedReader(fileReader);
			String readLine = reader.readLine();
			
			while(readLine!=null){
				String[] split = readLine.trim().split(" ");
				if(split.length==2){
					int source = Integer.parseInt(split[0]);
					int target = Integer.parseInt(split[1]);
					addEdge(graph, source, target);
					addEdge(revGraph, target, source);
				}else{
					System.out.println("Error");
				}
				readLine = reader.readLine();
			}
			System.out.println("");
			reader.close();
			fileReader.close();
		} catch (Exception e) {
		}
	}
	
	private void runFirstDFS(){
		explored.clear();
		for(int i=1; i<=revGraph.size(); i++){
			runDFS(revGraph, i, true);
		}
	}
	
	private void runDFS(Map<Integer, List<Integer>> graph, int node, boolean flag){
		if(explored.contains(node))
			return;
		
		System.out.print(node+",");
		
		explored.add(node);
		if(graph.get(node)!=null){
			for(int head : graph.get(node)){
				if(!explored.contains(head))
					runDFS(graph, head, flag);
			}
		}
		
		if(flag)
			finishTime.add(node);
		else{
			if(sizeSCC.size()==0){	
				sizeSCC.add(explored.size());
			}else{
				if(explored.size()-size>60)
					System.out.println("");
				
				sizeSCC.add(explored.size()-size);
			}
			size = explored.size();
		}
	}
	
	private void addEdge(Map<Integer, List<Integer>> graph, int source, int target){
		if(graph.containsKey(source)){
			graph.get(source).add(target);
		}else{
			List<Integer> list = new ArrayList<Integer>();
			list.add(target);
			graph.put(source, list);
		}
	}
}
