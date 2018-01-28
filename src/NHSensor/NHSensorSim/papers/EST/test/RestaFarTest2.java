package NHSensor.NHSensorSim.papers.EST.test;

import NHSensor.NHSensorSim.algorithm.RestaFarAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.RDCTraverseSectorInRectEventFar;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.papers.EST.AllParam;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.ui.Animator;

public class RestaFarTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String paramString = "networkID(4) nodeNum(800) queryRegionRate(0.4) gridWidthRate(0.8660254037844386) queryMessageSize(30) answerMessageSize(50) networkWidth(100.0) networkHeight(100.0) radioRange(10.0) queryAndPatialAnswerSize(100) resultSize(50)nodeFailProbability(0.04)k(0)nodeFailModelID(0)failNodeNum(0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		SensorSim sensorSim = SensorSim.createSensorSim(param);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		RestaFarAlg alg = (RestaFarAlg) sensorSim
				.generateAlgorithm(RestaFarAlg.NAME);
		alg.getParam().setQUERY_MESSAGE_SIZE(param.getQueryMessageSize());
		alg.getParam().setANSWER_SIZE(param.getAnswerMessageSize());

		alg.initSectorInRectByMaxAlpha();
		alg.init();
		Node orig = alg.getQuery().getOrig();
		GPSRAttachment root = (GPSRAttachment) orig
				.getAttachment(alg.getName());

		RDCTraverseSectorInRectEventFar event = new RDCTraverseSectorInRectEventFar(
				root, alg.getSectorInRects()[6], new Message(alg.getParam()
						.getQUERY_MESSAGE_SIZE(), alg), alg);
		try {
			alg.getSimulator().handle(event);
		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Animator animator = new Animator(alg);
		animator.start();

	}

}
