package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class SimpleEventListener extends BaseEventListener {
	final static String NAME = "SimpleEventListener";

	public SimpleEventListener() {
		super(SimpleEventListener.NAME);
	}

	public SimpleEventListener(String name) {
		super(name);
	}

	public void handle(Event event) throws SensornetBaseException,
			HasNoEnergyException {
		event.handle();
	}

}
