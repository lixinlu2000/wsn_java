package NHSensor.NHSensorSim.events;

import java.util.Vector;

import org.apache.log4j.Logger;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.GKNNUtil;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.MessageSegment;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.RingSector;
import NHSensor.NHSensorSim.util.Convertor;
import NHSensor.NHSensorSim.util.RadianUtil;

/*
 * 小微论文实验，但是里面有bug
 * 查询节点发送给数据节点的消息不对
 */
public class ItineraryTraverseRingEvent extends TraverseRingEvent {
	static Logger logger = Logger.getLogger(GridTraverseRingEvent.class);
	double ringSectorSita;
	RingSector[] ringSectors;
	RingSector fistRingSector;
	NeighborAttachment firstNeighborAttachmentInRing;
	boolean isFirstTime = true;
	double traversedRadian = 0;
	boolean useRingSectorToAvoidVoid = true;

	public boolean isUseRingSectorToAvoidVoid() {
		return useRingSectorToAvoidVoid;
	}

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

	protected void initRingSectors() {
		this.ringSectorSita = GKNNUtil.calculateRingSectorSita(this.getRing()
				.getLowRadius(), this.getAlg().getParam().getRADIO_RANGE());
		this.ringSectors = this.calculateRingSectors(getRoot(), getRing(),
				ringSectorSita);
		this.fistRingSector = this.ringSectors[0];
	}

	public RingSector[] getRingSectors() {
		return ringSectors;
	}

	public double getRingSectorSita() {
		return ringSectorSita;
	}

	public ItineraryTraverseRingEvent(NeighborAttachment root, Ring ring,
			Message message, Algorithm alg) {
		super(root, ring, message, alg);
		this.initRingSectors();
	}

	protected int getRingSectorIndexContainsNode(NeighborAttachment node) {
		for (int i = 0; i < this.ringSectors.length; i++) {
			if (this.ringSectors[i].contains(node.getNode().getPos())) {
				return i;
			}
		}
		return -1;
	}

	protected int getNextRingSectorIndex(NeighborAttachment node)
			throws Exception {
		int nodeRingSectorIndex = this.getRingSectorIndexContainsNode(node);
		if (nodeRingSectorIndex == -1) {
			throw new Exception("node ringSector index == -1");
		} else {
			return ++nodeRingSectorIndex;
		}
	}

	protected boolean greedyForwardToANodeInRing()
			throws SensornetBaseException {
		NeighborAttachment entryNode = this.getRoot();
		RingSector entryRingSector;
		int entryRingSectorIndex = 0;
		GreedyForwardToShapeEvent gftse;

		do {
			entryRingSector = this.ringSectors[entryRingSectorIndex];
			if (entryRingSectorIndex >= ringSectors.length) {
				logger
						.debug("greedy Forward To A Node In Ring, reach the end of the ring");
				return false;
			}

			gftse = new GreedyForwardToShapeEvent(entryNode, entryRingSector,
					this.getMessage(), this.getAlg());
			gftse.setSendQueryTag();
			this.getAlg().getSimulator().handle(gftse);
			this.getAlg().getSimulator().handle(
					EventsUtil.NeighborAttachmentsToItinerayNodeEvents(gftse,
							false));

			logger.debug("greedy forward to  " + entryRingSector);
			if (gftse.isSuccess()) {
				logger.debug("greedy forward to shape success");
				entryNode = (NeighborAttachment) gftse.getLastNode();
				break;
			} else {
				if (!this.isUseRingSectorToAvoidVoid()) {
					this.setSuccess(false);
					return false;
				}
				entryNode = (NeighborAttachment) gftse.getLastNode();
				entryRingSectorIndex++;
			}
		} while (true);
		this.firstNeighborAttachmentInRing = entryNode;
		return true;
	}

	protected NeighborAttachment getNextQueryNode(
			NeighborAttachment curQueryNode) {
		Position curQueryNodePos = curQueryNode.getNode().getPos();
		NeighborAttachment nextQueryNode = curQueryNode;
		Vector neighbors = curQueryNode.getNeighbors(this.getRing());
		NeighborAttachment neighbor;
		double neighborBearing;
		double neighborRelativeBearing;

		Position ringCircleCentre = this.getRing().getCircleCentre();
		double d = curQueryNodePos.distance(ringCircleCentre)
				+ this.getRing().getHighRadius();
		double curQueryNodeBearing = ringCircleCentre.bearing(curQueryNodePos);
		double maxRelativeBearing = 0;

		for (int i = 0; i < neighbors.size(); i++) {
			neighbor = (NeighborAttachment) neighbors.elementAt(i);
			neighborBearing = ringCircleCentre.bearing(neighbor.getNode()
					.getPos());

			if (this.getAlg().getParam().getRADIO_RANGE() >= d) {
				neighborRelativeBearing = Radian.relativeTo(neighborBearing,
						curQueryNodeBearing);
			} else {
				neighborRelativeBearing = Convertor.relativeRadianNPiToPi(
						neighborBearing, curQueryNodeBearing);
			}

			if (neighborRelativeBearing > maxRelativeBearing) {
				maxRelativeBearing = neighborRelativeBearing;
				nextQueryNode = neighbor;
			}
		}
		return nextQueryNode;
	}

	// TODO
	protected boolean isReachTheEndOfRing(NeighborAttachment curQueryNode) {

		if (this.getRingSectorIndexContainsNode(curQueryNode) == this
				.getRingSectors().length - 1)
			return true;
		if (curQueryNode == this.firstNeighborAttachmentInRing
				&& this.isFirstTime) {
			return false;
		}

		Position curQueryNodePos = curQueryNode.getNode().getPos();
		Position ringCircleCenter = this.getRing().getCircleCentre();

		double d1 = curQueryNodePos.distance(ringCircleCenter);
		double d2 = this.getRing().getHighRadius();
		double r = this.getAlg().getParam().getRADIO_RANGE();
		double sita = RadianUtil.sita(d1, d2, r);

		double curQueryNodeRadian = ringCircleCenter.bearing(curQueryNodePos);

		RingSector ringSecor = new RingSector(this.getRing(),
				curQueryNodeRadian, sita);
		if (ringSecor.contains(this.firstNeighborAttachmentInRing.getNode()
				.getPos()))
			return true;
		if (this.getTraversedRadian() >= 2 * Math.PI)
			return true;
		else
			return false;
	}

	// TODO
	protected boolean isReachTheEndOfRing(RingSector ringSector) {
		double endRadian = ringSector.getEndRadian();
		double firstRingSectorStartRadian = this.fistRingSector
				.getStartRadian();

		if (endRadian == firstRingSectorStartRadian)
			return true;
		else
			return false;
	}

	protected RingSector getNextRingSector(NeighborAttachment curQueryNode) {
		RingSector nextRingSector;
		Position ringCircleCentre = this.getRing().getCircleCentre();
		double curQueryNodeBearing = ringCircleCentre.bearing(curQueryNode
				.getNode().getPos());

		// TODO ring sector sita should be optimized
		nextRingSector = new RingSector(this.getRing(), curQueryNodeBearing,
				this.getRingSectorSita());

		double sita = Radian.relativeTo(this.ringSectors[0].getStartRadian(),
				nextRingSector.getStartRadian());
		double newRingSectorSita = this.getRingSectorSita();
		if (sita < newRingSectorSita) {
			newRingSectorSita = sita;
		}
		nextRingSector.setSita(newRingSectorSita);
		return nextRingSector;
	}

	protected RingSector getNextRingSector(RingSector ringSector) {
		RingSector nextRingSector;

		// TODO ring sector sita should be optimized
		nextRingSector = new RingSector(this.getRing(), ringSector
				.getEndRadian(), this.getRingSectorSita());

		double sita = Radian.relativeTo(this.ringSectors[0].getStartRadian(),
				nextRingSector.getStartRadian());
		double newRingSectorSita = this.getRingSectorSita();
		if (sita < newRingSectorSita) {
			newRingSectorSita = sita;
		}
		nextRingSector.setSita(newRingSectorSita);
		return nextRingSector;
	}

	protected void changeTraversedRadian(NeighborAttachment curQueryNode) {
		Position ringCircleCentre = this.getRing().getCircleCentre();
		double firstNodeBearing = ringCircleCentre
				.bearing(this.firstNeighborAttachmentInRing.getNode().getPos());
		double relativeBearing = Radian.relativeTo(ringCircleCentre
				.bearing(curQueryNode.getNode().getPos()), firstNodeBearing);

		while (relativeBearing < this.traversedRadian) {
			relativeBearing += Math.PI * 2;
		}
		this.traversedRadian = relativeBearing;
	}

	public double getTraversedRadian() {
		return traversedRadian;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment curQueryNode;

		NeighborAttachment entryNode;
		RingSector entryRingSector;
		GreedyForwardToAnotherNodeInShapeEvent gftse;

		NeighborAttachment nextQueryNode;
		NeighborAttachment dataNode;
		Vector dataNodes;
		BroadcastEvent queryEvent;
		Message answerMessage = new Message(this.getAlg().getParam()
				.getANSWER_SIZE(), null);
		// bug
		Message queryMessage = new Message(this.getAlg().getParam()
				.getQUERY_MESSAGE_SIZE(), null);

		BroadcastEvent answerEvent;
		NodeHasQueryResultEvent nodeHasQueryResultEvent;
		ItineraryNodeEvent itineraryNodeEvent;

		logger.debug("ItineraryTraverseRingEvent start");
		if (!this.greedyForwardToANodeInRing()) {
			this.setSuccess(false);
			return;
		}
		curQueryNode = this.firstNeighborAttachmentInRing;
		this.isFirstTime = true;
		while (true) {

			while (true) {
				// bug
				/*
				 * queryEvent = new BroadcastEvent(curQueryNode,
				 * null,this.getMessage(),this.getAlg());
				 */
				queryEvent = new BroadcastEvent(curQueryNode, null,
						queryMessage, this.getAlg());
				queryEvent.setSendQueryTag();
				this.getAlg().getSimulator().handle(queryEvent);
				this.changeTraversedRadian(curQueryNode);

				// collect data node data
				dataNodes = curQueryNode.getNeighbors(this.getRing());
				for (int i = 0; i < dataNodes.size(); i++) {
					dataNode = (NeighborAttachment) dataNodes.elementAt(i);
					if (dataNode.isHasSendAnswer())
						continue;
					else {
						answerEvent = new BroadcastEvent(dataNode,
								curQueryNode, answerMessage, this.getAlg());
						answerEvent.setSendAnswerTag();
						this.getAlg().getSimulator().handle(answerEvent);
						dataNode.setHasSendAnswer(true);
						this.getMessage().addMessageSegment(
								new MessageSegment(this.getAlg().getParam()
										.getANSWER_SIZE(), dataNode, dataNode));

						if (GlobalConstants.getInstance().isDebug()) {
							nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
									this.getAlg(), dataNode);
							this.getAlg().getSimulator().handle(
									nodeHasQueryResultEvent);
						}
					}
				}

				// add my answer
				if (!curQueryNode.isHasSendAnswer()) {
					this.getMessage().addMessageSegment(
							new MessageSegment(this.getAlg().getParam()
									.getANSWER_SIZE(), curQueryNode,
									curQueryNode));
					curQueryNode.setHasSendAnswer(true);

					if (GlobalConstants.getInstance().isDebug()) {
						nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
								this.getAlg(), curQueryNode);
						this.getAlg().getSimulator().handle(
								nodeHasQueryResultEvent);
					}
				}

				if (this.isReachTheEndOfRing(curQueryNode)) {
					this.setLastNode(curQueryNode);
					this.setSuccess(true);
					return;
				}

				nextQueryNode = this.getNextQueryNode(curQueryNode);
				if (nextQueryNode != curQueryNode) {
					queryEvent = new BroadcastEvent(curQueryNode,
							nextQueryNode, this.getMessage(), this.getAlg());
					queryEvent.setSendQueryTag();

					itineraryNodeEvent = new ItineraryNodeEvent(curQueryNode,
							nextQueryNode, this.getAlg());
					this.getAlg().getSimulator().handle(itineraryNodeEvent);

					this.getAlg().getSimulator().handle(queryEvent);
					curQueryNode = nextQueryNode;
					this.isFirstTime = false;
				} else {
					// do not use ring sector to avoid void so return;
					if (!this.useRingSectorToAvoidVoid) {
						this.setSuccess(false);
						return;
					}
					break;
				}
			}

			entryNode = curQueryNode;
			entryRingSector = this.getNextRingSector(entryNode);
			while (true) {
				logger.debug("greedy forward to  " + entryRingSector);
				gftse = new GreedyForwardToAnotherNodeInShapeEvent(entryNode,
						entryRingSector, this.getMessage(), this.getAlg());
				gftse.setSendQueryTag();
				this.getAlg().getSimulator().handle(gftse);
				this.getAlg().getSimulator().handle(
						EventsUtil.NeighborAttachmentsToItinerayNodeEvents(
								gftse, false, null));

				if (gftse.isSuccess()) {
					logger.debug("greedy forward to shape success");
					curQueryNode = (NeighborAttachment) gftse.getLastNode();
					break;
				} else {
					logger.debug("greedy forward to shape failed and continue");
					if (this.isReachTheEndOfRing(entryRingSector)) {
						this.setLastNode(entryNode);
						this.setSuccess(true);
						return;
					}
					entryNode = (NeighborAttachment) gftse.getLastNode();
					entryRingSector = this.getNextRingSector(entryRingSector);
				}
			}
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ItineraryTraverseRingEvent ");
		sb.append(this.root.getNode().getPos());
		sb.append("---");
		sb.append(this.getRing());
		sb.append(" ringSectorSita(");
		sb.append(this.getRingSectorSita());
		sb.append(")");
		return sb.toString();
	}
}
