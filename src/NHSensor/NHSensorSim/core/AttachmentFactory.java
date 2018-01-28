package NHSensor.NHSensorSim.core;

public class AttachmentFactory {
	private static AttachmentFactory af = null;

	private AttachmentFactory() {

	}

	public static AttachmentFactory getInstance() {
		if (af == null) {
			af = new AttachmentFactory();
		}
		return af;
	}
}
