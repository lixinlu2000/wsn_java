package NHSensor.NHSensorSim.algorithm;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BuildGreedyTreeEvent;
import NHSensor.NHSensorSim.events.CollectTreeResultEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;

public class GreedyTAGAlg extends AbstractTreeAlg {
	public static final String NAME = "GreedyTAG";
	private double radioRange;

	public double getRadioRange() {
		return radioRange;
	}

	public void setRadioRange(double radioRange) {
		this.radioRange = radioRange;
	}

	public GreedyTAGAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public GreedyTAGAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, GreedyTAGAlg.NAME, statistics);
	}

	public GreedyTAGAlg(Query query) {
		this.query = query;
	}

	public boolean run() {
		// broadcast query
		NodeAttachment root = (NodeAttachment) this.getSpatialQuery().getOrig()
				.getAttachment(this.getName());
		int msgSize = this.getParam().getQUERY_MESSAGE_SIZE();
		Message message = new Message(msgSize, this.getQuery());
		BuildGreedyTreeEvent bgte = new BuildGreedyTreeEvent(this
				.getRadioRange(), root, this.getSpatialQuery().getRect(),
				message, this);
		if (this.isJustCollect())
			bgte.setConsumeEnergy(false);

		try {
			this.getSimulator().handle(bgte);
			CollectTreeResultEvent cte = new CollectTreeResultEvent(bgte, this
					.getSpatialQuery().getRect(), this);
			this.getSimulator().handle(cte);
			this.setAns(cte.getAns());
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
