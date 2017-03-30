package gamehandle;

import gameobject.Ball;
import gamestate.GameStateManager;
import gui.GUIText;

import input.Input;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import screen.Screen;
import util.AssetManager;
import util.Vector2f;

public class GameHandler {
	
	private Ball whiteBall;
	private NineBallHandler ballHandler;
	
	private boolean turnActivated = true;
	private boolean aimActivated = false;
	
	private int cursorRadius = 10;
	private GUIText turnIndicator;
	
	private Stroke dashedStroke;
	private BufferedImage stickImage;
	
	private Point hitTarget = new Point();
	private boolean adjustingPower = false;
	private float powerLimit = 40f;
	private float powerIncrement = powerLimit/40f;
	private int powerBarLength = 300;
	private float hitPower = 0f;
	
	float stickRotationInRadians = 0f;
	
	public GameHandler() {
		initWhiteBall();
		dashedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		ballHandler = new NineBallHandler(whiteBall);
		turnIndicator = new GUIText("Your Turn", new Font("Arial", Font.BOLD, 30), Color.white);
		turnIndicator.getTransform().position.set(Screen.screenWidth/2, 30f/2);
		stickImage = AssetManager.getInstance().loadImage("cue.png");
	}
	
	private void initWhiteBall()
	{
		whiteBall = new Ball(Color.white, 0){
			@Override
			public void render(Graphics2D g) {
				g.setColor(Color.white);
				int renderX = (int)(transform.position.x - radius);
				int renderY = (int)(transform.position.y - radius);
				g.fillOval(renderX, renderY, (int)radius * 2, (int)radius * 2);
				g.setColor(Color.black);
				g.drawOval(renderX, renderY, (int)radius * 2, (int)radius * 2);
			}
		};
		whiteBall.transform.position.set(200, Screen.screenHeight/2);
	}
	
	public void update()
	{
		if(!turnActivated)
		{
			ballHandler.update();
			if(ballHandler.allBallsHaveStopped())
			{
				turnActivated = true;
				ballHandler.restoreFouledBalls();
			}
		}
		else
		{
			updateTurnMode();
		}
	}
	
	private Ball ballBeingMoved = null;
	
	private void updateTurnMode()
	{
		Vector2f mousePos = new Vector2f(Input.getMousePosition().x, Input.getMousePosition().y);
		if(Input.isMouseDown(Input.MOUSE_LEFT))
		{
			Ball b = ballHandler.mouseHit(mousePos);
			if(b != null && ballBeingMoved == null)
			{
				ballBeingMoved = b;
				aimActivated = false;
			}
			else if(ballBeingMoved == null && ballHandler.isBallClear(whiteBall))
			{
				hitTarget.setLocation(Input.getMousePosition());
				calculateStickRotation();
				aimActivated = true;
			}
		}
		
		if(ballBeingMoved != null)
			ballBeingMoved.transform.position.set(mousePos.x, mousePos.y);
		
		if(Input.mouseUp(Input.MOUSE_LEFT))
		{
			ballBeingMoved = null;
		}
		
		if(aimActivated)
		{
			if(Input.mouseDown(Input.MOUSE_RIGHT))
			{
				adjustingPower = true;
			}
			else if(Input.mouseUp(Input.MOUSE_RIGHT))
			{
				turnActivated = false;
				aimActivated = false;
				adjustingPower = false;
				whiteBall.setMoveable(false);
				
				Vector2f targetVector = new Vector2f(hitTarget.x, hitTarget.y);
				Vector2f directionVector = Vector2f.sub(targetVector, whiteBall.transform.position);
				directionVector.normalize();
				directionVector.mult(hitPower);
				whiteBall.rigidBody.addForce(directionVector);
				
				hitPower = 0;
			}
		}
		
		if(adjustingPower)
		{
			hitPower += powerIncrement;
			if(hitPower > powerLimit)
			{
				hitPower = powerLimit;
				powerIncrement *= -1;
			}
			else if(hitPower < 0)
			{
				hitPower = 0;
				powerIncrement *= -1;
			}
		}
	}
	
	private void calculateStickRotation()
	{
		stickRotationInRadians = (float) Math.atan2(hitTarget.y - whiteBall.transform.position.y, hitTarget.x - whiteBall.transform.position.x);
	}
	
	public void render(Graphics2D g)
	{
		ballHandler.render(g);
		if(turnActivated)
		{
			if(ballHandler.isOver())
				GameStateManager.getInstance().setState(GameStateManager.WIN_STATE);
			turnIndicator.render(g);
			renderTurnMode(g);
			if(adjustingPower)
			{
				renderPowerBar(g);
			}
		}
	}
	
	private void renderPowerBar(Graphics2D g)
	{
		g.setColor(Color.black);
		g.drawRect(0, 0, powerBarLength, 40);
		g.setColor(Color.red);
		g.fillRect(0, 0, (int)(hitPower * powerBarLength/powerLimit), 40);
	}
	
	private void renderTurnMode(Graphics2D g)
	{
		if(aimActivated)
		{
			renderStick(g);
			drawDashedLine(g);
			drawTargetCursor(g);
		}
	}
	
	private void renderStick(Graphics2D g)
	{
		AffineTransform prevTransform = g.getTransform();
		g.rotate(stickRotationInRadians, whiteBall.transform.position.x, whiteBall.transform.position.y);
		g.drawImage(stickImage, (int)(whiteBall.transform.position.x - 530 - hitPower*3), (int)whiteBall.transform.position.y-5, 500, 10, null);
		g.setTransform(prevTransform);
	}
	
	private void drawDashedLine(Graphics2D g)
	{
		Stroke prevStroke = g.getStroke();
		
		g.setColor(Color.black);
		g.setStroke(dashedStroke);
		g.drawLine((int)whiteBall.transform.position.x, (int)whiteBall.transform.position.y, hitTarget.x, hitTarget.y);
		g.setStroke(prevStroke);
	}
	
	private BasicStroke cursorStroke = new BasicStroke(3);
	
	private void drawTargetCursor(Graphics2D g)
	{
		Stroke prevStroke = g.getStroke();
		g.setStroke(cursorStroke);
		g.setColor(Color.red);
		g.drawLine(hitTarget.x - cursorRadius, hitTarget.y, hitTarget.x + cursorRadius, hitTarget.y);
		g.drawLine(hitTarget.x, hitTarget.y - cursorRadius, hitTarget.x, hitTarget.y + cursorRadius);
		g.drawOval(hitTarget.x - cursorRadius, hitTarget.y - cursorRadius, cursorRadius*2, cursorRadius*2);
		g.setStroke(prevStroke);
	}
}
