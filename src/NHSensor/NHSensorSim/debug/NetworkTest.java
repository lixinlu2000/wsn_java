package NHSensor.NHSensorSim.debug;

import java.util.Iterator;
import java.util.Vector;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;

public class NetworkTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int width = 50;
		int height = 50;
		int nodeNum = 10;
		
		Network network = Network.createRandomNetwork(width, height, nodeNum,1);
		System.out.println("The number of network: " + network.getNodeNum());
		Vector<Node> nodes = network.getNodes();
		
//		Iterator<Node> it = nodes.iterator();
//		while (it.hasNext()) {
//		System.out.println("" + it.next());
//		}
		
		Node node1 = network.getFirstNode();
		System.out.println(node1);
		Node node2 = network.getLBNode();
		System.out.println(node2);
		Node node3 = network.get2LRBNode();
		System.out.println(node3);
		Node node4 = network.getNode(4);
		System.out.println(node4);
		
	}

}
