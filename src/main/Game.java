package main;

import java.awt.Graphics;

import javax.swing.JFrame;

public class Game extends JFrame{

	public static final int UPS = 60;
	public static final int FPS = 60;
	public static final boolean RENDER_TIME = true;

	private boolean running;
	private Screen window;

	public Game(Screen window) {
		running = true;
	}

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
				update();
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
				}
				frames = 0;
				ticks = 0;
				timer += 1000;
			}
		}
	}

	private void getInput() {

	}

	private void update() {

	}

	private void render() {

	}

	public void paintComponent(Graphics g) {

	}

	public static void main(String[] args) {
		Game game = new Game(new Screen());
		game.run();
	}
}
