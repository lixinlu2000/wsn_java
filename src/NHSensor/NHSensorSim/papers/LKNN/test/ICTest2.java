package NHSensor.NHSensorSim.papers.LKNN.test;

import NHSensor.NHSensorSim.papers.LKNN.IWQETraverseRingEventUseIteratorLinkAwareEnergyExperiment;
import NHSensor.NHSensorSim.papers.LKNN.TraverseRingExperimentParam;

public class ICTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean showAnimator = true;
		String paramString = "d0(1.0) networkWidth(100.0) preambleLength(0.0) maxHoleRadius(15.0) radioRange(20.0) nodeNum(400) encodingRatio(1.0) pld0(55.0) networkID(10) sigma(4.0) pt(0.0) ringWidth(10.0) n(4.0) queryMessageSize(10) networkHeight(100.0) pn(-105.0) holeModelID(1) holeNumber(0) answerMessageSize(30) ringLowRadius(40.0) frameLength(50.0) linkID(1)";
		TraverseRingExperimentParam param;
		try {
			param = TraverseRingExperimentParam.fromString(paramString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println(param);

		IWQETraverseRingEventUseIteratorLinkAwareEnergyExperiment e = new IWQETraverseRingEventUseIteratorLinkAwareEnergyExperiment(param);
		e.setShowAnimator(showAnimator);
		e.run();
		System.out.println(e.toString());
	}

}
