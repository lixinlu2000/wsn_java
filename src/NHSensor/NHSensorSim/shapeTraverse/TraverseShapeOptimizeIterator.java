package NHSensor.NHSensorSim.shapeTraverse;

import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Shape;

public interface TraverseShapeOptimizeIterator {
	public Shape getOptimizedNextShape(NeighborAttachment curNa, Shape curShape);

	public NeighborAttachment getOptimizeNextNeighborAttachment(
			NeighborAttachment curNa, Shape curShape);

}
