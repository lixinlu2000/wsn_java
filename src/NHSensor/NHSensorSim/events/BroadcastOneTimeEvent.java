package NHSensor.NHSensorSim.events;

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.shape.Shape;

public class BroadcastOneTimeEvent extends TransferEvent {
	static Logger logger = Logger.getLogger(BroadcastOneTimeEvent.class);

	NeighborAttachment sender;
	NeighborAttachment receiver;
	Color color = null;
	Shape shape = null;
	double broadcastTimes = 1;

	public BroadcastOneTimeEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg,
			Color color) {
		super(message, alg);
		this.receiver = receiver;
		this.sender = sender;
		this.color = color;
	}

	public BroadcastOneTimeEvent(NeighborAttachment sender, Shape shape,
			Message message, Algorithm alg, Color color) {
		super(message, alg);
		this.receiver = null;
		this.sender = sender;
		this.shape = shape;
		this.color = color;
	}

	private double getPacketFrameNum() {
		int messageSize = this.message.getTotalSize();
		double framePayloadSize = this.getAlg().getParam()
				.getFramePayloadSize();
		return Math.ceil(messageSize / framePayloadSize);
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
		BroadcastOneTimeEvent.logger = logger;
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

	public BroadcastOneTimeEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg) {
		super(message, alg);
		this.sender = sender;
		this.receiver = receiver;
	}

	public BroadcastOneTimeEvent(NeighborAttachment sender, Message message,
			Shape shape, Algorithm alg) {
		super(message, alg);
		this.sender = sender;
		this.receiver = null;
		this.shape = shape;
	}

	public BroadcastOneTimeEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg, int tag) {
		super(message, alg);
		this.sender = sender;
		this.receiver = receiver;
		this.tag = tag;
	}

	public void broadcast(double times) throws HasNoEnergyException {
		this.getAlg().getStatistics().countPacketFrame(
				this.getPacketFrameNum(), times);

		if (!this.isConsumeEnergy()) {
			logger.debug("cannot consume energy");
			return;
		}

		double et = this.getAlg().getParam().getTEnergy(this.message,
				this.sender.getRadioRange());
		double er = this.getAlg().getParam().getREnergy(message);
		NeighborAttachment neighbor;

		sender.consumeEnergy(et * times);
		// na.setReceivedQID(qid);
		this.getAlg().getStatistics().addConsumedEnergy(et * times);

		for (Iterator it = sender.getNeighbors().iterator(); it.hasNext();) {
			neighbor = (NeighborAttachment) it.next();
			neighbor.consumeEnergy(er * times);
			// nba = (NodeAttachment)neighbor.getAttachment("SWinFloodAlg");
			// nba.setReceivedQID(qid);
			this.getAlg().getStatistics().addConsumedEnergy(er * times);
		}
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
				if (!attachment.isHasSendAnswer()) {
					times = this.getAlg().getNetwork().getSendTimes(
							this.sender.getNode(), attachment.getNode());
					broadcastQueryMessageTimes = Math.max(times,
							broadcastQueryMessageTimes);
				}
			}
			this.broadcastTimes = broadcastQueryMessageTimes;
			this.broadcast(broadcastQueryMessageTimes);
		}
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
		return this.sender.getNeighbors(this.getRadioRange());
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("BroadcastEvent  ");
		sb.append(sender.getNode().getPos());
		sb.append("-->");
		if (this.receiver != null) {
			sb.append(this.receiver.getNode().getPos().toString());
		} else if (this.shape != null) {
			sb.append(this.shape.toString());
		} else {
			sb.append("all neighbors");
		}
		sb.append(" SIZE:");
		sb.append(this.message.getTotalSize());
		sb.append(" Times:");
		sb.append(this.getBroadcastTimes());
		return sb.toString();
	}

}
