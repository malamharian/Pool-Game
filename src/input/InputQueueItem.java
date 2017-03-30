package input;

public class InputQueueItem {

	int keyCode;
	boolean isPressed;
	
	public InputQueueItem(int keyCode, boolean isPressed) {
		this.keyCode = keyCode;
		this.isPressed = isPressed;
	}
}
