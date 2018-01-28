package NHSensor.NHSensorSim.experiment;

public class SequenceInfo {
	double beginValue;
	double endValue;
	double stepVaule;

	public SequenceInfo(double beginValue, double endValue, double stepVaule) {
		this.beginValue = beginValue;
		this.endValue = endValue;
		this.stepVaule = stepVaule;
	}

	public double getBeginValue() {
		return beginValue;
	}

	public void setBeginValue(double beginValue) {
		this.beginValue = beginValue;
	}

	public double getEndValue() {
		return endValue;
	}

	public void setEndValue(double endValue) {
		this.endValue = endValue;
	}

	public double getStepVaule() {
		return stepVaule;
	}

	public void setStepVaule(double stepVaule) {
		this.stepVaule = stepVaule;
	}
}
