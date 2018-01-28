package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.CSAUseCDAAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.Animator;

public class CSAUseCDATest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		// SensorSim sensorSim = SensorSim.createSensorSim(15,400,400,400,0.6);
		SensorSim sensorSim = SensorSim.createSensorSim(1, 400, 400, 400, 0.6);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(CSAUseCDAAlg.NAME);
		CSAUseCDAAlg csa = (CSAUseCDAAlg) sensorSim
				.getAlgorithm(CSAUseCDAAlg.NAME);
		csa.getParam().setANSWER_SIZE(30);
		csa.getParam().setRADIO_RANGE(50);

		// double subQueryRegionWidth =
		// Math.sqrt(3)/2*csa.getParam().getRADIO_RANGE();
		double subQueryRegionWidth = 0.6 * csa.getParam().getRADIO_RANGE();
		csa.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(csa);
		animator.start();
	}
}
