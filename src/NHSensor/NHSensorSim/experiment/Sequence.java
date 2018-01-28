package NHSensor.NHSensorSim.experiment;

public interface Sequence {
	public int getSize();

	public Object valueAt(int index);
}
