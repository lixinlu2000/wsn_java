package NHSensor.NHSensorSim.query;

import java.util.Vector;

public class SumAggregation implements AggregationOperator {

	public Object evaluate(Object attribteValue) {
		return attribteValue;
	}

	public Object init(Object attribteValue) {
		return attribteValue;
	}

	public Object merge(Object attribteValue, Vector childValues) {
		double sumValue = ((Number) attribteValue).doubleValue();
		double childValue;

		for (int i = 0; i < childValues.size(); i++) {
			childValue = ((Number) childValues.elementAt(i)).doubleValue();
			sumValue += childValue;
		}
		return new Double(sumValue);
	}
}
