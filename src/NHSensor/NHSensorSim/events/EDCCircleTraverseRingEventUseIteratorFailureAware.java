package NHSensor.NHSensorSim.events;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shapeTraverse.EDCCircleTraverseRingIteratorFailureAware;

/*
 * CRISC
 */
public class EDCCircleTraverseRingEventUseIteratorFailureAware extends
		TraverseRingEvent {
	static Logger logger = Logger
			.getLogger(EDCCircleTraverseRingEventUseIteratorFailureAware.class);

	EDCCircleTraverseRingIteratorFailureAware gtri;

	public EDCCircleTraverseRingEventUseIteratorFailureAware(
			NeighborAttachment root, Ring ring, Message message, Algorithm alg) {
		super(root, ring, message, alg);
		gtri = new EDCCircleTraverseRingIteratorFailureAware(alg, root, ring);
	}

	public EDCCircleTraverseRingEventUseIteratorFailureAware(
			NeighborAttachment root, Ring ring, Message message,
			boolean sendQuery, Algorithm alg) {
		super(root, ring, message, sendQuery, alg);
		gtri = new EDCCircleTraverseRingIteratorFailureAware(alg, root, ring);
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		Shape curShape = null;
		NeighborAttachment nextNa;
		Shape nextShape;

		CollectDataInShapeViaNodeEventFailureAware cde;
		boolean isSendQueryMessage = this.isSendQuery();
		boolean firstTime = true;

		logger.debug("EDCCircleTraverseRingEventUseIteratorFailureAware start");
		do {
			if (gtri.isEnd(curNa, curShape)) {
				this.setSuccess(true);
				this.setLastNode(curNa);
				break;
			}

			nextNa = gtri.getNextNeighborAttachment(curNa, curShape);
			nextShape = gtri.getNextShape(curNa, curShape);

			cde = new CollectDataInShapeViaNodeEventFailureAware(curNa, nextNa,
					nextShape, isSendQueryMessage, this.message, this.alg);
			cde.setAggregation(false);
			this.getAlg().getSimulator().handle(cde);
			this.getRoute().addAll(cde.getRoute());

			// boolean firstTime = true;
			if (firstTime) {
				this.setFirstPhaseRoute(cde.getRoute());
				firstTime = false;
			}

			if (nextNa != null) {
				this.backupNodeNum += gtri.getRepairedNeighborAttachmentNum(
						curNa, nextNa, nextShape);
			}

			if (nextNa != null && !nextNa.isAlive()) {
				curShape = gtri.getRepairedShape(curNa, nextNa, nextShape);
				curNa = gtri.getRepairedNeighborAttachment(curNa, nextNa,
						nextShape);
				this.updateNodeStatus(nextNa, nextShape);

				// TODO
				if (curNa == null) {
					this.setSuccess(false);
					throw new SensornetBaseException();
				}

				if (curShape != null) {
					DrawShapeEvent dse = new DrawShapeEvent(this.getAlg(),
							curShape);
					this.getAlg().getSimulator().handle(dse);
				}

			} else {
				curNa = cde.getEndNeighborAttachment();
				curShape = nextShape;
			}

			if (isSendQueryMessage && curShape != null && curNa != null
					&& curShape.contains(curNa.getNode().getPos())) {
				isSendQueryMessage = false;
				this.setSendQuery(false);
			}
		} while (true);
		this.backupNodeNum /= this.getQueryNodeNum();
		logger.debug("EDCCircleTraverseRingEventUseIteratorFailureAware end");
	}

	protected void updateNodeStatus(NeighborAttachment nextNa, Shape fail) {
		Vector nas = nextNa.getNeighbors(fail);
		NeighborAttachment na;

		nextNa.destroy();
		for (int i = 0; i < nas.size(); i++) {
			na = (NeighborAttachment) nas.elementAt(i);
			na.destroy();
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("EDCCircleTraverseRingEventUseIteratorFailureAware ");
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
