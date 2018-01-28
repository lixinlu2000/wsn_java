package NHSensor.NHSensorSim.events;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.NodeFailureErrorException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.Shape;

/*
 * for shape traverse
 */
public class GreedyForwardToShapeForCollectingDataEvent extends
		CollectDataInShapeEvent {
	static Logger logger = Logger
			.getLogger(GreedyForwardToShapeForCollectingDataEvent.class);

	public GreedyForwardToShapeForCollectingDataEvent(NeighborAttachment root,
			Shape shape, Message message, Algorithm alg) {
		super(root, shape, message, alg);
	}

	public GreedyForwardToShapeForCollectingDataEvent(NeighborAttachment root,
			Shape shape, boolean sendQueryMessageFirst, Message message,
			Algorithm alg) {
		super(root, shape, sendQueryMessageFirst, message, alg);
	}

	public void handle() throws SensornetBaseException {
		Circle rootRadioCircle = new Circle(this.root.getPos(), this.getAlg()
				.getParam().getRADIO_RANGE());
		if (rootRadioCircle.contains(this.shape)
				&& this.root.getNeighbors(this.shape).size() == 0) {
			this.endNeighborAttachment = this.root;
			return;
		}

		GreedyForwardToShapeEvent gftse = new GreedyForwardToShapeEvent(
				this.root, this.shape, this.getGreedyMessage(), this.getAlg());
		gftse.setSendQueryTag();
		this.getAlg().getSimulator().handle(gftse);
		this.setRoute(gftse.getRoute());

		logger.debug("greedy forward to  " + this.shape);
		this.endNeighborAttachment = (NeighborAttachment) gftse.getLastNode();

		if (!this.endNeighborAttachment.isAlive()) {
			throw new NodeFailureErrorException(this.endNeighborAttachment);
		}

		if (!gftse.isSuccess()) {
			logger.debug("greedy forward to shape failed");
			this.setSucceed(false);
			return;
		}

		DrawShapeEvent dse = new DrawShapeEvent(this.getAlg(), this.shape);
		this.getAlg().getSimulator().handle(dse);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GreedyForwardToShapeForCollectingDataEvent ");
		sb.append(this.root.getNode().getPos());
		sb.append(" ---");
		sb.append(this.shape);
		sb.append(" SendFirstSize:");
		sb.append(this.getGreedyMessage().getTotalSize());
		sb.append(" Size:");
		sb.append(this.getMessage().getTotalSize());
		return sb.toString();
	}
}
