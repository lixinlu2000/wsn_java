package NHSensor.NHSensorSim.events;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.MessageSegment;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Shape;

public class CollectDataInShapeEvent extends CollectDataEvent {
	static Logger logger = Logger.getLogger(CollectDataInShapeEvent.class);
	protected NeighborAttachment root;
	protected Shape shape;
	protected NeighborAttachment endNeighborAttachment;
	protected Vector route = new Vector();

	public NeighborAttachment getRoot() {
		return root;
	}

	public void setRoot(NeighborAttachment root) {
		this.root = root;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void setEndNeighborAttachment(
			NeighborAttachment endNeighborAttachment) {
		this.endNeighborAttachment = endNeighborAttachment;
	}

	public CollectDataInShapeEvent(NeighborAttachment root, Shape shape,
			Message message, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.shape = shape;
	}

	public CollectDataInShapeEvent(NeighborAttachment root, Shape shape,
			boolean sendQueryMessageFirst, Message message, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.sendQueryMessageFirst = sendQueryMessageFirst;
		this.shape = shape;
	}

	public void handle() throws SensornetBaseException {
		GreedyForwardToShapeEvent gftse = new GreedyForwardToShapeEvent(
				this.root, this.shape, this.getGreedyMessage(), this.getAlg());
		gftse.setSendQueryTag();
		this.getAlg().getSimulator().handle(gftse);
		this.setRoute(gftse.getRoute());

		logger.debug("greedy forward to  " + this.shape);
		this.endNeighborAttachment = (NeighborAttachment) gftse.getLastNode();

		if (!gftse.isSuccess()) {
			logger.debug("greedy forward to shape failed");
			this.setSucceed(true);
			return;
		}

		// broadcast message
		BroadcastEvent queryEvent = new BroadcastEvent(
				this.endNeighborAttachment, null, this.getMessage(), this
						.getAlg());
		queryEvent.setSendQueryTag();
		this.getAlg().getSimulator().handle(queryEvent);

		// add my answer
		if (!this.endNeighborAttachment.isHasSendAnswer()) {
			if (this.isCollectAll()) {
				this.getMessage().addMessageSegment(
						new MessageSegment(this.getAlg().getParam()
								.getANSWER_SIZE(), this.endNeighborAttachment,
								this.endNeighborAttachment));
			}

			this.endNeighborAttachment.setHasSendAnswer(true);
			this.getAlg().addAnswerAttachment(this.endNeighborAttachment);

			if (GlobalConstants.getInstance().isDebug()) {
				NodeHasQueryResultEvent nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
						this.getAlg(), this.endNeighborAttachment);
				this.getAlg().getSimulator().handle(nodeHasQueryResultEvent);
			}

		}

		// collect data node data
		Vector dataNodes = this.endNeighborAttachment.getNeighbors(this.shape);
		BroadcastEvent answerEvent;
		NeighborAttachment dataNode;
		Message answerMessage = new Message(this.getAlg().getParam()
				.getANSWER_SIZE(), null);
		for (int i = 0; i < dataNodes.size(); i++) {
			dataNode = (NeighborAttachment) dataNodes.elementAt(i);
			if (dataNode.isHasSendAnswer())
				continue;
			else {
				answerEvent = new BroadcastEvent(dataNode,
						this.endNeighborAttachment, answerMessage, this
								.getAlg());
				answerEvent.setSendAnswerTag();
				this.getAlg().getSimulator().handle(answerEvent);
				dataNode.setHasSendAnswer(true);

				if (this.isCollectAll()) {
					this.getMessage().addMessageSegment(
							new MessageSegment(this.getAlg().getParam()
									.getANSWER_SIZE(), dataNode, dataNode));
				}

				if (GlobalConstants.getInstance().isDebug()) {
					NodeHasQueryResultEvent nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
							this.getAlg(), dataNode);
					this.getAlg().getSimulator()
							.handle(nodeHasQueryResultEvent);
				}
			}
		}
	}

	public NeighborAttachment getEndNeighborAttachment() {
		return endNeighborAttachment;
	}

	public Vector getRoute() {
		return route;
	}

	public void setRoute(Vector route) {
		this.route = route;
	}

	public int getRouteSize() {
		return this.route.size();
	}
}
