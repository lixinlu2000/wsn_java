package NHSensor.NHSensorSim.analyser;

import NHSensor.NHSensorSim.events.Event;

public class EventTypeChooser implements SensornetEventChooser {
	private Class eventType;

	public EventTypeChooser(Class eventType) {
		this.eventType = eventType;
	}

	public boolean choose(Event event) {
		if (event.getClass().equals(this.getEventType()))
			return true;
		else
			return false;
	}

	public Class getEventType() {
		return eventType;
	}

	public void setEventType(Class eventType) {
		this.eventType = eventType;
	}

}
