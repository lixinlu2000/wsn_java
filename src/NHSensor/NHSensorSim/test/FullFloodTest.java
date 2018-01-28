package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.FullFloodAlg;
import NHSensor.NHSensorSim.algorithm.SWinFloodAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.Animator;

public class FullFloodTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		// SensorSim sensorSim = SensorSim.createSensorSim(15,400,400,400,0.6);
		SensorSim sensorSim = SensorSim.createSensorSim(1, 400, 400, 400, 0.6);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(FullFloodAlg.NAME);
		FullFloodAlg alg = (FullFloodAlg) sensorSim
				.getAlgorithm(FullFloodAlg.NAME);
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setRADIO_RANGE(50);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}
}
