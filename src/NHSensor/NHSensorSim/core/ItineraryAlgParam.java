package NHSensor.NHSensorSim.core;

public class ItineraryAlgParam extends AlgParam {
	private int querySize;
	private int answerSize;
	private int queryAndPatialAnswerSize;
	private int resultSize;

	public ItineraryAlgParam(int sensorDataSize) {
		this.querySize = 20;
		this.answerSize = sensorDataSize;
		this.queryAndPatialAnswerSize = this.answerSize + this.querySize;
		this.resultSize = sensorDataSize;
	}

	public ItineraryAlgParam() {

	}

	public ItineraryAlgParam(int sensorDataSize, int querySize) {
		this.querySize = querySize;
		this.answerSize = sensorDataSize;
		this.queryAndPatialAnswerSize = this.answerSize + querySize;
		this.resultSize = sensorDataSize;
	}

	public int getAnswerSize() {
		return answerSize;
	}

	public void setAnswerSize(int answerSize) {
		this.answerSize = answerSize;
	}

	public int getQueryAndPatialAnswerSize() {
		return queryAndPatialAnswerSize;
	}

	public void setQueryAndPatialAnswerSize(int queryAndPatialAnswerSize) {
		this.queryAndPatialAnswerSize = queryAndPatialAnswerSize;
	}

	public int getQuerySize() {
		return querySize;
	}

	public void setQuerySize(int querySize) {
		this.querySize = querySize;
	}

	public int getResultSize() {
		return resultSize;
	}

	public void setResultSize(int resultSize) {
		this.resultSize = resultSize;
	}
}
