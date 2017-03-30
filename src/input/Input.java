package input;

import java.awt.Point;


public class Input {
	
	private static int USED_KEYS = 6;

	private static boolean keyState[] = new boolean[USED_KEYS];
	private static boolean keyPressed[] = new boolean[USED_KEYS];
	private static boolean keyReleased[] = new boolean[USED_KEYS];
	
	public static int UP = 0;
	public static int DOWN = 1;
	public static int LEFT = 2;
	public static int RIGHT = 3;
	public static int ENTER = 4;
	public static int ESCAPE = 5;
	
	private static boolean mouseState[] = new boolean[3];
	private static boolean mousePressed[] = new boolean[3];
	private static boolean mouseReleased[] = new boolean[3];
	private static boolean mouseMoved;
	private static Point mousePosition = new Point(0, 0);
	
	public static int MOUSE_LEFT = 0;
	public static int MOUSE_RIGHT = 1;
	public static int MOUSE_MIDDLE = 2;
	
	public static void setMousePosition(int x, int y)
	{
		mousePosition.setLocation(x, y);
	}
	
	public static Point getMousePosition()
	{
		return mousePosition;
	}
	
	public static boolean isMouseMoved()
	{
		return mouseMoved;
	}
	
	public static void mouseMoveTrigger()
	{
		mouseMoved = true;
	}
	
	public static boolean isMouseDown(int mouseCode)
	{
		return mouseState[mouseCode];
	}
	
	public static boolean mouseDown(int mouseCode)
	{
		return mousePressed[mouseCode];
	}
	
	public static boolean mouseUp(int mouseCode)
	{
		return mouseReleased[mouseCode];
	}
	
	public static boolean isKeyDown(int keyCode)
	{
		return keyState[keyCode];
	}
	
	public static boolean keyDown(int keyCode)
	{
		return keyPressed[keyCode];
	}
	
	public static boolean keyUp(int keyCode)
	{
		return keyReleased[keyCode];
	}
	
	public static void setKeyStatus(int keyCode, boolean isPressed)
	{
		keyState[keyCode] = isPressed;
		if(isPressed)
			keyPressed[keyCode] = true;
		else
			keyReleased[keyCode] = true;
	}
	
	public static void setMouseStatus(int mouseCode, boolean isPressed)
	{
		mouseState[mouseCode] = isPressed;
		if(isPressed)
			mousePressed[mouseCode] = true;
		else
			mouseReleased[mouseCode] = true;
	}
	
	public static void clear()
	{
		for(int i = 0; i<USED_KEYS; i++)
		{
			keyPressed[i] = false;
			keyReleased[i] = false;
		}
		
		for(int i = 0; i<mouseState.length; i++)
		{
			mousePressed[i] = false;
			mouseReleased[i] = false;
		}
		
		mouseMoved = false;
	}
}
