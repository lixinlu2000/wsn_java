package NHSensor.NHSensorSim.papers.RSA;

import java.util.Vector;

import NHSensor.NHSensorSim.util.Util;

public class TestRSAParamsFactory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RSAParamsFactory rSAParamsFactory = new RSAParamsFactory();
		Vector vector = rSAParamsFactory.queryMessageSizeVariable();
		System.out.println(Util.toString(vector));
	}

}
