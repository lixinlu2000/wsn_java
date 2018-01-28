package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class SleepEvent extends Event {
	double sleepTime;

	public SleepEvent(Algorithm alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	public SleepEvent(Algorithm alg, double sleepTime) {
		super(alg);
		this.sleepTime = sleepTime;
	}

	public SleepEvent(Algorithm alg, int tag) {
		super(alg, tag);
		// TODO Auto-generated constructor stub
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub

	}

	public double getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(double sleepTime) {
		this.sleepTime = sleepTime;
	}

}
