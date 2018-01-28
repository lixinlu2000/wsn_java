package NHSensor.NHSensorSim.ui;

import java.awt.Graphics2D;

public class SensornetQueryResultCanvas extends SensornetCanvas {

	public SensornetQueryResultCanvas(int width, int height,
			SensornetModel sensornetModel) {
		super(width, height, sensornetModel, false);
	}

	public void drawOthers(Graphics2D g2d) {
		super.drawOthers(g2d);
		this.drawNodesHaveQueryResult(g2d, this.getSensornetModel()
				.getAlgorithm().getTraversedAnswerNodes());
	}
}
