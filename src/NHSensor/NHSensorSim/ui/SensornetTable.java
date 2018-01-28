package NHSensor.NHSensorSim.ui;

import javax.swing.JTable;

public class SensornetTable extends JTable implements SensornetView {
	private SensornetModel sensornetModel;
	private SensornetTableModel sensornetTableModel;

	public SensornetTable(SensornetModel sensornetModel) {
		this.sensornetModel = sensornetModel;
		this.sensornetTableModel = new SensornetTableModel(sensornetModel);
		this.setModel(sensornetTableModel);
	}

	public SensornetModel getSensornetModel() {
		return this.sensornetModel;
	}

	public void setSensornetModel(SensornetModel sensornetModel) {
		this.sensornetModel = sensornetModel;

	}

	public void updateSensornetView() {
		this.sensornetTableModel.updateCellsData();
	}
}
