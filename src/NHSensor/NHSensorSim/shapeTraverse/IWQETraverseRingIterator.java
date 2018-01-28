package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.RingSector;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.util.Convertor;

public class IWQETraverseRingIterator extends TraverseRingIterator {
	RingSector firstRingSector;
	static final double MINI_DOUBLE_VALUE = 0.0001;
	double prevNaRelativeRadian = 0;

	public IWQETraverseRingIterator(Algorithm alg, NeighborAttachment root,
			Ring ring) {
		super(alg, root, ring);
		this.firstRingSector = this.caculateFirstRingSector();
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

	public RingSector getFirstRingSector() {
		return firstRingSector;
	}

	protected double caculateFirstRingSectorSita() {
		return this.caculateMaxSita();
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

	/*
	 * caculate the max sita of the ringSector that any two node in this
	 * ringSector can communicate with each other.
	 */
	protected double caculateMaxSita() {
		double lowRadius = this.getRing().getLowRadius();
		double highRadius = this.getRing().getHighRadius();

		return IWQETraverseRingIterator.calculateRingSectorSita(lowRadius,
				highRadius, this.getAlg().getParam().getRADIO_RANGE());
	}

	public NeighborAttachment getNextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape) {
		NeighborAttachment nextNa;
		if (this.isEnd(curNa, curShape)) {
			return null;
		}

		if (curNa == null) {
			return null;
		} else {
			if (curShape != null) {
				if (curShape.contains(curNa.getNode().getPos())) {
					nextNa = this.caculateNextNa(curNa);
					if (nextNa == curNa) {
						return null;
					} else
						return nextNa;
				} else {
					return null;
				}
			} else {
				nextNa = this.caculateNextNa(curNa);
				if (nextNa == curNa) {
					return null;
				} else
					return nextNa;
			}
		}
	}

	protected NeighborAttachment caculateNextNa(NeighborAttachment curNa) {
		Position curNaPos = curNa.getNode().getPos();
		NeighborAttachment nextNa = curNa;
		Vector neighbors = curNa.getNeighbors(this.getRing());

		NeighborAttachment neighbor;
		double neighborBearing;
		double neighborRelativeBearing;

		Position ringCircleCentre = this.getRing().getCircleCentre();
		double d = curNaPos.distance(ringCircleCentre)
				+ this.getRing().getHighRadius();
		double curNaBearing = ringCircleCentre.bearing(curNaPos);
		double maxRelativeBearing = 0;

		for (int i = 0; i < neighbors.size(); i++) {
			neighbor = (NeighborAttachment) neighbors.elementAt(i);
			neighborBearing = ringCircleCentre.bearing(neighbor.getNode()
					.getPos());

			if (this.getAlg().getParam().getRADIO_RANGE() >= d) {
				neighborRelativeBearing = Radian.relativeTo(neighborBearing,
						curNaBearing);
			} else {
				neighborRelativeBearing = Convertor.relativeRadianNPiToPi(
						neighborBearing, curNaBearing);
			}

			if (neighborRelativeBearing > maxRelativeBearing) {
				maxRelativeBearing = neighborRelativeBearing;
				nextNa = neighbor;
			}
		}
		return nextNa;
	}

	public Shape getNextShape(NeighborAttachment curNa, Shape curShape) {
		NeighborAttachment nextNa;
		if (this.isEnd(curNa, curShape))
			return null;

		if (curNa == null) {
			return null;
		} else {
			if (curShape != null) {
				if (curShape.contains(curNa.getNode().getPos())) {
					nextNa = this.caculateNextNa(curNa);
					if (nextNa == curNa) {
						return this
								.caculateNextRingSector((RingSector) curShape);
					} else
						return null;
				} else {
					return this.caculateNextRingSector((RingSector) curShape);
				}
			} else {
				nextNa = this.caculateNextNa(curNa);
				if (nextNa == curNa) {
					return this.caculateNextRingSector(curNa);
				} else
					return null;
			}
		}
	}

	protected RingSector caculateNextRingSector(NeighborAttachment curNa) {
		RingSector nextRingSector;
		Position ringCircleCentre = this.getRing().getCircleCentre();
		double curNaBearing = ringCircleCentre
				.bearing(curNa.getNode().getPos());

		// TODO ring sector sita should be optimized
		nextRingSector = new RingSector(this.getRing(), curNaBearing, this
				.caculateFirstRingSectorSita());

		double sita = Radian.relativeTo(this.firstRingSector.getStartRadian(),
				nextRingSector.getStartRadian());
		double newRingSectorSita = nextRingSector.getSita();
		if (sita < newRingSectorSita) {
			newRingSectorSita = sita;
		}
		nextRingSector.setSita(newRingSectorSita);
		return nextRingSector;
	}

	protected RingSector caculateNextRingSector(RingSector ringSector) {
		RingSector nextRingSector;

		// TODO ring sector sita should be optimized
		nextRingSector = new RingSector(this.getRing(), ringSector
				.getEndRadian(), this.caculateFirstRingSectorSita());

		double sita = Radian.relativeTo(this.firstRingSector.getStartRadian(),
				nextRingSector.getStartRadian());
		double newRingSectorSita = this.caculateFirstRingSectorSita();
		if (sita < newRingSectorSita) {
			newRingSectorSita = sita;
		}
		nextRingSector.setSita(newRingSectorSita);
		return nextRingSector;
	}

	/*
	 * bogus code
	 */
	public boolean isEnd(NeighborAttachment curNa, Shape curShape) {
		if (curShape != null) {
			return this.isReachTheEndOfRing((RingSector) curShape);
		} else if (curNa != null) {
			return this.isReachTheEndOfRing(curNa);
		} else
			return false;
	}

	public boolean isEnd(NeighborAttachment curNa, Shape curShape,
			NeighborAttachment prevNa) {
		if (curShape != null) {
			return this.isReachTheEndOfRing((RingSector) curShape);
		} else if (curNa != null) {
			if (this.isReachTheEndOfRing(curNa))
				return true;

			if (prevNa != null) {
				double relativeSita1 = Radian.relativeTo(this.firstRingSector
						.getStartRadian(), this.ring.getCircleCentre().bearing(
						prevNa.getPos()));
				double relativeSita2 = Radian.relativeTo(this.ring
						.getCircleCentre().bearing(curNa.getPos()), this.ring
						.getCircleCentre().bearing(prevNa.getPos()));
				if (relativeSita2 >= relativeSita1)
					return true;
				else
					return false;
			}
			return false;
		} else
			return false;
	}

	// TODO
	protected boolean isReachTheEndOfRing(NeighborAttachment curNa) {
		RingSector ringSector = this.caculateNextRingSector(curNa);
		return this.isReachTheEndOfRing(ringSector);
	}

	// TODO
	protected boolean isReachTheEndOfRing(RingSector ringSector) {
		double endRadian = ringSector.getEndRadian();
		double firstRingSectorStartRadian = this.firstRingSector
				.getStartRadian();

		if (endRadian == firstRingSectorStartRadian)
			return true;
		else
			return false;
	}
}
