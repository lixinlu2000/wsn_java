package NHSensor.NHSensorSim.core;

public class Time {
	public static Time current = new Time(0);
	public double time;

	public Time(double time) {
		this.time = time;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public void advance(double a) {
		this.time += a;
	}

	public static Time getCurrentTime() {
		return Time.current;
	}

	public static void tick(double time) {
		Time.getCurrentTime().advance(time);
	}
}
