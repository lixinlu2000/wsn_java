package NHSensor.NHSensorSim.papers.RESALifeTime;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.IWQEIdealUseICForRESACompareAlg;
import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.EnergyIsSuccessResult;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.link.LinkModel;
import NHSensor.NHSensorSim.link.ModelLinkEstimator;
import NHSensor.NHSensorSim.ui.Animator;

public class IWQEIdealUseICEnergyExperiment extends AlgorithmExperiment {
	AllParam param;
	double consumedEnergy = 0;
	double packetFrameNum = 0;
	double packetFrameNumNeed = 0;
	double queryResultCorrectRate = 0;
	int itineraryNodeSize = 0;
	int queryNodeNum;
	boolean useAlgorithmRadioRangeToCalEnergy = true;

	public IWQEIdealUseICEnergyExperiment(AllParam param) {
		this(param, false);
	}

	public IWQEIdealUseICEnergyExperiment(AllParam param,
			boolean useTraversedNodeSumForSuccessRate) {
		this.param = param;
		SensorSim sensorSim = SensorSim.createSensorSim(param);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		IWQEIdealUseICForRESACompareAlg alg = (IWQEIdealUseICForRESACompareAlg) sensorSim
				.generateAlgorithm(IWQEIdealUseICForRESACompareAlg.NAME);
		alg
				.setUseTraversedNodeSumForSuccessRate(useTraversedNodeSumForSuccessRate);
		alg.setRadioBroadcastNature(false);
		alg.getParam().setQUERY_MESSAGE_SIZE(this.param.getQueryMessageSize());
		alg.getParam().setANSWER_SIZE(this.param.getAnswerMessageSize());
		alg.getParam().setFrameSize(this.param.getFrameLength());
		alg.getParam().setPreambleLength(this.param.getPreambleLength());
		
		//NOTE
		alg.setUseAlgorithmRadioRangeToCalEnergy(useAlgorithmRadioRangeToCalEnergy);
		alg.setAlgorithmRadioRange(25);

		LinkModel linkModel = param.createLinkModel();
		Network network = alg.getNetwork();
		alg.getNetwork().setLinkEstimator(
				new ModelLinkEstimator(linkModel, network)
						.createArrayLinkEstimator(param.getLinkID()));
		alg.setConsiderLinkQuality(true);

		double subQueryRegionWidth = this.getParam().getGridWidthRate()
				* alg.getParam().getRADIO_RANGE();
		// double subQueryRegionWidth = this.getParam().getGridWidthRate()*10;
		// double subQueryRegionWidth = 0.8*alg.getParam().getRADIO_RANGE();
		alg.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);
		this.setAlgorithm(alg);
	}
	
	protected void reInitInitialEnergy() {
		if(this.param.getInitialEnergyDelta()==0)
			return;
		
		Vector nodes = this.getAlgorithm().getNetwork().getNodes();
		Node node;
		Attachment na;
		double initialEnergy = this.param.getInitialEnergy();
		double delta;
		Random r = new Random(0);

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();			
			na = (Attachment)node.getAttachment(this.getAlgorithm().getName());
			delta = (r.nextDouble()-0.5)*2*this.param.getInitialEnergyDelta();
			na.setEnergy(initialEnergy+delta);
		}
	}

	public void run() {
		this.getAlgorithm().init();
		this.reInitInitialEnergy();
		
		int times = 100000, realTimes = 0;
		int i;
		for (i = 0; i < times; i++) {
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

		if(realTimes==0)this.isSuccess = false;
		else this.isSuccess = true;
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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

}
