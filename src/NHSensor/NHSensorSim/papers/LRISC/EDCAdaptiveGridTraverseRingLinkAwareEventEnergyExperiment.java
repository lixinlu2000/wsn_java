package NHSensor.NHSensorSim.papers.LRISC;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.EDCAdaptiveGridTraverseRingEventUseIteratorFailureAware;
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
public class EDCAdaptiveGridTraverseRingLinkAwareEventEnergyExperiment extends
		AlgorithmExperiment {
	TraverseRingExperimentParam param;
	double consumedEnergy = 0;
	int queryNodeNum = 0;
	double backupNodeNum = 0;
	NeighborAttachment root;
	Ring ring;

	public EDCAdaptiveGridTraverseRingLinkAwareEventEnergyExperiment(
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
		Message mesage = new Message(param.getQueryMessageSize()
				+ param.getAnswerMessageSize(), null);

		// config network link model
		LinkModel linkModel = param.createLinkModel();
		Network network = alg.getNetwork();
		alg.getNetwork().setLinkEstimator(
				new ModelLinkEstimator(linkModel, network)
						.createArrayLinkEstimator(param.getLinkID()));
		alg.setConsiderLinkQuality(true);

		EDCAdaptiveGridTraverseRingEventUseIteratorFailureAware e = new EDCAdaptiveGridTraverseRingEventUseIteratorFailureAware(
				root, ring, mesage, true, this.getAlgorithm());
		this.isSuccess = this.getAlgorithm().run(e);
		if (!e.isSuccess())
			this.isSuccess = false;

		if (this.isShowAnimator()) {
			Animator animator = new Animator(this.getAlgorithm());
			animator.start();
		}
		this.consumedEnergy = this.getAlgorithm().getStatistics()
				.getConsumedEnergy();
		this.queryNodeNum = e.getQueryNodeNum();
		this.backupNodeNum = e.getBackupNodeNum();
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
		return new EnergyIsSuccessResult(this.consumedEnergy,
				this.queryNodeNum, this.backupNodeNum, this.isSuccess);
	}

	public ExperimentParamResultPair getExperimentParamResultPair() {
		return new ExperimentParamResultPair(this.getParam(), this.getResult());
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("EDCAdaptiveGrid");
		sb.append(" isSuccess(");
		sb.append(this.isSuccess);
		sb.append(")");
		sb.append(" queryNodeNum(");
		sb.append(this.queryNodeNum);
		sb.append(")");
		sb.append(" backupNodeNum(");
		sb.append(this.backupNodeNum);
		sb.append(")");
		sb.append(" consumedEnergy(");
		sb.append(this.consumedEnergy);
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
