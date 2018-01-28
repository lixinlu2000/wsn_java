package NHSensor.NHSensorSim.core;

import java.util.Comparator;

public class NodeAttachmentComparator implements Comparator {

	public NodeAttachmentComparator() {

	}

	public int compare(Object o1, Object o2) {
		NodeAttachment na1 = (NodeAttachment) o1;
		NodeAttachment na2 = (NodeAttachment) o2;

		if (na1.getHopCount() == na2.getHopCount())
			return 0;
		else if (na1.getHopCount() < na2.getHopCount())
			return 1;
		else
			return -1;
	}
}
