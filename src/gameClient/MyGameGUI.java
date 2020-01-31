package gameClient;



//import java.awt.Robot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.plaf.synth.SynthStyle;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Fruit;
import Server.Game_Server;
import Server.RobotG;
import Server.game_service;
import algorithms.Graph_Algo;
import gui.Graph_GUI;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import oop_dataStructure.oop_node_data;
import oop_utils.OOP_Point3D;
import utils.StdDraw;

public class MyGameGUI {
	private Graph_GUI mygui;
	OOP_DGraph gg = new OOP_DGraph();
	//private MyGameGUI myGameGUI = new MyGameGUI();
	public void init(int manualOrAuto) //1= manual else auto
	{
		int scenario_num = 5;
		game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games

		String g = game.getGraph();

		gg.init(g);
		String info = game.toString();
		Fruit b = getFruits(game.getFruits()).get(1);//check
		//b.getType();
		//int v=edgeOfFruit(b).getDest();//check
		//System.out.println(v);
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("robots");
			System.out.println(info);
			System.out.println(g);
			// the list of fruits should be considered in your solution
			Iterator<String> f_iter = game.getFruits().iterator();
			while(f_iter.hasNext()) {System.out.println(f_iter.next());}	
			int src_node = 0;  // arbitrary node, you should start at one of the fruits
			for(int a = 0;a<rs;a++) {
				game.addRobot(src_node+a);
			}
		}
		catch (JSONException e) {e.printStackTrace();}

		//MyGameGUI myGameGUI = new MyGameGUI();
		this.graphToGraph(game.getGraph());
		this.graphToFruit(game.getFruits());
        this.graphToRobots(game.getRobots());

		//test if we have fruits list

		//		ArrayList<RobotG> rbs=robots(game.getRobots());
		//		for (RobotG r : rbs) {
		//			System.out.println("temp test:: "+r.getLocation().x());
		//		}




		//this.graphToRobots(game.getRobots());
		// now we have scenario and dr; fruits and robots and we need to draw them with our graph-gui
		game.startGame();

		// should be a Thread!!!
		if(manualOrAuto==1) {
			while(game.isRunning()) 
				moveRobotsManual(game, gg);		
		}else {
			ArrayList<Fruit> fs=getFruits(game.getFruits());
			ArrayList<RobotG> rs=getRobots(game.getRobots());

			ArrayList<RobotG> robots=new ArrayList<RobotG>(rs);
			//System.out.println("Before robot changed his location: "+Arrays.toString(rs.toArray()));
			Pair<RobotG,ArrayList<oop_node_data>> pathAndRobot=getNearestPathOfRobotToFruit(robots,fs);
			path=pathAndRobot.getValue();

			System.out.print("First algorithm path: ");
			for (oop_node_data oo : pathAndRobot.getValue()) 
				System.out.print(oo.getKey()+" ");
			System.out.println();
			System.out.println("First algorithm path: "+Arrays.toString(path.toArray()));
			while (game.isRunning()) {
				//System.out.println("Running...");
				//for (RobotG robotG : rs) {

				if(pathAndRobot!=null) {
					//robots.remove(pathAndRobot.getKey());
					moveRobotsAlgo(game, gg,pathAndRobot);
				}else {
					return;
				}

				//}

			}

		}

		String results = game.toString();
		System.out.println("Game Over: "+results);
	}
	static int myI=1;
	ArrayList<oop_node_data> path;//=pathAndRobot.getValue();
	private  void moveRobotsAlgo(game_service game, oop_graph gg,Pair<RobotG,ArrayList<oop_node_data>> pathAndRobot) {
		if(pathAndRobot==null||pathAndRobot.getValue()==null) {
			System.err.println("pathAndRobot | pathAndRobot.getValue() is null");
			return;
		}




		StdDraw.clear();
		this.graphToRobots(game.getRobots());
		List<String> log = game.move();
		MyGameGUI myGameGUI = new MyGameGUI();
		myGameGUI.graphToGraph(game.getGraph());

		if(log!=null) {
			long t = game.timeToEnd();
			for(int i=0;i<log.size();i++) {
				String robot_json = log.get(i);
				try {
					JSONObject line = new JSONObject(robot_json);
					JSONObject ttt = line.getJSONObject("Robot");
					int rid = ttt.getInt("id");
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");
					//here we need draw the robots on graph with our gui ()
					//					MyGameGUI myGameGUI = new MyGameGUI();
					//				    myGameGUI.graphToGraph(game.getGraph());
					myGameGUI.graphToFruit(game.getFruits());
					myGameGUI.graphToRobots(game.getRobots());
					//////
					if(dest==-1&&myI<path.size()) {	
						//System.out.println("Enter next node:");
						//dest=Integer.parseInt(new Scanner(System.in).next());
						dest=path.get(myI++).getKey();
						//System.out.println("the key2: "+dest);

						game.chooseNextEdge(rid, dest);
						System.out.println("Robot id: "+rid+" turn to node: "+dest+" from source: "+src+"  time to end:"+(t/1000));
						System.out.println(ttt);
					}else if(path.size()==myI) {
						ArrayList<Fruit> fs=getFruits(game.getFruits());
						ArrayList<RobotG> rs=getRobots(game.getRobots());
						//pathAndRobot.getValue().remove(index)
						//ArrayList<RobotG> rs=pathAndRobot.getValue();
						//System.out.println("After robot changed his location: "+Arrays.toString(rs.toArray()));
						Pair<RobotG,ArrayList<oop_node_data>> pAr=getNearestPathOfRobotToFruit(rs,fs);
						path=pAr.getValue();

						System.out.print("New algorithm path: ");
						for (oop_node_data oo : pAr.getValue()) 
							System.out.print(oo.getKey()+" ");
						System.out.println();

						myI=1;
					}
				} 
				catch (JSONException e) {e.printStackTrace();}
			}
		}
		//mygui.clearrobots();
	}
	//mygui.clearrobots();



	private static int nextNode(oop_graph g, int src) {
		int ans = -1;
		Collection<oop_edge_data> ee = g.getE(src);
		Iterator<oop_edge_data> itr = ee.iterator();
		int s = ee.size();
		int r = (int)(Math.random()*s);
		int i=0;
		while(i<r) {itr.next();i++;}
		ans = itr.next().getDest();
		return ans;
	}

	/// i need do manual nextnode1 here or in somewhere
	private  int nextNode1(oop_graph g, int src,game_service game) {
		int ans = -1;
		Collection<oop_edge_data> ee = g.getE(src);
		Iterator<oop_edge_data> itr = ee.iterator();
		int s = ee.size();

		String toShow="";
		int count=0;
		for (oop_edge_data oop_edge_data : ee) {
			if(count<ee.size()-1)
				toShow+=	oop_edge_data.getDest()+" Or ";
			else
				toShow+=	oop_edge_data.getDest();
			count++;
		}
		long t = game.timeToEnd();
		//StdDraw.text(0,200,"time to end "+t);
		//StdDraw.clear();


		//this.graphToGraph(game.getGraph());
		//graphToFruit(game.getFruits());
		//graphToRobots(game.getRobots());


		//init();
		StdDraw.text(0.1,0.1,"time to end: 00:" + game.timeToEnd()/1000);
		JFrame input = new JFrame();
		String num = JOptionPane.showInputDialog(input, "Enter destination "+toShow+"");
		int r=Integer.parseInt(num);

		int selected=0;
		count=0;
		for (oop_edge_data oop_edge_data : ee) {
			if(oop_edge_data.getDest()==r) {
				selected=count;
			}
			count++;
		}
		r=selected;


		//int r = (int)(Math.random()*s);
		int i=0;
		while(i<r) {itr.next();i++;}
		ans = itr.next().getDest();
		return ans;
	}

	private oop_edge_data edgeOfFruit(Fruit f)
	{

		return edgeOfFruit(f.getType(),f.getLocation());
	}
	private oop_edge_data edgeOfFruit(int type,OOP_Point3D pos)// 
	{

		//System.out.println("ttyppe: "+type);
		Collection<oop_node_data> v = gg.getV();
		for(oop_node_data n : v) 
		{
			Collection<oop_edge_data> edges = gg.getE(n.getKey());
			for(oop_edge_data edge: edges) 
			{

				OOP_Point3D p1 =gg.getNode(edge.getSrc()).getLocation();
				OOP_Point3D p2 =gg.getNode(edge.getDest()).getLocation();
				//check if   that fruit is on  edge
				if(Math.abs((p1.distance2D(p2)-(Math.abs(   (pos.distance2D(p1))    )+(Math.abs((pos.distance2D(p2))))))) <=  0.0001){
					int low=n.getKey();
					int high=edge.getDest();
					if(n.getKey()>edge.getDest()) 
					{
						low= edge.getDest();
						high= n.getKey();
					}
					if(type==1) 
					{// we can eat in that direction
						oop_edge_data ouredge = gg.getEdge(low,high);

						//						if(ouredge!= null)
						//							GameObject.setEdge(ouredge);
						return ouredge;
					}
					//need to eat fruit reverse
					if(type==-1) 
					{
						oop_edge_data ouredge = gg.getEdge(high,low);
						//						if(ouredge!= null)
						//						GameObject.setEdge(ouredge);
						return ouredge;

					}
					else 
					{// we can eat in that direction
						oop_edge_data ouredge = gg.getEdge(low,high);

						//						if(ouredge!= null)
						//							GameObject.setEdge(ouredge);
						return ouredge;
					}
				}

			}
		}
		return null;

	}

	private Pair<RobotG,ArrayList<oop_node_data>> getNearestPathOfRobotToFruit(ArrayList<RobotG> robots, ArrayList<Fruit> fruits) {
		if(robots.size()==0) {
			System.err.println("robots list is empty");
			return null;
		}

		ArrayList<oop_node_data> pathResult=null; 
		double min=pathWeight(getNearestFruitPath(robots.get(0).getSrcNode(),fruits));
		RobotG rMin=robots.get(0);
		pathResult=getNearestFruitPath(rMin.getSrcNode(),fruits);
		for (RobotG r : robots) {
			System.out.println("RobotGsrc: "+r.getSrcNode()+"");
			double dist=pathWeight(getNearestFruitPath(r.getSrcNode(),fruits));
			if(dist<min) {
				rMin=r;
				min=dist;
				pathResult=getNearestFruitPath(r.getSrcNode(),fruits);
			}
		}	
		return new Pair<RobotG,ArrayList<oop_node_data>>(rMin,pathResult);
	}

	private double pathWeight(ArrayList<oop_node_data> path) {
		double res=0;
		for (oop_node_data oop_node_data : path)res+=oop_node_data.getWeight();
		return res;
	}


	private ArrayList<oop_node_data> getNearestFruitPath(int currentNode, ArrayList<Fruit> fruits) {	
		Graph_Algo algo=new Graph_Algo(gg);
		algo.init(gg);
		double min=algo.shortestPathDist(currentNode, edgeOfFruit(fruits.get(0)).getSrc());
		min+=(edgeOfFruit(fruits.get(0))).getWeight();

		Fruit nearestFruit=null;
		for (Fruit fruit : fruits) {
			try {

				System.out.println(edgeOfFruit(fruit).toString());
			}catch(Exception x){
				Fruit f=fruit;

			}
			System.out.println("currentNode"+currentNode+"edgeOfFruitsrc"+edgeOfFruit(fruit).getSrc());
			double dist=algo.shortestPathDist(currentNode, edgeOfFruit(fruit).getSrc());
			dist+=edgeOfFruit(fruit).getWeight();
			if(dist<=min) {
				min=dist;
				nearestFruit=fruit;
			}
		}
		//return   algo.shortestPath(currentNode,  edgeOfFruit(nearestFruit).getDest());
		ArrayList<oop_node_data> res=algo.shortestPath(currentNode,  edgeOfFruit(nearestFruit).getSrc());
		res.add(gg.getNode(edgeOfFruit(nearestFruit).getDest()));
		return  res ;
	}




	private  void moveRobotsManual(game_service game, oop_graph gg) {
		//mygui.clearrobots();
		StdDraw.clear();
		this.graphToRobots(game.getRobots());
		//this.graphToFruit(game.getFruits());
		//StdDraw.clear();
		//this.graphToGraph(game.getGraph());
		//graphToFruit(game.getFruits());
		//graphToRobots(game.getRobots());
		List<String> log = game.move();
		MyGameGUI myGameGUI = new MyGameGUI();
		myGameGUI.graphToGraph(game.getGraph());

		if(log!=null) {
			long t = game.timeToEnd();
			for(int i=0;i<log.size();i++) {
				String robot_json = log.get(i);
				try {
					JSONObject line = new JSONObject(robot_json);
					JSONObject ttt = line.getJSONObject("Robot");
					int rid = ttt.getInt("id");
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");
					//here we need draw the robots on graph with our gui ()
					//					MyGameGUI myGameGUI = new MyGameGUI();
					//				    myGameGUI.graphToGraph(game.getGraph());
					myGameGUI.graphToFruit(game.getFruits());
					myGameGUI.graphToRobots(game.getRobots());
					//////
					if(dest==-1) {	
						//we need to build our nexxt node (not randomal ) get from user
						dest = nextNode1(gg, src, game);// manual
						//dest = nextNode(gg, src);
						game.chooseNextEdge(rid, dest);
						System.out.println("id "+rid+"  dest: "+dest);
						//System.out.println();
						//System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
						System.out.println(ttt);
					}
				} 
				catch (JSONException e) {e.printStackTrace();}
			}
		}
		//mygui.clearrobots();
	}



	public void graphToGraph(String g) {


		OOP_DGraph gg = new OOP_DGraph();
		gg.init(g);
		mygui =new Graph_GUI(gg);
		mygui.drawGraph();

	}// do function call to my gui.call


	public void graphToFruit(List<String> g) {

		Iterator<String> f_iter =g.iterator();
		while(f_iter.hasNext()){
			try {
				JSONObject line = new JSONObject(f_iter.next());
				JSONObject fruit=line.getJSONObject("Fruit");
				String pos =fruit.getString("pos");
				OOP_Point3D p3 = new OOP_Point3D(pos);

				MyFruit ob = new MyFruit(p3,"fruit.png");
				mygui.addMyFruit(ob);

			} catch (Exception e) {
				e.printStackTrace();
			}	



		}
		mygui.drawObjects();;
	}



	public void graphToRobots(List<String> g) {
		Iterator<String> f_iter =g.iterator();
		while(f_iter.hasNext()){
			try {
				JSONObject line = new JSONObject(f_iter.next());
				//System.out.println("test json2: "+line);
				JSONObject robot=line.getJSONObject("Robot");
				String pos =robot.getString("pos");
				OOP_Point3D p3 = new OOP_Point3D(pos);

				GameRobots ob = new GameRobots(p3,"robot.png");
				mygui.addGameRobot(ob);

			} catch (Exception e) {
				e.printStackTrace();
			}	


		}
		mygui.drawrobots();;
	}

	public  ArrayList<RobotG> getRobots (List<String> g) {
		ArrayList<RobotG> robots=new ArrayList<RobotG>();
		Iterator<String> f_iter =g.iterator();
		while(f_iter.hasNext()){
			try {
				JSONObject line = new JSONObject(f_iter.next());
				System.out.println("test json2: "+line);
				JSONObject robot=line.getJSONObject("Robot");
				int src =robot.getInt("src");

				String pos =robot.getString("pos");
				OOP_Point3D p3 = new OOP_Point3D(pos);
				//   Robot(,,,,);/// i am not finish
				//				GameRobots ob = new GameRobots(p3,"robot.png");
				//				mygui.addGameRobot(ob);

				RobotG r=new RobotG(gg,src,p3.x(),p3.y());

				robots.add(r);
			} catch (Exception e) {
				e.printStackTrace();
			}	


		}
		mygui.drawrobots();;
		return robots;
	}

	public  ArrayList<Fruit> getFruits (List<String> g)
	{
		ArrayList<Fruit> fruits=new ArrayList<Fruit>();
		Iterator<String> f_iter =g.iterator();
		while(f_iter.hasNext()){
			try {
				JSONObject line = new JSONObject(f_iter.next());
				JSONObject fruit=line.getJSONObject("Fruit");
				String pos =fruit.getString("pos");
				OOP_Point3D p3 = new OOP_Point3D(pos);

				double val=  fruit.getDouble("value");
				//MyFruit ob = new MyFruit(p3,"fruit.png");
				int type =fruit.getInt("type");
				// OOP_Edge ed=new OOP_Edge();
				//System.out.println("The tyype json: "+   type);
				Fruit f1=new Fruit(val, p3, edgeOfFruit(type,p3));
				//int type =fruit.getInt("type");
				//Fruit f=new Fruit(val, p3, edgeOfFruit(f1));//edgeOfFruit it help function

				//System.out.println("The tyype: "+   f1.getType());
				fruits.add(f1);

			} catch (Exception e) {
				e.printStackTrace();
			}	



		}
		return  fruits;

	}

}//package gameClient;
//
//
//
////import java.awt.Robot;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Scanner;
//
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import Server.Fruit;
//import Server.Game_Server;
//import Server.RobotG;
//import Server.game_service;
//import algorithms.Graph_Algo;
//import gui.Graph_GUI;
//import oop_dataStructure.OOP_DGraph;
//import oop_dataStructure.oop_edge_data;
//import oop_dataStructure.oop_graph;
//import oop_dataStructure.oop_node_data;
//import oop_utils.OOP_Point3D;
//import utils.StdDraw;
//
//public class MyGameGUI {
//	private Graph_GUI mygui;
//	OOP_DGraph gg = new OOP_DGraph();
//	//private MyGameGUI myGameGUI = new MyGameGUI();
//	public void init(int manualOrAuto) //1= manual else auto
//	{
//		int scenario_num = 2;
//		game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games
//
//		String g = game.getGraph();
//
//		gg.init(g);
//		String info = game.toString();
//		Fruit b = getFruits(game.getFruits()).get(1);//check
//		//b.getType();
//		//int v=edgeOfFruit(b).getDest();//check
//		//System.out.println(v);
//		JSONObject line;
//		try {
//			line = new JSONObject(info);
//			JSONObject ttt = line.getJSONObject("GameServer");
//			int rs = ttt.getInt("robots");
//			System.out.println(info);
//			System.out.println(g);
//			// the list of fruits should be considered in your solution
//			Iterator<String> f_iter = game.getFruits().iterator();
//			while(f_iter.hasNext()) {System.out.println(f_iter.next());}	
//			int src_node = 13;  // arbitrary node, you should start at one of the fruits
//			for(int a = 0;a<rs;a++) {
//				game.addRobot(src_node+a);
//			}
//		}
//		catch (JSONException e) {e.printStackTrace();}
//
//		//MyGameGUI myGameGUI = new MyGameGUI();
//		this.graphToGraph(game.getGraph());
//		this.graphToFruit(game.getFruits());
//		
//
//		//test if we have fruits list
//
//		//		ArrayList<RobotG> rbs=robots(game.getRobots());
//		//		for (RobotG r : rbs) {
//		//			System.out.println("temp test:: "+r.getLocation().x());
//		//		}
//
//
//
//
//		//this.graphToRobots(game.getRobots());
//		// now we have scenario and dr; fruits and robots and we need to draw them with our graph-gui
//		game.startGame();
//
//		// should be a Thread!!!
//		if(manualOrAuto==1) {
//			while(game.isRunning()) 
//				moveRobotsManual(game, gg);		
//		}else {
//			ArrayList<Fruit> fs=getFruits(game.getFruits());
//			ArrayList<RobotG> rs=getRobots(game.getRobots());
//System.out.println(game.getRobots());
//			ArrayList<RobotG> robots=new ArrayList<RobotG>(rs);
//			//System.out.println("Before robot changed his location: "+Arrays.toString(rs.toArray()));
//			Pair<RobotG,ArrayList<oop_node_data>> pathAndRobot=getNearestPathOfRobotToFruit(robots,fs);
//			path=pathAndRobot.getValue();
//
//			System.out.print("First algorithm path: ");
//			for (oop_node_data oo : pathAndRobot.getValue()) 
//				System.out.print(oo.getKey()+" ");
//			System.out.println();
//			System.out.println("First algorithm path: "+Arrays.toString(path.toArray()));
//			while (game.isRunning()) {
//				//System.out.println("Running...");
//				//for (RobotG robotG : rs) {
//
//				if(pathAndRobot!=null) {
//					//robots.remove(pathAndRobot.getKey());
//					moveRobotsAlgo(game, gg,pathAndRobot);
//				}else {
//					return;
//				}
//
//				//}
//
//			}
//
//		}
//
//		String results = game.toString();
//		System.out.println("Game Over: "+results);
//	}
//	static int myI=1;
//	ArrayList<oop_node_data> path;//=pathAndRobot.getValue();
//	private  void moveRobotsAlgo(game_service game, oop_graph gg,Pair<RobotG,ArrayList<oop_node_data>> pathAndRobot) {
//		if(pathAndRobot==null||pathAndRobot.getValue()==null) {
//			System.err.println("pathAndRobot | pathAndRobot.getValue() is null");
//			return;
//		}
//
//
//
//
//		StdDraw.clear();
//		this.graphToRobots(game.getRobots());
//		List<String> log = game.move();
//		MyGameGUI myGameGUI = new MyGameGUI();
//		myGameGUI.graphToGraph(game.getGraph());
//
//		if(log!=null) {
//			long t = game.timeToEnd();
//			for(int i=0;i<log.size();i++) {
//				String robot_json = log.get(i);
//				try {
//					JSONObject line = new JSONObject(robot_json);
//					JSONObject ttt = line.getJSONObject("Robot");
//					int rid = ttt.getInt("id");
//					int src = ttt.getInt("src");
//					int dest = ttt.getInt("dest");
//					//here we need draw the robots on graph with our gui ()
//					//					MyGameGUI myGameGUI = new MyGameGUI();
//					//				    myGameGUI.graphToGraph(game.getGraph());
//					myGameGUI.graphToFruit(game.getFruits());
//					myGameGUI.graphToRobots(game.getRobots());
//					//////
//					if(dest==-1&&myI<path.size()) {	
//						//System.out.println("Enter next node:");
//						//dest=Integer.parseInt(new Scanner(System.in).next());
//						dest=path.get(myI++).getKey();
//						//System.out.println("the key2: "+dest);
//
//						game.chooseNextEdge(rid, dest);
//						System.out.println("Robot id: "+rid+" turn to node: "+dest+" from source: "+src+"  time to end:"+(t/1000));
//						System.out.println(ttt);
//					}else if(path.size()==myI) {
//						ArrayList<Fruit> fs=getFruits(game.getFruits());
//						ArrayList<RobotG> rs=getRobots(game.getRobots());
//						//pathAndRobot.getValue().remove(index)
//						//ArrayList<RobotG> rs=pathAndRobot.getValue();
//						//System.out.println("After robot changed his location: "+Arrays.toString(rs.toArray()));
//						Pair<RobotG,ArrayList<oop_node_data>> pAr=getNearestPathOfRobotToFruit(rs,fs);
//						path=pAr.getValue();
//
//						System.out.print("New algorithm path: ");
//						for (oop_node_data oo : pAr.getValue()) 
//							System.out.print(oo.getKey()+" ");
//						System.out.println();
//
//						myI=1;
//					}
//				} 
//				catch (JSONException e) {e.printStackTrace();}
//			}
//		}
//		//mygui.clearrobots();
//	}
//	//mygui.clearrobots();
//
//
//
//	private static int nextNode(oop_graph g, int src) {
//		int ans = -1;
//		Collection<oop_edge_data> ee = g.getE(src);
//		Iterator<oop_edge_data> itr = ee.iterator();
//		int s = ee.size();
//		int r = (int)(Math.random()*s);
//		int i=0;
//		while(i<r) {itr.next();i++;}
//		ans = itr.next().getDest();
//		return ans;
//	}
//
//	/// i need do manual nextnode1 here or in somewhere
//	private  int nextNode1(oop_graph g, int src,game_service game) {
//		int ans = -1;
//		Collection<oop_edge_data> ee = g.getE(src);
//		Iterator<oop_edge_data> itr = ee.iterator();
//		int s = ee.size();
//
//		String toShow="";
//		int count=0;
//		for (oop_edge_data oop_edge_data : ee) {
//			if(count<ee.size()-1)
//				toShow+=	oop_edge_data.getDest()+" Or ";
//			else
//				toShow+=	oop_edge_data.getDest();
//			count++;
//		}
//		long t = game.timeToEnd();
//		//StdDraw.text(0,200,"time to end "+t);
//		//StdDraw.clear();
//
//
//		//this.graphToGraph(game.getGraph());
//		//graphToFruit(game.getFruits());
//		//graphToRobots(game.getRobots());
//
//
//		//init();
//		StdDraw.text(0.1,0.1,"time to end: 00:" + game.timeToEnd()/1000);
//		JFrame input = new JFrame();
//		String num = JOptionPane.showInputDialog(input, "Enter destination "+toShow+"");
//		int r=Integer.parseInt(num);
//
//		int selected=0;
//		count=0;
//		for (oop_edge_data oop_edge_data : ee) {
//			if(oop_edge_data.getDest()==r) {
//				selected=count;
//			}
//			count++;
//		}
//		r=selected;
//
//
//		//int r = (int)(Math.random()*s);
//		int i=0;
//		while(i<r) {itr.next();i++;}
//		ans = itr.next().getDest();
//		return ans;
//	}
//
//	private oop_edge_data edgeOfFruit(Fruit f)
//	{
//		
//		return edgeOfFruit(f.getType(),f.getLocation());
//	}
//	private oop_edge_data edgeOfFruit(int type,OOP_Point3D pos)// 
//	{
//
//		Collection<oop_node_data> v = gg.getV();
//		for(oop_node_data n : v) 
//		{
//			Collection<oop_edge_data> edges = gg.getE(n.getKey());
//			for(oop_edge_data edge: edges) 
//			{
//
//				OOP_Point3D p1 =gg.getNode(edge.getSrc()).getLocation();
//				OOP_Point3D p2 =gg.getNode(edge.getDest()).getLocation();
//				//check if   that fruit is on  edge
//				if(Math.abs((p1.distance2D(p2)-(Math.abs(   (pos.distance2D(p1))    )+(Math.abs((pos.distance2D(p2))))))) <=  0.0001){
//					int low=n.getKey();
//					int high=edge.getDest();
//					if(n.getKey()>edge.getDest()) 
//					{
//						low= edge.getDest();
//						high= n.getKey();
//					}
//					if(type==1) 
//					{// we can eat in that direction
//						oop_edge_data ouredge = gg.getEdge(high,high);
//						//						if(ouredge!= null)
//						//							GameObject.setEdge(ouredge);
//						return ouredge;
//					}
//					//need to eat fruit reverse
//					if(type==-1) 
//					{
//						oop_edge_data ouredge = gg.getEdge(high,low);
//						//						if(ouredge!= null)
//						//						GameObject.setEdge(ouredge);
//						return ouredge;
//
//					}
//					else 
//					{
//						oop_edge_data ouredge = gg.getEdge(high,high);
//						//						if(ouredge!= null)
//						//							GameObject.setEdge(ouredge);
//						return ouredge;
//					}
//				}
//
//			}
//		}
//		return null;
//
//	}
//
//	private Pair<RobotG,ArrayList<oop_node_data>> getNearestPathOfRobotToFruit(ArrayList<RobotG> robots, ArrayList<Fruit> fruits) {
//		if(robots.size()==0) {
//			System.err.println("robots list is empty");
//			return null;
//		}
//
//		ArrayList<oop_node_data> pathResult=null; 
//		double min=pathWeight(getNearestFruitPath(robots.get(0).getSrcNode(),fruits));
//		RobotG rMin=robots.get(0);
//		pathResult=getNearestFruitPath(rMin.getSrcNode(),fruits);
//		for (RobotG r : robots) {
//			System.out.println("RobotGsrc: "+r.getSrcNode()+"");
//			double dist=pathWeight(getNearestFruitPath(r.getSrcNode(),fruits));
//			if(dist<min) {
//				rMin=r;
//				min=dist;
//				pathResult=getNearestFruitPath(r.getSrcNode(),fruits);
//			}
//		}	
//		return new Pair<RobotG,ArrayList<oop_node_data>>(rMin,pathResult);
//	}
//
//	private double pathWeight(ArrayList<oop_node_data> path) {
//		double res=0;
//		for (oop_node_data oop_node_data : path)res+=oop_node_data.getWeight();
//		return res;
//	}
//
//
//	private ArrayList<oop_node_data> getNearestFruitPath(int currentNode, ArrayList<Fruit> fruits) {	
//		Graph_Algo algo=new Graph_Algo(gg);
//		algo.init(gg);
//		double min=algo.shortestPathDist(currentNode, edgeOfFruit(fruits.get(0)).getSrc());
//		min+=(edgeOfFruit(fruits.get(0))).getWeight();
//
//		Fruit nearestFruit=null;
//		for (Fruit fruit : fruits) {
//			try {
//				
//			System.out.println(edgeOfFruit(fruit).toString());
//			}catch(Exception x){
//				Fruit f=fruit;
//				
//			}
//			System.out.println("currentNode"+currentNode+"edgeOfFruitsrc"+edgeOfFruit(fruit).getSrc());
//			double dist=algo.shortestPathDist(currentNode, edgeOfFruit(fruit).getSrc());
//			dist+=edgeOfFruit(fruit).getWeight();
//			if(dist<=min) {
//				min=dist;
//				nearestFruit=fruit;
//			}
//		}
//		//return   algo.shortestPath(currentNode,  edgeOfFruit(nearestFruit).getDest());
//		ArrayList<oop_node_data> res=algo.shortestPath(currentNode,  edgeOfFruit(nearestFruit).getSrc());
//		res.add(gg.getNode(edgeOfFruit(nearestFruit).getDest()));
//		return  res ;
//	}
//
//
//
//
//	private  void moveRobotsManual(game_service game, oop_graph gg) {
//		//mygui.clearrobots();
//		StdDraw.clear();
//		this.graphToRobots(game.getRobots());
//		//this.graphToFruit(game.getFruits());
//		//StdDraw.clear();
//		//this.graphToGraph(game.getGraph());
//		//graphToFruit(game.getFruits());
//		//graphToRobots(game.getRobots());
//		List<String> log = game.move();
//		MyGameGUI myGameGUI = new MyGameGUI();
//		myGameGUI.graphToGraph(game.getGraph());
//
//		if(log!=null) {
//			long t = game.timeToEnd();
//			for(int i=0;i<log.size();i++) {
//				String robot_json = log.get(i);
//				try {
//					JSONObject line = new JSONObject(robot_json);
//					JSONObject ttt = line.getJSONObject("Robot");
//					int rid = ttt.getInt("id");
//					int src = ttt.getInt("src");
//					int dest = ttt.getInt("dest");
//					//here we need draw the robots on graph with our gui ()
//					//					MyGameGUI myGameGUI = new MyGameGUI();
//					//				    myGameGUI.graphToGraph(game.getGraph());
//					myGameGUI.graphToFruit(game.getFruits());
//					myGameGUI.graphToRobots(game.getRobots());
//					//////
//					if(dest==-1) {	
//						//we need to build our nexxt node (not randomal ) get from user
//						dest = nextNode1(gg, src, game);// manual
//						//dest = nextNode(gg, src);
//						game.chooseNextEdge(rid, dest);
//						System.out.println("id "+rid+"  dest: "+dest);
//						//System.out.println();
//						//System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
//						System.out.println(ttt);
//					}
//				} 
//				catch (JSONException e) {e.printStackTrace();}
//			}
//		}
//		//mygui.clearrobots();
//	}
//
//
//
//	public void graphToGraph(String g) {
//
//
//		OOP_DGraph gg = new OOP_DGraph();
//		gg.init(g);
//		mygui =new Graph_GUI(gg);
//		mygui.drawGraph();
//
//	}// do function call to my gui.call
//
//
//	public void graphToFruit(List<String> g) {
//
//		Iterator<String> f_iter =g.iterator();
//		while(f_iter.hasNext()){
//			try {
//				JSONObject line = new JSONObject(f_iter.next());
//				JSONObject fruit=line.getJSONObject("Fruit");
//				String pos =fruit.getString("pos");
//				OOP_Point3D p3 = new OOP_Point3D(pos);
//
//				MyFruit ob = new MyFruit(p3,"fruit.png");
//				mygui.addMyFruit(ob);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}	
//
//
//
//		}
//		mygui.drawObjects();;
//	}
//
//
//
//	public void graphToRobots(List<String> g) {
//		Iterator<String> f_iter =g.iterator();
//		while(f_iter.hasNext()){
//			try {
//				JSONObject line = new JSONObject(f_iter.next());
//				//System.out.println("test json2: "+line);
//				JSONObject robot=line.getJSONObject("Robot");
//				String pos =robot.getString("pos");
//				OOP_Point3D p3 = new OOP_Point3D(pos);
//
//				GameRobots ob = new GameRobots(p3,"robot.png");
//				mygui.addGameRobot(ob);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}	
//
//
//		}
//		mygui.drawrobots();;
//	}
//
//	public  ArrayList<RobotG> getRobots (List<String> g) {
//		ArrayList<RobotG> robots=new ArrayList<RobotG>();
//		Iterator<String> f_iter =g.iterator();
//		while(f_iter.hasNext()){
//			try {
//				JSONObject line = new JSONObject(f_iter.next());
//				System.out.println("test json2: "+line);
//				JSONObject robot=line.getJSONObject("Robot");
//				int src =robot.getInt("src");
//
//				String pos =robot.getString("pos");
//				OOP_Point3D p3 = new OOP_Point3D(pos);
//				//   Robot(,,,,);/// i am not finish
//				//				GameRobots ob = new GameRobots(p3,"robot.png");
//				//				mygui.addGameRobot(ob);
//
//				RobotG r=new RobotG(gg,src,p3.x(),p3.y());
//
//				robots.add(r);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}	
//
//
//		}
//		mygui.drawrobots();;
//		return robots;
//	}
//
//	public  ArrayList<Fruit> getFruits (List<String> g)
//	{
//		ArrayList<Fruit> fruits=new ArrayList<Fruit>();
//		Iterator<String> f_iter =g.iterator();
//		while(f_iter.hasNext()){
//			try {
//				JSONObject line = new JSONObject(f_iter.next());
//				JSONObject fruit=line.getJSONObject("Fruit");
//				String pos =fruit.getString("pos");
//				OOP_Point3D p3 = new OOP_Point3D(pos);
//
//				double val=  fruit.getDouble("value");
//				//MyFruit ob = new MyFruit(p3,"fruit.png");
//				int type =fruit.getInt("type");
//				// OOP_Edge ed=new OOP_Edge();
//				System.out.println("The tyype json: "+   type);
//				Fruit f1=new Fruit(val, p3, edgeOfFruit(type,p3));
//				//int type =fruit.getInt("type");
//				//Fruit f=new Fruit(val, p3, edgeOfFruit(f1));//edgeOfFruit it help function
//
//				System.out.println("The tyype: "+   f1.getType());
//				fruits.add(f1);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}	
//
//
//
//		}
//		return  fruits;
//
//	}
//
//}