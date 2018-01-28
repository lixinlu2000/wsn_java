package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.CSAUseCDAAlg;
import NHSensor.NHSensorSim.algorithm.SWinFloodAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.Animator;

public class SWinFloodTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		// SensorSim sensorSim = SensorSim.createSensorSim(15,400,400,400,0.6);
		SensorSim sensorSim = SensorSim.createSensorSim(1, 400, 400, 400,1);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(SWinFloodAlg.NAME);
		SWinFloodAlg alg = (SWinFloodAlg) sensorSim
				.getAlgorithm(SWinFloodAlg.NAME);
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setRADIO_RANGE(50);

		// double subQueryRegionWidth =
		// Math.sqrt(3)/2*csa.getParam().getRADIO_RANGE();
		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}
}
