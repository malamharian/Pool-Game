package gameobject;

import util.Vector2f;

public class RigidBody {

	public Vector2f velocity;
	public Vector2f acceleration;
	public float mass;
	
	public RigidBody() {
		velocity = new Vector2f();
		acceleration = new Vector2f();
		mass = 1;
	}
	
	public void addForce(Vector2f force)
	{
		force.div(mass);
		acceleration.add(force);
	}
	
	public void update(Transform transform)
	{
		applyForce();
		transform.position.add(velocity);
	}
	
	private void applyForce()
	{
		velocity.add(acceleration);
		acceleration.mult(0);
	}
}
