package NHSensor.NHSensorSim.papers.CSA;

import NHSensor.NHSensorSim.algorithm.IWQEIdealUseICFailureAwareForSuccessRateAlg;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.EnergyIsSuccessResult;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.failure.NodeFailureModelFactory;
import NHSensor.NHSensorSim.ui.IWQEAnimator;

public class IWQEIdealUseICEnergyExperimentHasFailNodes extends
		AlgorithmExperiment {
	AllParam param;
	double consumedEnergy = 0;
	double packetFrameNum = 0;
	double packetFrameNumNeed = 0;
	double queryResultCorrectRate = 0;
	int itineraryNodeSize = 0;
	int queryNodeNum = 0;
	double communicationTime = 0;
	double maintainLocationsFrameNum = 0; 

	public IWQEIdealUseICEnergyExperimentHasFailNodes(AllParam param) {
		this(param, false);
	}

	public IWQEIdealUseICEnergyExperimentHasFailNodes(AllParam param,
			boolean useTraversedNodeSumForSuccessRate) {
		GlobalConstants.getInstance().setOnDemandCollectNeighborLocations(true);

		this.param = param;
		SensorSim sensorSim = SensorSim.createSensorSim(param);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		IWQEIdealUseICFailureAwareForSuccessRateAlg alg = (IWQEIdealUseICFailureAwareForSuccessRateAlg) sensorSim
				.generateAlgorithm(IWQEIdealUseICFailureAwareForSuccessRateAlg.NAME);
		alg
				.setUseTraversedNodeSumForSuccessRate(useTraversedNodeSumForSuccessRate);
		alg.getParam().setQUERY_MESSAGE_SIZE(this.param.getQueryMessageSize());
		alg.getParam().setANSWER_SIZE(this.param.getAnswerMessageSize());
		alg.getParam().setINIT_ENERGY(Double.MAX_VALUE);
		alg.getParam().setFrameSize(1);

		Network network = alg.getNetwork();
		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(param.getNodeFailModelID(), param
						.getFailNodeNum()));

		double subQueryRegionWidth = this.getParam().getGridWidthRate()
				* alg.getParam().getRADIO_RANGE();
		// double subQueryRegionWidth = this.getParam().getGridWidthRate()*10;
		// double subQueryRegionWidth = 0.8*alg.getParam().getRADIO_RANGE();
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
		this.packetFrameNum = this.getAlgorithm().getStatistics()
				.getPacketFrameNum();
		this.packetFrameNumNeed = this.getAlgorithm().getStatistics()
				.getPacketFrameNumNeed();
		this.queryResultCorrectRate = this.getAlgorithm().getStatistics()
				.getQueryResultCorrectness().getResultCorrectRate();
		this.itineraryNodeSize = this.getAlgorithm().getItineraryNodeSize();
		this.queryNodeNum = ((IWQEIdealUseICFailureAwareForSuccessRateAlg) this
				.getAlgorithm()).getQueryNodeNum();
		this.communicationTime = this.getAlgorithm().getTotalCommunicationTime();
		this.maintainLocationsFrameNum = this.getAlgorithm().getStatistics().getMaintainLocationsFrameNum();
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
				this.queryNodeNum, this.communicationTime, this.maintainLocationsFrameNum);
	}

	public ExperimentParamResultPair getExperimentParamResultPair() {
		return new ExperimentParamResultPair(this.getParam(), this.getResult());
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("IWQE");
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
