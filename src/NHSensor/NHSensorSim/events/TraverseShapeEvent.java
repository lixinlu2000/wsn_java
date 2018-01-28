package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.shape.Shape;

public abstract class TraverseShapeEvent extends TransferEvent {
	protected Shape shape;

	public TraverseShapeEvent(Message message, Algorithm alg) {
		super(message, alg);
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}
}
