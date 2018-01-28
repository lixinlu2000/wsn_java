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

public class RDCTraverseSectorInRectIteratorNear extends
		TraverseSectorInRectIterator implements TraverseShapeOptimizeIterator {

	public RDCTraverseSectorInRectIteratorNear(Algorithm alg,
			SectorInRect sectorInRect) {
		super(alg, sectorInRect, SectorDirection.NEAR);
	}

	public Shape getInitailShape() {
		double radius = this.getAlg().getParam().getRADIO_RANGE();
		
		if(!this.getSectorInRect().isSameBorder()) {
			return this.getSectorInRect().caculateInitialShapeWithBorder(radius, this.getDirection());
		}
		else {
			Line initialLine = this.getSectorInRect().caculateInitialLine(
					this.getDirection());
			return this.getSectorInRect().caculateNextShape(initialLine, radius,
					this.getDirection());
		}
	}

	protected LineLineInSectorInRect getMaxNextShape(NeighborAttachment curNa,
			LineLineInSectorInRect curShape)  throws IntersectException{
		Position beginIntersect, endIntersect;
		Position maxPosition;
		Circle circle = new Circle(curNa.getPos(), this.getAlg().getParam()
				.getRADIO_RANGE());

		beginIntersect = this.getSectorInRect().getSector()
				.getBeginNear(circle);
		endIntersect = this.getSectorInRect().getSector().getEndNear(circle);
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
		
//		Position p1, p2;
//
//		p1 = this.getSectorInRect().getBN();
//		p2 = this.getSectorInRect().getEN();
//		if (!endLine.lowerThan(p1) && !endLine.lowerThan(p2)) {
//			endLine = this.sectorInRect.caculateNearLine();
//		}
		return new LineLineInSectorInRect(curShape.getEnd(), endLine, this
				.getSectorInRect(), this.getDirection());
	}

	protected NextNaAndShape caculateNextNaAndShape(NeighborAttachment curNa,
			Shape curShape, boolean isOptimize)  throws IntersectException{
		// if(curShape instanceof LineLineInSectorInRect) {
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		Vector nasInCurShape = curNa.getNeighbors(curShape);
		Vector nas;
		LineLineInSectorInRect curLineLineInSectorInRect = (LineLineInSectorInRect) curShape;
		LineLineInSectorInRect maxNextShape = this.getMaxNextShape(curNa,
				curLineLineInSectorInRect);
		Vector neighborsInMaxNextShape = curNa.getNeighbors(maxNextShape);

		if(!isOptimize&&neighborsInMaxNextShape.isEmpty()) {
			return new NextNaAndShape(null, maxNextShape);
		}
		
		if(isOptimize) {
			neighborsInMaxNextShape.addAll(nasInCurShape);
			nas = neighborsInMaxNextShape;
		}
		else nas = neighborsInMaxNextShape;
		if(!nas.contains(curNa))nas.add(curNa);
		
		Collections.sort(nas,
				new RelativeDistanceAlongDirectionComparator(
						curNa.getPos(), this.getSectorInRect().getSector()
								.diagonalRadian()));
		NeighborAttachment nextNa = null, candidateNa, prevNa;
		boolean isNextNa;
		for (int i = 0; i < nas.size(); i++) {
			candidateNa = (NeighborAttachment) nas.elementAt(i);
			isNextNa = true;
			for (int j = 0; j < i; j++) {
				prevNa = (NeighborAttachment) nas.elementAt(j);
				if (prevNa.getPos().distance(candidateNa.getPos()) > radioRange) {
					isNextNa = false;
					break;
				}
			}
			//
			if (isNextNa) {
				nextNa = candidateNa;
			} else {
				break;
			}
		}
		//

		Position sectorCentre = this.sectorInRect.getSector().getCentre();
		PolarPosition pp = new PolarPosition(sectorCentre, 
				nextNa.getPos().distance(sectorCentre)-0.001, sectorCentre.bearing(nextNa.getPos()));
		
		Line endLine = new Line(pp.toPosition(), this.getSectorInRect()
				.getSector().diagonalRadian()
				+ Math.PI / 2);
		return new NextNaAndShape(nextNa, new LineLineInSectorInRect(
				curLineLineInSectorInRect.getBegin(), endLine, this
						.getSectorInRect(), this.getDirection()));
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
							curShape,false);
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
			return this.getInitailShape();
		} else if (curNa != null && curShape != null) {
			if (!curShape.contains(curNa.getNode().getPos())) {
				return this.nextShape(curShape);
			} else {
				NextNaAndShape nextNaAndShape;
				try {
					nextNaAndShape = this.caculateNextNaAndShape(
							curNa, curShape, false);
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

	protected Shape nextShape(Shape curShape) {
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

		p1 = this.getSectorInRect().getBN();
		p2 = this.getSectorInRect().getEN();
		if (!endLine.lowerThan(p1) && !endLine.lowerThan(p2))
			return true;
		else
			return false;
	}
	

	public NeighborAttachment getOptimizeNextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape) {
		NeighborAttachment na = null;
		try {
			na = this.caculateNextNaAndShape(curNa, curShape,
					true).getNextNa();
		} catch (IntersectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return na;
	}

	public Shape getOptimizedNextShape(NeighborAttachment curNa, Shape curShape) {
		Shape nextShape = null;
		try {
			nextShape = this.caculateNextNaAndShape(curNa, curShape, true).getNextShape();
		} catch (IntersectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextShape;
	}
}
