package NHSensor.NHSensorSim.papers.GKNN;

import NHSensor.NHSensorSim.algorithm.GKNNUseGridTraverseRingEventAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.AllParam;
import NHSensor.NHSensorSim.experiment.EnergyIsSuccessResult;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.ui.Animator;

public class GKNNAlgEnergyExperiment extends AlgorithmExperiment {
	AllParam param;
	double consumedEnergy = 0;

	public GKNNAlgEnergyExperiment(AllParam param) {
		this.param = param;
		SensorSim sensorSim = SensorSim.createKNNSensorSim(param);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		GKNNUseGridTraverseRingEventAlg alg = (GKNNUseGridTraverseRingEventAlg) sensorSim
				.generateAlgorithm(GKNNUseGridTraverseRingEventAlg.NAME);
		this.setAlgorithm(alg);
	}

	public void run() {
		this.getAlgorithm().init();
		this.getAlgorithm().run();
		if (this.isShowAnimator()) {
			Animator animator = new Animator(this.getAlgorithm());
			animator.start();
		}
		this.consumedEnergy = this.getAlgorithm().getStatistics()
				.getConsumedEnergy();
		this.isSuccess = this.getAlgorithm().getStatistics().isSuccess();
	}

	public double getConsumedEnergy() {
		return consumedEnergy;
	}

	public AllParam getParam() {
		return param;
	}

	public void setParam(AllParam param) {
		this.param = param;
	}

	public ExperimentResult getResult() {
		return new EnergyIsSuccessResult(this.consumedEnergy, this.isSuccess);
	}

	public ExperimentParamResultPair getExperimentParamResultPair() {
		return new ExperimentParamResultPair(this.getParam(), this.getResult());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
