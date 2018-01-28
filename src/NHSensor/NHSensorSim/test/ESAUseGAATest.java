package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.ESAUseGAAAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.Animator;
import NHSensor.NHSensorSim.ui.IWQEAnimator;

public class ESAUseGAATest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(4, 100, 100, 320, 0.4);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(ESAUseGAAAlg.NAME);
		ESAUseGAAAlg esa = (ESAUseGAAAlg) sensorSim
				.getAlgorithm(ESAUseGAAAlg.NAME);
		esa.getParam().setANSWER_SIZE(30);
		esa.getParam().setRADIO_RANGE(10);

		double subQueryRegionWidth = Math.sqrt(3) / 2
				* esa.getParam().getRADIO_RANGE();
		esa.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);

		sensorSim.run();
		System.out.println(esa.getStatistics().getQueryResultCorrectness()
				.getResultCorrectRate());
		sensorSim.printStatistic();
		Animator animator = new Animator(esa);
		animator.start();
	}

}
