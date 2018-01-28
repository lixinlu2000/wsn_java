package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.Line;
import NHSensor.NHSensorSim.shape.LineCircleInRing;
import NHSensor.NHSensorSim.shape.LineLineInRing;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.RingDirection;
import NHSensor.NHSensorSim.shape.RingSector;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shape.ShapeUtil;

public class LineTraverseRingIteratorLinkAware extends TraverseRingIterator {
	RingSector firstRingSector;
	static final double MINI_DOUBLE_VALUE = 0.0001;
	boolean containRightDown = false;
	boolean containRightUp = false;
	int partialAnswerSize = 0;

	public LineTraverseRingIteratorLinkAware(Algorithm alg,
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

	public int getPartialAnswerSize() {
		return partialAnswerSize;
	}

	public void setPartialAnswerSize(int partialAnswerSize) {
		this.partialAnswerSize = partialAnswerSize;
	}

	public NeighborAttachment getNextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape) {
		if (curNa == null && curShape == null) {
			return null;
		} else if (curNa != null && curShape == null) { // first call
			return null;
		} else if (curNa != null && curShape != null) {
			if (!curShape.contains(curNa.getNode().getPos())) {
				return null;
			} else {
				NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(
						curNa, curShape, this.partialAnswerSize);
				return nextNaAndShape.getNextNa();
			}
		}
		return null;
	}

	protected double cost(NeighborAttachment curNa, NeighborAttachment nextNa,
			LineLineInRing nextLineLineInRing, int partialAnswerSize) {
		Vector neighborNodes = curNa.getNeighbors(nextLineLineInRing);
		double totalCost = 0;
		double collectAnswerCost = 0;
		NeighborAttachment na;
		int nextNaIndex = neighborNodes.indexOf(nextNa);
		double broadcastQueryMessageTimes = 0;
		double times = 0;

		for (int i = 0; i <= neighborNodes.size(); i++) {
			na = (NeighborAttachment) neighborNodes.elementAt(i);
			if (i != nextNaIndex)
				collectAnswerCost += this.alg.getParam().getANSWER_SIZE()
						* na.getSendTimes(nextNa);
			times = curNa.getSendTimes(na);
			broadcastQueryMessageTimes = Math.max(times,
					broadcastQueryMessageTimes);
		}
		collectAnswerCost += partialAnswerSize * curNa.getSendTimes(nextNa);
		totalCost += collectAnswerCost;
		totalCost += this.alg.getParam().getQUERY_MESSAGE_SIZE()
				* broadcastQueryMessageTimes;
		totalCost /= neighborNodes.size();
		return totalCost;
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

		return LineTraverseRingIteratorLinkAware.calculateRingSectorSita(
				lowRadius, highRadius, this.getAlg().getParam()
						.getRADIO_RANGE());
	}

	protected LineLineInRing caculateNextShape(LineLineInRing curLineLineInRing) {
		Circle c1 = new Circle(curLineLineInRing.getEH(), this.getAlg()
				.getParam().getRADIO_RANGE());
		Circle c2 = new Circle(curLineLineInRing.getEL(), this.getAlg()
				.getParam().getRADIO_RANGE());

		double sita1 = ShapeUtil.circleIntersectRingMinSita(c1, this.ring);
		double sita2 = ShapeUtil.circleIntersectRingMinSita(c2, this.ring);
		Position ringCircleCentre = curLineLineInRing.getRing()
				.getCircleCentre();
		Position curShapeCentre = curLineLineInRing.getCentre();
		double sita3 = ringCircleCentre.bearing(curShapeCentre);
		double sita;
		Line end;

		if (curLineLineInRing.getDirection() == RingDirection.ANTICLOCKWISE) {
			sita1 += ringCircleCentre.bearing(curLineLineInRing.getEH());
			sita2 += ringCircleCentre.bearing(curLineLineInRing.getEL());
			sita1 = Radian.relativeTo(sita1, sita3);
			sita2 = Radian.relativeTo(sita2, sita3);
			sita = Math.min(sita1, sita2);
			end = new Line(ringCircleCentre, sita3 + sita);
		} else {
			sita1 = curLineLineInRing.getRing().getCircleCentre().bearing(
					curLineLineInRing.getEH())
					- sita1;
			sita2 = curLineLineInRing.getRing().getCircleCentre().bearing(
					curLineLineInRing.getEL())
					- sita2;
			sita1 = Radian.relativeTo(sita3, sita1);
			sita2 = Radian.relativeTo(sita3, sita2);
			sita = Math.min(sita1, sita2);
			end = new Line(ringCircleCentre, sita3 - sita);
		}

		return new LineLineInRing(curLineLineInRing.getEnd(), end,
				curLineLineInRing.getRing(), curLineLineInRing.getDirection());
	}

	protected RingSector caculateNextRingSector(Circle circle, Ring ring) {
		double minSita = ShapeUtil.circleIntersectRingMinSita(circle, ring);
		double startRadian = ring.getCircleCentre().bearing(circle.getCentre())
				+ minSita;

		double nextRingSectorSita = this.caculateFirstRingSectorSita();
		RingSector nextRingSector = new RingSector(ring, startRadian,
				nextRingSectorSita);

		// check if reach the end of the ring
		double sita = Radian.relativeTo(this.firstRingSector.getStartRadian(),
				nextRingSector.getStartRadian());
		if (nextRingSector.getSita() > sita)
			nextRingSector.setEndRadian(this.firstRingSector.getStartRadian());
		return nextRingSector;
	}

	protected RingSector caculateNextRingSector(RingSector curRingSector) {
		double ringSectorSita = this.caculateFirstRingSectorSita();
		RingSector nextRingSector = new RingSector(curRingSector
				.getCircleCentre(), curRingSector.getLowRadius(), curRingSector
				.getHighRadius(), curRingSector.getEndRadian(), ringSectorSita);

		// check if reach the end of the ring
		double sita = Radian.relativeTo(this.firstRingSector.getStartRadian(),
				nextRingSector.getStartRadian());
		if (nextRingSector.getSita() > sita)
			nextRingSector.setEndRadian(this.firstRingSector.getStartRadian());
		return nextRingSector;
	}

	/*
	 * check if circle intersects ring with two points
	 */
	protected boolean circleIntersectRing(Circle circle, Ring ring) {
		double d = circle.getCentre().distance(ring.getCircleCentre());
		double d1 = d - ring.getLowRadius();
		double d2 = ring.getHighRadius() - d;
		double r = circle.getRadius();
		if (r >= d1 && r >= d2)
			return true;
		else
			return false;
	}

	protected NextNaAndShape caculateNextNaAndShape(NeighborAttachment curNa,
			Shape curShape, int partialAnswerSize) {
		LineCircleInRing maxNextShape = null;
		NeighborAttachment nextNa = null;
		Circle circle;

		LineLineInRing curLineLineInRing = (LineLineInRing) curShape;
		circle = new Circle(curNa.getPos(), this.getAlg().getParam()
				.getRADIO_RANGE());
		maxNextShape = new LineCircleInRing(curLineLineInRing.getEnd(), circle,
				this.getRing(), curLineLineInRing.getDirection());
		Shape nextShape = maxNextShape.getMaxContainedLineLineInRing();

		Vector neighborNodes = curNa.getNeighbors(this.getAlg().getParam()
				.getRADIO_RANGE(), maxNextShape);

		if (neighborNodes.size() == 0) {
			return new NextNaAndShape(nextNa, nextShape);
		} else if (neighborNodes.size() == 1) {
			nextNa = (NeighborAttachment) neighborNodes.elementAt(0);
			return new NextNaAndShape(nextNa, nextShape);
		} else {
			Circle innerCircle = this.ring.getInnerCircle();
			Circle outerCircle = this.ring.getOuterCircle();
			LineLineInRing maxLineLineInRing = maxNextShape
					.getMaxContainedLineLineInRing();
			LineLineInRing nextLineLineInRingCandidate;
			NeighborAttachment na1, na2;
			Line line;
			Position p1, p2;
			double cost, minCost = Double.MAX_VALUE;

			for (int i = 0; i < neighborNodes.size() - 1; i++) {
				for (int j = i; j < neighborNodes.size(); j++) {
					na1 = (NeighborAttachment) neighborNodes.elementAt(i);
					na2 = (NeighborAttachment) neighborNodes.elementAt(j);
					line = new Line(na1.getPos(), na2.getPos());
					p1 = line.intersectNear(innerCircle);
					p2 = line.intersectNear(outerCircle);
					if (maxLineLineInRing.contains(p1)
							&& maxLineLineInRing.contains(p2)) {
						nextLineLineInRingCandidate = new LineLineInRing(
								maxNextShape.getLine(), line, maxNextShape
										.getRing(), maxNextShape.getDirection());
						cost = this.cost(curNa, na1,
								nextLineLineInRingCandidate, partialAnswerSize);
						if (cost < minCost) {
							minCost = cost;
							nextNa = na1;
							nextShape = nextLineLineInRingCandidate;
						}

						cost = this.cost(curNa, na2,
								nextLineLineInRingCandidate, partialAnswerSize);
						if (cost < minCost) {
							minCost = cost;
							nextNa = na2;
							nextShape = nextLineLineInRingCandidate;
						}
					}
				}
			}
		}
		return new NextNaAndShape(nextNa, nextShape);
	}

	public Shape getNextShape(NeighborAttachment curNa, Shape curShape) {
		if (curNa != null && curShape == null) { // first call
			return this.firstRingSector.toLineLineInRing();
		} else if (curNa != null && curShape != null) {
			if (curShape.contains(curNa.getPos())) {
				NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(
						curNa, curShape, this.partialAnswerSize);
				return nextNaAndShape.getNextShape();
			} else {
				return this.caculateNextShape((LineLineInRing) curShape);
			}
		}
		return null;
	}

	public boolean isEnd(NeighborAttachment curNa, Shape curShape) {
		return this.isEnd(curShape);
	}

	public boolean isEnd(Shape curShape) {
		if (curShape == null)
			return false;
		if (curShape instanceof RingSector) {
			RingSector ringSector = (RingSector) curShape;
			if (ringSector.equals(this.firstRingSector))
				return false;
		}

		if (curShape.contains(this.firstRingSector.getRightDownVertex0())) {
			this.containRightDown = true;
		}
		if (curShape.contains(this.firstRingSector.getRightUpVertex0())) {
			this.containRightUp = true;
		}
		if (this.containRightDown && this.containRightUp)
			return true;
		else
			return false;
	}

}
