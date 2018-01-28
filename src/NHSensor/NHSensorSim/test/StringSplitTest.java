package NHSensor.NHSensorSim.test;

public class StringSplitTest {
	public static void main(String[] args) {
		String str = "2004-02-28 \t02:21:16.59372 167 5    2.69964";
		String[] s = str.split("\\s+");
		for (int i = 0; i < s.length; i++) {
			System.out.println(s[i]);
		}
	}
}
