package main;
import input.Input;
import input.KeyQueue;
import input.InputQueueItem;
import input.MouseQueue;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import screen.Screen;

import gamestate.GameStateManager;

public class Main extends Thread{

	private GameStateManager ssm = GameStateManager.getInstance();
	private KeyQueue inputQueue = new KeyQueue();
	private boolean isRunning = true;
	private Canvas canvas;
	private BufferStrategy strategy;
	private BufferedImage background;
	private Graphics2D bgGraphics;
	private Graphics2D graphics;
	private JFrame frame;
	private GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	private int width = Screen.screenWidth;
	private int height = Screen.screenHeight;
	
	public final BufferedImage create(final int width, final int height,
    		final boolean alpha) {
    	return config.createCompatibleImage(width, height, alpha
    			? Transparency.TRANSLUCENT : Transparency.OPAQUE);
    }
	
	public Main() {
		
    	frame = new JFrame();
    	frame.addWindowListener(new FrameClose());
    	frame.setUndecorated(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(width, height);
    	frame.setVisible(true);
    	frame.requestFocus();
    	

    	canvas = new Canvas(config);
    	canvas.setSize(width, height);
    	frame.add(canvas, "South");

    	background = create(width, height, false);
    	canvas.createBufferStrategy(2);
    	
    	do {
    		strategy = canvas.getBufferStrategy();
    	} while (strategy == null);
    	
    	start();
    	initMouseListener();
    	initKeyListener();
	}
	
	private void initKeyListener()
	{
		KeyAdapter adapter = new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				int inputKey = -1;
				if(keyCode == KeyEvent.VK_UP)
				{
					inputKey = Input.UP;
				}
				else if(keyCode == KeyEvent.VK_DOWN)
				{
					inputKey = Input.DOWN;
				}
				else if(keyCode == KeyEvent.VK_LEFT)
				{
					inputKey = Input.LEFT;
				}
				else if(keyCode == KeyEvent.VK_RIGHT)
				{
					inputKey = Input.RIGHT;
				}
				else if(keyCode == KeyEvent.VK_ENTER)
				{
					inputKey = Input.ENTER;
				}
				else if(keyCode == KeyEvent.VK_ESCAPE)
				{
					inputKey = Input.ESCAPE;
				}
				
				if(inputKey != -1)
				{
					inputQueue.enqueueInput(new InputQueueItem(inputKey, true));
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
				int inputKey = -1;
				if(keyCode == KeyEvent.VK_UP)
				{
					inputKey = Input.UP;
				}
				else if(keyCode == KeyEvent.VK_DOWN)
				{
					inputKey = Input.DOWN;
				}
				else if(keyCode == KeyEvent.VK_LEFT)
				{
					inputKey = Input.LEFT;
				}
				else if(keyCode == KeyEvent.VK_RIGHT)
				{
					inputKey = Input.RIGHT;
				}
				else if(keyCode == KeyEvent.VK_ENTER)
				{
					inputKey = Input.ENTER;
				}
				else if(keyCode == KeyEvent.VK_ESCAPE)
				{
					inputKey = Input.ESCAPE;
				}
				
				if(inputKey != -1)
				{
					inputQueue.enqueueInput(new InputQueueItem(inputKey, false));
				}
			}
		};
		
		canvas.requestFocus();
		canvas.setFocusable(true);
		canvas.addKeyListener(adapter);
	}
	
	private MouseQueue mouseInputQueue = new MouseQueue();
	
	private void initMouseListener()
	{
		MouseAdapter adapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int mouseButton = e.getButton();
				int mouseCode = -1;
				if(mouseButton == MouseEvent.BUTTON1)
				{
					mouseCode = Input.MOUSE_LEFT;
				}
				else if(mouseButton == MouseEvent.BUTTON2)
				{
					mouseCode = Input.MOUSE_MIDDLE;
				}
				else if(mouseButton == MouseEvent.BUTTON3)
				{
					mouseCode = Input.MOUSE_RIGHT;
				}
				
				if(mouseCode != -1)
				{
					mouseInputQueue.enqueueInput(new InputQueueItem(mouseCode, true));
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				int mouseButton = e.getButton();
				int mouseCode = -1;
				if(mouseButton == MouseEvent.BUTTON1)
				{
					mouseCode = Input.MOUSE_LEFT;
				}
				else if(mouseButton == MouseEvent.BUTTON2)
				{
					mouseCode = Input.MOUSE_MIDDLE;
				}
				else if(mouseButton == MouseEvent.BUTTON3)
				{
					mouseCode = Input.MOUSE_RIGHT;
				}
				
				if(mouseCode != -1)
				{
					mouseInputQueue.enqueueInput(new InputQueueItem(mouseCode, false));
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				Input.setMousePosition(e.getX(), e.getY());
				Input.mouseMoveTrigger();
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				Input.setMousePosition(e.getX(), e.getY());
				Input.mouseMoveTrigger();
			}
		};
		
		canvas.addMouseListener(adapter);
		canvas.addMouseMotionListener(adapter);
	}
	
	private class FrameClose extends WindowAdapter
	{
		@Override
		public void windowClosing(WindowEvent arg0) {
			isRunning = false;
		}
	}
	
	private Graphics2D getBuffer() {
    	if (graphics == null) {
    		try {
    			graphics = (Graphics2D) strategy.getDrawGraphics();
    		} catch (IllegalStateException e) {
    			return null;
    		}
    	}
    	return graphics;
    }
	
	private boolean updateScreen() {
    	graphics.dispose();
    	graphics = null;
    	try {
    		strategy.show();
    		Toolkit.getDefaultToolkit().sync();
    		return (!strategy.contentsLost());

    	} catch (NullPointerException e) {
    		return true;

    	} catch (IllegalStateException e) {
    		return true;
    	}
    }
	
	public void run() {
    	bgGraphics = (Graphics2D) background.getGraphics();
    	bgGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    	        RenderingHints.VALUE_ANTIALIAS_ON);
    	long fpsWait = (long) (1.0 / 30 * 1000);
    	main: while (isRunning) {
    		long renderStart = System.nanoTime();
    		
    		do {
    			Graphics2D bg = getBuffer();
    			if (!isRunning) {
    				break main;
    			}
    			renderGame(bgGraphics);
    			
    			bg.drawImage(background, 0, 0, width, height, null);
    			
    			bg.dispose();
    		} while (!updateScreen());

    		long renderTime = (System.nanoTime() - renderStart) / 1000000;
    		updateGame(renderTime/1000.0);
    		try {
    			Thread.sleep(Math.max(0, fpsWait - renderTime));
    		} catch (InterruptedException e) {
    			Thread.interrupted();
    			break;
    		}
    		renderTime = (System.nanoTime() - renderStart) / 1000000;

    	}
    	frame.dispose();
    }

    public void updateGame(double deltaTime) {
    	inputQueue.handleQueue();
    	mouseInputQueue.handleQueue();
    	ssm.update(deltaTime);
    	Input.clear();
    } 

    public void renderGame(Graphics2D g) {
    	g.setColor(Color.white);
    	g.fillRect(0, 0, width, height);
    	ssm.render(g);
    }
	
	public static void main(String[] args) {
		new Main();
	}
}
