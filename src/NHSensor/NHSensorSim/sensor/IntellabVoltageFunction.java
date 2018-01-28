package NHSensor.NHSensorSim.sensor;

import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.dataset.IntelLabDataset;

public class IntellabVoltageFunction implements NodeIDFunction {
	IntelLabDataset intelLabDataset = new IntelLabDataset();

	public IntellabVoltageFunction() {
		intelLabDataset.extract();
	}

	public Object getValue(int nodeID, Time time) {
		return intelLabDataset.getVoltage(nodeID, time);
	}

}
