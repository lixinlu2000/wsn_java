package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Collections;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.util.Util;

/*
 * grid traverse a rect region
 */
public class EDCLinkAwareGridTraverseRectIteratorGBA extends
		TraverseRectIterator {

	public EDCLinkAwareGridTraverseRectIteratorGBA(Algorithm alg,
			int direction, Rect rect) {
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
				return this.nextRectUseGBA(curNa, curRect);
			}
		}
		return null;
	}

	protected Rect caculateMaxNextRect(NeighborAttachment curNa, Rect curRect) {
		double radioRange = this.alg.getParam().getRADIO_RANGE();
		double x0 = curNa.getNode().getPos().getX();
		double y0 = curNa.getNode().getPos().getY();
		;
		double dx, dy;
		double width, height;
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
		return nextRect;
	}

	/*
	 * 基于簇头节点前进距离的算法GBA
	 */
	public Rect nextRectUseGBA(NeighborAttachment curNa, Rect curRect) {
		Rect maxNextRect = this.caculateMaxNextRect(curNa, curRect);
		if (maxNextRect == null)
			return null;
		Vector neigbhorsInMaxNextRect = curNa.getNeighbors(maxNextRect);
		NeighborAttachment nextNa = this.nextNeighborAttachment(curNa,
				neigbhorsInMaxNextRect);
		if (nextNa == null)
			return maxNextRect;
		else
			return this.minRectContainNode(maxNextRect, direction, nextNa);
	}

	/*
	 * 基于簇头节点前进距离的算法GBA
	 */
	public NeighborAttachment nextNeighborUseGBA(NeighborAttachment curNa,
			Rect curRect) {
		Rect maxNextRect = this.caculateMaxNextRect(curNa, curRect);
		if (maxNextRect == null)
			return null;
		Vector neigbhorsInMaxNextRect = curNa.getNeighbors(maxNextRect);
		return this.nextNeighborAttachment(curNa, neigbhorsInMaxNextRect);
	}

	public NeighborAttachment nextNeighborAttachment(NeighborAttachment curNa,
			Vector neigbhorsInMaxNextRect) {

		if (neigbhorsInMaxNextRect.size() == 0) {
			return null;
		}

		Collections.sort(neigbhorsInMaxNextRect,
				new NodeDirectionDistanceComparatorEx(this.direction, curNa
						.getNode().getPos()));

		Vector neigbhorsSatisfyConstraint = this.nasSatisfyConstraint(curNa,
				neigbhorsInMaxNextRect);
		Collections.sort(neigbhorsSatisfyConstraint,
				new NodeDirectionDistanceComparatorEx(this.direction, curNa
						.getNode().getPos()));

		NeighborAttachment na;
		NeighborAttachment nextAttachment = null;
		double minCost = Double.MAX_VALUE;
		double curCost;
		int index;

		for (int i = 0; i < neigbhorsSatisfyConstraint.size(); i++) {
			na = (NeighborAttachment) neigbhorsSatisfyConstraint.elementAt(i);
			index = neigbhorsInMaxNextRect.indexOf(na);
			if (index == -1)
				continue;

			curCost = this.cost(curNa, neigbhorsInMaxNextRect, index);

			if (curCost < minCost) {
				nextAttachment = na;
				minCost = curCost;
			}
		}
		return nextAttachment;
	}

	protected Vector nasSatisfyConstraint(NeighborAttachment curNa,
			Vector nasSortedInNextMaxRect) {
		Vector nas = new Vector();

		if (nasSortedInNextMaxRect.size() == 0) {
			return nas;
		}

		double radioRange = this.alg.getParam().getRADIO_RANGE();
		NeighborAttachment na0, na;
		for (int i = 0; i < nasSortedInNextMaxRect.size(); i++) {
			na = (NeighborAttachment) nasSortedInNextMaxRect.elementAt(i);

			int j;
			for (j = 0; j < i; j++) {
				na0 = (NeighborAttachment) nasSortedInNextMaxRect.elementAt(j);
				if (na.getNode().getPos().distance(na0.getNode().getPos()) > radioRange) {
					break;
				}
			}
			if (j == i)
				nas.add(na);
		}
		return nas;
	}

	protected double cost(NeighborAttachment curNa,
			Vector nasSortedInNextMaxRect, int index) {
		NeighborAttachment nextNa = (NeighborAttachment) nasSortedInNextMaxRect
				.elementAt(index);

		double totalCost = 0;
		NeighborAttachment na;
		double broadcastQueryAndPatialAnswerTimes = curNa.getSendTimes(nextNa);
		;
		double times = 0;
		double nodeNum = index + 1;

		// collect data node's data
		for (int i = 0; i < index; i++) {
			na = (NeighborAttachment) nasSortedInNextMaxRect.elementAt(i);
			totalCost += this.alg.getParam().getANSWER_SIZE()
					* na.getSendTimes(nextNa);
			times = curNa.getSendTimes(na);
			broadcastQueryAndPatialAnswerTimes = Math.max(times,
					broadcastQueryAndPatialAnswerTimes);

		}

		// send query and patial answer
		int queryAndPatialAnswerSize = this.alg.getParam().getANSWER_SIZE()
				+ this.alg.getParam().getQUERY_MESSAGE_SIZE();
		totalCost += queryAndPatialAnswerSize
				* broadcastQueryAndPatialAnswerTimes;
		// int queryAndPatialAnswerSize =
		// this.alg.getParam().getANSWER_SIZE()+this
		// .alg.getParam().getQUERY_MESSAGE_SIZE();
		// totalCost += queryAndPatialAnswerSize*curNa.getSendTimes(nextNa);

		totalCost /= nodeNum;
		return totalCost;
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

}
