package NHSensor.NHSensorSim.papers.GKNN;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.IKNNAlg;
import NHSensor.NHSensorSim.analyser.ItineraryNodeEventAnalyser;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.AllParam;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.experiment.RobustnessResult;
import NHSensor.NHSensorSim.ui.Animator;

public class IKNNAlgRobustnessExperiment extends AlgorithmExperiment {
	private AllParam param;
	private RobustnessResult robustnessResult;

	public IKNNAlgRobustnessExperiment(AllParam param) {
		this.param = param;
		SensorSim sensorSim = SensorSim.createKNNSensorSim(param);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		IKNNAlg alg = (IKNNAlg) sensorSim.generateAlgorithm(IKNNAlg.NAME);
		this.setAlgorithm(alg);
	}

	public AllParam getParam() {
		return param;
	}

	public void setParam(AllParam param) {
		this.param = param;
	}

	public void run() {
		this.getAlgorithm().init();
		this.getAlgorithm().run();
		if (this.isShowAnimator()) {
			Animator animator = new Animator(this.getAlgorithm());
			animator.start();
		}

		ItineraryNodeEventAnalyser analyser = new ItineraryNodeEventAnalyser(
				this.getAlgorithm(), this.getParam().getNodeFailProbability());
		analyser.analyse();
		this.robustnessResult = (RobustnessResult) analyser.getResult();
	}

	public ExperimentResult getResult() {
		return this.robustnessResult;
	}

	public ExperimentParamResultPair getExperimentParamResultPair() {
		return new ExperimentParamResultPair(this.getParam(), this.getResult());
	}

	public double getAverageBackupNodeCount() {
		return robustnessResult.getAverageBackupNodeCount();
	}

	public Vector getBackupNodeCount() {
		return robustnessResult.getBackupNodeCount();
	}

	public int getItineraryNodeCount() {
		return robustnessResult.getItineraryNodeCount();
	}

	public double getSuccessProbability() {
		return robustnessResult.getSuccessProbability();
	}

	public boolean isSuccess() {
		return robustnessResult.isSuccess();
	}

}
