package NHSensor.NHSensorSim.test;

import java.util.Random;

public class RandomTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			System.out.print(r.nextGaussian());
			System.out.print(" ");
		}
		System.out.println();

		Random r1 = new Random();
		for (int i = 0; i < 10; i++) {
			System.out.print(r1.nextGaussian());
			System.out.print(" ");
		}

	}

}
