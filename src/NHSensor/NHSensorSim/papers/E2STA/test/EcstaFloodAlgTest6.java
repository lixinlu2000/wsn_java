package NHSensor.NHSensorSim.papers.E2STA.test;

import NHSensor.NHSensorSim.papers.E2STA.AllParam;
import NHSensor.NHSensorSim.papers.E2STA.EcstaFloodEnergyExperiment;

public class EcstaFloodAlgTest6 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(200) nodeNum(480) nodeFailProbability(0.0) queryMessageSize(0) networkHeight(100.0) networkID(2) k(0) nodeFailModelID(2) networkWidth(100.0) gridWidthRate(5.0) resultSize(200) queryAndPatialAnswerSize(250) failNodeNum(0) queryRegionRate(0.8) radioRange(10.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		EcstaFloodEnergyExperiment e = new EcstaFloodEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		System.out.println(e.toString());

	}

}
