package NHSensor.NHSensorSim.ui;

import javax.swing.table.AbstractTableModel;

import NHSensor.NHSensorSim.util.ReflectUtil;

class SensornetTableModel extends AbstractTableModel {
	private String[] columnNames = { "name", "value" };
	private String[][] cells;
	private SensornetModel sensornetModel;
	private Object cellsObject;

	public SensornetTableModel(SensornetModel sensornetModel) {
		this.sensornetModel = sensornetModel;
		this.updateCellsData();
	}

	/*
	 * public void updateCellsData() { cells[0][0] = "Description"; cells[0][1]
	 * = sensornetModel.getDescription(); cells[1][0] = "Node Num"; cells[1][1]
	 * = String.valueOf(sensornetModel.getNodes().size()); cells[2][0] =
	 * "Network"; cells[2][1] =
	 * sensornetModel.getNetwork().getRect().toString(); cells[3][0] = "Query";
	 * cells[3][1] = sensornetModel.getQuery().toString(); cells[4][0] =
	 * "Node Num"; cells[4][1] = "Node Num";
	 * 
	 * }
	 */
	public void updateCellsData() {
		try {
			cells = ReflectUtil.getAllFields(this.sensornetModel.getAlgorithm()
					.getParam());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SensornetModel getSensornetModel() {
		return sensornetModel;
	}

	public void setSensornetModel(SensornetModel sensornetModel) {
		this.sensornetModel = sensornetModel;
	}

	public SensornetTableModel() {
		this.updateCellsData();
	}

	public int getColumnCount() {
		return cells[0].length;
	}

	public int getRowCount() {
		return cells.length;
	}

	public Object getValueAt(int row, int column) {
		return cells[row][column];
	}

	public Object getCellsObject() {
		return cellsObject;
	}

	public void setCellsObject(Object cellsObject) {
		this.cellsObject = cellsObject;
	}

}
