package NHSensor.NHSensorSim.shapeTraverse;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.shape.Shape;

public abstract class TraverseRingIterator {
	protected Ring ring;
	protected Algorithm alg;
	// It is caused by node failures and void region
	protected boolean cannotTraverse = false;
	protected NeighborAttachment root;

	public TraverseRingIterator(Algorithm alg, NeighborAttachment root,
			Ring ring) {
		super();
		this.alg = alg;
		this.root = root;
		this.ring = ring;
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

	public abstract Shape getNextShape(NeighborAttachment curNa, Shape curShape);

	public abstract NeighborAttachment getNextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape);

	public abstract boolean isEnd(NeighborAttachment curNa, Shape curShape);

	public NextNaAndShape getNextNaAndShape(NeighborAttachment curNa,
			Shape curShape) {
		NeighborAttachment nextNa = this.getNextNeighborAttachment(curNa,
				curShape);
		Shape nextShape = this.getNextShape(curNa, curShape);
		return new NextNaAndShape(nextNa, nextShape);
	}

	public Ring getRing() {
		return ring;
	}

	public void setRing(Ring ring) {
		this.ring = ring;
	}

	public NeighborAttachment getRoot() {
		return root;
	}
}
