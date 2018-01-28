package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class InformationEvent extends Event {
	public InformationEvent(Algorithm alg) {
		super(alg);
	}

	public void handle() throws SensornetBaseException {
	}

}
