package animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AnimationControl {

	private BufferedImage image;
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	private int currentAnimation = 0;

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public ArrayList<Animation> getAnimations() {
		return animations;
	}

	public void setAnimations(ArrayList<Animation> animations) {
		this.animations = animations;
	}
	
	public AnimationControl(BufferedImage image) {
		this.image = image;
	}
	
	public void updateAnimation()
	{
		animations.get(currentAnimation).update();
	}
	
	public void setAnimation(int animation)
	{
		currentAnimation = animation;
	}
	
	public void startAnimation()
	{
		animations.get(currentAnimation).start();
	}
	
	public void stopAnimation()
	{
		animations.get(currentAnimation).stop();
	}
	
	public BufferedImage getAnimationImage()
	{
		Sprite sprite = animations.get(currentAnimation).getCurrentSprite();
		return image.getSubimage(sprite.x, sprite.y, sprite.width, sprite.height);
	}
}
