package gameobject;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class GameObject {

	public Transform transform = new Transform();
	public RigidBody rigidBody = new RigidBody();
	
	public void update(float tpf) {
		rigidBody.update(transform);
	}
	
	private AffineTransform prevTransform;

	protected void applyRotation(Graphics2D g, float rotation)
	{
		prevTransform = g.getTransform();
		g.rotate(rotation, transform.position.x, transform.position.y);
	}
	
	protected void revertRotation(Graphics2D g)
	{
		g.setTransform(prevTransform);
	}
	
	protected Point getRenderPosition()
	{
		return new Point((int)(transform.position.x - transform.size.x/2), (int)(transform.position.y - transform.size.y/2));
	}
	
	public void render(Graphics2D g) {
		
	}
}
