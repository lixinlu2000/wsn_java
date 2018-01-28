package NHSensor.NHSensorSim.events;

import java.util.Collections;
import java.util.Comparator;
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
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.RingSector;

public class ROCAdaptiveGridTraverseRingEvent extends TraverseRingEvent {
	static Logger logger = Logger
			.getLogger(ROCAdaptiveGridTraverseRingEvent.class);

	RingSector firstRingSector;

	public static double calculateRingSectorSita(double lowRadius,
			double highRadius, double radioRange) {
		double l = lowRadius;
		double h = highRadius;
		double r = radioRange;
		double l2 = l * l;
		double h2 = h * h;
		double r2 = r * r;
		double cosSita1, sita1 = 0;
		double cosSita2, sita2 = 0;

		// bug fixed
		if (r < h + l) {
			cosSita1 = (h2 + l2 - r2) / (2 * h * l);
			sita1 = Math.acos(cosSita1);
			cosSita2 = (2 * h2 - r2) / (2 * h2);
			sita2 = Math.acos(cosSita2);
		} else if (radioRange >= 2 * h) {
			return 2 * Math.PI;
		} else {
			sita1 = Math.PI;
			cosSita2 = (2 * h2 - r2) / (2 * h2);
			sita2 = Math.acos(cosSita2);
		}
		return Math.min(sita1, sita2);
	}

	protected double caculateFirstRingSectorSita() {
		return this.caculateMaxSita();
	}

	protected RingSector calculateFirstRingSector() {
		double firstBearing = this.getRing().getCircleCentre().bearing(
				this.getRoot().getNode().getPos());

		// fixed code
		double firstRingSectorSita = this.caculateFirstRingSectorSita();
		firstBearing = firstBearing - firstRingSectorSita / 2;

		double lowRadius = this.getRing().getLowRadius();
		double highRadius = this.getRing().getHighRadius();

		return new RingSector(this.ring.getCircleCentre(), lowRadius,
				highRadius, firstBearing, firstRingSectorSita);
	}

	protected void initFirstRingSector() {
		this.firstRingSector = this.calculateFirstRingSector();
	}

	public ROCAdaptiveGridTraverseRingEvent(Message message, Algorithm alg) {
		super(message, alg);
		this.initFirstRingSector();
	}

	public ROCAdaptiveGridTraverseRingEvent(NeighborAttachment root, Ring ring,
			Message message, Algorithm alg) {
		super(root, ring, message, alg);
		this.initFirstRingSector();
	}

	/*
	 * judge whether reach the end of the ring
	 */
	protected boolean isReachTheEndOfRing(RingSector nextRingSector) {
		if (this.firstRingSector.getStartRadian() == nextRingSector
				.getEndRadian())
			return true;
		else
			return false;
	}

	/*
	 * if nextRingSector has no nodes, then caculate the ring sector after
	 * nextRingSector
	 */
	protected RingSector caculateNextRingSector(RingSector nextRingSector) {
		double ringSectorSita = this.caculateFirstRingSectorSita();
		RingSector nextNextRingSector = new RingSector(nextRingSector
				.getCircleCentre(), nextRingSector.getLowRadius(),
				nextRingSector.getHighRadius(), nextRingSector.getEndRadian(),
				ringSectorSita);

		// check if reach the end of the ring
		double sita = Radian.relativeTo(this.firstRingSector.getStartRadian(),
				nextNextRingSector.getStartRadian());
		if (nextNextRingSector.getSita() > sita)
			nextNextRingSector.setEndRadian(this.firstRingSector
					.getStartRadian());
		return nextNextRingSector;
	}

	/*
	 * caculate the max sita of the ringSector that any two node in this
	 * ringSector can communicate with each other.
	 */
	protected double caculateMaxSita() {
		double lowRadius = this.getRing().getLowRadius();
		double highRadius = this.getRing().getHighRadius();

		return ROCAdaptiveGridTraverseRingEvent.calculateRingSectorSita(
				lowRadius, highRadius, this.getAlg().getParam()
						.getRADIO_RANGE());
	}

	/*
	 * if nextRingSector has nodes and nextRingSectorGridNode is the grid node,
	 * then caculate the ring sector after nextRingSector. All the node in
	 * nextRingSector can communicate with nextRingSectorGridNode and
	 * communicate with each other.
	 */
	protected RingSector caculateNextRingSector(
			NeighborAttachment nextRingSectorGridNode, RingSector nextRingSector) {
		RingSector nextNextRingSector;

		double l = this.getRing().getLowRadius();
		double h = this.getRing().getHighRadius();
		double r = this.getAlg().getParam().getRADIO_RANGE();
		double d = nextRingSectorGridNode.getNode().getPos().distance(
				this.getRing().getCircleCentre());
		double startRadian = this.getRing().getCircleCentre().bearing(
				nextRingSectorGridNode.getNode().getPos());
		double l2 = l * l;
		double h2 = h * h;
		double r2 = r * r;
		double d2 = d * d;
		double cosSita1, sita1 = 0;
		double cosSita2, sita2 = 0;
		double sita;

		// bug fixed
		if (r >= d + h) {
			sita = 2 * Math.PI;
		} else if (r >= d + l) {
			sita1 = Math.PI;
			cosSita2 = (h2 + d2 - r2) / (2 * h * d);
			sita2 = Math.acos(cosSita2);
			sita = Math.min(sita1, sita2);
		} else {
			cosSita1 = (l2 + d2 - r2) / (2 * l * d);
			sita1 = Math.acos(cosSita1);
			cosSita2 = (h2 + d2 - r2) / (2 * h * d);
			sita2 = Math.acos(cosSita2);
			sita = Math.min(sita1, sita2);
		}

		// bug
		sita -= Radian.relativeTo(nextRingSector.getEndRadian(), startRadian);

		double maxSita = this.caculateMaxSita();

		if (sita <= maxSita) {
			// TODO magic code
			sita *= 0.999;

			nextNextRingSector = new RingSector(this.ring.getCircleCentre(), l,
					h, nextRingSector.getEndRadian(), sita);

			// check if reach the end of the ring
			double relativeSita = Radian.relativeTo(this.firstRingSector
					.getStartRadian(), nextNextRingSector.getStartRadian());
			if (nextNextRingSector.getSita() > relativeSita)
				nextNextRingSector.setEndRadian(this.firstRingSector
						.getStartRadian());

			return nextNextRingSector;
		}

		// caculate min nextNextRingSector
		RingSector shouldContainRingSector = new RingSector(this.ring
				.getCircleCentre(), l, h, nextRingSector.getEndRadian(),
				maxSita);
		double relativeSita = Radian.relativeTo(this.firstRingSector
				.getStartRadian(), shouldContainRingSector.getStartRadian());
		if (shouldContainRingSector.getSita() > relativeSita) {
			shouldContainRingSector.setEndRadian(this.firstRingSector
					.getStartRadian());
		}

		// caculate candidate RingSector
		RingSector candidateRingSector = new RingSector(this.ring
				.getCircleCentre(), l, h, shouldContainRingSector
				.getEndRadian(), sita - maxSita);
		relativeSita = Radian.relativeTo(this.firstRingSector.getStartRadian(),
				candidateRingSector.getStartRadian());
		if (candidateRingSector.getSita() > relativeSita)
			candidateRingSector.setEndRadian(this.firstRingSector
					.getStartRadian());

		Vector shouldContainNodes = nextRingSectorGridNode
				.getNeighbors(shouldContainRingSector);
		Vector candidateNodes = nextRingSectorGridNode
				.getNeighbors(candidateRingSector);
		RelativeRadianComparator comparator = new RelativeRadianComparator(
				shouldContainRingSector.getStartRadian());
		Collections.sort(shouldContainNodes, comparator);
		Collections.sort(candidateNodes, comparator);

		NeighborAttachment candidateNode;
		NeighborAttachment shouldContainNode;
		NeighborAttachment newShouldContainNode = null;
		boolean isNewShouldContainNode;
		for (int i = 0; i < candidateNodes.size(); i++) {
			candidateNode = (NeighborAttachment) candidateNodes.elementAt(i);
			isNewShouldContainNode = true;
			for (int j = 0; j < shouldContainNodes.size(); j++) {
				shouldContainNode = (NeighborAttachment) shouldContainNodes
						.elementAt(j);
				if (candidateNode.getNode().getPos().distance(
						shouldContainNode.getNode().getPos()) > this.getAlg()
						.getParam().getRADIO_RANGE()) {
					isNewShouldContainNode = false;
					break;
				}
			}
			if (isNewShouldContainNode) {
				shouldContainNodes.add(candidateNode);
				newShouldContainNode = candidateNode;
			} else {
				break;
			}
		}

		nextNextRingSector = new RingSector(this.ring.getCircleCentre(), l, h,
				nextRingSector.getEndRadian(), maxSita);
		if (newShouldContainNode != null) {
			nextNextRingSector.setEndRadian(this.ring.getCircleCentre()
					.bearing(newShouldContainNode.getNode().getPos()));
		}
		// check if reach the end of the ring
		relativeSita = Radian.relativeTo(this.firstRingSector.getStartRadian(),
				nextNextRingSector.getStartRadian());
		if (nextNextRingSector.getSita() > relativeSita)
			nextNextRingSector.setEndRadian(this.firstRingSector
					.getStartRadian());
		return nextNextRingSector;
	}

	public class RelativeRadianComparator implements Comparator {
		private double baseRadian;

		public RelativeRadianComparator(double baseRadian) {
			this.baseRadian = baseRadian;
		}

		public int compare(Object arg0, Object arg1) {
			NeighborAttachment n1 = (NeighborAttachment) arg0;
			NeighborAttachment n2 = (NeighborAttachment) arg1;

			double relativeRadian1 = Radian.relativeTo(ring.getCircleCentre()
					.bearing(n1.getNode().getPos()), baseRadian);
			double relativeRadian2 = Radian.relativeTo(ring.getCircleCentre()
					.bearing(n2.getNode().getPos()), baseRadian);
			if (relativeRadian1 > relativeRadian2)
				return 1;
			else if (relativeRadian1 < relativeRadian2)
				return -1;
			else
				return 0;
		}

	}

	/*
	 * caculate the rank of node na which is in ringSector. This value decide
	 * which node will be choose as a grid node in ringSector;
	 */
	protected double getRankOfNodeInRingSector(NeighborAttachment na,
			RingSector ringSector) {
		return this.caculateNextRingSector(na, ringSector).area();
	}

	/*
	 * caculate next grid node
	 */
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

		double maxRank = Double.MIN_VALUE;
		double rank;
		NeighborAttachment attachment;
		NeighborAttachment nextAttachment = (GPSRAttachment) attachmentsInNextRingSector
				.elementAt(0);
		;

		for (int i = 0; i < attachmentsInNextRingSector.size(); i++) {
			attachment = (GPSRAttachment) attachmentsInNextRingSector
					.elementAt(i);
			rank = this.getRankOfNodeInRingSector(attachment, nextRingSector);
			if (rank > maxRank) {
				nextAttachment = attachment;
				maxRank = rank;
			}
		}
		return nextAttachment;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curGridNode;

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
		nextRingSector = this.firstRingSector;

		logger.debug("GridTraverseRingEvent start");
		do {
			broadCastNode = curGridNode;
			do {
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
					nextRingSector = this
							.caculateNextRingSector(nextRingSector);
				}

				if (this.isReachTheEndOfRing(nextRingSector)) {
					logger
							.debug("traverse ring sectors, reach the end of the ring");
					this.setLastNode(broadCastNode);
					this.setSuccess(true);
					logger.debug("GridTraverseRingEvent end");
					return;
				}

			} while (true);

			curGridNode = broadCastNode;

			aisc = new AttachmentInShapeCondition(nextRingSector);
			nextGridNode = this.getNextAttachment(curGridNode, nextRingSector);
			dataNodesInNextGrid = nextGridNode.getNeighbors(this.getAlg()
					.getParam().getRADIO_RANGE(), aisc);

			// draw shap
			drawRingSectorEvent = new DrawShapeEvent(this.getAlg(),
					nextRingSector);
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

			if (this.isReachTheEndOfRing(nextRingSector)) {
				logger
						.debug("traverse ring sectors, reach the end of the ring");
				this.setLastNode(curGridNode);
				this.setSuccess(true);
				logger.debug("GridTraverseRingEvent end");
				return;
			} else {
				curGridNode = nextGridNode;
				nextRingSector = this.caculateNextRingSector(curGridNode,
						nextRingSector);
			}

		} while (true);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ROCAdaptiveGridTraverseRingEvent ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getRing());
		sb.append(")");
		return sb.toString();
	}

}
