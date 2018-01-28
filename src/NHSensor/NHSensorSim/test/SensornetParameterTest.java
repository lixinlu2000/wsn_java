package NHSensor.NHSensorSim.test;

import NHSensor.NHSensorSim.core.Param;

public class SensornetParameterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Param param = new Param();
		double radioCircleNum = 450 * 450 / (Math.PI * param.getRADIO_RANGE() * param
				.getRADIO_RANGE());

	}

}
