package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;

public class DrawRectEvent extends Event {
	public Rect rect;

	public DrawRectEvent(Algorithm alg, Rect rect) {
		super(alg);
		this.rect = rect;
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}

	public String toString() {
		return "DrawRect  " + this.rect.toString();
	}
}
