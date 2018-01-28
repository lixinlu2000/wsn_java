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
import NHSensor.NHSensorSim.shape.Shape;

/*
 * greedy forward a message to a node so that all the nodes in the destination rect
 * can receive the message from the node
 * 
 */
public class GreedyForwardToAllNodeInShapeEvent extends ForwardToShapeEvent {
	static Logger logger = Logger
			.getLogger(GreedyForwardToAllNodeInShapeEvent.class);
	protected boolean hasNodesInShape = false;

	public boolean isHasNodesInShape() {
		return hasNodesInShape;
	}

	public void setHasNodesInShape(boolean hasNodesInShape) {
		this.hasNodesInShape = hasNodesInShape;
	}

	public GreedyForwardToAllNodeInShapeEvent(NeighborAttachment root,
			Shape shape, Message message, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.shape = shape;
	}

	public GreedyForwardToAllNodeInShapeEvent(NeighborAttachment root,
			Shape shape, Message message, Algorithm alg, int tag) {
		super(message, alg);
		this.root = root;
		this.shape = shape;
		this.tag = tag;
	}

	/**
	 * 
	 */
	public void handle() throws SensornetBaseException {
		NeighborAttachment cur = this.getRoot();

		Circle radioCircle = new Circle(cur.getNode().getPos(), cur
				.getRadioRange());
		Position dest = this.getShape().getCentre();

		NeighborAttachment next = null;
		AttachmentInShapeCondition nairc;
		Vector neighborsInRect;

		RadioEvent event;
		Message msg = this.getMessage();
		logger.debug("GreedyForwardToAllNodeInShapeEvent start");

		while (!radioCircle.contains(this.getShape())) {
			next = cur.getNextNeighborAttachmentToDest(dest);
			if (cur != next) {
				this.getRoute().add(next);
				event = new BroadcastEvent(cur, next, msg, this.getAlg());
				event.setParent(this);
				logger.debug("GreedyForwardToAllNodeInShapeEvent forward to  "
						+ new Integer(next.getNode().getId()));
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
				this.setHasNodesInShape(false);
				RouteProtocolEndEvent rpee = new RouteProtocolEndEvent(cur,
						false, this.getAlg());
				this.getAlg().getSimulator().handle(rpee);
				logger
						.debug("GreedyForwardToAllNodeInShapeEvent cannot greedy");
				return;
			}
		}
		logger.debug("GreedyForwardToAllNodeInShapeEvent end");

		RouteProtocolEndEvent rpee = new RouteProtocolEndEvent(cur, true, this
				.getAlg());
		this.getAlg().getSimulator().handle(rpee);

		nairc = new AttachmentInShapeCondition(this.getShape());
		neighborsInRect = cur.getNeighbors(cur.getRadioRange(), nairc);

		// bug add this code
		if (nairc.isTrue(cur))
			neighborsInRect.add(cur);

		if (neighborsInRect.isEmpty()) {
			this.setLastNode(cur);
			this.setSuccess(true);
			this.setHasNodesInShape(false);
			return;
		} else {
			this.setLastNode(cur);
			this.setSuccess(true);
			this.setHasNodesInShape(true);
			return;
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GreedyForwardToAllNodeInShapeEvent  ");
		sb.append(this.root.getNode().getPos());
		sb.append("-->");
		sb.append(this.getShape().getCentre());
		sb.append(" ");
		sb.append(this.getShape());
		return sb.toString();

	}
}
