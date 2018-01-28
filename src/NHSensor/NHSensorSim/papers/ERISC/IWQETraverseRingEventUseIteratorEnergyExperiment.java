package NHSensor.NHSensorSim.papers.ERISC;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.QueryResultCorrectness;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.core.TraversedNodes;
import NHSensor.NHSensorSim.events.DrawShapeEvent;
import NHSensor.NHSensorSim.events.IWQETraverseRingEventUseIterator;
import NHSensor.NHSensorSim.events.IWQETraverseRingEventUseIteratorFailureAware;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.EnergyIsSuccessResult;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.failure.NodeFailureModelFactory;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.ui.Animator;

public class IWQETraverseRingEventUseIteratorEnergyExperiment extends
		AlgorithmExperiment {
	TraverseRingExperimentParam param;
	double consumedEnergy = 0;
	double queryResultCorrectRate = 0;
	NeighborAttachment root;
	Ring ring;

	public IWQETraverseRingEventUseIteratorEnergyExperiment(
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
		this.getAlgorithm().init();
		this.ring = new Ring(this.getAlgorithm().getNetwork().getCentreNode()
				.getPos(), param.getRingLowRadius(), param.getRingHighRadius());
		this.root = (NeighborAttachment) this.getAlgorithm().getNetwork()
				.getTopNodeInRing(ring).getAttachment(
						this.getAlgorithm().getName());
		Message message = new Message(param.getQueryMessageSize()
				+ param.getAnswerMessageSize(), null);

		// config node failure model
		Network network = this.getAlgorithm().getNetwork();
		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(param.getNodeFailModelID(), param
						.getFailNodeNum()));

		DrawShapeEvent drawShapeEvent = new DrawShapeEvent(this.getAlgorithm(),
				ring);
		this.getAlgorithm().run(drawShapeEvent);

		IWQETraverseRingEventUseIteratorFailureAware e = new IWQETraverseRingEventUseIteratorFailureAware(
				root, ring, message, true, this.getAlgorithm());
		this.isSuccess = this.getAlgorithm().run(e);
		if (!e.isSuccess())
			this.isSuccess = false;

		if (this.isShowAnimator()) {
			Animator animator = new Animator(this.getAlgorithm());
			animator.start();
		}
		this.consumedEnergy = this.getAlgorithm().getStatistics()
				.getConsumedEnergy();

		TraversedNodes shouldTraversedNodes = TraversedNodes.getTraversedNodes(
				this.getAlgorithm().getNetwork(), this.ring);
		QueryResultCorrectness queryResultCorrectness = this.getAlgorithm()
				.getQueryResultCorrectness(shouldTraversedNodes);
		this.queryResultCorrectRate = queryResultCorrectness
				.getResultCorrectRate();
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
		EnergyIsSuccessResult energyIsSuccessResult = new EnergyIsSuccessResult(
				this.consumedEnergy, this.isSuccess);
		energyIsSuccessResult
				.setQueryResultCorrectRate(this.queryResultCorrectRate);
		return energyIsSuccessResult;
	}

	public ExperimentParamResultPair getExperimentParamResultPair() {
		return new ExperimentParamResultPair(this.getParam(), this.getResult());
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("IC");
		sb.append(" isSuccess(");
		sb.append(this.isSuccess);
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
