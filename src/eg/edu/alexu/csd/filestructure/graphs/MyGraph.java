package eg.edu.alexu.csd.filestructure.graphs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MyGraph implements IGraph {
	private int verticiesCount, edgesCount;

	private ArrayList<ArrayList<MyNode<Integer, Integer>>> adjList = new ArrayList<ArrayList<MyNode<Integer, Integer>>>();
	private ArrayList<MyEdge> edgeList = new ArrayList<MyEdge>();
	private PriorityQueue<MyNode<Integer, Integer>> pq = new PriorityQueue<MyNode<Integer, Integer>>(
			new MyNodeComparator());
	private int[] parent;
	private boolean[] visited;
	private ArrayList<Integer> processedNodes;
	final int INF = 1000000000;
	
	public MyGraph() {

	}

	public void readGraph(File file) {
		verticiesCount = 0;
		edgesCount = 0;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			//System.out.println("err 1");
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		String line = null;
		int index = 0;

		try {
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				String[] splited = line.split("\\s+");
				if (index == 0) {
					verticiesCount = Integer.parseInt(splited[0]);
					edgesCount = Integer.parseInt(splited[1]);
					for(int i=0 ; i<verticiesCount ; i++){
						adjList.add((new ArrayList<MyNode<Integer,Integer>>()));
					}
				} else {
					int from = Integer.parseInt(splited[0]);
					int to = Integer.parseInt(splited[1]);
					int weight = Integer.parseInt(splited[2]);
					if(from > verticiesCount-1 || to > verticiesCount-1 || from < 0 || to < 0)
						throw new RuntimeException();
					adjList.get(from).add(new MyNode<Integer, Integer>(to, weight));
					edgeList.add(new MyEdge(from,to,weight));
				}
				index++;
			}
		} catch (IOException e) {
		}
		if(edgeList.size() != edgesCount)
			throw new RuntimeException();
		try {
			br.close();
		} catch (IOException e) {
		}
	}

	public int size() {
		return edgesCount;
	}

	public ArrayList<Integer> getVertices() {
		ArrayList<Integer> verticies = new ArrayList<Integer>();
		for (int i = 0; i < verticiesCount; i++) {
			verticies.add(i);
		}
		return verticies;
	}

	public ArrayList<Integer> getNeighbors(int v) {
		if(v > verticiesCount-1 || v < 0)
			throw new RuntimeException();
		ArrayList<MyNode<Integer, Integer>> tempList = adjList.get(v);
		ArrayList<Integer> adjMyNodesList = new ArrayList<Integer>();
		for (MyNode<Integer, Integer> p : tempList) {
			adjMyNodesList.add(p.index);
		}
		return adjMyNodesList;
	}

	public void runDijkstra(int src, int[] distances) {
		
		if(src > verticiesCount-1 || src < 0 || distances.length != verticiesCount)
			throw new RuntimeException();
		
		parent = new int[distances.length];
		visited = new boolean[distances.length];
		processedNodes = new ArrayList<Integer>();
		
		
		for(int i=0 ; i<distances.length ; i++){
			distances[i] = Integer.MAX_VALUE/2;
		}
		
		distances[src] = 0;
		parent[src] = -1;
		pq.add(new MyNode<Integer, Integer>(src,0) );
		while (!pq.isEmpty()) {
			MyNode<Integer, Integer> temp = pq.remove();
			processedNodes.add((Integer) temp.index);
			int from = (int) temp.index;
			visited[from] = true;
			for (int j = 0; j < adjList.get(from).size(); j++) {
				int to = adjList.get(from).get(j).index;
				long weight = adjList.get(from).get(j).value;
				if (!visited[to]) {
					if (distances[to] > distances[from] + weight) {
						distances[to] = (int) (distances[from] + weight);
						pq.add(new MyNode<Integer, Integer>(to, distances[to]));
						parent[to] = from;
					}
				}
			}
		}
	}
	
	public ArrayList<Integer> getDijkstraProcessedOrder() {
		return processedNodes;
	}

	public boolean runBellmanFord(int src, int[] distances) {
		parent = new int[distances.length];
		visited = new boolean[distances.length];

		if(src > verticiesCount-1 || src < 0)
			throw new RuntimeException();

		for(int i=0;i<verticiesCount ; i++){
			distances[i] = Integer.MAX_VALUE/2;
		}
		
		distances[src] = 0;
		parent[src] = -1;
		
		for(int i=0 ; i<verticiesCount-1 ; i++){
			for(int j=0 ; j<edgesCount ; j++){
				MyEdge tempEdge = edgeList.get(j);
				int from = tempEdge.from;
				int to = tempEdge.to;
				int weight = tempEdge.weight;
				if(distances[from] != Integer.MAX_VALUE/2)
					if(distances[to] > distances[from] + weight)
						distances[to] = distances[from] + weight; 
			}
		}

		for(int j=0 ; j<edgesCount ; j++){
			MyEdge tempEdge = edgeList.get(j);
			int from = tempEdge.from;
			int to = tempEdge.to;
			int weight = tempEdge.weight;
			if(distances[from] != Integer.MAX_VALUE/2)
				if(distances[to] > distances[from] + weight)
					return false;
		}

		return true;		
	}

	private class MyNode<T1 extends Comparable<T1>, T2 extends Comparable<T2>> implements Comparable<MyNode<T1, T2>> {

		private T1 index;
		private T2 value;

		public MyNode(T1 index, T2 value) {
			this.index = index;
			this.value = value;
		}

		public int compareTo(MyNode<T1, T2> MyNode2) {
			return value.compareTo(MyNode2.value);
		}

	}

	private class MyEdge{
		int from;
		int to;
		int weight;
		public MyEdge(int from, int to, int weight){
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
	}
	
	private class MyNodeComparator implements Comparator<MyNode<Integer, Integer>> {
		public int compare(MyNode<Integer, Integer> n1, MyNode<Integer, Integer> n2) {
			return n1.value.compareTo(n2.value);
		}
	}

}
