package NHSensor.NHSensorSim.papers.LKNN;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.EDCGridTraverseRingEventUseIteratorLinkAware;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.EnergyIsSuccessResult;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.link.LinkModel;
import NHSensor.NHSensorSim.link.ModelLinkEstimator;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.ui.Animator;

/*
 * energy efficient grid traverse ring
 */
public class EDCGridTraverseRingLinkAwareEventEnergyExperiment extends
		AlgorithmExperiment {
	TraverseRingExperimentParam param;
	double consumedEnergy = 0;
	double packetFrameNum = 0;
	double packetFrameNumNeed = 0;
	double queryResultCorrectRate = 0;
	int itineraryNodeSize = 0;

	NeighborAttachment root;
	Ring ring;

	public EDCGridTraverseRingLinkAwareEventEnergyExperiment(
			TraverseRingExperimentParam param) {
		this.param = param;
		SensorSim sensorSim = SensorSim.createSensorSim(param);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(GPSRAttachmentAlg.NAME);
		GPSRAttachmentAlg alg = (GPSRAttachmentAlg) sensorSim
				.getAlgorithm(GPSRAttachmentAlg.NAME);
		alg.setQuery(null);
		this.setAlgorithm(alg);
	}

	public void run() {
		Algorithm alg = this.getAlgorithm();
		this.getAlgorithm().init();
		this.ring = new Ring(alg.getNetwork().getCentreNode().getPos(), param
				.getRingLowRadius(), param.getRingHighRadius());
		this.root = (NeighborAttachment) alg.getNetwork()
				.getTopNodeInRing(ring).getAttachment(alg.getName());
		Message mesage = new Message(param.getQueryMessageSize(), null);

		// config network link model
		param.setPt(0);
		LinkModel linkModel = param.createLinkModel();
		Network network = alg.getNetwork();
		alg.getNetwork().setLinkEstimator(
				new ModelLinkEstimator(linkModel, network)
						.createArrayLinkEstimator(param.getLinkID()));
		alg.setConsiderLinkQuality(true);

		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(this.getAlgorithm(),
				ring);
		this.getAlgorithm().run(drawShapeEvent);

		EDCGridTraverseRingEventUseIteratorLinkAware e = new EDCGridTraverseRingEventUseIteratorLinkAware(
				root, ring, mesage, true, this.getAlgorithm());
		this.getAlgorithm().run(e);
		this.getAlgorithm().insertLostNodesEventToSimulatorEventsHead(ring);

		this.getAlgorithm().getStatistics().setQueryResultCorrectness(this.getAlgorithm().getQueryResultCorrectness(ring));
		if(this.getAlgorithm().getStatistics().getQueryResultCorrectness().isQueryResultCorrect(1.0)) {
			this.isSuccess = true;
		}
		else this.isSuccess = false;

//		if (!e.isSuccess())
//			this.isSuccess = false;

		if (this.isShowAnimator()) {
			Animator animator = new Animator(this.getAlgorithm());
			animator.start();
		}

		this.consumedEnergy = this.getAlgorithm().getStatistics()
				.getConsumedEnergy();
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

	public TraverseRingExperimentParam getParam() {
		return param;
	}

	public void setParam(TraverseRingExperimentParam param) {
		this.param = param;
	}

	public ExperimentResult getResult() {
		return new EnergyIsSuccessResult(this.consumedEnergy, this.isSuccess,
				this.packetFrameNum, this.packetFrameNumNeed,
				this.queryResultCorrectRate, this.itineraryNodeSize);
	}

	public ExperimentParamResultPair getExperimentParamResultPair() {
		return new ExperimentParamResultPair(this.getParam(), this.getResult());
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LAC");
		sb.append(" isSuccess(");
		sb.append(this.isSuccess);
		sb.append(")");
		sb.append(" consumedEnergy(");
		sb.append(this.consumedEnergy);
		sb.append(")");
		sb.append(" queryResultCorrectRate(");
		sb.append(this.queryResultCorrectRate);
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
