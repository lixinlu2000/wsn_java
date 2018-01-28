package NHSensor.NHSensorSim.papers.E2STA.test;

import NHSensor.NHSensorSim.papers.E2STA.AllParam;
import NHSensor.NHSensorSim.papers.E2STA.E2STAExEnergyExperiment;

public class E2STAExAlgTest6 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(200) nodeNum(800) nodeFailProbability(0.667) queryMessageSize(50) networkHeight(100.0) networkID(1) k(0) nodeFailModelID(0) networkWidth(100.0) gridWidthRate(3.0) resultSize(200) queryAndPatialAnswerSize(250) failNodeNum(0) queryRegionRate(0.4) radioRange(10.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		E2STAExEnergyExperiment e = new E2STAExEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		System.out.println(e.toString());
	}

}
