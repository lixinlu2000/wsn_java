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

public class BroadcastEventLinkAware extends BroadcasBaseEvent {
	static Logger logger = Logger.getLogger(BroadcastEventLinkAware.class);

	public BroadcastEventLinkAware(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg,
			Color color) {
		super(sender, receiver, message, alg, color);
	}

	public BroadcastEventLinkAware(NeighborAttachment sender, Shape shape,
			Message message, Algorithm alg, Color color) {
		super(sender, shape, message, alg, color);
	}

	public double getPacketFrameNum() {
		int messageSize = this.message.getTotalSize();
		double framePayloadSize = this.getAlg().getParam()
				.getFramePayloadSize();
		return Math.ceil(messageSize / framePayloadSize);
	}

	public double getPacketFrameSize() {
		return this.getAlg().getParam().getFrameSize();
	}

	public BroadcastEventLinkAware(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg) {
		super(sender, receiver, message, alg);
	}

	public BroadcastEventLinkAware(NeighborAttachment sender, Message message,
			Shape shape, Algorithm alg) {
		super(sender, message, shape, alg);
	}

	public BroadcastEventLinkAware(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg, int tag) {
		super(sender, receiver, message, alg, tag);
	}

	public void broadcast(double times) throws HasNoEnergyException {
		this.getAlg().getStatistics().countPacketFrame(
				this.getPacketFrameNum(), times);

		if (!this.isConsumeEnergy()) {
			logger.debug("cannot consume energy");
			return;
		}

		// double et = this.getAlg().getParam().getTEnergy(this.message,
		// this.sender.getRadioRange());
		// double er = this.getAlg().getParam().getREnergy(message);

		double et = this.getAlg().getParam().getTEnergy(
				(int) this.getPacketFrameSize(), this.sender.getRadioRange());
		double er = this.getAlg().getParam().getREnergy(
				(int) this.getPacketFrameSize());
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

	public double getOneTimeSuccessProbability() {
		double sendAFrameSuccessRate = this.getAlg().getNetwork().getLinkPRR(
				this.sender.getNode(), this.receiver.getNode());
		double sendMessageSuccessRate = Math.pow(sendAFrameSuccessRate, this
				.getPacketFrameNum());
		double totalSuccessRate = sendAFrameSuccessRate
				* sendMessageSuccessRate;
		return totalSuccessRate;
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
		sb.append(" FrameNum:");
		sb.append(this.getPacketFrameNum());

		if (this.receiver != null) {
			sb.append(" OneTimeSuccessProbability:");
			sb.append(this.getOneTimeSuccessProbability());
		}
		sb.append(" Times:");
		sb.append(this.getBroadcastTimes());
		return sb.toString();
	}

}
