package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.CKNNUseGBAFailureAwareAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.Animator;

public class EKNNUseGBAFailureAwareAlgTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim
				.createKNNSensorSim(2, 600, 600, 750, 60);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(CKNNUseGBAFailureAwareAlg.NAME);
		CKNNUseGBAFailureAwareAlg eknn = (CKNNUseGBAFailureAwareAlg) sensorSim
				.getAlgorithm(CKNNUseGBAFailureAwareAlg.NAME);
		eknn.getParam().setANSWER_SIZE(1);
		sensorSim.run();
		sensorSim.printStatistic();

		Animator gknnAnimator = new Animator(eknn);
		gknnAnimator.start();
	}

}
