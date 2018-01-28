package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.RestaIDCFarAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.IDCTraverseSectorInRectEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.SectorDirection;
import NHSensor.NHSensorSim.ui.Animator;

public class RestaIDCFarTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 450, 450, 400, 0.35);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		RestaIDCFarAlg alg = (RestaIDCFarAlg) sensorSim
				.generateAlgorithm(RestaIDCFarAlg.NAME);
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setINIT_ENERGY(Double.MAX_VALUE);
		alg.initSectorInRectByArea(3);

		alg.init();
		Node orig = alg.getQuery().getOrig();
		GPSRAttachment root = (GPSRAttachment) orig
				.getAttachment(alg.getName());

		IDCTraverseSectorInRectEvent event = new IDCTraverseSectorInRectEvent(
				root, alg.getSectorInRects()[1], SectorDirection.FAR,
				new Message(alg.getParam().getQUERY_MESSAGE_SIZE(), alg), alg);
		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(alg, alg
				.getSectorInRects()[1]);

		try {
			alg.getSimulator().handle(drawShapeEvent);
			alg.getSimulator().handle(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Animator animator = new Animator(alg);
		animator.start();

	}

}
