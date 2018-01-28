package NHSensor.NHSensorSim.core;

import NHSensor.NHSensorSim.events.Event;

public class Log {
	private static Log log;

	private Log() {

	}

	public static Log getInstance() {
		if (log == null) {
			log = new Log();
			return log;
		} else
			return log;
	}

	public void log(Event e) {

	}
}
