package cn.org1.user1;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.ESAUseGBAFailureAwareFinalAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.EnergyIsSuccessResult;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.query.Query;
import NHSensor.NHSensorSim.shape.Rect;
import NHSensor.NHSensorSim.ui.Animator;

public class ThirdPartyAlg1EnergyExperiment extends AlgorithmExperiment {
	AlgParam param;
	double consumedEnergy = 0;
	double packetFrameNum = 0;
	double packetFrameNumNeed = 0;
	double queryResultCorrectRate = 0;
	int itineraryNodeSize = 0;
	int queryNodeNum = 0;

	public ThirdPartyAlg1EnergyExperiment(AlgParam param) throws Exception {
		this.param = param;		
		

		Class algClass = Class.forName(ThirdPartyAlg1.class.getCanonicalName());
		Algorithm alg = (Algorithm)algClass.newInstance();
		alg.setName(ThirdPartyAlg1.class.getCanonicalName());
		
		double width = param.getNetworkWidth();
		double height = param.getNetworkHeight();
		int networkID = param.getNetworkID();
		int nodeNum = param.getNodeNum();
		double radioRange = param.getRadioRange();
		
		double queryRegionRate = param.getQueryRegionRate();
		double initialEnergy = param.getInitialEnergy();
		
		Network network = Network.createRandomNetworkHasAMiddleTopNode(width,
				height, nodeNum, networkID);
		
		Node orig = network.get2LRTNode();
		double sqrtQueryRegionRate = Math.sqrt(queryRegionRate);
		double queryWidth = width*sqrtQueryRegionRate;
		double queryHeight = height*sqrtQueryRegionRate;
		Rect queryRect = new Rect((width - queryWidth) / 2,
				(width + queryWidth) / 2, (height - queryHeight) / 2,
				(height + queryHeight) / 2);
		Query query = new Query(orig, queryRect);

		Simulator simulator = new Simulator();
		simulator.addHandleAndTraceEventListener();
		Param simParam = new Param(radioRange);
		simParam.setINIT_ENERGY(initialEnergy);
		Statistics statistics = new Statistics(ThirdPartyAlg1.class.getCanonicalName());

		alg.setNetwork(network);
		alg.setQuery(query);
		alg.setSimulator(simulator);
		alg.setParam(simParam);
		alg.setStatistics(statistics);

		alg.getParam().setQUERY_MESSAGE_SIZE(param.getQueryMessageSize());
		alg.getParam().setANSWER_SIZE(param.getAnswerMessageSize());
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


	public ExperimentResult getResult() {
		return new EnergyIsSuccessResult(this.consumedEnergy, this.isSuccess,
				this.packetFrameNum, this.packetFrameNumNeed,
				this.queryResultCorrectRate, this.itineraryNodeSize,
				this.queryNodeNum);
	}

	public AlgParam getParam() {
		return param;
	}

	public void setParam(AlgParam param) {
		this.param = param;
	}

	public ExperimentParamResultPair getExperimentParamResultPair() {
		return new ExperimentParamResultPair(this.getParam(), this.getResult());
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("FullFlood");
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
