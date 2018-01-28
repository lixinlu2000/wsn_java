package NHSensor.NHSensorSim.events;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shapeTraverse.IDCTraverseSectorInRectIterator;

public class IDCTraverseSectorInRectEvent extends TraverseSectorInRectEvent {
	static Logger logger = Logger.getLogger(IDCTraverseSectorInRectEvent.class);
	NeighborAttachment cur;

	public IDCTraverseSectorInRectEvent(Message message, Algorithm alg) {
		super(message, alg);
	}

	public IDCTraverseSectorInRectEvent(NeighborAttachment root,
			SectorInRect sectorInRect, int direction, Message message,
			Algorithm alg) {
		super(root, sectorInRect, direction, message, alg);
		this.cur = root;
	}

	public IDCTraverseSectorInRectEvent(NeighborAttachment root,
			NeighborAttachment cur, SectorInRect sectorInRect, int direction,
			Message message, Algorithm alg) {
		super(root, sectorInRect, direction, message, alg);
		this.cur = cur;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.cur;
		Shape curShape;
		NeighborAttachment nextNa;
		Shape nextShape;
		IDCTraverseSectorInRectIterator rti = new IDCTraverseSectorInRectIterator(
				this.alg, this.sectorInRect, this.direction);

		if (this.root == this.cur) {
			curShape = null;
		} else {
			curShape = rti.getInitailShape();
		}

		CollectDataAroundNodeOrShapeAndSendQueryToANodeEventForIDC cde;
		boolean isSendQueryMessage = this.isSendQuery();
		Message queryMessage = new Message(this.getAlg().getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		Message m;
		boolean firstTime = true;
		Message collectedAnswers;
		Vector collectedNodes;

		logger.debug("IDCTraverseSectorInRectEvent start");
		do {

			if (rti.isEnd(curNa, curShape)) {
				this.setSuccess(true);
				this.setLastNode(curNa);
				break;
			}

			nextNa = rti.getNextNeighborAttachment(curNa, curShape);
			nextShape = rti.getNextShape(curNa, curShape);

			if (isSendQueryMessage) {
				m = queryMessage;
			} else {
				m = this.message;
			}

			cde = new CollectDataAroundNodeOrShapeAndSendQueryToANodeEventForIDC(
					curNa, nextNa, nextShape, this.sectorInRect, m, this.alg);
			this.getAlg().getSimulator().handle(cde);
			this.getRoute().addAll(cde.getRoute());

			collectedNodes = cde.getCollectedNodes();
			if (collectedNodes.size() != 0) {
				collectedAnswers = new Message(this.getAlg().getParam()
						.getANSWER_SIZE()
						* collectedNodes.size(), null);
				GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(
						cde.getEndNeighborAttachment(), this.root.getPos(),
						collectedAnswers, this.getAlg());
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

		} while (true);
		logger.debug("IDCTraverseSectorInRectEvent end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("IDCTraverseSectorInRectEvent ");
		sb.append(this.root.getNode().getPos());
		if (this.cur != this.root) {
			sb.append(" cur:");
			sb.append(this.cur.getNode().getPos());
		}
		sb.append("---");
		sb.append(this.getSectorInRect());
		return sb.toString();
	}
}
