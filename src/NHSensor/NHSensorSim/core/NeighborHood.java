package NHSensor.NHSensorSim.core;

public class NeighborHood {
	int nodeID1;
	int nodeID2;

	public NeighborHood(int nodeID1, int nodeID2) {
		super();
		this.nodeID1 = nodeID1;
		this.nodeID2 = nodeID2;
	}

	public int getNodeID1() {
		return nodeID1;
	}

	public void setNodeID1(int nodeID1) {
		this.nodeID1 = nodeID1;
	}

	public int getNodeID2() {
		return nodeID2;
	}

	public void setNodeID2(int nodeID2) {
		this.nodeID2 = nodeID2;
	}
}
