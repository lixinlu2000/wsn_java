package NHSensor.NHSensorSim.shape;

import NHSensor.NHSensorSim.util.Convertor;

public class Radian {
	private double radian;
	private static final double MINOR = 0.5;

	public Radian(double r) {
		this.radian = Convertor.convertRadian(r);
	}

	public double getDegree() {
		return Convertor.radianToDegree(radian);
	}

	public static double getDegree(double radian) {
		return Convertor.radianToDegree(radian);
	}

	public double getRadian() {
		return radian;
	}

	public boolean isVertical() {
		double degree = this.getDegree();
		if (Math.abs(degree - 90) <= MINOR || Math.abs(degree - 270) <= MINOR)
			return true;
		else
			return false;
	}

	public void setRadian(double r) {
		this.radian = Convertor.convertRadian(r);
	}

	public double relativeTo(double baseRadian) {
		return Convertor.convertRadian(this.getRadian() - baseRadian);
	}

	public double relativeTo(Radian baseRadian) {
		return Convertor.convertRadian(this.getRadian()
				- baseRadian.getRadian());
	}

	public static double relativeTo(double radian, double baseRadian) {
		return Convertor.convertRadian(radian - baseRadian);
	}
}
