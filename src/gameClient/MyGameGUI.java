package gameClient;



import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import gui.Graph_GUI;
import oop_dataStructure.OOP_DGraph;
import oop_utils.OOP_Point3D;
import utils.Point3D;

public class MyGameGUI {
	private Graph_GUI mygui;



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
		mygui.drawGraph();
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

				GameObject ob = new GameObject(p3,"robot.png");
				mygui.addGameObject(ob);

			} catch (Exception e) {
				e.printStackTrace();
			}	


		}
		mygui.drawGraph();
	}
}
