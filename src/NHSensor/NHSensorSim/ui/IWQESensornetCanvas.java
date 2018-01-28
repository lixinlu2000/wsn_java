package NHSensor.NHSensorSim.ui;

import java.awt.Graphics2D;
import java.util.Vector;

import NHSensor.NHSensorSim.shape.Rect;

public class IWQESensornetCanvas extends SensornetCanvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9144972801530595269L;

	public IWQESensornetCanvas(int width, int height,
			SensornetModel sensornetModel) {
		super(width, height, sensornetModel);
		// TODO Auto-generated constructor stub
	}

	public void drawSubQueryRegions(Graphics2D g2d) {
		Vector subQueryRegions = this.getIWQESensornetModel()
				.getSubQueryRegions();
		Rect rect;
		for (int i = 0; i < subQueryRegions.size(); i++) {
			rect = (Rect) subQueryRegions.elementAt(i);
			this.drawRectangle(g2d, rect);
		}
	}

	public IWQESensornetModel getIWQESensornetModel() {
		return (IWQESensornetModel) this.getSensornetModel();
	}

	public void drawOthers(Graphics2D g2d) {
		this.drawSubQueryRegions(g2d);
		return;
	}

}
