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

public class RDCTraverseSectorInRectEventNearApproximate extends TraverseSectorInRectEvent {
	static Logger logger = Logger
			.getLogger(RDCTraverseSectorInRectEventNearApproximate.class);
	NeighborAttachment cur;
	double delta;

	public RDCTraverseSectorInRectEventNearApproximate(Message message, Algorithm alg) {
		super(message, alg);
	}

	public RDCTraverseSectorInRectEventNearApproximate(NeighborAttachment root,
			NeighborAttachment cur, SectorInRect sectorInRect, Message message,
			Algorithm alg,
			double delta) {
		super(root, sectorInRect, SectorDirection.NEAR, message, alg);
		this.cur = cur;
		this.delta = delta;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.cur;
		NeighborAttachment representativeNa =  this.cur;
		Shape curShape = this.sectorInRect.caculateLineLineInSectorInRect(curNa.getPos(), this.getDirection());
		
		this.getAlg().getSimulator().handle(new DrawShapeEvent(this.getAlg(), curShape));
		NeighborAttachment nextNa;
		Shape nextShape;
		RDCTraverseSectorInRectIteratorNear rti = new RDCTraverseSectorInRectIteratorNear(
				this.alg, this.sectorInRect);

		CollectDataInShapeViaNodeEventForHSAApproximate cde = null;
		boolean isSendQueryMessage = false;

		logger.debug("RDCTraverseSectorInRectEventNearApproximate start");
		do {
			if (rti.isEnd(curNa, curShape)) {
				this.setSuccess(true);
				this.setLastNode(curNa);
				break;
			}

			nextNa = rti.getNextNeighborAttachment(curNa, curShape);
			nextShape = rti.getNextShape(curNa, curShape);

			cde = new CollectDataInShapeViaNodeEventForHSAApproximate(curNa, nextNa, nextShape,
					this.root,
					 rti, isSendQueryMessage, this.message, this.alg, representativeNa, delta);
						this.getAlg().getSimulator().handle(cde);
			this.getRoute().addAll(cde.getRoute());

			curNa = cde.getEndNeighborAttachment();
			curShape = cde.getEndShape();
			representativeNa = cde.getNewRepresentativeNa();

		} while (true);

		Message lastDataMessage = new Message(this.getAlg().getParam().getANSWER_SIZE(), null);
		GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(curNa,
				this.root.getPos(), lastDataMessage, this.getAlg());
		this.getAlg().getSimulator().handle(gftpe);
		this.getRoute().addAll(gftpe.getRoute());
		
		if(!representativeNa.isHasSendAnswer()) {
			representativeNa.setHasSendAnswer(true);
			this.getAlg().addAnswerAttachment(representativeNa);
		}
		
		logger.debug("RDCTraverseSectorInRectEventNearApproximate end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("RDCTraverseSectorInRectEventNearApproximate ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getSectorInRect());
		return sb.toString();
	}
}
