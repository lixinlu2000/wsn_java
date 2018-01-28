package NHSensor.NHSensorSim.util;

public class NHSensorSimClass {
	NHSensorSimPackage nHSensorSimPackage;
	String className;

	public NHSensorSimClass(NHSensorSimPackage nHSensorSimPackage,
			String className) {
		this.nHSensorSimPackage = nHSensorSimPackage;
		this.className = className;
	}

	public NHSensorSimPackage getNHSensorSimPackage() {
		return nHSensorSimPackage;
	}

	public void setNHSensorSimPackage(NHSensorSimPackage sensorSimPackage) {
		nHSensorSimPackage = sensorSimPackage;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(this.getNHSensorSimPackage().getName());
		sb.append(".");
		sb.append(this.getClassName());
		return sb.toString();
	}
}
