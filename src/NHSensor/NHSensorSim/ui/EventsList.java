package NHSensor.NHSensorSim.ui;

import java.util.Vector;

import javax.swing.JList;

public class EventsList extends JList implements SensornetView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SensornetModel sensornetModel;

	public EventsList(SensornetModel sensornetModel) {
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
		this.setListData(events);
		this.ensureIndexIsVisible(this.getModel().getSize() - 1);
	}

}
