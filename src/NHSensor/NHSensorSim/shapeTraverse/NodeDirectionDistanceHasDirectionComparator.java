package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Comparator;

import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.util.Util;

public class NodeDirectionDistanceHasDirectionComparator implements Comparator {
	private int direction;
	private Position position;

	public NodeDirectionDistanceHasDirectionComparator(int direction,
			Position position) {
		super();
		this.direction = direction;
		this.position = position;
	}

	protected double directionDistance(Position other) {
		double delta = 0;

		switch (this.direction) {
		case Direction.LEFT:
			delta = this.position.getX() - other.getX();
			break;
		case Direction.RIGHT:
			delta = other.getX() - this.position.getX();
			break;
		case Direction.UP:
			delta = other.getY() - this.position.getY();
			break;
		case Direction.DOWN:
			delta = this.position.getY() - other.getY();
			break;
		default:
		}
		return delta;
	}

	public int compare(Object arg0, Object arg1) {
		Position p1 = ((Attachment) arg0).getNode().getPos();
		Position p2 = ((Attachment) arg1).getNode().getPos();

		double d1 = this.directionDistance(p1);
		double d2 = this.directionDistance(p2);
		return Util.sign(d1, d2);
	}
}