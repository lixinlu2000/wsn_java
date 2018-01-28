package NHSensor.NHSensorSim.test;

import java.io.File;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.LSAAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.ui.Animator;

public class LSATest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		Network network = Network
				.createNetworkFromFile(new File(
						"./dataset/dataset/indoor-ceiling-array/2003_05_05/locations-lab.txt"));
		SensorSim sensorSim = new SensorSim(network);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		Rect queryRect = network.getRect();
		Query query = new Query(network.get2LRTNode(), queryRect);
		sensorSim.setQuery(query);

		sensorSim.addAlgorithm(LSAAlg.NAME);
		LSAAlg lsa = (LSAAlg) sensorSim.getAlgorithm(LSAAlg.NAME);
		lsa.getParam().setANSWER_SIZE(30);

		double gridWidth = Math.sqrt(3) / 2 * lsa.getParam().getRADIO_RANGE();
		lsa.initSubQueryRegionByRegionWidth(gridWidth);
		sensorSim.run();
		sensorSim.printStatistic();

		Animator animator = new Animator(lsa);
		animator.start();
	}

}
