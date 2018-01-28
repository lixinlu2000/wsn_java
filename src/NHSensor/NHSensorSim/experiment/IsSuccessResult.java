package NHSensor.NHSensorSim.experiment;

public class IsSuccessResult implements ExperimentResult {
	protected boolean isSuccess = false;

	public IsSuccessResult(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public int getIsSuccess() {
		if (this.isSuccess)
			return 1;
		else
			return 0;
	}
}
