package NHSensor.NHSensorSim.events;

import java.awt.Color;
import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Shape;

public abstract class BroadcasBaseEvent extends TransferEvent {
	static Logger logger = Logger.getLogger(BroadcasBaseEvent.class);
	NeighborAttachment sender;
	NeighborAttachment receiver;
	Color color = null;
	double broadcastTimes = 1;
	Shape shape = null;

	public BroadcasBaseEvent(NeighborAttachment sender, Shape shape,
			Message message, Algorithm alg, Color color) {
		super(message, alg);
		this.sender = sender;
		this.receiver = null;
		this.color = color;
		this.shape = shape;
	}

	public BroadcasBaseEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg,
			Color color) {
		super(message, alg);
		this.receiver = receiver;
		this.sender = sender;
		this.color = color;
	}

	public BroadcasBaseEvent(NeighborAttachment sender, Message message,
			Shape shape, Algorithm alg) {
		this(sender, null, message, alg);
		this.shape = shape;
	}

	public BroadcasBaseEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg) {
		super(message, alg);
		this.sender = sender;
		this.receiver = receiver;
	}

	public BroadcasBaseEvent(NeighborAttachment sender,
			NeighborAttachment receiver, Message message, Algorithm alg, int tag) {
		super(message, alg);
		this.sender = sender;
		this.receiver = receiver;
		this.tag = tag;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		BroadcasBaseEvent.logger = logger;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	/*
	 * public void handle() throws HasNoEnergyException {
	 * if(!this.isConsumeEnergy()) { logger.debug("cannot consume energy");
	 * return; } double et = this.getAlg().getParam().getTEnergy(this.message,
	 * this.sender.getRadioRange()); double er =
	 * this.getAlg().getParam().getREnergy(message); NeighborAttachment
	 * neighbor;
	 * 
	 * sender.consumeEnergy(et); //na.setReceivedQID(qid);
	 * this.getAlg().getStatistics().addConsumedEnergy(et);
	 * 
	 * for(Iterator it=sender.getNeighbors().iterator();it.hasNext();) {
	 * neighbor = (NeighborAttachment)it.next(); neighbor.consumeEnergy(er);
	 * //nba = (NodeAttachment)neighbor.getAttachment("SWinFloodAlg");
	 * //nba.setReceivedQID(qid);
	 * this.getAlg().getStatistics().addConsumedEnergy(er); } }
	 */

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

	public double getBroadcastTimes() {
		return broadcastTimes;
	}

	public void setBroadcastTimes(double broadcastTimes) {
		this.broadcastTimes = broadcastTimes;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}
}
