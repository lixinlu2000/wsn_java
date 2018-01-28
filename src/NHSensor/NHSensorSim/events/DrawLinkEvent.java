package NHSensor.NHSensorSim.events;

import java.awt.Color;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class DrawLinkEvent extends Event {
	private Node node1;
	private Node node2;
	private Color color;

	public DrawLinkEvent(Algorithm alg, Node node1, Node node2, Color color) {
		super(alg);
		this.node1 = node1;
		this.node2 = node2;
		this.color = color;
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub
	}

	public Node getNode1() {
		return node1;
	}

	public void setNode1(Node node1) {
		this.node1 = node1;
	}

	public Node getNode2() {
		return node2;
	}

	public void setNode2(Node node2) {
		this.node2 = node2;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String toString() {
		return "DrawLink  " + this.node1.toString() + " "
				+ this.node2.toString();
	}
}
