package gameClient;

import oop_dataStructure.oop_edge_data;
import oop_utils.OOP_Point3D;
import utils.Point3D;

public class GameObject {
	public OOP_Point3D pos;
	public String image;//name of file.
	public oop_edge_data ed;
	public GameObject(OOP_Point3D pos, String image ) {
		super();
		this.pos = pos;
		this.image = image;
	}
	
	public static  void setEdge(oop_edge_data ed) {
		
	
	}
}
