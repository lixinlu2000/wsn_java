package NHSensor.NHSensorSim.core;

public class QueryResultCorrectness {
	// traversed node which has query result
	double correctResultSize;
	// not traversed node which has query result
	double lossResultSize;
	// real query result node;
	double realResultSize;
	final double successRateThrehold = 0.95;

	public QueryResultCorrectness(double correctResultSize,
			double lossResultSize, double realResultSize) {
		super();
		this.correctResultSize = correctResultSize;
		this.lossResultSize = lossResultSize;
		this.realResultSize = realResultSize;
	}

	public double getCorrectResultSize() {
		return correctResultSize;
	}

	public void setCorrectResultSize(double correctResultSize) {
		this.correctResultSize = correctResultSize;
	}

	public double getLossResultSize() {
		return lossResultSize;
	}

	public void setLossResultSize(double lossResultSize) {
		this.lossResultSize = lossResultSize;
	}

	public double getRealResultSize() {
		return realResultSize;
	}

	public void setRealResultSize(double realResultSize) {
		this.realResultSize = realResultSize;
	}

	public double getResultCorrectRate() {
		return this.correctResultSize / this.realResultSize;
	}

	public double getTraversedQueryResultSize() {
		return this.realResultSize - this.lossResultSize;
	}

	public double getTraversedQueryResultRate() {
		return (this.realResultSize - this.lossResultSize)
				/ this.realResultSize;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CorrectResultSize:" + this.correctResultSize);
		sb.append(" LossResultSize:" + this.lossResultSize);
		sb.append(" RealResultSize:" + this.realResultSize);
		sb.append(" ResultCorrectRate:" + this.getResultCorrectRate());
		return sb.toString();
	}

	public boolean isQueryResultCorrect() {
		// return this.correctResultSize==this.realResultSize;
		return this.correctResultSize * 1.0 / this.realResultSize > successRateThrehold;
	}
	
	public boolean isQueryResultCorrect(double userSuccessRateThrehold) {
		// return this.correctResultSize==this.realResultSize;
		return this.correctResultSize * 1.0 / this.realResultSize >= userSuccessRateThrehold;
	}


	public boolean isQueryResultCorrectInTraversedResultNodeRate() {
		return this.getTraversedQueryResultRate() > successRateThrehold;
	}
}
