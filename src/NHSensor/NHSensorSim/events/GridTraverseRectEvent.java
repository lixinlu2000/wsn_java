package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shapeTraverse.GridTraverseRectIterator;
import NHSensor.NHSensorSim.shapeTraverse.TraverseRectIterator;

public class GridTraverseRectEvent extends TraverseRectEvent {
	static Logger logger = Logger.getLogger(GridTraverseRectEvent.class);
	protected int direction;

	public GridTraverseRectEvent(Message message, Algorithm alg) {
		super(message, alg);
	}

	public GridTraverseRectEvent(NeighborAttachment root, Rect rect,
			int direction, Message message, Algorithm alg) {
		super(root, rect, message, alg);
		this.direction = direction;
	}

	public GridTraverseRectEvent(NeighborAttachment root, Rect rect,
			int direction, Message message, boolean sendQuery, Algorithm alg) {
		super(root, rect, message, sendQuery, alg);
		this.direction = direction;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		Rect curRect = null;
		NeighborAttachment nextNa;
		Rect nextRect;
		TraverseRectIterator rti = new GridTraverseRectIterator(this.alg,
				this.direction, this.rect);

		CollectDataInShapeViaNodeEvent collectDataInShapeViaNodeEvent;
		boolean isSendQueryMessage = this.isSendQuery();
		Message queryMessage = new Message(this.getAlg().getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		Message m;
		boolean firstTime = true;

		logger.debug("GridTraverseRectEvent start");
		do {
			nextNa = rti.getNextNeighborAttachment(curNa, curRect);
			nextRect = rti.getNextRect(curNa, curRect);

			if (isSendQueryMessage) {
				m = queryMessage;
			} else {
				m = this.message;
			}

			collectDataInShapeViaNodeEvent = new CollectDataInShapeViaNodeEvent(
					curNa, nextNa, nextRect, m, this.alg);
			this.getAlg().getSimulator().handle(collectDataInShapeViaNodeEvent);

			// boolean firstTime = true;
			if (firstTime) {
				this.setFirstPhaseRoute(collectDataInShapeViaNodeEvent
						.getRoute());
				firstTime = false;
			}

			curNa = collectDataInShapeViaNodeEvent.getEndNeighborAttachment();
			curRect = nextRect;

			if (isSendQueryMessage && curRect != null && curNa != null
					&& curRect.contains(curNa.getNode().getPos())) {
				isSendQueryMessage = false;
				this.setSendQuery(false);
			}

			if (rti.isEnd(curNa, curRect)) {
				this.setSuccess(true);
				break;
			}
		} while (true);
		logger.debug("GridTraverseRectEvent end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GridTraverseRectEvent ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getRect());
		return sb.toString();
	}
}
