package NHSensor.NHSensorSim.events;

import java.util.Iterator;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;

public class ComplexBroadcastEvent extends BroadcastEvent {
	private double radioRange;

	public ComplexBroadcastEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, double radioRange,
			Algorithm alg) {
		super(sender, receiver, message, alg);
		this.radioRange = radioRange;
	}

	public void handle() throws HasNoEnergyException {
		if (!this.isConsumeEnergy())
			return;
		double et = this.getAlg().getParam().getTEnergy(this.message,
				this.radioRange);
		double er = this.getAlg().getParam().getREnergy(message);
		NeighborAttachment neighbor;

		sender.consumeEnergy(et);
		// na.setReceivedQID(qid);
		this.getAlg().getStatistics().addConsumedEnergy(et);

		for (Iterator it = this.getNeighborAttachments().iterator(); it
				.hasNext();) {
			neighbor = (NeighborAttachment) it.next();
			neighbor.consumeEnergy(er);
			// nba = (NodeAttachment)neighbor.getAttachment("SWinFloodAlg");
			// nba.setReceivedQID(qid);
			this.getAlg().getStatistics().addConsumedEnergy(er);
		}
	}

	public double getRadioRange() {
		return radioRange;
	}

	public void setRadioRange(double radioRange) {
		this.radioRange = radioRange;
	}
}
