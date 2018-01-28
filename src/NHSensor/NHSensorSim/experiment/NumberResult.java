package NHSensor.NHSensorSim.experiment;

public class NumberResult implements ExperimentResult {
	private Number result;
	private boolean isSuccess;

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public NumberResult(Number result, boolean isSuccess) {
		this.result = result;
		this.isSuccess = isSuccess;
	}

	public Number getResult() {
		return result;
	}

	public void setResult(Number result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return true;
	}

	public int getIsSuccess() {
		if (this.isSuccess)
			return 1;
		else
			return 0;
	}
}
