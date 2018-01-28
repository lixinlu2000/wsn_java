package NHSensor.NHSensorSim.experiment;

public abstract class Experiment {
	private String description;
	private boolean showAnimator = false;

	// private boolean showAnimator = true;

	public Experiment() {
	}

	public Experiment(String description) {
		this.description = description;
	}

	public Experiment(boolean showAnimator) {
		this.showAnimator = showAnimator;
	}

	public boolean isShowAnimator() {
		return showAnimator;
	}

	public void setShowAnimator(boolean showAnimator) {
		this.showAnimator = showAnimator;
	}

	public abstract void run();

	public abstract ExperimentResult getResult();

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
