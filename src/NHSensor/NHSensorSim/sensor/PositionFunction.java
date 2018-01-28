package NHSensor.NHSensorSim.sensor;

import NHSensor.NHSensorSim.core.*;
import NHSensor.NHSensorSim.shape.Position;

public interface PositionFunction extends Function {
	public Object getValue(Position position, Time time);
}
