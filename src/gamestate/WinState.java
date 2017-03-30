package gamestate;

import input.Input;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import screen.Screen;
import util.AssetManager;

public class WinState extends GameState{
	
	BufferedImage img = AssetManager.getInstance().loadImage("win.jpg");
	
	public WinState() {
		
	}
	
	@Override
	public void render(Graphics2D g) {
		
		g.drawImage(img, 0, 0, Screen.screenWidth, Screen.screenHeight, null);
	}
	
	@Override
	public void update(double deltaTime) {
		
		if(Input.keyDown(Input.ESCAPE))
			GameStateManager.getInstance().setState(GameStateManager.MENU_STATE);
	}
}
