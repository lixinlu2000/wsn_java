package NHSensor.NHSensorSim.papers.HSA.test;

import NHSensor.NHSensorSim.papers.HSA.AllParam;
import NHSensor.NHSensorSim.papers.HSA.HSAEnergyExperiment;

public class HSAAlgTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "answerMessageSize(200) nodeNum(480) queryMessageSize(50) networkHeight(100.0) networkID(5) initialEnergy(1500.0) networkWidth(100.0) subAreaNum(20) delta(0.2) datasetID(0) queryRegionRate(0.2) radioRange(10.0) datasetMax(10.0)";
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
