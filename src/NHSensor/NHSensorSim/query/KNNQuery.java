package NHSensor.NHSensorSim.query;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.TraversedNodes;
import NHSensor.NHSensorSim.shape.Position;

public class KNNQuery extends QueryBase {
	int k;
	Position center;

	public KNNQuery(Node orig, Position center, int k) {
		super(orig);
		this.center = center;
		this.k = k;
	}

	public Position getCenter() {
		return center;
	}

	public void setCenter(Position center) {
		this.center = center;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("KNNQuery(");
		sb.append(this.getCenter());
		sb.append(" ");
		sb.append("K(" + this.getK() + ")");
		sb.append(")");
		return sb.toString();

	}

	public TraversedNodes getTraversedNodes(Network network) {
		// TODO Auto-generated method stub
		return null;
	}

}
