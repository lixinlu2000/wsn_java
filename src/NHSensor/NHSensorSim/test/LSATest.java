package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.LSAAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;
import NHSensor.NHSensorSim.ui.Animator;

public class LSATest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 450, 450, 400, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(LSAAlg.NAME);
		LSAAlg lsa = (LSAAlg) sensorSim.getAlgorithm(LSAAlg.NAME);
		lsa.getParam().setANSWER_SIZE(30);
		lsa.getParam().setINIT_ENERGY(Double.MAX_VALUE);

		Network network = lsa.getNetwork();
		network.setLinkEstimator(LinkEstimatorFactory.getModelLinkEstimator(
				network, 20));

		double gridWidth = Math.sqrt(3) / 2 * lsa.getParam().getRADIO_RANGE();
		lsa.initSubQueryRegionByRegionWidth(gridWidth);
		sensorSim.run();
		sensorSim.printStatistic();

		Animator animator = new Animator(lsa);
		animator.start();
	}

}
