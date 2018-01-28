package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.ESAUseGBAFailureAwareAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.failure.NodeFailureModelFactory;
import NHSensor.NHSensorSim.ui.Animator;

public class ESAUseGBAFailureAwareTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(6, 450, 450, 500, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(ESAUseGBAFailureAwareAlg.NAME);
		ESAUseGBAFailureAwareAlg esa = (ESAUseGBAFailureAwareAlg) sensorSim
				.getAlgorithm(ESAUseGBAFailureAwareAlg.NAME);
		esa.getParam().setANSWER_SIZE(30);

		Network network = esa.getNetwork();
		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(0, 100));

		double subQueryRegionWidth = Math.sqrt(3) / 2
				* esa.getParam().getRADIO_RANGE();
		esa.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(esa);
		animator.start();
	}

}
