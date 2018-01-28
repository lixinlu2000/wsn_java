package NHSensor.NHSensorSim.test;

public class BitTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int a = 1;
		int b;
		int size = 16;
		int maxValueInt = 8;
		for (int i = size - 1; i >= 0; i--) {
			b = a << i;
			if ((b & maxValueInt) != 0) {
				System.out.println(i + 1);
				return;
			}
		}
		System.out.println(1);
	}

}
