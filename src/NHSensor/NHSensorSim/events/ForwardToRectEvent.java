package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.shape.Rect;

public abstract class ForwardToRectEvent extends ForwardToShapeEvent {

	public ForwardToRectEvent(Message message, Algorithm alg) {
		super(message, alg);
	}

	public Rect getRect() {
		return (Rect) this.getShape();
	}

	public void setRect(Rect rect) {
		this.shape = rect;
	}
}
