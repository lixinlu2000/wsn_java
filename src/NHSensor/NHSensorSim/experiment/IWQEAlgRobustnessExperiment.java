package NHSensor.NHSensorSim.experiment;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.IWQEAlg;
import NHSensor.NHSensorSim.analyser.ItineraryNodeEventAnalyser;
import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.IWQEAnimator;

public class IWQEAlgRobustnessExperiment extends AlgorithmExperiment {
	private AllParam param;
	private RobustnessResult robustnessResult;

	public IWQEAlgRobustnessExperiment(AllParam param) {
		this.param = param;
		SensorSim sensorSim = SensorSim.createSensorSim(param);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		IWQEAlg alg = (IWQEAlg) sensorSim.generateAlgorithm(IWQEAlg.NAME);
		ItineraryAlgParam algParam = new ItineraryAlgParam();
		algParam.setQuerySize(this.getParam().getQueryMessageSize());
		algParam.setQueryAndPatialAnswerSize(this.getParam()
				.getQueryAndPatialAnswerSize());
		algParam.setAnswerSize(this.getParam().getAnswerMessageSize());
		algParam.setResultSize(this.getParam().getResultSize());
		alg.setAlgParam(algParam);

		double subQueryRegionWidth = Constants.IWQE_GRID_WIDTH_RATE
				* alg.getParam().getRADIO_RANGE();
		alg.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);
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
			IWQEAnimator animator = new IWQEAnimator(this.getAlgorithm());
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
