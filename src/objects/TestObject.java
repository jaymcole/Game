package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Random;
import main.Game;
import main.GameObject;
import main.ObjectManager;
import utilities.Utilities;

public class TestObject extends GameObject {

	private int bx, by;
	private Color c;
	private Rectangle bounds;
	private float speed;
	private float rotSpeed;
	private Point2D movePos;
	private Random rand;
	private int dir;
	private boolean isTurning;
	private float lifespan;
	
	public TestObject(Point2D pos, float rot, int bx, int by, ObjectManager om) {
		super(pos, rot, om);
		c = Utilities.randomColor();
		int buffer = 50;
		bounds = new Rectangle(buffer, buffer, bx-(buffer*2), by-(buffer*2));
		rand = new Random();
		this.bx = bx;
		this.by = by;
		speed = 2;
		rotSpeed = rand.nextFloat() + 0.5f;
		setDirection();
		movePos = new Point2D.Double(pos.getX(), pos.getY());
		lifespan = rand.nextInt(10000) + 50000;
	}
	
	public TestObject(Point2D pos, float rot, int bx, int by, ObjectManager om, Color color) {
		super(pos, rot, om);
		c = color;
		int buffer = 50;
		bounds = new Rectangle(buffer, buffer, bx-(buffer*2), by-(buffer*2));
		rand = new Random();
		this.bx = bx;
		this.by = by;
		speed = 2;
		rotSpeed = rand.nextFloat() + 0.5f;
		setDirection();
		movePos = new Point2D.Double(pos.getX(), pos.getY());
		lifespan = rand.nextInt(60000) + 10000;
	}
	
	@Override
	public void tick(long deltaTime) {
		if (movePos != null) {
			Point2D test = new Point2D.Double(pos.getX() + movePos.getX(), pos.getY() + movePos.getY());
			if (bounds.contains(test)) {
				pos = test;
			} else {
				isTurning = true;
				updateRot();
			}
		}
		
		if (isTurning) {			
			updateRot();
		} else {
			setDirection();
		}
		
		if (rand.nextFloat() > 0.9f) {
			isTurning = !isTurning;	
			if (isTurning && rand.nextFloat() > 0.9f)
				om.add(new TestObject(pos, rot, bx, by, om, c));
		}
			
		double nextX = (Math.cos(Math.toRadians(rot)) * speed);
		double nextY = (Math.sin(Math.toRadians(rot)) * speed);
		movePos.setLocation(nextX, nextY);
		lifespan -= deltaTime;
		if (lifespan <= 0) {
			om.remove(this);
		}
	}
	
	private void setDirection() {
		if (rand.nextBoolean())
			dir = 1;
		else 
			dir = -1;
	}
	
	private void updateRot() {
		rot += rotSpeed * dir;
		if (rot > 360) {
			rot %= 360;
		} else if (rot < 0) {
			rot += 360;
		}	
	}

	@Override
	public void draw(Graphics2D g) {
		int width = 15;
		int height = 20;
		Rectangle rect = new Rectangle((int)pos.getX()-width/2, (int)pos.getY()-height/2, width, height);
		
		AffineTransform old = g.getTransform();
		AffineTransform trans = new AffineTransform();
		
		trans.rotate(Math.toRadians(rot + 90), rect.getCenterX(), rect.getCenterY());
		
		g.setTransform(trans);
		
		g.setColor(c);
		g.draw(rect);
		g.setTransform(old);
		
		if (movePos != null) {
			int m = 20;
			g.drawLine((int)(pos.getX() + (movePos.getX() * m)), (int)(pos.getY() + (movePos.getY() * m)), (int)pos.getX(), (int)pos.getY());
		}
		
		if (Game.DEBUG) {
			g.setFont(g.getFont().deriveFont(10.0f));
			g.drawString("Pos: " + pos.getX() + ", " + pos.getY(), (int)pos.getX(), (int)pos.getY() - 10);
			g.drawString("Pos: " + movePos.getX() + ", " + movePos.getY(), (int)pos.getX(), (int)pos.getY() - 20);
			g.drawString("Rot " + rot, (int)pos.getX(), (int)pos.getY() - 30);
			g.drawString("SPD: " + speed + "\t RSPD: " + rotSpeed + " \t DIR: " + dir, (int)pos.getX(), (int)pos.getY() - 40);
		}
		
		g.drawString("" + lifespan, (int)pos.getX(), (int)pos.getY());
		
		g.setColor(Color.CYAN);
		g.draw(bounds);
	}

}
