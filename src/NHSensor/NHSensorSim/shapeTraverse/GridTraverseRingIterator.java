package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.RingSector;
import NHSensor.NHSensorSim.shape.Shape;

public class GridTraverseRingIterator extends TraverseRingIterator {
	double ringSectorSita;
	Vector ringSectors;

	protected Vector calculateRingSectors(NeighborAttachment firstNodeInRing,
			Ring ring, double ringSectorSita) {

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

		Vector rss = new Vector();

		for (int i = 0; i < ringSectorNum2 - 1; i++) {
			rss.add(new RingSector(this.ring.getCircleCentre(), lowRadius,
					highRadius, i * ringSectorSita + firstBearing,
					ringSectorSita));
		}
		rss.add(new RingSector(this.ring.getCircleCentre(), lowRadius,
				highRadius, (ringSectorNum2 - 1) * ringSectorSita
						+ firstBearing, restSita));
		return rss;
	}

	public GridTraverseRingIterator(Algorithm alg, NeighborAttachment root,
			Ring ring, double ringSectorSita) {
		super(alg, root, ring);
		this.ringSectorSita = ringSectorSita;
		this.ringSectors = this
				.calculateRingSectors(root, ring, ringSectorSita);
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

	protected NeighborAttachment nextNode(NeighborAttachment cur,
			RingSector nextRingSector) {
		AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
				nextRingSector);
		Vector neighborsInNextRect = cur.getNeighbors(this.alg.getParam()
				.getRADIO_RANGE(), nairc);

		if (neighborsInNextRect.size() == 0) {
			return null;
		} else
			return (NeighborAttachment) neighborsInNextRect.elementAt(0);

	}

	public Shape getNextShape(NeighborAttachment curNa, Shape curShape) {
		if (this.isEnd(curNa, curShape))
			return null;
		if (curNa != null && curShape == null) { // first call
			if (this.ringSectors.size() > 0) {
				return (RingSector) this.ringSectors.elementAt(0);
			} else
				return null;
		} else if (curNa != null && curShape != null) {
			int curShapeIndex = this.ringSectors.indexOf(curShape);
			if (curShapeIndex < this.ringSectors.size() - 1) {
				return (RingSector) this.ringSectors
						.elementAt(curShapeIndex + 1);
			} else
				return null;
		}
		return null;
	}

	public boolean isEnd(NeighborAttachment curNa, Shape curShape) {
		if (this.ringSectors.size() == 0)
			return false;

		if (curShape != null) {
			int curShapeIndex = this.ringSectors.indexOf(curShape);
			if (curShapeIndex == this.ringSectors.size() - 1) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	public double getRingSectorSita() {
		return ringSectorSita;
	}

}
