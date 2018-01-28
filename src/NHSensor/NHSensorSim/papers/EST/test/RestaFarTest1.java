package NHSensor.NHSensorSim.papers.EST.test;

import NHSensor.NHSensorSim.papers.EST.AllParam;
import NHSensor.NHSensorSim.papers.EST.RestaFarEnergyExperiment;

public class RestaFarTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "networkID(4) nodeNum(800) queryRegionRate(0.4) gridWidthRate(0.8660254037844386) queryMessageSize(30) answerMessageSize(50) networkWidth(100.0) networkHeight(100.0) radioRange(10.0) queryAndPatialAnswerSize(100) resultSize(50)nodeFailProbability(0.04)k(0)nodeFailModelID(0)failNodeNum(0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		RestaFarEnergyExperiment e = new RestaFarEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		System.out.println(e.toString());
	}

}