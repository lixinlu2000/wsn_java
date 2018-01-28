package NHSensor.NHSensorSim.events;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Shape;

public class DrawShapesEvent extends Event {
	public Vector shapes;

	public DrawShapesEvent(Algorithm alg, Vector shapes) {
		super(alg);
		this.shapes = shapes;
	}

	public DrawShapesEvent(Algorithm alg, Shape[] shapeArray) {
		super(alg);
		this.shapes = new Vector();
		for (int i = 0; i < shapeArray.length; i++) {
			this.shapes.add(shapeArray[i]);
		}
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub
	}

	public String toString() {
		return "DrawShapes  " + this.shapes.toString();
	}

	public Vector getShapes() {
		return shapes;
	}

	public void setShapes(Vector shapes) {
		this.shapes = shapes;
	}
}
