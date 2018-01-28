package NHSensor.NHSensorSim.ui;

import java.util.Vector;

public class SensornetStatusBar extends JStatusBar implements SensornetView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SensornetModel sensornetModel;

	public SensornetModel getSensornetModel() {
		return this.sensornetModel;
	}

	public void setSensornetModel(SensornetModel sensornetModel) {
		this.sensornetModel = sensornetModel;
	}

	public SensornetStatusBar(SensornetModel sensornetModel) {
		this.sensornetModel = sensornetModel;

		this.addStatusCell(180);
		this.addStatusCell(200);
		this.addStatusCell(200);
		this.addStatusCell(100);
		this.addStatusCell(300);
		this.addStatusCell(200);
		this.setStatus(1, "Description: "
				+ this.sensornetModel.getDescription());
		this.setStatus(2, "Network: "
				+ this.sensornetModel.getNetwork().getRect());
		this.setStatus(3, "Node Num: " + this.sensornetModel.getNodes().size());
		this.setStatus(4, "Query: " + this.sensornetModel.getQuery());
		this.setStatus(5, "Event: null");
	}

	public void updateSensornetView() {
		Vector events = this.getSensornetModel().getEvents();
		if (events != null && events.size() > 0)
			this.setStatus(5, "Event: " + events.lastElement());
		else
			this.setStatus(5, "Event: null");
	}
}
