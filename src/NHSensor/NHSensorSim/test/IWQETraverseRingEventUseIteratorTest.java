package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.IWQETraverseRingEventUseIterator;
import NHSensor.NHSensorSim.failure.NodeFailureModelFactory;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.ui.Animator;

public class IWQETraverseRingEventUseIteratorTest {

	/**
	 * @param args
	 *            IWQETraverseRingEventUseIterator
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 450, 450, 400, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(GPSRAttachmentAlg.NAME);
		GPSRAttachmentAlg alg = (GPSRAttachmentAlg) sensorSim
				.getAlgorithm(GPSRAttachmentAlg.NAME);
		alg.setQuery(null);
		sensorSim.run();
		sensorSim.printStatistic();
		alg.getParam().setANSWER_SIZE(35);
		alg.getParam().setQUERY_MESSAGE_SIZE(10);

		Network network = alg.getNetwork();
		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(0, 100));
		/*
		 * NeighborAttachment root =
		 * (NeighborAttachment)alg.getNetwork().get2LRTNode
		 * ().getAttachment(alg.getName());
		 */

		Ring ring = new Ring(alg.getNetwork().getRect().getCentre(), 80, 125);
		NeighborAttachment root = (NeighborAttachment) alg.getNetwork()
				.getTopNodeInRing(ring).getAttachment(alg.getName());

		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(alg, ring);
		alg.run(drawShapeEvent);

		Message mesage = new Message(10, null);
		IWQETraverseRingEventUseIterator e = new IWQETraverseRingEventUseIterator(
				root, ring, mesage, alg);
		alg.run(e);
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}
}
