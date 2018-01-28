package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.LSAAlg;
import NHSensor.NHSensorSim.algorithm.RESAAlg;
import NHSensor.NHSensorSim.algorithm.RESAUseGridAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;
import NHSensor.NHSensorSim.ui.Animator;

public class RESAUseGridTest3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		// SensorSim sensorSim =
		// SensorSim.createSensorSim(3,100,100,320,0.64,11);
		SensorSim sensorSim = SensorSim.createSensorSim(1, 400, 400, 500, 0.36);

		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(RESAUseGridAlg.NAME);
		RESAUseGridAlg resa = (RESAUseGridAlg) sensorSim
				.getAlgorithm(RESAUseGridAlg.NAME);
		resa.getParam().setANSWER_SIZE(110);
		resa.getParam().setQUERY_MESSAGE_SIZE(22);
		resa.getParam().setINIT_ENERGY(Double.MAX_VALUE);
		resa.getParam().setPreambleLength(28);
		resa.getParam().setFrameSize(50);

		Network network = resa.getNetwork();
		network.setLinkEstimator(LinkEstimatorFactory.getArrayLinkEstimator(
				network, 0, 3));

		// double gridWidth = Math.sqrt(3)/2*lsa.getParam().getRADIO_RANGE();
		// double gridWidth = Math.sqrt(3)/2*10;
		double gridWidth = 40;
		resa.getParam().setRADIO_RANGE(60);
		resa.initSubQueryRegionAndGridsByRegionWidth(gridWidth);
		sensorSim.run();
		sensorSim.printStatistic();

		Animator animator = new Animator(resa);
		animator.start();

	}

}
