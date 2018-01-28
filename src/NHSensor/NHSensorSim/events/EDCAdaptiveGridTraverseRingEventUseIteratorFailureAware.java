package NHSensor.NHSensorSim.events;

import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import org.apache.log4j.Logger;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shapeTraverse.EDCAdaptiveGridTraverseRingIteratorFailureAware;

/*
 * ERISC
 */
public class EDCAdaptiveGridTraverseRingEventUseIteratorFailureAware extends
		TraverseRingEvent {
	static Logger logger = Logger
			.getLogger(EDCAdaptiveGridTraverseRingEventUseIteratorFailureAware.class);

	EDCAdaptiveGridTraverseRingIteratorFailureAware gtri;

	public EDCAdaptiveGridTraverseRingEventUseIteratorFailureAware(
			NeighborAttachment root, Ring ring, Message message, Algorithm alg) {
		super(root, ring, message, alg);
		gtri = new EDCAdaptiveGridTraverseRingIteratorFailureAware(alg, root,
				ring);
	}

	public EDCAdaptiveGridTraverseRingEventUseIteratorFailureAware(
			NeighborAttachment root, Ring ring, Message message,
			boolean sendQuery, Algorithm alg) {
		super(root, ring, message, sendQuery, alg);
		gtri = new EDCAdaptiveGridTraverseRingIteratorFailureAware(alg, root,
				ring);
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		Shape curShape = null;
		NeighborAttachment nextNa;
		Shape nextShape;

		CollectDataInShapeViaNodeEventForERISTFailureAware collectDataInShapeViaNodeEvent;
		boolean isSendQueryMessage = this.isSendQuery();
		boolean firstTime = true;

		logger
				.debug("EDCAdaptiveGridTraverseRingEventUseIteratorFailureAware start");
		do {
			if (gtri.isEnd(curNa, curShape)) {
				this.setSuccess(true);
				this.setLastNode(curNa);
				break;
			}

			nextNa = gtri.getNextNeighborAttachment(curNa, curShape);
			nextShape = gtri.getNextShape(curNa, curShape);

			collectDataInShapeViaNodeEvent = new CollectDataInShapeViaNodeEventForERISTFailureAware(
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

			if (nextNa != null) {
				this.backupNodeNum += gtri.getRepairedNeighborAttachmentNum(
						curNa, nextNa, nextShape);
			}

			if (nextNa != null && !nextNa.isAlive()) {
				curNa = gtri.getRepairedNeighborAttachment(curNa, nextNa,
						nextShape);
				curShape = gtri.getRepairedShape(curNa, nextNa, nextShape);
				this.repairMessage(nextShape, curShape);
				this.updateNodeStatus(nextNa, nextShape);

				// TODO
				if (curNa == null) {
					this.setSuccess(false);
					throw new SensornetBaseException();
				}
			} else {
				curNa = collectDataInShapeViaNodeEvent
						.getEndNeighborAttachment();
				curShape = nextShape;
			}

			if (isSendQueryMessage && curShape != null && curNa != null
					&& curShape.contains(curNa.getNode().getPos())) {
				isSendQueryMessage = false;
				this.setSendQuery(false);
			}
		} while (true);
		this.backupNodeNum /= this.getQueryNodeNum();
		logger
				.debug("EDCAdaptiveGridTraverseRingEventUseIteratorFailureAware end");
	}

	protected void repairMessage(Shape fail, Shape repair) {
		Set messageSegments = this.message.getSegments().keySet();

		Iterator iterator = messageSegments.iterator();
		NeighborAttachment na;
		Vector shouldRemoveMessageSegmentKeys = new Vector();

		while (iterator.hasNext()) {
			na = (NeighborAttachment) iterator.next();
			if (fail.contains(na.getPos()) && !repair.contains(na.getPos())) {
				shouldRemoveMessageSegmentKeys.add(na);
			}
		}

		for (int i = 0; i < shouldRemoveMessageSegmentKeys.size(); i++) {
			na = (NeighborAttachment) shouldRemoveMessageSegmentKeys
					.elementAt(i);
			this.message.removeMessageSegment(na);
		}
	}

	protected void updateNodeStatus(NeighborAttachment nextNa, Shape fail) {
		Vector nas = nextNa.getNeighbors(fail);
		NeighborAttachment na;

		for (int i = 0; i < nas.size(); i++) {
			na = (NeighborAttachment) nas.elementAt(i);
			na.destroy();
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("EDCAdaptiveGridTraverseRingEventUseIteratorFailureAware ");
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
