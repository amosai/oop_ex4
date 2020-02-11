package gameClient;

import Server.Fruit;

public class Three<F,R,P> {
	
	private R robot;
	private P path;
	private F fruit;
	
	public Three(R key, P value) {
		this.robot = key;
		this.path = value;
	}
	public Three(F fruit,R key, P value) {
		this.robot = key;
		this.path = value;
		this.fruit=fruit;
	}
	
	
	public F getFruit() {
		return fruit;
	}

	public void setFruit(F fruit) {
		this.fruit = fruit;
	}

	public R getRobot() {
		return robot;
	}

	public void setRobot(R key) {
		this.robot = key;
	}

	public P getPath() {
		return path;
	}

	public void setPath(P value) {
		this.path = value;
	}
	

}
