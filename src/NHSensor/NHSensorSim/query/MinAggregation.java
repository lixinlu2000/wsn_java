package NHSensor.NHSensorSim.query;

import java.util.Vector;

public class MinAggregation implements AggregationOperator {

	public Object evaluate(Object attribteValue) {
		return attribteValue;
	}

	public Object init(Object attribteValue) {
		return attribteValue;
	}

	public Object merge(Object attribteValue, Vector childValues) {
		double minValue = ((Number) attribteValue).doubleValue();
		double childValue;

		for (int i = 0; i < childValues.size(); i++) {
			childValue = ((Number) childValues.elementAt(i)).doubleValue();
			if (childValue < minValue)
				minValue = childValue;
		}
		return new Double(minValue);
	}
}
