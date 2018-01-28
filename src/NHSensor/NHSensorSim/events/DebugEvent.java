package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public abstract class DebugEvent extends Event {
	public DebugEvent(Algorithm alg) {
		super(alg);
	}

	public DebugEvent(Algorithm alg, int tag) {
		super(alg, tag);
	}
}
