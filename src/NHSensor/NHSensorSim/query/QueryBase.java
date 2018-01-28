package NHSensor.NHSensorSim.query;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.TraversedNodes;

public abstract class QueryBase {
	protected int id;
	protected static int num = 0;
	protected Node orig;

	public QueryBase(Node orig) {
		this.orig = orig;
		this.id = QueryBase.num++;
	}

	public Node getOrig() {
		return orig;
	}

	public void setOrig(Node orig) {
		this.orig = orig;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public abstract TraversedNodes getTraversedNodes(Network network);

}
