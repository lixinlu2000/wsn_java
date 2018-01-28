package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GSAAlg;
import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.core.SensorSim;

public class GSATest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim("Random2");
		sensorSim.getSimulator().addHandleEventListener();

		
		sensorSim.addAlgorithm(GSAAlg.NAME);
		GSAAlg gsa = (GSAAlg) sensorSim.getAlgorithm(GSAAlg.NAME);
		ItineraryAlgParam algParam = new ItineraryAlgParam(4);
		gsa.setAlgParam(algParam);

		double gridWidth = Math.sqrt(2) / 2 * gsa.getParam().getRADIO_RANGE();
		double gridHeight = Math.sqrt(2) / 4 * gsa.getParam().getRADIO_RANGE();
		gsa.initGridWithGridSize(gridWidth, gridHeight);
		sensorSim.run();
		sensorSim.printStatistic();
	}

}
