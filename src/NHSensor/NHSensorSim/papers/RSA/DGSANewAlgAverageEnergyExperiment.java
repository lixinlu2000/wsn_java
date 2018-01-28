package NHSensor.NHSensorSim.papers.RSA;

import NHSensor.NHSensorSim.algorithm.DGSANewAlg;
import NHSensor.NHSensorSim.analyser.ItineraryNodeEventAnalyser;
import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.AllParam;
import NHSensor.NHSensorSim.experiment.EnergyIsSuccessResult;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.experiment.RobustnessResult;
import NHSensor.NHSensorSim.ui.DGSANewAnimator;

public class DGSANewAlgAverageEnergyExperiment extends AlgorithmExperiment {
	AllParam param;
	double consumedEnergy = 0;

	public DGSANewAlgAverageEnergyExperiment(AllParam param) {
		this.param = param;
		SensorSim sensorSim = SensorSim.createSensorSim(param);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		DGSANewAlg alg = (DGSANewAlg) sensorSim
				.generateAlgorithm(DGSANewAlg.NAME);
		ItineraryAlgParam algParam = new ItineraryAlgParam();
		algParam.setQuerySize(this.getParam().getQueryMessageSize());
		algParam.setQueryAndPatialAnswerSize(this.getParam()
				.getQueryAndPatialAnswerSize());
		algParam.setAnswerSize(this.getParam().getAnswerMessageSize());
		algParam.setResultSize(this.getParam().getResultSize());
		alg.setAlgParam(algParam);

		double subQueryRegionWidth = this.getParam().getGridWidthRate()
				* alg.getParam().getRADIO_RANGE();
		alg.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);
		this.setAlgorithm(alg);
	}

	public void run() {
		this.getAlgorithm().init();
		this.getAlgorithm().run();
		if (this.isShowAnimator()) {
			DGSANewAnimator animator = new DGSANewAnimator(this.getAlgorithm());
			animator.start();
		}

		this.consumedEnergy = this.getAlgorithm().getStatistics()
				.getConsumedEnergy();
		this.isSuccess = this.getAlgorithm().getStatistics().isSuccess();

		ItineraryNodeEventAnalyser analyser = new ItineraryNodeEventAnalyser(
				this.getAlgorithm(), this.getParam().getNodeFailProbability());
		analyser.analyse();
		RobustnessResult robustnessResult = (RobustnessResult) analyser
				.getResult();
		this.consumedEnergy /= robustnessResult.getSuccessProbability();
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
