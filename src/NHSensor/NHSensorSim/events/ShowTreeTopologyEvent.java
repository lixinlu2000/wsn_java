package NHSensor.NHSensorSim.events;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class ShowTreeTopologyEvent extends Event {
	private Vector nodeAttachments;

	public Vector getNodeAttachments() {
		return nodeAttachments;
	}

	public void setNodeAttachments(Vector nodeAttachments) {
		this.nodeAttachments = nodeAttachments;
	}

	public ShowTreeTopologyEvent(Algorithm alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	public ShowTreeTopologyEvent(Algorithm alg, Vector nodeAttachments) {
		super(alg);
		this.nodeAttachments = nodeAttachments;
	}

	public ShowTreeTopologyEvent(Algorithm alg, int tag) {
		super(alg, tag);
		// TODO Auto-generated constructor stub
	}

	public void handle() throws SensornetBaseException {
	}

}
