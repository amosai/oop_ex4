package gameClient;

import java.util.ArrayList;

import Server.Fruit;
import oop_dataStructure.oop_node_data;

public class Pair {
	private Fruit fruit;
	private ArrayList<oop_node_data> path;
	
	public Pair(Fruit fruit, ArrayList<oop_node_data> path) {
		this.fruit = fruit;
		this.path = path;
	}

	public Fruit getFruit() {
		return fruit;
	}

	public void setFruit(Fruit fruit) {
		this.fruit = fruit;
	}

	public ArrayList<oop_node_data> getPath() {
		return path;
	}

	public void setPath(ArrayList<oop_node_data> path) {
		this.path = path;
	}
}
