package NHSensor.NHSensorSim.events;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.MessageSegment;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.Shape;

/*
 * for grid traverse
 */
public class CollectDataInShapeViaNodeEventForERISTFailureAware extends
		CollectDataInShapeEvent {
	static Logger logger = Logger
			.getLogger(CollectDataInShapeViaNodeEventForERISTFailureAware.class);
	protected NeighborAttachment queryNa;

	public CollectDataInShapeViaNodeEventForERISTFailureAware(
			NeighborAttachment root, NeighborAttachment queryNa, Shape shape,
			Message message, Algorithm alg) {
		super(root, shape, message, alg);
		this.queryNa = queryNa;
	}

	public CollectDataInShapeViaNodeEventForERISTFailureAware(
			NeighborAttachment root, NeighborAttachment queryNa, Shape shape,
			boolean sendQueryMessageFirst, Message message, Algorithm alg) {
		super(root, shape, sendQueryMessageFirst, message, alg);
		this.queryNa = queryNa;
	}

	public void handle() throws SensornetBaseException {
		if (this.queryNa != null) {
			GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(
					this.root, this.queryNa.getNode().getPos(), this
							.getGreedyMessage(), this.getAlg());
			gftpe.setSendQueryTag();
			this.getAlg().getSimulator().handle(gftpe);
			this.setRoute(gftpe.getRoute());

			logger.debug("greedy forward to  "
					+ this.queryNa.getNode().getPos());
			this.endNeighborAttachment = (NeighborAttachment) gftpe
					.getLastNode();

			if (!gftpe.isSuccess()) {
				logger.debug("greedy forward to node failed");
				this.setSucceed(true);
				return;
			}
		} else {
			Circle rootRadioCircle = new Circle(this.root.getPos(), this
					.getAlg().getParam().getRADIO_RANGE());
			if (rootRadioCircle.contains(this.shape)
					&& this.root.getNeighbors(this.shape).size() == 0) {
				this.endNeighborAttachment = this.root;
				return;
			}

			GreedyForwardToShapeEvent gftse = new GreedyForwardToShapeEvent(
					this.root, this.shape, this.getGreedyMessage(), this
							.getAlg());
			gftse.setSendQueryTag();
			this.getAlg().getSimulator().handle(gftse);
			this.setRoute(gftse.getRoute());

			logger.debug("greedy forward to  " + this.shape);
			this.endNeighborAttachment = (NeighborAttachment) gftse
					.getLastNode();

			if (!gftse.isSuccess()) {
				logger.debug("greedy forward to shape failed");
				this.setSucceed(true);
				return;
			}
		}
		//
		DrawShapeEvent dse = new DrawShapeEvent(this.getAlg(), this.shape);
		this.getAlg().getSimulator().handle(dse);

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

		Vector dataNodes = this.endNeighborAttachment.getNeighbors(this.shape);
		if (dataNodes.isEmpty()) {
			this.setSucceed(true);
			return;
		}

		// broadcast query message
		if (this.queryNa == null || this.shape == null) {
			Message queryMessage = new Message(this.getAlg().getParam()
					.getQUERY_MESSAGE_SIZE(), null);
			BroadcastEvent queryEvent = new BroadcastEvent(
					this.endNeighborAttachment, null, queryMessage, this
							.getAlg());
			queryEvent.setSendQueryTag();
			this.getAlg().getSimulator().handle(queryEvent);
		}

		// collect data node data
		BroadcastEvent answerEvent;
		NeighborAttachment dataNode;
		Message answerMessage = new Message(this.getAlg().getParam()
				.getANSWER_SIZE(), null);
		for (int i = 0; i < dataNodes.size(); i++) {
			dataNode = (NeighborAttachment) dataNodes.elementAt(i);
			if (!dataNode.isAlive() || dataNode.isDestroy())
				continue;
			else {
				answerEvent = new BroadcastEvent(dataNode,
						this.endNeighborAttachment, answerMessage, this
								.getAlg());
				answerEvent.setSendAnswerTag();
				this.getAlg().getSimulator().handle(answerEvent);
				dataNode.setHasSendAnswer(true);
				this.getAlg().addAnswerAttachment(dataNode);

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

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CollectDataInShapeViaNodeEventForERISTFailureAware ");
		sb.append(this.root.getNode().getPos());
		if (this.queryNa != null) {
			sb.append(" via ");
			sb.append(this.queryNa.getNode().getPos());
		}
		sb.append(" ---");
		sb.append(this.shape);
		sb.append(" SendFirstSize:");
		sb.append(this.getGreedyMessage().getTotalSize());
		sb.append(" Size:");
		sb.append(this.getMessage().getTotalSize());
		return sb.toString();
	}
}
