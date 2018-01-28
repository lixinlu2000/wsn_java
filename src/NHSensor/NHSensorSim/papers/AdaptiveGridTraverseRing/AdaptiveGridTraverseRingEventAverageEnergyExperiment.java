package NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.analyser.ItineraryNodeEventAnalyser;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.AdaptiveGridTraverseRingEvent;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.EnergyIsSuccessResult;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.experiment.RobustnessResult;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.ui.Animator;

public class AdaptiveGridTraverseRingEventAverageEnergyExperiment extends
		AlgorithmExperiment {
	TraverseRingExperimentParam param;
	double consumedEnergy = 0;
	NeighborAttachment root;
	Ring ring;

	public AdaptiveGridTraverseRingEventAverageEnergyExperiment(
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
		Message queryMesage = new Message(param.getQueryMessageSize(), null);
		AdaptiveGridTraverseRingEvent e = new AdaptiveGridTraverseRingEvent(
				root, ring, queryMesage, this.getAlgorithm());
		this.isSuccess = this.getAlgorithm().run(e);
		if (!e.isSuccess())
			this.isSuccess = false;

		if (this.isShowAnimator()) {
			Animator animator = new Animator(this.getAlgorithm());
			animator.start();
		}

		ItineraryNodeEventAnalyser analyser = new ItineraryNodeEventAnalyser(
				this.getAlgorithm(), this.getParam().getNodeFailProbability());
		analyser.analyse();
		double successProbability = ((RobustnessResult) analyser.getResult())
				.getSuccessProbability();

		this.consumedEnergy = this.getAlgorithm().getStatistics()
				.getConsumedEnergy()
				/ successProbability;
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
		return new EnergyIsSuccessResult(this.consumedEnergy, this.isSuccess);
	}

	public ExperimentParamResultPair getExperimentParamResultPair() {
		return new ExperimentParamResultPair(this.getParam(), this.getResult());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
