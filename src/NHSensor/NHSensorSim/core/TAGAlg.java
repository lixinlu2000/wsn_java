package NHSensor.NHSensorSim.core;

import NHSensor.NHSensorSim.algorithm.AbstractTreeAlg;
import NHSensor.NHSensorSim.events.BuildTreeEvent;
import NHSensor.NHSensorSim.events.CollectTreeResultEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;

public class TAGAlg extends AbstractTreeAlg {
	public static final String NAME = "TAG";

	public TAGAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
	}

	public TAGAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, TAGAlg.NAME, statistics);
	}

	public TAGAlg(Query query) {
		super(query);
	}

	public boolean run() {
		// broadcast query
		NodeAttachment root = (NodeAttachment) this.getSpatialQuery().getOrig()
				.getAttachment(this.getName());
		int msgSize = this.getParam().getQUERY_MESSAGE_SIZE();
		Message message = new Message(msgSize, this.getQuery());
		BuildTreeEvent buildTreeEvent = new BuildTreeEvent(root, this
				.getNetwork().getRect(), message, this);
		if (this.isJustCollect())
			buildTreeEvent.setConsumeEnergy(false);

		try {
			this.getSimulator().handle(buildTreeEvent);

			CollectTreeResultEvent cte = new CollectTreeResultEvent(
					buildTreeEvent, this.getSpatialQuery().getRect(), this);
			this.getSimulator().handle(cte);

			this.setAns(cte.getAns());
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		this.getStatistics().setSuccess(true);
		return true;
	}

}
