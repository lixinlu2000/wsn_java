package NHSensor.NHSensorSim.core;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.query.Query;

public class CompositeAlg extends Algorithm {

	public CompositeAlg(Query query) {
		super(query);
		// TODO Auto-generated constructor stub
	}

	public CompositeAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public CompositeAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, statistics);
		// TODO Auto-generated constructor stub
	}

	public CompositeAlg() {
		// TODO Auto-generated constructor stub
	}

	public void initAttachment() {
		// TODO Auto-generated method stub

	}

	public boolean run() {
		// TODO Auto-generated method stub
		return false;
	}

}
