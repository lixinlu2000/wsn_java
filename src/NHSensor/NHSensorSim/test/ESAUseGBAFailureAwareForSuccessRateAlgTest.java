package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.ESAUseGBAFailureAwareForSuccessRateAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.failure.NodeFailureModelFactory;
import NHSensor.NHSensorSim.ui.Animator;

public class ESAUseGBAFailureAwareForSuccessRateAlgTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(6, 400, 400, 800, 0.6,
				50);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(ESAUseGBAFailureAwareForSuccessRateAlg.NAME);
		ESAUseGBAFailureAwareForSuccessRateAlg esa = (ESAUseGBAFailureAwareForSuccessRateAlg) sensorSim
				.getAlgorithm(ESAUseGBAFailureAwareForSuccessRateAlg.NAME);
		esa.getParam().setANSWER_SIZE(30);

		Network network = esa.getNetwork();
		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(0, 200));

		double subQueryRegionWidth = Math.sqrt(3) / 2
				* esa.getParam().getRADIO_RANGE();
		esa.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(esa);
		animator.start();
	}
}
