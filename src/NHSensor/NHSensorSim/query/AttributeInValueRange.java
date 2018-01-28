package NHSensor.NHSensorSim.query;

public class AttributeInValueRange {
	Attribute attribute;
	ValueRange valueRange;

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public ValueRange getValueRange() {
		return valueRange;
	}

	public void setValueRange(ValueRange valueRange) {
		this.valueRange = valueRange;
	}

	public AttributeInValueRange(Attribute attribute, ValueRange valueRange) {
		this.attribute = attribute;
		this.valueRange = valueRange;
	}
}
