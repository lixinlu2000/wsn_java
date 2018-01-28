package NHSensor.NHSensorSim.sensor;

import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.dataset.IntelLabDataset;

public class IntellabTemperatureFunction implements NodeIDFunction {
	IntelLabDataset intelLabDataset = new IntelLabDataset();

	public IntellabTemperatureFunction() {
		intelLabDataset.extract();
	}

	public Object getValue(int nodeID, Time time) {
		return intelLabDataset.getTemperature(nodeID, time);
	}

}
