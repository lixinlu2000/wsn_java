package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public abstract class CollectDataEvent extends RadioEvent {
	private boolean succeed = false;
	protected Message message;
	protected boolean isAggregation = true;
	boolean sendQueryMessageFirst = false;

	public CollectDataEvent(Message message, Algorithm alg) {
		super(alg);
		this.message = message;
	}

	public CollectDataEvent(Message message, boolean sendQueryMessageFirst,
			Algorithm alg) {
		super(alg);
		this.message = message;
		this.sendQueryMessageFirst = sendQueryMessageFirst;
	}

	public boolean isSucceed() {
		return succeed;
	}

	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public boolean isAggregation() {
		return isAggregation;
	}

	public void setAggregation(boolean isAggregation) {
		this.isAggregation = isAggregation;
	}

	public boolean isCollectAll() {
		return !this.isAggregation;
	}

	public boolean isSendQueryMessageFirst() {
		return sendQueryMessageFirst;
	}

	public void setSendQueryMessageFirst(boolean sendQueryMessageFirst) {
		this.sendQueryMessageFirst = sendQueryMessageFirst;
	}

	public Message getGreedyMessage() {
		if (this.isSendQueryMessageFirst()) {
			return new Message(
					this.getAlg().getParam().getQUERY_MESSAGE_SIZE(), null);
		} else {
			return this.getMessage();
		}
	}
}
