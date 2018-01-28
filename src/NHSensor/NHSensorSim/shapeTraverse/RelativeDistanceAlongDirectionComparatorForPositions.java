package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Comparator;

import NHSensor.NHSensorSim.shape.Position;

public class RelativeDistanceAlongDirectionComparatorForPositions implements
		Comparator {
	private Position basePosition;
	private double radian;

	public RelativeDistanceAlongDirectionComparatorForPositions(
			Position basePosition, double radian) {
		this.basePosition = basePosition;
		this.radian = radian;
	}

	public Position getBasePosition() {
		return basePosition;
	}

	public void setBasePosition(Position basePosition) {
		this.basePosition = basePosition;
	}

	public double getRadian() {
		return radian;
	}

	public void setRadian(double radian) {
		this.radian = radian;
	}

	public int compare(Object arg0, Object arg1) {
		Position p1 = (Position) arg0;
		Position p2 = (Position) arg1;

		double d1 = this.basePosition.distance(p1, radian);
		double d2 = this.basePosition.distance(p2, radian);

		int result;
		if (d1 > d2)
			result = 1;
		else if (d1 < d2)
			result = -1;
		else
			result = 0;

		return result;
	}

}
