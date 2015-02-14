import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GraphMinCutProblem {
	List<Integer> graph[] = new ArrayList[200];
	List<Integer> nodes = new ArrayList<Integer>();
	long edges = 0;
	long minCount = -1;
	List<Integer> finalNodes = new ArrayList<Integer>();

	List<Integer> origGraph[] = new ArrayList[200];
	List<Integer> origNodes = new ArrayList<Integer>();
	long origEdges = 0;

	public static void main(String[] args) throws IOException {
		GraphMinCutProblem cutProblem = new GraphMinCutProblem();
		cutProblem.readGraph();
		for(int exp=0; exp<40000;exp++){
			cutProblem.reload();
			cutProblem.runGraphContraction();
		}
		cutProblem.print();
	}

	private void reload() {
		nodes.clear();
		edges = origEdges;
		nodes.addAll(origNodes);
		for(int i=0; i<200; i++){
			graph[i].clear();
			graph[i].addAll(origGraph[i]);
		}
	}

	private void print(){
		System.out.println("Final Min Count is: "+minCount);
		System.out.println("Final Nodes are : "+finalNodes);
	}

	private void runGraphContraction(){
		for(int i=0; i< 198; i++){
			int random = (int) (Math.random()*edges) + 1;
			Edge edge = getEdge(random);
			merge(edge);
		}

		System.out.println("Nodes are :"+nodes);
		System.out.println("Min Count is :"+edges);
		if(minCount==-1){
			minCount = edges;
		}else{
			if(edges<minCount){
				minCount = edges;
				finalNodes.clear();
				finalNodes.addAll(nodes);
			}
		}
	}

	private void merge(Edge edge) {
		boolean remove = nodes.remove(new Integer(edge.second));

		if(!remove)
			System.out.println("WrongS");

		for(int val : graph[edge.second-1]){
			if(val==edge.first){
				edges--;
			}else{
				insertNode(graph[edge.first-1], val);
			}

			boolean b = graph[val-1].remove(new Integer(edge.second));
			if(!b)
				System.out.println("Wrong");

			if(val==edge.first){
				edges--;
			}else{
				insertNode(graph[val-1],edge.first);
			}
		}
	}

	private Edge getEdge(int random) {
		Edge edge = new Edge();
		int index = 0;
		edge.first = nodes.get(index);

		while(true){
			if(graph[edge.first-1].size()>=random){
				edge.second = graph[edge.first-1].get(random-1);
				return edge;
			}else{
				random-=graph[edge.first-1].size();
				edge.first = nodes.get(++index);
			}
		}
	}

	private void readGraph() throws IOException{
		edges = 0;
		nodes.clear();
		BufferedReader reader = new BufferedReader(new FileReader("/Users/gsrinivas37/Downloads/kargerMinCut.txt"));
		while(true){
			String line = reader.readLine();
			if(line==null) break;

			String[] split = line.split("\\t");
			int node = Integer.parseInt(split[0]);
			nodes.add(node);
			graph[node-1] = new ArrayList<Integer>();

			for(int i=1;i<split.length;i++){
				insertNode(graph[node-1], Integer.parseInt(split[i]));
				edges++;
			}
		}

		origNodes.addAll(nodes);
		for(int i=0; i<200; i++){
			origGraph[i]=new ArrayList<Integer>();
			origGraph[i].addAll(graph[i]);
		}
		origEdges = edges;
	}

	private void insertNode(List<Integer> list, int val){
		int index = 0;
		for(;index<list.size();index++){
			if(list.get(index)>val) break;
		}
		list.add(index, val);
	}
}

class Edge{
	int first;
	int second;
}
