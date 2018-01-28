package NHSensor.NHSensorSim.core;

import NHSensor.NHSensorSim.shape.Rect;

public class GetNextGridResult {
	private Rect nextRect;
	private boolean allNodesReceived;

	public GetNextGridResult(Rect nextRect, boolean allNodesReceived) {
		this.nextRect = nextRect;
		this.allNodesReceived = allNodesReceived;
	}

	public Rect getNextRect() {
		return nextRect;
	}

	public void setNextRect(Rect nextRect) {
		this.nextRect = nextRect;
	}

	public boolean isAllNodesReceived() {
		return allNodesReceived;
	}

	public void setAllNodesReceived(boolean allNodesReceived) {
		this.allNodesReceived = allNodesReceived;
	}

}
