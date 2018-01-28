package NHSensor.NHSensorSim.events;

import java.awt.Color;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;

public class CVDProbeEvent extends BroadcastEvent {

	public CVDProbeEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg,
			Color color) {
		super(sender, receiver, message, alg, color);
		// TODO Auto-generated constructor stub
	}

	public CVDProbeEvent(BroadcastEvent b) {
		super(b);
		// TODO Auto-generated constructor stub
	}

	public CVDProbeEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg) {
		super(sender, receiver, message, alg);
		// TODO Auto-generated constructor stub
	}

	public CVDProbeEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg, int tag) {
		super(sender, receiver, message, alg, tag);
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CVDProbeEvent  ");
		sb.append("ID(" + sender.getNode().getId() + ") ");
		sb.append(sender.getNode().getPos());
		sb.append("-->");
		if (this.receiver != null) {
			sb.append("ID(" + receiver.getNode().getId() + ") ");
			sb.append(this.receiver.getNode().getPos().toString());
		} else {
			sb.append("all neighbors");
		}
		sb.append(" SIZE:");
		// sb.append(this.message.getTotalSize());
		sb.append(this.getMessageSizeForDebug());
		sb.append(" FrameNum:");
		sb.append(this.getPacketFrameNum());

		sb.append(" ConsumedEnergy:");
		sb.append(this.consumedEnergy);
		sb.append(" ConsumedEnergyUtilThisEvent:");
		sb.append(this.consumedEnergyUtilThisEvent);

		if (this.receiver != null) {
			sb.append(" OneTimeSuccessProbability:");
			sb.append(this.getOneTimeSuccessProbability());
		}
		sb.append(" Times:");
		sb.append(this.getBroadcastTimes());

		return sb.toString();
	}

}
