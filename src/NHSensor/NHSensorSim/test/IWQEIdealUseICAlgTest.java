package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.IWQEIdealUseICAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;
import NHSensor.NHSensorSim.ui.Animator;
import NHSensor.NHSensorSim.ui.IWQEAnimator;

public class IWQEIdealUseICAlgTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 100, 100, 320, 0.36,
				10);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(IWQEIdealUseICAlg.NAME);
		IWQEIdealUseICAlg laiwqe = (IWQEIdealUseICAlg) sensorSim
				.getAlgorithm(IWQEIdealUseICAlg.NAME);
		laiwqe.getParam().setANSWER_SIZE(220);
		laiwqe.getParam().setQUERY_MESSAGE_SIZE(22);
		laiwqe.getParam().setINIT_ENERGY(Double.MAX_VALUE);
		laiwqe.getParam().setPreambleLength(28);
		laiwqe.getParam().setFrameSize(50);

		Network network = laiwqe.getNetwork();
		network.setLinkEstimator(LinkEstimatorFactory.getArrayLinkEstimator(
				network, 0, 0));

		/*
		 * ItineraryAlgParam algParam = new ItineraryAlgParam(4);
		 * lsa.setAlgParam(algParam);
		 */
		double gridWidth = Math.sqrt(3) / 2
				* laiwqe.getParam().getRADIO_RANGE();
		laiwqe.initSubQueryRegionByRegionWidth(gridWidth);
		sensorSim.run();
		sensorSim.printStatistic();

		Animator animator = new IWQEAnimator(laiwqe);
		animator.start();

	}

}
