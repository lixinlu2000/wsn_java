package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shapeTraverse.ROCAdaptiveGridTraverseRingIterator;

/*
 * ROC
 */
public class ROCAdaptiveGridTraverseRingEventUseIterator extends
		TraverseRingEvent {
	static Logger logger = Logger
			.getLogger(ROCAdaptiveGridTraverseRingEventUseIterator.class);

	ROCAdaptiveGridTraverseRingIterator gtri;

	public ROCAdaptiveGridTraverseRingEventUseIterator(NeighborAttachment root,
			Ring ring, Message message, Algorithm alg) {
		super(root, ring, message, alg);
		gtri = new ROCAdaptiveGridTraverseRingIterator(alg, root, ring);
	}

	public ROCAdaptiveGridTraverseRingEventUseIterator(NeighborAttachment root,
			Ring ring, Message message, boolean sendQuery, Algorithm alg) {
		super(root, ring, message, sendQuery, alg);
		gtri = new ROCAdaptiveGridTraverseRingIterator(alg, root, ring);
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		Shape curShape = null;
		NeighborAttachment nextNa;
		Shape nextShape;

		CollectDataInShapeViaNodeEvent collectDataInShapeViaNodeEvent;
		boolean isSendQueryMessage = this.isSendQuery();
		boolean firstTime = true;

		logger.debug("GridTraverseRectEvent start");
		do {
			if (gtri.isEnd(curNa, curShape)) {
				this.setSuccess(true);
				break;
			}

			nextNa = gtri.getNextNeighborAttachment(curNa, curShape);
			nextShape = gtri.getNextShape(curNa, curShape);

			collectDataInShapeViaNodeEvent = new CollectDataInShapeViaNodeEvent(
					curNa, nextNa, nextShape, isSendQueryMessage, this.message,
					this.alg);
			collectDataInShapeViaNodeEvent.setAggregation(false);
			this.getAlg().getSimulator().handle(collectDataInShapeViaNodeEvent);

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
		logger.debug("GridTraverseRectEvent end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ROCAdaptiveGridTraverseRingEvent ");
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
