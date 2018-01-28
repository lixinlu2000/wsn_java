package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.IWQEAlg;
import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.IWQEAnimator;

public class IWQEAnimatorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 450, 450, 400, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(IWQEAlg.NAME);
		IWQEAlg iwqe = (IWQEAlg) sensorSim.getAlgorithm(IWQEAlg.NAME);
		ItineraryAlgParam algParam = new ItineraryAlgParam(40, 20);
		iwqe.setAlgParam(algParam);

		double subQueryRegionWidth = Math.sqrt(2) / 2
				* iwqe.getParam().getRADIO_RANGE();
		iwqe.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);
		sensorSim.run();
		sensorSim.printStatistic();

		IWQEAnimator animator = new IWQEAnimator(iwqe);
		animator.start();
	}

}
