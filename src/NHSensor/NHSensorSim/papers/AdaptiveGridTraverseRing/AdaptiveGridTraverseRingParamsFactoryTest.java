package NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing;

import NHSensor.NHSensorSim.util.FormatUtil;

public class AdaptiveGridTraverseRingParamsFactoryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AdaptiveGridTraverseRingParamsFactory f = new AdaptiveGridTraverseRingParamsFactory();
		System.out.println(FormatUtil.toString(f.nodeNumVariable(), "\n"));
	}

}
