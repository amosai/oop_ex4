//package algorithms;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.ListIterator;
//import java.util.Map;
//
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//
//import oop_dataStructure.OOP_DGraph;
//import oop_dataStructure.oop_edge_data;
//import oop_dataStructure.oop_graph;
//import oop_dataStructure.oop_node_data;
//import oop_utils.OOP_Point3D;
//import utils.Point3D;
///**
// * This empty class represents the set of graph-theory algorithms
// * which should be implemented as part of Ex2 - Do edit this class.
// * @author 
// *
// */
//public class Graph_Algo {
//	public static final int TAG_VISITED = 1;
//	public static final int TAG_NOTVISITED = 0;
//	public oop_graph g;/////maybe don't need that ...
//
//	public Graph_Algo(oop_graph g) {
//		init (g);
//
//	}
//
//	
//	public void init(oop_graph g) {
//		this.g=g;
//		//		 init(String file_name);
//		//		 save(String file_name);
//		//		 isConnected();
//		//		  double shortestPathDist(int src, int dest);
//		//		  List<Node> shortestPath(int src, int dest);
//
//	}
//
//
//	/*public void init(String file_name) {
//
//		JSONParser parser = new JSONParser();
//		OOP_DGraph g = new OOP_DGraph();
//		try {
//
//			Object obj = parser.parse(new FileReader(file_name ));
//
//			JSONObject jsonObject = (JSONObject) obj;
//
//			//	            String nodes = (String) jsonObject.get("Name");
//			//	            String author = (String) jsonObject.get("Author");
//			JSONArray nodes = (JSONArray) jsonObject.get("nodes");
//			JSONArray edges = (JSONArray) jsonObject.get("edges");
//
//			Iterator<JSONObject> iterator1 = nodes.iterator();
//			while (iterator1.hasNext()) {
//				JSONObject jnodes=iterator1.next();
//				int key = Integer.parseInt( (String) jnodes.get("key") ) ;	           	            		            
//				String locationstr = (String) jnodes.get("location");
//				Point3D location = null;
//
//
//				if(!locationstr.equals("null"))
//					location = new Point3D (locationstr);
//				double weight = Double.parseDouble( (String) jnodes.get("weight") ) ; 
//				String info = (String ) jnodes.get("info");
//				int tag = Integer.parseInt( (String ) jnodes.get("tag") );
//				// System.out.println(""+key+location+weight+info+tag);
//
//				//make node
//				oop_node_data node = new oop_node_data(key);
//				node.setInfo(info);
//				node.setLocation(location);
//				node.setTag(tag);
//				node.setWeight(weight);
//
//				g.addNode(node);
//			}
//			Iterator<JSONObject> iterator2 = edges.iterator();	          
//			while (iterator2.hasNext()) {
//				JSONObject jnodes=iterator2.next();
//				int src = Integer.parseInt( (String) jnodes.get("src") );
//				int dest = Integer.parseInt( (String) jnodes.get("dest") );	            		            	
//				double weight = Double.parseDouble( (String) jnodes.get("weight") ); 
//				String info = (String ) jnodes.get("info");
//				int tag = Integer.parseInt( (String) jnodes.get("tag") );
//				//System.out.println(src+dest+weight+info+tag);
//
//				//make edge
//				// Edge edge = new Edge(src, dest, weight);
//				// edge.setInfo(info);
//				// edge.setTag(tag);
//
//				g.connect(src, dest, weight);
//				g.getEdge(src, dest).setTag(tag);
//				g.getEdge(src, dest).setInfo(info);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		init(g);
//
//	}*/
//
//
//	 
//	public void save(String file_name) {
//		// creating JSONObject 
//		JSONObject jo = new JSONObject(); 
//
//		// nodes JSONArray  
//		JSONArray jnodes = new  JSONArray(); 
//
//		for (oop_node_data v :this.g.getV()) {
//			Map<String,String> node = new LinkedHashMap<String,String>(5); 
//			node.put("key",""+v.getKey() );
//			node.put("info",""+v.getInfo());
//			node.put("location",""+v.getLocation());
//			node.put("tag",""+v.getTag());
//			node.put("weight",""+v.getWeight());
//			jnodes.add(node);
//		}
//
//		jo.put("nodes", jnodes);
//
//		// edges JSONArray  
//		JSONArray jedges = new JSONArray(); 
//
//		for (oop_node_data v :this.g.getV()) {
//			for(oop_edge_data e:g.getE(v.getKey())) {
//				Map<String,String> edge = new LinkedHashMap<String,String>(5); 
//
//				edge.put("src",""+ e.getSrc() );
//				edge.put("dest",""+ e.getDest());
//				edge.put("info",""+ e.getInfo());
//				edge.put("tag",""+ e.getTag());
//				edge.put("weight",""+ e.getWeight());
//
//				jedges.add(edge);
//			}
//		}
//
//		jo.put("edges", jedges);
//
//		// writing JSON to file:"JSONExample.json" in cwd 
//		PrintWriter pw;
//		try {
//			pw = new PrintWriter(file_name);
//			pw.write(jo.toJSONString()); 
//
//			pw.flush();
//			pw.close(); 
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//	}
//
//
//	 
//	public boolean isConnected() {// Check if graph is strongly connected or not
//		//n.setTag(); // TAG_NOTVISITED = 0 /// NOT FORGET
//
//
//		for (oop_node_data v :this.g.getV())
//			v.setTag(TAG_NOTVISITED);
//		// do for every vertex
//
//		for (oop_node_data v :this.g.getV())//
//		{
//
//
//			// start DFS from first vertex
//			boolean have = DFS(v.getKey());
//			if(!have)//Because we don't have dest to one of the nodes///
//				return false;
//
//
//			// If DFS traversal doesn’t visit all vertices,
//			// then graph is not strongly connected
//			for (oop_node_data checkV :this.g.getV())
//			{
//				if	(checkV.getTag()==(TAG_NOTVISITED))
//					return false; 
//
//			}
//
//		}
//		return true;
//
//	}
//
//	//thanks to /www.techiedelight.com/
//	// Function to perform DFS Traversal
//	//private  void DFS(graph graph, int v, boolean[] visited)
//	private  boolean DFS( int node_id)
//	{
//		oop_node_data  n= this.g.getNode(node_id);
//		// mark current node as visited
//		n.setTag(TAG_VISITED);
//
//
//		// do for every edge (v -> u)
//		Collection<oop_edge_data>  collection = this.g.getE(node_id);
//		if(collection == null)
//			return false;//Because we don't have dest to one of the nodes///
//		for (oop_edge_data e : collection)   // graph.adjList.get(v))
//		{
//			oop_node_data  dest= this.g.getNode(e.getDest());
//
//			// dest is not visited
//
//			if (dest.getTag()==TAG_NOTVISITED)
//				DFS(dest.getKey());
//		}
//
//		return true;
//	}
//
//	//		// Check if graph is strongly connected or not
//	//		public static boolean check(graph graph, int N)
//	//		{
//	//			//n.setTag(); // TAG_NOTVISITED = 0 /// NOT FORGET
//	//			// do for every vertex
//	//			for (int i = 0; i < N; i++)
//	//			{
//	//				// stores vertex is visited or not
//	//				boolean[] visited = new boolean[N];
//	//
//	//				// start DFS from first vertex
//	//				DFS(graph, i, visited);
//	//
//	//				// If DFS traversal doesn’t visit all vertices,
//	//				// then graph is not strongly connected
//	//				for (boolean b: visited)
//	//					if (!b)
//	//					return false;
//	//			}
//	//			return true;
//	//		}
//
//	 
//	public double shortestPathDist(int src, int dest) {
//
//		oop_node_data source = g.getNode(src);
//		dijkstra(source);
//		oop_node_data destination = g.getNode(dest);
//		return destination.getWeight();
//	}
//
//	 
//	public List<oop_node_data> shortestPath(int src, int dest) {
//		oop_node_data source = g.getNode(src);
//		dijkstra(source);
//		oop_node_data destination = g.getNode(dest);
//
//		ArrayList<oop_node_data> pathe = new ArrayList<oop_node_data>();
//		while(destination.getKey() != source.getKey()) {
//			pathe.add(destination);
//			destination = g.getNode( destination.getTag() );
//		}
//		pathe.add(source);
//
//
//		return pathe;
//	}
//	private void dijkstra(oop_node_data src) { // help function for shortestPath method
//		ArrayList<oop_node_data> q=new ArrayList<>();
//		for (oop_node_data v :this.g.getV()) {
//			v.setWeight(Double.MAX_VALUE);
//			v.setTag(-1);       // tag=-1 mean not define
//			q.add(v);
//		}
//		src.setWeight(0);
//
//		while (!q.isEmpty()) {
//			oop_node_data u=find_min ( q);
//			q.remove(u);
//
//
//			for (oop_node_data v :neighbors(u)) {
//
//				double alt=u.getWeight()+g.getEdge(u.getKey(), v.getKey()).getWeight();
//				if(alt < v.getWeight()) {
//					v.setWeight(alt);
//					v.setTag(u.getKey());
//				}
//			}
//
//
//		}
//	}
//	 
//	public List<oop_node_data> TSP(List<Integer> targets) {
//
//
//		if (this.isConnected()==false) {
//			
//			return null;
//		}
//		ArrayList<oop_node_data> path = new ArrayList<oop_node_data>();
//
//		//int size=targets.size();
//
//		for (int i = 1;  targets.size()!=1; i++) {// over nodes there id of them exist in targets
//
//			
//			
//			System.out.println("m");
//			int our_location_index=this.g.getNode(((ListIterator<Integer>) targets).next()).getKey();
//			//int next_index=-1;
//			System.out.println(our_location_index);
//			//this.g.getNode(targets.listIterator().previous()).getKey();
//			int	 next_index =this.g.getNode(targets.listIterator().next()).getKey();
////			while (targets.listIterator().hasNext()) {//ask aviv if it o.k or maybe do a for loop// check what the closest node
//			System.out.println(next_index);
////				
////				double min_dist= Double.MAX_VALUE;
////				OOP_Point3D other_location=this.g.getNode(targets.listIterator().next()).getLocation();
//				path.addAll(shortestPath(our_location_index,next_index ));
//				targets.listIterator(next_index);
//
//				targets.remove(our_location_index);
//				
//				//if (our_location.distance3D(other_location)<min_dist) {
//
////					min_dist=our_location.distance3D(other_location);
////					this.g.getNode(targets.listIterator().previous());
////					next_index=this.g.getNode(targets.listIterator().next()).getKey();
////					this.g.getNode(targets.listIterator().previous());
//
//		//		}
//
//			}
//
//			
//
//		
//
//
//
//		return path;
//	}
//
//	 
//	/*public oop_graph copy() {
//
//		save("temp.txt");
//		Graph_Algo tamp = new Graph_Algo(null);
//		tamp.init("temp.txt");
//		oop_graph newG = tamp.g;
//
//		return newG;
//	}*/
//
//	private oop_node_data find_min (ArrayList<oop_node_data> q) {
//
//		oop_node_data u =q.get(0);
//		double min =u.getWeight();
//		for (oop_node_data x :q) {// 
//			double temp=x.getWeight();
//			if (temp<min) {
//				min=temp;
//				u=x;
//			}
//		}
//		return u;
//	}
//
//	private Collection<oop_node_data> neighbors  (oop_node_data q) {// help function for Dijkstra
//
//		ArrayList<oop_node_data> ans= new ArrayList<>();
//
//		Collection<oop_edge_data>  collection = this.g.getE(q.getKey());
//		if(collection == null)
//			return ans;//Because we dont have more  neighbors ///
//		for (oop_edge_data e : collection)   //  for each pointer edges neighbors
//		{
//			oop_node_data  neighbor= this.g.getNode(e.getDest());
//			ans.add(neighbor);
//
//		}
//		return ans;
//	}
//	public double get_left () {
//		double left = Double.POSITIVE_INFINITY;
//		for ( oop_node_data    v : g.getV()) {
//			if(v.getLocation()==null)
//				continue;
//			if(v.getLocation().x()<left) {
//				left=v.getLocation().x();
//
//			}
//		}
//		return left;
//	}
//	public double get_right () {
//		double right = Double.NEGATIVE_INFINITY;
//		for ( oop_node_data    v : g.getV()) {
//			if(v.getLocation()==null)
//				continue;
//			if(v.getLocation().x()>right) {
//				right=v.getLocation().x();
//
//			}
//		}
//		return right;
//	}
//	public double get_top () {
//		double top = Double.NEGATIVE_INFINITY;
//		for ( oop_node_data    v : g.getV()) {
//			if(v.getLocation()==null)
//				continue;
//			if(v.getLocation().y()>top) {
//				top=v.getLocation().y();
//
//			}
//		}
//		return top;
//	}
//	public double get_bottom () {
//		double bottom = Double.POSITIVE_INFINITY;
//		for ( oop_node_data    v : g.getV()) {
//			if(v.getLocation()==null)
//				continue;
//			if(v.getLocation().y()<bottom) {
//				bottom=v.getLocation().y();
//
//			}
//		}
//		return bottom;
//	}
//}
//
package algorithms;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import oop_dataStructure.oop_node_data;
import oop_elements.OOP_NodeData;
import oop_utils.OOP_Point3D;
import utils.Point3D;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms{
	public static final int TAG_VISITED = 1;
	public static final int TAG_NOTVISITED = 0;
	public oop_graph g;/////maybe don't need that ...

	public Graph_Algo(oop_graph g) {
		init (g);

	}

	public void init(oop_graph g) {
		this.g=g;
		//		 init(String file_name);
		//		 save(String file_name);
		//		 isConnected();
		//		  double shortestPathDist(int src, int dest);
		//		  List<Node> shortestPath(int src, int dest);
	}


	public void init(String file_name) {

		JSONParser parser = new JSONParser();
		OOP_DGraph g = new OOP_DGraph();
		try {

			Object obj = parser.parse(new FileReader(file_name ));

			JSONObject jsonObject = (JSONObject) obj;

			//	            String nodes = (String) jsonObject.get("Name");
			//	            String author = (String) jsonObject.get("Author");
			JSONArray nodes = (JSONArray) jsonObject.get("nodes");
			JSONArray edges = (JSONArray) jsonObject.get("edges");

			Iterator<JSONObject> iterator1 = nodes.iterator();
			while (iterator1.hasNext()) {
				JSONObject jnodes=iterator1.next();
				int key = Integer.parseInt( (String) jnodes.get("key") ) ;	           	            		            
				String locationstr = (String) jnodes.get("location");
				OOP_Point3D location = null;


				if(!locationstr.equals("null"))
					location = new OOP_Point3D (locationstr);
				double weight = Double.parseDouble( (String) jnodes.get("weight") ) ; 
				String info = (String ) jnodes.get("info");
				int tag = Integer.parseInt( (String ) jnodes.get("tag") );
				// System.out.println(""+key+location+weight+info+tag);

				//make node
				OOP_NodeData node = new OOP_NodeData(key,location);
				node.setInfo(info);
				//node.setLocation(location);
				node.setTag(tag);
				node.setWeight(weight);

				g.addNode(node);
			}
			Iterator<JSONObject> iterator2 = edges.iterator();	          
			while (iterator2.hasNext()) {
				JSONObject jnodes=iterator2.next();
				int src = Integer.parseInt( (String) jnodes.get("src") );
				int dest = Integer.parseInt( (String) jnodes.get("dest") );	            		            	
				double weight = Double.parseDouble( (String) jnodes.get("weight") ); 
				String info = (String ) jnodes.get("info");
				int tag = Integer.parseInt( (String) jnodes.get("tag") );
				//System.out.println(src+dest+weight+info+tag);

				//make edge
				// Edge edge = new Edge(src, dest, weight);
				// edge.setInfo(info);
				// edge.setTag(tag);

				g.connect(src, dest, weight);
				g.getEdge(src, dest).setTag(tag);
				g.getEdge(src, dest).setInfo(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		init(g);

	}



	public void save(String file_name) {
		// creating JSONObject 
		JSONObject jo = new JSONObject(); 

		// nodes JSONArray  
		JSONArray jnodes = new  JSONArray(); 

		for (oop_node_data v :this.g.getV()) {
			Map<String,String> node = new LinkedHashMap<String,String>(5); 
			node.put("key",""+v.getKey() );
			node.put("info",""+v.getInfo());
			node.put("location",""+v.getLocation());
			node.put("tag",""+v.getTag());
			node.put("weight",""+v.getWeight());
			jnodes.add(node);
		}

		jo.put("nodes", jnodes);

		// edges JSONArray  
		JSONArray jedges = new JSONArray(); 

		for (oop_node_data v :this.g.getV()) {
			for(oop_edge_data e:g.getE(v.getKey())) {
				Map<String,String> edge = new LinkedHashMap<String,String>(5); 

				edge.put("src",""+ e.getSrc() );
				edge.put("dest",""+ e.getDest());
				edge.put("info",""+ e.getInfo());
				edge.put("tag",""+ e.getTag());
				edge.put("weight",""+ e.getWeight());

				jedges.add(edge);
			}
		}

		jo.put("edges", jedges);

		// writing JSON to file:"JSONExample.json" in cwd 
		PrintWriter pw;
		try {
			pw = new PrintWriter(file_name);
			pw.write(jo.toJSONString()); 

			pw.flush();
			pw.close(); 

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}



	public boolean isConnected() {// Check if graph is strongly connected or not
		//n.setTag(); // TAG_NOTVISITED = 0 /// NOT FORGET


		for (oop_node_data v :this.g.getV())
			v.setTag(TAG_NOTVISITED);
		// do for every vertex

		for (oop_node_data v :this.g.getV())//
		{


			// start DFS from first vertex
			boolean have = DFS(v.getKey());
			if(!have)//Because we don't have dest to one of the nodes///
				return false;


			// If DFS traversal doesn’t visit all vertices,
			// then graph is not strongly connected
			for (oop_node_data checkV :this.g.getV())
			{
				if	(checkV.getTag()==(TAG_NOTVISITED))
					return false; 

			}

		}
		return true;

	}

	//thanks to /www.techiedelight.com/
	// Function to perform DFS Traversal
	//private  void DFS(graph graph, int v, boolean[] visited)
	private  boolean DFS( int node_id)
	{
		oop_node_data  n= this.g.getNode(node_id);
		// mark current node as visited
		n.setTag(TAG_VISITED);


		// do for every edge (v -> u)
		Collection<oop_edge_data>  collection = this.g.getE(node_id);
		if(collection == null)
			return false;//Because we don't have dest to one of the nodes///
		for (oop_edge_data e : collection)   // graph.adjList.get(v))
		{
			oop_node_data  dest= this.g.getNode(e.getDest());

			// dest is not visited

			if (dest.getTag()==TAG_NOTVISITED)
				DFS(dest.getKey());
		}

		return true;
	}

	//		// Check if graph is strongly connected or not
	//		public static boolean check(graph graph, int N)
	//		{
	//			//n.setTag(); // TAG_NOTVISITED = 0 /// NOT FORGET
	//			// do for every vertex
	//			for (int i = 0; i < N; i++)
	//			{
	//				// stores vertex is visited or not
	//				boolean[] visited = new boolean[N];
	//
	//				// start DFS from first vertex
	//				DFS(graph, i, visited);
	//
	//				// If DFS traversal doesn’t visit all vertices,
	//				// then graph is not strongly connected
	//				for (boolean b: visited)
	//					if (!b)
	//					return false;
	//			}
	//			return true;
	//		}


	public double shortestPathDist(int src, int dest) {

		oop_node_data source = g.getNode(src);
		dijkstra(source);
		oop_node_data destination = g.getNode(dest);
		return destination.getWeight();
	}


	public ArrayList<oop_node_data> shortestPath(int src, int dest) {
		oop_node_data source = g.getNode(src);
		dijkstra(source);
		oop_node_data destination = g.getNode(dest);

		ArrayList<oop_node_data> pathe = new ArrayList<oop_node_data>();
		while(destination.getKey() != source.getKey()) {
			pathe.add(destination);
			destination = g.getNode( destination.getTag() );
		}
		pathe.add(source);
		Collections.reverse(pathe);
		
		//System.out.print(Thread.currentThread().getName()+" shortestPath algorithm result: ");
		for (oop_node_data oo : pathe) 
			System.out.print(oo.getKey()+" ");
		System.out.println();

		return pathe;
	}
	private void dijkstra(oop_node_data src) { // help function for shortestPath method
		ArrayList<oop_node_data> q=new ArrayList<>();
		for (oop_node_data v :this.g.getV()) {
			v.setWeight(Double.MAX_VALUE);
			v.setTag(-1);       // tag=-1 mean not define
			q.add(v);
		}
		src.setWeight(0);

		while (!q.isEmpty()) {
			oop_node_data u=find_min ( q);
			q.remove(u);


			for (oop_node_data v :neighbors(u)) {

				double alt=u.getWeight()+g.getEdge(u.getKey(), v.getKey()).getWeight();
				if(alt < v.getWeight()) {
					v.setWeight(alt);
					v.setTag(u.getKey());
				}
			}


		}
	}

	public List<oop_node_data> TSP(List<Integer> targets) {/// the list we return it from


		if (this.isConnected()==false) {

			return null;
		}
		ArrayList<oop_node_data> path = new ArrayList<oop_node_data>();

		//int size=targets.size();

		for (int i = 1;  targets.size()!=1; i++) {// over nodes there id of them exist in targets




			int our_location_index=this.g.getNode((int )targets.get(0)).getKey();
			//int next_index=-1;
			//System.out.println("our_location_index "+our_location_index);

			//this.g.getNode(targets.listIterator().previous()).getKey();
			int	 next_index =this.g.getNode((int )targets.get(1)).getKey();
			//			while (targets.listIterator().hasNext()) {//ask aviv if it o.k or maybe do a for loop// check what the closest node

			//				
			//				double min_dist= Double.MAX_VALUE;
			//				OOP_Point3D other_location=this.g.getNode(targets.listIterator().next()).getLocation();
			path.addAll(shortestPath(our_location_index,next_index ));


			targets.remove(0);
		}

		int size=path.size();

		for (int i = 0; i < size-1; i++) {
			if(i<path.size()-1&&path.get(i).getKey()==path.get(i+1).getKey())
				path.remove(i);
		}

		//if (our_location.distance3D(other_location)<min_dist) {

		//					min_dist=our_location.distance3D(other_location);
		//					this.g.getNode(targets.listIterator().previous());
		//					next_index=this.g.getNode(targets.listIterator().next()).getKey();
		//					this.g.getNode(targets.listIterator().previous());

		//		}




		return path;
	}


	public oop_graph copy() {

		save("temp.txt");
		Graph_Algo tamp = new Graph_Algo(null);
		tamp.init("temp.txt");
		oop_graph newG = tamp.g;

		return newG;
	}

	private oop_node_data find_min (ArrayList<oop_node_data> q) {

		oop_node_data u =q.get(0);
		double min =u.getWeight();
		for (oop_node_data x :q) {// 
			double temp=x.getWeight();
			if (temp<min) {
				min=temp;
				u=x;
			}
		}
		return u;
	}

	private Collection<oop_node_data> neighbors  (oop_node_data q) {// help function for Dijkstra

		ArrayList<oop_node_data> ans= new ArrayList<>();

		Collection<oop_edge_data>  collection = this.g.getE(q.getKey());
		if(collection == null)
			return ans;//Because we dont have more  neighbors ///
		for (oop_edge_data e : collection)   //  for each pointer edges neighbors
		{
			oop_node_data  neighbor= this.g.getNode(e.getDest());
			ans.add(neighbor);

		}
		return ans;
	}
	public double get_left () {
		double left = Double.POSITIVE_INFINITY;
		for ( oop_node_data    v : g.getV()) {
			if(v.getLocation()==null)
				continue;
			if(v.getLocation().x()<left) {
				left=v.getLocation().x();

			}
		}
		return left;
	}
	public double get_right () {
		double right = Double.NEGATIVE_INFINITY;
		for ( oop_node_data    v : g.getV()) {
			if(v.getLocation()==null)
				continue;
			if(v.getLocation().x()>right) {
				right=v.getLocation().x();

			}
		}
		return right;
	}
	public double get_top () {
		double top = Double.NEGATIVE_INFINITY;
		for ( oop_node_data    v : g.getV()) {
			if(v.getLocation()==null)
				continue;
			if(v.getLocation().y()>top) {
				top=v.getLocation().y();

			}
		}
		return top;
	}
	public double get_bottom () {
		double bottom = Double.POSITIVE_INFINITY;
		for ( oop_node_data    v : g.getV()) {
			if(v.getLocation()==null)
				continue;
			if(v.getLocation().y()<bottom) {
				bottom=v.getLocation().y();

			}
		}
		return bottom;
	}
}

