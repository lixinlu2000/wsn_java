package NHSensor.NHSensorSim.ui;

import java.awt.Graphics2D;
import java.util.Vector;

import NHSensor.NHSensorSim.shape.RingSector;

public class GKNNSensornetCanvas extends SensornetCanvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2218124485114702243L;

	public GKNNSensornetCanvas(int width, int height,
			SensornetModel sensornetModel) {
		super(width, height, sensornetModel);
		// TODO Auto-generated constructor stub
	}

	public GKNNSensornetModel getGKNNSensornetModel() {
		return (GKNNSensornetModel) this.getSensornetModel();
	}

	public void drawRingSectors(Graphics2D g2d) {
		Vector allRingSectors = this.getGKNNSensornetModel()
				.getAllRingSectors();
		RingSector ringSector;
		for (int i = 0; i < allRingSectors.size(); i++) {
			ringSector = (RingSector) allRingSectors.elementAt(i);
			this.drawRingSector(g2d, ringSector, this.getQueryColor());
		}
	}

	public void drawOthers(Graphics2D g2d) {
		this.drawRingSectors(g2d);
		return;
	}

}
