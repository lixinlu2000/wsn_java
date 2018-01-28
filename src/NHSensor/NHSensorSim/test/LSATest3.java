package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.LSAAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;
import NHSensor.NHSensorSim.ui.Animator;

public class LSATest3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(3, 100, 100, 320, 0.64,
				11);
		// SensorSim sensorSim =
		// SensorSim.createSensorSim(1,400,400,500,0.36,10);

		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(LSAAlg.NAME);
		LSAAlg lsa = (LSAAlg) sensorSim.getAlgorithm(LSAAlg.NAME);
		lsa.getParam().setANSWER_SIZE(110);
		lsa.getParam().setQUERY_MESSAGE_SIZE(22);
		lsa.getParam().setINIT_ENERGY(Double.MAX_VALUE);
		lsa.getParam().setPreambleLength(28);
		lsa.getParam().setFrameSize(50);

		Network network = lsa.getNetwork();
		network.setLinkEstimator(LinkEstimatorFactory.getArrayLinkEstimator(
				network, 0, 3));

		// double gridWidth = Math.sqrt(3)/2*lsa.getParam().getRADIO_RANGE();
		// double gridWidth = Math.sqrt(3)/2*10;
		double gridWidth = 40;
		lsa.getParam().setRADIO_RANGE(60);
		lsa.initSubQueryRegionAndGridsByRegionWidth(gridWidth);
		sensorSim.run();
		sensorSim.printStatistic();

		Animator animator = new Animator(lsa);
		animator.start();

	}

}
