package NHSensor.NHSensorSim.end;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.Animator;

public class EndTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(2, 100, 100, 200, 1);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(EndAlg.NAME);
		EndAlg alg = (EndAlg) sensorSim.getAlgorithm(EndAlg.NAME);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}
}