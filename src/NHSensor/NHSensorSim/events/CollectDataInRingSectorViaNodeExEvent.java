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
import NHSensor.NHSensorSim.shape.RingSector;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shape.ShapeUtil;
import NHSensor.NHSensorSim.shapeTraverse.TraverseRingIterator;

/*
 * The difference between CollectDataInShapeViaNodeExEvent and
 * CollectDataInShapeViaNodeEvent is that CollectDataInShapeViaNodeExEvent
 * has a endRingSector member variable
 */

public class CollectDataInRingSectorViaNodeExEvent extends
		CollectDataInShapeEvent {
	static Logger logger = Logger
			.getLogger(CollectDataInRingSectorViaNodeExEvent.class);
	protected NeighborAttachment queryNa;
	protected RingSector endRingSector;
	// NOTE: region contains endRect and shape
	protected TraverseRingIterator traverseRingIterator;

	public CollectDataInRingSectorViaNodeExEvent(NeighborAttachment root,
			NeighborAttachment queryNa, Shape shape,
			TraverseRingIterator traverseRingIterator,
			boolean isSendQueryMesage, Message message, Algorithm alg) {
		super(root, shape, isSendQueryMesage, message, alg);
		this.queryNa = queryNa;
		this.traverseRingIterator = traverseRingIterator;
		this.endRingSector = (RingSector) this.shape;
	}

	public RingSector getRingSector() {
		return (RingSector) this.getShape();
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

			Vector dataNodes = this.endNeighborAttachment
					.getNeighbors(this.traverseRingIterator.getRing());
			if (dataNodes.isEmpty()) {
				this.setSucceed(true);
				return;
			}
			RingSector firstRingSector = ShapeUtil.firstRingSectorInRingSector(
					this.getRingSector(), this.endNeighborAttachment);
			NeighborAttachment firstNa = ShapeUtil.firstNodeInRingSector(this
					.getRingSector(), this.endNeighborAttachment);
			RingSector nextRingSector = (RingSector) this.traverseRingIterator
					.getNextShape(firstNa, firstRingSector);
			NeighborAttachment nextQueryNa = this.traverseRingIterator
					.getNextNeighborAttachment(firstNa, firstRingSector);

			if (nextQueryNa != null
					&& nextQueryNa != this.endNeighborAttachment) {
				this.endRingSector = firstRingSector.add(nextRingSector);
				this.shape = this.endRingSector;

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

				GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(
						this.endNeighborAttachment, nextQueryNa.getNode()
								.getPos(), this.getMessage(), this.getAlg());
				gftpe.setSendQueryTag();
				this.getAlg().getSimulator().handle(gftpe);
				this.getRoute().addAll(gftpe.getRoute());

				logger.debug("greedy forward to  "
						+ nextQueryNa.getNode().getPos());
				this.endNeighborAttachment = (NeighborAttachment) gftpe
						.getLastNode();
				this.queryNa = this.endNeighborAttachment;
				if (!gftpe.isSuccess()) {
					logger.debug("greedy forward to node failed");
					this.setSucceed(true);
					return;
				}
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
									.getANSWER_SIZE(),
									this.endNeighborAttachment,
									this.endNeighborAttachment));
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

	public RingSector getEndRingSector() {
		return endRingSector;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CollectDataInRingSectorViaNodeExEvent ");
		sb.append(this.root.getNode().getPos());
		if (this.queryNa != null) {
			sb.append(" via ");
			sb.append(this.queryNa.getNode().getPos());
		}
		sb.append(" ---");
		sb.append(this.shape);
		return sb.toString();
	}
}
