package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Position;

public class GreedyForwardToPointEvent extends ForwardToShapeEvent {
	public GreedyForwardToPointEvent(NeighborAttachment root, Position dest,
			Message message, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.shape = dest;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment cur = this.getRoot();

		Position dest = this.getDest();

		// TODO find closest node to destination which has NodeAttachment! Done
		// 2008.4.10
		NeighborAttachment next = cur.getNextNeighborAttachmentToDest(dest);

		RadioEvent event;
		Message msg = this.getMessage();

		while (cur != next) {
			this.getRoute().add(next);
			event = new BroadcastEvent(cur, next, msg, this.getAlg());
			event.setParent(this);
			if (!this.isConsumeEnergy())
				event.setConsumeEnergy(false);
			this.getAlg().getSimulator().handle(event);
			cur = next;
			if(cur.getPos().equals(dest))break;
			next = cur.getNextNeighborAttachmentToDest(dest);
		}

		this.setLastNode(cur);
		// TODO distance == 0 or distance < 10e-10
		if (Position.distance(cur.getNode().getPos(), dest) == 0) {
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
		sb.append("GreedyForwardToPointEvent  ");
		sb.append(this.root.getNode().getPos());
		sb.append("-->");
		sb.append(this.getDest());
		return sb.toString();
	}

}
