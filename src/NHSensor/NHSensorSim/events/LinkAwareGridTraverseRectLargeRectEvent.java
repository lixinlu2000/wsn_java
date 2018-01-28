package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shapeTraverse.LinkAwareGridTraverseRectIterator;
import NHSensor.NHSensorSim.shapeTraverse.TraverseRectIterator;

/*
 * NOTE: the member variable TransferEvent.message means the partial answer message!
 * the member variable TraverseRectEvent.sendQuery means whether send query message first!
 */

public class LinkAwareGridTraverseRectLargeRectEvent extends TraverseRectEvent {
	static Logger logger = Logger
			.getLogger(LinkAwareGridTraverseRectLargeRectEvent.class);
	protected int direction;

	public LinkAwareGridTraverseRectLargeRectEvent(Message message,
			Algorithm alg) {
		super(message, alg);
	}

	public LinkAwareGridTraverseRectLargeRectEvent(NeighborAttachment root,
			Rect rect, int direction, Message message, Algorithm alg) {
		super(root, rect, message, alg);
		this.direction = direction;
	}

	public LinkAwareGridTraverseRectLargeRectEvent(NeighborAttachment root,
			Rect rect, int direction, Message message, boolean sendQuery,
			Algorithm alg) {
		super(root, rect, message, sendQuery, alg);
		this.direction = direction;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		Rect curRect = null;
		NeighborAttachment nextNa;
		Rect nextRect;
		TraverseRectIterator rti = new LinkAwareGridTraverseRectIterator(
				this.alg, this.direction, this.rect);

		LinkAwareCollectDataInShapeAroundNodeEvent collectDataAroundNodeEvent;
		Message queryMessage = new Message(this.getAlg().getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		boolean isSendQueryMessage = this.isSendQuery();
		Message m;
		boolean firstTime = true;

		logger.debug("LinkAwareGridTraverseRectEvent start");
		do {
			logger.debug("call getNextNeighborAttachment "
					+ curNa.getNode().getPos() + " " + curRect);
			nextNa = rti.getNextNeighborAttachment(curNa, curRect);
			logger.debug("end getNextNeighborAttachment");
			logger.debug("call getNextRect");
			nextRect = rti.getNextRect(curNa, curRect);
			logger.debug("end getNextRect");

			if (isSendQueryMessage) {
				m = queryMessage;
			} else {
				m = this.message;
			}

			collectDataAroundNodeEvent = new LinkAwareCollectDataInShapeAroundNodeEvent(
					curNa, nextNa, nextRect, m, this.alg);
			this.getAlg().getSimulator().handle(collectDataAroundNodeEvent);

			// boolean firstTime = true;
			if (firstTime) {
				this.setFirstPhaseRoute(collectDataAroundNodeEvent.getRoute());
				firstTime = false;
			}

			curNa = collectDataAroundNodeEvent.getEndNeighborAttachment();
			curRect = nextRect;
			if (isSendQueryMessage
					&& curRect.contains(curNa.getNode().getPos())) {
				isSendQueryMessage = false;
				this.setSendQuery(false);
			}
			if (rti.isEnd(curNa, curRect)) {
				this.setSuccess(true);
				this.setLastNode(curNa);
				break;
			}

		} while (true);
		logger.debug("LinkAwareGridTraverseRectEvent end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LinkAwareGridTraverseRectEvent ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getRect());
		return sb.toString();
	}
}
