package NHSensor.NHSensorSim.papers.HSA.test;

import NHSensor.NHSensorSim.algorithm.HSAAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.GreedyForwardToANodeInSectorInRectFarFromPosEvent;
import NHSensor.NHSensorSim.events.RDCTraverseSectorInRectEventNear;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.papers.HSA.AllParam;
import NHSensor.NHSensorSim.papers.HSA.HSAEnergyExperiment;
import NHSensor.NHSensorSim.shape.SectorDirection;
import NHSensor.NHSensorSim.ui.Animator;

public class HSAAlgTest9 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(150) nodeNum(480) nodeFailProbability(0.6666666666666666) queryMessageSize(50) networkHeight(100.0) networkID(28) k(0) nodeFailModelID(0) networkWidth(100.0) gridWidthRate(22.0) resultSize(150) queryAndPatialAnswerSize(200) failNodeNum(0) queryRegionRate(0.5) radioRange(10.0)";
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
		int index = 11;
		NeighborAttachment root = (NeighborAttachment) orig
				.getAttachment(alg.getName());

		Message queryMessage = new Message(alg.getParam().getQUERY_MESSAGE_SIZE(), null);
		GreedyForwardToANodeInSectorInRectFarFromPosEvent gftse = new GreedyForwardToANodeInSectorInRectFarFromPosEvent(root,
				alg.getSectorInRects()[index].getSector().getCentre(), alg.getSectorInRects()[index].getSector().diagonalRadian(),
				alg.getSectorInRects()[index], SectorDirection.NEAR, 
				queryMessage, alg);
		try {
			alg.getSimulator().handle(gftse);
		} catch (SensornetBaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		alg.getRoute().addAll(gftse.getRoute());
		
		NeighborAttachment curNa = (NeighborAttachment) gftse.getLastNode();

		RDCTraverseSectorInRectEventNear event = new RDCTraverseSectorInRectEventNear(
				root, curNa, alg.getSectorInRects()[index],
				new Message(alg.getParam().getQUERY_MESSAGE_SIZE(), alg), alg);
		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(alg, alg
				.getSectorInRects()[index]);

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
