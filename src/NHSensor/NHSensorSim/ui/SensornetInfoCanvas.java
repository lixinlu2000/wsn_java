package NHSensor.NHSensorSim.ui;

import java.awt.Graphics2D;

public class SensornetInfoCanvas extends SensornetCanvas {

	public SensornetInfoCanvas(int width, int height,
			SensornetModel sensornetModel) {
		super(width, height, sensornetModel, false);
	}

	public void drawOthers(Graphics2D g2d) {
		super.drawOthers(g2d);
		this.drawAllLinkQuality(g2d);
	}
}
