package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Collections;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.IntersectException;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.Line;
import NHSensor.NHSensorSim.shape.LineLineInSectorInRect;
import NHSensor.NHSensorSim.shape.PolarPosition;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.SectorDirection;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.shape.Shape;

public class RDCTraverseSectorInRectIteratorFar extends
		TraverseSectorInRectIterator {
	final double MINOR_MOVEMENT = 0.00001;

	public RDCTraverseSectorInRectIteratorFar(Algorithm alg,
			SectorInRect sectorInRect) {
		super(alg, sectorInRect, SectorDirection.FAR);
	}

	public LineLineInSectorInRect getInitailShape() throws IntersectException {
		Line initialLine = this.getSectorInRect().caculateInitialLine(
				this.getDirection());
		double radius = this.getAlg().getParam().getRADIO_RANGE();
		return this.getSectorInRect().caculateNextShape(initialLine, radius,
				this.getDirection());
	}

	protected LineLineInSectorInRect getMaxNextShape(NeighborAttachment curNa,
			LineLineInSectorInRect curShape) throws IntersectException {
		Position beginIntersect, endIntersect;
		Position maxPosition;
		Circle circle = new Circle(curNa.getPos(), this.getAlg().getParam()
				.getRADIO_RANGE());

		beginIntersect = this.getSectorInRect().getSector().getBeginFar(circle);
		endIntersect = this.getSectorInRect().getSector().getEndFar(circle);

		RelativeDistanceAlongDirectionComparatorForPositions comparator = new RelativeDistanceAlongDirectionComparatorForPositions(
				curNa.getPos(), this.getSectorInRect().getSector()
						.diagonalRadian());
		if (comparator.compare(beginIntersect, endIntersect) <= 0) {
			maxPosition = beginIntersect;
		} else
			maxPosition = endIntersect;
		Line endLine = new Line(maxPosition, this.getSectorInRect().getSector()
				.diagonalRadian()
				+ Math.PI / 2);
		return new LineLineInSectorInRect(curShape.getEnd(), endLine, this
				.getSectorInRect(), this.getDirection());
	}

	protected NextNaAndShape caculateNextNaAndShape(NeighborAttachment curNa,
			Shape curShape) throws IntersectException {
		// if(curShape instanceof LineLineInSectorInRect) {
		LineLineInSectorInRect curLineLineInSectorInRect = (LineLineInSectorInRect) curShape;
		LineLineInSectorInRect maxNextShape = this.getMaxNextShape(curNa,
				curLineLineInSectorInRect);
		Vector neighborsInMaxNextShape = curNa.getNeighbors(maxNextShape);
		if (neighborsInMaxNextShape.isEmpty()) {
			return new NextNaAndShape(null, maxNextShape);
		} else {
			Collections.sort(neighborsInMaxNextShape,
					new RelativeDistanceAlongDirectionComparator(
							curNa.getPos(), this.getSectorInRect().getSector()
									.diagonalRadian()));
			NeighborAttachment nextNa = (NeighborAttachment) neighborsInMaxNextShape
					.lastElement();
			Position centre = this.sectorInRect.getSector().getCentre();
			double distance = nextNa.getPos().distance(centre)
					+ this.MINOR_MOVEMENT;
			PolarPosition polarPosition = new PolarPosition(centre, distance,
					centre.bearing(nextNa.getPos()));
			Line endLine = new Line(polarPosition.toPosition(), this
					.getSectorInRect().getSector().diagonalRadian()
					+ Math.PI / 2);
			return new NextNaAndShape(nextNa, new LineLineInSectorInRect(
					curLineLineInSectorInRect.getEnd(), endLine, this
							.getSectorInRect(), this.getDirection()));
		}
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
				NextNaAndShape nextNaAndShape;
				try {
					nextNaAndShape = this.caculateNextNaAndShape(curNa,
							curShape);
					return nextNaAndShape.getNextNa();
				} catch (IntersectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}

	public Shape getNextShape(NeighborAttachment curNa, Shape curShape) {
		if (this.isEnd(curNa, curShape))
			return null;
		if (curNa != null && curShape == null) {
			try {
				return this.getInitailShape();
			} catch (IntersectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else if (curNa != null && curShape != null) {
			if (!curShape.contains(curNa.getNode().getPos())) {
				try {
					return this.nextShape(curShape);
				} catch (IntersectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			} else {
				NextNaAndShape nextNaAndShape;
				try {
					nextNaAndShape = this.caculateNextNaAndShape(curNa,
							curShape);
					return nextNaAndShape.getNextShape();
				} catch (IntersectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}

	protected Shape nextShape(Shape curShape) throws IntersectException {
		LineLineInSectorInRect curLineLineInSectorInRect = (LineLineInSectorInRect) curShape;
		double radius = this.getAlg().getParam().getRADIO_RANGE();
		return curLineLineInSectorInRect.getSectorInRect()
				.caculateNextShape(curLineLineInSectorInRect.getEnd(), radius,
						this.getDirection());
	}

	public boolean isEnd(NeighborAttachment curNa, Shape curShape) {
		if (curShape == null)
			return false;
		LineLineInSectorInRect curS = (LineLineInSectorInRect) curShape;
		Line endLine = curS.getEnd();
		Position p1, p2;

		p1 = this.getSectorInRect().getBF();
		p2 = this.getSectorInRect().getEF();
		if (!endLine.higherThan(p1) && !endLine.higherThan(p2))
			return true;
		else
			return false;

	}
}
