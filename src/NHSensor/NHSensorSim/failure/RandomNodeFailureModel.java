package NHSensor.NHSensorSim.failure;

import java.util.Random;
import java.util.Vector;

import NHSensor.NHSensorSim.core.Node;

public class RandomNodeFailureModel implements NodeFailureModel {
	int randomNumber;
	int failNodeSize;

	public RandomNodeFailureModel(int randomNumber, int failNodeSize) {
		super();
		this.randomNumber = randomNumber;
		this.failNodeSize = failNodeSize;
	}

	public Vector getFailNodes(Vector nodes) {
		Random r = new Random(randomNumber);
		Vector failNodes = new Vector();
		Node node;
		int index;

		for (int i = 0; i < failNodeSize; i++) {
			index = r.nextInt(nodes.size());
			node = (Node) nodes.elementAt(index);
			failNodes.add(node);
		}
		return failNodes;
	}
}
