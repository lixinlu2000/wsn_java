package NHSensor.NHSensorSim.events;

import java.awt.Color;
import org.apache.log4j.Logger;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shapeTraverse.EDCGridTraverseRingIteratorLinkAware;

/*
 * ERISC Link Aware
 */
public class EDCGridTraverseRingEventUseIteratorLinkAware extends
		TraverseRingEvent {
	static Logger logger = Logger
			.getLogger(EDCGridTraverseRingEventUseIteratorLinkAware.class);

	EDCGridTraverseRingIteratorLinkAware gtri;

	public EDCGridTraverseRingEventUseIteratorLinkAware(
			NeighborAttachment root, Ring ring, Message message, Algorithm alg) {
		super(root, ring, message, alg);
		gtri = new EDCGridTraverseRingIteratorLinkAware(alg, root, ring);
	}

	public EDCGridTraverseRingEventUseIteratorLinkAware(
			NeighborAttachment root, Ring ring, Message message,
			boolean sendQuery, Algorithm alg) {
		super(root, ring, message, sendQuery, alg);
		gtri = new EDCGridTraverseRingIteratorLinkAware(alg, root, ring);
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		Shape curShape = null;
		NeighborAttachment nextNa;
		Shape nextShape;

		CollectDataInShapeViaNodeEventFoLACLinkAware collectDataInShapeViaNodeEvent;
		boolean isSendQueryMessage = this.isSendQuery();
		boolean firstTime = true;
		DrawShapeEvent dse;
		DrawNodeEvent dne;

		logger.debug("EDCGridTraverseRingEventUseIteratorLinkAware start");
		do {
			if (gtri.isEnd(curNa, curShape)) {
				this.setSuccess(true);
				this.setLastNode(curNa);
				break;
			}

			gtri.setPartialQueryResultSize(this.message.getTotalSize()
					- this.getAlg().getParam().getQUERY_MESSAGE_SIZE());
			nextNa = gtri.getNextNeighborAttachment(curNa, curShape);
			nextShape = gtri.getNextShape(curNa, curShape);

			//
			if (nextShape != null) {
				dse = new DrawShapeEvent(this.getAlg(), nextShape);
				this.getAlg().getSimulator().handle(dse);
			}
			//
			if (nextNa != null) {
				dne = new DrawNodeEvent(this.getAlg(), nextNa.getNode(),
						Color.cyan);
				this.getAlg().getSimulator().handle(dne);
			}

			collectDataInShapeViaNodeEvent = new CollectDataInShapeViaNodeEventFoLACLinkAware(
					curNa, nextNa, nextShape, gtri, isSendQueryMessage,
					this.message, this.alg);
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
			curShape = collectDataInShapeViaNodeEvent.getEndShape();

			if (isSendQueryMessage && curShape != null && curNa != null
					&& curShape.contains(curNa.getNode().getPos())) {
				isSendQueryMessage = false;
				this.setSendQuery(false);
			}
		} while (true);
		logger.debug("EDCGridTraverseRingEventUseIteratorLinkAware end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("EDCGridTraverseRingEventUseIteratorLinkAware ");
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
