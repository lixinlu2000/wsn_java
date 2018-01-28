package NHSensor.NHSensorSim.core;

import java.util.Comparator;

public class AttachmentYAdvanceComparator implements Comparator {
	private boolean isDown;

	public AttachmentYAdvanceComparator(boolean isDown) {
		this.isDown = isDown;
	}

	public boolean isDown() {
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}

	public int compare(Object o1, Object o2) {
		double y1 = ((Attachment) o1).getNode().getPos().getY();
		double y2 = ((Attachment) o2).getNode().getPos().getY();
		int result;

		if (y1 < y2) {
			result = -1;
		} else if (y1 == y2) {
			result = 0;
		} else {
			result = 1;
		}

		if (this.isDown())
			result = -result;

		return result;
	}

}
