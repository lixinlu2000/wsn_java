package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Comparator;

import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.util.Util;

public class NodeDirectionDistanceComparator implements Comparator {
	private int direction;
	private Position position;

	public NodeDirectionDistanceComparator(int direction, Position position) {
		super();
		this.direction = direction;
		this.position = position;
	}

	public int compare(Object arg0, Object arg1) {
		Position p1 = ((Attachment) arg0).getNode().getPos();
		Position p2 = ((Attachment) arg1).getNode().getPos();

		switch (this.direction) {
		case Direction.LEFT:
		case Direction.RIGHT:
			return Util.sign(p1.xDistance(position), p2.xDistance(position));
		case Direction.UP:
		case Direction.DOWN:
			return Util.sign(p1.yDistance(position), p2.yDistance(position));
		default:
			return 0;
		}
	}
}