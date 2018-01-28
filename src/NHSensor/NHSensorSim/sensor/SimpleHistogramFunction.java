package NHSensor.NHSensorSim.sensor;

import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.shape.Position;

public class SimpleHistogramFunction implements PositionFunction {
	private int index = 0;

	public Object getValue(Position position, Time time) {
		return new Double(time.getTime());
	}
}
