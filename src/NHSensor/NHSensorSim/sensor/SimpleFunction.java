package NHSensor.NHSensorSim.sensor;

import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.shape.Position;

public class SimpleFunction implements PositionFunction {

	public Object getValue(Position position, Time time) {
		return new Double(time.getTime());
	}
}
