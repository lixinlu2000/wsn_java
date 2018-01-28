package NHSensor.NHSensorSim.sensor;

import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.dataset.IntelLabDataset;

public class IntellabLightFunction implements NodeIDFunction {
	IntelLabDataset intelLabDataset = new IntelLabDataset();

	public IntellabLightFunction() {
		intelLabDataset.extract();
	}

	public Object getValue(int nodeID, Time time) {
		return intelLabDataset.getLight(nodeID, time);
	}

}
