import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Dijkstra {
	Graph graph = new Graph();
	Map<Integer, Long> sp = new HashMap<Integer, Long>();
	Map<Integer, Long> temp = new HashMap<Integer, Long>();
	
	public static void main(String[] args) {
		Dijkstra problem = new Dijkstra();
		problem.readGraph();
		problem.runAlgo();
		System.out.println("");
	}
	
	private void runAlgo() {
		Integer current = 1;
		sp.put(1, (long) 0);
		for(Edge outEdge : graph.getOutEdges(1)){
			temp.put(outEdge.getDestination(), outEdge.getDistance());
		}
		
		while(temp.size()!=0){
			Integer node = getMin();
			if(node==-1)
				break;
			
			sp.put(node,temp.get(node));
			temp.remove(node);
			
			current = node;
			
			for(Edge outEdge : graph.getOutEdges(node)){
				if(sp.containsKey(outEdge.getDestination()))
					continue;
				
				Long val = sp.get(current)+outEdge.getDistance();
				
				if(temp.containsKey(outEdge.getDestination())){
					if(temp.get(outEdge.getDestination())<val){
						continue;
					}
				}
				temp.put(outEdge.getDestination(), val);
			}
		}
		
		System.out.println("");
	}
	
	private Integer getMin(){
		long min = 1000000;
		int node =-1;
		for(Integer val : temp.keySet()){
			if(temp.get(val)<min){
				min = temp.get(val);
				node = val;
			}
		}
		
		return node;
	}

	private void readGraph(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader("/Users/gsrinivas37/Downloads/dijkstraData.txt"));
			String line = reader.readLine();
			
			while(line!=null){
				String[] split = line.split("\t");
				if(split.length<2){
					continue;
				}
				
				int node = Integer.parseInt(split[0]);
				
				for(int i=1; i<split.length; i++){
					String[] edgeArray = split[i].trim().split(",");
					if(edgeArray.length==2){
						Edge e = new Edge(node, Integer.parseInt(edgeArray[0]), Long.parseLong(edgeArray[1]));
						graph.addEdge(e);
					}else{
						System.out.println("Something is wrong");
					}
				}
				line = reader.readLine();
			}
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

class Graph {
	List<Integer> vertices = new ArrayList<Integer>();
	Map<Integer, List<Edge>> outEdges = new HashMap<Integer, List<Edge>>();
	Map<Integer, List<Edge>> inEdges = new HashMap<Integer, List<Edge>>();
	
	Graph(){
		for(int i=0; i<=200; i++){
			vertices.add(i);
		}
	}
	
	public List<Edge> getOutEdges(Integer node){
		return outEdges.get(node);
	}
	
	public List<Edge> getInEdges(Integer node){
		return inEdges.get(node);
	}
	
	public void addEdge(Edge e){
		addOutEdge(e);
		addInEdge(e);
	}
	
	private void addOutEdge(Edge e){
		List<Edge> list = outEdges.get(e.getSource());
		if(list==null){
			list = new ArrayList<Edge>();
		}
		for(Edge edge: list){
			if(edge.getDestination()==e.getDestination()){
				return;
			}
		}
		
		list.add(e);
		outEdges.put(e.getSource(), list);
	}
	
	private void addInEdge(Edge e){
		List<Edge> list = inEdges.get(e.getDestination());
		if(list==null){
			list = new ArrayList<Edge>();
		}
		
		for(Edge edge: list){
			if(edge.getSource()==e.getSource()){
				return;
			}
		}
		
		list.add(e);
		inEdges.put(e.getDestination(), list);
	}
}

class Edge {
	private Integer source;
	private Integer destination;
	private long distance;
	
	Edge(Integer s, Integer d, long l){
		this.source = s;
		this.destination = d;
		this.distance = l;
	}
	
	public Integer getSource(){
		return source;
	}
	
	public Integer getDestination(){
		return destination;
	}
	
	public long getDistance(){
		return distance;
	}
}