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

public class ROCAdaptiveGridTraverseRingIterator extends TraverseRingIterator {
	RingSector firstRingSector;

	public ROCAdaptiveGridTraverseRingIterator(Algorithm alg,
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

		return ROCAdaptiveGridTraverseRingIterator.calculateRingSectorSita(
				lowRadius, highRadius, this.getAlg().getParam()
						.getRADIO_RANGE());
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

		double maxSita = this.caculateMaxSita();

		if (sita <= maxSita) {
			// TODO magic code
			sita *= 0.999;

			nextRingSector = new RingSector(this.ring.getCircleCentre(), l, h,
					curRingSector.getEndRadian(), sita);

			// check if reach the end of the ring
			double relativeSita = Radian.relativeTo(this.firstRingSector
					.getStartRadian(), nextRingSector.getStartRadian());
			if (nextRingSector.getSita() > relativeSita)
				nextRingSector.setEndRadian(this.firstRingSector
						.getStartRadian());

			return nextRingSector;
		}

		// caculate min nextRingSector
		RingSector shouldContainRingSector = new RingSector(this.ring
				.getCircleCentre(), l, h, curRingSector.getEndRadian(), maxSita);
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

		Vector shouldContainNodes = curNa.getNeighbors(shouldContainRingSector);
		Vector candidateNodes = curNa.getNeighbors(candidateRingSector);
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

		nextRingSector = new RingSector(this.ring.getCircleCentre(), l, h,
				curRingSector.getEndRadian(), maxSita);
		if (newShouldContainNode != null) {
			nextRingSector.setEndRadian(this.ring.getCircleCentre().bearing(
					newShouldContainNode.getNode().getPos()));
		}
		// check if reach the end of the ring
		relativeSita = Radian.relativeTo(this.firstRingSector.getStartRadian(),
				nextRingSector.getStartRadian());
		if (nextRingSector.getSita() > relativeSita)
			nextRingSector.setEndRadian(this.firstRingSector.getStartRadian());
		return nextRingSector;
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
}
