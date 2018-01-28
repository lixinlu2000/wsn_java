package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.DefaultTreeAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.BuildTreeEvent;
import NHSensor.NHSensorSim.events.ShowTreeTopologyEvent;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.ui.Animator;

public class BuildTreeEventTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(2, 450, 450, 150, 1);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(DefaultTreeAlg.NAME);
		DefaultTreeAlg alg = (DefaultTreeAlg) sensorSim
				.getAlgorithm(DefaultTreeAlg.NAME);
		sensorSim.run();
		sensorSim.printStatistic();
		alg.getParam().setANSWER_SIZE(1);

		NodeAttachment root = (NodeAttachment) alg.getNetwork().get2LRTNode()
				.getAttachment(alg.getName());
		Message queryMesage = new Message(10, null);
		Rect rect = alg.getNetwork().getRect();
		Message queryMessage = new Message(10, alg.getQuery());

		BuildTreeEvent bte = new BuildTreeEvent(root, rect, queryMessage, alg);
		bte.setVisible(false);
		alg.run(bte);

		ShowTreeTopologyEvent stte = new ShowTreeTopologyEvent(alg, bte
				.getAns());
		alg.run(stte);

		Animator animator = new Animator(alg);
		animator.start();
	}
}
