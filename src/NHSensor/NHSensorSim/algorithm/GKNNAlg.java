package NHSensor.NHSensorSim.algorithm;

import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.core.AndCondition;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.GKNNUtil;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.MessageSegment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.EventsUtil;
import NHSensor.NHSensorSim.events.GreedyForwardToAllNodeInShapeEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToPointEvent;
import NHSensor.NHSensorSim.events.ItineraryNodeEvent;
import NHSensor.NHSensorSim.events.NodeHasQueryResultEvent;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.KNNQuery;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.routing.gpsr.GPSR;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAlg;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.RingSector;

public class GKNNAlg extends GPSRAlg {
	static Logger logger = Logger.getLogger(GKNNAlg.class);

	public static final String NAME = "GKNN";
	private GPSRAttachment homeNodeAttachment;
	private Message queryMessage;
	private GPSRAttachment lastNodeAttachment;
	private Vector rings = new Vector();
	private Vector allRingSectors = new Vector();

	public Vector getAllRingSectors() {
		return allRingSectors;
	}

	public void setAllRingSectors(Vector allRingSectors) {
		this.allRingSectors = allRingSectors;
	}

	public void appendCurrentRingSectors(RingSector[] currentRingSectors) {
		for (int i = 0; i < currentRingSectors.length; i++) {
			this.getAllRingSectors().add(currentRingSectors[i]);
		}
	}

	public GPSRAttachment getLastNodeAttachment() {
		return lastNodeAttachment;
	}

	public void setLastNodeAttachment(GPSRAttachment lastNodeAttachment) {
		this.lastNodeAttachment = lastNodeAttachment;
	}

	public GPSRAttachment getHomeNodeAttachment() {
		return homeNodeAttachment;
	}

	public void setHomeNodeAttachment(GPSRAttachment homeNodeAttachment) {
		this.homeNodeAttachment = homeNodeAttachment;
	}

	public GKNNAlg(QueryBase query) {
		super(query);
		// TODO Auto-generated constructor stub
	}

	public GKNNAlg(KNNQuery query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		// TODO Auto-generated constructor stub
	}

	public GKNNAlg(KNNQuery query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, GKNNAlg.NAME, statistics);
		// TODO Auto-generated constructor stub
	}

	public GKNNAlg() {
		// TODO Auto-generated constructor stub
	}

	public void initOthers() {
		// TODO Auto-generated method stub
		super.initOthers();
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		GPSRAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = (GPSRAttachment) node.getAttachment(this.getName());
			if (na != null) {
				na.setHasSendAnswer(false);
			}
		}
		this.queryMessage = new Message(
				this.getParam().getQUERY_MESSAGE_SIZE(), this.getKNNQuery());
	}

	public boolean greedyForwardByGPSR() throws SensornetBaseException {
		Node orig = this.getKNNQuery().getOrig();
		GPSRAttachment sink = (GPSRAttachment) orig.getAttachment(this
				.getName());
		GPSR gpsr = new GPSR(sink, this.getKNNQuery().getCenter(),
				this.queryMessage, this);
		gpsr.setSendQueryTag();
		this.getSimulator().handle(gpsr);
		this.getSimulator().handle(
				EventsUtil.NeighborAttachmentsToItinerayNodeEvents(gpsr, false,
						null));
		this.setHomeNodeAttachment((GPSRAttachment) gpsr.getLastNode());
		return true;
	}

	public boolean greedyForward() throws SensornetBaseException {
		Node orig = this.getKNNQuery().getOrig();
		GPSRAttachment sink = (GPSRAttachment) orig.getAttachment(this
				.getName());
		GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(sink,
				this.getKNNQuery().getCenter(), this.queryMessage, this);
		gftpe.setSendQueryTag();
		this.getSimulator().handle(gftpe);
		this.getSimulator().handle(
				EventsUtil.NeighborAttachmentsToItinerayNodeEvents(gftpe,
						false, null));
		this.setHomeNodeAttachment((GPSRAttachment) gftpe.getLastNode());
		return true;
	}

	public KNNQuery getKNNQuery() {
		return (KNNQuery) this.getQuery();
	}

	public RingSector[] calculateRingSectors(GPSRAttachment firstNodeInRing,
			Ring ring) {
		KNNQuery kNNQuery = this.getKNNQuery();
		double firstBearing = kNNQuery.getCenter().bearing(
				firstNodeInRing.getNode().getPos());

		double lowRadius = ring.getLowRadius();
		double highRadius = ring.getHighRadius();
		double ringSectorSita = GKNNUtil.calculateRingSectorSita(lowRadius,
				this.getParam().getRADIO_RANGE());

		// fixed code
		firstBearing = firstBearing - ringSectorSita / 2;

		double twoPI = 2 * Math.PI;
		double ringSectorNum1 = twoPI / ringSectorSita;
		int ringSectorNum2 = (int) Math.ceil(ringSectorNum1);
		double restSita = twoPI - (ringSectorNum2 - 1) * ringSectorSita;

		RingSector[] ringSector = new RingSector[ringSectorNum2];

		for (int i = 0; i < ringSectorNum2 - 1; i++) {
			ringSector[i] = new RingSector(kNNQuery.getCenter(), lowRadius,
					highRadius, i * ringSectorSita + firstBearing,
					ringSectorSita);
			logger.debug(ringSector[i]);
		}
		ringSector[ringSectorNum2 - 1] = new RingSector(kNNQuery.getCenter(),
				lowRadius, highRadius, (ringSectorNum2 - 1) * ringSectorSita
						+ firstBearing, restSita);
		logger.debug(ringSector[ringSectorNum2 - 1]);
		return ringSector;
	}

	public GPSRAttachment traverseRing(GPSRAttachment firstNodeAttachment,
			Ring ring) throws SensornetBaseException {
		RingSector[] ringSectors;

		GPSRAttachment curGridNode = firstNodeAttachment;
		int nextGridIndex = 0;

		GPSRAttachment nextGridNode;

		GPSRAttachment nextNode;
		int nextRingSectorIndex;
		RingSector nextRingSector;
		GreedyForwardToAllNodeInShapeEvent gftse;

		GPSRAttachment dataNode;
		Vector dataNodesInRingSector;
		AttachmentInShapeCondition aisc;
		BroadcastEvent queryEvent;
		Message answerMessage = new Message(this.getParam().getANSWER_SIZE(),
				null);
		BroadcastEvent answerEvent;
		NodeHasQueryResultEvent nodeHasQueryResultEvent;
		ItineraryNodeEvent itineraryNodeEvent;

		ringSectors = this.calculateRingSectors(firstNodeAttachment, ring);
		this.appendCurrentRingSectors(ringSectors);

		do {
			nextNode = curGridNode;
			nextRingSectorIndex = nextGridIndex;
			do {
				if (nextRingSectorIndex >= ringSectors.length) {
					logger
							.debug("traverse ring sectors, reach the end of the ring");
					return nextNode;
				}
				nextRingSector = ringSectors[nextRingSectorIndex];
				gftse = new GreedyForwardToAllNodeInShapeEvent(nextNode,
						nextRingSector, this.getQueryMessage(), this);
				gftse.setSendQueryTag();
				this.getSimulator().handle(gftse);
				this.getSimulator().handle(
						EventsUtil.NeighborAttachmentsToItinerayNodeEvents(
								gftse, false, null));

				logger.debug("greedy forward to " + nextRingSector);
				if (gftse.isSuccess() && gftse.isHasNodesInShape()) {
					// bug
					// queryEvent = new
					// BroadcastEvent(cur,null,this.getQueryMessage(),this);
					// this.simulator.handle(queryEvent);
					logger.debug("greedy forward to shape success");
					curGridNode = (GPSRAttachment) gftse.getLastNode();
					nextGridIndex = nextRingSectorIndex;
					break;
				} else {
					nextNode = (GPSRAttachment) gftse.getLastNode();
					nextRingSectorIndex++;
				}
			} while (true);

			nextGridNode = this.getNextAttachment(curGridNode,
					ringSectors[nextGridIndex]);

			aisc = new AttachmentInShapeCondition(ringSectors[nextGridIndex]);
			dataNodesInRingSector = nextGridNode.getNeighbors(this.getParam()
					.getRADIO_RANGE(), aisc);

			// broadcast query
			itineraryNodeEvent = null;
			if (curGridNode != nextGridNode) {
				queryEvent = new BroadcastEvent(curGridNode, nextGridNode, this
						.getQueryMessage(), this);
				queryEvent.setSendQueryTag();
				this.getSimulator().handle(queryEvent);
				itineraryNodeEvent = new ItineraryNodeEvent(curGridNode,
						nextGridNode, this);
			} else {
				if (!dataNodesInRingSector.isEmpty()) {
					queryEvent = new BroadcastEvent(curGridNode, null, this
							.getQueryMessage(), this);
					queryEvent.setSendQueryTag();
					this.getSimulator().handle(queryEvent);
					itineraryNodeEvent = new ItineraryNodeEvent(curGridNode,
							nextGridNode, this);
				}
			}

			logger.debug("collect answers");
			dataNodesInRingSector.remove(nextGridNode);
			for (int i = 0; i < dataNodesInRingSector.size(); i++) {
				dataNode = (GPSRAttachment) dataNodesInRingSector.elementAt(i);
				if (dataNode.isHasSendAnswer())
					continue;
				else {
					// the destination node should be null. Now we set it to cur
					// for display
					answerEvent = new BroadcastEvent(dataNode, nextGridNode,
							answerMessage, this);
					answerEvent.setSendAnswerTag();
					this.getSimulator().handle(answerEvent);
					if (itineraryNodeEvent != null) {
						itineraryNodeEvent.addDataNode(dataNode);
					}
					this.getAns().add(dataNode);
					dataNode.setHasSendAnswer(true);
					if (GlobalConstants.getInstance().isDebug()) {
						nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
								this, dataNode);
						this.getSimulator().handle(nodeHasQueryResultEvent);
					}
					this.getQueryMessage().addMessageSegment(
							new MessageSegment(
									this.getParam().getANSWER_SIZE(), dataNode,
									dataNode));
				}
			}
			if (itineraryNodeEvent != null) {
				this.getSimulator().handle(itineraryNodeEvent);
			}

			// add my answer
			if (!nextGridNode.isHasSendAnswer()) {
				this.getQueryMessage().addMessageSegment(
						new MessageSegment(this.getParam().getANSWER_SIZE(),
								nextGridNode, nextGridNode));
				this.getAns().add(nextGridNode);
				nextGridNode.setHasSendAnswer(true);
				if (GlobalConstants.getInstance().isDebug()) {
					nodeHasQueryResultEvent = new NodeHasQueryResultEvent(this,
							nextGridNode);
					this.getSimulator().handle(nodeHasQueryResultEvent);
				}
			}

			curGridNode = nextGridNode;
			nextGridIndex++;
		} while (true);

	}

	public boolean isHasKAnswers() {
		if (this.getQueryMessage().getSegments().size() >= this.getKNNQuery()
				.getK())
			return true;
		else
			return false;
	}

	public GPSRAttachment getNextAttachment(GPSRAttachment cur,
			RingSector nextRingSector) {
		Vector attachmentsInNextRingSector;
		AttachmentInShapeCondition aisc = new AttachmentInShapeCondition(
				nextRingSector);
		attachmentsInNextRingSector = cur.getNeighbors(this.getParam()
				.getRADIO_RANGE(), aisc);

		if (aisc.isTrue(cur)) {
			attachmentsInNextRingSector.add(cur);
		}

		double maxRelativeRadian = 0;
		double relativeRadian;
		GPSRAttachment attachment;
		GPSRAttachment nextAttachment = cur;

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

	public boolean isRingSectorEmpty(GPSRAttachment cur, RingSector ringSector) {
		Vector attachmentsInRingSector;
		AttachmentInShapeCondition aisc = new AttachmentInShapeCondition(
				ringSector);
		attachmentsInRingSector = cur.getNeighbors(this.getParam()
				.getRADIO_RANGE(), aisc);
		if (attachmentsInRingSector.isEmpty())
			return true;
		else
			return false;
	}

	public boolean itenararyTraverse() throws SensornetBaseException {
		GPSRAttachment firstNodeAttachmentInRing;
		GPSRAttachment lastNodeAttachmentInRing;
		GPSRAttachment firstAttachmentInNextRing;
		double lowRadius;
		double highRadius;

		firstNodeAttachmentInRing = this.getHomeNodeAttachment();
		lowRadius = firstNodeAttachmentInRing.getNode().getPos().distance(
				this.getKNNQuery().getCenter());
		highRadius = GKNNUtil.calculateHighRadius(lowRadius, this.getParam()
				.getRADIO_RANGE());
		Ring ring = new Ring(this.getKNNQuery().getCenter(), lowRadius,
				highRadius);
		this.getRings().add(ring);
		lastNodeAttachmentInRing = this.traverseRing(firstNodeAttachmentInRing,
				ring);

		while (!this.isHasKAnswers()) {
			/*
			 * old code firstAttachmentInNextRing =
			 * this.getFirstAttachmentInNextRing(lastNodeAttachmentInRing,
			 * lowRadius); this.getSimulator().handle(new
			 * BroadcastEvent(lastNodeAttachmentInRing
			 * ,firstAttachmentInNextRing, this.getQueryMessage(),this));
			 */
			// new code
			firstAttachmentInNextRing = lastNodeAttachmentInRing;
			lowRadius = highRadius;
			highRadius = GKNNUtil.calculateHighRadius(lowRadius, this
					.getParam().getRADIO_RANGE());
			ring = new Ring(this.getKNNQuery().getCenter(), lowRadius,
					highRadius);
			this.getRings().add(ring);
			lastNodeAttachmentInRing = this.traverseRing(
					firstAttachmentInNextRing, ring);
		}
		this.setLastNodeAttachment(lastNodeAttachmentInRing);

		/*
		 * if(ring.contains(lastNodeAttachmentInRing.getNode().getPos())
		 * &&!lastNodeAttachmentInRing.isHasSendAnswer()) {
		 * this.getQueryMessage().addMessageSegment( new
		 * MessageSegment(this.getParam().getANSWER_SIZE(),
		 * lastNodeAttachmentInRing,lastNodeAttachmentInRing));
		 * lastNodeAttachmentInRing.setHasSendAnswer(true);
		 * 
		 * if(GlobalConstants.getInstance().isDebug()) { NodeHasQueryResultEvent
		 * nodeHasQueryResultEvent = new NodeHasQueryResultEvent(this,
		 * lastNodeAttachmentInRing);
		 * this.getSimulator().handle(nodeHasQueryResultEvent); } }
		 */

		return true;
	}

	// beta version
	public GPSRAttachment getFirstAttachmentInNextRing(GPSRAttachment cur,
			double lowRadius) {
		// TODO need more sophisticated policy
		Circle radioCircle = new Circle(cur.getNode().getPos(), this.getParam()
				.getRADIO_RANGE());
		double nextRingLowRadius = GKNNUtil.calculateHighRadius(lowRadius, this
				.getParam().getRADIO_RANGE());
		double nextRingHighRadius = GKNNUtil.calculateHighRadius(
				nextRingLowRadius, this.getParam().getRADIO_RANGE());
		Ring nextRing = new Ring(this.getKNNQuery().getCenter(),
				nextRingLowRadius, nextRingHighRadius);
		AttachmentInShapeCondition radioCondition = new AttachmentInShapeCondition(
				radioCircle);
		AttachmentInShapeCondition ringCondition = new AttachmentInShapeCondition(
				nextRing);
		AndCondition andCondition = new AndCondition(radioCondition,
				ringCondition);
		Vector neighbors = cur.getNeighbors(this.getParam().getRADIO_RANGE(),
				andCondition);
		if (neighbors.isEmpty()) {
			return null;
		} else {
			return (GPSRAttachment) neighbors.elementAt(0);
		}
	}

	public boolean forwardBackToSink() throws SensornetBaseException {
		Node orig = this.getKNNQuery().getOrig();
		GreedyForwardToPointEvent gftpe = new GreedyForwardToPointEvent(this
				.getLastNodeAttachment(), orig.getPos(), this.queryMessage,
				this);
		gftpe.setSendQueryTag();
		this.getSimulator().handle(gftpe);
		this.getSimulator().handle(
				EventsUtil.NeighborAttachmentsToItinerayNodeEvents(gftpe, true,
						null));
		return true;

		/*
		 * Node sink = this.getKNNQuery().getOrig(); GPSR gpsr = new
		 * GPSR(this.getLastNodeAttachment(),sink.getPos(),
		 * this.getQueryMessage(), this); gpsr.setSendQueryTag();
		 * this.getSimulator().handle(gpsr); if(gpsr.isSuccess()) return true;
		 * else return false;
		 */
	}

	public boolean run() {
		try {
			this.greedyForward();
			if (this.getKNNQuery().getK() == 1) {
				this.getAns().add(this.getHomeNodeAttachment());
				this.getStatistics().setSuccess(true);
				return true;
			}

			if (!this.itenararyTraverse()) {
				logger.debug("itenarary traverse failed");
				return false;
			}

			if (!this.forwardBackToSink()) {
				logger.debug("forward back to sink failed");
				return false;
			}
		} catch (HasNoEnergyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.getAlgBlackBox().setAttachmentWhichHasNoEnergy(
					e.getAttachment());
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		this.getStatistics().setSuccess(true);
		return true;
	}

	public Message getQueryMessage() {
		return queryMessage;
	}

	public void setQueryMessage(Message queryMessage) {
		this.queryMessage = queryMessage;
	}

	public Vector getRings() {
		return rings;
	}

	public void setRings(Vector rings) {
		this.rings = rings;
	}
}
