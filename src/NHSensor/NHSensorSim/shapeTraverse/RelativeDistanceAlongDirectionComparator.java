package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Comparator;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.SectorDirection;

public class RelativeDistanceAlongDirectionComparator implements Comparator {
	private Position basePosition;
	private double radian;

	public RelativeDistanceAlongDirectionComparator(Position basePosition,
			double radian) {
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
		NeighborAttachment n1 = (NeighborAttachment) arg0;
		NeighborAttachment n2 = (NeighborAttachment) arg1;

		double d1 = this.basePosition.distance(n1.getPos(), radian);
		double d2 = this.basePosition.distance(n2.getPos(), radian);

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
