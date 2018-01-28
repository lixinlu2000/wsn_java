package NHSensor.NHSensorSim.events;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;

/*
 * greedy forward a message to a node so that all the nodes in the destination rect
 * can receive the message from the node
 * 
 */
public class GreedyForwardToRectExNewEvent extends ForwardToRectEvent {
	static Logger logger = Logger
			.getLogger(GreedyForwardToRectExNewEvent.class);
	private boolean hasNodesInRect = false;

	public GreedyForwardToRectExNewEvent(NeighborAttachment root, Rect rect,
			Message message, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.shape = rect;
	}

	/**
	 * 
	 */
	public void handle() throws SensornetBaseException {
		NeighborAttachment cur = this.getRoot();

		Circle radioCircle = new Circle(cur.getNode().getPos(), cur
				.getRadioRange());
		Position dest = this.getRect().getCentre();

		NeighborAttachment next = null;
		AttachmentInShapeCondition nairc;
		Vector neighborsInRect;

		RadioEvent event;
		Message msg = this.getMessage();
		logger.debug("start");

		while (!radioCircle.containsRect(this.getRect())) {
			next = cur.getNextNeighborAttachmentToDest(dest);
			if (cur != next) {
				this.getRoute().add(next);
				event = new BroadcastEvent(cur, next, msg, this.getAlg());
				event.setParent(this);
				logger.debug("forward to");
				logger.debug(new Integer(next.getNode().getId()));
				if (!this.isConsumeEnergy())
					event.setConsumeEnergy(false);
				this.getAlg().getSimulator().handle(event);
				cur = next;
				radioCircle = new Circle(cur.getNode().getPos(), cur
						.getRadioRange());
			} else {
				// can not greedy
				this.setSuccess(false);
				this.setLastNode(cur);
				this.setHasNodesInRect(false);
				return;
			}
		}

		nairc = new AttachmentInShapeCondition(this.getRect());
		neighborsInRect = cur.getNeighbors(cur.getRadioRange(), nairc);

		// bug add this code
		if (nairc.isTrue(cur))
			neighborsInRect.add(cur);

		if (neighborsInRect.isEmpty()) {
			this.setLastNode(cur);
			this.setSuccess(true);
			this.setHasNodesInRect(false);
			return;
		} else {
			this.setLastNode(cur);
			this.setSuccess(true);
			this.setHasNodesInRect(true);
			return;
		}
	}

	public boolean isHasNodesInRect() {
		return hasNodesInRect;
	}

	public void setHasNodesInRect(boolean hasNodesInRect) {
		this.hasNodesInRect = hasNodesInRect;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GreedyForwardToRectExNewEvent  ");
		sb.append(this.root.getNode().getPos());
		sb.append("-->");
		sb.append(this.getRect().toString());
		return sb.toString();
	}

}
