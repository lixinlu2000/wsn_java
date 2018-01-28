package NHSensor.NHSensorSim.end;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.Animator;

public class CVDAlgTestIntellabNetwork {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createIntelLabSensorSim(1);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(CVDAlg.NAME);
		CVDAlg alg = (CVDAlg) sensorSim.getAlgorithm(CVDAlg.NAME);
		alg.getParam().setRADIO_RANGE(6);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}
}
