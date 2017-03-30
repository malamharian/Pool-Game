package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import util.Vector2f;

public class GUIText extends GUIComponent{

	private String text = "";
	private Color color = Color.black;
	private Font font = new Font("Arial", Font.PLAIN, 10);
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
	
	public GUIText(String text) {
		this.text = text;
	}
	
	public GUIText() {
		
	}
	
	public GUIText(String text, Font font)
	{
		this.text = text;
		this.font = font;
	}
	
	public GUIText(String text, Font font, Color color)
	{
		this.text = text;
		this.font = font;
		this.color = color;
	}
	
	public GUIText(String text, Font font, Color color, Vector2f position)
	{
		this.text = text;
		this.font = font;
		this.color = color;
		this.transform.position = position;
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(this.color);
		g.setFont(this.font);
		int textWidth = (int)font.getStringBounds(text, g.getFontRenderContext()).getWidth();
		g.drawString(this.text, transform.position.x - textWidth/2f, transform.position.y + font.getSize2D()/2f);
	}
}
