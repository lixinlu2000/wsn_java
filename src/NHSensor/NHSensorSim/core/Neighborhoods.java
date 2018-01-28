package NHSensor.NHSensorSim.core;

import java.util.Hashtable;
import java.util.Vector;

public class Neighborhoods {
	Vector neighborhoods = new Vector();
	Hashtable idNeighborIds = new Hashtable();

	public Neighborhoods() {
		super();
	}

	public void add(int nodeID1, int nodeID2) {
		this.add(new NeighborHood(nodeID1, nodeID2));
	}

	public void add(int nodeID1, int nodeID2, boolean singleLink) {
		if (singleLink) {
			this.add(new NeighborHood(nodeID1, nodeID2));
		} else {
			this.add(new NeighborHood(nodeID1, nodeID2));
			this.add(new NeighborHood(nodeID2, nodeID1));
		}
	}

	public void addTwoLink(int nodeID1, int nodeID2) {
		this.add(nodeID1, nodeID2, false);
	}

	public void add(NeighborHood nh) {
		this.neighborhoods.add(nh);

		Integer id1Integer = new Integer(nh.getNodeID1());
		Vector neighborIds = (Vector) this.idNeighborIds.get(id1Integer);
		if (neighborIds == null) {
			neighborIds = new Vector();
			this.idNeighborIds.put(id1Integer, neighborIds);
		}
		neighborIds.add(nh.getNodeID2());
	}

	public NeighborHood elementAt(int i) {
		return (NeighborHood) neighborhoods.elementAt(i);
	}

	public Vector getNeighborIds(int nodeID) {
		Vector result = (Vector) this.idNeighborIds.get(new Integer(nodeID));
		if (result == null)
			return new Vector();
		else
			return result;
	}
}
