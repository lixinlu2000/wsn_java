package NHSensor.NHSensorSim.ui;

import java.awt.Graphics2D;
import java.util.Vector;

import NHSensor.NHSensorSim.shape.Rect;

public class DGSASensornetCanvas extends IWQESensornetCanvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8522492960109700017L;

	public DGSASensornetCanvas(int width, int height,
			SensornetModel sensornetModel) {
		super(width, height, sensornetModel);
		// TODO Auto-generated constructor stub
	}

	public void drawCollectAnswersRects(Graphics2D g2d) {
		Vector rects = this.getDGSASensornetModel().getRects();
		Rect rect;

		for (int i = 0; i < rects.size(); i++) {
			rect = (Rect) rects.elementAt(i);
			this.drawRectangle(g2d, rect);
		}
	}

	public DGSASensornetModel getDGSASensornetModel() {
		return (DGSASensornetModel) this.getSensornetModel();
	}

	public void drawOthers(Graphics2D g2d) {
		// this.drawCollectAnswersRects(g2d);
		this.drawSubQueryRegions(g2d);
		return;
	}

}
