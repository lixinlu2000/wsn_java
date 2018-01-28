package NHSensor.NHSensorSim.ui;

import NHSensor.NHSensorSim.core.*;
import NHSensor.NHSensorSim.events.BaseEventListener;
import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.Event;

public class GUIEventListener extends BaseEventListener {
	final static String NAME = "GUIEventListener";
	private SensornetCanvas sensornetCanvas;

	// private Vector events;

	public GUIEventListener(SensornetCanvas sensornetCanvas) {
		super(GUIEventListener.NAME);
		this.sensornetCanvas = sensornetCanvas;
	}

	public void handle(Event e) {
		BroadcastEvent be = (BroadcastEvent) e;
		// this.getSensornetCanvas().drawEvent(be);
		try {
			Thread.sleep((long) (be.getDuration()));
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public SensornetCanvas getSensornetCanvas() {
		return sensornetCanvas;
	}

	public void setSensornetCanvas(SensornetCanvas sensornetCanvas) {
		this.sensornetCanvas = sensornetCanvas;
	}

}
