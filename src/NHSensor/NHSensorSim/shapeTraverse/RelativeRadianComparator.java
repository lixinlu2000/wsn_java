package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Comparator;

import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Radian;

public class RelativeRadianComparator implements Comparator {
	private Position centre;
	private double baseRadian;

	public RelativeRadianComparator(Position centre, double baseRadian) {
		this.centre = centre;
		this.baseRadian = baseRadian;
	}

	public int compare(Object arg0, Object arg1) {
		NeighborAttachment n1 = (NeighborAttachment) arg0;
		NeighborAttachment n2 = (NeighborAttachment) arg1;

		double relativeRadian1 = Radian.relativeTo(centre.bearing(n1.getNode()
				.getPos()), baseRadian);
		double relativeRadian2 = Radian.relativeTo(centre.bearing(n2.getNode()
				.getPos()), baseRadian);
		if (relativeRadian1 > relativeRadian2)
			return 1;
		else if (relativeRadian1 < relativeRadian2)
			return -1;
		else
			return 0;
	}

}
