package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shapeTraverse.LineTraverseRingIteratorLinkAware;

/*
 * For LKNN
 */
public class LineTraverseRingEventLinkAware extends TraverseRingEvent {
	static Logger logger = Logger
			.getLogger(LineTraverseRingEventLinkAware.class);

	LineTraverseRingIteratorLinkAware tri;

	public LineTraverseRingEventLinkAware(NeighborAttachment root, Ring ring,
			Message message, Algorithm alg) {
		super(root, ring, message, alg);
		tri = new LineTraverseRingIteratorLinkAware(alg, root, ring);
	}

	public LineTraverseRingEventLinkAware(NeighborAttachment root, Ring ring,
			Message message, boolean sendQuery, Algorithm alg) {
		super(root, ring, message, sendQuery, alg);
		tri = new LineTraverseRingIteratorLinkAware(alg, root, ring);
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		Shape curShape = null;
		NeighborAttachment nextNa;
		Shape nextShape;

		CollectDataInShapeViaNodeEvent collectDataInShapeViaNodeEvent;
		boolean isSendQueryMessage = this.isSendQuery();
		boolean firstTime = true;

		logger.debug("LineTraverseRingEventLinkAware start");
		do {
			if (tri.isEnd(curNa, curShape)) {
				this.setSuccess(true);
				this.setLastNode(curNa);
				break;
			}

			tri.setPartialAnswerSize(this.message.getTotalSize()
					- this.getAlg().getParam().getQUERY_MESSAGE_SIZE());
			nextNa = tri.getNextNeighborAttachment(curNa, curShape);
			nextShape = tri.getNextShape(curNa, curShape);

			collectDataInShapeViaNodeEvent = new CollectDataInShapeViaNodeEvent(
					curNa, nextNa, nextShape, isSendQueryMessage, this.message,
					this.alg);
			collectDataInShapeViaNodeEvent.setAggregation(false);
			this.getAlg().getSimulator().handle(collectDataInShapeViaNodeEvent);
			this.getRoute().addAll(collectDataInShapeViaNodeEvent.getRoute());

			// boolean firstTime = true;
			if (firstTime) {
				this.setFirstPhaseRoute(collectDataInShapeViaNodeEvent
						.getRoute());
				firstTime = false;
			}

			curNa = collectDataInShapeViaNodeEvent.getEndNeighborAttachment();
			curShape = nextShape;

			if (isSendQueryMessage && curShape != null && curNa != null
					&& curShape.contains(curNa.getNode().getPos())) {
				isSendQueryMessage = false;
				this.setSendQuery(false);
			}
		} while (true);
		logger.debug("LineTraverseRingIteratorLinkAware end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LineTraverseRingIteratorLinkAware ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getRing());
		sb.append(" SendQueryFirst:");
		sb.append(this.isSendQuery());
		sb.append(" Size:");
		sb.append(this.getMessage().getTotalSize());
		sb.append(")");
		return sb.toString();
	}

}
