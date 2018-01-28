package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GKNNAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.GKNNItineraryAnimator;

public class GKNNItineraryNodeEventTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim
				.createKNNSensorSim(2, 600, 600, 750, 60);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(GKNNAlg.NAME);
		GKNNAlg gknnAlg = (GKNNAlg) sensorSim.getAlgorithm(GKNNAlg.NAME);
		gknnAlg.getParam().setANSWER_SIZE(1);
		sensorSim.run();
		sensorSim.printStatistic();

		GKNNItineraryAnimator animator = new GKNNItineraryAnimator(gknnAlg);
		animator.start();
	}

}
