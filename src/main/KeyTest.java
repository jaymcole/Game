package main;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyTest implements KeyListener{
	
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

	public static void main(String[] args)
	{
		new KeyTest().init();
	}
	
	private String mess = "";
	
	public void init()
	{
		s = new ScreenManager();
		DisplayMode dm = s.findFirstCompatibleMode(modes);
		s.setFullScreen(dm);
		
		Window w = s.getFullScreenWindow();
		w.setFont(new Font("Arial", Font.PLAIN, 20));
		w.setBackground(Color.GREEN);
		w.setForeground(Color.WHITE);
		w.setFocusTraversalKeysEnabled(false);
		w.addKeyListener(this);
		mess = "press escape to exit";
		running = true;
		run();
	}
	
	public void run ()
	{
		long cumTime = 0;
		while(running)
		{
			long timePassed = System.currentTimeMillis() - cumTime;
			cumTime += timePassed;
			
			Graphics2D g = s.getGraphics();
			draw(g);
			g.dispose();
			s.update();
			
			try{
			//	Thread.sleep(20);
			}catch(Exception e) {}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ESCAPE)
			System.exit(0);
		else
		{
			mess = "Pressed : " + KeyEvent.getKeyText(keyCode);
			e.consume();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public synchronized void draw(Graphics2D g)
	{
		Window w = s.getFullScreenWindow();
		g.setColor(w.getBackground());
		g.fillRect(0, 0, s.getWidth(), s.getheight());
		g.setColor(Color.BLACK);
		g.drawString(mess, 30, 30);
	}
	
	
	
}
