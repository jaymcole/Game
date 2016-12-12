package main;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.geom.Point2D;
import java.util.Random;

import objects.TestObject;

public class Game {

	public static final int UPS = 60;
	public static final int FPS = 60;
	public static final boolean RENDER_TIME = true;
	public static final boolean DEBUG = false;
	

	private static DisplayMode modes[] = {
			new DisplayMode(1920, 1080, 32, 0),
			new DisplayMode(1920, 1080, 24, 0),
			new DisplayMode(1920, 1080, 16, 0),
			new DisplayMode(800, 600, 32, 0),
			new DisplayMode(800, 600, 24, 0),
			new DisplayMode(800, 600, 16, 0),
			new DisplayMode(640, 480, 32, 0),
			new DisplayMode(640, 480, 24, 0),
			new DisplayMode(640, 480, 16, 0)
	};

	private boolean running; 
	protected ScreenManager s;
	private ObjectManager om;

	public Game(Screen window) {		
		try { 
			init();
			gameSetup();
			run();
		}finally {
			s.restoreScreen();
		}
	}

	public void init()
	{
		s = new ScreenManager();
		DisplayMode dm = s.findFirstCompatibleMode(modes);
		s.setFullScreen(dm);
		
		Window w = s.getFullScreenWindow();
		w.setFont(new Font("Arial", Font.PLAIN, 20));
		w.setBackground(Color.GREEN);
		w.setForeground(Color.WHITE);
		running = true;
	}
	
	public void gameSetup () {
		om = new ObjectManager();
		Random rand = new Random();
		int bx = s.getWidth();
		int by = s.getheight();
		for (int i = 0; i < 100; i++) {
			om.add(new TestObject(new Point2D.Double((bx/2) + (rand.nextInt(200)-100),  (by/2) + (rand.nextInt(200)-100)), rand.nextInt(360), bx, by, om));
		}
		
	}
	
	private int fps, ups;
	public void run() {

		long initialTime = System.nanoTime();
		final double timeU = 1000000000 / UPS;
		final double timeF = 1000000000 / FPS;
		double deltaU = 0, deltaF = 0;
		int frames = 0, ticks = 0;
		long timer = System.currentTimeMillis();

		while (running) {

			long currentTime = System.nanoTime();
			deltaU += (currentTime - initialTime) / timeU;
			deltaF += (currentTime - initialTime) / timeF;
			initialTime = currentTime;

			if (deltaU >= 1) {
				getInput();
				update(currentTime);
				ticks++;
				deltaU--;
			}

			if (deltaF >= 1) {
				render();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				if (RENDER_TIME) {
					System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
					fps = frames;
					ups = ticks;
				}
				frames = 0;
				ticks = 0;
				timer += 1000;
			}
		}
	}

	private void getInput() {
		
	}

	private void update(long deltaTime) {
		//s.update();
		om.tick(deltaTime);
	}

	private void render() {
		
		
		Graphics2D g = s.getGraphics();
		draw(g);		
		g.dispose();
		s.update();
	}
	
	public synchronized void draw(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, s.getWidth(), s.getheight());
		
		g.setColor(Color.CYAN);
		g.draw(new Rectangle(s.getWidth()/2, s.getheight()/2, 200, 75));
		
		
		om.draw(g);
		
		if (DEBUG) {
			g.drawString("FPS: " + fps + " / " + FPS, 10, 10);
			g.drawString("UPS: " + ups  + " / " + UPS, 10, 20);
		}
	}

	public static void main(String[] args) {
		Game game = new Game(new Screen());
		game.run();
	}
}
