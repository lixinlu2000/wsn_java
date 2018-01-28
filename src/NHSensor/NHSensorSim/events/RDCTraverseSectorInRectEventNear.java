package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.SectorDirection;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shapeTraverse.RDCTraverseSectorInRectIteratorNear;

public class RDCTraverseSectorInRectEventNear extends TraverseSectorInRectEvent {
	static Logger logger = Logger
			.getLogger(RDCTraverseSectorInRectEventNear.class);
	NeighborAttachment cur;

	public RDCTraverseSectorInRectEventNear(Message message, Algorithm alg) {
		super(message, alg);
	}

	public RDCTraverseSectorInRectEventNear(NeighborAttachment root,
			NeighborAttachment cur, SectorInRect sectorInRect, Message message,
			Algorithm alg) {
		super(root, sectorInRect, SectorDirection.NEAR, message, alg);
		this.cur = cur;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.cur;
		Shape curShape = this.sectorInRect.caculateLineLineInSectorInRect(curNa.getPos(), this.getDirection());
		NeighborAttachment nextNa;
		Shape nextShape;
		RDCTraverseSectorInRectIteratorNear rti = new RDCTraverseSectorInRectIteratorNear(
				this.alg, this.sectorInRect);

		CollectDataInShapeViaNodeEventForHSA cde;
		boolean isSendQueryMessage = false;
		boolean firstTime = true;

		logger.debug("RDCTraverseSectorInRectEventNear start");
		do {
			if (rti.isEnd(curNa, curShape)) {
				this.setSuccess(true);
				this.setLastNode(curNa);
				break;
			}

			nextNa = rti.getNextNeighborAttachment(curNa, curShape);
			nextShape = rti.getNextShape(curNa, curShape);

			cde = new CollectDataInShapeViaNodeEventForHSA(curNa, nextNa, nextShape, this.root,
					 rti, isSendQueryMessage, this.message, this.alg);
			cde.setAggregation(false);
			this.getAlg().getSimulator().handle(cde);
			this.getRoute().addAll(cde.getRoute());

			curNa = cde.getEndNeighborAttachment();
			curShape = cde.getEndShape();

		} while (true);

		logger.debug("RDCTraverseSectorInRectEventNear end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("RDCTraverseSectorInRectEventNear ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getSectorInRect());
		return sb.toString();
	}
}
