package NHSensor.NHSensorSim.core;

import NHSensor.NHSensorSim.shape.Rect;

public class NeighborAttachmentInRectCondition implements Condition {
	protected Rect rect;

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}

	public NeighborAttachmentInRectCondition(Rect rect) {
		this.rect = rect;
	}

	public boolean isTrue(Object neighborAttachment) {
		NeighborAttachment na = (NeighborAttachment) neighborAttachment;
		if (na.getNode().getPos().in(rect))
			return true;
		else
			return false;
	}

}
