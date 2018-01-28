package NHSensor.NHSensorSim.link;

import java.util.Vector;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;

public class IdealRadioRangeLinkEstimator extends LinkEstimator {
	double radioRange;
	Network network;
	
	public IdealRadioRangeLinkEstimator() {
		super();
	}

	public IdealRadioRangeLinkEstimator(Network network, double radioRange) {
		this.radioRange = radioRange;
		this.network = network;
		
		Node node;
		Vector nodes = this.network.getNodes();
		for (int i = 0; i < nodes.size(); i++) {
			node = (Node) nodes.elementAt(i);
			node.setRadioRange(radioRange);
		}
		
	}

	public double getLinkPRR(int nodeID1, int nodeID2) {
		Node node1 = (Node)network.getNodeIDNodes().get(nodeID1);
		Node node2 = (Node)network.getNodeIDNodes().get(nodeID2);
		
		boolean send = node1.getPos().distance(node2.getPos())<=node1.getRadioRange();
		boolean receive = node1.getPos().distance(node2.getPos())<=node2.getRadioRange();
		if(send&&receive) return 1;
		else return 0;
	}

	public double getRadioRange() {
		return radioRange;
	}

	public void setRadioRange(double radioRange) {
		this.radioRange = radioRange;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}
}
