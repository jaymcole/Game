package main;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;

public class ObjectManager {
	
	private LinkedList<GameObject> objects;
	
	public ObjectManager() {
		objects = new LinkedList<GameObject>();
		
	}
	
	public void tick (long deltaTime) {
		Iterator<GameObject> it = objects.iterator();
		while (it.hasNext()) {
			((GameObject)it.next()).tick(deltaTime);
		}
	}
	
	public void draw (Graphics2D g) {
		Iterator<GameObject> it = objects.iterator();
		while (it.hasNext()) {
			((GameObject)it.next()).draw(g);
		}
	}
	
	public void add(GameObject object) {
		if (object != null) {
			objects.add(object);
		}
	}
	
	public void remove (GameObject object) {
		if (object != null) {
			objects.remove(object);
		}
	}
	
	
}
