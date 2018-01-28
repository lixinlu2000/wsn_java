package NHSensor.NHSensorSim.papers.HSA.test;

import NHSensor.NHSensorSim.papers.HSA.AllParam;
import NHSensor.NHSensorSim.papers.HSA.HSAEnergyExperiment;

public class HSAAlgTest6 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(150) nodeNum(480) nodeFailProbability(0.6666666666666666) queryMessageSize(50) networkHeight(100.0) networkID(29) k(0) nodeFailModelID(0) networkWidth(100.0) gridWidthRate(20.0) resultSize(150) queryAndPatialAnswerSize(200) failNodeNum(0) queryRegionRate(0.5) radioRange(10.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		HSAEnergyExperiment e = new HSAEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		System.out.println(e.toString());
	}

}
