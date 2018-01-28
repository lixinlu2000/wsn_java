package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.LSAAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;
import NHSensor.NHSensorSim.ui.Animator;

public class LSATest4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(3, 54, 54, 320, 0.32,
				11);
		// SensorSim sensorSim =
		// SensorSim.createSensorSim(1,400,400,500,0.36,10);

		sensorSim.getSimulator().addHandleAndTraceEventListener();
		Network network = Network.anrgNetwork1();
		network.setLinkEstimator(LinkEstimatorFactory
				.getArrayLinkEstimatorFromANRGFile1());
		sensorSim.setNetwork(network);
		sensorSim.setQuery(network.createQuery(0.9));

		sensorSim.addAlgorithm(LSAAlg.NAME);
		LSAAlg lsa = (LSAAlg) sensorSim.getAlgorithm(LSAAlg.NAME);
		lsa.getParam().setANSWER_SIZE(110);
		lsa.getParam().setQUERY_MESSAGE_SIZE(22);
		lsa.getParam().setINIT_ENERGY(Double.MAX_VALUE);
		lsa.getParam().setPreambleLength(28);
		lsa.getParam().setFrameSize(50);

		// double gridWidth = Math.sqrt(3)/2*lsa.getParam().getRADIO_RANGE();
		// double gridWidth = Math.sqrt(3)/2*10;
		double gridWidth = 0.6 * 11;
		lsa.initSubQueryRegionAndGridsByRegionWidth(gridWidth);
		lsa.getParam().setRADIO_RANGE(30);
		sensorSim.run();
		sensorSim.printStatistic();

		Animator animator = new Animator(lsa);
		animator.start();

	}

}
