package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Random;

import main.GameObject;
import main.ObjectManager;

public class TestObject2 extends GameObject {

	private Color c;
	private Rectangle bounds;
	private float speed;
	private float rotSpeed;
	private Point2D movePos;
	private Random rand;
	private int dir;
	private boolean isTurning;
	
	public TestObject2(Point2D pos, float rot, int bx, int by, ObjectManager om) {
		super(pos, rot, om);
		c = Color.BLUE;
		int buffer = 50;
		bounds = new Rectangle(buffer, buffer, bx-(buffer*2), by-(buffer*2));
		rand = new Random();
		
		speed = 2;
		rotSpeed = rand.nextFloat() + 0.5f;
		setDirection();
		movePos = new Point2D.Double(pos.getX(), pos.getY());
	}
	
	@Override
	public void tick(long deltaTime) {
		if (movePos != null) {
			Point2D test = new Point2D.Double(pos.getX() + movePos.getX(), pos.getY() + movePos.getY());
			if (bounds.contains(test)) {
				c = Color.GREEN;
				pos = test;
			} else {
				isTurning = true;
				updateRot();
				c = Color.RED;
			}
		}
		
		if (isTurning) {			
			updateRot();
		} else {
			setDirection();
		}
		
		if (rand.nextFloat() > 0.9f) {
			isTurning = !isTurning;	
		}
			
		double nextX = (Math.cos(Math.toRadians(rot)) * speed);
		double nextY = (Math.sin(Math.toRadians(rot)) * speed);
		movePos.setLocation(nextX, nextY);
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
			int m = 100;
			g.drawLine((int)(pos.getX() + (movePos.getX() * m)), (int)(pos.getY() + (movePos.getY() * m)), (int)pos.getX(), (int)pos.getY());
		}
		
		g.setFont(g.getFont().deriveFont(10.0f));
		g.drawString("Pos: " + pos.getX() + ", " + pos.getY(), (int)pos.getX(), (int)pos.getY() - 10);
		
		g.drawString("Pos: " + movePos.getX() + ", " + movePos.getY(), (int)pos.getX(), (int)pos.getY() - 20);
		g.drawString("Rot " + rot, (int)pos.getX(), (int)pos.getY() - 30);
		g.drawString("SPD: " + speed + "\t RSPD: " + rotSpeed + " \t DIR: " + dir, (int)pos.getX(), (int)pos.getY() - 40);
		
		
		g.setColor(Color.CYAN);
		g.draw(bounds);
	}

}