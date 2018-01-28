package NHSensor.NHSensorSim.demo;

import NHSensor.NHSensorSim.papers.EST.AllParam;
import NHSensor.NHSensorSim.papers.EST.RestaFarMulticastQueryEnergyExperiment;
import NHSensor.NHSensorSim.papers.EST.RestaIDCFarMulticastQueryEnergyExperiment;

public class RestaIDCFarMulticastQueryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "networkID(4) nodeNum(800) queryRegionRate(0.4) gridWidthRate(0) queryMessageSize(30) answerMessageSize(50) networkWidth(100.0) networkHeight(100.0) radioRange(10.0) queryAndPatialAnswerSize(100) resultSize(50) nodeFailProbability(0.04) k(0) nodeFailModelID(0) failNodeNum(0)";
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