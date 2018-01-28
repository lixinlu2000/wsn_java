package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;

public abstract class RadioEvent extends Event {
	protected boolean consumeEnergy = true;

	public boolean isConsumeEnergy() {
		return consumeEnergy;
	}

	public void setConsumeEnergy(boolean consumeEnergy) {
		this.consumeEnergy = consumeEnergy;
	}

	public RadioEvent(Algorithm alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	public double getDuration() {
		// return this.getEndTime()-this.getStartTime();
		return 1000;
	}
}
