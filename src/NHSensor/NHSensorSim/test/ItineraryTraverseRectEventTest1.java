package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.ItineraryTraverseRectEvent;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.shapeTraverse.Direction;
import NHSensor.NHSensorSim.ui.Animator;

public class ItineraryTraverseRectEventTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(3, 450, 450, 600);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(GPSRAttachmentAlg.NAME);
		GPSRAttachmentAlg alg = (GPSRAttachmentAlg) sensorSim
				.getAlgorithm(GPSRAttachmentAlg.NAME);
		alg.setQuery(null);
		sensorSim.run();
		sensorSim.printStatistic();
		alg.getParam().setANSWER_SIZE(30);

		NeighborAttachment root = (NeighborAttachment) alg.getNetwork()
				.get2LRTNode().getAttachment(alg.getName());
		double width = 300;
		double height = 40;
		double minx = alg.getNetwork().getRect().getCentre().getX() - width / 2;
		double maxx = alg.getNetwork().getRect().getCentre().getX() + width / 2;
		double miny = alg.getNetwork().getRect().getCentre().getY() - height
				/ 2;
		double maxy = alg.getNetwork().getRect().getCentre().getY() + height
				/ 2;
		Rect rect = new Rect(minx, maxx, miny, maxy);
		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(alg, rect);
		alg.run(drawShapeEvent);

		Message queryAndPatialAnswerMesage = new Message(alg.getParam()
				.getANSWER_SIZE(), null);
		/*
		 * Message queryAndPatialAnswerMesage = new
		 * Message(alg.getParam().getANSWER_SIZE()+
		 * alg.getParam().getQUERY_MESSAGE_SIZE(),null);
		 */
		ItineraryTraverseRectEvent e = new ItineraryTraverseRectEvent(root,
				rect, Direction.LEFT, queryAndPatialAnswerMesage, true, alg);
		alg.run(e);
		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();
	}
}
