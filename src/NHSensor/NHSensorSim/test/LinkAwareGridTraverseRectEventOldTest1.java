package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.LinkAwareGridTraverseRectEventOld;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shapeTraverse.Direction;
import NHSensor.NHSensorSim.ui.Animator;

public class LinkAwareGridTraverseRectEventOldTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		// SensorSim sensorSim = SensorSim.createSensorSim(5,450,450,600);
		SensorSim sensorSim = SensorSim.createSensorSim(1, 450, 450, 600);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(GPSRAttachmentAlg.NAME);
		GPSRAttachmentAlg alg = (GPSRAttachmentAlg) sensorSim
				.getAlgorithm(GPSRAttachmentAlg.NAME);
		alg.setQuery(null);
		sensorSim.run();
		sensorSim.printStatistic();
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setINIT_ENERGY(Double.MAX_VALUE);

		double width = 40;
		double height = 300;
		double minx = alg.getNetwork().getRect().getCentre().getX() - width / 2;
		double maxx = alg.getNetwork().getRect().getCentre().getX() + width / 2;
		double miny = alg.getNetwork().getRect().getCentre().getY() - height
				/ 2;
		double maxy = alg.getNetwork().getRect().getCentre().getY() + height
				/ 2;
		Rect rect = new Rect(minx, maxx, miny, maxy);
		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(alg, rect);
		alg.run(drawShapeEvent);

		NeighborAttachment root = (NeighborAttachment) alg.getNetwork()
				.get2LRTNode().getAttachment(alg.getName());

		Network network = alg.getNetwork();
		network.setLinkEstimator(LinkEstimatorFactory.getModelLinkEstimator(
				network, 20));

		Message answerMesage = new Message(alg.getParam().getANSWER_SIZE(),
				null);
		/*
		 * LinkAwareGridTraverseRectEvent e = new
		 * LinkAwareGridTraverseRectEvent(root, rect,Direction.DOWN,
		 * answerMesage, true, alg);
		 */
		LinkAwareGridTraverseRectEventOld e = new LinkAwareGridTraverseRectEventOld(
				root, rect, Direction.DOWN, answerMesage, alg);

		alg.run(e);
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}
}
