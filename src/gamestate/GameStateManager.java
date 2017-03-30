package gamestate;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {

	public static int MENU_STATE = 0;
	public static int PLAY_STATE = 1;
	public static int BONUS_STATE = 2;
	public static int ABOUT_STATE = 3;
	public static int WIN_STATE = 4;
	
	private static GameStateManager ssm = new GameStateManager();
	private ArrayList<GameState> screenStates = new ArrayList<GameState>();
	private int currentState = 0;
	
	public GameStateManager() {
		
		screenStates.add(new MenuState());
		screenStates.add(new PlayState());
		screenStates.add(new BonusState());
		screenStates.add(new AboutState());
		screenStates.add(new WinState());
	}
	
	public static GameStateManager getInstance()
	{
		return ssm;
	}
	
	public void setState(int state)
	{
		screenStates.get(currentState).onExit();
		screenStates.get(state).onEnter();
		currentState = state;
	}
	
	public GameState getState()
	{
		return screenStates.get(currentState);
	}
	
	public void render(Graphics2D g)
	{
		screenStates.get(currentState).render(g);
		screenStates.get(currentState).renderGUI(g);
	}
	
	public void update(double deltaTime)
	{
		screenStates.get(currentState).update(deltaTime);
	}
}
