package NHSensor.NHSensorSim.papers.PEVA;

import NHSensor.NHSensorSim.algorithm.PEVAAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.dataset.DataSet;
import NHSensor.NHSensorSim.dataset.UniformDataSet;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.EnergyIsSuccessResult;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.ui.Animator;
import NHSensor.NHSensorSim.util.Util;

public class PEVAEnergyExperiment extends AlgorithmExperiment {
	AllParam param;
	double consumedEnergy = 0;
	double packetFrameNum = 0;
	double packetFrameNumNeed = 0;
	double queryResultCorrectRate = 0;
	int itineraryNodeSize = 0;
	int queryNodeNum = 0;

	public PEVAEnergyExperiment(AllParam param) {
		this.param = param;
		SensorSim sensorSim = SensorSim.createSensorSim(param);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		PEVAAlg alg = (PEVAAlg) sensorSim.generateAlgorithm(PEVAAlg.NAME);
		alg.getParam().setQUERY_MESSAGE_SIZE(this.param.getQueryMessageSize());
		alg.getParam().setANSWER_SIZE(
				Util.byteNum(this.param.getAnswerMessageSize()));
		alg.setDataSize(this.param.getAnswerMessageSize());
		DataSet dataset = new UniformDataSet(Math.pow(2, this.param
				.getAnswerMessageSize()) - 1, 1);
		alg.setDataset(dataset);
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
		this.packetFrameNum = this.getAlgorithm().getStatistics()
				.getPacketFrameNum();
		this.packetFrameNumNeed = this.getAlgorithm().getStatistics()
				.getPacketFrameNumNeed();
		this.queryResultCorrectRate = this.getAlgorithm().getStatistics()
				.getQueryResultCorrectness().getResultCorrectRate();
		this.itineraryNodeSize = this.getAlgorithm().getItineraryNodeSize();
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
		return new EnergyIsSuccessResult(this.consumedEnergy, this.isSuccess,
				this.packetFrameNum, this.packetFrameNumNeed,
				this.queryResultCorrectRate, this.itineraryNodeSize,
				this.queryNodeNum);
	}

	public ExperimentParamResultPair getExperimentParamResultPair() {
		return new ExperimentParamResultPair(this.getParam(), this.getResult());
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("PEVAAlg");
		sb.append(" isSuccess(");
		sb.append(this.isSuccess);
		sb.append(")");
		sb.append(" consumedEnergy(");
		sb.append(this.consumedEnergy);
		sb.append(")");
		sb.append(" queryResultCorrectRate(");
		sb.append(this.queryResultCorrectRate);
		sb.append(")");
		sb.append(" queryNodeNum(");
		sb.append(this.queryNodeNum);
		sb.append(")");
		sb.append(" itineraryNodeSize(");
		sb.append(this.itineraryNodeSize);
		sb.append(")");
		sb.append(" packetFrameNum(");
		sb.append(this.packetFrameNum);
		sb.append(")");
		sb.append(" packetFrameNumNeed(");
		sb.append(this.packetFrameNumNeed);
		sb.append(")");

		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
