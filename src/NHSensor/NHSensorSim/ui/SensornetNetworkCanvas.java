package NHSensor.NHSensorSim.ui;

import java.awt.Graphics2D;

public class SensornetNetworkCanvas extends SensornetCanvas {

	public SensornetNetworkCanvas(int width, int height,
			SensornetModel sensornetModel) {
		super(width, height, sensornetModel, false);
	}

	public void drawOthers(Graphics2D g2d) {
		super.drawOthers(g2d);
		// Color oldColor = g2d.getColor();
		// g2d.setColor(this.getNodeColor());
		// this.drawCircle(g2d, Convertor.positionToPoint2DDouble(new
		// Position(10,10)),20,Color.red, true);
		// //this.drawString(g2d, Integer.toString(node.getId()),
		// node.getPos().getAddedPosition(new Position(3,3)),
		// this.getNodeColor());
		// g2d.setColor(oldColor);

	}
}
