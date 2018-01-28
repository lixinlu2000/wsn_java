package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.RSAAlg;
import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.Animator;

public class RSATest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(2, 450, 450, 600, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(RSAAlg.NAME);
		RSAAlg rsa = (RSAAlg) sensorSim.getAlgorithm(RSAAlg.NAME);
		ItineraryAlgParam algParam = new ItineraryAlgParam(4);
		rsa.setAlgParam(algParam);

		double gridWidth = Math.sqrt(2) / 2 * rsa.getParam().getRADIO_RANGE();
		double gridHeight = Math.sqrt(2) / 4 * rsa.getParam().getRADIO_RANGE();
		rsa.initGridWithGridSize(gridWidth, gridHeight);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(rsa);
		animator.start();
	}

}
