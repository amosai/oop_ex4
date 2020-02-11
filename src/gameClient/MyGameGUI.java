package gameClient;



import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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


public class MyGameGUI implements Runnable{

	private Graph_GUI   mygui;
	public OOP_DGraph gg = new OOP_DGraph();
	volatile static ArrayList<Integer> myIs=new ArrayList<Integer>();
	//ArrayList<oop_node_data> path;//=new ArrayList<oop_node_data>();
	ArrayList<Thread> threads=new ArrayList<Thread>();


	KML_Logger kml =new KML_Logger();
	public void init(int[] scenari_and_case_num) throws InterruptedException //1= manual else auto
	, ParseException
	{	
		allInAll=new ArrayList<Three<Fruit,RobotG,ArrayList<oop_node_data>>>();
		game_service game = Game_Server.getServer(scenari_and_case_num[1]); // you have [0,23] games
		String g = game.getGraph();



		gg.init(g);
		String info = game.toString();
		Fruit b = getFruits(game.getFruits()).get(1);//check

		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("robots");
			for (int i = 0; i < rs; i++) 
				myIs.add(0);

			////////////////0
			System.out.println(rs);
			/////////////////////
			System.out.println(info);
			System.out.println(g);
			// the list of fruits should be considered in your solution
			Iterator<String> f_iter = game.getFruits().iterator();
			while(f_iter.hasNext()) {System.out.println(f_iter.next());}	
			//int src_node = scenari_and_case_num[2];  // arbitrary node, you should start at one of the fruits
			for(int a = 0;a<rs;a++) {
				JFrame input2 = new JFrame();
				String num2 = JOptionPane.showInputDialog(input2, "press  set node num for robot num " +(a+1)+"  " );
				int t=Integer.parseInt(num2);
				game.addRobot(t);

			}
		}
		catch (JSONException e) {e.printStackTrace();}

		//MyGameGUI myGameGUI = new MyGameGUI();
		this.graphToGraph(game.getGraph());

		this.graphToRobots(game.getRobots());
		this.graphToFruit(game.getFruits());
		//this.graphToFruit(game.getFruits());
		// this.graphToRobots(game.getRobots());
		// this.getRobots(game.getRobots());
		//this.graphToGraphdraw();
		//test if we have fruits list

		//		ArrayList<RobotG> rbs=robots(game.getRobots());
		//		for (RobotG r : rbs) {
		//			System.out.println("temp test:: "+r.getLocation().x());
		//		}




		//this.graphToRobots(game.getRobots());
		// now we have scenario and dr; fruits and robots and we need to draw them with our graph-gui
		game.startGame();

		// should be a Thread!!!
		if(scenari_and_case_num[0]==1) {
			while(game.isRunning()) {
				moveRobotsManual(game, gg);		

			}
		}else {
			ArrayList<Fruit> fs=getFruits(game.getFruits());
			ArrayList<RobotG> rs=getRobots(game.getRobots());

						for (RobotG robotG : rs) {
							kml.SetRobot(robotG.getLocation().x(),(robotG.getLocation().y()));
						}
			
						for (Fruit f : fs) {
							kml.SetFruit(f.getLocation().x(),(f.getLocation().y()));
						}

			//ArrayList<RobotG> robots=new ArrayList<RobotG>(rs);
			//System.out.println("Before robot changed his location: "+Arrays.toString(rs.toArray()));

			//			System.out.print("First algorithm path: ");
			//			for (oop_node_data oo : pathAndRobot.getValue()) 
			//				System.out.print(oo.getKey()+" ");
			//			System.out.println();
			//			System.out.println("First algorithm path: "+Arrays.toString(path.toArray()));
			int rs_size=rs.size();
			int c=0;
			//			////////////////////////////////////////////////////////////////////////////////////////////////			
			//			for (int i = 0; i<rs_size ; i++) {
			//				Three<Fruit,RobotG,ArrayList<oop_node_data>> pathAndRobot=getNearestPathOfRobotToFruit(rs,fs);
			//				if(pathAndRobot==null)break;
			//				path=pathAndRobot.getPath();
			//
			//				if (rs.size()>0)
			//					rs.remove(pathAndRobot.getRobot());
			//				if (fs.size()>0)
			//					fs.remove(pathAndRobot.getFruit());
			//				//need to remove fruit from list
			//
			//				Runnable runa=()->{
			//					while (game.isRunning()) {
			//						System.out.println("Running..."+Thread.currentThread().getName());
			//						if(pathAndRobot!=null) {
			//							//	System.out.println("rs num "+rs.size()+"  fs num "+fs.size());
			//							moveRobotsAlgo(game, gg,pathAndRobot);
			//						}else {
			//
			//							/// maybe do here interupt beacuse  if(pathAndRobot==null) we get game over syso
			//							return;
			//						}
			//					}
			//				};
			//
			//				threads.add(new Thread(runa));
			//				threads.get(i).start();
			//				//	System.out.println(i+" its number thread");
			//			}				
			//////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////		//////////////////////////////////////////////////////////////////////////////////


			ArrayList <Three<Fruit, RobotG, ArrayList<oop_node_data>>>  pathsRobotsfruits=new  ArrayList<>();
			for (int i = 0; i<rs_size ; i++) {
				Three<Fruit,RobotG,ArrayList<oop_node_data>> pathRobotfruit=getNearestPathOfRobotToFruit(rs,fs);
				if(pathRobotfruit==null)break;
				//path=pathAndRobot.getPath();

				pathsRobotsfruits.add(pathRobotfruit);

				if (rs.size()>0)

					rs.remove(pathRobotfruit.getRobot());
				if (fs.size()>0)
					fs.remove(pathRobotfruit.getFruit());
				//need to remove fruit from list
			}
			for (int i = 0; i<rs_size ; i++) {
				Three<Fruit, RobotG, ArrayList<oop_node_data>> frp =pathsRobotsfruits.get(i);
				allInAll.add(frp);
				Runnable runa=()->{

					while (game.isRunning()) {
						//System.out.println("Running..."+Thread.currentThread().getName());
						if(frp!=null) {
							//	System.out.println("rs num "+rs.size()+"  fs num "+fs.size());
							moveRobotsAlgo(game, gg,frp);
						}else {

							/// maybe do here interupt beacuse  if(pathAndRobot==null) we get game over syso
							return;
						}
					}
				};

				threads.add(new Thread(runa));
				threads.get(i).start();
				//	System.out.println(i+" its number thread");
			}
			//			
			//						while (game.isRunning()) {
			//							//				System.out.println("Running...");
			//							if(pathAndRobot!=null) {
			//								//////////////////////////////
			//								Thread.sleep(200);
			//								/////////////////////////
			//								moveRobotsAlgo(game, gg,pathAndRobot);
			//								//StdDraw.clear();
			//							}else {
			//								return;
			//							}
			//						}

		}
		for(Thread t: threads)
			t.join();
		kml.CreatFile(String.valueOf(scenari_and_case_num[1]));
		String results = game.toString();
		System.out.println("Game Over: "+results);
	}



	//	/////////////////** help function**/////////////////////
	volatile boolean  isInFruit=false;  /// need to  volatile
	volatile  static int countWaitThreads=0;
	//static int myI=1;
	volatile	ArrayList<Three<Fruit,RobotG,ArrayList<oop_node_data>>> allInAll;
	synchronized private  void  moveRobotsAlgo(game_service game, oop_graph gg,Three<Fruit,RobotG,ArrayList<oop_node_data>> frp) {
		if(frp==null||frp.getPath()==null) {
			System.err.println("pathAndRobot | pathAndRobot.getValue() is null");
			return;
		}
		//reDrawAll(game);
		//StdDraw.clear();
		/////////////////////////////////////////////
		//graphToGraph(game. getGraph());
		//graphToGraphdraw(myGui);
		graphToFruit(game.getFruits());
		graphToRobots(game.getRobots());




		/*for (RobotG robotG : getRobots(game.getRobots()))
			try {
				kml.SetRobot(robotG);
				//kml.SetFruit(f);
				System.out.println("painted a robot, location: "+robotG.getLocation());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/


		/*for (Fruit f :getFruits(game.getFruits()))
			try {
				kml.SetFruit(f);
				System.out.println("painted a fruit");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/


		/////////////////////////////////////////////////

		//this.graphToRobots(game.getRobots());
		List<String> log = game.move();
		//		MyGameGUI myGameGUI = new MyGameGUI();
		//		myGameGUI.graphToGraph(game.getGraph());
		//
		//myGameGUI.graphToRobots(game.getRobots());
		//myGameGUI.graphToRobots(game.getRobots());
		//graphToFruit(game.getFruits());
		//MyGameGUI myGameGUI = new MyGameGUI();
		//myGameGUI.graphToGraph(game.getGraph());

		if(log!=null) {
			long t = game.timeToEnd();
			//for(int i=0;i<log.size();i++) {
			//	System.out.println("log size: "+log.size()+" allinall "+allInAll.size());
			//String robot_json = log.get(i);
			try {
				//JSONObject line = new JSONObject(robot_json);
				//JSONObject ttt = line.getJSONObject("Robot");
				//int rid = ttt.getInt("id");
				//int src = ttt.getInt("src");
				//int dest = ttt.getInt("dest");

				int rid = frp.getRobot().getID()-allInAll.size();
				//int src = frp.getRobot().getSrcNode();
				int dest = frp.getRobot().getNextNode();


				//System.out.println(Thread.currentThread()+" json rid: "+rid +" json src: "+src+" json dest: "+dest);
				//System.out.println(Thread.currentThread()+" robotG rid: "+rid +" robotG src: "+frp.getRobot().getSrcNode()+" robotG dest: "+frp.getRobot().getNextNode());
				//if(src!=frp.getPath().get(i).getKey()) {
				//	System.out.println("srcs  not equal");
				//	if(rid!=frp.getRobot().getID()-allInAll.size()) {
				//	System.out.println(rid+" : "+frp.getRobot().getID());
				//	continue;
				//}
				//System.out.println("srcs  equal "+src +"equal"+frp.getPath().get(i).getKey());


				//										int rid =pathAndRobot.getKey().getID(); //ttt.getInt("id");
				//										int src =pathAndRobot.getValue().get(myI-1).getKey();// ttt.getInt("src");
				//										int dest =pathAndRobot.getKey().getNextNode(); //ttt.getInt("dest");
				//pathAndRobot.getKey().se
				//					int rid = pathAndRobot.getKey().getID();
				//					int src =pathAnd1Robot.getKey().getSrcNode();
				//					int dest =pathAndRobot.getValue().get(1).getKey();

				//here we need draw the robots on graph with our gui ()
				//					MyGameGUI myGameGUI = new MyGameGUI();
				//				    myGameGUI.graphToGraph(game.getGraph());
				//graphToFruit(game.getFruits());
				//graphToRobots(game.getRobots());
				//////

				if(dest==-1&&myIs.get(rid)<allInAll.get(rid).getPath().size()) {
					//if(dest==-1&&myIs.get(rid)<frp.getPath().size()) {


					//System.out.println(Thread.currentThread().getName()+" is  in  "+src+" node");
					//System.out.println("Enter next node:");
					//dest=Integer.parseInt(new Scanner(System.in).next());

					//if(isInFruit) {
					//						for (int j = 0; j < threads.size(); j++) {
					//							if(!threads.get(j).getName().equals(Thread.currentThread().getName())) {
					//								System.out.println(threads.get(j).getName()+" waiting...");
					//
					//
					//								threads.get(j).wait();
					//							}
					//							isInFruit=false;
					//}
					//System.out.println(Thread.currentThread().getName()+" interrupted "+" rid: "+rid+" dest "+dest);
					//Thread.currentThread().interrupt();

					//dest=frp.getPath().get(myIs.set(i, myIs.get(i)+1)).getKey();
					//dest=path.get(myIs.set(i, myIs.get(i)+1)).getKey();
					//						if(i<allInAll.size()) {
					//							int n =myIs.get(i)+1   ;
					//							dest=allInAll.get(i).getPath().get( n   ).getKey();
					//if(i<allInAll.size()) {

					//int nextN=myIs.set(rid, myIs.get(rid)+1) ;


					int nextN=myIs.get(rid) ;
					System.out.println(Thread.currentThread().getName()+" rid::  "+rid+" next node:: "+nextN);
					//if(allInAll.get(rid).getPath().size()>nextN) {
					dest=allInAll.get(rid).getPath().get(nextN).getKey();
					myIs.set(rid, nextN+1) ;
					game.chooseNextEdge(rid, dest);
					//}else {
					//	myIs.set(rid,1);
					//	 nextN=myIs.get(rid) ;
					//	dest=allInAll.get(rid).getPath().get(nextN).getKey();
					//	game.chooseNextEdge(rid, dest);
					//	
					//}


					System.out.println("  time to end:"+(t/1000));


				}else if(allInAll.get(rid).getPath().size()==myIs.get(rid)) {
					ArrayList<Fruit> fs=getFruits(game.getFruits());
					ArrayList<RobotG> rs=getRobots(game.getRobots());
					//System.out.println(Thread.currentThread().getName()+" get fruit in" );

					isInFruit=true;


					//pathAndRobot.getValue().remove(index)
					//ArrayList<RobotG> rs=pathAndRobot.getValue();
					//System.out.println("After robot changed his location: "+Arrays.toString(rs.toArray()));


					//	Three<Fruit,RobotG,ArrayList<oop_node_data>> pathRobotfruit=getNearestPathOfRobotToFruit(rs,fs);
					//	allInAll.set(rid,pathRobotfruit);

					//allInAll.clear();
					for (int j = 0; j<log.size(); j++) {
						Three<Fruit,RobotG,ArrayList<oop_node_data>> pathRobotfruit=getNearestPathOfRobotToFruit(rs,fs);
						if(pathRobotfruit==null)break;

						//path=pathAndRobot.getPath();
						int id= frp.getRobot().getID()-allInAll.size();
						allInAll.set(id,pathRobotfruit);

						if (rs.size()>0)
							rs.remove(pathRobotfruit.getRobot());
						if (fs.size()>0)
							fs.remove(pathRobotfruit.getFruit());
						//need to remove fruit from list


						System.out.print("New algorithm path of "+Thread.currentThread().getName()+" : ");
						for (oop_node_data oo : pathRobotfruit.getPath()) 
							System.out.print(oo.getKey()+" ");
						System.out.println();
						myIs.set(id,1);
						//							isInFruit=false;
					}

					//myIs.set(rid,1);


					//						for (int j = 0; j < threads.size(); j++) {
					//							if(!threads.get(j).getName().equals(Thread.currentThread().getName())) {
					//								System.out.println(threads.get(j).getName()+" notify all");
					//						
					//									
					//							//	threads.notifyAll();
					//							}
					//						}
					//myIs.set(i, 1);




					//Three<Fruit,RobotG,ArrayList<oop_node_data>> pAr=getNearestPathOfRobotToFruit(rs,fs);
					//path=pAr.getPath();
					//frp.setPath(pAr.getPath());
					//allInAll.set(i, pAr);

					//						System.out.print("New algorithm path: ");
					//						for (oop_node_data oo : pAr.getPath()) 
					//							System.out.print(oo.getKey()+" ");
					//						System.out.println();

					//myIs.set(i, 1);
				}else {

					//						dest=path.get(myIs.get(i)).getKey();
					//						myIs.set(i, myIs.get(i)+1);
					//						game.chooseNextEdge(rid, dest);
					//						System.out.println("Robot id: "+rid+" turn to node: "+dest+" from source: "+src+"  time to end:"+(t/1000));
				}


			}
			catch (Exception e) {e.printStackTrace();}
		}
		//}
		//		StdDraw.textLeft(0.1,0.9,"time : 00:" + game.timeToEnd()/1000);
		//		StdDraw.textRight(0.1,0.9,"moves:" + game.move());
		//mygui.clearrobots();
	}
	//mygui.clearrobots();

	/*private void reDrawAll(game_service game) {
	StdDraw.clear();
	graphToFruit(game.getFruits());
	graphToRobots(game.getRobots());
}
	 */
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
		String info = game.toString();
		StdDraw.text(0.1,0.1,"time to end: 00:" + game.timeToEnd()/1000);
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int ms= ttt.getInt("moves");
			StdDraw.text(0.93,0.05,"move:" + ms);

		}

		catch (JSONException e) {e.printStackTrace();}
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

	private Three<Fruit,RobotG,ArrayList<oop_node_data>> getNearestPathOfRobotToFruit(ArrayList<RobotG> robots, ArrayList<Fruit> fruits) {
		if(robots.size()==0) {
			System.err.println("robots list is empty");
			return null;
		}
		Pair fruitAndPath=getNearestFruitPath(robots.get(0).getSrcNode(),fruits); // not need

		double min=pathWeight(fruitAndPath.getPath())/(fruitAndPath.getFruit().getValue());//------
		RobotG rMin=robots.get(0);
		Fruit fMin=fruitAndPath.getFruit();
		fruitAndPath=getNearestFruitPath(rMin.getSrcNode(),fruits);
		for (RobotG r : robots) {
			Pair fruitAndPathTemp=getNearestFruitPath(r.getSrcNode(),fruits);
			double dist=pathWeight(fruitAndPathTemp.getPath())/(fruitAndPathTemp.getFruit().getValue());//-------
			if(dist<=min) {
				rMin=r;
				//fMin=fruitAndPathTemp.getFruit();
				min=dist;
				fruitAndPath=fruitAndPathTemp;
			}
		}
		System.out.println(rMin+" ***************** "+fMin+" ********************");
		return new Three<Fruit,RobotG,ArrayList<oop_node_data>>(fruitAndPath.getFruit(),rMin,fruitAndPath.getPath());
	}

	private double pathWeight(ArrayList<oop_node_data> path) {
		double res=0;
		for (oop_node_data oop_node_data : path)res+=oop_node_data.getWeight();
		return res;
	}

	Graph_Algo algo=new Graph_Algo(gg);
	private Pair getNearestFruitPath(int currentNode, ArrayList<Fruit> fruits) {	

		algo.init(gg);
		oop_edge_data edgeOfFruit = edgeOfFruit(fruits.get(0));
		double min=algo.shortestPathDist(currentNode, edgeOfFruit.getSrc());
		min+= edgeOfFruit.getWeight();
		min=min/(fruits.get(0).getValue());//----
		Fruit nearestFruit=fruits.get(0);//Fruit nearestFruit=null;----------
		int i=0;
		for (Fruit fruit : fruits) {
			i++;
			try {

				//System.out.println(edgeOfFruit(fruit).toString());
			}catch(Exception x){
				Fruit f=fruit;

			}
			oop_edge_data edgeOfFruitTemp = edgeOfFruit(fruit);
			double dist=algo.shortestPathDist(currentNode, edgeOfFruitTemp.getSrc());
			dist+=edgeOfFruitTemp.getWeight();
			dist=dist/(fruit.getValue());//-----------

			if(dist<=min) {
				min=dist;
				nearestFruit=fruit;
			}
		}
		//return   algo.shortestPath(currentNode,  edgeOfFruit(nearestFruit).getDest());
		ArrayList<oop_node_data> toAdd=algo.shortestPath(currentNode,  edgeOfFruit(nearestFruit).getSrc());
		toAdd.add(gg.getNode(edgeOfFruit(nearestFruit).getDest()));
		Pair res=new Pair(nearestFruit,toAdd);	
		return  res ;
	}




	private  void moveRobotsManual(game_service game, oop_graph gg) {
		//mygui.clearrobots();
		//MyGameGUI myGameGUI1 = new MyGameGUI();
		//myGameGUI1.graphToGraph(game.getGraph());
		//myGameGUI1.graphToFruit(game.getFruits());
		//myGameGUI1.graphToRobots(game.getRobots());
		//myGameGUI.graphToRobots(game.getRobots());

		//////////////////////////////////////////////

		graphToFruit(game.getFruits());
		graphToRobots(game.getRobots());
		//////////////////////////////////////////////////////////////		//////////////////////////////////////
		List<String> log = game.move();
		//		MyGameGUI myGameGUI = new MyGameGUI();
		//		myGameGUI.graphToGraph(game.getGraph());

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
					//MyGameGUI myGameGUI1 = new MyGameGUI();
					//	myGameGUI1.graphToGraph(game.getGraph());
					//myGameGUI1.graphToFruit(game.getFruits());
					//myGameGUI1.graphToRobots(game.getRobots());

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
						Iterator<String> f_iter = game.getFruits().iterator();
						while(f_iter.hasNext()) {System.out.println(f_iter.next());}	
					}
				} 
				catch (JSONException e) {e.printStackTrace();}
			}
		}
		//mygui.clearrobots();
	}


	//OOP_DGraph dg = new OOP_DGraph();
	public void graphToGraph(String g) {	
		//gg.init(g);
		mygui =new Graph_GUI(gg);
		mygui.drawGraph();

	}// do function call to my gui.call

	public void graphToGraphdraw(Graph_GUI myGui) {	
		//gg.init(g);
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
				//kml.SetFruit(p3.x(),p3.y());
				MyFruit ob = new MyFruit(p3,"fruit.png");
				mygui.addMyFruit(ob);

			} catch (Exception e) {
				e.printStackTrace();
			}	



		}

		mygui.drawFruits();;
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
				kml.SetRobot(p3.x(),p3.y());
				System.out.println("painted robot: "+p3.x()+" , "+p3.y());
				GameRobots ob = new GameRobots(p3,"robot.png");
				mygui.addGameRobot(ob);

			} catch (Exception e) {
				e.printStackTrace();
			}	


		}
		//System.out.println("graphToRobots");

		mygui.clearrobots();
		mygui.drawrobots();

	}

	public  ArrayList<RobotG> getRobots (List<String> g) {
		ArrayList<RobotG> robots=new ArrayList<RobotG>();
		Iterator<String> f_iter =g.iterator();
		while(f_iter.hasNext()){
			try {
				JSONObject line = new JSONObject(f_iter.next());
				//System.out.println("test json2: "+line);
				JSONObject robot=line.getJSONObject("Robot");
				int src =robot.getInt("src");

				String pos =robot.getString("pos");
				OOP_Point3D p3 = new OOP_Point3D(pos);
				//   Robot(,,,,);/// i am not finish
				//				GameRobots ob = new GameRobots(p3,"robot.png");
				//				mygui.addGameRobot(ob);

				RobotG r=new RobotG(gg,src,p3.x(),p3.y());

				GameRobots ob = new GameRobots(p3,"robot.png");
				mygui.addGameRobot(ob);
				robots.add(r);
			} catch (Exception e) {
				e.printStackTrace();
			}	


		}
		//StdDraw.clear();
		//mygui.drawGraph();
		//mygui.drawFruits();
		//System.out.println(" getRobots");
		//mygui.drawrobots();;
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

	@Override
	public void run() {


	}

}