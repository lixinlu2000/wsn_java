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
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.RingSector;

/*
 * RISC
 */
public class GridTraverseRingEvent extends TraverseRingEvent {
	static Logger logger = Logger.getLogger(GridTraverseRingEvent.class);

	double ringSectorSita;
	RingSector[] ringSectors;

	protected RingSector[] calculateRingSectors(
			NeighborAttachment firstNodeInRing, Ring ring, double ringSectorSita) {
		double firstBearing = this.getRing().getCircleCentre().bearing(
				firstNodeInRing.getNode().getPos());

		// fixed code
		firstBearing = firstBearing - ringSectorSita / 2;

		double lowRadius = this.getRing().getLowRadius();
		double highRadius = this.getRing().getHighRadius();

		double twoPI = 2 * Math.PI;
		double ringSectorNum1 = twoPI / ringSectorSita;
		int ringSectorNum2 = (int) Math.ceil(ringSectorNum1);
		double restSita = twoPI - (ringSectorNum2 - 1) * ringSectorSita;

		RingSector[] ringSector = new RingSector[ringSectorNum2];

		for (int i = 0; i < ringSectorNum2 - 1; i++) {
			ringSector[i] = new RingSector(this.ring.getCircleCentre(),
					lowRadius, highRadius, i * ringSectorSita + firstBearing,
					ringSectorSita);
		}
		ringSector[ringSectorNum2 - 1] = new RingSector(this.ring
				.getCircleCentre(), lowRadius, highRadius, (ringSectorNum2 - 1)
				* ringSectorSita + firstBearing, restSita);
		return ringSector;
	}

	public GridTraverseRingEvent(Message message, Algorithm alg) {
		super(message, alg);
	}

	public GridTraverseRingEvent(NeighborAttachment root, Ring ring,
			double ringSectorSita, Message message, Algorithm alg) {
		super(root, ring, message, alg);
		this.ringSectorSita = ringSectorSita;
		this.ringSectors = this.calculateRingSectors(getRoot(), getRing(),
				getRingSectorSita());
	}

	public RingSector[] getRingSectors() {
		return ringSectors;
	}

	public double getRingSectorSita() {
		return ringSectorSita;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curGridNode;
		int nextGridIndex;

		int nextIndex;
		NeighborAttachment broadCastNode;
		RingSector nextRingSector;
		GreedyForwardToAllNodeInShapeEvent gftse;

		NeighborAttachment nextGridNode;
		NeighborAttachment dataNode;
		Vector dataNodesInNextGrid;
		AttachmentInShapeCondition aisc;
		BroadcastEvent queryEvent;
		Message answerMessage = new Message(this.getAlg().getParam()
				.getANSWER_SIZE(), null);
		BroadcastEvent answerEvent;
		DrawShapeEvent drawRingSectorEvent;
		ItineraryNodeEvent itineraryNodeEvent;

		// draw ring event
		DrawShapeEvent drawRingEvent = new DrawShapeEvent(this.getAlg(), this
				.getRing());
		this.getAlg().getSimulator().handle(drawRingEvent);

		curGridNode = this.getRoot();
		nextGridIndex = 0;

		logger.debug("GridTraverseRingEvent start");
		do {
			broadCastNode = curGridNode;
			nextIndex = nextGridIndex;
			do {
				if (nextIndex >= ringSectors.length) {
					logger
							.debug("traverse ring sectors, reach the end of the ring");
					this.setLastNode(broadCastNode);
					this.setSuccess(true);
					logger.debug("GridTraverseRingEvent end");
					return;
				}

				nextRingSector = ringSectors[nextIndex];
				gftse = new GreedyForwardToAllNodeInShapeEvent(broadCastNode,
						nextRingSector, this.getMessage(), this.getAlg());
				gftse.setSendQueryTag();
				this.getAlg().getSimulator().handle(gftse);
				this.getAlg().getSimulator().handle(
						EventsUtil.NeighborAttachmentsToItinerayNodeEvents(
								gftse, false));

				logger.debug("greedy forward to  " + nextRingSector);
				if (gftse.isSuccess() && gftse.isHasNodesInShape()) {
					logger.debug("greedy forward to shape success");
					broadCastNode = (NeighborAttachment) gftse.getLastNode();
					break;
				} else {
					broadCastNode = (NeighborAttachment) gftse.getLastNode();
					nextIndex++;
				}
			} while (true);

			curGridNode = broadCastNode;
			nextGridIndex = nextIndex;

			aisc = new AttachmentInShapeCondition(ringSectors[nextGridIndex]);
			nextGridNode = this.getNextAttachment(curGridNode,
					ringSectors[nextGridIndex]);
			dataNodesInNextGrid = nextGridNode.getNeighbors(this.getAlg()
					.getParam().getRADIO_RANGE(), aisc);

			// draw shap
			drawRingSectorEvent = new DrawShapeEvent(this.getAlg(),
					ringSectors[nextGridIndex]);
			this.getAlg().getSimulator().handle(drawRingSectorEvent);

			// broadcast query
			if (curGridNode != nextGridNode) {
				queryEvent = new BroadcastEvent(curGridNode, nextGridNode, this
						.getMessage(), this.getAlg());
				queryEvent.setSendQueryTag();
				this.getAlg().getSimulator().handle(queryEvent);
			} else {
				dataNodesInNextGrid.remove(nextGridNode);
				if (!dataNodesInNextGrid.isEmpty()) {
					queryEvent = new BroadcastEvent(curGridNode, null, this
							.getMessage(), this.getAlg());
					queryEvent.setSendQueryTag();
					this.getAlg().getSimulator().handle(queryEvent);
				}
			}

			itineraryNodeEvent = new ItineraryNodeEvent(curGridNode,
					nextGridNode, this.getAlg());
			this.getAlg().getSimulator().handle(itineraryNodeEvent);

			logger.debug("collect answers");
			dataNodesInNextGrid.remove(nextGridNode);
			for (int i = 0; i < dataNodesInNextGrid.size(); i++) {
				dataNode = (NeighborAttachment) dataNodesInNextGrid
						.elementAt(i);
				if (dataNode.isHasSendAnswer())
					continue;
				else {
					// the destination node should be null. Now we set it to cur
					// for display
					answerEvent = new BroadcastEvent(dataNode, nextGridNode,
							answerMessage, this.getAlg());
					answerEvent.setSendAnswerTag();
					this.getAlg().getSimulator().handle(answerEvent);
					itineraryNodeEvent.addDataNode(dataNode);
					dataNode.setHasSendAnswer(true);
					this.getMessage().addMessageSegment(
							new MessageSegment(this.getAlg().getParam()
									.getANSWER_SIZE(), dataNode, dataNode));
				}
			}

			// add my answer
			if (!nextGridNode.isHasSendAnswer()) {
				this.getMessage().addMessageSegment(
						new MessageSegment(this.getAlg().getParam()
								.getANSWER_SIZE(), nextGridNode, nextGridNode));
				curGridNode.setHasSendAnswer(true);
				if (GlobalConstants.getInstance().isDebug()) {
					NodeHasQueryResultEvent nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
							this.getAlg(), nextGridNode);
					this.getAlg().getSimulator()
							.handle(nodeHasQueryResultEvent);
				}
			}

			curGridNode = nextGridNode;
			nextGridIndex++;
		} while (true);
	}

	protected NeighborAttachment getNextAttachment(NeighborAttachment cur,
			RingSector nextRingSector) {
		Vector attachmentsInNextRingSector;
		AttachmentInShapeCondition aisc = new AttachmentInShapeCondition(
				nextRingSector);
		attachmentsInNextRingSector = cur.getNeighbors(this.getAlg().getParam()
				.getRADIO_RANGE(), aisc);

		if (aisc.isTrue(cur)) {
			attachmentsInNextRingSector.add(cur);
		}

		double maxRelativeRadian = 0;
		double relativeRadian;
		NeighborAttachment attachment;
		NeighborAttachment nextAttachment = cur;

		for (int i = 0; i < attachmentsInNextRingSector.size(); i++) {
			attachment = (GPSRAttachment) attachmentsInNextRingSector
					.elementAt(i);
			relativeRadian = nextRingSector.relativeRadian(attachment.getNode()
					.getPos());
			if (relativeRadian > maxRelativeRadian) {
				nextAttachment = attachment;
				maxRelativeRadian = relativeRadian;
			}
		}
		return nextAttachment;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GridTraverseRingEvent ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getRing());
		sb.append(" ringSectorSita(");
		sb.append(this.getRingSectorSita());
		sb.append(")");
		return sb.toString();
	}

}
