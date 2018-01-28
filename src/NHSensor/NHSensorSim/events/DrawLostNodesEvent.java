package NHSensor.NHSensorSim.events;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class DrawLostNodesEvent extends Event {
	private Vector lostNodes;

	public DrawLostNodesEvent(Algorithm alg, Vector lostNodes) {
		super(alg);
		this.lostNodes = lostNodes;
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub
	}

	public Vector getLostNodes() {
		return lostNodes;
	}

	public void setLostNodes(Vector lostNodes) {
		this.lostNodes = lostNodes;
	}

	public String toString() {
		return "DrawLostNodes  " + this.lostNodes.toString();
	}
}
