package NHSensor.NHSensorSim.analyser;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;

public class EventTypeOccurenceAnalyser extends AlgorithmAnalyser {
	private Class eventType;
	private int eventCounter = 0;

	public EventTypeOccurenceAnalyser(Algorithm algorithm, Class eventType) {
		super(algorithm);
		this.eventType = eventType;
	}

	public Class getEventType() {
		return eventType;
	}

	public void setEventType(Class eventType) {
		this.eventType = eventType;
	}

	public void analyse() {
		EventsDatabase eventsDatabase = new EventsDatabase(this.algorithm
				.getSimulator().getAllEvents());
		EventTypeChooser eventTypeChooser = new EventTypeChooser(this.eventType);
		Vector events = eventsDatabase.select(eventTypeChooser);

		this.eventCounter = events.size();
	}

	public Object getResult() {
		return new Double(this.eventCounter);
	}

}
