package NHSensor.NHSensorSim.ui;

import java.awt.Graphics2D;

import NHSensor.NHSensorSim.shape.Rect;

public class GSASensornetCanvas extends SensornetCanvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7003556538705622384L;

	public GSASensornetCanvas(int width, int height,
			SensornetModel sensornetModel) {
		super(width, height, sensornetModel);
		// TODO Auto-generated constructor stub
	}

	public GSASensornetModel getGSASensornetModel() {
		return (GSASensornetModel) this.getSensornetModel();
	}

	public void drawGrids(Graphics2D g2d) {
		Rect[][] grids = this.getGSASensornetModel().getGrids();
		for (int x = 0; x < this.getGSASensornetModel().getXnum(); x++) {
			for (int y = 0; y < this.getGSASensornetModel().getYnum(); y++) {
				this.drawRectangle(g2d, grids[x][y]);
			}
		}
	}

	public void drawOthers(Graphics2D g2d) {
		this.drawGrids(g2d);
		return;
	}

}
