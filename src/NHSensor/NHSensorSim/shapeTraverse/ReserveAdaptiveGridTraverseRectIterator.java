package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.util.Util;

public class ReserveAdaptiveGridTraverseRectIterator extends
		TraverseRectIterator {

	public ReserveAdaptiveGridTraverseRectIterator(Algorithm alg,
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
				Rect nextRect = this.getNextRect(curNa, curRect);
				if (nextRect == null)
					return null;
				else {
					return this.chooseNextNodeByArea(curNa, nextRect);
				}
			}
		}
		return null;
	}

	public NeighborAttachment chooseNextNodeByArea(NeighborAttachment cur,
			Rect nextRect) {
		AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
				nextRect);
		Vector neighborsInNextRect = cur.getNeighbors(this.alg.getParam()
				.getRADIO_RANGE(), nairc);

		// bug add this code
		if (nairc.isTrue(cur))
			neighborsInNextRect.add(cur);

		double maxArea = -Double.MIN_VALUE;
		boolean isFound = false;
		NeighborAttachment maxAreaNeighbor = null;
		NeighborAttachment neighbor;
		Rect neighborNextRect;
		double neighborNextRectArea;

		for (int i = 0; i < neighborsInNextRect.size(); i++) {
			neighbor = (NeighborAttachment) neighborsInNextRect.elementAt(i);
			neighborNextRect = this.getNextRect(neighbor, nextRect);

			neighborNextRectArea = neighborNextRect == null ? 0
					: neighborNextRect.area();
			if (neighborNextRectArea > maxArea) {
				maxAreaNeighbor = neighbor;
				maxArea = neighborNextRectArea;
				isFound = true;
			}
		}
		if (isFound) {
			return maxAreaNeighbor;
		} else {
			return null;
		}
	}

	/*
	 * BUG: method getNextRect is not correct!
	 */
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
				double radioRange = this.alg.getParam().getRADIO_RANGE();
				double x0 = curNa.getNode().getPos().getX();
				double y0 = curNa.getNode().getPos().getY();
				;
				double dx, dy;
				double width, height;
				double maxHeight = Math.sqrt(radioRange * radioRange
						- this.rect.getWidth() * this.rect.getWidth());
				double maxWidth = Math.sqrt(radioRange * radioRange
						- this.rect.getHeight() * this.rect.getHeight());
				switch (this.direction) {
				case Direction.LEFT:
					dx = Util.minDx(x0, y0, curRect.getMinY(), curRect
							.getMaxY(), radioRange);
					width = dx - (x0 - curRect.getMinX());
					width = Math.min(width, maxWidth);
					if (width <= 0) {
						return null;
					} else
						return curRect
								.leftNeighbour(width, this.rect.getMinX());
				case Direction.RIGHT:
					dx = Util.minDx(x0, y0, curRect.getMinY(), curRect
							.getMaxY(), radioRange);
					width = dx - (curRect.getMaxX() - x0);
					width = Math.min(width, maxWidth);
					if (width <= 0) {
						return null;
					} else
						return curRect.rightNeighbour(width, this.rect
								.getMaxX());
				case Direction.DOWN:
					dy = Util.minDy(x0, y0, curRect.getMinX(), curRect
							.getMaxX(), radioRange);
					height = dy - (y0 - curRect.getMinY());
					height = Math.min(height, maxHeight);
					if (height <= 0) {
						return null;
					} else
						return curRect.downNeighbour(height, this.rect
								.getMinY());
				case Direction.UP:
					dy = Util.minDy(x0, y0, curRect.getMinX(), curRect
							.getMaxX(), radioRange);
					height = dy - (curRect.getMaxY() - y0);
					height = Math.min(height, maxHeight);
					if (height <= 0) {
						return null;
					} else
						return curRect
								.topNeighbour(height, this.rect.getMaxY());
				default:
					return null;
				}
			}
		}
		return null;
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
