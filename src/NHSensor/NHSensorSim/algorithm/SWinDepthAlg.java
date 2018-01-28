package NHSensor.NHSensorSim.algorithm;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.query.Query;

public class SWinDepthAlg extends Algorithm {
	protected NodeAttachment coordinatorNode; // coordinator node;
	public final static String NAME = "SWinDepth";

	public SWinDepthAlg(Query query) {
		super(query);
	}

	public SWinDepthAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public SWinDepthAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, SWinDepthAlg.NAME, statistics);
	}

	public void initAttachment() {
		// TODO Auto-generated method stub

	}

	public boolean run() {
		return false;
	}

}
