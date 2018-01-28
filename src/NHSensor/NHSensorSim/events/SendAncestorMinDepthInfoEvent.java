package NHSensor.NHSensorSim.events;

import java.awt.Color;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;

public class SendAncestorMinDepthInfoEvent extends BroadcastEvent {
	int ancestorMinDepth;

	public SendAncestorMinDepthInfoEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg,
			Color color) {
		super(sender, receiver, message, alg, color);
		// TODO Auto-generated constructor stub
	}

	public SendAncestorMinDepthInfoEvent(BroadcastEvent b) {
		super(b);
		// TODO Auto-generated constructor stub
	}

	public SendAncestorMinDepthInfoEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg) {
		super(sender, receiver, message, alg);
		// TODO Auto-generated constructor stub
	}

	public SendAncestorMinDepthInfoEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg, int tag) {
		super(sender, receiver, message, alg, tag);
		// TODO Auto-generated constructor stub
	}

	public int getAncestorMinDepth() {
		return ancestorMinDepth;
	}

	public void setAncestorMinDepth(int ancestorMinDepth) {
		this.ancestorMinDepth = ancestorMinDepth;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SendAncestorMinDepthInfoEvent  ");
		sb.append("ID(" + sender.getNode().getId() + ") ");
		sb.append(sender.getNode().getPos());
		sb.append("-->");
		sb.append("ID(" + receiver.getNode().getId() + ") ");
		sb.append(this.receiver.getNode().getPos().toString());
		sb.append(" SIZE:");
		// sb.append(this.message.getTotalSize());
		sb.append(this.getMessageSizeForDebug());
		sb.append(" FrameNum:");
		sb.append(this.getPacketFrameNum());

		sb.append(" ConsumedEnergy:");
		sb.append(this.consumedEnergy);
		sb.append(" ConsumedEnergyUtilThisEvent:");
		sb.append(this.consumedEnergyUtilThisEvent);

		sb.append(" OneTimeSuccessProbability:");
		sb.append(this.getOneTimeSuccessProbability());
		sb.append(" Times:");
		sb.append(this.getBroadcastTimes());

		sb.append(" AncestorMinDepth:");
		sb.append(this.ancestorMinDepth);
		return sb.toString();
	}

}
