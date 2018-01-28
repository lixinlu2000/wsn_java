package NHSensor.NHSensorSim.query;

import java.util.Hashtable;
import java.util.Vector;

public class AttributesInValueRanges {
	Vector attributes;
	Hashtable valueRanges;

	public AttributesInValueRanges() {
		attributes = new Vector();
		valueRanges = new Hashtable();
	}

	public void add(Attribute attribute, ValueRange valueRange) {
		this.attributes.add(attribute);
		this.valueRanges.put(attribute, valueRange);
	}

	public int getAttributeSize() {
		return this.attributes.size();
	}
}
