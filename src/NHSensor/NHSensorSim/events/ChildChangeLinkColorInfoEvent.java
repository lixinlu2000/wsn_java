package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Node;

public class ChildChangeLinkColorInfoEvent extends InformationEvent {
	Node node, node1, node2;
	int color1, color2;
	Node node3;

	public ChildChangeLinkColorInfoEvent(Algorithm alg, Node node, Node node1,
			Node node2, int color1, int color2, Node node3) {
		super(alg);
		this.node = node;
		this.node1 = node1;
		this.node2 = node2;
		this.color1 = color1;
		this.color2 = color2;
		this.node3 = node3;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
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

	public int getColor1() {
		return color1;
	}

	public void setColor1(int color1) {
		this.color1 = color1;
	}

	public int getColor2() {
		return color2;
	}

	public void setColor2(int color2) {
		this.color2 = color2;
	}

	public Node getNode3() {
		return node3;
	}

	public void setNode3(Node node3) {
		this.node3 = node3;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ChildChangeLinkColorInfoEvent  ");
		sb.append(" Node:");
		sb.append(this.node.getId());
		sb.append(" Node1:");
		sb.append(this.node1.getId());
		sb.append(" Color1:");
		sb.append(this.color1);
		sb.append(" Node2:");
		sb.append(this.node2.getId());
		sb.append(" Color2:");
		sb.append(this.color2);
		sb.append(" Node3:");
		sb.append(this.node3.getId());

		return sb.toString();
	}
}
