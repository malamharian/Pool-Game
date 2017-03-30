package gameobject;

import gamehandle.PhysicsHelper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import screen.Screen;
import util.Vector2f;

public class Ball extends GameObject {
	
	public static float radius = 18;
	private Color color;
	private int number;
	public static Font ballNumberFont = new Font("Arial", Font.BOLD, 10);
	private boolean moveable;
	
	public Ball(Color color, int number) {
		this.color = color;
		this.number = number;
	}
	
	@Override
	public void update(float tpf) {
		if(rigidBody.velocity.mag() < PhysicsHelper.frictionCoefficient)
			rigidBody.velocity.mult(0);
		else
			rigidBody.addForce(Vector2f.mult(Vector2f.normalize(Vector2f.mult(rigidBody.velocity, -1)), PhysicsHelper.frictionCoefficient));
		rigidBody.update(this.transform);
		checkBounds();
	}
	
	private void checkBounds()
	{
		if(transform.position.x < radius)
		{
			transform.position.x = radius;
			rigidBody.velocity.mult(-PhysicsHelper.wallBounceCoefficient, 1);
		}
		else if(transform.position.x > Screen.screenWidth - radius)
		{
			transform.position.x = Screen.screenWidth - radius;
			rigidBody.velocity.mult(-PhysicsHelper.wallBounceCoefficient, 1);
		}
		
		if(transform.position.y < radius)
		{
			transform.position.y = radius;
			rigidBody.velocity.mult(1, -PhysicsHelper.wallBounceCoefficient);
		}
		else if(transform.position.y > Screen.screenHeight - radius)
		{
			transform.position.y = Screen.screenHeight - radius;
			rigidBody.velocity.mult(1, -PhysicsHelper.wallBounceCoefficient);
		}
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public float getRadius()
	{
		return radius;
	}
	
	public void setMoveable(boolean moveable)
	{
		this.moveable = moveable;
	}
	
	public boolean isMoveable()
	{
		return moveable;
	}
	
	public boolean colliding(Ball ball)
	{
	    float xd = transform.position.x - ball.transform.position.x;
	    float yd = transform.position.y - ball.transform.position.y;

	    float sumRadius = getRadius() + ball.getRadius();
	    float sqrRadius = sumRadius * sumRadius;

	    float distSqr = (xd * xd) + (yd * yd);

	    if (distSqr <= sqrRadius)
	    {
	        return true;
	    }

	    return false;
	}
	
	public boolean isHit(Vector2f hitPos)
	{
		return Vector2f.sub(this.transform.position, hitPos).mag() < Ball.radius;
	}
	
	public void resolveCollision(Ball ball)
	{
		Vector2f delta = Vector2f.sub(this.transform.position, ball.transform.position);
		float m = delta.mag();
		Vector2f minDistance = Vector2f.mult(delta, ((radius + ball.getRadius()) - m)/m);
		
		float inverseMass1 = 1/rigidBody.mass;
		float inverseMass2 = 1/ball.rigidBody.mass;
		
		transform.position.add(Vector2f.mult(minDistance, inverseMass1 / (inverseMass1 + inverseMass2)));
		ball.transform.position.sub(Vector2f.mult(minDistance, inverseMass2 / (inverseMass1 + inverseMass2)));
		
		Vector2f impactSpeed = Vector2f.sub(rigidBody.velocity, ball.rigidBody.velocity);
		impactSpeed.normalize();
		float vn = Vector2f.dot(impactSpeed, Vector2f.normalize(minDistance));
		
		if(vn > 0.0f)
			return;
		
		float i = (-(1.0f + PhysicsHelper.ballConstitution) * vn) / (inverseMass1 + inverseMass2);
		Vector2f impulse = Vector2f.mult(minDistance, i);
		
		rigidBody.velocity = Vector2f.add(rigidBody.velocity, Vector2f.mult(impulse, inverseMass1));
		ball.rigidBody.velocity = Vector2f.sub(ball.rigidBody.velocity, Vector2f.mult(impulse, inverseMass2));
	}
	
	@Override
	public void render(Graphics2D g) {
		drawBall(g);
		drawInnerBall(g);
		g.setFont(ballNumberFont);
		int textWidth = (int)ballNumberFont.getStringBounds(""+number, g.getFontRenderContext()).getWidth();
		g.drawString(""+number, (int)transform.position.x - textWidth/2f, (int)transform.position.y + ballNumberFont.getSize2D()/2f);
	}
	
	private void drawBall(Graphics2D g)
	{
		g.setColor(this.color);
		int renderX = (int)(transform.position.x - radius);
		int renderY = (int)(transform.position.y - radius);
		g.fillOval(renderX, renderY, (int)radius * 2, (int)radius * 2);
		g.setColor(Color.black);
		g.drawOval(renderX, renderY, (int)radius * 2, (int)radius * 2);
	}
	
	private void drawInnerBall(Graphics2D g)
	{
		int whiteX = (int)(transform.position.x - radius/2);
		int whiteY = (int)(transform.position.y - radius/2);
		g.setColor(Color.white);
		g.fillOval(whiteX, whiteY, (int)radius, (int)radius);
		g.setColor(Color.black);
		g.drawOval(whiteX, whiteY, (int)radius, (int)radius);
	}
}
