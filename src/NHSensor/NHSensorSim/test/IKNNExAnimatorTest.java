package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.IKNNExAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.Animator;

public class IKNNExAnimatorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createKNNSensorSim(4, 450, 450, 750,
				200);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(IKNNExAlg.NAME);
		IKNNExAlg alg = (IKNNExAlg) sensorSim.getAlgorithm(IKNNExAlg.NAME);
		alg.getParam().setANSWER_SIZE(1);
		sensorSim.run();
		sensorSim.printStatistic();

		Animator animator = new Animator(alg);
		animator.start();
	}

}
