package NHSensor.NHSensorSim.analyser;

import NHSensor.NHSensorSim.algorithm.Algorithm;

public class AlgorithmSuccessAnalyser extends AlgorithmAnalyser {
	private boolean isSuccess;

	public AlgorithmSuccessAnalyser(Algorithm algorithm) {
		super(algorithm);
	}

	public void analyse() {
		this.isSuccess = this.getAlgorithm().getStatistics().isSuccess();
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public Object getResult() {
		return new Boolean(this.isSuccess);
	}

}
