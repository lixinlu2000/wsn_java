package NHSensor.NHSensorSim.events;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;

public class AncestorMinDepthForCutDetectionInfoEvent extends InformationEvent {
	Vector ancestorMinDepths;

	public AncestorMinDepthForCutDetectionInfoEvent(Vector ancestorMinDepths,
			Algorithm alg) {
		super(alg);
		this.ancestorMinDepths = ancestorMinDepths;
	}

	public Vector getAncestorMinDepths() {
		return ancestorMinDepths;
	}

	public void setAncestorMinDepths(Vector ancestorMinDepths) {
		this.ancestorMinDepths = ancestorMinDepths;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("AncestorMinDepthForCutDetectionInfoEvent  ");
		sb.append(" AncestorMinDepths:");
		for (int i = 0; i < this.ancestorMinDepths.size(); i++) {
			if (i != 0)
				sb.append(",");
			sb.append(this.ancestorMinDepths.elementAt(i));
		}
		return sb.toString();
	}
}
