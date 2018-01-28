package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.ROCAdaptiveGridTraverseRingEvent;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.ui.Animator;

public class ROCAdaptiveGridTraverseRingEventTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(3, 450, 450, 400, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(GPSRAttachmentAlg.NAME);
		GPSRAttachmentAlg alg = (GPSRAttachmentAlg) sensorSim
				.getAlgorithm(GPSRAttachmentAlg.NAME);
		alg.setQuery(null);
		sensorSim.run();
		sensorSim.printStatistic();
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setQUERY_MESSAGE_SIZE(24);

		NeighborAttachment root = (NeighborAttachment) alg.getNetwork()
				.get2LRTNode().getAttachment(alg.getName());
		Ring ring = new Ring(alg.getNetwork().getRect().getCentre(), 100, 140);
		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(alg, ring);
		alg.run(drawShapeEvent);

		Message queryMesage = new Message(10, null);
		ROCAdaptiveGridTraverseRingEvent e = new ROCAdaptiveGridTraverseRingEvent(
				root, ring, queryMesage, alg);
		alg.run(e);
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}
}
