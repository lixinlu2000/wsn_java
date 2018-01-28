package NHSensor.NHSensorSim.papers.HSA.test;

import NHSensor.NHSensorSim.algorithm.HSAAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.IDCTraverseSectorInRectEvent;
import NHSensor.NHSensorSim.events.RDCTraverseSectorInRectEventNear;
import NHSensor.NHSensorSim.papers.HSA.AllParam;
import NHSensor.NHSensorSim.papers.HSA.HSAEnergyExperiment;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.SectorDirection;
import NHSensor.NHSensorSim.shapeTraverse.RDCTraverseSectorInRectIteratorNear;
import NHSensor.NHSensorSim.ui.Animator;

public class HSAAlgTest4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "networkID(4) nodeNum(800) queryRegionRate(0.4) gridWidthRate(20) queryMessageSize(30) answerMessageSize(50) networkWidth(100.0) networkHeight(100.0) radioRange(10.0) queryAndPatialAnswerSize(100) resultSize(50)nodeFailProbability(0.04)k(0)nodeFailModelID(0)failNodeNum(0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		HSAEnergyExperiment e = new HSAEnergyExperiment(
				param);
		HSAAlg alg = (HSAAlg) e.getAlgorithm();

		alg.init();
		Node orig = alg.getQuery().getOrig();
		GPSRAttachment root = (GPSRAttachment) orig
				.getAttachment(alg.getName());

		RDCTraverseSectorInRectEventNear event = new RDCTraverseSectorInRectEventNear(
				root, root, alg.getSectorInRects()[4],
				new Message(alg.getParam().getQUERY_MESSAGE_SIZE(), alg), alg);
		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(alg, alg
				.getSectorInRects()[4]);

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
