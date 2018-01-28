package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;

public class AlgorithmEvent extends Event {
	private Algorithm algEvent;

	public Algorithm getAlgEvent() {
		return algEvent;
	}

	public void setAlgEvent(Algorithm algEvent) {
		this.algEvent = algEvent;
	}

	public AlgorithmEvent(Algorithm alg, Algorithm algEvent) {
		super(alg);
		this.setAlgEvent(algEvent);
		this.getAlgEvent().setName(this.getAlg().getName());
		this.getAlgEvent().setNetwork(this.getAlg().getNetwork());
		this.getAlgEvent().setParam(this.getAlg().getParam());
		this.getAlgEvent().setSimulator(this.getAlg().getSimulator());
		this.getAlgEvent().setStatistics(this.getAlg().getStatistics());
	}

	public void handle() {
		// TODO Auto-generated method stub
		this.getAlgEvent().run();
	}

}
