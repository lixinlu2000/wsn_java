package NHSensor.NHSensorSim.network;

import NHSensor.NHSensorSim.core.Neighborhoods;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;

public class NetworkUtil {

	public static Network createNetwork1() {
		Node[] nodes = new Node[10];
		nodes[0] = new Node(0, 3, 4);
		nodes[1] = new Node(1, 6, 4);
		nodes[2] = new Node(2, 8, 4);
		nodes[3] = new Node(3, 4, 3);
		nodes[4] = new Node(4, 4, 1);
		nodes[5] = new Node(5, 1, 3);
		nodes[6] = new Node(6, 2, 2);
		nodes[7] = new Node(7, 7, 3);
		nodes[8] = new Node(8, 1, 1);
		nodes[9] = new Node(9, 5, 5);

		Neighborhoods neighborhoods = new Neighborhoods();
		neighborhoods.addTwoLink(9, 0);
		neighborhoods.addTwoLink(9, 1);
		neighborhoods.addTwoLink(9, 2);
		neighborhoods.addTwoLink(0, 5);
		neighborhoods.addTwoLink(0, 6);
		neighborhoods.addTwoLink(0, 3);
		neighborhoods.addTwoLink(1, 3);
		neighborhoods.addTwoLink(1, 7);
		neighborhoods.addTwoLink(6, 8);
		neighborhoods.addTwoLink(6, 4);
		neighborhoods.addTwoLink(8, 7);
		neighborhoods.addTwoLink(8, 4);

		return Network.createNetwork(8, 5, nodes, neighborhoods);
	}

	public static Network createNetwork2() {
		Node[] nodes = new Node[10];
		nodes[0] = new Node(0, 3, 4);
		nodes[1] = new Node(1, 6, 4);
		nodes[2] = new Node(2, 8, 4);
		nodes[3] = new Node(3, 4, 3);
		nodes[4] = new Node(4, 4, 1);
		nodes[5] = new Node(5, 1, 3);
		nodes[6] = new Node(6, 2, 2);
		nodes[7] = new Node(7, 7, 3);
		nodes[8] = new Node(8, 1, 1);
		nodes[9] = new Node(9, 5, 5);

		Neighborhoods neighborhoods = new Neighborhoods();
		neighborhoods.addTwoLink(9, 0);
		neighborhoods.addTwoLink(9, 1);
		neighborhoods.addTwoLink(9, 2);
		neighborhoods.addTwoLink(0, 5);
		neighborhoods.addTwoLink(0, 6);
		neighborhoods.addTwoLink(0, 3);
		neighborhoods.addTwoLink(1, 3);
		neighborhoods.addTwoLink(1, 7);
		neighborhoods.addTwoLink(6, 8);
		neighborhoods.addTwoLink(6, 4);
		// neighborhoods.addTwoLink(8, 7);
		neighborhoods.addTwoLink(8, 4);

		return Network.createNetwork(8, 5, nodes, neighborhoods);
	}

}
