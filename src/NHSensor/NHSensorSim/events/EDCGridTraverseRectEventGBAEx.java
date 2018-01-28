package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shapeTraverse.EDCGridTraverseRectIteratorEx;
import NHSensor.NHSensorSim.shapeTraverse.TraverseRectIterator;

/*
 * ex means using TraverseShapeOptimizeIterator
 */
public class EDCGridTraverseRectEventGBAEx extends TraverseRectEvent {
	static Logger logger = Logger
			.getLogger(EDCGridTraverseRectEventGBAEx.class);
	protected int direction;

	public EDCGridTraverseRectEventGBAEx(Message message, Algorithm alg) {
		super(message, alg);
	}

	public EDCGridTraverseRectEventGBAEx(NeighborAttachment root, Rect rect,
			int direction, Message message, Algorithm alg) {
		super(root, rect, message, alg);
		this.direction = direction;
	}

	public EDCGridTraverseRectEventGBAEx(NeighborAttachment root, Rect rect,
			int direction, Message message, boolean sendQuery, Algorithm alg) {
		super(root, rect, message, sendQuery, alg);
		this.direction = direction;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		Rect curRect = null;
		NeighborAttachment nextNa;
		Rect nextRect;
		EDCGridTraverseRectIteratorEx rti = new EDCGridTraverseRectIteratorEx(
				this.alg, this.direction, this.rect);

		CollectDataInShapeViaNodeExEvent cde;
		boolean isSendQueryMessage = this.isSendQuery();
		Message queryMessage = new Message(this.getAlg().getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		Message m;
		boolean firstTime = true;

		logger.debug("EDCGridTraverseRectEventGBAEx start");
		do {
			nextNa = rti.getNextNeighborAttachment(curNa, curRect);
			nextRect = rti.getNextRect(curNa, curRect);

			if (isSendQueryMessage) {
				m = queryMessage;
			} else {
				m = this.message;
			}

			cde = new CollectDataInShapeViaNodeExEvent(curNa, nextNa, nextRect,
					rti, m, this.alg);
			this.getAlg().getSimulator().handle(cde);
			this.getRoute().addAll(cde.getRoute());

			// boolean firstTime = true;
			if (firstTime) {
				this.setFirstPhaseRoute(cde.getRoute());
				firstTime = false;
			}

			curNa = cde.getEndNeighborAttachment();
			curRect = (Rect) cde.getEndShape();

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
		logger.debug("EDCGridTraverseRectEventGBAEx end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("EDCGridTraverseRectEventGBAEx ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getRect());
		return sb.toString();
	}
}
