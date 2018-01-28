package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class ProxyDebugEvent extends DebugEvent {
	Event event;

	public ProxyDebugEvent(Event event, Algorithm alg) {
		super(alg);
		this.event = event;
	}

	public ProxyDebugEvent(Algorithm alg, int tag) {
		super(alg, tag);
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub

	}

	public String toString() {
		return "ProxyDebugEvent Event: " + event.toString();
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
