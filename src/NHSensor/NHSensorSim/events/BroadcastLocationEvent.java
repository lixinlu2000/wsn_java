package NHSensor.NHSensorSim.events;

import java.awt.Color;
import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.shape.Shape;

public class BroadcastLocationEvent extends TransferEvent {
	static Logger logger = Logger.getLogger(BroadcastLocationEvent.class);
	NeighborAttachment sender;
	NeighborAttachment receiver;
	Color color = null;
	Shape shape = null;
	double broadcastTimes = 1;
	double consumedEnergyUtilThisEvent = 0;
	double consumedEnergy = 0;
	String info = null;

	public BroadcastLocationEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg,
			Color color) {
		super(message, alg);
		this.receiver = receiver;
		this.sender = sender;
		this.color = color;
	}

	public BroadcastLocationEvent(NeighborAttachment sender, Shape shape,
			Message message, Algorithm alg, Color color) {
		super(message, alg);
		this.receiver = null;
		this.sender = sender;
		this.shape = shape;
		this.color = color;
	}

	public BroadcastLocationEvent(BroadcastLocationEvent b) {
		super(new Message(b.message), b.alg);
		this.receiver = b.receiver;
		this.sender = b.sender;
		this.shape = b.shape;
		this.color = b.color;
		this.success = b.success;
		this.consumeEnergy = b.consumeEnergy;
	}
	
	public double getPacketFrameNum() {
		if (!this.getAlg().isConsiderLinkQuality())
			return this.message.getTotalSize();
		int messageSize = this.message.getTotalSize();
		double framePayloadSize = this.getAlg().getParam()
				.getFramePayloadSize();
		return Math.ceil(messageSize / framePayloadSize);
	}

	public double getPacketFrameSize() {
		return this.getAlg().getParam().getFrameSize();
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		BroadcastLocationEvent.logger = logger;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getBroadcastTimes() {
		return broadcastTimes;
	}

	public void setBroadcastTimes(double broadcastTimes) {
		this.broadcastTimes = broadcastTimes;
	}

	public BroadcastLocationEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg) {
		super(message, alg);
		this.sender = sender;
		this.receiver = receiver;
	}

	public BroadcastLocationEvent(NeighborAttachment sender, Message message,
			Shape shape, Algorithm alg) {
		super(message, alg);
		this.sender = sender;
		this.receiver = null;
		this.shape = shape;
	}

	public BroadcastLocationEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg, int tag) {
		super(message, alg);
		this.sender = sender;
		this.receiver = receiver;
		this.tag = tag;
	}

	public void broadcast(double times) throws HasNoEnergyException {
		this.getAlg().getStatistics().addMaintainLocationsFrameNum(
				this.getPacketFrameNum()*times);
	}

	public void linkAwareHandle() throws HasNoEnergyException {

		if (this.receiver != null) {
			double averageSendTimes = this.getAlg().getNetwork().getSendTimes(
					this.sender.getNode(), this.receiver.getNode());
			this.broadcastTimes = averageSendTimes;
			this.broadcast(averageSendTimes);
		} else if (this.shape != null) {
			AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
					shape);
			Vector neighborsInShape = this.getSender().getNeighbors(
					this.alg.getParam().getRADIO_RANGE(), nairc);

			double broadcastQueryMessageTimes = 0;
			double times;
			for (int i = 0; i < neighborsInShape.size(); i++) {
				times = this.getAlg().getNetwork().getSendTimes(
						this.sender.getNode(),
						((Attachment) neighborsInShape.elementAt(i)).getNode());
				broadcastQueryMessageTimes = Math.max(times,
						broadcastQueryMessageTimes);

			}
			this.broadcastTimes = broadcastQueryMessageTimes;
			this.broadcast(broadcastQueryMessageTimes);
		} else { // broadcast
			Vector neighborsInShape = this.getSender().getNeighbors(
					this.alg.getParam().getRADIO_RANGE());

			double broadcastQueryMessageTimes = 0;
			double times;
			for (int i = 0; i < neighborsInShape.size(); i++) {
				Attachment attachment = (Attachment) neighborsInShape
						.elementAt(i);
				times = this.getAlg().getNetwork().getSendTimes(
						this.sender.getNode(), attachment.getNode());
				broadcastQueryMessageTimes = Math.max(times,
						broadcastQueryMessageTimes);
			}
			this.broadcastTimes = broadcastQueryMessageTimes;
			this.broadcast(broadcastQueryMessageTimes);
		}
	}

	public void linkUnAwareHandle() throws HasNoEnergyException {
		this.getAlg().getStatistics().addMaintainLocationsFrameNum(
				this.getPacketFrameNum());
	}

	public void handle() throws HasNoEnergyException {
		if (this.isConsiderLinkQuality()) {
			this.linkAwareHandle();
		} else {
			this.linkUnAwareHandle();
		}
		this.consumedEnergyUtilThisEvent = this.getAlg().getStatistics()
				.getConsumedEnergy();
	}

	public boolean isConsiderLinkQuality() {
		return this.getAlg().isConsiderLinkQuality();
	}

	public double getRadioRange() {
		return this.getSender().getRadioRange();
	}

	public NeighborAttachment getReceiver() {
		return receiver;
	}

	public void setReceiver(NeighborAttachment receiver) {
		this.receiver = receiver;
	}

	public NeighborAttachment getSender() {
		return sender;
	}

	public void setSender(NeighborAttachment sender) {
		this.sender = sender;
	}

	public Vector getNeighborNodes() {
		Vector nodes = new Vector();
		Vector neighborAttachments = this.getNeighborAttachments();
		Attachment attachment;

		for (int i = 0; i < neighborAttachments.size(); i++) {
			attachment = (Attachment) neighborAttachments.elementAt(i);
			nodes.add(attachment.getNode());
		}
		return nodes;
	}

	public Vector getNeighborAttachments() {
		return this.sender.getNeighbors();
	}

	public double getOneTimeSuccessProbability() {
		double sendAFrameSuccessRate = this.getAlg().getNetwork().getLinkPRR(
				this.sender.getNode(), this.receiver.getNode());
		double sendMessageSuccessRate = Math.pow(sendAFrameSuccessRate, this
				.getPacketFrameNum());
		double totalSuccessRate = sendAFrameSuccessRate
				* sendMessageSuccessRate;
		return totalSuccessRate;
	}

	public double getConsumedEnergyUtilThisEvent() {
		return consumedEnergyUtilThisEvent;
	}

	public double getConsumedEnergy() {
		return consumedEnergy;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (this.info == null) {
			sb.append("BroadcastLocationEvent  ");
		} else {
			sb.append(this.info + " ");
		}
		sb.append("ID(" + sender.getNode().getId() + ") ");
		sb.append(sender.getNode().getPos());
		sb.append("-->");
		if (this.receiver != null) {
			sb.append("ID(" + receiver.getNode().getId() + ") ");
			sb.append(this.receiver.getNode().getPos().toString());
		} else if (this.shape != null) {
			sb.append(this.shape.toString());
		} else {
			sb.append("all neighbors");
		}
		sb.append(" SIZE:");
		// sb.append(this.message.getTotalSize());
		sb.append(this.getMessageSizeForDebug());
		sb.append(" FrameNum:");
		sb.append(this.getPacketFrameNum());

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
