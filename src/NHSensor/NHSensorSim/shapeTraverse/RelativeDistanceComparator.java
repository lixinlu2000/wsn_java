package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Comparator;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Position;

public class RelativeDistanceComparator implements Comparator {
	private Position basePosition;

	public RelativeDistanceComparator(Position basePosition) {
		this.basePosition = basePosition;
	}

	public int compare(Object arg0, Object arg1) {
		NeighborAttachment n1 = (NeighborAttachment) arg0;
		NeighborAttachment n2 = (NeighborAttachment) arg1;

		double d1 = n1.getPos().distance(this.basePosition);
		double d2 = n2.getPos().distance(this.basePosition);
		if (d1 > d2)
			return 1;
		else if (d1 < d2)
			return -1;
		else
			return 0;
	}

}
