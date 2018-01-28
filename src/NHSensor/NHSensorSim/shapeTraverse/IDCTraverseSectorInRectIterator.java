package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Collections;
import java.util.Vector;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.IntersectException;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.Line;
import NHSensor.NHSensorSim.shape.LineCircleInSectorInRect;
import NHSensor.NHSensorSim.shape.LineLineInSectorInRect;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.SectorDirection;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.shape.Shape;

public class IDCTraverseSectorInRectIterator extends
		TraverseSectorInRectIterator {

	public IDCTraverseSectorInRectIterator(Algorithm alg,
			SectorInRect sectorInRect, int direction) {
		super(alg, sectorInRect, direction);
	}

	public LineLineInSectorInRect getInitailShape() throws IntersectException {
		Line initialLine = this.getSectorInRect().caculateInitialLine(
				this.getDirection());
		double radius = this.getAlg().getParam().getRADIO_RANGE();
		return this.getSectorInRect().caculateNextShape(initialLine, radius,
				this.getDirection());
	}

	public NeighborAttachment getNextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape) {
		if (this.isEnd(curNa, curShape)) {
			return null;
		}

		if (curNa == null) {
			return null;
		} else {
			if (curShape != null) {
				if (curShape.contains(curNa.getNode().getPos())) {
					NeighborAttachment next = this.getNextNaByMPI(curNa);
					if (next == curNa)
						return null;
					else
						return next;
				} else
					return null;
			} else {
				if (!this.sectorInRect.contains(curNa.getPos())) {
					return null;
				}
				NeighborAttachment next = this.getNextNaByMPI(curNa);
				if (next == curNa)
					return null;
				else
					return next;
			}
		}
	}

	// get next neighbor by Most Progress on Itinerary
	public NeighborAttachment getNextNaByMPIOld(NeighborAttachment cur) {
		Line beginLine = this.sectorInRect.getSector().diagonalLine()
				.perpendicularLine(cur.getPos());
		Line endLine;
		if (this.direction == SectorDirection.FAR) {
			endLine = this.sectorInRect.caculateFarLine();
		} else
			endLine = this.sectorInRect.caculateNearLine();
		LineLineInSectorInRect shape = new LineLineInSectorInRect(beginLine,
				endLine, this.sectorInRect, this.direction);
		Vector neighbors = cur.getNeighbors(shape);
		NeighborAttachment nextNa = cur;

		if (!neighbors.isEmpty()) {
			Collections
					.sort(neighbors,
							new RelativeDistanceAlongDirectionComparator(cur
									.getPos(), this.getSectorInRect()
									.getSector().diagonalRadian()));
			nextNa = (NeighborAttachment) neighbors.lastElement();
		}
		return nextNa;
	}

	// get next neighbor by Most Progress on Itinerary
	public NeighborAttachment getNextNaByMPI(NeighborAttachment cur) {
		Line beginLine = this.sectorInRect.getSector().diagonalLine()
				.perpendicularLine(cur.getPos());
		Line endLine;
		Circle circle = new Circle(cur.getPos(), this.alg.getParam()
				.getRADIO_RANGE());
		LineCircleInSectorInRect lineCircleInSectorInRect = new LineCircleInSectorInRect(
				beginLine, circle, this.sectorInRect, this.direction);
		Vector neighbors = cur.getNeighbors(lineCircleInSectorInRect);
		NeighborAttachment nextNa = cur;

		if (!neighbors.isEmpty()) {
			Collections
					.sort(neighbors,
							new RelativeDistanceAlongDirectionComparator(cur
									.getPos(), this.getSectorInRect()
									.getSector().diagonalRadian()));
			nextNa = (NeighborAttachment) neighbors.lastElement();
		}
		return nextNa;
	}

	public Shape getNextShape(NeighborAttachment curNa, Shape curShape) {
		if (this.isEnd(curNa, curShape))
			return null;

		if (curNa == null) {
			return null;
		} else {
			if (curShape != null) {
				if (curShape.contains(curNa.getNode().getPos())) {
					NeighborAttachment next = this.getNextNaByMPI(curNa);
					if (next == curNa) {
						return this.nextShape(curShape);
						// double radius =
						// this.getAlg().getParam().getRADIO_RANGE();
						// Line line = this.sectorInRect.getSector().getLine(new
						// Circle(curNa.getPos(),radius), this.direction);
						// return this.getSectorInRect().caculateNextShape(line,
						// radius, this.getDirection());
					} else
						return null;
				} else {
					return this.nextShape(curShape);
				}
			} else {
				if (!this.sectorInRect.contains(curNa.getPos())) {
					try {
						return this.getInitailShape();
					} catch (IntersectException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				}
				NeighborAttachment next = this.getNextNaByMPI(curNa);
				if (next == curNa) {
					double radius = this.getAlg().getParam().getRADIO_RANGE();
					Line line;
					try {
						line = this.sectorInRect.getSector().getLine(
								new Circle(curNa.getPos(), radius),
								this.direction);
					} catch (IntersectException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						line = new Line(curNa.getPos(), this.sectorInRect
								.getSector().diagonalRadian()
								+ Math.PI / 2);
					}
					return this.getSectorInRect().caculateNextShape(line,
							radius, this.getDirection());
				} else
					return null;
			}
		}
	}

	protected Shape nextShape(Shape curShape) {
		LineLineInSectorInRect curLineLineInSectorInRect = (LineLineInSectorInRect) curShape;
		double radius = this.getAlg().getParam().getRADIO_RANGE();
		return curLineLineInSectorInRect.getSectorInRect()
				.caculateNextShape(curLineLineInSectorInRect.getEnd(), radius,
						this.getDirection());
	}

	protected Line nextLine(Line cur, int direction, double moveDistance) {
		double k = cur.getK();
		double delta = moveDistance * Math.sqrt(1 + k * k);
		double b;

		if (direction == SectorDirection.FAR) {
			b = cur.getB() - delta;
		} else {
			b = cur.getB() + delta;
		}
		return new Line(new Position(0, b), cur.getAlpha());
	}

	public boolean isEnd(NeighborAttachment curNa, Shape curShape) {
		Line endLine;
		Position p1, p2, p3, p4, p5, p6;

		if (curShape == null) {
			double radius = this.getAlg().getParam().getRADIO_RANGE();
			try {
				endLine = this.sectorInRect.getSector().getLine(
						new Circle(curNa.getPos(), radius), this.direction);
			} catch (IntersectException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				endLine = new Line(curNa.getPos(), this.sectorInRect
						.getSector().diagonalRadian()
						+ Math.PI / 2);
			}
		} else {
			LineLineInSectorInRect curS = (LineLineInSectorInRect) curShape;
			endLine = curS.getEnd();
		}

		p1 = this.getSectorInRect().getBF();
		p2 = this.getSectorInRect().getEF();
		p3 = this.getSectorInRect().getBN();
		p4 = this.getSectorInRect().getEN();
		p5 = endLine.intersect(this.getSectorInRect().getSector()
				.getBeginLine());
		p6 = endLine.intersect(this.getSectorInRect().getSector().getEndLine());

		if (this.direction == SectorDirection.FAR) {
			if (p5.distance(p3) >= p1.distance(p3)
					&& p6.distance(p4) >= p2.distance(p4))
				return true;
			else
				return false;
		} else {
			if (p5.distance(p1) >= p3.distance(p1)
					&& p6.distance(p2) >= p4.distance(p2))
				return true;
			else
				return false;
		}
	}

	public boolean isEndOld(NeighborAttachment curNa, Shape curShape) {
		Line endLine;
		Position p1, p2;

		if (curShape == null) {
			double radius = this.getAlg().getParam().getRADIO_RANGE();
			try {
				endLine = this.sectorInRect.getSector().getLine(
						new Circle(curNa.getPos(), radius), this.direction);
			} catch (IntersectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				endLine = new Line(curNa.getPos(), this.sectorInRect
						.getSector().diagonalRadian()
						+ Math.PI / 2);
			}
		} else {
			LineLineInSectorInRect curS = (LineLineInSectorInRect) curShape;
			endLine = curS.getEnd();
		}

		if (this.direction == SectorDirection.FAR) {
			p1 = this.getSectorInRect().getBF();
			p2 = this.getSectorInRect().getEF();
			if (!endLine.higherThan(p1) && !endLine.higherThan(p2)
					&& endLine.getK() <= 0)
				return true;
			if (!endLine.lowerThan(p1) && !endLine.lowerThan(p2)
					&& endLine.getK() > 0)
				return true;
		} else {
			p1 = this.getSectorInRect().getBN();
			p2 = this.getSectorInRect().getEN();
			if (!endLine.lowerThan(p1) && !endLine.lowerThan(p2)
					&& endLine.getK() <= 0)
				return true;
			if (!endLine.higherThan(p1) && !endLine.higherThan(p2)
					&& endLine.getK() >= 0)
				return true;
		}
		return false;
	}

}
