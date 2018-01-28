package NHSensor.NHSensorSim.core;

public class OrCondition implements Condition {
	private Condition left;
	private Condition right;

	public OrCondition(Condition left, Condition right) {
		this.left = left;
		this.right = right;
	}

	public Condition getLeft() {
		return left;
	}

	public void setLeft(Condition left) {
		this.left = left;
	}

	public Condition getRight() {
		return right;
	}

	public void setRight(Condition right) {
		this.right = right;
	}

	public boolean isTrue(Object object) {
		if (object == null)
			return false;
		return this.getLeft().isTrue(object) || this.getRight().isTrue(object);
	}

}
