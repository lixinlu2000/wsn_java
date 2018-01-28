package NHSensor.NHSensorSim.shape;

import java.util.Collections;
import java.util.Vector;

import org.apache.commons.math.ConvergenceException;
import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.solvers.UnivariateRealSolver;
import org.apache.commons.math.analysis.solvers.UnivariateRealSolverFactory;

import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.math.DAFunction;
import NHSensor.NHSensorSim.math.MaxAlphaForIDC;
import NHSensor.NHSensorSim.shapeTraverse.Direction;
import NHSensor.NHSensorSim.shapeTraverse.NodePositionComparator;
import NHSensor.NHSensorSim.shapeTraverse.RelativeRadianComparator;
import NHSensor.NHSensorSim.util.Convertor;
import NHSensor.NHSensorSim.util.Util;

public class ShapeUtil {
	private static final double MINOR_VALUE = 0.01;

	public static Rect minRectContainNode(Rect maxNextRect, int direction,
			NeighborAttachment na) {
		switch (direction) {
		case Direction.LEFT:
			return new Rect(na.getNode().getPos().getX() - MINOR_VALUE,
					maxNextRect.getMaxX(), maxNextRect.getMinY(), maxNextRect
							.getMaxY());
		case Direction.RIGHT:
			return new Rect(maxNextRect.getMinX(), na.getNode().getPos().getX()
					+ MINOR_VALUE, maxNextRect.getMinY(), maxNextRect.getMaxY());
		case Direction.UP:
			return new Rect(maxNextRect.getMinX(), maxNextRect.getMaxX(),
					maxNextRect.getMinY(), na.getNode().getPos().getY()
							+ MINOR_VALUE);
		case Direction.DOWN:
			return new Rect(maxNextRect.getMinX(), maxNextRect.getMaxX(), na
					.getNode().getPos().getY()
					- MINOR_VALUE, maxNextRect.getMaxY());
		default:
			return null;
		}
	}

	public static Rect firstRectHasNodeInRect(Rect maxNextRect, int direction,
			NeighborAttachment na) {
		Vector nas = na.getNeighbors(maxNextRect);
		if (!nas.contains(na)) {
			nas.add(na);
		}
		Collections.sort(nas, new NodePositionComparator(direction));
		NeighborAttachment firstNa = (NeighborAttachment) nas.elementAt(0);

		switch (direction) {
		case Direction.LEFT:
			return new Rect(firstNa.getNode().getPos().getX() - MINOR_VALUE,
					maxNextRect.getMaxX(), maxNextRect.getMinY(), maxNextRect
							.getMaxY());
		case Direction.RIGHT:
			return new Rect(maxNextRect.getMinX(), firstNa.getNode().getPos()
					.getX()
					+ MINOR_VALUE, maxNextRect.getMinY(), maxNextRect.getMaxY());
		case Direction.UP:
			return new Rect(maxNextRect.getMinX(), maxNextRect.getMaxX(),
					maxNextRect.getMinY(), firstNa.getNode().getPos().getY()
							+ MINOR_VALUE);
		case Direction.DOWN:
			return new Rect(maxNextRect.getMinX(), maxNextRect.getMaxX(),
					firstNa.getNode().getPos().getY() - MINOR_VALUE,
					maxNextRect.getMaxY());
		default:
			return null;
		}
	}

	public static NeighborAttachment firstNodeInRect(Rect maxNextRect,
			int direction, NeighborAttachment na) {
		Vector nas = na.getNeighbors(maxNextRect);
		if (!nas.contains(na)) {
			nas.add(na);
		}
		Collections.sort(nas, new NodePositionComparator(direction));
		NeighborAttachment firstNa = (NeighborAttachment) nas.elementAt(0);
		return firstNa;
	}

	public static RingSector minRingSectorContainNode(RingSector maxRingSector,
			NeighborAttachment na) {
		if (!maxRingSector.contains(na.getPos())) {
			return null;
		} else {
			double naRadian = maxRingSector.getCircleCentre().bearing(
					na.getPos());
			double relativeRadian = Radian.relativeTo(naRadian, maxRingSector
					.getStartRadian());
			if (relativeRadian > maxRingSector.getSita()) {
				return null;
			} else {
				RingSector rs = new RingSector(maxRingSector.getCircleCentre(),
						maxRingSector.getLowRadius(), maxRingSector
								.getHighRadius(), maxRingSector
								.getStartRadian(), relativeRadian);
				return rs;
			}
		}
	}

	public static RingSector firstRingSectorInRingSector(
			RingSector maxRingSector, NeighborAttachment na) {
		if (!maxRingSector.contains(na.getPos())) {
			return null;
		} else {
			double naRadian = maxRingSector.getCircleCentre().bearing(
					na.getPos());
			double relativeRadian = Radian.relativeTo(naRadian, maxRingSector
					.getStartRadian());
			if (relativeRadian > maxRingSector.getSita()) {
				return null;
			} else {
				Vector nas = na.getNeighbors(maxRingSector);
				if (!nas.contains(na)) {
					nas.add(na);
				}
				Collections.sort(nas, new RelativeRadianComparator(
						maxRingSector.getCircleCentre(), maxRingSector
								.getStartRadian()));
				NeighborAttachment firstNa = (NeighborAttachment) nas
						.elementAt(0);

				naRadian = maxRingSector.getCircleCentre().bearing(
						firstNa.getPos());
				relativeRadian = Radian.relativeTo(naRadian, maxRingSector
						.getStartRadian());
				RingSector rs = new RingSector(maxRingSector.getCircleCentre(),
						maxRingSector.getLowRadius(), maxRingSector
								.getHighRadius(), maxRingSector
								.getStartRadian(), relativeRadian);
				return rs;
			}
		}
	}

	public static NeighborAttachment firstNodeInRingSector(
			RingSector maxRingSector, NeighborAttachment na) {
		if (!maxRingSector.contains(na.getPos())) {
			return null;
		} else {
			double naRadian = maxRingSector.getCircleCentre().bearing(
					na.getPos());
			double relativeRadian = Radian.relativeTo(naRadian, maxRingSector
					.getStartRadian());
			if (relativeRadian > maxRingSector.getSita()) {
				return null;
			} else {
				Vector nas = na.getNeighbors(maxRingSector);
				if (!nas.contains(na)) {
					nas.add(na);
				}
				Collections.sort(nas, new RelativeRadianComparator(
						maxRingSector.getCircleCentre(), maxRingSector
								.getStartRadian()));
				NeighborAttachment firstNa = (NeighborAttachment) nas
						.elementAt(0);
				return firstNa;
			}
		}
	}

	public static double circleIntersectRingMaxSita(Circle circle, Ring ring) {
		Position circleCentre = circle.getCentre();
		double l = ring.getLowRadius();
		double h = ring.getHighRadius();
		double r = circle.getRadius();
		double d = circleCentre.distance(ring.getCircleCentre());
		double l2 = l * l;
		double h2 = h * h;
		double r2 = r * r;
		double d2 = d * d;
		double d3;
		double cosSita1, sita1 = 0;
		double cosSita2, sita2 = 0;
		double cosSita3, sita3 = 0;
		double sita;

		// bug fixed
		if (r >= d + h) {
			sita = 2 * Math.PI;
		} else if (r >= d + l) {
			sita1 = Math.PI;
			cosSita2 = (h2 + d2 - r2) / (2 * h * d);
			sita2 = Math.acos(cosSita2);
			// TODO MAYBE A BUG
			sita = Math.min(sita1, sita2);
		} else {
			cosSita1 = (l2 + d2 - r2) / (2 * l * d);
			sita1 = Math.acos(cosSita1);
			cosSita2 = (h2 + d2 - r2) / (2 * h * d);
			sita2 = Math.acos(cosSita2);
			if (r < d) {
				d3 = Math.sqrt(d2 - r2);
				if (d3 >= l) {
					cosSita3 = d3 / d;
					sita3 = Math.acos(cosSita3);
				}
			}
			sita = Math.max(sita1, sita2);
			sita = Math.max(sita, sita3);
		}
		return sita;
	}

	public static double circleIntersectRingMaxSitaOld(Circle circle, Ring ring) {
		Position circleCentre = circle.getCentre();
		double l = ring.getLowRadius();
		double h = ring.getHighRadius();
		double r = circle.getRadius();
		double d = circleCentre.distance(ring.getCircleCentre());
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
			// TODO MAYBE A BUG
			sita = Math.min(sita1, sita2);
		} else {
			cosSita1 = (l2 + d2 - r2) / (2 * l * d);
			sita1 = Math.acos(cosSita1);
			cosSita2 = (h2 + d2 - r2) / (2 * h * d);
			sita2 = Math.acos(cosSita2);
			sita = Math.max(sita1, sita2);

		}
		return sita;
	}

	/*
	 * 
	 */
	public static double circleIntersectRingMinSita(Circle circle, Ring ring) {
		Position circleCentre = circle.getCentre();
		double l = ring.getLowRadius();
		double h = ring.getHighRadius();
		double r = circle.getRadius();
		double d = circleCentre.distance(ring.getCircleCentre());
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
			// TODO MAYBE A BUG
			sita = Math.min(sita1, sita2);
		} else {
			cosSita1 = (l2 + d2 - r2) / (2 * l * d);
			sita1 = Math.acos(cosSita1);
			cosSita2 = (h2 + d2 - r2) / (2 * h * d);
			sita2 = Math.acos(cosSita2);
			sita = Math.min(sita1, sita2);
		}
		return sita;
	}

	/*
	 * circle2 is bigger than circle1
	 */
	public static double circleIntersectCircleSita(Circle circle1,
			Circle circle2) {
		Position circleCentre = circle1.getCentre();
		double h = circle2.getRadius();
		double r = circle1.getRadius();
		double d = circleCentre.distance(circle2.getCentre());
		double h2 = h * h;
		double r2 = r * r;
		double d2 = d * d;
		double cosSita, sita = 0;

		// bug fixed
		if (r >= d + h) {
			sita = 2 * Math.PI;
		} else {
			cosSita = (h2 + d2 - r2) / (2 * h * d);
			sita = Math.acos(cosSita);
		}
		return sita;
	}

	public static Position circleIntersectCirclePosition(Circle circle1,
			Circle circle2, int direction) {
		double sita = ShapeUtil.circleIntersectCircleSita(circle1, circle2);

		if (direction == RingDirection.CLOCKWISE) {
			return new PolarPosition(circle2.getCentre(), circle2.getRadius(),
					circle2.getCentre().bearing(circle1.getCentre()) - sita)
					.toPosition();
		} else {
			return new PolarPosition(circle2.getCentre(), circle2.getRadius(),
					circle2.getCentre().bearing(circle1.getCentre()) + sita)
					.toPosition();
		}
	}

	/*
	 * 
	 */
	public static double circleIntersectRingMinEndRadian(Circle circle,
			Ring ring) {
		double minSita = ShapeUtil.circleIntersectRingMinSita(circle, ring);
		double startRadian = ring.getCircleCentre().bearing(circle.getCentre());
		return Convertor.convertRadian(startRadian + minSita);
	}

	public static RingSector angleCircleInRingMinRingSector(
			AngleCircleInRing angleCircleInRing) {
		double maxSita = ShapeUtil.circleIntersectRingMaxSita(angleCircleInRing
				.getCircle(), angleCircleInRing.getRing());
		double circleCentreRadian = angleCircleInRing.getRing()
				.getCircleCentre().bearing(
						angleCircleInRing.getCircle().getCentre());
		double angleRelativeRadian = Radian.relativeTo(angleCircleInRing
				.getAngle(), circleCentreRadian);

		if (angleRelativeRadian > maxSita) {
			return null;
		} else {
			return new RingSector(angleCircleInRing.getRing(),
					angleCircleInRing.getAngle(), maxSita - angleRelativeRadian);
		}
	}

	/*
	 * TODO do not consider traverse direction, just for anticlock
	 */
	public static LineLineInRing lineCircleInRingMinLineLineInRing(
			LineCircleInRing lineCircleInRing) {
		double maxSita = ShapeUtil.circleIntersectRingMaxSita(lineCircleInRing
				.getCircle(), lineCircleInRing.getRing());
		double circleCentreRadian = lineCircleInRing.getRing()
				.getCircleCentre().bearing(
						lineCircleInRing.getCircle().getCentre());
		Line end = new Line(lineCircleInRing.getRing().getCircleCentre(),
				circleCentreRadian + maxSita);

		return new LineLineInRing(lineCircleInRing.getLine(), end,
				lineCircleInRing.getRing(), lineCircleInRing.getDirection());
	}

	/*
	 * TODO do not consider traverse direction, just for anticlock
	 */
	public static LineLineInRing lineCircleInRingMaxContainedLineLineInRing(
			LineCircleInRing lineCircleInRing) {
		double minSita = ShapeUtil.circleIntersectRingMinSita(lineCircleInRing
				.getCircle(), lineCircleInRing.getRing());
		double circleCentreRadian = lineCircleInRing.getRing()
				.getCircleCentre().bearing(
						lineCircleInRing.getCircle().getCentre());
		Line end = new Line(lineCircleInRing.getRing().getCircleCentre(),
				circleCentreRadian + minSita);

		return new LineLineInRing(lineCircleInRing.getLine(), end,
				lineCircleInRing.getRing(), lineCircleInRing.getDirection());
	}

	public static RingSector circleAngleInRingMinRingSector(
			CircleAngleInRing circleAngleInRing) {
		double minSita = ShapeUtil.circleIntersectRingMinSita(circleAngleInRing
				.getCircle(), circleAngleInRing.getRing());
		double circleCentreRadian = circleAngleInRing.getRing()
				.getCircleCentre().bearing(
						circleAngleInRing.getCircle().getCentre());
		double startRadian = circleCentreRadian + minSita;
		double sita = Radian.relativeTo(circleAngleInRing.getAngle(),
				startRadian);

		return new RingSector(circleAngleInRing.getRing(), startRadian, sita);
	}

	public static RingSector circleCircleInRingMinRingSector(
			CircleCircleInRing circleCircleInRing) {
		double minSita = ShapeUtil.circleIntersectRingMinSita(
				circleCircleInRing.getCircleBegin(), circleCircleInRing
						.getRing());
		double maxSita = ShapeUtil
				.circleIntersectRingMaxSita(circleCircleInRing.getCircleEnd(),
						circleCircleInRing.getRing());

		double circleBeginCentreRadian = circleCircleInRing.getRing()
				.getCircleCentre().bearing(
						circleCircleInRing.getCircleBegin().getCentre());
		double startRadian = circleBeginCentreRadian + minSita;

		double circleEndCentreRadian = circleCircleInRing.getRing()
				.getCircleCentre().bearing(
						circleCircleInRing.getCircleEnd().getCentre());
		double endRadin = circleEndCentreRadian + maxSita;

		double sita = Radian.relativeTo(endRadin, startRadian);
		return new RingSector(circleCircleInRing.getRing(), startRadian, sita);
	}

	public static double circleIntersectRectMBRBeginBound(Circle circle,
			int direction, Rect rect) {
		double d;

		switch (direction) {
		case Direction.LEFT:
			d = Util.minDx(circle.getCentre().getX(),
					circle.getCentre().getY(), rect.getMinY(), rect.getMaxY(),
					circle.getRadius());
			return circle.getCentre().getX() - d;
		case Direction.RIGHT:
			d = Util.minDx(circle.getCentre().getX(),
					circle.getCentre().getY(), rect.getMinY(), rect.getMaxY(),
					circle.getRadius());
			return circle.getCentre().getX() + d;
		case Direction.UP:
			d = Util.minDy(circle.getCentre().getX(),
					circle.getCentre().getY(), rect.getMinX(), rect.getMaxX(),
					circle.getRadius());
			return circle.getCentre().getY() + d;
		case Direction.DOWN:
			d = Util.minDy(circle.getCentre().getX(),
					circle.getCentre().getY(), rect.getMinX(), rect.getMaxX(),
					circle.getRadius());
			return circle.getCentre().getY() - d;
		default:
			return 0;
		}
	}

	/*
	 * old version
	 */
	// public static double circleIntersectRectMBREndBound(Circle circle, int
	// direction, Rect rect) {
	// switch(direction) {
	// case Direction.LEFT:
	// return circle.getCentre().getX()-circle.getRadius();
	// case Direction.RIGHT:
	// return circle.getCentre().getX()+circle.getRadius();
	// case Direction.UP:
	// return circle.getCentre().getY()+circle.getRadius();
	// case Direction.DOWN:
	// return circle.getCentre().getY()-circle.getRadius();
	// default:
	// return 0;
	// }
	// }

	public static double circleIntersectRectMBREndBound(Circle circle,
			int direction, Rect rect) {
		double alpha = 0, beta = 0;
		double d1, d2;
		double startRadian = 0, sita = 0;
		double max1 = 0, max2 = 0;
		double max3 = 0;
		double max4;
		double radius = circle.getRadius();
		switch (direction) {
		case Direction.LEFT:
		case Direction.RIGHT:
			d1 = rect.getMaxY() - circle.getCentre().getY();
			d2 = circle.getCentre().getY() - rect.getMinY();
			max1 = Math.sqrt(radius * radius - d1 * d1);
			max2 = Math.sqrt(radius * radius - d2 * d2);
			alpha = Math.acos(d1 / radius);
			beta = Math.acos(d2 / radius);
			break;
		case Direction.UP:
		case Direction.DOWN:
			d1 = rect.getMaxX() - circle.getCentre().getX();
			d2 = circle.getCentre().getX() - rect.getMinX();
			max1 = Math.sqrt(radius * radius - d1 * d1);
			max2 = Math.sqrt(radius * radius - d2 * d2);
			alpha = Math.acos(d1 / radius);
			beta = Math.acos(d2 / radius);
			break;
		}

		switch (direction) {
		case Direction.LEFT:
			startRadian = Math.PI / 2 + alpha;
			sita = Math.PI - alpha - beta;
			if (Radian.relativeTo(Math.PI, startRadian) <= sita) {
				max3 = circle.getRadius();
			}
			max4 = Math.max(max1, max2);
			max4 = Math.max(max4, max3);
			return circle.getCentre().getX() - max4;
		case Direction.RIGHT:
			startRadian = Math.PI + beta;
			sita = Math.PI - alpha - beta;
			if (Radian.relativeTo(0, startRadian) <= sita) {
				max3 = circle.getRadius();
			}
			max4 = Math.max(max1, max2);
			max4 = Math.max(max4, max3);
			return circle.getCentre().getX() + max4;
		case Direction.UP:
			startRadian = alpha;
			sita = Math.PI - alpha - beta;
			if (Radian.relativeTo(Math.PI / 2, startRadian) <= sita) {
				max3 = circle.getRadius();
			}
			max4 = Math.max(max1, max2);
			max4 = Math.max(max4, max3);
			return circle.getCentre().getY() + max4;
		case Direction.DOWN:
			startRadian = Math.PI + beta;
			sita = Math.PI - alpha - beta;
			if (Radian.relativeTo(Math.PI * 1.5, startRadian) <= sita) {
				max3 = circle.getRadius();
			}
			max4 = Math.max(max1, max2);
			max4 = Math.max(max4, max3);
			return circle.getCentre().getY() - max4;
		}
		return 0;
	}

	public static Arc circleIntersectRect(Circle circle, int direction,
			Rect rect) {
		double alpha, beta;
		double d1, d2;
		double startRadian, sita;
		double radius = circle.getRadius();
		switch (direction) {
		case Direction.LEFT:
		case Direction.RIGHT:
			d1 = rect.getMaxY() - circle.getCentre().getY();
			d2 = circle.getCentre().getY() - rect.getMinY();
			alpha = Math.acos(d1 / radius);
			beta = Math.acos(d2 / radius);
			break;
		case Direction.UP:
		case Direction.DOWN:
			d1 = rect.getMaxX() - circle.getCentre().getX();
			d2 = circle.getCentre().getX() - rect.getMinX();
			alpha = Math.acos(d1 / radius);
			beta = Math.acos(d2 / radius);
			break;
		default:
			return null;
		}

		switch (direction) {
		case Direction.LEFT:
			startRadian = Math.PI / 2 + alpha;
			sita = Math.PI - alpha - beta;
			break;
		case Direction.RIGHT:
			startRadian = Math.PI + beta;
			sita = Math.PI - alpha - beta;
			break;
		case Direction.UP:
			startRadian = alpha;
			sita = Math.PI - alpha - beta;
			break;
		case Direction.DOWN:
			startRadian = Math.PI + beta;
			sita = Math.PI - alpha - beta;
			break;
		default:
			return null;
		}
		return new Arc(circle, startRadian, sita);

	}

	public static Arc circleIntersectRing(Circle circle, Ring ring) {
		double l = ring.getLowRadius();
		double h = ring.getHighRadius();
		double r = circle.getRadius();
		double d = circle.getCentre().distance(ring.getCircleCentre());
		double l2 = l * l;
		double h2 = h * h;
		double r2 = r * r;
		double d2 = d * d;
		double cosSita1, sita1 = 0;
		double cosSita2, sita2 = 0;

		double baseRadian;
		double alpha;
		double sita;

		// bug fixed
		if (r >= d + h) {
			sita1 = 2 * Math.PI;
			sita2 = 2 * Math.PI;
			return new Arc(circle);
		} else if (r >= d + l) {
			cosSita1 = (r2 + d2 - h2) / (2 * r * d);
			sita1 = Math.acos(cosSita1);
			baseRadian = ring.getCircleCentre().bearing(circle.getCentre());
			alpha = Math.PI - sita1 + baseRadian;
			sita = 2 * sita1;
			return new Arc(circle, alpha, sita);

		}

		if (r + d <= h) {
			if (r + l <= d) {
				return new Arc(circle);
			} else {
				cosSita2 = (r2 + d2 - l2) / (2 * r * d);
				sita2 = Math.acos(cosSita2);
				baseRadian = ring.getCircleCentre().bearing(circle.getCentre());
				alpha = baseRadian - (Math.PI - sita2);
				sita = 2 * (Math.PI - sita2);
				return new Arc(circle, alpha, sita);
			}
		} else {
			if (r + l <= d) {
				cosSita1 = (r2 + d2 - h2) / (2 * r * d);
				sita1 = Math.acos(cosSita1);
				baseRadian = ring.getCircleCentre().bearing(circle.getCentre());
				alpha = Math.PI - sita1 + baseRadian;
				sita = 2 * sita1;
				return new Arc(circle, alpha, sita);
			} else {
				cosSita1 = (r2 + d2 - h2) / (2 * r * d);
				sita1 = Math.acos(cosSita1);
				// double bs1 = Radian.getDegree(sita1);
				cosSita2 = (r2 + d2 - l2) / (2 * r * d);
				sita2 = Math.acos(cosSita2);
				// double bs2 = Radian.getDegree(sita2);
				baseRadian = ring.getCircleCentre().bearing(circle.getCentre());
				alpha = Math.PI - sita1 + baseRadian;
				sita = sita1 - sita2;
				if (alpha == Double.NaN) {
					System.out.println("NAA");
				}
				return new Arc(circle, alpha, sita);
			}
		}
	}

	public static Shape concatShape(Shape shape1, Shape shape2, int direction) {
		if (shape1 instanceof Rect) {
			Rect rect1 = (Rect) shape1;
			if (shape2 instanceof BoundCircleInRect) {
				BoundCircleInRect boundCircleInRect2 = (BoundCircleInRect) ((BoundCircleInRect) shape2)
						.clone();
				boundCircleInRect2
						.setBeginBound(rect1.getBeginBound(direction));
				return boundCircleInRect2;
			} else if (shape2 instanceof Rect) {
				return rect1.add((Rect) shape2, direction);
			} else if (shape2 instanceof BoundBoundInRect) {
				return rect1.add(((BoundBoundInRect) shape2).toRect(),
						direction);

			}
		}
		else if(shape1 instanceof LineLineInSectorInRect) {
			LineLineInSectorInRect s1 = (LineLineInSectorInRect)shape1;
			LineLineInSectorInRect s2 = (LineLineInSectorInRect)shape2;
			return new LineLineInSectorInRect(s1.getBegin(), s2.getEnd(),s1.getSectorInRect(), s1.getDirection());
		}
		return null;
	}

	public static double bound(Circle circle, Rect rect, int direction) {
		return ShapeUtil.bound(circle.getCentre(), circle.getRadius(), rect,
				direction);
	}

	public static double bound(Position pos, double radius, Rect rect,
			int direction) {
		double x0 = pos.getX();
		double y0 = pos.getY();
		;
		double dx, dy;
		double result = 0;

		switch (direction) {
		case Direction.LEFT:
			dx = Util.minDx(x0, y0, rect.getMinY(), rect.getMaxY(), radius);
			result = pos.getX() - dx;
			break;
		case Direction.RIGHT:
			dx = Util.minDx(x0, y0, rect.getMinY(), rect.getMaxY(), radius);
			result = pos.getX() + dx;
			break;
		case Direction.DOWN:
			dy = Util.minDy(x0, y0, rect.getMinX(), rect.getMaxX(), radius);
			result = pos.getY() - dy;
			break;
		case Direction.UP:
			dy = Util.minDy(x0, y0, rect.getMinX(), rect.getMaxX(), radius);
			result = pos.getY() + dy;
			break;
		}
		return result;
	}

	public static Rect caculateMBR(Shape shape) {
		Rect rect = new Rect(0, 0, 0, 0);

		if (shape instanceof Rect) {
			return (Rect) shape;
		} else if (shape instanceof CircleCircleInRect) {
			return ((CircleCircleInRect) shape).getMBR();
		} else if (shape instanceof BoundBoundInRect) {
			return ((BoundBoundInRect) shape).getRect();
		} else if (shape instanceof BoundCircleInRect) {
			return ((BoundCircleInRect) shape).getMBR();
		} else if (shape instanceof CircleBoundInRect) {
			return ((CircleBoundInRect) shape).getMBR();
		} else {
			return rect;
		}
	}

	public static double caculateAlpha1(Rect rect, Position centre) {
		return centre.bearing(rect.getLT());
	}

	public static double caculateAlpha2(Rect rect, Position centre) {
		return centre.bearing(rect.getLB());
	}

	public static double caculateAlpha3(Rect rect, Position centre) {
		return centre.bearing(rect.getRB());
	}

	public static double caculateAlpha4(Rect rect, Position centre) {
		return centre.bearing(rect.getRT());
	}

	public static double caculateBeta1(Rect rect, Position centre) {
		double alpha1 = ShapeUtil.caculateAlpha1(rect, centre);
		double alpha2 = ShapeUtil.caculateAlpha2(rect, centre);
		return Radian.relativeTo(alpha2, alpha1);
	}

	public static double caculateBeta2(Rect rect, Position centre) {
		double alpha1 = ShapeUtil.caculateAlpha1(rect, centre);
		double alpha3 = ShapeUtil.caculateAlpha3(rect, centre);
		return Radian.relativeTo(alpha3, alpha1);
	}

	public static double caculateBeta3(Rect rect, Position centre) {
		double alpha1 = ShapeUtil.caculateAlpha1(rect, centre);
		double alpha3 = Math.PI * 1.5;
		return Radian.relativeTo(alpha3, alpha1);
	}

	// public static double[] caculateAlphasSectorInRectsByArea(Position
	// rootPosition, Rect rect, int count) {
	// double as, ab, beta, ar, area, asd;
	// double[] alphas = new double[count];
	//		
	// as = rootPosition.distance(rect.getLT());
	// ab = rect.getHeight();
	// beta = rect.getLT().bearing(rootPosition);
	// ar = rect.area();
	// area = ar/count;
	// double startRadian = rootPosition.bearing(rect.getLT());
	// asd = Radian.relativeTo(rootPosition.bearing(rect.getRT()),
	// startRadian);
	//		
	// double sb,abs,leftArea,leftAndMiddleArea;
	// sb = rootPosition.distance(rect.getLB());
	// abs = Math.acos((ab*ab+sb*sb-as*as)/(2*ab*sb));
	// leftArea = ab*ab*Math.tan(abs)/2;
	// leftAndMiddleArea = ar-leftArea;
	//		
	// double totalArea;
	// double alpha;
	// Object result[] = null;
	// //as, ab, beta, ar, area, asd
	// Double[] parameters = new Double[]{new Double(as),
	// new Double(ab),
	// new Double(beta),
	// new Double(ar),
	// new Double(area),
	// new Double(asd)};
	// try {
	// Resta resta = new Resta();
	// for(int i=0;i<count;i++) {
	// totalArea = (i+1)*area;
	// if(totalArea<=leftArea) {
	// result = resta.f1(1,parameters);
	// System.out.println(result);
	// }
	// else if(totalArea>leftArea&&totalArea<=leftAndMiddleArea) {
	// result = resta.f2(1,parameters);
	// System.out.println(result);
	// }
	// else {
	// result = resta.f3(1,parameters);
	// System.out.println(result);
	// }
	// }
	// } catch (MWException e) {
	// e.printStackTrace();
	// }
	// return alphas;
	// }

	public static Vector caculateAlphasSectorInRectsByMaxAlpha(
			Position rootPosition, Rect rect, double radioRange) {
		Vector alphas = new Vector();
		boolean first = true;
		double startRadian = ShapeUtil.caculateAlpha1(rect, rootPosition);
		double endRadian = ShapeUtil.caculateAlpha4(rect, rootPosition);
		double sita;
		MaxAlphaForIDC maxAlphaForIDC;

		Sector sector;
		SectorInRect sectorInRect;
		while (startRadian < endRadian) {
			sector = new Sector(rootPosition, startRadian, 0);
			sectorInRect = new SectorInRect(sector, rect);

			maxAlphaForIDC = new MaxAlphaForIDC(radioRange, sectorInRect
					.getBeginLineBorderAlpha(), sectorInRect
					.getBeginLineSegment().getLength());
			sita = maxAlphaForIDC.getMaxAlpha();

			// if(sita<0) {
			// System.out.println("****************sita:"+sita);
			// break;
			// }

			if (startRadian + sita > endRadian) {
				sita = Radian.relativeTo(endRadian, startRadian);

				if (first) {
					alphas.add(new Double(sita));
					first = false;
				} else {
					alphas.add(new Double(((Double) alphas.lastElement())
							.doubleValue()
							+ sita));
				}
				break;
			}
			if (first) {
				alphas.add(new Double(sita));
				first = false;
			} else {
				alphas.add(new Double(((Double) alphas.lastElement())
						.doubleValue()
						+ sita));
			}
			startRadian += sita;
			// System.out.println("startRadian:"+startRadian);
			// System.out.println("endRadian:"+endRadian);
		}
		return alphas;
	}

	public static double[] caculateAlphasSectorInRectsByArea(
			Position rootPosition, Rect rect, int count) {
		double[] alphas = new double[count];
		double area;
		DAFunction daFunction;
		UnivariateRealSolverFactory factory = UnivariateRealSolverFactory
				.newInstance();
		UnivariateRealSolver solver = factory.newDefaultSolver();
		try {
			for (int i = 0; i < count; i++) {
				area = rect.area() * (i + 1) / count;
				daFunction = new DAFunction(rootPosition, rect);
				daFunction.setY(area);
				if (i < count - 1)
					alphas[i] = solver
							.solve(daFunction, 0, daFunction.getMax());
				else
					alphas[i] = daFunction.getMax();

				// System.out.println(i+1);
				// System.out.println(area);
				// if(i==0)
				// System.out.println(alphas[i]);
				// else System.out.println(alphas[i]-alphas[i-1]);
				// System.out.println(daFunction.value(alphas[i]));
				// System.out.println(daFunction.toString());
			}
		} catch (ConvergenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FunctionEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alphas;
	}

}
