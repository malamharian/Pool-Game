package util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class AssetManager {

	private static AssetManager assetManager = new AssetManager();
	
	public AssetManager() {
		
	}
	
	public static AssetManager getInstance()
	{
		return assetManager;
	}
	
	public BufferedImage loadImage(String path)
	{
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}
}
