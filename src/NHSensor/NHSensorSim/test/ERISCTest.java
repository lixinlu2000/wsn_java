package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.EDCAdaptiveGridTraverseRingEventUseIterator;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.ui.Animator;

public class ERISCTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim
				.createSensorSim(1, 450, 450, 1200, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(GPSRAttachmentAlg.NAME);
		GPSRAttachmentAlg alg = (GPSRAttachmentAlg) sensorSim
				.getAlgorithm(GPSRAttachmentAlg.NAME);
		alg.setQuery(null);
		sensorSim.run();
		sensorSim.printStatistic();
		alg.getParam().setANSWER_SIZE(35);
		alg.getParam().setQUERY_MESSAGE_SIZE(10);

		Ring ring = new Ring(alg.getNetwork().getRect().getCentre(), 80, 125);
		NeighborAttachment root = (NeighborAttachment) alg.getNetwork()
				.getTopNodeInRing(ring).getAttachment(alg.getName());
		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(alg, ring);
		alg.run(drawShapeEvent);

		Message mesage = new Message(10, null);
		EDCAdaptiveGridTraverseRingEventUseIterator e = new EDCAdaptiveGridTraverseRingEventUseIterator(
				root, ring, mesage, true, alg);
		alg.run(e);
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}
}
