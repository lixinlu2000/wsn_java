package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GKNNAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.GKNNAnimator;

public class GKNNAnimatorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createKNNSensorSim(1, 450, 450, 1700,
				60);
		sensorSim.getSimulator().addHandleAndTraceEventListener();
		sensorSim.getParam().setQUERY_MESSAGE_SIZE(20);
		sensorSim.getParam().setANSWER_SIZE(5);

		sensorSim.addAlgorithm(GKNNAlg.NAME);
		GKNNAlg gknnAlg = (GKNNAlg) sensorSim.getAlgorithm(GKNNAlg.NAME);
		sensorSim.run();
		sensorSim.printStatistic();

		GKNNAnimator gknnAnimator = new GKNNAnimator(gknnAlg);
		gknnAnimator.start();
	}

}
