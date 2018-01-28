package NHSensor.NHSensorSim.events;

import java.awt.Color;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class DrawNodeEvent extends Event {
	private Node node;
	private Color color;
	private double addedSize = 0;
	private boolean fill = true;

	public DrawNodeEvent(Algorithm alg, Node node, Color color) {
		super(alg);
		this.node = node;
		this.color = color;
	}

	public DrawNodeEvent(Algorithm alg, Node node, double addedSize, Color color) {
		super(alg);
		this.node = node;
		this.color = color;
		this.addedSize = addedSize;
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub
	}

	public double getAddedSize() {
		return addedSize;
	}

	public void setAddedSize(double addedSize) {
		this.addedSize = addedSize;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
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

	public String toString() {
		return "DrawNode  " + this.node.toString();
	}
}
