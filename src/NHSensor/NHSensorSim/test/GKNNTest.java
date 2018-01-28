package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GKNNAlg;
import NHSensor.NHSensorSim.core.SensorSim;

public class GKNNTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createKNNSensorSim("KNNTest");
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(GKNNAlg.NAME);
		sensorSim.run();
		sensorSim.printStatistic();
	}

}
