package gameClient;



//import java.awt.Robot;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.experimental.theories.ParameterSignature;

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
import utils.Point3D;
import utils.StdDraw;

public class MyGameGUI {
	private Graph_GUI mygui;
	OOP_DGraph gg = new OOP_DGraph();
	//private MyGameGUI myGameGUI = new MyGameGUI();
	public void init() {
		int scenario_num = 1;
		game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games

		String g = game.getGraph();
		
		gg.init(g);
		String info = game.toString();
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
			int src_node = 3;  // arbitrary node, you should start at one of the fruits
			for(int a = 0;a<rs;a++) {
				game.addRobot(src_node+a);
			}
		}
		catch (JSONException e) {e.printStackTrace();}

		//MyGameGUI myGameGUI = new MyGameGUI();
		this.graphToGraph(game.getGraph());
		this.graphToFruit(game.getFruits());
		
		
		//test if we have fruits list
		
//		ArrayList<RobotG> rbs=robots(game.getRobots());
//		for (RobotG r : rbs) {
//			System.out.println("temp test:: "+r.getLocation().x());
//		}
		
		
		
		
		//this.graphToRobots(game.getRobots());
		// now we have scenario and dr; fruits and robots and we need to draw them with our graph-gui
		game.startGame();

		// should be a Thread!!!
		while(game.isRunning()) {
			moveRobots(game, gg);
		}
		String results = game.toString();
		System.out.println("Game Over: "+results);
	}

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
		graphToRobots(game.getRobots());
		

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

	
	private void edgeOfFruit(Fruit f)// 
	{
		
		Collection<oop_node_data> v = gg.getV();
		for(oop_node_data n : v) 
		{
			Collection<oop_edge_data> edges = gg.getE(n.getKey());
			for(oop_edge_data edge: edges) 
			{
				
				OOP_Point3D p1 =gg.getNode(edge.getSrc()).getLocation();
				OOP_Point3D p2 =gg.getNode(edge.getDest()).getLocation();
				   //check if   that fruit is on  edge
				if(Math.abs((p1.distance2D(p2)-(Math.abs((f.getLocation().distance2D(p1)))+(Math.abs((f.getLocation().distance2D(p2))))))) <=  0.0001){
					int low=n.getKey();
					int high=edge.getDest();
					if(n.getKey()>edge.getDest()) 
					{
						low= edge.getDest();
						high= n.getKey();
					}
					if(f.getType()==1) 
					{// we can eat in that direction
						oop_edge_data ouredge = gg.getEdge(low, high);
						if(ouredge!= null)
							GameObject.setEdge(ouredge);
					}
					//need to eat fruit reverse
					if(f.getType()==-1) 
					{
						oop_edge_data ouredge = gg.getEdge(high,low);
						if(ouredge!= null)
							GameObject.setEdge(ouredge);
					}
				}

			}
		}
	}
	
	
	private ArrayList<Integer> getShortedPathToFruit(game_service game,oop_graph gg, JSONObject jsonObj) {	
		Graph_Algo algo=new Graph_Algo(gg);
		algo.init(gg);
		double min;
		//for (Robot robo : ArrayList<Robot> ) { 
		
		for (String fruit : game.getFruits()) {
			//double dist=algo.shortestPathDist(jsonObj);
		}// 
		return null;
	}
	
	
	

	private  void moveRobots(game_service game, oop_graph gg) {
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
						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
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

				GameObject ob = new GameObject(p3,"fruit.png");
				mygui.addGameObject(ob);

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
				System.out.println("test json: "+line);
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

	public  ArrayList<RobotG> robots (List<String> g) {
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

public  ArrayList<Fruit> fruits (List<String> g)
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
			GameObject ob = new GameObject(p3,"fruit.png");
			Fruit f=new Fruit(val, p3, null);
			fruits.add(f);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	



		}
	return  fruits;

	}

}