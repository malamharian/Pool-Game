package util;

public class Vector2f {

	public float x;
	public float y;
	
	public Vector2f()
	{
		x = y = 0;
	}
	
	public int getX()
	{
		return (int)x;
	}
	
	public int getY()
	{
		return (int)y;
	}
	
	public static Vector2f add(Vector2f a, Vector2f b)
	{
		return new Vector2f(a.x+b.x, a.y+b.y);
	}
	
	public static Vector2f sub(Vector2f a, Vector2f b)
	{
		return new Vector2f(a.x-b.x, a.y-b.y);
	}
	
	public static Vector2f mult(Vector2f a, float b)
	{
		return new Vector2f(a.x * b, a.y * b);
	}
	
	public static float dot(Vector2f a, Vector2f b)
	{
		return (a.x * b.x) + (a.y * b.y);
	}
	
	public static Vector2f normalize(Vector2f a)
	{
		Vector2f res = a.copy();
		res.normalize();
		return res;
	}
	
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void add(Vector2f vec)
	{
		x += vec.x;
		y += vec.y;
	}
	
	public float distance(Vector2f vec)
	{
		Vector2f temp = this.copy();
		temp.sub(vec);
		
		return temp.mag();
	}
	
	public void sub(Vector2f vec)
	{
		x -= vec.x;
		y -= vec.y;
	}
	
	public void mult(float n)
	{
		x *= n;
		y *= n;
	}
	
	public void mult(float a, float b)
	{
		x *= a;
		y *= b;
	}
	
	public void div(float n)
	{
		x /= n;
		y /= n;
	}
	
	public Vector2f copy()
	{
		return new Vector2f(x,y);
	}
	
	public float mag()
	{
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public void normalize()
	{
		float mag = mag();
		if(mag != 0)
			div(mag);
	}
}
