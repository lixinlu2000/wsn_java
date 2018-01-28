package NHSensor.NHSensorSim.test;

import NHSensor.NHSensorSim.util.Convertor;

public class MahtACosTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Convertor.radianToDegree(Math.acos(0.5)));
		System.out.println(Convertor.radianToDegree(Math.acos(-0.5)));
	}

}
