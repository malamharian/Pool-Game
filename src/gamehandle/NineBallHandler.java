package gamehandle;

import gameobject.Ball;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import screen.Screen;
import util.Vector2f;

public class NineBallHandler {
	
	
	private ArrayList<Ball> balls = new ArrayList<Ball>();
	private Vector2f centerPosition = new Vector2f(1000, Screen.screenHeight/2);
	private Vector2f holes[] = new Vector2f[6];
	private int holeRadius = 50;
	private Ball whiteBall;
	
	public NineBallHandler(Ball whiteBall) {
		this.whiteBall = whiteBall;
		balls.add(new Ball(Color.yellow, 1));
		balls.add(new Ball(Color.blue, 2));
		balls.add(new Ball(Color.red, 3));
		balls.add(new Ball(Color.magenta, 4));
		balls.add(new Ball(new Color(255, 100, 0), 5));
		balls.add(new Ball(Color.green, 6));
		balls.add(new Ball(new Color(125, 0, 0), 7));
		balls.add(new Ball(Color.black, 8));
		balls.add(new Ball(new Color(0, 255, 136), 9));
		balls.add(whiteBall);
		
		balls.get(8).transform.position.set(centerPosition.x, centerPosition.y);
		balls.get(4).transform.position.set(centerPosition.x-Ball.radius*2, centerPosition.y+Ball.radius);
		balls.get(3).transform.position.set(centerPosition.x-Ball.radius*2, centerPosition.y-Ball.radius);
		balls.get(5).transform.position.set(centerPosition.x+Ball.radius*2, centerPosition.y+Ball.radius);
		balls.get(6).transform.position.set(centerPosition.x+Ball.radius*2, centerPosition.y-Ball.radius);
		balls.get(0).transform.position.set(centerPosition.x-Ball.radius*4, centerPosition.y);
		balls.get(1).transform.position.set(centerPosition.x+Ball.radius*4, centerPosition.y);
		balls.get(7).transform.position.set(centerPosition.x, centerPosition.y+Ball.radius*2);
		balls.get(2).transform.position.set(centerPosition.x, centerPosition.y-Ball.radius*2);
		
		holes[0] = new Vector2f(0, 0);
		holes[1] = new Vector2f(Screen.screenWidth/2, 0);
		holes[2] = new Vector2f(Screen.screenWidth, 0);
		holes[3] = new Vector2f(0, Screen.screenHeight);
		holes[4] = new Vector2f(Screen.screenWidth/2, Screen.screenHeight);
		holes[5] = new Vector2f(Screen.screenWidth, Screen.screenHeight);
	}
	
	public void render(Graphics2D g)
	{
		g.setColor(Color.black);
		for(int i = 0; i<holes.length; i++)
		{
			g.fillOval((int)holes[i].x - holeRadius, (int)holes[i].y - holeRadius, holeRadius*2, holeRadius*2);
		}
		
		for(Ball b : balls)
			b.render(g);
	}
	
	public void update()
	{
		updateBallPhysics();
		checkHoles();
	}
	
	private void updateBallPhysics()
	{
		for(int i = 0; i<balls.size(); i++)
		{
			balls.get(i).update(0);
		}
		
		int size = balls.size();
		for(int i = 0; i<size-1; i++)
		{
			for(int j = i+1; j<size; j++)
			{
				if(balls.get(i).colliding(balls.get(j)))
				{
					balls.get(i).resolveCollision(balls.get(j));
				}
			}
		}
	}
	
	private boolean whiteBallRemoved;
	
	private void checkHoles()
	{
		for(int k = balls.size()-1; k>=0; k--)
		{
			for(int i = 0; i<holes.length; i++)
			{
				Vector2f distanceVector = Vector2f.sub(balls.get(k).transform.position, holes[i]);
				if(distanceVector.mag() < holeRadius)
				{
					if(balls.get(k).equals(whiteBall))
						whiteBallRemoved = true;
					balls.remove(k);
					break;
				}
			}
		}
	}
	
	public boolean isBallClear(Ball b)
	{
		for(int i = 0, l = balls.size(); i<l; i++)
		{
			Ball b2 = balls.get(i);
			if(b.equals(b2))
				continue;
			
			if(Vector2f.sub(b.transform.position, b2.transform.position).mag() < Ball.radius*2 - 3)
			{
				return false;
			}
		}
		
		for(int i = 0, l = holes.length; i<l; i++)
		{
			if(Vector2f.sub(b.transform.position, holes[i]).mag() < holeRadius)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public Ball mouseHit(Vector2f hitPos)
	{
		for(int i = 0, l = balls.size(); i < l; i++)
		{
			Ball b = balls.get(i);
			if(b.isHit(hitPos) && b.isMoveable())
				return b;
		}
		
		return null;
	}
	
	public void restoreFouledBalls()
	{
		if(whiteBallRemoved)
		{
			balls.add(balls.size(), whiteBall);
			whiteBall.transform.position.set(200, Screen.screenHeight/2);
			whiteBall.rigidBody.velocity.mult(0);
			whiteBall.setMoveable(true);
			whiteBallRemoved = false;
		}
	}
	
	public boolean allBallsHaveStopped()
	{
		for(Ball b : balls)
		{
			if(b.rigidBody.velocity.x != 0 || b.rigidBody.velocity.y != 0)
				return false;
		}
		
		return true;
	}
	
	public boolean isOver()
	{
		return balls.size() == 1;
	}
}
