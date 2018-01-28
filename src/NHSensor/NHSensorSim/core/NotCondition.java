package NHSensor.NHSensorSim.core;

public class NotCondition implements Condition {
	private Condition condition;

	public NotCondition(Condition condition) {
		this.condition = condition;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public boolean isTrue(Object object) {
		return !this.getCondition().isTrue(object);
	}

}
