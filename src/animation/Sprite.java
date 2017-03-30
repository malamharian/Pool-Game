package animation;

public class Sprite {
	
	public int x;
	public int y;
	public int width;
	public int height;
	public int frameInterval;
	
	public Sprite(int x, int y, int width, int height, int frameInterval) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.frameInterval = frameInterval;
	}
}
