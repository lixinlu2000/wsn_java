package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.IWQEIdealUseICAlg;
import NHSensor.NHSensorSim.algorithm.IWQEIdealUseICAndLinkAwareRoutingAlg;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;
import NHSensor.NHSensorSim.ui.Animator;
import NHSensor.NHSensorSim.ui.IWQEAnimator;

public class IWQEIdealUseICAndLinkAwareRoutingAlgTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 450, 450, 400, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(IWQEIdealUseICAndLinkAwareRoutingAlg.NAME);
		IWQEIdealUseICAndLinkAwareRoutingAlg laiwqe = (IWQEIdealUseICAndLinkAwareRoutingAlg) sensorSim
				.getAlgorithm(IWQEIdealUseICAndLinkAwareRoutingAlg.NAME);
		laiwqe.getParam().setANSWER_SIZE(30);
		laiwqe.getParam().setINIT_ENERGY(Double.MAX_VALUE);

		Network network = laiwqe.getNetwork();
		network.setLinkEstimator(LinkEstimatorFactory.getModelLinkEstimator(
				network, 20));

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
