package NHSensor.NHSensorSim.papers.EST.test;

import NHSensor.NHSensorSim.papers.EST.lifeTime.AllParam;
import NHSensor.NHSensorSim.papers.EST.lifeTime.SWinFloodEnergyExperiment;

public class SWinFloodTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(50) nodeNum(320) nodeFailProbability(0.0) queryMessageSize(200) networkHeight(100.0) networkID(14) k(0) nodeFailModelID(0) initialEnergy(800.0) networkWidth(100.0) gridWidthRate(17.0) resultSize(50) queryAndPatialAnswerSize(250) failNodeNum(0) queryRegionRate(0.4) radioRange(10.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		SWinFloodEnergyExperiment e = new SWinFloodEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		System.out.println(e.toString());
	}

}
