package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.RESACAAAlg;
import NHSensor.NHSensorSim.algorithm.RESACAAUseGridAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;
import NHSensor.NHSensorSim.ui.Animator;

public class RESACAAUseGridTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 450, 450, 400, 0.35);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(RESACAAUseGridAlg.NAME);
		RESACAAUseGridAlg resa = (RESACAAUseGridAlg) sensorSim
				.getAlgorithm(RESACAAUseGridAlg.NAME);
		resa.getParam().setANSWER_SIZE(30);
		resa.getParam().setINIT_ENERGY(Double.MAX_VALUE);

		Network network = resa.getNetwork();
		network.setLinkEstimator(LinkEstimatorFactory.getModelLinkEstimator(
				network, 20));
		resa.setConsiderLinkQuality(true);

		double gridWidth = Math.sqrt(3) / 2 * resa.getParam().getRADIO_RANGE();
		resa.initSubQueryRegionByRegionWidth(gridWidth);
		sensorSim.run();
		sensorSim.printStatistic();

		Animator animator = new Animator(resa);
		animator.start();

	}

}
