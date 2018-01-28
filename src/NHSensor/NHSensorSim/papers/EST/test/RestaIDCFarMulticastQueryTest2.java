package NHSensor.NHSensorSim.papers.EST.test;

import NHSensor.NHSensorSim.papers.EST.AllParam;
import NHSensor.NHSensorSim.papers.EST.RestaIDCFarMulticastQueryEnergyExperiment;

public class RestaIDCFarMulticastQueryTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(50) nodeNum(480) nodeFailProbability(0.04) queryMessageSize(30) networkHeight(100.0) networkID(20) k(0) nodeFailModelID(0) networkWidth(100.0) gridWidthRate(0) resultSize(50) queryAndPatialAnswerSize(80) failNodeNum(0) queryRegionRate(0.4) radioRange(10.0)";
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
