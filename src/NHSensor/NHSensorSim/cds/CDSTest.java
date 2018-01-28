package NHSensor.NHSensorSim.cds;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.ESAAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.Animator;

public class CDSTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(2, 100, 100, 400, 1);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(CDSAlg.NAME);
		CDSAlg alg = (CDSAlg) sensorSim.getAlgorithm(CDSAlg.NAME);
		alg.getParam().setRADIO_RANGE(30);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}

}
