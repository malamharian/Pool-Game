package gamestate;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import screen.Screen;
import util.AssetManager;

import input.Input;

public class BonusState extends GameState{

	BufferedImage bg = AssetManager.getInstance().loadImage("bonus.png");
	
	public BonusState() {
		
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(bg, 0, 0, Screen.screenWidth, Screen.screenHeight, null);
	}
	
	@Override
	public void update(double deltaTime) {
		if(Input.keyDown(Input.ESCAPE))
			GameStateManager.getInstance().setState(GameStateManager.MENU_STATE);
	}
}
