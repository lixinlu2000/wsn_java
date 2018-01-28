package NHSensor.NHSensorSim.papers.CSA.test;

import NHSensor.NHSensorSim.papers.CSA.AllParam;
import NHSensor.NHSensorSim.papers.CSA.CSACDAEnergyExperiment;

public class CSACDAAlgTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(40) nodeNum(480) queryMessageSize(20) networkHeight(100.0) networkID(1) nodeFailModelID(0) maxHoleRadius(20) networkWidth(100.0) gridWidthRate(0.8660254037844386) holeNumber(3) resultSize(40) queryAndPatialAnswerSize(60) failNodeNum(0) holeModelID(2) queryRegionRate(0.5) radioRange(10.0)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		CSACDAEnergyExperiment e = new CSACDAEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		System.out.println(e.toString());
	}

}
