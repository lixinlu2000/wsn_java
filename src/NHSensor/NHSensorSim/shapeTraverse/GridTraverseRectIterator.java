package NHSensor.NHSensorSim.shapeTraverse;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.AttachmentInShapeCondition;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Rect;

/*
 * grid traverse a rect region
 */
public class GridTraverseRectIterator extends TraverseRectIterator {

	public GridTraverseRectIterator(Algorithm alg, int direction, Rect rect) {
		super(alg, direction, rect);
	}

	public Rect getInitialDestRect(NeighborAttachment root) {
		double width, height;
		double radioRange = this.alg.getParam().getRADIO_RANGE();

		switch (this.direction) {
		case Direction.LEFT:
			height = rect.getHeight();
			width = Math.sqrt(radioRange * radioRange - height * height) / 2;
			return new Rect(this.rect.getMaxX() - width, this.rect.getMaxX(),
					this.rect.getMinY(), this.rect.getMaxY());
		case Direction.RIGHT:
			height = rect.getHeight();
			width = Math.sqrt(radioRange * radioRange - height * height) / 2;
			return new Rect(this.rect.getMinX(), this.rect.getMinX() + width,
					this.rect.getMinY(), this.rect.getMaxY());
		case Direction.DOWN:
			width = this.rect.getWidth();
			height = Math.sqrt(radioRange * radioRange - width * width) / 2;
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(), this.rect
					.getMaxY()
					- height, this.rect.getMaxY());
		case Direction.UP:
			width = this.rect.getWidth();
			height = Math.sqrt(radioRange * radioRange - width * width) / 2;
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
					return this.nextNode(curNa, nextRect);
				}
			}
		}
		return null;
	}

	public NeighborAttachment nextNode(NeighborAttachment cur, Rect nextRect) {
		AttachmentInShapeCondition nairc = new AttachmentInShapeCondition(
				nextRect);
		Vector neighborsInNextRect = cur.getNeighbors(this.alg.getParam()
				.getRADIO_RANGE(), nairc);

		if (neighborsInNextRect.size() == 0) {
			return null;
		} else
			return (NeighborAttachment) neighborsInNextRect.elementAt(0);
	}

	public Rect getNextRect(NeighborAttachment curNa, Rect curRect) {
		if (this.isEnd(curNa, curRect))
			return null;
		if (curNa != null && curRect == null) {
			return this.getInitialDestRect(curNa);
		} else if (curNa != null && curRect != null) {
			double radioRange = this.alg.getParam().getRADIO_RANGE();
			double width, height;
			switch (this.direction) {
			case Direction.LEFT:
				height = curRect.getHeight();
				width = Math.sqrt(radioRange * radioRange - height * height) / 2;
				return curRect.leftNeighbour(width, this.rect.getMinX());
			case Direction.RIGHT:
				height = curRect.getHeight();
				width = Math.sqrt(radioRange * radioRange - height * height) / 2;
				return curRect.rightNeighbour(width, this.rect.getMaxX());
			case Direction.UP:
				width = curRect.getWidth();
				height = Math.sqrt(radioRange * radioRange - width * width) / 2;
				return curRect.topNeighbour(height, this.rect.getMaxY());
			case Direction.DOWN:
				width = curRect.getWidth();
				height = Math.sqrt(radioRange * radioRange - width * width) / 2;
				return curRect.downNeighbour(height, this.rect.getMinY());
			default:
				return null;
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
