package NHSensor.NHSensorSim.events;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.NodeFailureErrorException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Shape;

/*
 * for iwqe
 */
public class IdealCollectDataAroundNodeOrShapeEventFailureAware extends
		CollectDataInShapeEvent {
	static Logger logger = Logger
			.getLogger(IdealCollectDataAroundNodeOrShapeEventFailureAware.class);
	protected NeighborAttachment queryNa;
	protected Rect rect; // the collected data must in rect

	public IdealCollectDataAroundNodeOrShapeEventFailureAware(
			NeighborAttachment root, NeighborAttachment queryNa, Shape shape,
			Rect rect, Message message, Algorithm alg) {
		super(root, shape, message, alg);
		this.queryNa = queryNa;
		this.rect = rect;
	}

	public void handle() throws SensornetBaseException {

		if (this.shape != null) {
			GreedyForwardToShapeEventFailureAware gftse = new GreedyForwardToShapeEventFailureAware(
					this.root, this.shape, this.getMessage(), this.getAlg());
			gftse.setSendQueryTag();
			this.getAlg().getSimulator().handle(gftse);
			this.getRoute().addAll(gftse.getRoute());

			logger.debug("greedy forward to  " + this.shape);
			this.endNeighborAttachment = (NeighborAttachment) gftse
					.getLastNode();

			if (!gftse.isSuccess()) {
				logger.debug("greedy forward to shape failed");
				this.setSucceed(false);
				return;
			}
		} else {
			if (this.queryNa != null) {
				GreedyForwardToPointEventFailureAware gftpe = new GreedyForwardToPointEventFailureAware(
						this.root, this.queryNa.getNode().getPos(), this
								.getMessage(), this.getAlg());
				gftpe.setSendQueryTag();
				this.getAlg().getSimulator().handle(gftpe);
				this.getRoute().addAll(gftpe.getRoute());

				logger.debug("greedy forward to  "
						+ this.queryNa.getNode().getPos());
				this.endNeighborAttachment = (NeighborAttachment) gftpe
						.getLastNode();

				if (!gftpe.isSuccess()) {
					logger.debug("greedy forward to node failed");
					this.setSucceed(false);
					return;
				}
			}
		}
		//

		if (!this.endNeighborAttachment.isAlive()) {
			throw new NodeFailureErrorException(this.endNeighborAttachment);
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
				this.rect);
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
				this.endNeighborAttachment, null, queryMessage, this.getAlg());
		queryEvent.setSendQueryTag();
		this.getAlg().getSimulator().handle(queryEvent);

		// collect data node data
		BroadcastEvent answerEvent;
		NeighborAttachment dataNode;
		Message answerMessage = new Message(this.getAlg().getParam()
				.getANSWER_SIZE(), null);
		for (int i = 0; i < dataNodes.size(); i++) {
			dataNode = (NeighborAttachment) dataNodes.elementAt(i);
			// Here is the difference
			if (dataNode.isHasSendAnswer() || !dataNode.isAlive())
				continue;
			else {
				answerEvent = new BroadcastEvent(dataNode,
						this.endNeighborAttachment, answerMessage, this
								.getAlg());
				answerEvent.setSendAnswerTag();
				this.getAlg().getSimulator().handle(answerEvent);
				dataNode.setHasSendAnswer(true);
				double receiveAQueryFrameSuccessRate = this.getAlg()
						.getNetwork().getLinkPRR(
								this.endNeighborAttachment.getNode(),
								dataNode.getNode());
				double receiveQuerySuccessRate = Math.pow(
						receiveAQueryFrameSuccessRate, queryEvent
								.getPacketFrameNum());
				double sendAAnswerFrameSuccessRate = this.getAlg().getNetwork()
						.getLinkPRR(dataNode.getNode(),
								this.endNeighborAttachment.getNode());
				double sendAnswerSuccessRate = Math.pow(
						sendAAnswerFrameSuccessRate, answerEvent
								.getPacketFrameNum());
				double totalSuccessRate = receiveQuerySuccessRate
						* sendAnswerSuccessRate;
				this.getAlg().addAnswerAttachment(dataNode, totalSuccessRate);
				// this.getAlg().addAnswerAttachment(dataNode);
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
		sb.append("IdealCollectDataAroundNodeOrShapeEvent ");
		sb.append(this.root.getNode().getPos());

		if (this.shape != null) {
			sb.append(" via shape ");
			sb.append(this.shape);
		} else if (this.queryNa != null) {
			sb.append(" via node ");
			sb.append(this.queryNa.getNode().getPos());
		}
		return sb.toString();
	}
}