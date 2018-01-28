package NHSensor.NHSensorSim.papers.E2STA.test;

import NHSensor.NHSensorSim.papers.E2STA.E2STAEnergyExperiment;
import NHSensor.NHSensorSim.papers.E2STA.AllParam;

public class E2STAAlgTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "networkID(1) nodeNum(300) queryRegionRate(0.4) gridWidthRate(1) queryMessageSize(30) answerMessageSize(50) networkWidth(100.0) networkHeight(100.0) radioRange(10.0) queryAndPatialAnswerSize(100) resultSize(50)nodeFailProbability(0.04)k(0)nodeFailModelID(0)failNodeNum(0)";
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
