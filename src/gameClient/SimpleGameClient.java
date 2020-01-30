package gameClient;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import gui.Graph_GUI;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import utils.StdDraw;
/**
 * This class represents a simple example for using the GameServer API:
 * the main file performs the following tasks:
 * 1. Creates a game_service [0,23] (line 36)
 * 2. Constructs the graph from JSON String (lines 37-39)
 * 3. Gets the scenario JSON String (lines 40-41)
 * 4. Prints the fruits data (lines 49-50)
 * 5. Add a set of robots (line 52-53) // note: in general a list of robots should be added
 * 6. Starts game (line 57)
 * 7. Main loop (should be a thread) (lines 59-60)
 * 8. move the robot along the current edge (line 74)
 * 9. direct to the next edge (if on a node) (line 87-88)
 * 10. prints the game results (after "game over"): (line 63)
 *  
 * @author boaz.benmoshe
 */
public class SimpleGameClient {


	public static void main(String[] a) {
		//test1();
		//MyGameGUI ggui=new MyGameGUI();
		//ggui.init();
	}
	//	public static void test1() {
	//		int scenario_num = 2;
	//		game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games
	//
	//		String g = game.getGraph();
	//		OOP_DGraph gg = new OOP_DGraph();
	//		gg.init(g);
	//		String info = game.toString();
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
	//			int src_node = 0;  // arbitrary node, you should start at one of the fruits
	//			for(int a = 0;a<rs;a++) {
	//				game.addRobot(src_node+a);
	//			}
	//		}
	//		catch (JSONException e) {e.printStackTrace();}
	//
	//		MyGameGUI myGameGUI = new MyGameGUI();
	//		myGameGUI.graphToGraph(game.getGraph());
	//		myGameGUI.graphToFruit(game.getFruits());
	//		myGameGUI.graphToRobots(game.getRobots());
	//		// now we have scenario and dr; fruits and robots and we need to draw them with our graph-gui
	//		game.startGame();
	//
	//		// should be a Thread!!!
	//		while(game.isRunning()) {
	//			moveRobots(game, gg);
	//
	//		}
	//		String results = game.toString();
	//		System.out.println("Game Over: "+results);
	//	}
	/** 
	 * Moves each of the robots along the edge, 
	 * in case the robot is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 * @param log
	 */
	//	private static void moveRobots(game_service game, oop_graph gg) {
	//		List<String> log = game.move();
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
	//					MyGameGUI myGameGUI = new MyGameGUI();
	//					myGameGUI.graphToGraph(game.getGraph());
	//					//	myGameGUI.graphToFruit(game.getFruits());
	//					myGameGUI.graphToRobots(game.getRobots());
	//					//////
	//					if(dest==-1) {	
	//						//we need to build our nexxt node (not randomal ) get from user
	//						dest = nextNode1(gg, src, game);
	//						//dest = nextNode(gg, src);
	//						game.chooseNextEdge(rid, dest);
	//						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
	//						System.out.println(ttt);
	//					}
	//				} 
	//				catch (JSONException e) {e.printStackTrace();}
	//			}
	//		}
	//	}
	/**
	 * a very simple random walk implementation!
	 * @param g
	 * @param src
	 * @return
	 */
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
	//	private static int nextNode1(oop_graph g, int src,game_service game) {
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
	//		StdDraw.text(0,200,"time to end "+t);
	//		//StdDraw.text(max_x-0.0001,min_y+0.0001,"00:" + game.timeToEnd()/1000);
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
}
