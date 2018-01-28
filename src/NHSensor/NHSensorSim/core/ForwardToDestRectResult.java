package NHSensor.NHSensorSim.core;

import NHSensor.NHSensorSim.routing.gpsr.GPSRAttachment;
import NHSensor.NHSensorSim.shape.Rect;

public class ForwardToDestRectResult {
	private boolean success;
	private Rect destRect;
	private GPSRAttachment lastNodeAttachment;

	public ForwardToDestRectResult(boolean success, Rect destRect,
			GPSRAttachment lastNodeAttachment) {
		this.success = success;
		this.destRect = destRect;
		this.lastNodeAttachment = lastNodeAttachment;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Rect getDestRect() {
		return destRect;
	}

	public void setDestRect(Rect destRect) {
		this.destRect = destRect;
	}

	public GPSRAttachment getLastNodeAttachment() {
		return lastNodeAttachment;
	}

	public void setLastNodeAttachment(GPSRAttachment lastNodeAttachment) {
		this.lastNodeAttachment = lastNodeAttachment;
	}

}
