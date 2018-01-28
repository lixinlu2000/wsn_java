package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.RingSector;
import NHSensor.NHSensorSim.shape.Shape;

public class EDCAdaptiveGridTraverseRingIteratorLinkAware extends
		TraverseRingIterator implements TraverseShapeOptimizeIterator {
	RingSector firstRingSector;
	final static double MIN_DOUBLE_VALUE = 0.0000001;
	private double averageAnswerSentTimesThrehold = 100;
	private int answersSize = 0;

	public EDCAdaptiveGridTraverseRingIteratorLinkAware(Algorithm alg,
			NeighborAttachment root, Ring ring) {
		super(alg, root, ring);
		this.firstRingSector = this.caculateFirstRingSector();
	}

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

	public NeighborAttachment getNextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape) {
		if (this.isEnd(curNa, curShape)) {
			return null;
		}
		if (curNa == null && curShape == null) {
			return null;
		} else if (curNa != null && curShape == null) { // first call
			return null;
		} else if (curNa != null && curShape != null) {
			if (!curShape.contains(curNa.getNode().getPos())) {
				return null;
			} else {
				RingSector nextRingSector = (RingSector) this.getNextShape(
						curNa, curShape);
				if (nextRingSector == null)
					return null;
				else {
					return this.nextNode(curNa, nextRingSector);
				}
			}
		}
		return null;
	}

	protected NeighborAttachment nextNode(NeighborAttachment curNa,
			RingSector nextRingSector) {
		AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
				nextRingSector);
		Vector neighborsInRingSector = curNa.getNeighbors(this.alg.getParam()
				.getRADIO_RANGE(), nairc);
		if (nextRingSector.contains(curNa.getPos()))
			neighborsInRingSector.add(curNa);

		if (neighborsInRingSector.size() == 0) {
			return null;
		} else {
			double startRadian = this.getRing().getCircleCentre().bearing(
					curNa.getNode().getPos());
			RelativeRadianComparator comparator = new RelativeRadianComparator(
					startRadian);
			Collections.sort(neighborsInRingSector, comparator);

			return (NeighborAttachment) neighborsInRingSector.lastElement();
		}
	}

	public int getAnswersSize() {
		return answersSize;
	}

	public void setAnswersSize(int answersSize) {
		this.answersSize = answersSize;
	}

	protected RingSector caculateFirstRingSector() {
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

	protected double caculateFirstRingSectorSita() {
		return this.caculateMaxSita();
	}

	/*
	 * caculate the max sita of the ringSector that any two node in this
	 * ringSector can communicate with each other.
	 */
	protected double caculateMaxSita() {
		double lowRadius = this.getRing().getLowRadius();
		double highRadius = this.getRing().getHighRadius();

		return EDCAdaptiveGridTraverseRingIteratorLinkAware
				.calculateRingSectorSita(lowRadius, highRadius, this.getAlg()
						.getParam().getRADIO_RANGE());
	}

	/*
	 * if cannot greedy forward to curRingSector then caculate the ring sector
	 * after curRingSector. All the node in curRingSector can communicate with
	 * each other.
	 */
	protected RingSector caculateNextRingSector(RingSector curRingSector) {
		RingSector nextRingSector;
		double maxSita = this.caculateMaxSita();
		double l = this.getRing().getLowRadius();
		double h = this.getRing().getHighRadius();

		nextRingSector = new RingSector(this.ring.getCircleCentre(), l, h,
				curRingSector.getEndRadian(), maxSita);

		// check if reach the end of the ring
		double relativeSita = Radian.relativeTo(this.firstRingSector
				.getStartRadian(), nextRingSector.getStartRadian());
		if (nextRingSector.getSita() > relativeSita)
			nextRingSector.setEndRadian(this.firstRingSector.getStartRadian());

		return nextRingSector;
	}

	protected double viaNodeCostNoOptimize(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex) {
		double totalCost = 0;
		double collectAnswerCost = 0;
		NeighborAttachment na;
		NeighborAttachment nextNa = (NeighborAttachment) sortedNasInMaxNextShape
				.elementAt(nextNaIndex);
		double broadcastQueryMessageTimes = 0;
		double times = 0;

		for (int i = 0; i <= nextNaIndex; i++) {
			na = (NeighborAttachment) sortedNasInMaxNextShape.elementAt(i);
			if (i != nextNaIndex)
				collectAnswerCost += this.alg.getParam().getANSWER_SIZE()
						* na.getSendTimes(nextNa);
			times = curNa.getSendTimes(na);
			broadcastQueryMessageTimes = Math.max(times,
					broadcastQueryMessageTimes);
		}
		collectAnswerCost += this.getAnswersSize() * curNa.getSendTimes(nextNa);
		totalCost += collectAnswerCost;
		totalCost += this.alg.getParam().getQUERY_MESSAGE_SIZE()
				* broadcastQueryMessageTimes;
		totalCost /= (nextNaIndex + 1);
		return totalCost;
	}

	protected double viaNodeCost(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex, boolean isOptimize) {
		if (isOptimize)
			return this.viaNodeCostOptimize(curNa, sortedNasInMaxNextShape,
					nextNaIndex);
		else
			return this.viaNodeCostNoOptimize(curNa, sortedNasInMaxNextShape,
					nextNaIndex);
	}

	protected double viaNodeTimes(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex, boolean isOptimize) {
		double totalCost;
		if (isOptimize)
			totalCost = this.viaNodeCostOptimize(curNa,
					sortedNasInMaxNextShape, nextNaIndex);
		else
			totalCost = this.viaNodeCostNoOptimize(curNa,
					sortedNasInMaxNextShape, nextNaIndex);
		return totalCost / this.alg.getParam().getANSWER_SIZE();
	}

	protected double viaNodeCostOptimize(NeighborAttachment curNa,
			Vector sortedNasInMaxNextShape, int nextNaIndex) {
		double totalCost = 0;
		double collectAnswerCost = 0;
		NeighborAttachment na;
		NeighborAttachment nextNa = (NeighborAttachment) sortedNasInMaxNextShape
				.elementAt(nextNaIndex);
		double broadcastQueryMessageTimes = 0;
		double times = 0;

		int collectedNodeNum = 0;
		for (int i = 0; i <= nextNaIndex; i++) {
			na = (NeighborAttachment) sortedNasInMaxNextShape.elementAt(i);
			if (na != nextNa && na != curNa) {
				collectedNodeNum++;
				collectAnswerCost += this.alg.getParam().getANSWER_SIZE()
						* na.getSendTimes(nextNa);
			}
			times = curNa.getSendTimes(na);
			broadcastQueryMessageTimes = Math.max(times,
					broadcastQueryMessageTimes);
		}
		if (curNa != nextNa) {
			collectedNodeNum++;
			collectAnswerCost += this.getAnswersSize()
					* curNa.getSendTimes(nextNa);
		}
		totalCost += collectAnswerCost;

		totalCost += this.alg.getParam().getQUERY_MESSAGE_SIZE()
				* broadcastQueryMessageTimes;

		if (collectedNodeNum == 0)
			return 0;
		else {
			totalCost /= collectedNodeNum;
			return totalCost;
		}
	}

	/*
	 * if curRingSector has nodes and curNa is the grid node, then caculate the
	 * ring sector after curRingSector. All the node in curRingSector can
	 * communicate with curNa and communicate with each other.
	 */
	protected RingSector caculateNextRingSector(NeighborAttachment curNa,
			RingSector curRingSector) {
		RingSector nextRingSector;

		double l = this.getRing().getLowRadius();
		double h = this.getRing().getHighRadius();
		double r = this.getAlg().getParam().getRADIO_RANGE();
		double d = curNa.getNode().getPos().distance(
				this.getRing().getCircleCentre());
		double startRadian = this.getRing().getCircleCentre().bearing(
				curNa.getNode().getPos());
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
		sita -= Radian.relativeTo(curRingSector.getEndRadian(), startRadian);

		nextRingSector = new RingSector(this.ring,
				curRingSector.getEndRadian(), sita);
		double relativeSita = Radian.relativeTo(this.firstRingSector
				.getStartRadian(), nextRingSector.getStartRadian());
		if (nextRingSector.getSita() > relativeSita) {
			nextRingSector.setEndRadian(this.firstRingSector.getStartRadian());
		}

		Vector candidateNodes = curNa.getNeighbors(nextRingSector);
		RelativeRadianComparator comparator = new RelativeRadianComparator(
				nextRingSector.getStartRadian());
		Collections.sort(candidateNodes, comparator);

		NeighborAttachment candidateNode;
		NeighborAttachment candidateNodePrev;
		NeighborAttachment nextNa = null;
		boolean isNextNa;
		double cost;
		double minCost = Double.MAX_VALUE;
		for (int i = candidateNodes.size() - 1; i >= 0; i--) {
			candidateNode = (NeighborAttachment) candidateNodes.elementAt(i);
			isNextNa = true;
			for (int j = 0; j < i; j++) {
				candidateNodePrev = (NeighborAttachment) candidateNodes
						.elementAt(j);
				if (candidateNode.getNode().getPos().distance(
						candidateNodePrev.getNode().getPos()) > this.getAlg()
						.getParam().getRADIO_RANGE()) {
					isNextNa = false;
					break;
				}
			}
			if (isNextNa) {
				cost = this.viaNodeCost(curNa, candidateNodes, i, false);
				if (cost < minCost) {
					nextNa = candidateNode;
					minCost = cost;
				}
			}
		}

		if (nextNa == null) {
			return nextRingSector;
		}

		double averageAnwerSentTimes = this.viaNodeTimes(curNa, candidateNodes,
				candidateNodes.indexOf(nextNa), false);
		if (averageAnwerSentTimes >= this.averageAnswerSentTimesThrehold) {
			NeighborAttachment na = (NeighborAttachment) candidateNodes
					.elementAt(0);
			nextRingSector.setEndRadian(this.ring.getCircleCentre().bearing(
					na.getNode().getPos())
					- MIN_DOUBLE_VALUE);
			return nextRingSector;
		}

		if (nextNa != null) {
			nextRingSector.setEndRadian(this.ring.getCircleCentre().bearing(
					nextNa.getNode().getPos())
					+ MIN_DOUBLE_VALUE);
		}

		// check if reach the end of the ring
		relativeSita = Radian.relativeTo(this.firstRingSector.getStartRadian(),
				nextRingSector.getStartRadian());
		if (nextRingSector.getSita() > relativeSita)
			nextRingSector.setEndRadian(this.firstRingSector.getStartRadian());
		return nextRingSector;
	}

	protected RingSector caculateNextRingSectorOptimize(
			NeighborAttachment curNa, RingSector curRingSector) {
		RingSector nextRingSector;

		double l = this.getRing().getLowRadius();
		double h = this.getRing().getHighRadius();
		double r = this.getAlg().getParam().getRADIO_RANGE();
		double d = curNa.getNode().getPos().distance(
				this.getRing().getCircleCentre());
		double startRadian = this.getRing().getCircleCentre().bearing(
				curNa.getNode().getPos());
		double l2 = l * l;
		double h2 = h * h;
		double r2 = r * r;
		double d2 = d * d;
		double cosSita1, sita1 = 0;
		double cosSita2, sita2 = 0;
		double sita;
		double relativeSita;

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
		sita -= Radian.relativeTo(curRingSector.getEndRadian(), startRadian);
		nextRingSector = new RingSector(this.ring, curRingSector
				.getStartRadian(), sita + curRingSector.getSita());
		if (curRingSector.equals(this.firstRingSector)) {
			if (nextRingSector.getSita() > Math.PI * 2)
				nextRingSector.setSita(Math.PI * 2);
		} else {
			relativeSita = Radian.relativeTo(this.firstRingSector
					.getStartRadian(), nextRingSector.getStartRadian());
			if (nextRingSector.getSita() > relativeSita) {
				nextRingSector.setEndRadian(this.firstRingSector
						.getStartRadian());
			}
		}

		Vector candidateNodes = curNa.getNeighbors(nextRingSector);
		if (nextRingSector.contains(curNa.getPos()))
			candidateNodes.add(curNa);
		RelativeRadianComparator comparator = new RelativeRadianComparator(
				nextRingSector.getStartRadian());
		Collections.sort(candidateNodes, comparator);

		NeighborAttachment candidateNode;
		NeighborAttachment candidateNodePrev;
		NeighborAttachment nextNa = null;
		boolean isNextNa;
		double cost;
		double minCost = Double.MAX_VALUE;
		for (int i = candidateNodes.size() - 1; i >= 0; i--) {
			candidateNode = (NeighborAttachment) candidateNodes.elementAt(i);
			isNextNa = true;
			for (int j = 0; j < i; j++) {
				candidateNodePrev = (NeighborAttachment) candidateNodes
						.elementAt(j);
				if (candidateNode.getNode().getPos().distance(
						candidateNodePrev.getNode().getPos()) > this.getAlg()
						.getParam().getRADIO_RANGE()) {
					isNextNa = false;
					break;
				}
			}
			if (isNextNa) {
				cost = this.viaNodeCost(curNa, candidateNodes, i, true);
				if (cost < minCost) {
					nextNa = candidateNode;
					minCost = cost;
				}
			}
		}

		double averageAnwerSentTimes = this.viaNodeTimes(curNa, candidateNodes,
				candidateNodes.indexOf(nextNa), false);
		if (averageAnwerSentTimes >= this.averageAnswerSentTimesThrehold) {
			NeighborAttachment na = (NeighborAttachment) candidateNodes
					.elementAt(0);
			nextRingSector.setEndRadian(this.ring.getCircleCentre().bearing(
					na.getNode().getPos())
					- MIN_DOUBLE_VALUE);
			return nextRingSector;
		}

		if (nextNa != null) {
			nextRingSector.setEndRadian(this.ring.getCircleCentre().bearing(
					nextNa.getNode().getPos())
					+ MIN_DOUBLE_VALUE);
		}

		// check if reach the end of the ring
		if (curRingSector.equals(this.firstRingSector)) {
			if (nextRingSector.getSita() > Math.PI * 2)
				nextRingSector.setSita(Math.PI * 2);
		} else {
			relativeSita = Radian.relativeTo(this.firstRingSector
					.getStartRadian(), nextRingSector.getStartRadian());
			if (nextRingSector.getSita() > relativeSita)
				nextRingSector.setEndRadian(this.firstRingSector
						.getStartRadian());
		}
		return nextRingSector;
	}

	class RelativeRadianComparator implements Comparator {
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

	public Shape getNextShape(NeighborAttachment curNa, Shape curShape) {
		if (this.isEnd(curNa, curShape))
			return null;
		if (curNa != null && curShape == null) { // first call
			return this.caculateFirstRingSector();
		} else if (curNa != null && curShape != null) {
			if (curShape.contains(curNa.getPos())) {
				return this
						.caculateNextRingSector(curNa, (RingSector) curShape);
			} else {
				return this.caculateNextRingSector((RingSector) curShape);
			}
		}
		return null;
	}

	public boolean isEnd(NeighborAttachment curNa, Shape curShape) {
		RingSector curRingSector = (RingSector) curShape;
		if (curShape != null) {
			if (this.firstRingSector.getStartRadian() == curRingSector
					.getEndRadian())
				return true;
			else
				return false;
		} else
			return false;
	}

	public NeighborAttachment getOptimizeNextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape) {
		RingSector optimizedNextShape = (RingSector) this
				.getOptimizedNextShape(curNa, curShape);
		return this.nextNode(curNa, optimizedNextShape);
	}

	public Shape getOptimizedNextShape(NeighborAttachment curNa, Shape curShape) {
		return this
				.caculateNextRingSectorOptimize(curNa, (RingSector) curShape);
	}
}
