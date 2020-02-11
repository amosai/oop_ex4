package gui;

import java.util.ArrayList;
import java.util.List;

import algorithms.Graph_Algo;
import gameClient.MyFruit;
import gameClient.GameRobots;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import oop_dataStructure.oop_node_data;
import oop_utils.OOP_Point3D;
import utils.Point3D;
import utils.StdDraw;

public class Graph_GUI {


	private double left ; 
	private double right ;
	private double top ;
	private double bottom ;
	private oop_graph g;
	private ArrayList<MyFruit> fruits = new ArrayList<MyFruit>();
	private ArrayList<GameRobots> robots = new ArrayList<GameRobots>();


	public Graph_GUI(oop_graph g) {
		super();
		this.g = g;
		set_box();
	}
	public  void set_box() {// help to  know the ultimate screen "%"of view 

		Graph_Algo algo = new Graph_Algo(this.g);
		this.left=algo.get_left();
		this.right =algo.get_right();
		this.top=algo.get_top();
		this.bottom=algo.get_bottom();

	}
	public  OOP_Point3D screen_position(OOP_Point3D p) {// help to change point 3d to std.draw "lunguich"


		double x=Math.abs((p.x()-left)/(left -right));
		x = 0.9*x+0.05;//0.9 Because we wont frame be not on the shell of screen
		double y=Math.abs((p.y()-bottom)/(bottom -top));
		y = 0.9*y+0.05;//0.9 Because we wont frame be not on the shell of screen

		return new OOP_Point3D(x,y);

	}
	private void drawNodes() {

		StdDraw.setPenRadius(0.04);
		StdDraw.setPenColor(StdDraw.BLUE);


		for (oop_node_data v  :g.getV()) {
			if (v.getLocation()==null)
				continue;
			OOP_Point3D sp=screen_position(v.getLocation());

			StdDraw.point(sp.x(), sp.y());
			StdDraw.text(sp.x(), sp.y()+0.04, ""+v.getKey());

		}
	}

	private void drawEdges() {

		StdDraw.setPenRadius(0.01);
		StdDraw.setPenColor(StdDraw.MAGENTA);

		for (oop_node_data v  :g.getV()) {// over all the vertices
			if (v.getLocation()==null)
				continue;

			if( g.getE(v.getKey()) == null)
				continue;

			for (oop_edge_data ed : g.getE(v.getKey())) {//// over all the edges in vertices
				oop_node_data u = g.getNode(ed.getDest());
				if (u.getLocation()==null)
					continue;

				OOP_Point3D vsp=screen_position(v.getLocation());
				OOP_Point3D usp=screen_position(u.getLocation());

				double xc = Math.abs( (usp.x() + vsp.x())/2 );//x center edge
				double yc = Math.abs( (usp.y() + vsp.y())/2 );//y center edge

				double xd = Math.abs(  (usp.x() - vsp.x())*7/8 + vsp.x() );//x direction
				double yd = Math.abs(  (usp.y() - vsp.y())*7/8 + vsp.y() );//y direction

				StdDraw.setPenColor(StdDraw.MAGENTA);
				StdDraw.line(vsp.x(), vsp.y(), usp.x(), usp.y());
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.point(xd, yd);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.text(xc, yc+0.02, (""+ed.getWeight()).substring(0, 4)  );
			}




		}



	}

	public void drawFruits() {
		for (MyFruit MyFruit :fruits ) {
			OOP_Point3D sp= screen_position(MyFruit.pos);
			StdDraw.picture(sp.x(), sp.y(), MyFruit.image);
			
		//	System.out.println(fruits.size());
		}
		 fruits.clear();
	}

	public void drawrobots() {
		for (GameRobots r : robots) {
			OOP_Point3D sp= screen_position(r.pos);
			StdDraw.picture(sp.x(), sp.y(), r.image);
			//System.out.println(robots.size());
			
		}
		robots .clear();
	}

	public void clearrobots() {
		
	}

	public void drawGraph() {
		drawEdges();
		drawNodes();
		//drawObjects();

		/*StdDraw.setPenRadius(0.05);
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.point(0.5, 0.5);*/




		/*StdDraw.setPenColor(StdDraw.MAGENTA);
		StdDraw.line(0.2, 0.2, 0.8, 0.2);*/

	}
	//	public void drawObjects() {
	//		
	//		//drawObjects();
	//		
	//	}
	//		//drawObjects();
	public void addMyFruit(MyFruit g) {
		fruits.add(g);
	}
	public void addGameRobot(GameRobots r) {
		robots.add(r);

	}
	public void listening() throws InterruptedException {
		char c = 0;
		while (c != 'q') {

			if (StdDraw.hasNextKeyTyped()) {
				c = StdDraw.nextKeyTyped();
			} 

			switch (c) {
			case 's':
				System.out.println("save");
				break;

			case 'l':
				System.out.println("load");
				break;

			case 'q':
				System.out.println("loads");
				break;

			default:
				break;
			}

			c = 0;
			Thread.sleep(15);

		}
	}


}
