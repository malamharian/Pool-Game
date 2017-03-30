package gamestate;

import gameobject.Transform;
import gui.GUIComponent;
import gui.GUIText;

import input.Input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import screen.Screen;
import util.AssetManager;
import util.Vector2f;

public class MenuState extends GameState{
	
	private int selectedMenu = 0;
	private GUIText[] menuGuiTexts = new GUIText[4];
	private GUIComponent menuPointer;
	
	public MenuState() {
		initMenuGuiTexts();
		initMenuPointer();
	}
	
	private void initMenuGuiTexts()
	{
		Font menuFont = new Font("Arial", Font.PLAIN, 30);
		
		menuGuiTexts[0] = new GUIText("New game", menuFont, Color.black, new Vector2f(Screen.getCenter().x, 100));
		menuGuiTexts[1] = new GUIText("BONUS", menuFont, Color.black, new Vector2f(Screen.getCenter().x, 200));
		menuGuiTexts[2] = new GUIText("About", menuFont, Color.black, new Vector2f(Screen.getCenter().x, 300));
		menuGuiTexts[3] = new GUIText("Exit", menuFont, Color.black, new Vector2f(Screen.getCenter().x, 400));
		
		for(int i = 0; i<menuGuiTexts.length; i++)
			guiNode.add(menuGuiTexts[i]);
	}
	
	private void initMenuPointer()
	{
		menuPointer = new GUIComponent() {
			@Override
			public void render(Graphics2D g) {
				g.setColor(Color.red);
				g.fillRect((int)(transform.position.getX()-transform.size.x/2), (int)(transform.position.getY()-transform.size.y/2), (int)transform.size.x, (int)transform.size.y);
			}
		};
		menuPointer.setTransform(new Transform(new Vector2f(Screen.getCenter().x - 150, menuGuiTexts[selectedMenu].getTransform().position.y), new Vector2f(40, 40)));
		guiNode.add(menuPointer);
	}
	
	@Override
	public void update(double deltaTime) {
		
		if(Input.keyDown(Input.DOWN))
		{
			selectedMenu = selectedMenu < menuGuiTexts.length-1 ? selectedMenu+1 : 0;
			updateMenuPointerPosition();
		}
		else if(Input.keyDown(Input.UP))
		{
			selectedMenu = selectedMenu > 0 ? selectedMenu-1 : menuGuiTexts.length-1;
			updateMenuPointerPosition();
		}
		else if(Input.keyDown(Input.ENTER))
		{
			if(selectedMenu == 0)
			{
				GameStateManager.getInstance().setState(GameStateManager.PLAY_STATE);
			}
			else if(selectedMenu == 1)
			{
				GameStateManager.getInstance().setState(GameStateManager.BONUS_STATE);
			}
			else if(selectedMenu == 2)
			{
				GameStateManager.getInstance().setState(GameStateManager.ABOUT_STATE);
			}
			else if(selectedMenu == 3)
				System.exit(0);
		}
	}
	
	private int bgX1 = -2, bgX2 = -Screen.screenWidth+2;
	private int bgScrollSpeed = 3;
	
	@Override
	public void render(Graphics2D g) {
		drawBackground(g);
	}
	
	private BufferedImage bgImage = AssetManager.getInstance().loadImage("bg.jpg");
	
	private void drawBackground(Graphics2D g)
	{
		g.drawImage(bgImage, bgX2, 0, Screen.screenWidth, Screen.screenHeight, null);
		g.drawImage(bgImage, bgX1, 0, Screen.screenWidth, Screen.screenHeight, null);
		
		bgX1 += bgScrollSpeed;
		bgX2 += bgScrollSpeed;
		
		if(bgX2 >= -2)
		{
			bgX1 = -2;
			bgX2 = -Screen.screenWidth+2;
		}
	}
	
	private void updateMenuPointerPosition()
	{
		menuPointer.getTransform().position.y = menuGuiTexts[selectedMenu].getTransform().position.y;
	}
}
