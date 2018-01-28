package NHSensor.NHSensorSim.events;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;

public class FriendsForCutDetectionInfoEvent extends InformationEvent {
	Vector startEndObjects;

	public FriendsForCutDetectionInfoEvent(Vector startEndObjects, Algorithm alg) {
		super(alg);
		this.startEndObjects = startEndObjects;
	}

	public Vector getStartEndObjects() {
		return startEndObjects;
	}

	public void setStartEndObjects(Vector startEndObjects) {
		this.startEndObjects = startEndObjects;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("FriendsForCutDetectionInfoEvent  ");
		sb.append(" Friends:");
		for (int i = 0; i < this.startEndObjects.size(); i++) {
			if (i != 0)
				sb.append(",");
			sb.append(this.startEndObjects.elementAt(i));
		}
		return sb.toString();
	}
}
