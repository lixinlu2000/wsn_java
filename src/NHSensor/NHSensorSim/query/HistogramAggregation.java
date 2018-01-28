package NHSensor.NHSensorSim.query;

import java.util.Vector;

public class HistogramAggregation implements AggregationOperator {

	public Object evaluate(Object attribteValue) {
		return attribteValue;
	}

	public Object init(Object attribteValue) {
		return attribteValue;
	}

	public Object merge(Object attribteValue, Vector childValues) {
		return new Double(1 + childValues.size());
	}
}
