package NHSensor.NHSensorSim.sensor;

import NHSensor.NHSensorSim.core.*;
import NHSensor.NHSensorSim.shape.Position;

public interface NodeIDFunction extends Function {
	public Object getValue(int nodeID, Time time);
}
