package NHSensor.NHSensorSim.end;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.network.NetworkUtil;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.ui.Animator;

public class EndAlgTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		Network network = NetworkUtil.createNetwork1();
		Node orig = network.getNode(9);
		Query query = new Query(orig, network.getRect());

		SensorSim sensorSim = SensorSim.createSensorSim(network, query);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(EndAlg.NAME);
		EndAlg alg = (EndAlg) sensorSim.getAlgorithm(EndAlg.NAME);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}
}