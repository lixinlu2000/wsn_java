package NHSensor.NHSensorSim.papers.PEVA.test;

import NHSensor.NHSensorSim.papers.PEVA.AllParam;
import NHSensor.NHSensorSim.papers.PEVA.PEVAEnergyExperiment;

public class PEVATest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(1) nodeNum(320) nodeFailProbability(0.0) queryMessageSize(4) networkHeight(100.0) networkID(0) k(0) nodeFailModelID(0) networkWidth(100.0) gridWidthRate(17.0) resultSize(50) queryAndPatialAnswerSize(250) failNodeNum(0) queryRegionRate(1) radioRange(10.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		PEVAEnergyExperiment e = new PEVAEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		System.out.println(e.toString());
	}

}
