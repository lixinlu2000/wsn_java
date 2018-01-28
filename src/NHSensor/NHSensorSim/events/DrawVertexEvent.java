package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class DrawVertexEvent extends Event {
	private Node vertex;

	public DrawVertexEvent(Algorithm alg, Node vertex) {
		super(alg);
		this.vertex = vertex;
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub
	}

	public Node getVertex() {
		return vertex;
	}

	public void setVertex(Node vertex) {
		this.vertex = vertex;
	}

	public String toString() {
		return "DrawVertex  " + this.vertex.toString();
	}
}
