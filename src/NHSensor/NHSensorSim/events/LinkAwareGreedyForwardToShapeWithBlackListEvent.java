package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Shape;

public class LinkAwareGreedyForwardToShapeWithBlackListEvent extends
		ForwardToShapeEvent {
	private double linkQualityThrehold = 0.05;

	public LinkAwareGreedyForwardToShapeWithBlackListEvent(
			NeighborAttachment root, Shape shape, Message message, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.shape = shape;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment cur = this.getRoot();

		NeighborAttachment next = null;

		RadioEvent event;
		Message msg = this.getMessage();

		while (!this.getShape().contains(cur.getNode().getPos())) {
			next = cur.getNextNeighborAttachmentToShapeLinkAware(shape);
			if (cur != next) {
				if (this.getAlg().getNetwork().getLinkPRR(cur.getNode(),
						next.getNode()) <= linkQualityThrehold)
					break;
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
		sb.append("LinkAwareGreedyForwardToShapeWithBlackListEvent  ");
		sb.append(this.root.getNode().getPos());
		sb.append("-->");
		sb.append(this.shape.toString());
		return sb.toString();

	}

}
