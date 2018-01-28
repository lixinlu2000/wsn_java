package NHSensor.NHSensorSim.ui;

import java.util.Vector;

import NHSensor.NHSensorSim.events.Event;

public interface EventGenerator {
	public Event getNextEvent();
}
