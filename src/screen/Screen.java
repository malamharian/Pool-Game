package screen;

import java.awt.Point;
import java.awt.Toolkit;

public class Screen {

	public static int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public static Point getCenter()
	{
		return new Point(screenWidth/2, screenHeight/2);
	}
}
