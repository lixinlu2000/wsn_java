package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.AngleCircleInRing;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.CircleAngleInRing;
import NHSensor.NHSensorSim.shape.CircleCircleInRing;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Radian;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.RingSector;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.shape.ShapeUtil;

public class EDCCircleTraverseRingIteratorFailureAware extends
		TraverseRingIterator implements TraverseRingRepairIterator {
	RingSector firstRingSector;
	static final double MINI_DOUBLE_VALUE = 0.0001;
	EDCCircleTraverseRingIteratorUseEndRadian eDCCircleTraverseRingIteratorUseEndRadian;
	boolean containRightDown = false;
	boolean containRightUp = false;

	public EDCCircleTraverseRingIteratorFailureAware(Algorithm alg,
			NeighborAttachment root, Ring ring) {
		super(alg, root, ring);
		this.firstRingSector = this.caculateFirstRingSector();
		eDCCircleTraverseRingIteratorUseEndRadian = new EDCCircleTraverseRingIteratorUseEndRadian(
				alg, root, ring);
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
		if (curNa == null && curShape == null) {
			return null;
		} else if (curNa != null && curShape == null) { // first call
			return null;
		} else if (curNa != null && curShape != null) {
			if (!curShape.contains(curNa.getNode().getPos())) {
				return null;
			} else {
				NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(
						curNa, curShape);
				return nextNaAndShape.getNextNa();
			}
		}
		return null;
	}

	protected NeighborAttachment nextNodeByDistance(NeighborAttachment curNa,
			Vector neighborNodes) {
		RelativeDistanceComparator comparator = new RelativeDistanceComparator(
				curNa.getPos());
		Collections.sort(neighborNodes, comparator);

		NeighborAttachment nextNa = null;
		NeighborAttachment nextNaCandidate = null;
		NeighborAttachment nextNaPrev;
		boolean isNextNa;
		for (int i = neighborNodes.size() - 1; i >= 0; i--) {
			nextNaCandidate = (NeighborAttachment) neighborNodes.elementAt(i);
			isNextNa = true;
			for (int j = 0; j < i; j++) {
				nextNaPrev = (NeighborAttachment) neighborNodes.elementAt(j);
				if (nextNaCandidate.getNode().getPos().distance(
						nextNaPrev.getNode().getPos()) > this.getAlg()
						.getParam().getRADIO_RANGE()) {
					isNextNa = false;
					break;
				}
			}

			if (isNextNa) {
				nextNa = nextNaCandidate;
				break;
			}
		}
		return nextNa;

	}

	protected NeighborAttachment nextNodeByRadian(NeighborAttachment curNa,
			Vector neighborNodes) {
		double baseRadian = this.getRing().getCircleCentre().bearing(
				curNa.getPos());
		RelativeRadianComparator comparator = new RelativeRadianComparator(
				baseRadian);
		Collections.sort(neighborNodes, comparator);

		NeighborAttachment nextNa = null;
		NeighborAttachment nextNaCandidate = null;
		NeighborAttachment nextNaPrev;
		boolean isNextNa;
		for (int i = neighborNodes.size() - 1; i >= 0; i--) {
			nextNaCandidate = (NeighborAttachment) neighborNodes.elementAt(i);
			isNextNa = true;
			for (int j = 0; j < i; j++) {
				nextNaPrev = (NeighborAttachment) neighborNodes.elementAt(j);
				if (nextNaCandidate.getNode().getPos().distance(
						nextNaPrev.getNode().getPos()) > this.getAlg()
						.getParam().getRADIO_RANGE()) {
					isNextNa = false;
					break;
				}
			}

			if (isNextNa) {
				nextNa = nextNaCandidate;
				break;
			}
		}
		return nextNa;
	}

	protected int backupNodeNum(NeighborAttachment curNa, Vector neighborNodes) {
		double baseRadian = this.getRing().getCircleCentre().bearing(
				curNa.getPos());
		RelativeRadianComparator comparator = new RelativeRadianComparator(
				baseRadian);
		Collections.sort(neighborNodes, comparator);

		int num = 0;
		NeighborAttachment nextNaCandidate = null;
		NeighborAttachment nextNaPrev;
		boolean isNextNa;
		for (int i = neighborNodes.size() - 1; i >= 0; i--) {
			nextNaCandidate = (NeighborAttachment) neighborNodes.elementAt(i);
			isNextNa = true;
			for (int j = 0; j < i; j++) {
				nextNaPrev = (NeighborAttachment) neighborNodes.elementAt(j);
				if (nextNaCandidate.getNode().getPos().distance(
						nextNaPrev.getNode().getPos()) > this.getAlg()
						.getParam().getRADIO_RANGE()) {
					isNextNa = false;
					break;
				}
			}

			if (isNextNa) {
				num = i + 1;
				break;
			}
		}
		return num;
	}

	protected NeighborAttachment nextNode(NeighborAttachment curNa,
			Shape nextShape) {
		Vector neighborNodes = curNa.getNeighbors(this.getAlg().getParam()
				.getRADIO_RANGE(), nextShape);
		return this.nextNodeByDistance(curNa, neighborNodes);
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

		return EDCCircleTraverseRingIteratorFailureAware
				.calculateRingSectorSita(lowRadius, highRadius, this.getAlg()
						.getParam().getRADIO_RANGE());
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

	protected RingSector caculateNextRingSector(
			CircleAngleInRing curCircleAngleInRing) {
		double startRadian = curCircleAngleInRing.getAngle();
		double nextRingSectorSita = this.caculateFirstRingSectorSita();
		RingSector nextRingSector = new RingSector(curCircleAngleInRing
				.getRing(), startRadian, nextRingSectorSita);

		// check if reach the end of the ring
		double sita = Radian.relativeTo(this.firstRingSector.getStartRadian(),
				nextRingSector.getStartRadian());
		if (nextRingSector.getSita() > sita)
			nextRingSector.setEndRadian(this.firstRingSector.getStartRadian());
		return nextRingSector;
	}

	/*
	 * if cannot greedy forward to curShape then caculate the shape after
	 * curShape. All the node in next shape can communicate with each other.
	 */
	protected Shape caculateNextShape(Shape curShape) {
		Shape nextShape = null;
		Circle circle;

		if (curShape instanceof RingSector) {
			RingSector curRingSector = (RingSector) curShape;
			return this.caculateNextRingSector(curRingSector);
		} else if (curShape instanceof AngleCircleInRing) {
			AngleCircleInRing curAngleCircleInRing = (AngleCircleInRing) curShape;
			circle = curAngleCircleInRing.getCircle();
			return this.caculateNextRingSector(circle, this.getRing());
		} else if (curShape instanceof CircleCircleInRing) {
			CircleCircleInRing curCircleCircleInRing = (CircleCircleInRing) curShape;
			circle = curCircleCircleInRing.getCircleEnd();
			return this.caculateNextRingSector(circle, this.getRing());
		} else if (curShape instanceof CircleAngleInRing) {
			CircleAngleInRing curCircleAngleInRing = (CircleAngleInRing) curShape;
			return this.caculateNextRingSector(curCircleAngleInRing);
		}
		return nextShape;
	}

	protected RingSector truncateShape(AngleCircleInRing shape) {
		double sita = Radian.relativeTo(this.firstRingSector.getStartRadian(),
				shape.getAngle());
		return new RingSector(this.ring, shape.getAngle(), sita);
	}

	protected CircleAngleInRing truncateShape(CircleCircleInRing shape) {
		return new CircleAngleInRing(shape.getCircleBegin(),
				this.firstRingSector.getStartRadian(), this.ring);
	}

	protected CircleAngleInRing truncateShape(CircleAngleInRing shape) {
		return new CircleAngleInRing(shape.getCircle(), this.firstRingSector
				.getStartRadian(), this.ring);
	}

	protected RingSector truncateShape(RingSector shape) {
		double sita = Radian.relativeTo(this.firstRingSector.getStartRadian(),
				shape.getStartRadian());
		return new RingSector(this.ring, shape.getStartRadian(), sita);
	}

	protected Shape truncateShape(Shape shape) {
		if (shape instanceof RingSector) {
			return this.truncateShape((RingSector) shape);
		} else if (shape instanceof AngleCircleInRing) {
			return this.truncateShape((AngleCircleInRing) shape);
		} else if (shape instanceof CircleAngleInRing) {
			return this.truncateShape((CircleAngleInRing) shape);
		} else if (shape instanceof CircleCircleInRing) {
			return this.truncateShape((CircleCircleInRing) shape);
		} else
			return null;
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
			Shape curShape) {
		Shape maxNextShape = null;
		NeighborAttachment nextNa;
		Shape nextShape = null;
		Circle circle;

		if (curShape instanceof RingSector) {
			RingSector curRingSector = (RingSector) curShape;
			circle = new Circle(curNa.getPos(), this.getAlg().getParam()
					.getRADIO_RANGE());
			maxNextShape = new AngleCircleInRing(curRingSector.getEndRadian(),
					circle, this.getRing());
			if (this.isEnd(maxNextShape)) {
				maxNextShape = this.truncateShape(maxNextShape);
			}
			Vector neighborNodes = curNa.getNeighbors(this.getAlg().getParam()
					.getRADIO_RANGE(), maxNextShape);
			nextNa = this.nextNodeByDistance(curNa, neighborNodes);
			if (nextNa == null) {
				nextShape = maxNextShape;
				return new NextNaAndShape(nextNa, nextShape);
			} else {
				circle = new Circle(
						curNa.getPos(),
						nextNa.getPos().distance(curNa.getPos())
								+ EDCCircleTraverseRingIteratorFailureAware.MINI_DOUBLE_VALUE);
				if (!this.circleIntersectRing(circle, this.ring)) {
					return this.eDCCircleTraverseRingIteratorUseEndRadian
							.getNextNaAndShape(curNa, curShape);
				} else {
					nextShape = new AngleCircleInRing(curRingSector
							.getEndRadian(), circle, this.getRing());
					return new NextNaAndShape(nextNa, nextShape);
				}
			}
		} else if (curShape instanceof AngleCircleInRing) {
			AngleCircleInRing curAngleCircleInRing = (AngleCircleInRing) curShape;
			circle = new Circle(curNa.getPos(), this.getAlg().getParam()
					.getRADIO_RANGE());
			maxNextShape = new CircleCircleInRing(curAngleCircleInRing
					.getCircle(), circle, this.getRing());
			if (this.isEnd(maxNextShape)) {
				maxNextShape = this.truncateShape(maxNextShape);
			}
			Vector neighborNodes = curNa.getNeighbors(this.getAlg().getParam()
					.getRADIO_RANGE(), maxNextShape);
			nextNa = this.nextNodeByDistance(curNa, neighborNodes);

			if (nextNa == null) {
				nextShape = maxNextShape;
				return new NextNaAndShape(nextNa, nextShape);
			} else {
				circle = new Circle(
						curNa.getPos(),
						nextNa.getPos().distance(curNa.getPos())
								+ EDCCircleTraverseRingIteratorFailureAware.MINI_DOUBLE_VALUE);
				if (!this.circleIntersectRing(circle, this.ring)) {
					return this.eDCCircleTraverseRingIteratorUseEndRadian
							.getNextNaAndShape(curNa, curShape);
				} else {
					nextShape = new CircleCircleInRing(curAngleCircleInRing
							.getCircle(), circle, this.getRing());
					return new NextNaAndShape(nextNa, nextShape);
				}
			}
		} else if (curShape instanceof CircleCircleInRing) {
			CircleCircleInRing curCircleCircleInRing = (CircleCircleInRing) curShape;
			circle = new Circle(curNa.getPos(), this.getAlg().getParam()
					.getRADIO_RANGE());
			maxNextShape = new CircleCircleInRing(curCircleCircleInRing
					.getCircleEnd(), circle, this.getRing());
			if (this.isEnd(maxNextShape)) {
				maxNextShape = this.truncateShape(maxNextShape);
			}
			Vector neighborNodes = curNa.getNeighbors(this.getAlg().getParam()
					.getRADIO_RANGE(), maxNextShape);
			nextNa = this.nextNodeByDistance(curNa, neighborNodes);

			if (nextNa == null) {
				nextShape = maxNextShape;
				return new NextNaAndShape(nextNa, nextShape);
			} else {
				circle = new Circle(
						curNa.getPos(),
						nextNa.getPos().distance(curNa.getPos())
								+ EDCCircleTraverseRingIteratorFailureAware.MINI_DOUBLE_VALUE);
				if (!this.circleIntersectRing(circle, this.ring)) {
					return this.eDCCircleTraverseRingIteratorUseEndRadian
							.getNextNaAndShape(curNa, curShape);
				} else {
					nextShape = new CircleCircleInRing(curCircleCircleInRing
							.getCircleEnd(), circle, this.getRing());
					return new NextNaAndShape(nextNa, nextShape);
				}
			}
		} else if (curShape instanceof CircleAngleInRing) {
			CircleAngleInRing curCircleAngleInRing = (CircleAngleInRing) curShape;
			circle = new Circle(curNa.getPos(), this.getAlg().getParam()
					.getRADIO_RANGE());
			maxNextShape = new AngleCircleInRing(curCircleAngleInRing
					.getAngle(), circle, this.getRing());
			if (this.isEnd(maxNextShape)) {
				maxNextShape = this.truncateShape(maxNextShape);
			}
			Vector neighborNodes = curNa.getNeighbors(this.getAlg().getParam()
					.getRADIO_RANGE(), maxNextShape);
			nextNa = this.nextNodeByDistance(curNa, neighborNodes);

			if (nextNa == null) {
				nextShape = maxNextShape;
				return new NextNaAndShape(nextNa, nextShape);
			} else {
				circle = new Circle(
						curNa.getPos(),
						nextNa.getPos().distance(curNa.getPos())
								+ EDCCircleTraverseRingIteratorFailureAware.MINI_DOUBLE_VALUE);
				if (!this.circleIntersectRing(circle, this.ring)) {
					return this.eDCCircleTraverseRingIteratorUseEndRadian
							.getNextNaAndShape(curNa, curShape);
				} else {
					nextShape = new AngleCircleInRing(curCircleAngleInRing
							.getAngle(), circle, this.getRing());
					return new NextNaAndShape(nextNa, nextShape);
				}
			}
		}

		return null;
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

	public class RelativeDistanceComparator implements Comparator {
		private Position basePosition;

		public RelativeDistanceComparator(Position basePosition) {
			this.basePosition = basePosition;
		}

		public int compare(Object arg0, Object arg1) {
			NeighborAttachment n1 = (NeighborAttachment) arg0;
			NeighborAttachment n2 = (NeighborAttachment) arg1;

			double d1 = n1.getPos().distance(this.basePosition);
			double d2 = n2.getPos().distance(this.basePosition);
			if (d1 > d2)
				return 1;
			else if (d1 < d2)
				return -1;
			else
				return 0;
		}

	}

	public Shape getNextShape(NeighborAttachment curNa, Shape curShape) {
		if (curNa != null && curShape == null) { // first call
			return this.firstRingSector;
		} else if (curNa != null && curShape != null) {
			if (curShape.contains(curNa.getPos())) {
				NextNaAndShape nextNaAndShape = this.caculateNextNaAndShape(
						curNa, curShape);
				return nextNaAndShape.getNextShape();
			} else {
				return this.caculateNextShape(curShape);
			}
		}
		return null;
	}

	public boolean isEnd(NeighborAttachment curNa, Shape curShape) {
		return this.isEnd(curShape);
	}

	public boolean isEndOld(Shape curShape) {
		if (curShape == null || curShape == this.firstRingSector)
			return false;
		if (curShape.contains(this.firstRingSector.getRightDownVertex0())
				&& curShape.contains(this.firstRingSector.getRightUpVertex0()))
			return true;
		else
			return false;
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

	public NeighborAttachment getRepairedNeighborAttachment(
			NeighborAttachment prevNa, NeighborAttachment curNa, Shape curShape) {
		Vector nas = curNa.getNeighbors(curShape);
		NeighborAttachment na;
		Vector aliveNas = new Vector();

		for (int i = 0; i < nas.size(); i++) {
			na = (NeighborAttachment) nas.elementAt(i);
			if (na.isAlive()) {
				aliveNas.add(na);
			}
		}

		NeighborAttachment nextNa = this.nextNodeByRadian(prevNa, aliveNas);
		return nextNa;
	}

	public Shape getRepairedShape(NeighborAttachment prevNa,
			NeighborAttachment curNa, Shape curShape) {
		Vector nas = curNa.getNeighbors(curShape);
		NeighborAttachment na;
		Vector aliveNas = new Vector();
		Shape repairedShape = null;
		double endRadian, sita;

		for (int i = 0; i < nas.size(); i++) {
			na = (NeighborAttachment) nas.elementAt(i);
			if (na.isAlive()) {
				aliveNas.add(na);
			}
		}

		NeighborAttachment nextNa = this.nextNodeByRadian(prevNa, aliveNas);
		if (nextNa == null) {
			return curShape;
		} else {
			if (curShape instanceof RingSector) {
				RingSector curRingSector = (RingSector) curShape;
				endRadian = this.ring.getCircleCentre()
						.bearing(nextNa.getPos())
						+ MINI_DOUBLE_VALUE;
				sita = Radian.relativeTo(endRadian, curRingSector
						.getStartRadian());
				repairedShape = new RingSector(this.ring, curRingSector
						.getStartRadian(), sita);
			} else if (curShape instanceof AngleCircleInRing) {
				AngleCircleInRing curAngleCircleInRing = (AngleCircleInRing) curShape;
				endRadian = this.ring.getCircleCentre()
						.bearing(nextNa.getPos())
						+ MINI_DOUBLE_VALUE;
				sita = Radian.relativeTo(endRadian, curAngleCircleInRing
						.getAngle());
				repairedShape = new RingSector(this.ring, curAngleCircleInRing
						.getAngle(), sita);
			} else if (curShape instanceof CircleAngleInRing) {
				CircleAngleInRing curCircleAngleInRing = (CircleAngleInRing) curShape;
				endRadian = this.ring.getCircleCentre()
						.bearing(nextNa.getPos())
						+ MINI_DOUBLE_VALUE;
				repairedShape = new CircleAngleInRing(curCircleAngleInRing
						.getCircle(), endRadian, this.getRing());
			} else if (curShape instanceof CircleCircleInRing) {
				CircleCircleInRing curCircleCircleInRing = (CircleCircleInRing) curShape;
				endRadian = this.ring.getCircleCentre()
						.bearing(nextNa.getPos())
						+ MINI_DOUBLE_VALUE;
				repairedShape = new CircleAngleInRing(curCircleCircleInRing
						.getCircleBegin(), endRadian, this.getRing());
			}
			return repairedShape;
		}
	}

	public int getRepairedNeighborAttachmentNum(NeighborAttachment prevNa,
			NeighborAttachment curNa, Shape curShape) {
		Vector nas = curNa.getNeighbors(curShape);
		NeighborAttachment na;
		Vector aliveNas = new Vector();

		for (int i = 0; i < nas.size(); i++) {
			na = (NeighborAttachment) nas.elementAt(i);
			if (na.isAlive()) {
				aliveNas.add(na);
			}
		}

		return this.backupNodeNum(prevNa, aliveNas);
	}
}
