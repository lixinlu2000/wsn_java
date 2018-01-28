package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class NodeHasQueryResultEvent extends Event {
	private NeighborAttachment node;

	public NodeHasQueryResultEvent(Algorithm alg, NeighborAttachment node) {
		super(alg);
		this.node = node;
	}

	public NodeHasQueryResultEvent(Algorithm alg, int tag) {
		super(alg, tag);
	}

	public void handle() throws SensornetBaseException {

	}

	public NeighborAttachment getNode() {
		return node;
	}

	public void setNode(NeighborAttachment node) {
		this.node = node;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("NodeHasQueryResultEvent  ");
		sb.append(this.getNode().getNode().getPos());
		return sb.toString();

	}
}
