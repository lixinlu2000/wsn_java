package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToShapeEvent;
import NHSensor.NHSensorSim.shape.RingSector;
import NHSensor.NHSensorSim.ui.Animator;

public class GreedyForwardToShapeTest {

	/**
	 * @param args
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

		RingSector ringSector = new RingSector(alg.getNetwork().getRect()
				.getCentre(), 10, 60, Math.PI, Math.PI / 2);
		DrawShapeEvent DrawShapeEvent = new DrawShapeEvent(alg, ringSector);
		alg.run(DrawShapeEvent);

		NeighborAttachment root = (NeighborAttachment) alg.getNetwork()
				.get2LRTNode().getAttachment(alg.getName());
		GreedyForwardToShapeEvent e = new GreedyForwardToShapeEvent(root,
				ringSector, new Message(10, null), alg);
		alg.run(e);
		Animator animator = new Animator(alg);
		animator.start();
	}

}
