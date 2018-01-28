package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Shape;

public class GreedyForwardToShapeEventFailureAware extends ForwardToShapeEvent {
	public GreedyForwardToShapeEventFailureAware(NeighborAttachment root,
			Shape shape, Message message, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.shape = shape;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment cur = this.getRoot();

		Position dest = this.getShape().getCentre();

		NeighborAttachment next = null;

		RadioEvent event;
		Message msg = this.getMessage();

		while (!this.getShape().contains(cur.getNode().getPos())) {
			next = cur.getNextNeighborAttachmentToDestFailureAware(dest);
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
		if (this.getShape().contains(cur.getNode().getPos())) {
			this.setSuccess(true);
		} else {
			this.setSuccess(false);
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GreedyForwardToShapeEventFailureAware  ");
		sb.append(this.root.getNode().getPos());
		sb.append("-->");
		sb.append(this.shape.toString());
		return sb.toString();

	}

}
