package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.ESAUseGBAAlg;
import NHSensor.NHSensorSim.algorithm.ESAUseGBALinkAwareAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;
import NHSensor.NHSensorSim.ui.Animator;

public class ESAUseGBALinkAwareTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(2, 450, 450, 600, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(ESAUseGBALinkAwareAlg.NAME);
		ESAUseGBALinkAwareAlg esa = (ESAUseGBALinkAwareAlg) sensorSim
				.getAlgorithm(ESAUseGBALinkAwareAlg.NAME);
		esa.getParam().setANSWER_SIZE(30);

		Network network = esa.getNetwork();
		network.setLinkEstimator(LinkEstimatorFactory.getModelLinkEstimator(
				network, 20));

		double subQueryRegionWidth = Math.sqrt(3) / 2
				* esa.getParam().getRADIO_RANGE();
		esa.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(esa);
		animator.start();
	}

}
