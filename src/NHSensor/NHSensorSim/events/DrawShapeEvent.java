package NHSensor.NHSensorSim.events;

import java.awt.Color;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Shape;

public class DrawShapeEvent extends Event {
	private Shape shape;
	private Color color = null;

	public DrawShapeEvent(Algorithm alg, Shape shape) {
		super(alg);
		this.shape = shape;
	}

	public DrawShapeEvent(Algorithm alg, Shape shape, Color color) {
		super(alg);
		this.shape = shape;
		this.color = color;
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String toString() {
		return "DrawShape  " + this.shape.toString();
	}
}
