package main;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;


public abstract class GameObject {
	protected Point2D pos;
	protected float rot;
	protected ObjectManager om;
	
	public abstract void tick (long deltaTime);
	public abstract void draw(Graphics2D g);
	
	public GameObject (ObjectManager om) {
		pos = new Point(0,0);
		rot = 0;
		this.om = om;
	}
	
	public GameObject (Point2D pos, ObjectManager om) {
		this.pos = pos;
		rot = 0;
		this.om = om;
	}
	
	public GameObject (Point2D pos, float rot, ObjectManager om) {
		this.pos = pos;
		this.rot = rot;
		this.om = om;
	}
	
	public void kill () {
		om.remove(this);
	}
	
	
	
	public void setPos(Point2D newPos) {
		pos = newPos;
	}
	
	public Point2D getPos () {
		return pos;
	}
	
	public void setRot(float newRot) {
		rot = newRot;
	}
	
	public float getRot() {
		return rot;
	}
	
}
