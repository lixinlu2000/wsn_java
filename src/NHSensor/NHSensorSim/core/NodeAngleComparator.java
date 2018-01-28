package NHSensor.NHSensorSim.core;

import java.util.Comparator;

import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Position;

public class NodeAngleComparator implements Comparator {
	private Position centre;

	public NodeAngleComparator(Position centre) {
		this.centre = centre;
	}

	public Position getCentre() {
		return centre;
	}

	public void setCentre(Position centre) {
		this.centre = centre;
	}

	public int compare(Object arg0, Object arg1) {
		GPSRAttachment node0 = (GPSRAttachment) arg0;
		GPSRAttachment node1 = (GPSRAttachment) arg1;

		double angle0 = this.getCentre().bearing(node0.getNode().getPos());
		double angle1 = this.getCentre().bearing(node1.getNode().getPos());

		if (angle0 < angle1)
			return -1;
		else if (angle0 == angle1)
			return 0;
		else
			return 1;
	}

}
