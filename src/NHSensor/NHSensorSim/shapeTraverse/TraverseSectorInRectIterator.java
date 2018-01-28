package NHSensor.NHSensorSim.shapeTraverse;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.SectorInRect;
import NHSensor.NHSensorSim.shape.Shape;

public abstract class TraverseSectorInRectIterator {
	protected SectorInRect sectorInRect;
	protected Algorithm alg;
	// It is caused by node failures and void region
	protected boolean cannotTraverse = false;
	// away or near
	protected int direction;

	public TraverseSectorInRectIterator(Algorithm alg,
			SectorInRect sectorInRect, int direction) {
		super();
		this.alg = alg;
		this.sectorInRect = sectorInRect;
		this.direction = direction;
	}

	public SectorInRect getSectorInRect() {
		return sectorInRect;
	}

	public void setSectorInRect(SectorInRect sectorInRect) {
		this.sectorInRect = sectorInRect;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Algorithm getAlg() {
		return alg;
	}

	public void setAlg(Algorithm alg) {
		this.alg = alg;
	}

	public abstract Shape getNextShape(NeighborAttachment curNa, Shape curShape);

	public abstract NeighborAttachment getNextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape);

	public abstract boolean isEnd(NeighborAttachment curNa, Shape curShape);

}
