package NHSensor.NHSensorSim.analyser;

import NHSensor.NHSensorSim.algorithm.Algorithm;

public abstract class AlgorithmAnalyser implements Analyser {
	protected Algorithm algorithm;

	public AlgorithmAnalyser(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public abstract void analyse();

	public abstract Object getResult();
}
