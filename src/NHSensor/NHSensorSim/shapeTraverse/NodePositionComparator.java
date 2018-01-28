package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Comparator;

import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.util.Util;

public class NodePositionComparator implements Comparator {
	private int direction;

	public NodePositionComparator(int direction) {
		super();
		this.direction = direction;
	}

	public int compare(Object arg0, Object arg1) {
		Attachment a1 = (Attachment) arg0;
		Attachment a2 = (Attachment) arg1;

		switch (this.direction) {
		case Direction.LEFT:
			return -Util.sign(a1.getNode().getPos().getX(), a2.getNode()
					.getPos().getX());
		case Direction.RIGHT:
			return Util.sign(a1.getNode().getPos().getX(), a2.getNode()
					.getPos().getX());
		case Direction.UP:
			return Util.sign(a1.getNode().getPos().getY(), a2.getNode()
					.getPos().getY());
		case Direction.DOWN:
			return -Util.sign(a1.getNode().getPos().getY(), a2.getNode()
					.getPos().getY());
		default:
			return 0;
		}
	}
}
