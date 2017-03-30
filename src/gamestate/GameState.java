package gamestate;

import gui.GUIComponent;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameState {

	protected ArrayList<GUIComponent> guiNode = new ArrayList<GUIComponent>();
	
	public ArrayList<GUIComponent> getGuiComponents() {
		return guiNode;
	}
	public void setGuiComponents(ArrayList<GUIComponent> guiComponents) {
		this.guiNode = guiComponents;
	}
	
	public void renderGUI(Graphics2D g)
	{
		for(GUIComponent gui : guiNode)
			gui.render(g);
	}
	
	public void onExit(){};
	public void onEnter(){};
	public void update(double deltaTime){};
	public void render(Graphics2D g){};
}
