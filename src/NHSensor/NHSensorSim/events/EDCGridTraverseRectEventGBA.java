package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shapeTraverse.EDCGridTraverseRectIterator;
import NHSensor.NHSensorSim.shapeTraverse.TraverseRectIterator;

public class EDCGridTraverseRectEventGBA extends TraverseRectEvent {
	static Logger logger = Logger.getLogger(EDCGridTraverseRectEventGBA.class);
	protected int direction;

	public EDCGridTraverseRectEventGBA(Message message, Algorithm alg) {
		super(message, alg);
	}

	public EDCGridTraverseRectEventGBA(NeighborAttachment root, Rect rect,
			int direction, Message message, Algorithm alg) {
		super(root, rect, message, alg);
		this.direction = direction;
	}

	public EDCGridTraverseRectEventGBA(NeighborAttachment root, Rect rect,
			int direction, Message message, boolean sendQuery, Algorithm alg) {
		super(root, rect, message, sendQuery, alg);
		this.direction = direction;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		Rect curRect = null;
		NeighborAttachment nextNa;
		Rect nextRect;
		TraverseRectIterator rti = new EDCGridTraverseRectIterator(this.alg,
				this.direction, this.rect);

		CollectDataInRectViaNodeExEvent collectDataInRectViaNodeExEvent;
		boolean isSendQueryMessage = this.isSendQuery();
		Message queryMessage = new Message(this.getAlg().getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		Message m;
		boolean firstTime = true;

		logger.debug("EDCGridTraverseRectEventGBA start");
		do {
			nextNa = rti.getNextNeighborAttachment(curNa, curRect);
			nextRect = rti.getNextRect(curNa, curRect);

			if (isSendQueryMessage) {
				m = queryMessage;
			} else {
				m = this.message;
			}

			collectDataInRectViaNodeExEvent = new CollectDataInRectViaNodeExEvent(
					curNa, nextNa, nextRect, rti, m, this.alg);
			this.getAlg().getSimulator()
					.handle(collectDataInRectViaNodeExEvent);
			this.getRoute().addAll(collectDataInRectViaNodeExEvent.getRoute());

			// boolean firstTime = true;
			if (firstTime) {
				this.setFirstPhaseRoute(collectDataInRectViaNodeExEvent
						.getRoute());
				firstTime = false;
			}

			curNa = collectDataInRectViaNodeExEvent.getEndNeighborAttachment();
			curRect = collectDataInRectViaNodeExEvent.getEndRect();

			if (isSendQueryMessage && curRect != null && curNa != null
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
		logger.debug("EDCGridTraverseRectEventGBA end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("EDCGridTraverseRectEvent ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getRect());
		return sb.toString();
	}
}
