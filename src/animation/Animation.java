package animation;

import java.util.ArrayList;

public class Animation {

	public ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private int currentSprite = 0;
	private boolean animationStarted = false;
	private int frameCount = 0;
	
	public Animation() {
		
	}
	
	public void update()
	{
		frameCount++;
		if(frameCount == sprites.get(currentSprite).frameInterval)
		{
			frameCount = 0;
			currentSprite++;
			currentSprite %= sprites.size();
		}
	}
	
	public void start()
	{
		if(!animationStarted)
		{
			currentSprite = 0;
			animationStarted = true;
		}
	}
	
	public void stop()
	{
		animationStarted = false;
	}
	
	public Sprite getCurrentSprite()
	{
		return sprites.get(currentSprite);
	}
}
