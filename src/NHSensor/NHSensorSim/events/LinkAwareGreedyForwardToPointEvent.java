package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Constants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Position;

public class LinkAwareGreedyForwardToPointEvent extends ForwardToShapeEvent {
	private double linkQualityThrehold = 0.01;

	public LinkAwareGreedyForwardToPointEvent(NeighborAttachment root,
			Position dest, Message message, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.shape = dest;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment cur = this.getRoot();

		Position dest = this.getDest();

		// TODO find closest node to destination which has NodeAttachment! Done
		// 2008.4.10
		NeighborAttachment next = cur
				.getNextNeighborAttachmentToDestLinkAware(dest);

		RadioEvent event;
		Message msg = this.getMessage();

		while (cur != next) {
			if (this.getAlg().getNetwork().getLinkPRR(cur.getNode(),
					next.getNode()) <= linkQualityThrehold) {
				break;
			}
			this.getRoute().add(next);
			event = new BroadcastEvent(cur, next, msg, this.getAlg());
			event.setParent(this);
			if (!this.isConsumeEnergy())
				event.setConsumeEnergy(false);
			this.getAlg().getSimulator().handle(event);
			cur = next;
			next = cur.getNextNeighborAttachmentToDestLinkAware(dest);
		}

		this.setLastNode(cur);
		// TODO distance == 0 or distance < 10e-10
		if (Position.distance(cur.getNode().getPos(), dest) <= Constants.DOUBLE_EQUAEL_VALUE) {
			this.setSuccess(true);
		} else {
			this.setSuccess(false);
		}
	}

	public Position getDest() {
		return (Position) this.getShape();
	}

	public void setDest(Position dest) {
		this.shape = dest;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LinkAwareGreedyForwardToPointEvent  ");
		sb.append(this.root.getNode().getPos());
		sb.append("-->");
		sb.append(this.getDest());
		return sb.toString();
	}

}
