package NHSensor.NHSensorSim.events;

import java.util.Vector;
import org.apache.log4j.Logger;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.MessageSegment;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shapeTraverse.TraverseShapeOptimizeIterator;

/*
 * 
 */
public class CollectDataInShapeViaNodeEventForHSAApproximate extends CollectDataInShapeEvent {
	static Logger logger = Logger
			.getLogger(CollectDataInShapeViaNodeEventForHSAApproximate.class);
	protected NeighborAttachment queryNa;
	protected NeighborAttachment representativeNa, newRepresentativeNa;
	protected Shape endShape;
	protected TraverseShapeOptimizeIterator traverseShapeOptimizeIterator;
	protected double delta;
	protected Message collectedDataMessage = new Message(0, null);
	protected NeighborAttachment sink;
	

	public CollectDataInShapeViaNodeEventForHSAApproximate(NeighborAttachment root,
			NeighborAttachment queryNa, Shape shape,
			NeighborAttachment sink,
			TraverseShapeOptimizeIterator traverseShapeOptimizeIterator,
			boolean sendQueryMessageFirst,
			Message message, Algorithm alg,
			NeighborAttachment representativeNa,
			double delta) {
		super(root, shape, sendQueryMessageFirst, message, alg);
		this.traverseShapeOptimizeIterator = traverseShapeOptimizeIterator;
		this.queryNa = queryNa;
		this.endShape = shape;
		this.sink = sink;
		this.representativeNa = representativeNa;
		this.newRepresentativeNa = representativeNa;
		this.delta = delta;
	}

	public void handle() throws SensornetBaseException {
		//
		DrawShapeEvent dse = new DrawShapeEvent(this.getAlg(), this.shape);
		this.getAlg().getSimulator().handle(dse);

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
				if (!dataNodes.isEmpty()) {
					// broadcast query and partial answer message
					BroadcastEvent queryEvent = new BroadcastEvent(
							this.endNeighborAttachment, null, this.getGreedyMessage(), this
							.getAlg());
					queryEvent.setSendQueryTag();
					this.getAlg().getSimulator().handle(queryEvent);
				}
			}
		}

		// DEBUG
		// DrawShapeEvent dse1 = new DrawShapeEvent(this.getAlg(),
		// ShapeUtil.caculateMBR(this.shape));
		// this.getAlg().getSimulator().handle(dse1);
		
		// add my answer
		if (!this.endNeighborAttachment.isHasSendAnswer()) {
			if(!this.satisfyDelta(this.endNeighborAttachment, this.getRepresentativeNa())) {
				this.getRepresentativeNa().setHasSendAnswer(true);
				this.getAlg().addAnswerAttachment(this.getRepresentativeNa());
				this.addCollectedData(this.getRepresentativeNa());
				this.setNewRepresentativeNa(this.endNeighborAttachment);
			}
			else {
				this.endNeighborAttachment.setHasSendAnswer(true);
				this.getAlg().addAnswerAttachment(this.endNeighborAttachment);
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
				if(!this.satisfyDelta(dataNode, this.getRepresentativeNa())) {
					answerEvent = new BroadcastEvent(dataNode,
							this.endNeighborAttachment, answerMessage, this
									.getAlg());
					answerEvent.setSendAnswerTag();
					this.getAlg().getSimulator().handle(answerEvent);
					this.addCollectedData(dataNode);
				}
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
		
		if(this.collectedDataMessage.getTotalSize()!=0) {
			GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(
					this.endNeighborAttachment, this.sink.getPos(),
					collectedDataMessage, this.getAlg());
			this.getAlg().getSimulator().handle(gftpe);
		}
	}
	
	protected boolean satisfyDelta(NeighborAttachment na1, NeighborAttachment na2) {
		double d1 = na1.getDefaultData();
		double d2 = na2.getDefaultData();
		if(Math.abs(d1-d2)<=this.delta) return true;
		else return false;
	}
	
	protected void addCollectedData(NeighborAttachment na) {
		int dataSize = this.getAlg().getParam().getANSWER_SIZE();
		
		this.collectedDataMessage.addMessageSegment(new MessageSegment(dataSize, na, na));
	}

	public Shape getEndShape() {
		return endShape;
	}

	public NeighborAttachment getRepresentativeNa() {
		return representativeNa;
	}

	public void setRepresentativeNa(NeighborAttachment representativeNa) {
		this.representativeNa = representativeNa;
	}
	
	public double getRepresentativeData() {
		return this.getAlg().getDataset().getValue(this.getRepresentativeNa().getPos(), new Time(0), 0);
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public NeighborAttachment getNewRepresentativeNa() {
		return newRepresentativeNa;
	}

	public void setNewRepresentativeNa(NeighborAttachment newRepresentativeNa) {
		this.newRepresentativeNa = newRepresentativeNa;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CollectDataInShapeViaNodeEventForHSAApproximate ");
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
