package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.IKNNAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.IKNNItineraryAnimator;

public class IKNNItineraryNodeEventTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim
				.createKNNSensorSim(2, 450, 450, 750, 60);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(IKNNAlg.NAME);
		IKNNAlg alg = (IKNNAlg) sensorSim.getAlgorithm(IKNNAlg.NAME);
		alg.getParam().setANSWER_SIZE(1);
		sensorSim.run();
		sensorSim.printStatistic();

		IKNNItineraryAnimator animator = new IKNNItineraryAnimator(alg);
		animator.start();
	}

}
