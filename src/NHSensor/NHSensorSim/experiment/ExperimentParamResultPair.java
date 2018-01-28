package NHSensor.NHSensorSim.experiment;

public class ExperimentParamResultPair {
	private ExperimentParam param;
	private ExperimentResult result;

	public ExperimentParamResultPair(ExperimentParam param,
			ExperimentResult result) {
		this.param = param;
		this.result = result;
	}

	public ExperimentParam getParam() {
		return param;
	}

	public void setParam(ExperimentParam param) {
		this.param = param;
	}

	public ExperimentResult getResult() {
		return result;
	}

	public void setResult(ExperimentResult result) {
		this.result = result;
	}
}
