package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.EcstaAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.Animator;

public class EcstaFloodTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		// SensorSim sensorSim = SensorSim.createSensorSim(15,400,400,400,0.6);
		SensorSim sensorSim = SensorSim.createSensorSim(2, 400, 400, 400, 0.6);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(EcstaAlg.NAME);
		EcstaAlg alg = (EcstaAlg) sensorSim.getAlgorithm(EcstaAlg.NAME);
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
