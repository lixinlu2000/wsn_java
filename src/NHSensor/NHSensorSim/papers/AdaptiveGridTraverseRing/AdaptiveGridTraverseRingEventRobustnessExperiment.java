package NHSensor.NHSensorSim.papers.AdaptiveGridTraverseRing;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.GPSRAttachmentAlg;
import NHSensor.NHSensorSim.analyser.ItineraryNodeEventAnalyser;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.events.AdaptiveGridTraverseRingEvent;
import NHSensor.NHSensorSim.experiment.AlgorithmExperiment;
import NHSensor.NHSensorSim.experiment.ExperimentParamResultPair;
import NHSensor.NHSensorSim.experiment.ExperimentResult;
import NHSensor.NHSensorSim.experiment.RobustnessResult;
import NHSensor.NHSensorSim.shape.Ring;
import NHSensor.NHSensorSim.ui.Animator;

public class AdaptiveGridTraverseRingEventRobustnessExperiment extends
		AlgorithmExperiment {
	private TraverseRingExperimentParam param;
	private RobustnessResult robustnessResult;
	private NeighborAttachment root;
	private Ring ring;

	public AdaptiveGridTraverseRingEventRobustnessExperiment(
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

	public TraverseRingExperimentParam getParam() {
		return param;
	}

	public void setParam(TraverseRingExperimentParam param) {
		this.param = param;
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
		this.robustnessResult = (RobustnessResult) analyser.getResult();
		this.robustnessResult.setSuccess(this.isSuccess);
	}

	public ExperimentResult getResult() {
		return this.robustnessResult;
	}

	public ExperimentParamResultPair getExperimentParamResultPair() {
		return new ExperimentParamResultPair(this.getParam(), this.getResult());
	}

	public double getAverageBackupNodeCount() {
		return robustnessResult.getAverageBackupNodeCount();
	}

	public Vector getBackupNodeCount() {
		return robustnessResult.getBackupNodeCount();
	}

	public int getItineraryNodeCount() {
		return robustnessResult.getItineraryNodeCount();
	}

	public double getSuccessProbability() {
		return robustnessResult.getSuccessProbability();
	}

	public boolean isSuccess() {
		return robustnessResult.isSuccess();
	}

}
