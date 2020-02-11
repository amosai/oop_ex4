package gameClient;


import java.awt.Robot;
import java.text.ParseException;
import java.util.ArrayList;

import Server.Fruit;
import Server.game_service;
import oop_dataStructure.oop_graph;

public class GameManager {


	static MyGameGUI mygui;
	game_service game;
	oop_graph gg;
	ArrayList<Fruit> ListFruit;
	ArrayList<Robot> ListRobots;


	public static void main(String[] a) throws InterruptedException, ParseException {

		MyGameGUI ggui=new MyGameGUI();


		ggui.init(scenari_and_case_num());
		//TODO input validation

	}



	public static int[] scenari_and_case_num(){	



//		JFrame input1 = new JFrame();
//		String num1 = JOptionPane.showInputDialog(input1, "press 1 for manual and 2 for automatic:" );
//		int r=Integer.parseInt(num1);
//		if (r==3){ 
//			int[] rs= {2,3};
//			return rs;}
//	
//		JFrame input3 = new JFrame();
//		String num3 = JOptionPane.showInputDialog(input3, "press scenario number please 0-23:" );
//		int s=Integer.parseInt(num3);

		///  insert exception
		//		
		//		JFrame input2 = new JFrame();
		//		String num2 = JOptionPane.showInputDialog(input2, "press  set node num for robot 1" );
		//		int t=Integer.parseInt(num2);
		//		

		///  insert exception

		int[] rs= {2,2};
		return rs;
	}
	//	public int scenario_num(){
	//		JFrame input2 = new JFrame();
	//		String num2 = JOptionPane.showInputDialog(input2, "press scenario number please:" );
	//		int s=Integer.parseInt(num2);
	//		///  insert exception
	//		return s;
	//
	//	}
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
