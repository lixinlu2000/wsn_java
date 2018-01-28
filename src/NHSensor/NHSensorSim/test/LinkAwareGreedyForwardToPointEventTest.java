package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.LinkAwareGreedyForwardToPointEvent;
import NHSensor.NHSensorSim.events.LinkAwareGridTraverseRectEvent;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shapeTraverse.Direction;
import NHSensor.NHSensorSim.ui.Animator;

public class LinkAwareGreedyForwardToPointEventTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		// SensorSim sensorSim = SensorSim.createSensorSim(5,450,450,600);
		SensorSim sensorSim = SensorSim.createSensorSim(1, 100, 100, 480, 0.36,
				10);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(GPSRAttachmentAlg.NAME);
		GPSRAttachmentAlg alg = (GPSRAttachmentAlg) sensorSim
				.getAlgorithm(GPSRAttachmentAlg.NAME);
		alg.setQuery(null);
		sensorSim.run();
		sensorSim.printStatistic();
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setINIT_ENERGY(Double.MAX_VALUE);

		NeighborAttachment root = (NeighborAttachment) alg.getNetwork()
				.get2LRTNode().getAttachment(alg.getName());

		Network network = alg.getNetwork();
		network.setLinkEstimator(LinkEstimatorFactory.getModelLinkEstimator(
				network, 0));

		Message answerMesage = new Message(alg.getParam().getANSWER_SIZE(),
				null);
		LinkAwareGreedyForwardToPointEvent e = new LinkAwareGreedyForwardToPointEvent(
				root, network.getCentreNode().getPos(), answerMesage, alg);
		alg.run(e);
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}
}
