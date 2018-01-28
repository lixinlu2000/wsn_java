package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;

/*
 * greedy forward a message to a node in the destination rect
 */
public class GreedyForwardToRectEvent extends ForwardToRectEvent {
	public GreedyForwardToRectEvent(NeighborAttachment root, Rect rect,
			Message message, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.shape = rect;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment cur = this.getRoot();

		Position dest = this.getRect().getCentre();

		NeighborAttachment next = null;

		RadioEvent event;
		Message msg = this.getMessage();

		while (!cur.getNode().getPos().in(this.getRect())) {
			next = cur.getNextNeighborAttachmentToDest(dest);
			if (cur != next) {
				this.getRoute().add(next);
				event = new BroadcastEvent(cur, next, msg, this.getAlg());
				event.setParent(this);
				if (!this.isConsumeEnergy())
					event.setConsumeEnergy(false);
				this.getAlg().getSimulator().handle(event);
				cur = next;
			} else {
				// can not greedy
				break;
			}
		}

		this.setLastNode(cur);
		if (cur.getNode().getPos().in(this.getRect())) {
			this.setSuccess(true);
		} else {
			this.setSuccess(false);
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GreedyForwardToRectEvent  ");
		sb.append(this.root.getNode().getPos());
		sb.append("-->");
		sb.append(this.getRect().toString());
		return sb.toString();
	}

}
