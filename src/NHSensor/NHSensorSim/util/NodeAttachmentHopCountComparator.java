package NHSensor.NHSensorSim.util;

import java.util.Comparator;

import NHSensor.NHSensorSim.core.NodeAttachment;

public class NodeAttachmentHopCountComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		NodeAttachment na1, na2;
		na1= (NodeAttachment)o1;
		na2 = (NodeAttachment)o2;
		
		return (na1.getHopCount()-na2.getHopCount());
	}

}
