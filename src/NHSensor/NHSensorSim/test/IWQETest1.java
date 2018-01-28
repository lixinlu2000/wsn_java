package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.IWQEAlg;
import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.core.SensorSim;

public class IWQETest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim("Random2");
		sensorSim.getSimulator().addHandleEventListener();

		sensorSim.addAlgorithm(IWQEAlg.NAME);
		IWQEAlg iwqe = (IWQEAlg) sensorSim.getAlgorithm(IWQEAlg.NAME);
		ItineraryAlgParam algParam = new ItineraryAlgParam(4);
		iwqe.setAlgParam(algParam);

		double subQueryRegionWidth = Math.sqrt(3) / 2
				* iwqe.getParam().getRADIO_RANGE();
		iwqe.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);
		sensorSim.run();
		sensorSim.printStatistic();
	}

}
