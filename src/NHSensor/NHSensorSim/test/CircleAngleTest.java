package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.shape.AngleCircleInRing;
import NHSensor.NHSensorSim.shape.Circle;
import NHSensor.NHSensorSim.shape.CircleCircleInRing;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.ui.Animator;

public class CircleAngleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(2, 450, 450, 600, 0.36);
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
		DrawShapeEvent drawShapeEvent1 = new DrawShapeEvent(alg, ring);
		alg.run(drawShapeEvent1);

		Position centre = alg.getNetwork().getRect().getCentre();
		Position p1 = new Position(centre.getX(), centre.getY() + 120);
		Circle beginCircle = new Circle(p1, 50);
		Position p2 = new Position(centre.getX() - 20, centre.getY() + 105);
		Circle endCircle = new Circle(p2, 50);
		CircleCircleInRing circleCircleInRing = new CircleCircleInRing(
				beginCircle, endCircle, ring);
		DrawShapeEvent drawShapeEvent2 = new DrawShapeEvent(alg,
				circleCircleInRing);
		// alg.run(drawShapeEvent2);

		AngleCircleInRing angleCircleInRing = new AngleCircleInRing(
				Math.PI / 2, beginCircle, ring);
		DrawShapeEvent drawShapeEvent3 = new DrawShapeEvent(alg,
				angleCircleInRing);
		alg.run(drawShapeEvent3);

		DrawShapeEvent drawShapeEvent4 = new DrawShapeEvent(alg, beginCircle);
		alg.run(drawShapeEvent4);

		sensorSim.printStatistic();
		Animator animator = new Animator(alg);
		animator.start();

	}

}
