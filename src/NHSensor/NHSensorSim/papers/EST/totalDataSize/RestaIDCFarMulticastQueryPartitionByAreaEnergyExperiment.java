package NHSensor.NHSensorSim.papers.EST.totalDataSize;

import NHSensor.NHSensorSim.algorithm.RestaIDCFarMulticastQueryAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.EnergyIsSuccessResult;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.ui.Animator;

public class RestaIDCFarMulticastQueryPartitionByAreaEnergyExperiment extends
		AlgorithmExperiment {
	AllParam param;
	double consumedEnergy = 0;
	double packetFrameNum = 0;
	double packetFrameNumNeed = 0;
	double queryResultCorrectRate = 0;
	int itineraryNodeSize = 0;
	int queryNodeNum = 0;

	public RestaIDCFarMulticastQueryPartitionByAreaEnergyExperiment(
			AllParam param) {
		this.param = param;
		SensorSim sensorSim = SensorSim.createSensorSim(param);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		RestaIDCFarMulticastQueryAlg alg = (RestaIDCFarMulticastQueryAlg) sensorSim
				.generateAlgorithm(RestaIDCFarMulticastQueryAlg.NAME);
		alg.getParam().setQUERY_MESSAGE_SIZE(this.param.getQueryMessageSize());
		alg.getParam().setANSWER_SIZE(this.param.getAnswerMessageSize());

		if (this.param.getGridWidthRate() == 0) {
			alg.initSectorInRectByMaxAlpha();
		} else {
			alg.initSectorInRectByArea((int) this.param.getGridWidthRate());
		}
		this.setAlgorithm(alg);
	}

	public void run() {
		this.getAlgorithm().init();
		int times = 100, realTimes;
		int i;
		for (i = 0; i < times; i++) {
			this.getAlgorithm().run();

			this.consumedEnergy += this.getAlgorithm().getStatistics()
					.getConsumedEnergy();
			this.packetFrameNum += this.getAlgorithm().getStatistics()
					.getPacketFrameNum();
			this.packetFrameNumNeed += this.getAlgorithm().getStatistics()
					.getPacketFrameNumNeed();
			this.queryResultCorrectRate += this.getAlgorithm().getStatistics()
					.getQueryResultCorrectness().getResultCorrectRate();
			this.itineraryNodeSize += this.getAlgorithm()
					.getItineraryNodeSize();

			if (this.getAlgorithm().isaNodeHasNoEnergy()) {
				break;
			}
		}

		realTimes = i + 1;
		this.consumedEnergy /= realTimes;
		this.packetFrameNum /= realTimes;
		this.packetFrameNumNeed /= realTimes;
		// this.queryResultCorrectRate /= realTimes;
		this.itineraryNodeSize /= realTimes;
		this.queryNodeNum = realTimes;

		if (this.getAlgorithm().getStatistics().getQueryResultCorrectness()
				.getResultCorrectRate() == 0) {
			Animator animator = new Animator(this.getAlgorithm());
			animator.start();
		}
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
		sb.append("RestaIDCFarMulticastQueryPartitionByArea");
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
