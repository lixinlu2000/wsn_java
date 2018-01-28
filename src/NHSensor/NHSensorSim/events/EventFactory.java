package NHSensor.NHSensorSim.events;

import java.awt.Color;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;

public class EventFactory {
	public static BroadcastEventLinkAware createLinkAware(
			NeighborAttachment sender, NeighborAttachment receiver,
			Message message, Algorithm alg, Color color) {
		return new BroadcastEventLinkAware(sender, receiver, message, alg,
				color);
	}

	public static BroadcastEventLinkAware createLinkAware(
			NeighborAttachment sender, NeighborAttachment receiver,
			Message message, Algorithm alg, int tag) {
		return new BroadcastEventLinkAware(sender, receiver, message, alg, tag);
	}

	public static AttachmentDebugEvent createAttachmentDebugEvent(
			Attachment attachment, Algorithm alg) {
		return new AttachmentDebugEvent(attachment, alg);
	}

	public static ProxyDebugEvent createProxyDebugEvent(Event event,
			Algorithm alg) {
		return new ProxyDebugEvent(event, alg);
	}

}
