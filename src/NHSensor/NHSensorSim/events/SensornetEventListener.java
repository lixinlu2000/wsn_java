package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.exception.SensornetBaseException;

public interface SensornetEventListener {
	public void handle(Event event) throws SensornetBaseException;

	public String getName();
}
