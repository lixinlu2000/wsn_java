package NHSensor.NHSensorSim.experiment;

import NHSensor.NHSensorSim.algorithm.IWQEAlg;
import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.IWQEAnimator;

public class IWQEAlgEnergyExperiment extends AlgorithmExperiment {
	AllParam param;
	double consumedEnergy = 0;
	boolean adjustSubQueryRegionWidthRate = true;

	public boolean isAdjustSubQueryRegionWidthRate() {
		return adjustSubQueryRegionWidthRate;
	}

	public void setAdjustSubQueryRegionWidthRate(
			boolean adjustSubQueryRegionWidthRate) {
		this.adjustSubQueryRegionWidthRate = adjustSubQueryRegionWidthRate;
	}

	public IWQEAlgEnergyExperiment(AllParam param,
			boolean adjustSubQueryRegionWidthRate) {
		this.param = param;
		this.adjustSubQueryRegionWidthRate = adjustSubQueryRegionWidthRate;
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

		double subQueryRegionWidthRate;
		double subQueryRegionWidth;

		if (this.isAdjustSubQueryRegionWidthRate()) {
			subQueryRegionWidthRate = param.getGridWidthRate();
			if (subQueryRegionWidthRate > Constants.IWQE_GRID_WIDTH_RATE)
				subQueryRegionWidthRate = Constants.IWQE_GRID_WIDTH_RATE;
		} else {
			subQueryRegionWidthRate = Constants.IWQE_GRID_WIDTH_RATE;
		}
		subQueryRegionWidth = subQueryRegionWidthRate
				* alg.getParam().getRADIO_RANGE();
		alg.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);
		this.setAlgorithm(alg);
	}

	public IWQEAlgEnergyExperiment(AllParam param) {
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

		double subQueryRegionWidthRate;
		double subQueryRegionWidth;

		subQueryRegionWidthRate = Constants.IWQE_GRID_WIDTH_RATE;
		subQueryRegionWidth = subQueryRegionWidthRate
				* alg.getParam().getRADIO_RANGE();
		alg.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);
		this.setAlgorithm(alg);
	}

	public void run() {
		this.getAlgorithm().init();
		this.getAlgorithm().run();
		if (this.isShowAnimator()) {
			IWQEAnimator animator = new IWQEAnimator(this.getAlgorithm());
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
