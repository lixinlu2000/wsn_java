package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shapeTraverse.GridTraverseRingIterator;

/*
 * RISC
 */
public class GridTraverseRingEventUseIterator extends TraverseRingEvent {
	static Logger logger = Logger
			.getLogger(GridTraverseRingEventUseIterator.class);
	GridTraverseRingIterator gtri;

	public GridTraverseRingEventUseIterator(NeighborAttachment root, Ring ring,
			double ringSectorSita, Message message, Algorithm alg) {
		super(root, ring, message, alg);
		gtri = new GridTraverseRingIterator(alg, root, ring, ringSectorSita);
	}

	public GridTraverseRingEventUseIterator(NeighborAttachment root, Ring ring,
			double ringSectorSita, Message message, boolean sendQuery,
			Algorithm alg) {
		super(root, ring, message, sendQuery, alg);
		gtri = new GridTraverseRingIterator(alg, root, ring, ringSectorSita);
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
		sb.append("GridTraverseRingEventUseIterator ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getRing());
		sb.append(" ringSectorSita(");
		sb.append(this.gtri.getRingSectorSita());
		sb.append(")");
		return sb.toString();
	}

}
