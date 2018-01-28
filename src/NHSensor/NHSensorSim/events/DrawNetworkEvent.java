package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class DrawNetworkEvent extends Event {
	private Network network;

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public DrawNetworkEvent(Algorithm alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	public DrawNetworkEvent(Algorithm alg, int tag) {
		super(alg, tag);
		// TODO Auto-generated constructor stub
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub

	}

	public String toString() {
		return "DrawNetwork  " + this.getNetwork().getRect().toString();
	}

}
