package NHSensor.NHSensorSim.events;

import java.awt.Color;
import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.LineSegment;
import NHSensor.NHSensorSim.shape.Shape;

/*
 * for RDC 
 * far traverse
 */
public class CollectDataInShapeAndSendQueryToANodeEventForRDCFar extends
		CollectDataInShapeEvent {
	static Logger logger = Logger
			.getLogger(CollectDataInShapeAndSendQueryToANodeEventForRDCFar.class);
	protected NeighborAttachment queryNa;
	protected Vector collectedNodes = new Vector();
	protected NeighborAttachment backNa;

	public CollectDataInShapeAndSendQueryToANodeEventForRDCFar(
			NeighborAttachment root, NeighborAttachment queryNa, Shape shape,
			Message message, Algorithm alg) {
		super(root, shape, message, alg);
		this.queryNa = queryNa;
	}

	public Vector getCollectedNodes() {
		return collectedNodes;
	}

	public void setCollectedNodes(Vector collectedNodes) {
		this.collectedNodes = collectedNodes;
	}

	public void handle() throws SensornetBaseException {
		if (this.queryNa == null) {
			GreedyForwardToShapeEvent gftse = new GreedyForwardToShapeEvent(
					this.root, this.shape, this.message, this.getAlg());
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
			this.backNa = this.endNeighborAttachment;
		} else {
			this.endNeighborAttachment = root;
			this.backNa = this.endNeighborAttachment;
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
				this.endNeighborAttachment.setHasSendAnswer(true);
				this.getAlg().addAnswerAttachment(this.endNeighborAttachment);
				collectedNodes.add(this.endNeighborAttachment);

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
			this.endNeighborAttachment.setHasSendAnswer(true);
			this.getAlg().addAnswerAttachment(this.endNeighborAttachment);
			collectedNodes.add(this.endNeighborAttachment);

			if (GlobalConstants.getInstance().isDebug()) {
				NodeHasQueryResultEvent nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
						this.getAlg(), this.endNeighborAttachment);
				this.getAlg().getSimulator().handle(nodeHasQueryResultEvent);
			}
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
				this.getAlg().addAnswerAttachment(dataNode);
				collectedNodes.add(this.endNeighborAttachment);

				if (GlobalConstants.getInstance().isDebug()) {
					NodeHasQueryResultEvent nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
							this.getAlg(), dataNode);
					this.getAlg().getSimulator()
							.handle(nodeHasQueryResultEvent);
				}
			}
		}

		if (this.queryNa != null) {
			LineSegment ls = new LineSegment(this.root.getPos(), this.queryNa
					.getPos());
			DrawShapeEvent de = new DrawShapeEvent(this.getAlg(), ls, Color.RED);
			this.getAlg().getSimulator().handle(de);
			this.endNeighborAttachment = this.queryNa;
			this.getRoute().add(this.queryNa);
		}
	}

	public NeighborAttachment getBackNa() {
		return backNa;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CollectDataInShapeAndSendQueryToANodeEventForRDC ");
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
