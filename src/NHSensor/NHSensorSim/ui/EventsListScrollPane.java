package NHSensor.NHSensorSim.ui;

import javax.swing.JScrollPane;

public class EventsListScrollPane extends JScrollPane implements SensornetView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EventsList eventsList;

	public EventsListScrollPane(SensornetModel sensornetModel) {
		this.eventsList = new EventsList(sensornetModel);
		this.setViewportView(eventsList);
	}

	public SensornetModel getSensornetModel() {
		return this.eventsList.getSensornetModel();
	}

	public void setSensornetModel(SensornetModel sensornetModel) {
		this.eventsList.setSensornetModel(sensornetModel);
	}

	public void updateSensornetView() {
		this.eventsList.updateSensornetView();
	}

}
