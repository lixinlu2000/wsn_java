package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class QueryResultEvent extends Event {
	Object queryResult;

	public QueryResultEvent(Algorithm alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	public QueryResultEvent(Algorithm alg, int tag) {
		super(alg, tag);
		// TODO Auto-generated constructor stub
	}

	public QueryResultEvent(Algorithm alg, Object queryResult) {
		super(alg);
		this.queryResult = queryResult;
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub

	}

	public Object getQueryResult() {
		return queryResult;
	}

	public void setQueryResult(Object queryResult) {
		this.queryResult = queryResult;
	}

	public String toString() {
		return "QueryResultEvent " + this.queryResult.toString();
	}
}
