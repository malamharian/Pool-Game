package gameobject;

import util.Vector2f;

public class Transform {

	public Vector2f position;
	public float rotation;
	public Vector2f size;
	
	public Transform() {
		position = new Vector2f();
		size = new Vector2f();
		rotation = 0;
	}
	
	public Transform(Vector2f position)
	{
		this.position = position;
	}
	
	public Transform(Vector2f position, Vector2f size)
	{
		this.position = position;
		this.size = size;
	}
}
