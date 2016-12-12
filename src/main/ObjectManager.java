package main;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;

public class ObjectManager {
	
	public static final int MAX_OBJECTS = 15000;
	
	private LinkedList<GameObject> objects;
	
	public ObjectManager() {
		objects = new LinkedList<GameObject>();
	}
	
	public void tick (long deltaTime) {
		LinkedList<GameObject> temp = (LinkedList<GameObject>) objects.clone();
		Iterator<GameObject> it = temp.iterator();
		while (it.hasNext()) {
			((GameObject)it.next()).tick(deltaTime);
		}
	}
	
	public void draw (Graphics2D g) {
		LinkedList<GameObject> temp = (LinkedList<GameObject>) objects.clone();
		Iterator<GameObject> it = temp.iterator();
		while (it.hasNext()) {
			((GameObject)it.next()).draw(g);
		}
	}
	
	public void add(GameObject object) {
		if (objects.size() >= MAX_OBJECTS)
			return;
			
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
