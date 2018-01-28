package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.ui.Animator;

public class arngNetworkTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 100, 100, 100, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();
		Network network = Network.anrgNetwork1();
		network.setLinkEstimator(LinkEstimatorFactory
				.getArrayLinkEstimatorFromANRGFile1());
		sensorSim.setNetwork(network.getSubNetwork(new Rect(0, 15, 0, 15)));

		sensorSim.addAlgorithm(GPSRAttachmentAlg.NAME);
		GPSRAttachmentAlg alg = (GPSRAttachmentAlg) sensorSim
				.getAlgorithm(GPSRAttachmentAlg.NAME);
		alg.setQuery(null);
		sensorSim.run();
		sensorSim.printStatistic();

		/*
		 * NeighborAttachment root =
		 * (NeighborAttachment)alg.getNetwork().get2LRTNode
		 * ().getAttachment(alg.getName()); Ring ring = new
		 * Ring(alg.getNetwork().getRect().getCentre(),100,130); DrawShapeEvent
		 * drawShapeEvent = new DrawShapeEvent(alg,ring);
		 * alg.run(drawShapeEvent);
		 * 
		 * Message queryMesage = new Message(10,null);
		 * AdaptiveGridTraverseRingEvent e = new
		 * AdaptiveGridTraverseRingEvent(root, ring, queryMesage, alg);
		 * alg.run(e);
		 */

		Animator animator = new Animator(alg);
		animator.start();
	}

}
