package NHSensor.NHSensorSim.papers.RESALifeTime.test;

import NHSensor.NHSensorSim.papers.RESALifeTime.AllParam;
import NHSensor.NHSensorSim.papers.RESALifeTime.RESACAAEnergyExperiment;

public class RESACAATest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "nodeFailProbability(0.04) d0(1.0) networkWidth(100.0) preambleLength(28.0) radioRange(11.0) encodingRatio(1.0) pld0(55.0) nodeNum(320) networkID(1) sigma(4.0) pt(0.0) n(4.0) queryMessageSize(22) k(30) networkHeight(100.0) resultSize(110) queryAndPatialAnswerSize(132) pn(-105.0) initialEnergy(800.0) queryRegionRate(0.64) gridWidthRate(0.8660254037844386) answerMessageSize(110) frameLength(50.0) linkID(1)";
		AllParam param;
		try {
			param = AllParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		RESACAAEnergyExperiment e = new RESACAAEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		System.out.println(e.toString());
	}

}
