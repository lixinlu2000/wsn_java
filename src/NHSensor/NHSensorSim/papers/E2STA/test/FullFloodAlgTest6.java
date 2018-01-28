package NHSensor.NHSensorSim.papers.E2STA.test;

import NHSensor.NHSensorSim.papers.E2STA.AllParam;
import NHSensor.NHSensorSim.papers.E2STA.FullFloodEnergyExperiment;

public class FullFloodAlgTest6 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(480) nodeNum(2050) nodeFailProbability(0.0) queryMessageSize(32) networkHeight(1000.0) networkID(6) k(0) nodeFailModelID(0) networkWidth(1000.0) gridWidthRate(12.0) resultSize(480) queryAndPatialAnswerSize(512) failNodeNum(0) queryRegionRate(0.05) radioRange(50.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		FullFloodEnergyExperiment e = new FullFloodEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		System.out.println(e.toString());
	}

}
