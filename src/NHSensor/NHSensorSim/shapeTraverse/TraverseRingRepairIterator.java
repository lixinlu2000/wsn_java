package NHSensor.NHSensorSim.shapeTraverse;

import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Shape;

public interface TraverseRingRepairIterator {

	// caculate repaired shape
	public abstract Shape getRepairedShape(NeighborAttachment prevNa,
			NeighborAttachment curNa, Shape curShape);

	// caculate repaired NeighborAttachment
	public abstract NeighborAttachment getRepairedNeighborAttachment(
			NeighborAttachment prevNa, NeighborAttachment curNa, Shape curShape);

	// public abstract int getRepairedNeighborAttachmentNum(NeighborAttachment
	// prevNa, NeighborAttachment curNa, Shape curShape);

}
