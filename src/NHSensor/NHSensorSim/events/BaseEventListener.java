package NHSensor.NHSensorSim.events;

public abstract class BaseEventListener implements SensornetEventListener {
	private String name;

	public BaseEventListener(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
