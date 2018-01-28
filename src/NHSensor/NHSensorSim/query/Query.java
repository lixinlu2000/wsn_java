package NHSensor.NHSensorSim.query;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.TraversedNodes;
import NHSensor.NHSensorSim.shape.Rect;

public class Query extends QueryBase {
	private Rect rect;

	public Query(Node orig, Rect rect) {
		super(orig);
		this.rect = rect;
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ID(");
		sb.append(id);
		sb.append(")");
		sb.append(",");
		sb.append(this.orig.getPos());
		sb.append(",");
		sb.append(this.rect);
		return sb.toString();
	}

	public TraversedNodes getTraversedNodes(Network network) {
		return TraversedNodes.getTraversedNodes(network, this.getRect());
	}
}
