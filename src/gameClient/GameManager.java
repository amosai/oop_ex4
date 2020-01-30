package gameClient;


import java.awt.Robot;
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

public class GameManager {


	static MyGameGUI mygui;
	game_service game;
	oop_graph gg;
	ArrayList<Fruit> ListFruit;
	ArrayList<Robot> ListRobots;


	public static void main(String[] a) {

		MyGameGUI ggui=new MyGameGUI();
		
		
		ggui.init(manu_Or_Auto());
		//TODO input validation

	}



	public static int manu_Or_Auto(){	

		JFrame input1 = new JFrame();
		String num1 = JOptionPane.showInputDialog(input1, "press 1 for manual and 2 for automatic:" );
		int r=Integer.parseInt(num1);
		///  insert exception
		return r;

	}
	public int scenario_num(){
		JFrame input2 = new JFrame();
		String num2 = JOptionPane.showInputDialog(input2, "press scenario number please:" );
		int s=Integer.parseInt(num2);
		///  insert exception
		return s;

	}
	public ArrayList<Fruit> GetFruits ()
	{
		return null;

	}

	// Init Robots


	public ArrayList<Robot> initRobots(game_service game, oop_graph _gr)
	{
		return null;

	}

}