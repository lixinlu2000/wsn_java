package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shapeTraverse.IWQETraverseRectIterator;

/*
 * 
 */
public class IdealItineraryTraverseRectEvent extends TraverseRectEvent {
	static Logger logger = Logger
			.getLogger(IdealItineraryTraverseRectEvent.class);
	protected int direction;

	public IdealItineraryTraverseRectEvent(Message message, Algorithm alg) {
		super(message, alg);
	}

	public IdealItineraryTraverseRectEvent(NeighborAttachment root, Rect rect,
			int direction, Message message, Algorithm alg) {
		super(root, rect, message, alg);
		this.direction = direction;
	}

	public IdealItineraryTraverseRectEvent(NeighborAttachment root, Rect rect,
			int direction, Message message, boolean sendQuery, Algorithm alg) {
		super(root, rect, message, sendQuery, alg);
		this.direction = direction;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		Rect curRect = null;
		NeighborAttachment nextNa;
		Rect nextRect;
		IWQETraverseRectIterator ti = new IWQETraverseRectIterator(this.alg,
				this.direction, this.rect);

		IdealCollectDataAroundNodeOrShapeEvent cde;
		boolean isFirst = true;
		boolean firstTime = true;
		boolean isSendQueryMessage = this.isSendQuery();
		Message queryMessage = new Message(this.getAlg().getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		Message m;

		logger.debug("IdealItineraryTraverseRectEvent start");
		do {
			nextNa = ti.getNextNeighborAttachment(curNa, curRect);
			if (isFirst) {
				nextRect = ti.getInitialDestRect();
				isFirst = false;
			} else {
				nextRect = ti.getNextRect(curNa, curRect);
			}

			if (isSendQueryMessage) {
				m = queryMessage;
			} else {
				m = this.message;
			}

			cde = new IdealCollectDataAroundNodeOrShapeEvent(curNa, nextNa,
					nextRect, this.rect, m, this.alg);
			this.getAlg().getSimulator().handle(cde);
			this.getRoute().add(cde.getRoute());

			// boolean firstTime = true;
			if (firstTime) {
				this.setFirstPhaseRoute(cde.getRoute());
				firstTime = false;
			}

			curNa = cde.getEndNeighborAttachment();
			curRect = nextRect;

			if (isSendQueryMessage && curRect != null
					&& curRect.contains(curNa.getNode().getPos())) {
				isSendQueryMessage = false;
				this.setSendQuery(false);
			}

			if (ti.isEnd(curNa, curRect)) {
				this.setSuccess(true);
				this.setLastNode(curNa);
				break;
			}
		} while (true);
		logger.debug("IdealItineraryTraverseRectEvent end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("IdealItineraryTraverseRectEvent ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getRect());
		return sb.toString();
	}

}