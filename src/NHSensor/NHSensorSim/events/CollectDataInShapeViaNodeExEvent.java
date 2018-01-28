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
import NHSensor.NHSensorSim.shapeTraverse.TraverseShapeOptimizeIterator;

/*
 * for grid traverse
 */
public class CollectDataInShapeViaNodeExEvent extends CollectDataInShapeEvent {
	static Logger logger = Logger
			.getLogger(CollectDataInShapeViaNodeExEvent.class);
	protected NeighborAttachment queryNa;
	protected Shape endShape;
	protected TraverseShapeOptimizeIterator traverseShapeOptimizeIterator;

	public CollectDataInShapeViaNodeExEvent(NeighborAttachment root,
			NeighborAttachment queryNa, Shape shape,
			TraverseShapeOptimizeIterator traverseShapeOptimizeIterator,
			Message message, Algorithm alg) {
		super(root, shape, message, alg);
		this.traverseShapeOptimizeIterator = traverseShapeOptimizeIterator;
		this.queryNa = queryNa;
		this.endShape = shape;
	}

	public CollectDataInShapeViaNodeExEvent(NeighborAttachment root,
			NeighborAttachment queryNa, Shape shape,
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

			NeighborAttachment nextNa = this.traverseShapeOptimizeIterator
					.getOptimizeNextNeighborAttachment(
							this.endNeighborAttachment, this.shape);
			this.endShape = this.traverseShapeOptimizeIterator
					.getOptimizedNextShape(this.endNeighborAttachment,
							this.shape);
			if (this.endShape == null) {
				System.out.println("null");
				this.endShape = this.traverseShapeOptimizeIterator
						.getOptimizedNextShape(this.endNeighborAttachment,
								this.shape);

			}
			this.shape = this.endShape;

			if (nextNa != this.endNeighborAttachment) {
				GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(
						this.endNeighborAttachment, nextNa.getNode().getPos(),
						this.getGreedyMessage(), this.getAlg());
				gftpe.setSendQueryTag();
				this.getAlg().getSimulator().handle(gftpe);
				this.getRoute().addAll(gftpe.getRoute());
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
						this.endNeighborAttachment, null, queryMessage, this
								.getAlg());
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
		sb.append("CollectDataInShapeViaNodeExEvent ");
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
