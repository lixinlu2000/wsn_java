package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Collections;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.BoundCircleInRect;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.util.Util;

public class EDCGridTraverseRectIteratorGBAOptimize extends
		TraverseRectIterator implements TraverseShapeOptimizeIterator {

	public EDCGridTraverseRectIteratorGBAOptimize(Algorithm alg, int direction,
			Rect rect) {
		super(alg, direction, rect);
	}

	public Rect getInitialDestRect(NeighborAttachment root) {
		double width, height;
		double radioRange = this.alg.getParam().getRADIO_RANGE();

		switch (this.direction) {
		case Direction.LEFT:
			height = rect.getHeight();
			width = Math.sqrt(radioRange * radioRange - height * height);
			return new Rect(this.rect.getMaxX() - width, this.rect.getMaxX(),
					this.rect.getMinY(), this.rect.getMaxY());
		case Direction.RIGHT:
			height = rect.getHeight();
			width = Math.sqrt(radioRange * radioRange - height * height);
			return new Rect(this.rect.getMinX(), this.rect.getMinX() + width,
					this.rect.getMinY(), this.rect.getMaxY());
		case Direction.DOWN:
			width = this.rect.getWidth();
			height = Math.sqrt(radioRange * radioRange - width * width);
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(), this.rect
					.getMaxY()
					- height, this.rect.getMaxY());
		case Direction.UP:
			width = this.rect.getWidth();
			height = Math.sqrt(radioRange * radioRange - width * width);
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(), this.rect
					.getMinY(), this.rect.getMinY() + height);
		default:
			return null;
		}

	}

	public NeighborAttachment getNextNeighborAttachment(
			NeighborAttachment curNa, Rect curRect) {
		if (this.isEnd(curNa, curRect)) {
			return null;
		}
		if (curNa == null && curRect == null) {
			return null;
		} else if (curNa != null && curRect == null) { // first call
			return null;
		} else if (curNa != null && curRect != null) {
			if (!curRect.contains(curNa.getNode().getPos())) {
				return null;
			} else {
				this.shapeCalNextNa(curNa, curRect);
				return this.nextNeighborUseGBA(curNa, curRect);
			}
		}
		return null;
	}

	public Rect getNextRect(NeighborAttachment curNa, Rect curRect) {
		if (this.isEnd(curNa, curRect))
			return null;
		if (curNa != null && curRect == null) {
			return this.getInitialDestRect(curNa);
		} else if (curNa != null && curRect != null) {
			if (!curRect.contains(curNa.getNode().getPos())) {
				return null;
			} else {
				this.shapeCalNextNa(curNa, curRect);
				return this.nextRectUseGBA(curNa, curRect);
			}
		}
		return null;
	}

	/*
	 * 基于簇头节点前进距离的算法GBA
	 */
	public Rect nextRectUseGBA(NeighborAttachment curNa, Rect curRect) {
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		double x0 = curNa.getNode().getPos().getX();
		double y0 = curNa.getNode().getPos().getY();
		;
		double dx, dy;
		double width, height;
		Vector neigbhorsInNextRect;
		Rect nextRect;

		switch (this.direction) {
		case Direction.LEFT:
			dx = Util.minDx(x0, y0, curRect.getMinY(), curRect.getMaxY(),
					radioRange);
			width = dx - (x0 - curRect.getMinX());
			if (width <= 0) {
				return null;
			} else {
				nextRect = curRect.leftNeighbour(width, this.rect.getMinX());
			}
			break;
		case Direction.RIGHT:
			dx = Util.minDx(x0, y0, curRect.getMinY(), curRect.getMaxY(),
					radioRange);
			width = dx - (curRect.getMaxX() - x0);
			if (width <= 0) {
				return null;
			} else {
				nextRect = curRect.rightNeighbour(width, this.rect.getMaxX());
			}
			break;
		case Direction.DOWN:
			dy = Util.minDy(x0, y0, curRect.getMinX(), curRect.getMaxX(),
					radioRange);
			height = dy - (y0 - curRect.getMinY());
			if (height <= 0) {
				return null;
			} else {
				nextRect = curRect.downNeighbour(height, this.rect.getMinY());
			}
			break;
		case Direction.UP:
			dy = Util.minDy(x0, y0, curRect.getMinX(), curRect.getMaxX(),
					radioRange);
			height = dy - (curRect.getMaxY() - y0);
			if (height <= 0) {
				return null;
			} else {
				nextRect = curRect.topNeighbour(height, this.rect.getMaxY());
			}
			break;
		default:
			return null;
		}
		neigbhorsInNextRect = curNa.getNeighbors(nextRect);

		if (neigbhorsInNextRect.size() == 0) {
			return null;
		}
		Collections.sort(neigbhorsInNextRect,
				new NodeDirectionDistanceComparatorEx(this.direction, curNa
						.getNode().getPos()));

		Vector neigbhorsInCurRect = curNa.getNeighbors(curRect);
		NeighborAttachment na0, na;
		NeighborAttachment nextAttachment = null;
		boolean isNextNa;
		for (int i = neigbhorsInNextRect.size() - 1; i >= 0; i--) {
			na = (NeighborAttachment) neigbhorsInNextRect.elementAt(i);
			isNextNa = true;
			for (int j = 0; j < i; j++) {
				na0 = (NeighborAttachment) neigbhorsInNextRect.elementAt(j);
				if (na.getNode().getPos().distance(na0.getNode().getPos()) > radioRange) {
					isNextNa = false;
					break;
				}
			}
			if (isNextNa) {
				for (int k = 0; k < neigbhorsInCurRect.size(); k++) {
					na0 = (NeighborAttachment) neigbhorsInCurRect.elementAt(k);
					if (na.getNode().getPos().distance(na0.getNode().getPos()) > radioRange) {
						isNextNa = false;
						break;
					}
				}
			}

			if (isNextNa) {
				nextAttachment = na;
				break;
			}
		}
		if (nextAttachment == null) {
			return null;
		} else
			return this.minRectContainNode(nextRect, direction, nextAttachment);
	}

	public Shape shapeCalNextNa(NeighborAttachment curNa, Rect curRect) {		
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		Circle curNaRadioCircle = new Circle(curNa.getPos(), radioRange);
		BoundCircleInRect shape1 = new BoundCircleInRect(
				curRect.getBeginBound(this.getDirection()), curNaRadioCircle,
				this.getRect(), this.direction);
		return shape1;
	}

	/*
	 * 基于簇头节点前进距离的算法GBA
	 */
	public NeighborAttachment nextNeighborUseGBA(NeighborAttachment curNa,
			Rect curRect) {
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		double x0 = curNa.getNode().getPos().getX();
		double y0 = curNa.getNode().getPos().getY();
		;
		double dx, dy;
		double width, height;
		Vector neigbhorsInNextRect;
		Rect nextRect;

		switch (this.direction) {
		case Direction.LEFT:
			dx = Util.minDx(x0, y0, curRect.getMinY(), curRect.getMaxY(),
					radioRange);
			width = dx - (x0 - curRect.getMinX());
			if (width <= 0) {
				return null;
			} else {
				nextRect = curRect.leftNeighbour(width, this.rect.getMinX());
			}
			break;
		case Direction.RIGHT:
			dx = Util.minDx(x0, y0, curRect.getMinY(), curRect.getMaxY(),
					radioRange);
			width = dx - (curRect.getMaxX() - x0);
			if (width <= 0) {
				return null;
			} else {
				nextRect = curRect.rightNeighbour(width, this.rect.getMaxX());
			}
			break;
		case Direction.DOWN:
			dy = Util.minDy(x0, y0, curRect.getMinX(), curRect.getMaxX(),
					radioRange);
			height = dy - (y0 - curRect.getMinY());
			if (height <= 0) {
				return null;
			} else {
				nextRect = curRect.downNeighbour(height, this.rect.getMinY());
			}
			break;
		case Direction.UP:
			dy = Util.minDy(x0, y0, curRect.getMinX(), curRect.getMaxX(),
					radioRange);
			height = dy - (curRect.getMaxY() - y0);
			if (height <= 0) {
				return null;
			} else {
				nextRect = curRect.topNeighbour(height, this.rect.getMaxY());
			}
			break;
		default:
			return null;
		}
		neigbhorsInNextRect = curNa.getNeighbors(nextRect);

		if (neigbhorsInNextRect.size() == 0) {
			return null;
		}
		Collections.sort(neigbhorsInNextRect,
				new NodeDirectionDistanceComparatorEx(this.direction, curNa
						.getNode().getPos()));

		Vector neigbhorsInCurRect = curNa.getNeighbors(curRect);
		NeighborAttachment na0, na;
		NeighborAttachment nextAttachment = null;
		boolean isNextNa;
		for (int i = neigbhorsInNextRect.size() - 1; i >= 0; i--) {
			na = (NeighborAttachment) neigbhorsInNextRect.elementAt(i);
			isNextNa = true;
			for (int j = 0; j < i; j++) {
				na0 = (NeighborAttachment) neigbhorsInNextRect.elementAt(j);
				if (na.getNode().getPos().distance(na0.getNode().getPos()) > radioRange) {
					isNextNa = false;
					break;
				}
			}
			if (isNextNa) {
				for (int k = 0; k < neigbhorsInCurRect.size(); k++) {
					na0 = (NeighborAttachment) neigbhorsInCurRect.elementAt(k);
					if (na.getNode().getPos().distance(na0.getNode().getPos()) > radioRange) {
						isNextNa = false;
						break;
					}
				}
			}

			if (isNextNa) {
				nextAttachment = na;
				break;
			}
		}
		return nextAttachment;
	}

	/*
	 * to get rid of node in the boundary of the rectangle
	 */
	public Rect minRectContainNode(Rect maxNextRect, int direction,
			NeighborAttachment na) {
		switch (direction) {
		case Direction.LEFT:
			return new Rect(na.getNode().getPos().getX() - 0.01, maxNextRect
					.getMaxX(), maxNextRect.getMinY(), maxNextRect.getMaxY());
		case Direction.RIGHT:
			return new Rect(maxNextRect.getMinX(),
					na.getNode().getPos().getX() + 0.01, maxNextRect.getMinY(),
					maxNextRect.getMaxY());
		case Direction.UP:
			return new Rect(maxNextRect.getMinX(), maxNextRect.getMaxX(),
					maxNextRect.getMinY(), na.getNode().getPos().getY() + 0.01);
		case Direction.DOWN:
			return new Rect(maxNextRect.getMinX(), maxNextRect.getMaxX(), na
					.getNode().getPos().getY() - 0.01, maxNextRect.getMaxY());
		default:
			return null;
		}
	}

	public boolean isEnd(NeighborAttachment na, Rect curRect) {
		if (curRect != null) {
			switch (this.direction) {
			case Direction.LEFT:
				return curRect.getMinX() <= this.rect.getMinX();
			case Direction.RIGHT:
				return curRect.getMaxX() >= this.rect.getMaxX();
			case Direction.DOWN:
				return curRect.getMinY() <= this.rect.getMinY();
			case Direction.UP:
				return curRect.getMaxY() >= this.rect.getMaxY();
			default:
				return true;
			}
		} else {
			return false;
		}
	}

	public NeighborAttachment getOptimizeNextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape) {
		NeighborAttachment nextNa = this.getNextNeighborAttachment(curNa,
				(Rect) curShape);
		if (nextNa == null) {
			return curNa;
		} else
			return nextNa;
	}

	public Shape getOptimizedNextShape(NeighborAttachment curNa, Shape curShape) {
		Rect curRect = (Rect) curShape;
		Rect nextRect = this.getNextRect(curNa, curRect);
		if (nextRect != null) {
			return curRect.add(nextRect, direction);
		} else
			return curRect;
	}
}
