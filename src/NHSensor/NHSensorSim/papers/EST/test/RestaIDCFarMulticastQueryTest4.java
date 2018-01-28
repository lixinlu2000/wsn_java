package NHSensor.NHSensorSim.papers.EST.test;

import NHSensor.NHSensorSim.algorithm.RestaIDCFarMulticastQueryAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.IDCTraverseSectorInRectEvent;
import NHSensor.NHSensorSim.papers.EST.AllParam;
import NHSensor.NHSensorSim.papers.EST.RestaFarMulticastQueryEnergyExperiment;
import NHSensor.NHSensorSim.papers.EST.RestaIDCFarMulticastQueryEnergyExperiment;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.SectorDirection;
import NHSensor.NHSensorSim.ui.Animator;

public class RestaIDCFarMulticastQueryTest4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "networkID(4) nodeNum(800) queryRegionRate(0.4) gridWidthRate(0) queryMessageSize(30) answerMessageSize(50) networkWidth(100.0) networkHeight(100.0) radioRange(10.0) queryAndPatialAnswerSize(100) resultSize(50)nodeFailProbability(0.04)k(0)nodeFailModelID(0)failNodeNum(0)";
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
				root, alg.getSectorInRects()[9], SectorDirection.FAR,
				new Message(alg.getParam().getQUERY_MESSAGE_SIZE(), alg), alg);
		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(alg, alg
				.getSectorInRects()[9]);

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
