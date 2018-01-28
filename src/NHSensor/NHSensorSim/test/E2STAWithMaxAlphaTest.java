package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.E2STAAlg;
import NHSensor.NHSensorSim.algorithm.RestaFarAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.Animator;

public class E2STAWithMaxAlphaTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 450, 450, 400, 0.35);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(E2STAAlg.NAME);
		E2STAAlg alg = (E2STAAlg) sensorSim.getAlgorithm(E2STAAlg.NAME);
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setINIT_ENERGY(Double.MAX_VALUE);

		alg.initSectorInRectByMaxAlpha();
		sensorSim.run();
		sensorSim.printStatistic();

		Animator animator = new Animator(alg);
		animator.start();

	}

}
