package NHSensor.NHSensorSim.algorithm;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.query.Query;

public class TreeAlg extends Algorithm {
	public final static String NAME = "Tree";

	public TreeAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public TreeAlg(Query query) {
		super(query);
	}

	public TreeAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, TreeAlg.NAME, statistics);
		// TODO Auto-generated constructor stub
	}

	public TreeAlg() {
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
