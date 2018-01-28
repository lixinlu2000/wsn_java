package NHSensor.NHSensorSim.events;

import java.util.Vector;
import org.apache.log4j.Logger;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Shape;

/*
 * for link aware grid traverse rect event
 */
public class LinkAwareCollectDataInShapeAroundNodeEvent extends
		CollectDataInShapeEvent {
	static Logger logger = Logger
			.getLogger(LinkAwareCollectDataInShapeAroundNodeEvent.class);
	protected NeighborAttachment queryNa;

	public LinkAwareCollectDataInShapeAroundNodeEvent(NeighborAttachment root,
			NeighborAttachment queryNa, Shape shape, Message message,
			Algorithm alg) {
		super(root, shape, message, alg);
		this.queryNa = queryNa;
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

			logger.debug("greedy forward to  "
					+ this.queryNa.getNode().getPos());
			this.endNeighborAttachment = (NeighborAttachment) gftpe
					.getLastNode();

			if (!gftpe.isSuccess()) {
				logger.debug("greedy forward to node failed");
				this.setSucceed(false);
				return;
			}
		} else {
			LinkAwareGreedyForwardToShapeEvent gftse = new LinkAwareGreedyForwardToShapeEvent(
					this.root, this.shape, this.getMessage(), this.getAlg());
			gftse.setSendQueryTag();
			this.getAlg().getSimulator().handle(gftse);

			logger.debug("greedy forward to  " + this.shape);
			this.endNeighborAttachment = (NeighborAttachment) gftse
					.getLastNode();

			if (!gftse.isSuccess()) {
				logger.debug("greedy forward to shape failed");
				this.setSucceed(false);
				return;
			}
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
			 * this.getAlg().getSimulator().handle(nodeHasQueryResultEvent); }
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
		this.setSucceed(true);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LinkAwareCollectDataInShapeAroundNodeEvent ");
		sb.append(this.root.getNode().getPos());

		if (this.shape != null) {
			sb.append(" in shape ");
			sb.append(this.shape);
		}

		if (this.queryNa != null) {
			sb.append(" via node ");
			sb.append(this.queryNa.getNode().getPos());
		}
		return sb.toString();
	}
}
