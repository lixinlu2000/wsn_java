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
import NHSensor.NHSensorSim.shapeTraverse.TraverseShapeOptimizeIterator;

/*
 * for link aware grid traverse ring
 */
public class CollectDataInShapeViaNodeEventFoLACLinkAware extends
		CollectDataInShapeEvent {
	static Logger logger = Logger
			.getLogger(CollectDataInShapeViaNodeEventFoLACLinkAware.class);
	protected NeighborAttachment queryNa;
	protected Shape endShape;
	protected TraverseShapeOptimizeIterator traverseShapeOptimizeIterator;

	public CollectDataInShapeViaNodeEventFoLACLinkAware(
			NeighborAttachment root, NeighborAttachment queryNa, Shape shape,
			TraverseShapeOptimizeIterator traverseShapeOptimizeIterator,
			boolean isSendQueryMessage, Message message, Algorithm alg) {
		super(root, shape, isSendQueryMessage, message, alg);
		this.traverseShapeOptimizeIterator = traverseShapeOptimizeIterator;
		this.queryNa = queryNa;
		this.endShape = shape;
	}

	public void handle() throws SensornetBaseException {
		if (this.queryNa != null) {
			// broadcast query message
			Message queryMessage = new Message(this.getAlg().getParam()
					.getQUERY_MESSAGE_SIZE(), null);
			BroadcastEvent queryEvent = new BroadcastEvent(this.root,
					queryMessage, this.shape, this.getAlg());
			queryEvent.setSendQueryTag();
			this.getAlg().getSimulator().handle(queryEvent);

			Message partialAnwerMessage = new Message(this.message
					.getTotalSize()
					- this.getAlg().getParam().getQUERY_MESSAGE_SIZE(), null);
			BroadcastEvent answerMessageEvent = new BroadcastEvent(this.root,
					this.queryNa, partialAnwerMessage, this.alg);
			answerMessageEvent.setSendAnswerTag();
			this.getAlg().getSimulator().handle(answerMessageEvent);
			this.route.add(queryNa);

			this.endNeighborAttachment = this.queryNa;
		} else {
			Circle rootRadioCircle = new Circle(this.root.getPos(), this
					.getAlg().getParam().getRADIO_RANGE());
			if (rootRadioCircle.contains(this.shape)
					&& this.root.getNeighbors(this.shape).size() == 0) {
				this.endNeighborAttachment = this.root;
				return;
			}

			LinkAwareGreedyForwardToShapeWithBlackListEvent gftse = new LinkAwareGreedyForwardToShapeWithBlackListEvent(
					this.root, this.shape, this.getGreedyMessage(), this
							.getAlg());
			gftse.setSendQueryTag();
			this.getAlg().getSimulator().handle(gftse);
			this.getRoute().addAll(gftse.getRoute());

			logger.debug("greedy forward to  " + this.shape);
			this.endNeighborAttachment = (NeighborAttachment) gftse
					.getLastNode();

			if (!gftse.isSuccess()) {
				logger.debug("greedy forward to shape failed");
				this.setSucceed(true);
				return;
			}

			NeighborAttachment nextNa = this.traverseShapeOptimizeIterator
					.getOptimizeNextNeighborAttachment(
							this.endNeighborAttachment, this.shape);
			this.endShape = this.traverseShapeOptimizeIterator
					.getOptimizedNextShape(this.endNeighborAttachment,
							this.shape);
			this.shape = this.endShape;

			Vector nodesInEndShape = this.endNeighborAttachment
					.getNeighbors(this.endShape);
			if (this.endShape.contains(this.endNeighborAttachment.getPos())) {
				nodesInEndShape.add(this.endNeighborAttachment);
			}

			if (nodesInEndShape.size() == 1) {
				LinkAwareGreedyForwardToPointEvent gftpe = new LinkAwareGreedyForwardToPointEvent(
						this.endNeighborAttachment, nextNa.getNode().getPos(),
						this.getGreedyMessage(), this.getAlg());
				gftpe.setSendQueryTag();
				this.getAlg().getSimulator().handle(gftpe);
				this.getRoute().addAll(gftpe.getRoute());
				this.endNeighborAttachment = nextNa;
			} else if (nextNa != this.endNeighborAttachment) {
				// broadcast query message
				Message queryMessage = new Message(this.getAlg().getParam()
						.getQUERY_MESSAGE_SIZE(), null);
				BroadcastEvent queryEvent = new BroadcastEvent(
						this.endNeighborAttachment, queryMessage, this.shape,
						this.getAlg());
				queryEvent.setSendQueryTag();
				this.getAlg().getSimulator().handle(queryEvent);

				Message partialAnwerMessage = new Message(this.message
						.getTotalSize()
						- this.getAlg().getParam().getQUERY_MESSAGE_SIZE(),
						null);
				BroadcastEvent answerMessageEvent = new BroadcastEvent(
						this.endNeighborAttachment, nextNa,
						partialAnwerMessage, this.alg);
				answerMessageEvent.setSendAnswerTag();
				this.getAlg().getSimulator().handle(answerMessageEvent);
				this.route.add(nextNa);

				this.endNeighborAttachment = nextNa;
			} else {
				Vector dataNodes = this.endNeighborAttachment
						.getNeighbors(this.shape);
				if (dataNodes.isEmpty()) {
					// add my answer
					if (!this.endNeighborAttachment.isHasSendAnswer()) {
						if (this.isCollectAll()) {
							this.getMessage().addMessageSegment(
									new MessageSegment(this.getAlg().getParam()
											.getANSWER_SIZE(),
											this.endNeighborAttachment,
											this.endNeighborAttachment));
						}

						this.endNeighborAttachment.setHasSendAnswer(true);
						this.getAlg().addAnswerAttachment(
								this.endNeighborAttachment);

						if (GlobalConstants.getInstance().isDebug()) {
							NodeHasQueryResultEvent nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
									this.getAlg(), this.endNeighborAttachment);
							this.getAlg().getSimulator().handle(
									nodeHasQueryResultEvent);
						}

					}

					this.setSucceed(true);
					return;
				}

				// broadcast query message
				Message queryMessage = new Message(this.getAlg().getParam()
						.getQUERY_MESSAGE_SIZE(), null);
				BroadcastEvent queryEvent = new BroadcastEvent(
						this.endNeighborAttachment, queryMessage, this.shape,
						this.getAlg());
				queryEvent.setSendQueryTag();
				this.getAlg().getSimulator().handle(queryEvent);
			}
		}
		//
		DrawShapeEvent dse = new DrawShapeEvent(this.getAlg(), this.shape);
		this.getAlg().getSimulator().handle(dse);

		// DEBUG
		// DrawShapeEvent dse1 = new DrawShapeEvent(this.getAlg(),
		// ShapeUtil.caculateMBR(this.shape));
		// this.getAlg().getSimulator().handle(dse1);

		Vector dataNodes = this.endNeighborAttachment.getNeighbors(this.shape);
		if (dataNodes.isEmpty()) {
			// add my answer
			if (!this.endNeighborAttachment.isHasSendAnswer()) {
				if (this.isCollectAll()) {
					this.getMessage().addMessageSegment(
							new MessageSegment(this.getAlg().getParam()
									.getANSWER_SIZE(),
									this.endNeighborAttachment,
									this.endNeighborAttachment));
				}

				this.endNeighborAttachment.setHasSendAnswer(true);
				this.getAlg().addAnswerAttachment(this.endNeighborAttachment);

				if (GlobalConstants.getInstance().isDebug()) {
					NodeHasQueryResultEvent nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
							this.getAlg(), this.endNeighborAttachment);
					this.getAlg().getSimulator()
							.handle(nodeHasQueryResultEvent);
				}

			}
			this.setSucceed(true);
			return;
		}

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

	public Shape getEndShape() {
		return endShape;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CollectDataInShapeViaNodeEventForERISTLinkAware ");
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
