package NHSensor.NHSensorSim.papers.E2STA.test;

import NHSensor.NHSensorSim.papers.E2STA.AllParam;
import NHSensor.NHSensorSim.papers.E2STA.compare.E2STAExEcstaFloodEnergyExperiment;

public class E2STAExEcstaFloodCompareTest6 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(150) nodeNum(480) nodeFailProbability(0.6666666666666666) queryMessageSize(50) networkHeight(100.0) networkID(6) k(0) nodeFailModelID(3) networkWidth(100.0) gridWidthRate(5.0) resultSize(150) queryAndPatialAnswerSize(200) failNodeNum(0) queryRegionRate(0.5) radioRange(10.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		E2STAExEcstaFloodEnergyExperiment e = new E2STAExEcstaFloodEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		System.out.println(e.toString());
	}

}
