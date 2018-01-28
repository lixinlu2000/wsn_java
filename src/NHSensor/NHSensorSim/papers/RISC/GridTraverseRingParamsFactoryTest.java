package NHSensor.NHSensorSim.papers.RISC;

import NHSensor.NHSensorSim.util.FormatUtil;

public class GridTraverseRingParamsFactoryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GridTraverseRingParamsFactory f = new GridTraverseRingParamsFactory();
		System.out.println(FormatUtil.toString(f.nodeNumVariable(), "\n"));
	}

}
