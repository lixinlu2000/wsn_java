package NHSensor.NHSensorSim.events;

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;

public class BroadcastEventLinkUnAware extends BroadcasBaseEvent {
	static Logger logger = Logger.getLogger(BroadcastEventLinkUnAware.class);

	public BroadcastEventLinkUnAware(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg,
			Color color) {
		super(sender, receiver, message, alg, color);
	}

	public BroadcastEventLinkUnAware(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg) {
		super(sender, receiver, message, alg);
	}

	public BroadcastEventLinkUnAware(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg, int tag) {
		super(sender, receiver, message, alg, tag);
	}

	public void handle() throws HasNoEnergyException {
		if (!this.isConsumeEnergy()) {
			logger.debug("cannot consume energy");
			return;
		}
		double et = this.getAlg().getParam().getTEnergy(this.message,
				this.sender.getRadioRange());
		double er = this.getAlg().getParam().getREnergy(message);
		NeighborAttachment neighbor;

		sender.consumeEnergy(et);
		// na.setReceivedQID(qid);
		this.getAlg().getStatistics().addConsumedEnergy(et);

		for (Iterator it = sender.getNeighbors().iterator(); it.hasNext();) {
			neighbor = (NeighborAttachment) it.next();
			neighbor.consumeEnergy(er);
			// nba = (NodeAttachment)neighbor.getAttachment("SWinFloodAlg");
			// nba.setReceivedQID(qid);
			this.getAlg().getStatistics().addConsumedEnergy(er);
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("BroadcastEvent  ");
		sb.append(sender.getNode().getPos());
		sb.append("-->");
		sb.append(this.receiver != null ? this.receiver.getNode().getPos()
				.toString() : "all neighbors");
		sb.append(" SIZE:");
		sb.append(this.message.getTotalSize());
		return sb.toString();
	}
}
