package NHSensor.NHSensorSim.test;

import NHSensor.NHSensorSim.core.*;
import NHSensor.NHSensorSim.events.Event;
import NHSensor.NHSensorSim.routing.gpsr.*;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.ui.Animator;

public class GPSRTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SensorSim sensorSim = SensorSim.createSensorSim("Random2");
		sensorSim.addAlgorithm(GPSRAlg.NAME);
		GPSRAlg gpsrAlg = (GPSRAlg) sensorSim.getAlgorithm(GPSRAlg.NAME);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		int graphType = GraphType.GG;
		gpsrAlg.setGraphType(graphType);
		gpsrAlg.init();

		Node source = sensorSim.getNetwork().getLBNode();
		GPSRAttachment sourceAttachment = (GPSRAttachment) source
				.getAttachment(gpsrAlg.getName());
		
		Position destPos = sensorSim.getNetwork().getRect().getCentre();
		
		Message message = new Message(10, null);

		Event gpsr = new GPSR(sourceAttachment, destPos, message, gpsrAlg);
		gpsrAlg.run(gpsr);

		Animator animator = new Animator(gpsrAlg);
		animator.start();

	}

}
