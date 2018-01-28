package NHSensor.NHSensorSim.papers.ROC;

import NHSensor.NHSensorSim.util.FormatUtil;

public class ROCAdaptiveGridTraverseRingParamsFactoryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ROCAdaptiveGridTraverseRingParamsFactory f = new ROCAdaptiveGridTraverseRingParamsFactory();
		System.out.println(FormatUtil.toString(f.nodeNumVariable(), "\n"));
	}

}
