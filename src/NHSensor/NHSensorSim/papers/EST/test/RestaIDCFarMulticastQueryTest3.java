package NHSensor.NHSensorSim.papers.EST.test;

import NHSensor.NHSensorSim.algorithm.RestaIDCFarMulticastQueryAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.IDCTraverseSectorInRectEvent;
import NHSensor.NHSensorSim.papers.EST.AllParam;
import NHSensor.NHSensorSim.papers.EST.RestaIDCFarMulticastQueryEnergyExperiment;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.SectorDirection;
import NHSensor.NHSensorSim.ui.Animator;

public class RestaIDCFarMulticastQueryTest3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(50) nodeNum(480) nodeFailProbability(0.04) queryMessageSize(200) networkHeight(100.0) networkID(4) k(0) nodeFailModelID(0) networkWidth(100.0) gridWidthRate(0.0) resultSize(50) queryAndPatialAnswerSize(250) failNodeNum(0) queryRegionRate(0.9) radioRange(10.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		RestaIDCFarMulticastQueryEnergyExperiment e = new RestaIDCFarMulticastQueryEnergyExperiment(
				param);
		RestaIDCFarMulticastQueryAlg alg = (RestaIDCFarMulticastQueryAlg) e
				.getAlgorithm();

		alg.init();
		Node orig = alg.getQuery().getOrig();
		GPSRAttachment root = (GPSRAttachment) orig
				.getAttachment(alg.getName());

		IDCTraverseSectorInRectEvent event = new IDCTraverseSectorInRectEvent(
				root, alg.getSectorInRects()[0], SectorDirection.FAR,
				new Message(alg.getParam().getQUERY_MESSAGE_SIZE(), alg), alg);
		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(alg, alg
				.getSectorInRects()[0]);

		try {
			alg.getSimulator().handle(drawShapeEvent);
			alg.getSimulator().handle(event);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Animator animator = new Animator(alg);
		animator.start();

	}

}
