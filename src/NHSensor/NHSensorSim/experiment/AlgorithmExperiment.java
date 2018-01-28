package NHSensor.NHSensorSim.experiment;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.query.QueryBase;

public abstract class AlgorithmExperiment extends Experiment {
	private Algorithm algorithm;
	protected boolean isSuccess;
	
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public AlgorithmExperiment() {
	}

	public AlgorithmExperiment(String description) {
		super(description);
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}
	
	public Network getNetwork() {
		return this.getAlgorithm().getNetwork();
	}

	public Simulator getSimulator() {
		return this.getAlgorithm().getSimulator();
	}

	public Param getAlgParam() {
		return this.getAlgorithm().getParam();
	}

	public QueryBase getQuery() {
		return this.getAlgorithm().getQuery();
	}

	public abstract ExperimentParamResultPair getExperimentParamResultPair();
	public abstract void run();

}
