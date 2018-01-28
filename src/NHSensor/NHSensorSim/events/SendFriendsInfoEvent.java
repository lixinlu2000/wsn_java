package NHSensor.NHSensorSim.events;

import java.awt.Color;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;

public class SendFriendsInfoEvent extends BroadcastEvent {
	Vector startEndObjects;

	public SendFriendsInfoEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg,
			Color color) {
		super(sender, receiver, message, alg, color);
		// TODO Auto-generated constructor stub
	}

	public SendFriendsInfoEvent(BroadcastEvent b) {
		super(b);
		// TODO Auto-generated constructor stub
	}

	public SendFriendsInfoEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg) {
		super(sender, receiver, message, alg);
		// TODO Auto-generated constructor stub
	}

	public SendFriendsInfoEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg, int tag) {
		super(sender, receiver, message, alg, tag);
		// TODO Auto-generated constructor stub
	}

	public Vector getStartEndObjects() {
		return startEndObjects;
	}

	public void setStartEndObjects(Vector startEndObjects) {
		this.startEndObjects = startEndObjects;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SendFriendsInfoEvent  ");
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

		sb.append(" Friends:");
		for (int i = 0; i < this.startEndObjects.size(); i++) {
			if (i != 0)
				sb.append(",");
			sb.append(this.startEndObjects.elementAt(i));
		}
		return sb.toString();
	}

}
