package NHSensor.NHSensorSim.events;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.AttachmentEnergyRank;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.TopKQueryProcessor;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;

/*
 * greedy forward a message to a node in the destination rect
 * and record weather all nodes in the destination rect can reacieve the message 
 */
public class GreedyForwardToRectExEvent extends ForwardToRectEvent {
	static Logger logger = Logger.getLogger(GreedyForwardToRectExEvent.class);
	private boolean allNodesInRectReceived = false;

	public GreedyForwardToRectExEvent(NeighborAttachment root, Rect rect,
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

		Circle radioCircle;
		Position dest = this.getRect().getCentre();

		NeighborAttachment next = null;
		AttachmentInShapeCondition nairc;
		Vector neighborsInRect;

		RadioEvent event;
		Message msg = this.getMessage();
		logger.debug("start");

		while (!cur.getNode().getPos().in(this.getRect())) {
			radioCircle = new Circle(cur.getNode().getPos(), cur
					.getRadioRange());
			nairc = new AttachmentInShapeCondition(this.getRect());
			neighborsInRect = cur.getNeighbors(cur.getRadioRange(), nairc);
			if (radioCircle.containsRect(this.getRect())) {
				if (neighborsInRect.isEmpty()) {
					this.setLastNode(cur);
					this.setSuccess(false);
					return;
				} else {
					TopKQueryProcessor topKQueryProcessor = new TopKQueryProcessor(
							neighborsInRect, new AttachmentEnergyRank());
					next = (NeighborAttachment) topKQueryProcessor.getTop();
					this.getRoute().add(next);
					event = new BroadcastEvent(cur, next, msg, this.getAlg());
					event.setParent(this);
					logger.debug("forward to");
					logger.debug(new Integer(next.getNode().getId()));
					if (!this.isConsumeEnergy())
						event.setConsumeEnergy(false);
					this.getAlg().getSimulator().handle(event);

					this.setLastNode(next);
					this.setSuccess(true);
					this.setAllNodesInRectReceived(true);
					return;
				}
			}

			/*
			 * if(!neighborsInRect.isEmpty()) { //random choose a node as the
			 * destination next =
			 * (NeighborAttachment)neighborsInRect.elementAt(0);
			 * this.getRoute().add(next); event = new
			 * BroadcastEvent(cur,next,msg,this.getAlg());
			 * logger.debug("forward to"); logger.debug(new
			 * Integer(next.getNode().getId()));
			 * if(!this.isConsumeEnergy())event.setConsumeEnergy(false);
			 * this.getAlg().getSimulator().handle(event);
			 * 
			 * this.setLastNode(next); this.setSuccess(true); return; }
			 */

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

	public boolean isAllNodesInRectReceived() {
		return allNodesInRectReceived;
	}

	public void setAllNodesInRectReceived(boolean allNodesInRectReceived) {
		this.allNodesInRectReceived = allNodesInRectReceived;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GreedyForwardToRectExEvent  ");
		sb.append(this.root.getNode().getPos());
		sb.append("-->");
		sb.append(this.getRect().toString());
		return sb.toString();
	}

}
