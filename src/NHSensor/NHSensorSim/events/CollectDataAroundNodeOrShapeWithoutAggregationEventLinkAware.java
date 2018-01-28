package NHSensor.NHSensorSim.events;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.MessageSegment;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Shape;

/*
 * for iwqe traverse ring link aware
 */
public class CollectDataAroundNodeOrShapeWithoutAggregationEventLinkAware
		extends CollectDataInShapeEvent {
	static Logger logger = Logger
			.getLogger(CollectDataAroundNodeOrShapeWithoutAggregationEventLinkAware.class);
	protected NeighborAttachment queryNa;
	protected Shape region; // the collected data must in region

	public CollectDataAroundNodeOrShapeWithoutAggregationEventLinkAware(
			NeighborAttachment root, NeighborAttachment queryNa, Shape shape,
			Shape region, Message message, Algorithm alg) {
		super(root, shape, message, alg);
		this.queryNa = queryNa;
		this.region = region;
		this.isAggregation = false;
	}

	public CollectDataAroundNodeOrShapeWithoutAggregationEventLinkAware(
			NeighborAttachment root, NeighborAttachment queryNa, Shape shape,
			Shape region, boolean isSendQueryMessage, Message message,
			Algorithm alg) {
		super(root, shape, isSendQueryMessage, message, alg);
		this.queryNa = queryNa;
		this.region = region;
		this.isAggregation = false;
	}

	public void handle() throws SensornetBaseException {

		if (this.shape != null) {
			GreedyForwardToShapeEvent gftse = new GreedyForwardToShapeEvent(
					this.root, this.shape, this.getGreedyMessage(), this
							.getAlg());
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
		} else {
			if (this.queryNa != null) {
				GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(
						this.root, this.queryNa.getNode().getPos(), this
								.getGreedyMessage(), this.getAlg());
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
			}
		}
		//

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

		AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
				this.region);
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
			if (dataNode.isHasSendAnswer())
				continue;
			else {
				answerEvent = new BroadcastEvent(dataNode,
						this.endNeighborAttachment, answerMessage, this
								.getAlg());
				answerEvent.setSendAnswerTag();
				this.getAlg().getSimulator().handle(answerEvent);
				dataNode.setHasSendAnswer(true);

//				double receiveAQueryFrameSuccessRate = this.getAlg()
//						.getNetwork().getLinkPRR(
//								this.endNeighborAttachment.getNode(),
//								dataNode.getNode());
//				double receiveQuerySuccessRate = Math.pow(
//						receiveAQueryFrameSuccessRate, queryEvent
//								.getPacketFrameNum());
//				double sendAAnswerFrameSuccessRate = this.getAlg().getNetwork()
//						.getLinkPRR(dataNode.getNode(),
//								this.endNeighborAttachment.getNode());
//				double sendAnswerSuccessRate = Math.pow(
//						sendAAnswerFrameSuccessRate, answerEvent
//								.getPacketFrameNum());
//				double totalSuccessRate = receiveQuerySuccessRate
//						* sendAnswerSuccessRate;
//				this.getAlg().addAnswerAttachment(dataNode, totalSuccessRate);
				
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
		this.setSucceed(true);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb
				.append("CollectDataAroundNodeOrShapeWithoutAggregationEventLinkAware ");
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
