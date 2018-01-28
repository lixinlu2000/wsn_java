package NHSensor.NHSensorSim.algorithm;

import java.awt.Color;

import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.DrawNodeEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToRectEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.shape.Rect;

public class EcstaAlg extends SWinFloodAlg {
	public final static String NAME = "Ecsta";

	public EcstaAlg(Query query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, EcstaAlg.NAME, statistics);
	}

	public EcstaAlg(Query query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, EcstaAlg.NAME, statistics);
	}

	/*
	 * @return whether can find coordinator node?
	 */
	public boolean greedyForward() throws SensornetBaseException {
		Node node = this.getSpatialQuery().getOrig();
		NodeAttachment cur = (NodeAttachment) node
				.getAttachment(this.getName());

		Rect rect = this.getSpatialQuery().getRect();

		Message msg = new Message(param.getQUERY_MESSAGE_SIZE(), query);
		GreedyForwardToRectEvent event = new GreedyForwardToRectEvent(cur,
				rect, msg, this);
		this.getSimulator().handle(event);

		this.setCoordinatorNode((NodeAttachment) event.getLastNode());
		this.getSimulator().handle(new DrawNodeEvent(this, this.getCoordinatorNode().getNode(),
				3, Color.green));

		return event.isSuccess();
	}

}
