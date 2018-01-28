package NHSensor.NHSensorSim.papers.EST.test;

import NHSensor.NHSensorSim.algorithm.RestaIDCFarAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.IDCTraverseSectorInRectEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.papers.EST.AllParam;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.SectorDirection;
import NHSensor.NHSensorSim.ui.Animator;

public class RestaIDCFarTest3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String paramString = "answerMessageSize(50) nodeNum(480) nodeFailProbability(0.04) queryMessageSize(200) networkHeight(100.0) networkID(1) k(0) nodeFailModelID(0) networkWidth(100.0) gridWidthRate(0) resultSize(50) queryAndPatialAnswerSize(250) failNodeNum(0) queryRegionRate(0.8) radioRange(10.0)";
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

		RestaIDCFarAlg alg = (RestaIDCFarAlg) sensorSim
				.generateAlgorithm(RestaIDCFarAlg.NAME);
		alg.getParam().setQUERY_MESSAGE_SIZE(param.getQueryMessageSize());
		alg.getParam().setANSWER_SIZE(param.getAnswerMessageSize());

		alg.initSectorInRectByMaxAlpha();
		alg.init();
		Node orig = alg.getQuery().getOrig();
		GPSRAttachment root = (GPSRAttachment) orig
				.getAttachment(alg.getName());

		IDCTraverseSectorInRectEvent event = new IDCTraverseSectorInRectEvent(
				root, alg.getSectorInRects()[1], SectorDirection.FAR,
				new Message(alg.getParam().getQUERY_MESSAGE_SIZE(), alg), alg);
		try {
			alg.getSimulator().handle(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Animator animator = new Animator(alg);
		animator.start();

	}

}
