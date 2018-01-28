package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Node;

public class GetNeighborListEvent extends Event {
	private Node node;

	public GetNeighborListEvent(Node node, Algorithm alg) {
		super(alg);
		this.node = node;
	}

	public void handle() {

	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
}
