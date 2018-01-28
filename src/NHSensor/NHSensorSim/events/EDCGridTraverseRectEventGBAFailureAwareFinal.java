package NHSensor.NHSensorSim.events;

import java.util.Vector;
import org.apache.log4j.Logger;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shapeTraverse.EDCGridTraverseRectIteratorGBAFailureAwareFinal;

public class EDCGridTraverseRectEventGBAFailureAwareFinal extends
		TraverseRectEvent {
	static Logger logger = Logger
			.getLogger(EDCGridTraverseRectEventGBAFailureAwareFinal.class);
	protected int direction;
	int backupNodeNum = 0;

	public EDCGridTraverseRectEventGBAFailureAwareFinal(Message message,
			Algorithm alg) {
		super(message, alg);
	}

	public EDCGridTraverseRectEventGBAFailureAwareFinal(
			NeighborAttachment root, Rect rect, int direction, Message message,
			Algorithm alg) {
		super(root, rect, message, alg);
		this.direction = direction;
	}

	public EDCGridTraverseRectEventGBAFailureAwareFinal(
			NeighborAttachment root, Rect rect, int direction, Message message,
			boolean sendQuery, Algorithm alg) {
		super(root, rect, message, sendQuery, alg);
		this.direction = direction;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		Rect curShape = null;
		NeighborAttachment repairNa;
		NeighborAttachment nextNa;
		Rect repairShape;
		Rect nextShape;
		EDCGridTraverseRectIteratorGBAFailureAwareFinal rti = new EDCGridTraverseRectIteratorGBAFailureAwareFinal(
				this.alg, this.direction, this.rect);

		CollectDataInShapeEvent cde = null;
		boolean isSendQueryMessage = this.isSendQuery();
		boolean firstTime = true;

		logger.debug("EDCGridTraverseRectEventGBAFailureAwareFinal start");
		do {
			if (rti.isEnd(curNa, curShape)) {
				this.setSuccess(true);
				this.setLastNode(curNa);
				break;
			}

			nextNa = rti.getNextNeighborAttachment(curNa, curShape);
			nextShape = rti.getNextRect(curNa, curShape);

			if (nextNa == null) {
				cde = new GreedyForwardToShapeForCollectingDataEvent(curNa,
						nextShape, isSendQueryMessage, this.message, this.alg);
				this.getAlg().getSimulator().handle(cde);
				this.getRoute().addAll(cde.getRoute());
				curNa = cde.getEndNeighborAttachment();
				curShape = nextShape;

				if (firstTime) {
					this.setFirstPhaseRoute(cde.getRoute());
					firstTime = false;
				}

				if (curShape.contains(curNa.getPos())) {
					// add my answer
					if (!curNa.isHasSendAnswer()) {

						curNa.setHasSendAnswer(true);
						this.getAlg().addAnswerAttachment(curNa);

						if (GlobalConstants.getInstance().isDebug()) {
							NodeHasQueryResultEvent nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
									this.getAlg(), curNa);
							this.getAlg().getSimulator().handle(
									nodeHasQueryResultEvent);
						}

					}

					nextNa = rti.getOptimizeNextNeighborAttachment(curNa,
							curShape);
					nextShape = (Rect) rti.getOptimizedNextShape(curNa,
							curShape);
				} else {
					nextNa = null;
				}
			}

			if (nextNa != null) {
				if (curNa == nextNa) {
					nextNa = null;
				}
				cde = new CollectDataInShapeViaNodeEventFailureAwareForCSACDA(
						curNa, nextNa, nextShape, isSendQueryMessage,
						this.message, this.alg);
				this.getAlg().getSimulator().handle(cde);
				this.getRoute().addAll(cde.getRoute());
			}

			// boolean firstTime = true;
			if (firstTime) {
				this.setFirstPhaseRoute(cde.getRoute());
				firstTime = false;
			}

			if (nextNa != null && curNa != nextNa) {
				this.backupNodeNum += rti.getRepairedNeighborAttachmentNum(
						curNa, curShape, nextNa, nextShape);
			}

			if (nextNa != null && !nextNa.isAlive() && curNa != nextNa) {
				repairNa = rti.getRepairedNeighborAttachment(curNa, curShape,
						nextNa, nextShape);
				repairShape = (Rect) rti.getRepairedShape(curNa, curShape,
						nextNa, nextShape);

				if (repairNa == null) {
					this.setSuccess(false);
					throw new SensornetBaseException();
				} else {
					this.updateNodeStatus(curNa, repairShape, nextShape);
					curNa = repairNa;
					curShape = repairShape;

					// TODO
					if (curNa == null) {
						this.setSuccess(false);
						throw new SensornetBaseException();
					}
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
		logger.debug("EDCGridTraverseRectEventGBAFailureAwareFinal end");
	}

	protected void updateNodeStatus(NeighborAttachment prevNa,
			Shape repairShape, Shape nextShape) {
		Vector nasInRepairShape = prevNa.getNeighbors(repairShape);
		Vector nasInNextShape = prevNa.getNeighbors(nextShape);
		NeighborAttachment na;

		for (int i = 0; i < nasInNextShape.size(); i++) {
			na = (NeighborAttachment) nasInNextShape.elementAt(i);
			if (!na.isAlive())
				na.destroy();
		}

		for (int i = 0; i < nasInRepairShape.size(); i++) {
			na = (NeighborAttachment) nasInRepairShape.elementAt(i);
			nasInNextShape.remove(na);
		}

		for (int i = 0; i < nasInNextShape.size(); i++) {
			na = (NeighborAttachment) nasInNextShape.elementAt(i);
			na.setHasSendAnswer(false);
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("EDCGridTraverseRectEventGBAFailureAwareFinal ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getRect());
		return sb.toString();
	}
}
