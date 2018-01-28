package NHSensor.NHSensorSim.shapeTraverse;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Rect;

/*
 * Traverse Rect using rects
 */
public abstract class TraverseRectIterator {
	protected int direction;
	protected Rect rect;
	protected Algorithm alg;
	// It is caused by node failures and void region
	protected boolean cannotTraverse = false;

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}

	public TraverseRectIterator(Algorithm alg, int direction, Rect rect) {
		super();
		this.alg = alg;
		this.direction = direction;
		this.rect = rect;
	}

	public Algorithm getAlg() {
		return alg;
	}

	public boolean isCannotTraverse() {
		return cannotTraverse;
	}

	public void setCannotTraverse(boolean cannotTraverse) {
		this.cannotTraverse = cannotTraverse;
	}

	public abstract Rect getNextRect(NeighborAttachment curNa, Rect curRect);

	public abstract NeighborAttachment getNextNeighborAttachment(
			NeighborAttachment curNa, Rect curRect);

	public abstract boolean isEnd(NeighborAttachment curNa, Rect curRect);

}
