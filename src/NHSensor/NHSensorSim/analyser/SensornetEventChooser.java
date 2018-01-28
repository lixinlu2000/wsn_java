package NHSensor.NHSensorSim.analyser;

import NHSensor.NHSensorSim.events.Event;

public interface SensornetEventChooser {
	public boolean choose(Event event);
}
