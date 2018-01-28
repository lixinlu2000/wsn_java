package NHSensor.NHSensorSim.papers.E2STA.test;

import NHSensor.NHSensorSim.papers.E2STA.E2STAEnergyExperiment;
import NHSensor.NHSensorSim.papers.E2STA.AllParam;

public class E2STAAlgTest5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(200) nodeNum(480) nodeFailProbability(0.0) queryMessageSize(50) networkHeight(100.0) networkID(25) k(0) nodeFailModelID(0) networkWidth(100.0) gridWidthRate(10.0) resultSize(200) queryAndPatialAnswerSize(250) failNodeNum(0) queryRegionRate(0.8) radioRange(10.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		E2STAEnergyExperiment e = new E2STAEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		System.out.println(e.toString());
	}

}
