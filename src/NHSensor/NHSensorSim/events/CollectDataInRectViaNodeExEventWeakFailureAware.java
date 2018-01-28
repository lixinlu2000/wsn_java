package NHSensor.NHSensorSim.events;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.NodeFailureErrorException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shape.ShapeUtil;
import NHSensor.NHSensorSim.shapeTraverse.TraverseRectIterator;

/*
 * The difference between CollectDataInShapeViaNodeExEvent and
 * CollectDataInRectViaNodeExEventFailureAwareEx is that 
 * failure aware routing
 */

public class CollectDataInRectViaNodeExEventWeakFailureAware extends
		CollectDataInShapeEvent {
	static Logger logger = Logger
			.getLogger(CollectDataInRectViaNodeExEventWeakFailureAware.class);
	protected NeighborAttachment queryNa;
	protected Rect endRect;
	// NOTE: region contains endRect and shape
	protected TraverseRectIterator traverseRectIterator;

	public CollectDataInRectViaNodeExEventWeakFailureAware(
			NeighborAttachment root, NeighborAttachment queryNa, Shape shape,
			TraverseRectIterator traverseRectIterator, Message message,
			Algorithm alg) {
		super(root, shape, message, alg);
		this.queryNa = queryNa;
		this.traverseRectIterator = traverseRectIterator;
		this.endRect = (Rect) this.shape;
	}

	public Rect getRect() {
		return (Rect) this.getShape();
	}

	public void handle() throws SensornetBaseException {
		if (this.queryNa != null) {
			GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(
					this.root, this.queryNa.getNode().getPos(), this
							.getMessage(), this.getAlg());
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
					this.root, this.shape, this.getMessage(), this.getAlg());
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

			if (!this.endNeighborAttachment.isAlive()) {
				throw new NodeFailureErrorException(this.endNeighborAttachment);
			}

			Vector dataNodes = this.endNeighborAttachment
					.getNeighbors(this.traverseRectIterator.getRect());
			if (dataNodes.isEmpty()) {
				this.setSucceed(true);
				return;
			}
			Rect minRect = ShapeUtil.minRectContainNode(this.getRect(),
					this.traverseRectIterator.getDirection(),
					this.endNeighborAttachment);

			Rect nextRect = this.traverseRectIterator.getNextRect(
					this.endNeighborAttachment, minRect);
			NeighborAttachment nextQueryNa = this.traverseRectIterator
					.getNextNeighborAttachment(this.endNeighborAttachment,
							minRect);

			if (nextQueryNa != null) {
				this.endRect = minRect.add(nextRect, this.traverseRectIterator
						.getDirection());
				this.shape = this.endRect;

				// add my answer
				if (!this.endNeighborAttachment.isHasSendAnswer()) {
					/*
					 * this.getMessage().addMessageSegment( new
					 * MessageSegment(this
					 * .getAlg().getParam().getANSWER_SIZE(),curQueryNode
					 * ,curQueryNode));
					 */
					this.endNeighborAttachment.setHasSendAnswer(true);
					this.getAlg().addAnswerAttachment(
							this.endNeighborAttachment);
					/*
					 * if(GlobalConstants.getInstance().isDebug()) {
					 * nodeHasQueryResultEvent = new
					 * NodeHasQueryResultEvent(this.getAlg(), curQueryNode);
					 * this
					 * .getAlg().getSimulator().handle(nodeHasQueryResultEvent);
					 * }
					 */
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

		DrawShapeEvent dse = new DrawShapeEvent(this.getAlg(), this.shape);
		this.getAlg().getSimulator().handle(dse);

		// add my answer
		if (!this.endNeighborAttachment.isHasSendAnswer()) {
			/*
			 * this.getMessage().addMessageSegment( new
			 * MessageSegment(this.getAlg
			 * ().getParam().getANSWER_SIZE(),curQueryNode,curQueryNode));
			 */
			this.endNeighborAttachment.setHasSendAnswer(true);
			this.getAlg().addAnswerAttachment(this.endNeighborAttachment);
			/*
			 * if(GlobalConstants.getInstance().isDebug()) {
			 * nodeHasQueryResultEvent = new
			 * NodeHasQueryResultEvent(this.getAlg(), curQueryNode);
			 * this.getAlg().getSimulator().handle(nodeHasQueryResultEvent); }
			 */
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
			if (dataNode.isHasSendAnswer() || !dataNode.isAlive())
				continue;
			else {
				answerEvent = new BroadcastEvent(dataNode,
						this.endNeighborAttachment, answerMessage, this
								.getAlg());
				answerEvent.setSendAnswerTag();
				this.getAlg().getSimulator().handle(answerEvent);
				dataNode.setHasSendAnswer(true);
				this.getAlg().addAnswerAttachment(dataNode);
				/*
				 * this.getMessage().addMessageSegment( new
				 * MessageSegment(this.getAlg
				 * ().getParam().getANSWER_SIZE(),dataNode,dataNode));
				 */

				if (GlobalConstants.getInstance().isDebug()) {
					NodeHasQueryResultEvent nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
							this.getAlg(), dataNode);
					this.getAlg().getSimulator()
							.handle(nodeHasQueryResultEvent);
				}
			}
		}
	}

	public Rect getEndRect() {
		return endRect;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CollectDataInRectViaNodeExEventWeakFailureAware ");
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
