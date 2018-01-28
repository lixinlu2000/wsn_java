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

public class RestaIDCFarMulticastQueryTest10 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(50) nodeNum(480) nodeFailProbability(0.04) queryMessageSize(200) networkHeight(100.0) networkID(3) k(0) nodeFailModelID(0) networkWidth(100.0) gridWidthRate(15.0) resultSize(50) queryAndPatialAnswerSize(250) failNodeNum(0) queryRegionRate(0.4) radioRange(10.0)";
		// "answerMessageSize(50) nodeNum(480) nodeFailProbability(0.04) queryMessageSize(200) networkHeight(100.0) networkID(2) k(0) nodeFailModelID(0) networkWidth(100.0) gridWidthRate(10.0) resultSize(50) queryAndPatialAnswerSize(250) failNodeNum(0) queryRegionRate(0.4) radioRange(10.0)"
		// ;
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
		e.setShowAnimator(showAnimator);
		e.run();
		System.out.println(e.toString());
	}

}
