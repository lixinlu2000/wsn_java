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
 * 
 */
public class CollectDataInShapeViaNodeEventForHSA extends CollectDataInShapeEvent {
	static Logger logger = Logger
			.getLogger(CollectDataInShapeViaNodeEventForHSA.class);
	protected NeighborAttachment queryNa;
	protected NeighborAttachment sink;
	protected Shape endShape;
	protected TraverseShapeOptimizeIterator traverseShapeOptimizeIterator;
	protected Message collectedDataMessage = new Message(0, null);

	public CollectDataInShapeViaNodeEventForHSA(NeighborAttachment root,
			NeighborAttachment queryNa, Shape shape, NeighborAttachment sink,
			TraverseShapeOptimizeIterator traverseShapeOptimizeIterator,
			boolean sendQueryMessageFirst,
			Message message, Algorithm alg) {
		super(root, shape, sendQueryMessageFirst, message, alg);
		this.traverseShapeOptimizeIterator = traverseShapeOptimizeIterator;
		this.queryNa = queryNa;
		this.endShape = shape;
		this.sink = sink;
	}

	public void handle() throws SensornetBaseException {
		//
		DrawShapeEvent dse = new DrawShapeEvent(this.getAlg(), this.shape);
		this.getAlg().getSimulator().handle(dse);

		Message queryMessage = new Message(this.getAlg().getParam().getQUERY_MESSAGE_SIZE(), null);
		if (this.queryNa != null) {
			GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(
					this.root, this.queryNa.getNode().getPos(), queryMessage, this.getAlg());
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
					this.root, this.shape, queryMessage, this
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
						queryMessage, this.getAlg());
				gftpe.setSendQueryTag();
				this.getAlg().getSimulator().handle(gftpe);
				this.getRoute().addAll(gftpe.getRoute());
				this.endNeighborAttachment = nextNa;
			} else {
				Vector dataNodes = this.endNeighborAttachment
						.getNeighbors(this.shape);
				if (!dataNodes.isEmpty()) {
					// broadcast query and partial answer message
					BroadcastEvent queryEvent = new BroadcastEvent(
							this.endNeighborAttachment, null, queryMessage, this
									.getAlg());
					queryEvent.setSendQueryTag();
					this.getAlg().getSimulator().handle(queryEvent);
				}
			}
		}

		//
		dse = new DrawShapeEvent(this.getAlg(), this.shape);
		this.getAlg().getSimulator().handle(dse);
		// DEBUG
		// DrawShapeEvent dse1 = new DrawShapeEvent(this.getAlg(),
		// ShapeUtil.caculateMBR(this.shape));
		// this.getAlg().getSimulator().handle(dse1);

		// add my answer
		if (!this.endNeighborAttachment.isHasSendAnswer()) {
			this.addCollectedData(this.endNeighborAttachment);
			this.endNeighborAttachment.setHasSendAnswer(true);
			this.getAlg().addAnswerAttachment(this.endNeighborAttachment);

			if (GlobalConstants.getInstance().isDebug()) {
				NodeHasQueryResultEvent nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
						this.getAlg(), this.endNeighborAttachment);
				this.getAlg().getSimulator().handle(nodeHasQueryResultEvent);
			}

		}

		// collect data node data
		Vector dataNodes = this.endNeighborAttachment.getNeighbors(this.shape);
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
				this.addCollectedData(dataNode);
				dataNode.setHasSendAnswer(true);
				this.getAlg().addAnswerAttachment(dataNode);

				if (GlobalConstants.getInstance().isDebug()) {
					NodeHasQueryResultEvent nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
							this.getAlg(), dataNode);
					this.getAlg().getSimulator()
							.handle(nodeHasQueryResultEvent);
				}
			}
		}
		
		if(this.collectedDataMessage.getTotalSize()>0) {
			GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(
					this.endNeighborAttachment, sink.getPos(),
					collectedDataMessage, this.getAlg());
			this.getAlg().getSimulator().handle(gftpe);
		}
	}
	
	protected void addCollectedData(NeighborAttachment na) {
		int dataSize = this.getAlg().getParam().getANSWER_SIZE();
		
		this.collectedDataMessage.addMessageSegment(new MessageSegment(dataSize, na, na));
	}

	public Shape getEndShape() {
		return endShape;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CollectDataInShapeViaNodeEventForHSA ");
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
