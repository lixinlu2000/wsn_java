package NHSensor.NHSensorSim.ui;

import java.awt.Graphics2D;

public class SensornetLinkCanvas extends SensornetCanvas {

	public SensornetLinkCanvas(int width, int height,
			SensornetModel sensornetModel) {
		super(width, height, sensornetModel, false);
	}

	public void drawOthers(Graphics2D g2d) {
		super.drawOthers(g2d);
		this.drawAllLinks(g2d);
	}

}
