package gamestate;

import gamehandle.GameHandler;

import input.Input;

import java.awt.Color;
import java.awt.Graphics2D;

import screen.Screen;

public class PlayState extends GameState{
	
	private GameHandler gameHandler;
	
	public PlayState() {
		
	}
	
	@Override
	public void onEnter() {
		gameHandler = new GameHandler();
	}
	
	@Override
	public void update(double deltaTime) {
		if(Input.keyDown(Input.ESCAPE))
			GameStateManager.getInstance().setState(GameStateManager.MENU_STATE);
		gameHandler.update();
	}
	
	Color tableColor = new Color(65,129,240);
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(tableColor);
		g.fillRect(0, 0, Screen.screenWidth, Screen.screenHeight);
		gameHandler.render(g);
	}
}
