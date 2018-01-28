package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public abstract class Event {
	protected Algorithm alg;
	protected Object output;
	protected double startTime;
	protected double endTime;
	protected int tag = CommonEventTag.NO_TAG;
	protected Event parent = null;
	protected boolean visible = true;

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Event getParent() {
		return parent;
	}

	public void setParent(Event parent) {
		this.parent = parent;
		this.tag = parent.tag;
		this.visible = parent.isVisible();
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public double getEndTime() {
		return endTime;
	}

	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public double getDuration() {
		// return this.getEndTime()-this.getStartTime();
		return 1000;
	}

	public Event(Algorithm alg) {
		this.alg = alg;
	}

	public Event(Algorithm alg, int tag) {
		this.alg = alg;
		this.tag = tag;
	}

	public Algorithm getAlg() {
		return alg;
	}

	public void setOutput(Object output) {
		this.output = output;
	}

	public void setAlg(Algorithm alg) {
		this.alg = alg;
	}

	public abstract void handle() throws SensornetBaseException;

	public Object getOutput() {
		return null;
	}

	public void setSendQueryTag() {
		this.tag = CommonEventTag.SEND_QUERY;
	}

	public void setSendAnswerTag() {
		this.tag = CommonEventTag.SEND_ANSWER;
	}

}
