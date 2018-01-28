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

public class BroadcastEvent extends TransferEvent {
	static Logger logger = Logger.getLogger(BroadcastEvent.class);
	NeighborAttachment sender;
	NeighborAttachment receiver;
	Color color = null;
	Shape shape = null;
	double broadcastTimes = 1;
	double consumedEnergyUtilThisEvent = 0;
	double consumedEnergy = 0;
	String info = null;
	double communicationTime = 0;
	double consumedFrameNum = 0;

	public BroadcastEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg,
			Color color) {
		super(message, alg);
		this.receiver = receiver;
		this.sender = sender;
		this.color = color;
	}

	public BroadcastEvent(NeighborAttachment sender, Shape shape,
			Message message, Algorithm alg, Color color) {
		super(message, alg);
		this.receiver = null;
		this.sender = sender;
		this.shape = shape;
		this.color = color;
	}

	public BroadcastEvent(BroadcastEvent b) {
		super(new Message(b.message), b.alg);
		this.receiver = b.receiver;
		this.sender = b.sender;
		this.shape = b.shape;
		this.color = b.color;
		this.success = b.success;
		this.consumeEnergy = b.consumeEnergy;
	}
	
	protected void addCommunicationTime(double messageSize, double speed) {
		double time = messageSize/speed;
		this.communicationTime += time;
		this.getAlg().addCommunicationTime(time);
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
		BroadcastEvent.logger = logger;
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

	public BroadcastEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg) {
		super(message, alg);
		this.sender = sender;
		this.receiver = receiver;
	}

	public BroadcastEvent(NeighborAttachment sender, Message message,
			Shape shape, Algorithm alg) {
		super(message, alg);
		this.sender = sender;
		this.receiver = null;
		this.shape = shape;
	}

	public BroadcastEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg, int tag) {
		super(message, alg);
		this.sender = sender;
		this.receiver = receiver;
		this.tag = tag;
	}

	public void broadcast(double times) throws HasNoEnergyException {
		if (!this.isConsumeEnergy()) {
			logger.debug("cannot consume energy");
			return;
		}

		this.getAlg().getStatistics().countPacketFrame(
				this.getPacketFrameNum(), times);


		// double et = this.getAlg().getParam().getTEnergy(this.message,
		// this.sender.getRadioRange());
		// double er = this.getAlg().getParam().getREnergy(message);

		// double et =
		// this.getAlg().getParam().getTEnergy((int)this.getPacketFrameSize(),
		// this.sender.getRadioRange());
		// double er =
		// this.getAlg().getParam().getREnergy((int)this.getPacketFrameSize());

		double radioRange;
		if(this.getAlg().isUseAlgorithmRadioRangeToCalEnergy()) {
			radioRange = this.getAlg().getAlgorithmRadioRange();
		}
		else {
			radioRange = this.sender.getRadioRange();
		}
		
		double et = this.getAlg().getParam().getTEnergy(
				(int) (this.getPacketFrameSize() * this.getPacketFrameNum()),
				radioRange);
		double er = this.getAlg().getParam().getREnergy(
				(int) (this.getPacketFrameSize() * this.getPacketFrameNum()));
		Attachment neighbor;
		
		sender.consumeEnergy(et * times);
		this.getAlg().getStatistics().addConsumedEnergy(et * times);
		consumedEnergy += et * times;

		if(this.getAlg().isRadioBroadcastNature()) {	
			for (Iterator it = sender.getNeighbors().iterator(); it.hasNext();) {
				neighbor = (NeighborAttachment) it.next();
				neighbor.consumeEnergy(er * times);

				this.getAlg().getStatistics().addConsumedEnergy(er * times);
				consumedEnergy += er * times;
			}
		}
		else {
			if(this.receiver != null) {
				this.receiver.consumeEnergy(er * times);
				// nba = (NodeAttachment)neighbor.getAttachment("SWinFloodAlg");
				// nba.setReceivedQID(qid);
				this.getAlg().getStatistics().addConsumedEnergy(er * times);
				consumedEnergy += er * times;
			}
			else if(this.shape != null) {
				AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
						shape);
				Vector neighborsInShape = this.getSender().getNeighbors(
						this.alg.getParam().getRADIO_RANGE(), nairc);
				for (int i = 0; i < neighborsInShape.size(); i++) {
					neighbor = ((Attachment) neighborsInShape.elementAt(i));
					neighbor.consumeEnergy(er * times);

					this.getAlg().getStatistics().addConsumedEnergy(er * times);
					consumedEnergy += er * times;
				}				
			}
			else {
				Vector neighborsInShape = this.getSender().getNeighbors(
						this.alg.getParam().getRADIO_RANGE());

				for (int i = 0; i < neighborsInShape.size(); i++) {
					neighbor = (Attachment) neighborsInShape
							.elementAt(i);
					neighbor.consumeEnergy(er * times);

					this.getAlg().getStatistics().addConsumedEnergy(er * times);
					consumedEnergy += er * times;
				}
			}
		}
	}

	public void linkAwareHandle() throws HasNoEnergyException {
		if (!this.isConsumeEnergy()) {
			logger.debug("cannot consume energy");
			return;
		}

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
				//TODO
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
		this.addCommunicationTime(this.message.getTotalSize()*this.broadcastTimes*8, this.getAlg().getParam().getRadioSpeed());
	}

	public void linkUnAwareHandle() throws HasNoEnergyException {
		if (!this.isConsumeEnergy()) {
			logger.debug("cannot consume energy");
			return;
		}
		this.getAlg().getStatistics().countPacketFrame(
				this.getPacketFrameNum(), 1);
		
		double radioRange;
		if(this.getAlg().isUseAlgorithmRadioRangeToCalEnergy()) {
			radioRange = this.getAlg().getAlgorithmRadioRange();
		}
		else {
			radioRange = this.sender.getRadioRange();
		}

		double et = this.getAlg().getParam().getTEnergy(this.message,
				radioRange);
		double er = this.getAlg().getParam().getREnergy(message);
		NeighborAttachment neighbor;

		sender.consumeEnergy(et);
		// na.setReceivedQID(qid);
		this.getAlg().getStatistics().addConsumedEnergy(et);
		consumedEnergy += et;

		for (Iterator it = sender.getNeighbors().iterator(); it.hasNext();) {
			neighbor = (NeighborAttachment) it.next();
			neighbor.consumeEnergy(er);

			this.getAlg().getStatistics().addConsumedEnergy(er);
			consumedEnergy += er;
		}
		
		this.addCommunicationTime(this.message.getTotalSize()*8, this.getAlg().getParam().getRadioSpeed());
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
			sb.append("BroadcastEvent  ");
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
