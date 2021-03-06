package NHSensor.NHSensorSim.papers.RSA;

import NHSensor.NHSensorSim.algorithm.DGSANewAlg;
import NHSensor.NHSensorSim.analyser.DrawRectEventAnalyser;
import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.AllParam;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.experiment.NumberResult;
import NHSensor.NHSensorSim.ui.DGSANewAnimator;

public class DGSANewAlgAverageGridAreaExperiment extends AlgorithmExperiment {
	private AllParam param;
	private double averageGridArea;

	public DGSANewAlgAverageGridAreaExperiment(AllParam param) {
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
			DGSANewAnimator animator = new DGSANewAnimator(this.getAlgorithm());
			animator.start();
		}

		DrawRectEventAnalyser drawRectEventAnalyser = new DrawRectEventAnalyser(
				this.getAlgorithm());
		drawRectEventAnalyser.analyse();
		this.averageGridArea = drawRectEventAnalyser.getAverageArea();
		this.isSuccess = this.getAlgorithm().getStatistics().isSuccess();
	}

	public ExperimentResult getResult() {
		// TODO Auto-generated method stub
		return new NumberResult(new Double(this.averageGridArea),
				this.isSuccess);
	}

	public ExperimentParamResultPair getExperimentParamResultPair() {
		return new ExperimentParamResultPair(this.getParam(), this.getResult());
	}

	public double getAverageGridArea() {
		return averageGridArea;
	}

}
