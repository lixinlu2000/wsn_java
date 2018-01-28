package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shapeTraverse.EDCLinkAwareGridTraverseRectIteratorGBA;
import NHSensor.NHSensorSim.shapeTraverse.TraverseRectIterator;

public class EDCLinkAwareGridTraverseRectEventGBA extends TraverseRectEvent {
	static Logger logger = Logger
			.getLogger(EDCLinkAwareGridTraverseRectEventGBA.class);
	protected int direction;

	public EDCLinkAwareGridTraverseRectEventGBA(Message message, Algorithm alg) {
		super(message, alg);
	}

	public EDCLinkAwareGridTraverseRectEventGBA(NeighborAttachment root,
			Rect rect, int direction, Message message, Algorithm alg) {
		super(root, rect, message, alg);
		this.direction = direction;
	}

	public EDCLinkAwareGridTraverseRectEventGBA(NeighborAttachment root,
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
		TraverseRectIterator rti = new EDCLinkAwareGridTraverseRectIteratorGBA(
				this.alg, this.direction, this.rect);

		LinkAwareCollectDataInShapeViaNodeEvent cde;
		boolean isSendQueryMessage = this.isSendQuery();
		boolean firstTime = true;

		logger.debug("EDCLinkAwareGridTraverseRectEventGBA start");
		do {
			nextNa = rti.getNextNeighborAttachment(curNa, curRect);
			nextRect = rti.getNextRect(curNa, curRect);

			cde = new LinkAwareCollectDataInShapeViaNodeEvent(curNa, nextNa,
					nextRect, isSendQueryMessage, this.message, this.alg);
			this.getAlg().getSimulator().handle(cde);
			this.getRoute().addAll(cde.getRoute());

			// boolean firstTime = true;
			if (firstTime) {
				this.setFirstPhaseRoute(cde.getRoute());
				firstTime = false;
			}

			curNa = cde.getEndNeighborAttachment();
			curRect = nextRect;

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
		logger.debug("EDCLinkAwareGridTraverseRectEventGBA end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("EDCLinkAwareGridTraverseRectEventGBA ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getRect());
		return sb.toString();
	}
}
