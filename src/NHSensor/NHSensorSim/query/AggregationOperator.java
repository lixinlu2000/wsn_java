package NHSensor.NHSensorSim.query;

import java.util.Vector;

public interface AggregationOperator {
	public Object init(Object attribteValue);

	public Object merge(Object attribteValue, Vector childValues);

	public Object evaluate(Object attribteValue);
}
