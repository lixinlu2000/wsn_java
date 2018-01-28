package NHSensor.NHSensorSim.core;

public class Link {
	private Node node1;
	private Node node2;

	public Link(Node node1, Node node2) {
		this.node1 = node1;
		this.node2 = node2;
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

}
