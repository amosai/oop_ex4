package gameClient;

import oop_utils.OOP_Point3D;
import utils.Point3D;

public class GameObject {
	public OOP_Point3D pos;
	public String image;//name of file.
	
	public GameObject(OOP_Point3D pos, String image) {
		super();
		this.pos = pos;
		this.image = image;
	}
	
	
}
