package NHSensor.NHSensorSim.events;

import java.util.Vector;

import NHSensor.NHSensorSim.core.NeighborAttachment;

public class EventsUtil {

	public static Vector NeighborAttachmentsToItinerayNodeEvents(
			Vector neighborAttachments,
			boolean isConvertLastNeighborAttachment,
			Vector firstNeighborAttachmentDataNodes) {
		Vector itinerayNodeEvents = new Vector();
		ItineraryNodeEvent ie;
		NeighborAttachment cna;
		NeighborAttachment nna;

		for (int i = 0; i < neighborAttachments.size(); i++) {
			if (i == neighborAttachments.size() - 1
					&& !isConvertLastNeighborAttachment)
				break;
			cna = (NeighborAttachment) neighborAttachments.elementAt(i);
			nna = null;
			if (i < neighborAttachments.size() - 1) {
				nna = (NeighborAttachment) neighborAttachments.elementAt(i + 1);
			}
			ie = new ItineraryNodeEvent(cna, nna, cna.getAlgorithm());
			if (i == 0) {
				ie.setDataNodes(firstNeighborAttachmentDataNodes);
			}
			itinerayNodeEvents.add(ie);
		}
		return itinerayNodeEvents;
	}

	public static Vector NeighborAttachmentsToItinerayNodeEvents(
			ForwardToShapeEvent forwardToShapeEvent,
			boolean isConvertLastNeighborAttachment,
			Vector firstNeighborAttachmentDataNodes) {
		Vector path = forwardToShapeEvent.getRoute();
		path.add(0, forwardToShapeEvent.getRoot());
		return EventsUtil.NeighborAttachmentsToItinerayNodeEvents(path,
				isConvertLastNeighborAttachment,
				firstNeighborAttachmentDataNodes);
	}

	public static Vector NeighborAttachmentsToItinerayNodeEvents(
			Vector neighborAttachments, boolean isConvertLastNeighborAttachment) {
		return EventsUtil.NeighborAttachmentsToItinerayNodeEvents(
				neighborAttachments, isConvertLastNeighborAttachment, null);
	}

	public static Vector NeighborAttachmentsToItinerayNodeEvents(
			ForwardToShapeEvent forwardToShapeEvent,
			boolean isConvertLastNeighborAttachment) {
		return EventsUtil.NeighborAttachmentsToItinerayNodeEvents(
				forwardToShapeEvent, isConvertLastNeighborAttachment, null);
	}

}
