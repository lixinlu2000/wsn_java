package NHSensor.NHSensorSim.events;

import java.util.Vector;

public class TraceEventListener extends BaseEventListener {
	final static String NAME = "TraceEventListener";
	Vector Events = new Vector();

	public TraceEventListener() {
		super(TraceEventListener.NAME);
	}

	public TraceEventListener(String name) {
		super(name);
	}

	public void handle(Event event) {
		// TODO Auto-generated method stub
		// if(event instanceof BroadcastEvent) {
		// BroadcastEvent be = (BroadcastEvent)event;
		// BroadcastEvent beCopy = new BroadcastEvent(be);
		// this.getEvents().add(beCopy);
		// }
		// else {
		this.getEvents().add(event);
		// }
	}

	public Vector getEvents() {
		return Events;
	}
}
