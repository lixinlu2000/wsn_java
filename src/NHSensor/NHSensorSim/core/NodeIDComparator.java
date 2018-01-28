package NHSensor.NHSensorSim.core;

import java.util.Comparator;

public class NodeIDComparator implements Comparator {

	public int compare(Object arg0, Object arg1) {
		Node node1 = (Node) arg0;
		Node node2 = (Node) arg1;
		return node1.getId() - node2.getId();
	}

}
