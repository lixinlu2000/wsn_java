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

public class IDCTraverseSectorInRectEventOld extends TraverseSectorInRectEvent {
	static Logger logger = Logger
			.getLogger(IDCTraverseSectorInRectEventOld.class);

	public IDCTraverseSectorInRectEventOld(Message message, Algorithm alg) {
		super(message, alg);
	}

	public IDCTraverseSectorInRectEventOld(NeighborAttachment root,
			SectorInRect sectorInRect, int direction, Message message,
			Algorithm alg) {
		super(root, sectorInRect, direction, message, alg);
	}

	public IDCTraverseSectorInRectEventOld(NeighborAttachment root,
			SectorInRect sectorInRect, int direction, Message message,
			boolean sendQuery, Algorithm alg) {
		super(root, sectorInRect, direction, message, sendQuery, alg);
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		Shape curShape = null;
		NeighborAttachment nextNa;
		Shape nextShape;
		IDCTraverseSectorInRectIterator rti = new IDCTraverseSectorInRectIterator(
				this.alg, this.sectorInRect, this.direction);

		CollectDataInShapeAndSendQueryToANodeEventForIDC cde;
		boolean isSendQueryMessage = this.isSendQuery();
		Message queryMessage = new Message(this.getAlg().getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		Message m;
		boolean firstTime = true;
		Message collectedAnswers;
		Vector collectedNodes;

		logger.debug("IDCTraverseSectorInRectEvent start");
		do {

			nextNa = rti.getNextNeighborAttachment(curNa, curShape);
			nextShape = rti.getNextShape(curNa, curShape);

			if (isSendQueryMessage) {
				m = queryMessage;
			} else {
				m = this.message;
			}

			cde = new CollectDataInShapeAndSendQueryToANodeEventForIDC(curNa,
					nextNa, nextShape, this.sectorInRect, m, this.alg);
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

			if (rti.isEnd(curNa, curShape)) {
				this.setSuccess(true);
				this.setLastNode(curNa);
				break;
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
		sb.append("---");
		sb.append(this.getSectorInRect());
		return sb.toString();
	}
}
