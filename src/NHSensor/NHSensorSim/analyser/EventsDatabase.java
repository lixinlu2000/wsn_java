package NHSensor.NHSensorSim.analyser;

import java.util.Vector;

import NHSensor.NHSensorSim.events.Event;

public class EventsDatabase {
	private Vector events;
	private int currentEventIndex = -1;

	public int getCurrentEventIndex() {
		return currentEventIndex;
	}

	public synchronized void setCurrentEventIndex(int currentEventIndex)
			throws Exception {
		if (currentEventIndex < -1 || currentEventIndex >= this.events.size())
			throw new Exception("currentEventIndex out of range");
		this.currentEventIndex = currentEventIndex;
	}

	public synchronized void moveNext(int step) throws Exception {
		if (step < 0)
			throw new Exception("step " + step + "  is less than zero");
		int destEventIndex = this.currentEventIndex + step;
		if (destEventIndex >= this.events.size()) {
			throw new Exception("move to out of range");
		}
		this.setCurrentEventIndex(destEventIndex);
	}

	public synchronized void movePrevious(int step) throws Exception {
		if (step < 0)
			throw new Exception("step " + step + "  is less than zero");
		int destEventIndex = this.currentEventIndex - step;
		if (destEventIndex < -1) {
			throw new Exception("move to out of range");
		}
		this.setCurrentEventIndex(destEventIndex);
	}

	public EventsDatabase(Vector events) {
		this.events = events;
	}

	public Vector getEvents() {
		return events;
	}

	public synchronized void setEvents(Vector events) {
		this.events = events;
	}

	public synchronized Vector selectEvents(int startIndex, int endIndex) {
		Vector resultEvents = new Vector();

		if (startIndex < 0 || endIndex < startIndex)
			return resultEvents;

		if (endIndex >= this.events.size())
			endIndex = this.events.size() - 1;

		for (int i = startIndex; i <= endIndex; i++) {
			resultEvents.addElement(this.events.elementAt(i));
		}
		return resultEvents;
	}

	public Vector select(SensornetEventChooser chooser) {
		Vector result = new Vector();
		Event event;

		for (int i = 0; i < this.events.size(); i++) {
			event = (Event) this.events.elementAt(i);
			if (chooser.choose(event))
				result.add(event);
		}
		return result;
	}

	public Vector select(Class classType) {
		EventTypeChooser eventTypeChooser = new EventTypeChooser(classType);
		return this.select(eventTypeChooser);

	}

	public Vector getCurrentEvents() {
		return this.selectEvents(0, currentEventIndex);
	}

	public Event getCurrentEvent() {
		return (Event) this.events.elementAt(currentEventIndex);
	}

	public int size() {
		return this.events.size();
	}

	public boolean isEnd() {
		return this.currentEventIndex == this.size() - 1;
	}

	public boolean isBegin() {
		return this.currentEventIndex == -1;
	}
}
