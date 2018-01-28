package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Collections;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.events.EventFactory;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shape.Shape;
import NHSensor.NHSensorSim.util.Util;

/*
 * grid traverse a rect region
 */
public class EDCGridTraverseRectIteratorGAA extends TraverseRectIterator
		implements TraverseShapeOptimizeIterator {

	public EDCGridTraverseRectIteratorGAA(Algorithm alg, int direction,
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
				Rect nextRect = this.getNextRect(curNa, curRect);
				if (nextRect == null)
					return null;
				else {
					// return this.chooseNextNode(curNa, nextRect);
					return this.nextNeighborUseGAA(curNa, curRect);
				}
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
				double radioRange = this.alg.getParam().getRADIO_RANGE();
				double width, height;
				switch (this.direction) {
				case Direction.LEFT:
					height = curRect.getHeight();
					width = Math
							.sqrt(radioRange * radioRange - height * height);
					return curRect.leftNeighbour(width, this.rect.getMinX());
				case Direction.RIGHT:
					height = curRect.getHeight();
					width = Math
							.sqrt(radioRange * radioRange - height * height);
					return curRect.rightNeighbour(width, this.rect.getMaxX());
				case Direction.UP:
					width = curRect.getWidth();
					height = Math.sqrt(radioRange * radioRange - width * width);
					return curRect.topNeighbour(height, this.rect.getMaxY());
				case Direction.DOWN:
					width = curRect.getWidth();
					height = Math.sqrt(radioRange * radioRange - width * width);
					return curRect.downNeighbour(height, this.rect.getMinY());
				default:
					return null;
				}
			} else {
				return this.nextRectUseGAA(curNa, curRect);
			}
		}
		return null;
	}

	/*
	 * 基于网格面积的算法GAA
	 */
	public Rect nextRectUseGAA(NeighborAttachment curNa, Rect curRect) {
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		double x0 = curNa.getNode().getPos().getX();
		double y0 = curNa.getNode().getPos().getY();
		;
		double dx, dy;
		double width, height;
		Vector neigbhorsInNextRect;
		Rect maxNextRect;

		switch (this.direction) {
		case Direction.LEFT:
			dx = Util.minDx(x0, y0, curRect.getMinY(), curRect.getMaxY(),
					radioRange);
			width = dx - (x0 - curRect.getMinX());
			if (width <= 0) {
				return null;
			} else {
				maxNextRect = curRect.leftNeighbour(width, this.rect.getMinX());
			}
			break;
		case Direction.RIGHT:
			dx = Util.minDx(x0, y0, curRect.getMinY(), curRect.getMaxY(),
					radioRange);
			width = dx - (curRect.getMaxX() - x0);
			if (width <= 0) {
				return null;
			} else {
				maxNextRect = curRect
						.rightNeighbour(width, this.rect.getMaxX());
			}
			break;
		case Direction.DOWN:
			dy = Util.minDy(x0, y0, curRect.getMinX(), curRect.getMaxX(),
					radioRange);
			height = dy - (y0 - curRect.getMinY());
			if (height <= 0) {
				return null;
			} else {
				maxNextRect = curRect
						.downNeighbour(height, this.rect.getMinY());
			}
			break;
		case Direction.UP:
			dy = Util.minDy(x0, y0, curRect.getMinX(), curRect.getMaxX(),
					radioRange);
			height = dy - (curRect.getMaxY() - y0);
			if (height <= 0) {
				return null;
			} else {
				maxNextRect = curRect.topNeighbour(height, this.rect.getMaxY());
			}
			break;
		default:
			return null;
		}
		neigbhorsInNextRect = curNa.getNeighbors(maxNextRect);

		if (neigbhorsInNextRect.size() == 0) {
			return maxNextRect;
		}
		Collections.sort(neigbhorsInNextRect,
				new NodeDirectionDistanceComparatorEx(this.direction, curNa
						.getNode().getPos()));

		NeighborAttachment na0, na;
		NeighborAttachment nextAttachment = null;
		Rect naRect = null;
		Rect nextRect = null;
		double maxNaRectArea = 0;
		for (int i = 0; i < neigbhorsInNextRect.size(); i++) {
			na = (NeighborAttachment) neigbhorsInNextRect.elementAt(i);
			// try {
			// this.getAlg().getSimulator().handle(EventFactory.
			// createAttachmentDebugEvent(na, getAlg()));
			// } catch (SensornetBaseException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			naRect = this.cg(curNa, na, maxNextRect);
			if (naRect == null)
				continue;
			if (naRect.area() > maxNaRectArea) {
				nextAttachment = na;
				nextRect = naRect;
				maxNaRectArea = naRect.area();
			} else if (Math.abs(naRect.area() - maxNaRectArea) == 0) {
				Position curNaPos = curNa.getNode().getPos();
				switch (this.direction) {
				case Direction.LEFT:
				case Direction.RIGHT:
					if (na.getNode().getPos().xDistance(curNaPos) > nextAttachment
							.getNode().getPos().xDistance(curNaPos)) {
						nextAttachment = na;
						nextRect = naRect;
						maxNaRectArea = naRect.area();
					}
				case Direction.UP:
				case Direction.DOWN:
					if (na.getNode().getPos().yDistance(curNaPos) > nextAttachment
							.getNode().getPos().yDistance(curNaPos)) {
						nextAttachment = na;
						nextRect = naRect;
						maxNaRectArea = naRect.area();
					}
				}
			}
		}
		return nextRect;
	}

	/*
	 * 计算 na作为簇头节点时，对应的面积最大且满足网格约束条件的下一个网格
	 */
	public Rect cg(NeighborAttachment curNa, NeighborAttachment na,
			Rect maxNextRect) {
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		Vector neigbhorsInNextRect;
		neigbhorsInNextRect = curNa.getNeighbors(maxNextRect);

		Collections.sort(neigbhorsInNextRect,
				new NodeDirectionDistanceComparatorEx(this.direction, curNa
						.getNode().getPos()));
		NeighborAttachment na0;

		int naIndex = neigbhorsInNextRect.indexOf(na);
		int i;
		for (i = 0; i < neigbhorsInNextRect.size(); i++) {
			if (i == naIndex)
				continue;
			na0 = (NeighborAttachment) neigbhorsInNextRect.elementAt(i);
			if (na.getNode().getPos().distance(na0.getNode().getPos()) > radioRange) {
				break;
			}
		}

		if (i >= naIndex) {
			return this.minRectContainNode(maxNextRect, this.direction,
					(NeighborAttachment) neigbhorsInNextRect.elementAt(i - 1));
		} else
			return null;

	}

	/*
	 * 基于网格面积的算法GAA
	 */
	public NeighborAttachment nextNeighborUseGAA(NeighborAttachment curNa,
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

		NeighborAttachment na0, na;
		NeighborAttachment nextAttachment = null;
		Rect naRect = null;
		double maxNaRectArea = 0;
		for (int i = 0; i < neigbhorsInNextRect.size(); i++) {
			na = (NeighborAttachment) neigbhorsInNextRect.elementAt(i);
			naRect = this.cg(curNa, na, nextRect);
			if (naRect == null)
				continue;
			if (naRect.area() > maxNaRectArea) {
				nextAttachment = na;
				maxNaRectArea = naRect.area();
			} else if (Math.abs(naRect.area() - maxNaRectArea) == 0) {
				Position curNaPos = curNa.getNode().getPos();
				switch (this.direction) {
				case Direction.LEFT:
				case Direction.RIGHT:
					if (na.getNode().getPos().xDistance(curNaPos) > nextAttachment
							.getNode().getPos().xDistance(curNaPos)) {
						nextAttachment = na;
						maxNaRectArea = naRect.area();
					}
				case Direction.UP:
				case Direction.DOWN:
					if (na.getNode().getPos().yDistance(curNaPos) > nextAttachment
							.getNode().getPos().yDistance(curNaPos)) {
						nextAttachment = na;
						maxNaRectArea = naRect.area();
					}
				}
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
			return new Rect(na.getNode().getPos().getX() - 0.000001,
					maxNextRect.getMaxX(), maxNextRect.getMinY(), maxNextRect
							.getMaxY());
		case Direction.RIGHT:
			return new Rect(maxNextRect.getMinX(),
					na.getNode().getPos().getX() + 0.000001, maxNextRect
							.getMinY(), maxNextRect.getMaxY());
		case Direction.UP:
			return new Rect(maxNextRect.getMinX(), maxNextRect.getMaxX(),
					maxNextRect.getMinY(),
					na.getNode().getPos().getY() + 0.000001);
		case Direction.DOWN:
			return new Rect(maxNextRect.getMinX(), maxNextRect.getMaxX(), na
					.getNode().getPos().getY() - 0.000001, maxNextRect
					.getMaxY());
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
		// TODO Auto-generated method stub
		return null;
	}

	public Shape getOptimizedNextShape(NeighborAttachment curNa, Shape curShape) {
		// TODO Auto-generated method stub
		return null;
	}

}
