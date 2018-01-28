package NHSensor.NHSensorSim.papers.E2STA.lifetime;

import NHSensor.NHSensorSim.algorithm.SWinFloodAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.EnergyIsSuccessResult;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.ui.Animator;

public class SWinFloodEnergyExperiment extends AlgorithmExperiment {
	AllParam param;
	double consumedEnergy = 0;
	double packetFrameNum = 0;
	double packetFrameNumNeed = 0;
	double queryResultCorrectRate = 0;
	int itineraryNodeSize = 0;
	int queryNodeNum = 0;

	public SWinFloodEnergyExperiment(AllParam param) {
		this.param = param;
		SensorSim sensorSim = SensorSim.createSensorSim(param);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		SWinFloodAlg alg = (SWinFloodAlg) sensorSim
				.generateAlgorithm(SWinFloodAlg.NAME);
		alg.getParam().setQUERY_MESSAGE_SIZE(this.param.getQueryMessageSize());
		alg.getParam().setANSWER_SIZE(this.param.getAnswerMessageSize());
		this.setAlgorithm(alg);
	}

	public void run() {
		this.getAlgorithm().init();
		int times = 100000, realTimes = 0;
		int i;
		for (i = 0; i < times; i++) {
			this.getAlgorithm().getSimulator().clear();
			this.getAlgorithm().run();

			if (this.getAlgorithm().isaNodeHasNoEnergy()) {
				break;
			}

			if (this.getAlgorithm().getStatistics().isSuccess()) {
				this.consumedEnergy += this.getAlgorithm().getStatistics()
						.getConsumedEnergy();
				this.packetFrameNum += this.getAlgorithm().getStatistics()
						.getPacketFrameNum();
				this.packetFrameNumNeed += this.getAlgorithm().getStatistics()
						.getPacketFrameNumNeed();
				this.queryResultCorrectRate += this.getAlgorithm()
						.getStatistics().getQueryResultCorrectness()
						.getResultCorrectRate();
				this.itineraryNodeSize += this.getAlgorithm()
						.getItineraryNodeSize();
				realTimes++;
			} else {
				break;
			}
		}

		if (realTimes > 0) {
			this.consumedEnergy /= realTimes;
			this.packetFrameNum /= realTimes;
			this.packetFrameNumNeed /= realTimes;
			this.queryResultCorrectRate /= realTimes;
			this.itineraryNodeSize /= realTimes;
			this.queryNodeNum = realTimes;
		}

		if (this.isShowAnimator()) {
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
		sb.append("SWinFlood");
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
