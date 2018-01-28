package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.ESAUseGBAFailureAwareAlg;
import NHSensor.NHSensorSim.algorithm.IWQEIdealUseICFailureAwareAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.failure.NodeFailureModelFactory;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;
import NHSensor.NHSensorSim.ui.Animator;
import NHSensor.NHSensorSim.ui.IWQEAnimator;

public class IWQEIdealUseICFailureAwareAlgTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(4, 450, 450, 600, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(IWQEIdealUseICFailureAwareAlg.NAME);
		IWQEIdealUseICFailureAwareAlg iwqeFailueAware = (IWQEIdealUseICFailureAwareAlg) sensorSim
				.getAlgorithm(IWQEIdealUseICFailureAwareAlg.NAME);
		iwqeFailueAware.getParam().setANSWER_SIZE(30);

		Network network = iwqeFailueAware.getNetwork();
		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(0, 100));

		double subQueryRegionWidth = Math.sqrt(3) / 2
				* iwqeFailueAware.getParam().getRADIO_RANGE();
		iwqeFailueAware
				.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(iwqeFailueAware);
		animator.start();
	}

}
