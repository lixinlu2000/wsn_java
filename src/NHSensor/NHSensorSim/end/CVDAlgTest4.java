package NHSensor.NHSensorSim.end;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.ESAOldAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.end.CVDAlg;
import NHSensor.NHSensorSim.network.NetworkUtil;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.ui.Animator;

public class CVDAlgTest4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 400, 400, 200, 1);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(CVDAlg.NAME);
		CVDAlg alg = (CVDAlg) sensorSim.getAlgorithm(CVDAlg.NAME);
		// alg.getParam().setRADIO_RANGE(20);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}
}
