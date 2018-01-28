package NHSensor.NHSensorSim.ui;

import java.util.Vector;

import javax.swing.JList;

import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.Event;
import NHSensor.NHSensorSim.events.TransferEvent;

public class EventsMessageSizeList extends JList implements SensornetView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SensornetModel sensornetModel;

	public EventsMessageSizeList(SensornetModel sensornetModel) {
		this.sensornetModel = sensornetModel;
	}

	public SensornetModel getSensornetModel() {
		return sensornetModel;
	}

	public void setSensornetModel(SensornetModel sensornetModel) {
		this.sensornetModel = sensornetModel;
	}

	public void updateSensornetView() {
		Vector events = sensornetModel.getEvents();
		Vector messageSizes = new Vector();
		Event event;
		int messageSize;
		for (int i = 0; i < events.size(); i++) {
			event = (Event) events.elementAt(i);
			if (event instanceof BroadcastEvent) {
				messageSize = ((BroadcastEvent) event).getMessageSizeForDebug();
				messageSizes.add(new Integer(messageSize));
			} else if (event instanceof TransferEvent) {
				messageSize = ((TransferEvent) event).getMessageSizeForDebug();
				messageSizes.add("(" + new Integer(messageSize) + ")");
			} else {
				messageSizes.add("******");
			}
		}
		this.setListData(messageSizes);
		this.ensureIndexIsVisible(this.getModel().getSize() - 1);
	}

}
