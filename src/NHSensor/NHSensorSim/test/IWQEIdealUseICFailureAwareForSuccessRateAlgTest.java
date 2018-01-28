package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.IWQEIdealUseICFailureAwareForSuccessRateAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.failure.NodeFailureModelFactory;
import NHSensor.NHSensorSim.ui.Animator;

public class IWQEIdealUseICFailureAwareForSuccessRateAlgTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(10, 100, 100, 800,
				0.36, 10);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim
				.addAlgorithm(IWQEIdealUseICFailureAwareForSuccessRateAlg.NAME);
		IWQEIdealUseICFailureAwareForSuccessRateAlg iwqeFailueAware = (IWQEIdealUseICFailureAwareForSuccessRateAlg) sensorSim
				.getAlgorithm(IWQEIdealUseICFailureAwareForSuccessRateAlg.NAME);
		iwqeFailueAware.getParam().setANSWER_SIZE(30);

		Network network = iwqeFailueAware.getNetwork();
		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(0, 5));

		// double subQueryRegionWidth =
		// Math.sqrt(3)/2*iwqeFailueAware.getParam().getRADIO_RANGE();
		double subQueryRegionWidth = 0.99 * iwqeFailueAware.getParam()
				.getRADIO_RANGE();
		iwqeFailueAware
				.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(iwqeFailueAware);
		animator.start();
	}

}
