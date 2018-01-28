package NHSensor.NHSensorSim.test;

import java.util.Vector;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.DGSANewAlg;
import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.DGSANewAnimator;

public class DGSANewTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		// SensorSim sensorSim = SensorSim.createSensorSim(1,450,450,400,0.36);
		int networkID = 1;
		double width = 450;
		double height = 450;
		int xgap = 30;
		int ygap = 30;
		int nodeNum = 600;
		double queryRegionRate = 0.36;

		SensorSim sensorSim = SensorSim.createSensorSim(networkID, width,
				height, nodeNum, queryRegionRate);
		// SensorSim sensorSim = SensorSim.createGridSensorSim(width, height,
		// xgap, ygap, queryRegionRate);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(DGSANewAlg.NAME);
		DGSANewAlg dgsaNew = (DGSANewAlg) sensorSim
				.getAlgorithm(DGSANewAlg.NAME);
		ItineraryAlgParam algParam = new ItineraryAlgParam(40, 20);
		dgsaNew.setAlgParam(algParam);

		double subQueryRegionWidth = Math.sqrt(2) / 2
				* dgsaNew.getParam().getRADIO_RANGE();
		dgsaNew.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);
		sensorSim.run();
		sensorSim.printStatistic();

		DGSANewAnimator animator = new DGSANewAnimator(dgsaNew);
		animator.start();
		Vector events = dgsaNew.getSimulator().getEvents();
	}

}
