package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.DGSAAlg;
import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.DGSAAnimator;

public class DGSATest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 450, 450, 400, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(DGSAAlg.NAME);
		DGSAAlg dgsa = (DGSAAlg) sensorSim.getAlgorithm(DGSAAlg.NAME);
		ItineraryAlgParam algParam = new ItineraryAlgParam(40, 20);
		dgsa.setAlgParam(algParam);

		double subQueryRegionWidth = Math.sqrt(5) / 5
				* dgsa.getParam().getRADIO_RANGE();
		dgsa.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);
		sensorSim.run();
		sensorSim.printStatistic();

		DGSAAnimator animator = new DGSAAnimator(dgsa);
		animator.start();
	}

}
