package utilities;

import java.awt.Color;
import java.util.Random;

public class Utilities {

	private static final Random rand = new Random();
	
	private static Color[] colors = {Color.BLUE,Color.CYAN,Color.GREEN,Color.MAGENTA,Color.ORANGE,Color.RED,Color.YELLOW};
	
	public static Color randomColor() {
		return colors[rand.nextInt(colors.length)];
	}
	
}
