package input;

import java.util.ArrayList;

public class MouseQueue {

	private ArrayList<InputQueueItem> queueItems = new ArrayList<InputQueueItem>();
	
	public MouseQueue() {
		
	}
	
	public void enqueueInput(InputQueueItem queueItem)
	{
		queueItems.add(queueItem);
	}
	
	public void handleQueue()
	{
		for(int i = queueItems.size()-1; i>=0; i--)
		{
			Input.setMouseStatus(queueItems.get(i).keyCode, queueItems.get(i).isPressed);
			queueItems.remove(i);
		}
	}
}
