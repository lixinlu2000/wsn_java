package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;

public class RouteProtocolEndEvent extends Event {
	static Logger logger = Logger.getLogger(RouteProtocolEndEvent.class);

	NeighborAttachment neighborAttachment;
	boolean isRoutingSucceed;

	public RouteProtocolEndEvent(NeighborAttachment na,
			boolean isRoutingSucceed, Algorithm alg) {
		super(alg);
		this.neighborAttachment = na;
		this.isRoutingSucceed = isRoutingSucceed;
	}

	public void handle() throws HasNoEnergyException {
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("RouteProtocolEndEvent  ");
		sb.append(neighborAttachment.getNode().getPos());
		sb.append(" " + this.isRoutingSucceed);
		sb.append(" end routing");
		return sb.toString();
	}

}
