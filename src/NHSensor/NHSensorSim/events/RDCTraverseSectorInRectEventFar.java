package NHSensor.NHSensorSim.events;

import java.util.Vector;
import org.apache.log4j.Logger;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.IntersectException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.SectorDirection;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shapeTraverse.RDCTraverseSectorInRectIteratorFar;

public class RDCTraverseSectorInRectEventFar extends TraverseSectorInRectEvent {
	static Logger logger = Logger
			.getLogger(RDCTraverseSectorInRectEventFar.class);
	RDCTraverseSectorInRectIteratorFar rti;
	NeighborAttachment cur;

	public RDCTraverseSectorInRectEventFar(NeighborAttachment root,
			SectorInRect sectorInRect, Message message, Algorithm alg) {
		super(root, sectorInRect, SectorDirection.FAR, message, alg);
		rti = new RDCTraverseSectorInRectIteratorFar(this.alg,
				this.sectorInRect);
		this.cur = root;
	}

	public RDCTraverseSectorInRectEventFar(NeighborAttachment root,
			NeighborAttachment cur, SectorInRect sectorInRect, Message message,
			Algorithm alg) {
		super(root, sectorInRect, SectorDirection.FAR, message, alg);
		rti = new RDCTraverseSectorInRectIteratorFar(this.alg,
				this.sectorInRect);
		this.cur = cur;
	}

	public Shape getInitialShape() throws IntersectException {
		return rti.getInitailShape();
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.cur;
		Shape curShape = null;
		NeighborAttachment nextNa;
		Shape nextShape;

		CollectDataInShapeAndSendQueryToANodeEventForRDCFar cde;
		boolean isSendQueryMessage = this.isSendQuery();
		Message queryMessage = new Message(this.getAlg().getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		boolean firstTime = true;
		Message collectedAnswers;
		Vector collectedNodes;

		logger.debug("RDCTraverseSectorInRectEventFar start");
		do {
			nextNa = rti.getNextNeighborAttachment(curNa, curShape);
			nextShape = rti.getNextShape(curNa, curShape);

			if (nextNa == null && nextShape == null) {
				throw new SensornetBaseException();
			}
			cde = new CollectDataInShapeAndSendQueryToANodeEventForRDCFar(
					curNa, nextNa, nextShape, queryMessage, this.alg);
			this.getAlg().getSimulator().handle(cde);
			this.getRoute().addAll(cde.getRoute());

			collectedNodes = cde.getCollectedNodes();
			if (collectedNodes.size() != 0) {
				collectedAnswers = new Message(this.getAlg().getParam()
						.getANSWER_SIZE()
						* collectedNodes.size(), null);
				GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(
						cde.getBackNa(), this.root.getPos(), collectedAnswers,
						this.getAlg());
				this.getAlg().getSimulator().handle(gftpe);
				this.getRoute().addAll(gftpe.getRoute());
			}

			// boolean firstTime = true;
			if (firstTime) {
				this.setFirstPhaseRoute(cde.getRoute());
				firstTime = false;
			}

			curNa = cde.getEndNeighborAttachment();
			curShape = nextShape;

			if (isSendQueryMessage && curShape != null && curNa != null
					&& curShape.contains(curNa.getNode().getPos())) {
				isSendQueryMessage = false;
				this.setSendQuery(false);
			}

			if (rti.isEnd(curNa, curShape)) {
				this.setSuccess(true);
				this.setLastNode(curNa);
				break;
			}
		} while (true);
		logger.debug("RDCTraverseSectorInRectEventFar end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("RDCTraverseSectorInRectEventFar ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getSectorInRect());
		return sb.toString();
	}
}
