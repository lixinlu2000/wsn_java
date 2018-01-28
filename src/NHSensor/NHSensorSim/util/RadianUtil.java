package NHSensor.NHSensorSim.util;

public class RadianUtil {
	public static double sita(double d1, double d2, double r) {
		double cosSita = (d1 * d1 + d2 * d2 - r * r) / (2 * d1 * d2);
		double sita = Math.acos(cosSita);
		return Convertor.convertRadian(sita);
	}
}
