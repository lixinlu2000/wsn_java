package NHSensor.NHSensorSim.events;

import java.awt.Color;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.Position;

public class DrawCircleEvent extends Event {
	private Circle circle;
	private Color color;
	private boolean fill = true;

	public DrawCircleEvent(Algorithm alg, Circle circle, Color color) {
		super(alg);
		this.circle = circle;
		this.color = color;
	}

	public DrawCircleEvent(Algorithm alg, Position pos, double radius, Color color) {
		super(alg);
		this.circle = new Circle(pos, radius);
		this.color = color;
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public String toString() {
		return "DrawNode  " + this.circle.toString();
	}
}
