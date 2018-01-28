package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.IWQEUseICAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;
import NHSensor.NHSensorSim.ui.Animator;
import NHSensor.NHSensorSim.ui.IWQEAnimator;

public class IWQEUseICAlgTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 100, 100, 320, 0.36,
				15);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(IWQEUseICAlg.NAME);
		IWQEUseICAlg laiwqe = (IWQEUseICAlg) sensorSim
				.getAlgorithm(IWQEUseICAlg.NAME);
		laiwqe.getParam().setANSWER_SIZE(30);
		laiwqe.getParam().setINIT_ENERGY(Double.MAX_VALUE);

		Network network = laiwqe.getNetwork();
		network.setLinkEstimator(LinkEstimatorFactory.getArrayLinkEstimator(
				network, 20, 0));

		// double gridWidth = Math.sqrt(3)/2*laiwqe.getParam().getRADIO_RANGE();
		double gridWidth = 0.86 * laiwqe.getParam().getRADIO_RANGE();
		laiwqe.initSubQueryRegionByRegionWidth(gridWidth);
		sensorSim.run();
		sensorSim.printStatistic();

		Animator animator = new IWQEAnimator(laiwqe);
		animator.start();

	}

}
