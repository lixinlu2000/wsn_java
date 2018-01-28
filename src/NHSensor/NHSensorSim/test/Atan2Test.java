package NHSensor.NHSensorSim.test;

public class Atan2Test {

	public Atan2Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		System.out.println(Math.atan2(1, 1) * 180 / Math.PI);
		System.out.println(Math.atan2(1, -1) * 180 / Math.PI);
		System.out.println(Math.atan2(-1, -1) * 180 / Math.PI);
		System.out.println(Math.atan2(-1, 1) * 180 / Math.PI);
		System.out.println(Math.atan2(-1, 0) * 180 / Math.PI);

	}

}
