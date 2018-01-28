package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;

public abstract class TransferEvent extends RadioEvent {
	protected Message message;
	protected boolean success = false;
	// the field is useless just for debug
	private int messageSizeForDebug;

	public TransferEvent(Message message, Algorithm alg) {
		super(alg);
		this.message = message;
		this.messageSizeForDebug = this.message.getTotalSize();
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getMessageSizeForDebug() {
		return messageSizeForDebug;
	}

	public void setMessageSizeForDebug(int messageSizeForDebug) {
		this.messageSizeForDebug = messageSizeForDebug;
	}
}
