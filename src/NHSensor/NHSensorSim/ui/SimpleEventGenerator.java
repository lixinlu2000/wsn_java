package NHSensor.NHSensorSim.ui;

import java.util.Vector;

import NHSensor.NHSensorSim.events.Event;

public class SimpleEventGenerator implements EventGenerator {
	Vector events;
	int currentIndex = 0;

	public SimpleEventGenerator(Vector events) {
		this.events = events;
	}

	public Event getNextEvent() {
		Event event = null;
		if (this.getCurrentIndex() < this.getEvents().size()) {
			event = (Event) this.getEvents().elementAt(this.getCurrentIndex());
			this.currentIndex++;
		}

		return event;
	}

	public Vector getEvents() {
		return events;
	}

	public void setEvents(Vector events) {
		this.events = events;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

}
