package NHSensor.NHSensorSim.analyser;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.events.DrawRectEvent;

public class DrawRectEventAnalyser extends AlgorithmAnalyser {
	private double averageHeight;
	private double averageArea;

	public DrawRectEventAnalyser(Algorithm algorithm) {
		super(algorithm);
	}

	public void analyse() {
		EventsDatabase eventsDatabase = new EventsDatabase(this.algorithm
				.getSimulator().getAllEvents());
		EventTypeChooser eventTypeChooser = new EventTypeChooser(
				DrawRectEvent.class);
		Vector drawRectEvents = eventsDatabase.select(eventTypeChooser);

		DrawRectEvent de;
		double heightSum = 0;
		double areaSum = 0;

		for (int i = 0; i < drawRectEvents.size(); i++) {
			de = (DrawRectEvent) drawRectEvents.elementAt(i);
			heightSum += de.getRect().getHeight();
			areaSum += de.getRect().area();
		}
		this.averageHeight = heightSum / drawRectEvents.size();
		this.averageArea = areaSum / drawRectEvents.size();
	}

	public double getAverageHeight() {
		return this.averageHeight;
	}

	public double getAverageArea() {
		return averageArea;
	}

	public Object getResult() {
		return new Double(this.getAverageHeight());
	}

}
