package gui;

import gameobject.Renderable;
import gameobject.Transform;

public abstract class GUIComponent implements Renderable{

	protected Transform transform = new Transform();
	
	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public GUIComponent() {
		
	}
}
