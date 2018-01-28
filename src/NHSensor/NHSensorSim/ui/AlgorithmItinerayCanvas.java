package NHSensor.NHSensorSim.ui;

import java.awt.Graphics2D;
import java.util.Vector;

import NHSensor.NHSensorSim.events.ItineraryRouteEvent;

public class AlgorithmItinerayCanvas extends SensornetCanvas {

	public AlgorithmItinerayCanvas(int width, int height,
			SensornetModel sensornetModel) {
		super(width, height, sensornetModel, false);
	}

	public void drawOthers(Graphics2D g2d) {
		super.drawOthers(g2d);
		Vector route = this.getSensornetModel().getAlgorithm().getRoute();
		ItineraryRouteEvent itineraryRouteEvent = new ItineraryRouteEvent(this
				.getSensornetModel().getAlgorithm(), route);
		this.drawEvent(g2d, itineraryRouteEvent, false);
	}
}
