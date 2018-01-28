package NHSensor.NHSensorSim.events;

import java.util.Collections;
import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shapeTraverse.Direction;
import NHSensor.NHSensorSim.shapeTraverse.NodePositionComparator;

/*
 * for link aware grid traverse rect event
 */
public class LinkAwareCollectDataInRectAroundNodeEvent extends
		CollectDataInShapeEvent {
	static Logger logger = Logger
			.getLogger(LinkAwareCollectDataInRectAroundNodeEvent.class);
	protected NeighborAttachment queryNa;
	protected Rect endRect;
	protected int direction;

	public LinkAwareCollectDataInRectAroundNodeEvent(NeighborAttachment root,
			NeighborAttachment queryNa, Rect rect, int direction,
			Message message, Algorithm alg) {
		super(root, rect, message, alg);
		this.queryNa = queryNa;
		this.endRect = rect;
		this.direction = direction;
	}

	public void handle() throws SensornetBaseException {
		//
		DrawShapeEvent dse = new DrawShapeEvent(this.getAlg(), this.shape);
		this.getAlg().getSimulator().handle(dse);

		if (this.queryNa != null) {
			LinkAwareGreedyForwardToPointEvent gftpe = new LinkAwareGreedyForwardToPointEvent(
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
				this.setSucceed(false);
				return;
			}

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
				 * this.getAlg().getSimulator().handle(nodeHasQueryResultEvent);
				 * }
				 */
			}

			AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
					this.shape);
			Vector dataNodes = this.endNeighborAttachment.getNeighbors(this
					.getAlg().getParam().getRADIO_RANGE(), nairc);
			if (dataNodes.isEmpty()) {
				this.setSucceed(true);
				return;
			}

			// broadcast query message
			Message queryMessage = new Message(this.getAlg().getParam()
					.getQUERY_MESSAGE_SIZE(), null);
			BroadcastEvent queryEvent = new BroadcastEvent(
					this.endNeighborAttachment, queryMessage, this.shape, this
							.getAlg());
			queryEvent.setSendQueryTag();
			this.getAlg().getSimulator().handle(queryEvent);

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
					/*
					 * this.getMessage().addMessageSegment( new
					 * MessageSegment(this
					 * .getAlg().getParam().getANSWER_SIZE(),dataNode
					 * ,dataNode));
					 */

					if (GlobalConstants.getInstance().isDebug()) {
						NodeHasQueryResultEvent nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
								this.getAlg(), dataNode);
						this.getAlg().getSimulator().handle(
								nodeHasQueryResultEvent);
					}
				}
			}
			this.setSucceed(true);
		} else {
			LinkAwareGreedyForwardToShapeEvent gftse = new LinkAwareGreedyForwardToShapeEvent(
					this.root, this.shape, this.getMessage(), this.getAlg());
			gftse.setSendQueryTag();
			this.getAlg().getSimulator().handle(gftse);
			this.setRoute(gftse.getRoute());

			logger.debug("greedy forward to  " + this.shape);
			this.endNeighborAttachment = (NeighborAttachment) gftse
					.getLastNode();
			if (!gftse.isSuccess()) {
				logger.debug("greedy forward to shape failed");
				this.setSucceed(false);
				return;
			}

			AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
					this.shape);
			Vector dataNodes = this.endNeighborAttachment.getNeighbors(this
					.getAlg().getParam().getRADIO_RANGE(), nairc);
			if (dataNodes.isEmpty()) {
				this.setSucceed(false);
				return;
			}
			Collections.sort(dataNodes, new NodePositionComparator(
					this.direction));
			Node node = ((Attachment) dataNodes.elementAt(0)).getNode();

			LinkAwareGreedyForwardToPointEvent gftpe = new LinkAwareGreedyForwardToPointEvent(
					this.endNeighborAttachment, node.getPos(), this
							.getMessage(), this.getAlg());
			gftpe.setSendQueryTag();
			this.getAlg().getSimulator().handle(gftpe);
			this.getRoute().addAll(gftpe.getRoute());

			logger.debug("greedy forward to  " + node.getPos());
			this.endNeighborAttachment = (NeighborAttachment) gftpe
					.getLastNode();

			if (!gftpe.isSuccess()) {
				logger.debug("greedy forward to node failed");
				this.setSucceed(false);
				return;
			}

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
				 * this.getAlg().getSimulator().handle(nodeHasQueryResultEvent);
				 * }
				 */
			}

			Node endNode = this.endNeighborAttachment.getNode();
			Rect rect = (Rect) this.shape;
			switch (this.direction) {
			case Direction.LEFT:
			case Direction.RIGHT:
				this.endRect = new Rect(endNode.getPos().getX() - 0.01, endNode
						.getPos().getX() + 0.01, rect.getMinY(), rect.getMaxY());
				break;
			case Direction.UP:
			case Direction.DOWN:
				double y = endNode.getPos().getY();
				this.endRect = new Rect(rect.getMinX(), rect.getMaxX(),
						y - 0.01, y + 0.01);
				break;
			default:
				;
			}
			this.setSucceed(true);
		}
	}

	public Rect getEndRect() {
		return endRect;
	}

	public void setEndRect(Rect endRect) {
		this.endRect = endRect;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LinkAwareCollectDataInRectAroundNodeEvent ");
		sb.append(this.root.getNode().getPos());

		sb.append(" in shape ");
		if (this.shape != null) {
			sb.append(this.shape);
		} else {
			sb.append("null");
		}

		sb.append(" via node ");
		if (this.queryNa != null) {
			sb.append(this.queryNa.getNode().getPos());
		} else {
			sb.append("null");
		}
		return sb.toString();
	}
}
