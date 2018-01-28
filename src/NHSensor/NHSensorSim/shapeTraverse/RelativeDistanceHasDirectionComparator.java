package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Comparator;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Position;

public class RelativeDistanceHasDirectionComparator implements Comparator {
	private Position basePosition;
	private int direction;

	public RelativeDistanceHasDirectionComparator(Position basePosition,
			int direction) {
		this.basePosition = basePosition;
		this.direction = direction;
	}

	protected double relativeDistance(Position other) {
		double d1 = other.distance(this.basePosition);
		boolean positive = true;

		switch (direction) {
		case Direction.UP:
			positive = other.getY() >= basePosition.getY();
			break;
		case Direction.RIGHT:
			positive = other.getX() >= basePosition.getX();
			break;
		case Direction.DOWN:
			positive = other.getY() <= basePosition.getY();
			break;
		case Direction.LEFT:
			positive = other.getX() <= basePosition.getX();
			break;
		default:
		}
		if (positive)
			return d1;
		else
			return -d1;
	}

	public int compare(Object arg0, Object arg1) {
		NeighborAttachment n1 = (NeighborAttachment) arg0;
		NeighborAttachment n2 = (NeighborAttachment) arg1;

		double d1 = this.relativeDistance(n1.getPos());
		double d2 = this.relativeDistance(n2.getPos());
		if (d1 > d2)
			return 1;
		else if (d1 < d2)
			return -1;
		else
			return 0;
	}

}
