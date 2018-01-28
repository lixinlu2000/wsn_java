package NHSensor.NHSensorSim.dataset;

import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.shape.Position;

public abstract class DataSet {
	public abstract double getValue(Position pos, Time time, int attributeID);
}
