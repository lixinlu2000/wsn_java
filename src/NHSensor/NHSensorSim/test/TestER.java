package NHSensor.NHSensorSim.test;

public class TestER {
	public static void main(String[] args) {
		double r = 30 * Math.sqrt(3);
		double networkSize = 450;
		/*
		 * double d = 1500.0/(450450); double h = TestER.caculateEAdv(r, d);
		 * double w = TestER.caculateWidth(r, h);
		 */

		for (double nodeNum = 250; nodeNum <= 3000; nodeNum += 250) {
			double d = nodeNum / (networkSize * networkSize);
			double h = TestER.caculateEAdv(r, d);
			double w = TestER.caculateWidth(r, h);
			System.out.println(r + "\t" + nodeNum + "\t" + d + "\t" + h + "\t"
					+ w + "\t" + w / r);
		}
	}

	public static double caculateEAdv(double r, double d) {
		return r * r * Math.sqrt(d) / (1 + r * Math.sqrt(d));
	}

	public static double caculateWidth(double r, double h) {
		return Math.sqrt(r * r - h * h);
	}
}
