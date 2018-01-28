package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shapeTraverse.EDCCircleTraverseRectIteratorCDA;
import NHSensor.NHSensorSim.shapeTraverse.TraverseRectByCircleIterator;

public class EDCCircleTraverseRectEventCDAOld extends TraverseRectEvent {
	static Logger logger = Logger
			.getLogger(EDCCircleTraverseRectEventCDAOld.class);
	protected int direction;

	public EDCCircleTraverseRectEventCDAOld(Message message, Algorithm alg) {
		super(message, alg);
	}

	public EDCCircleTraverseRectEventCDAOld(NeighborAttachment root, Rect rect,
			int direction, Message message, Algorithm alg) {
		super(root, rect, message, alg);
		this.direction = direction;
	}

	public EDCCircleTraverseRectEventCDAOld(NeighborAttachment root, Rect rect,
			int direction, Message message, boolean sendQuery, Algorithm alg) {
		super(root, rect, message, sendQuery, alg);
		this.direction = direction;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curNa = this.root;
		Shape curShape = null;
		NeighborAttachment nextNa;
		Shape nextShape;
		TraverseRectByCircleIterator rti = new EDCCircleTraverseRectIteratorCDA(
				this.alg, this.direction, this.rect);

		CollectDataInShapeViaNodeEvent cde;
		boolean isSendQueryMessage = this.isSendQuery();
		Message queryMessage = new Message(this.getAlg().getParam()
				.getQUERY_MESSAGE_SIZE(), null);
		Message m;
		boolean firstTime = true;

		logger.debug("EDCCircleTraverseRectEventCDAOld start");
		do {
			nextNa = rti.getNextNeighborAttachment(curNa, curShape);
			nextShape = rti.getNextShape(curNa, curShape);

			if (isSendQueryMessage) {
				m = queryMessage;
			} else {
				m = this.message;
			}

			cde = new CollectDataInShapeViaNodeEvent(curNa, nextNa, nextShape,
					m, this.alg);
			this.getAlg().getSimulator().handle(cde);
			this.getRoute().addAll(cde.getRoute());

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
		logger.debug("EDCCircleTraverseRectEventCDAOld end");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("EDCCircleTraverseRectEventCDAOld ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getRect());
		return sb.toString();
	}
}
