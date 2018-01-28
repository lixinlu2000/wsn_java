package NHSensor.NHSensorSim.ui;

import javax.swing.JScrollPane;

public class EventsMessageSizeScrollPane extends JScrollPane implements
		SensornetView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EventsMessageSizeList eventsMessageSizeList;

	public EventsMessageSizeScrollPane(SensornetModel sensornetModel) {
		this.eventsMessageSizeList = new EventsMessageSizeList(sensornetModel);
		this.setViewportView(eventsMessageSizeList);
	}

	public SensornetModel getSensornetModel() {
		return this.eventsMessageSizeList.getSensornetModel();
	}

	public void setSensornetModel(SensornetModel sensornetModel) {
		this.eventsMessageSizeList.setSensornetModel(sensornetModel);
	}

	public void updateSensornetView() {
		this.eventsMessageSizeList.updateSensornetView();
	}

}
