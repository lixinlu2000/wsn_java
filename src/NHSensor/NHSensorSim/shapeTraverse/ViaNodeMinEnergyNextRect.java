package NHSensor.NHSensorSim.shapeTraverse;

import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Rect;

public class ViaNodeMinEnergyNextRect {
	private NeighborAttachment viaNode;
	private Rect nextRect;
	private double energy;

	public ViaNodeMinEnergyNextRect(NeighborAttachment viaNode, Rect nextRect,
			double energy) {
		super();
		this.energy = energy;
		this.nextRect = nextRect;
		this.viaNode = viaNode;
	}

	public ViaNodeMinEnergyNextRect() {
	}

	public Rect getNextRect() {
		return nextRect;
	}

	public void setNextRect(Rect nextRect) {
		this.nextRect = nextRect;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public NeighborAttachment getViaNode() {
		return viaNode;
	}

	public void setViaNode(NeighborAttachment viaNode) {
		this.viaNode = viaNode;
	}
}
