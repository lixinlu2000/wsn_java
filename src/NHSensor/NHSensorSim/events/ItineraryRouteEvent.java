package NHSensor.NHSensorSim.events;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.core.Node;

public class ItineraryRouteEvent extends InformationEvent {
	private Vector route = new Vector();

	public ItineraryRouteEvent(Algorithm alg, Vector route) {
		super(alg);
		this.route = route;
	}

	public Vector getRoute() {
		return route;
	}

	public void setRoute(Vector route) {
		this.route = route;
	}

	public Node getNode(int index) {
		Attachment att = (Attachment) route.elementAt(index);
		return att.getNode();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ItineraryRouteEvent ");
		return sb.toString();
	}
}