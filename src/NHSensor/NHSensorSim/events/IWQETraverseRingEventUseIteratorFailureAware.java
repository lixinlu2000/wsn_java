package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shapeTraverse.IWQETraverseRingIterator;

public class IWQETraverseRingEventUseIteratorFailureAware extends
		TraverseRingEvent {
	static Logger logger = Logger
			.getLogger(IWQETraverseRingEventUseIteratorFailureAware.class);

	IWQETraverseRingIterator gtri;

	public IWQETraverseRingEventUseIteratorFailureAware(
			NeighborAttachment root, Ring ring, Message message, Algorithm alg) {
		super(root, ring, message, alg);
		gtri = new IWQETraverseRingIterator(alg, root, ring);
	}

	public IWQETraverseRingEventUseIteratorFailureAware(
			NeighborAttachment root, Ring ring, Message message,
			boolean sendQuery, Algorithm alg) {
		super(root, ring, message, sendQuery, alg);
		gtri = new IWQETraverseRingIterator(alg, root, ring);
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		NeighborAttachment prevNa = null;
		Shape curShape = null;
		NeighborAttachment nextNa;
		Shape nextShape;

		CollectDataAroundNodeOrShapeWithoutAggregationEventFailureAware cde;
		boolean isSendQueryMessage = this.isSendQuery();
		boolean firstTime = true;
		boolean isFirst = true;

		logger.debug("IWQETraverseRingEventUseIteratorFailureAware start");
		do {
			if (gtri.isEnd(curNa, curShape, prevNa)) {
				this.setSuccess(true);
				break;
			}

			nextNa = gtri.getNextNeighborAttachment(curNa, curShape);
			if (isFirst) {
				nextShape = gtri.getFirstRingSector();
				isFirst = false;
			} else {
				nextShape = gtri.getNextShape(curNa, curShape);
			}

			cde = new CollectDataAroundNodeOrShapeWithoutAggregationEventFailureAware(
					curNa, nextNa, nextShape, this.ring, isSendQueryMessage,
					this.message, this.alg);
			this.getAlg().getSimulator().handle(cde);
			this.getRoute().addAll(cde.getRoute());

			// boolean firstTime = true;
			if (firstTime) {
				this.setFirstPhaseRoute(cde.getRoute());
				firstTime = false;
			}

			prevNa = curNa;
			curNa = cde.getEndNeighborAttachment();
			curShape = nextShape;

			if (!curNa.isAlive()) {
				this.setSuccess(false);
				throw new SensornetBaseException();
			}

			if (isSendQueryMessage && curShape != null && curNa != null
					&& curShape.contains(curNa.getNode().getPos())) {
				isSendQueryMessage = false;
				this.setSendQuery(false);
			}
		} while (true);
		logger.debug("IWQETraverseRingEventUseIteratorFailureAware end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("IWQETraverseRingEventUseIteratorFailureAware ");
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