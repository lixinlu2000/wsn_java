package NHSensor.NHSensorSim.sensor;

import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.dataset.IntelLabDataset;

public class IntellabHumidityFunction implements NodeIDFunction {
	IntelLabDataset intelLabDataset = new IntelLabDataset();

	public IntellabHumidityFunction() {
		intelLabDataset.extract();
	}

	public Object getValue(int nodeID, Time time) {
		return intelLabDataset.getHumidity(nodeID, time);
	}

}
