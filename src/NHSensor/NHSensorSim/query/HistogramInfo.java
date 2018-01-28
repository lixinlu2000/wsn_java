package NHSensor.NHSensorSim.query;

import java.util.Hashtable;
import java.util.Vector;

public class HistogramInfo {
	Attribute attribute;
	Vector valueRanges = new Vector();
	Hashtable values = new Hashtable();

	public HistogramInfo(Attribute attribute) {
		super();
		this.attribute = attribute;
	}

	public String getAttributeName() {
		return attribute.getName();
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public Vector getValueRanges() {
		return valueRanges;
	}

	public double getValueRangeValue(ValueRange valueRange) {
		Double value = (Double) this.values.get(valueRange);
		if (value == null)
			return 0;
		else
			return value.doubleValue();
	}

	public void setValueRangeValue(ValueRange valueRange, double value) {
		this.values.put(valueRange, new Double(value));
	}

	public void setValueRanges(Vector valueRanges) {
		this.valueRanges = valueRanges;
	}

	public HistogramInfo(Attribute attribute, Vector valueRanges) {
		super();
		this.attribute = attribute;
		this.valueRanges = valueRanges;
	}

	public boolean addValueRange(ValueRange valueRange) {
		return valueRanges.add(valueRange);
	}

	// TODO this is a simple version that should be extended
	public boolean equalValues(HistogramInfo histogramInfo) {
		if (histogramInfo == null)
			return false;
		ValueRange valueRange;
		double thisValue;
		double otherValue;

		for (int i = 0; i < this.valueRanges.size(); i++) {
			valueRange = (ValueRange) this.valueRanges.elementAt(i);
			thisValue = this.getValueRangeValue(valueRange);
			otherValue = histogramInfo.getValueRangeValue(valueRange);
			if (thisValue != otherValue)
				return false;
		}
		return true;
	}

	public boolean addValue(double value) {
		ValueRange valueRange;
		Double oldValue, newValue;

		for (int i = 0; i < this.valueRanges.size(); i++) {
			valueRange = (ValueRange) this.valueRanges.elementAt(i);
			if (valueRange.contains(value)) {
				oldValue = (Double) this.values.get(valueRange);
				if (oldValue != null) {
					newValue = new Double(oldValue.doubleValue() + 1);
				} else {
					newValue = new Double(1);
				}
				this.values.put(valueRange, newValue);
				return true;
			}
		}
		return false;
	}

	public Object clone() {
		/*
		 * Attribute clonedAttribute = this.getAttribute().clone(); Vector
		 * clonedValueRanges = new Vector();
		 * 
		 * ValueRange valueRange; for(int i=0;i<this.valueRanges.size();i++) {
		 * valueRange = (ValueRange)this.valueRanges.elementAt(i);
		 * clonedValueRanges.add(valueRange.clone()); } return new
		 * HistogramInfo(clonedAttribute,clonedValueRanges);
		 */
		return new HistogramInfo(this.attribute, this.valueRanges);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("HistogramInfo(");
		sb.append("(" + this.attribute.getName() + ") {");

		ValueRange valueRange;
		double value;
		for (int i = 0; i < this.valueRanges.size(); i++) {
			valueRange = (ValueRange) this.valueRanges.elementAt(i);
			value = this.getValueRangeValue(valueRange);
			sb.append(valueRange + "->" + value);
			if (i != this.valueRanges.size() - 1) {
				sb.append(";");
			}
		}
		sb.append("})");
		return sb.toString();
	}

}
