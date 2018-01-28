package NHSensor.NHSensorSim.core;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import NHSensor.NHSensorSim.analyser.EventsDatabase;
import NHSensor.NHSensorSim.analyser.SensornetEventChooser;
import NHSensor.NHSensorSim.events.Event;
import NHSensor.NHSensorSim.events.InformationEvent;
import NHSensor.NHSensorSim.events.SensornetEventListener;
import NHSensor.NHSensorSim.events.SimpleEventListener;
import NHSensor.NHSensorSim.events.TraceEventListener;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class Simulator {
	protected Vector events = new Vector();
	protected Vector informationEvents = new Vector();
	protected Vector allEvents = new Vector();
	protected Hashtable eventListeners = new Hashtable();
	// protected int maxEventSize = Integer.MAX_VALUE;
	protected int maxEventSize = 5000000;
	protected boolean isIncludeInformationEvent = true;
	protected double communicationTotalTime = 0;

	public Simulator() {
	}

	public static Simulator createSimulatorWithListener(String listenserName) {
		Simulator simulator = new Simulator();

		if (listenserName.equalsIgnoreCase("HandleAndTrace")) {
			simulator.addHandleAndTraceEventListener();
		}

		return simulator;
	}

	public void handle(Vector events) throws SensornetBaseException {
		Event e;
		for (int i = 0; i < events.size(); i++) {
			e = (Event) events.elementAt(i);
			this.handle(e);
		}
	}

	public void clear() {
		this.events.clear();
		this.informationEvents.clear();
		this.allEvents.clear();
	}

	public void handle(Event e) throws SensornetBaseException {
		if (this.allEvents.size() >= this.maxEventSize)
			throw new SensornetBaseException("There is too much event");

		Log.getInstance().log(e);
		this.allEvents.add(e);
		if (e instanceof InformationEvent) {
			this.informationEvents.add(e);
			if (this.isIncludeInformationEvent)
				this.events.add(e);
		} else {
			this.events.add(e);
		}

		for (Enumeration en = this.getEventListeners().elements(); en
				.hasMoreElements();) {
			SensornetEventListener l = (SensornetEventListener) en
					.nextElement();
			l.handle(e);
		}
	}

	/*
	 * insert the event into the head of allEvents list
	 */
	public void headHandle(Event e) throws SensornetBaseException {
		if (this.allEvents.size() >= this.maxEventSize)
			throw new SensornetBaseException("There is too much event");

		Log.getInstance().log(e);
		this.allEvents.insertElementAt(e, 0);
		if (e instanceof InformationEvent) {
			this.informationEvents.insertElementAt(e, 0);
		} else {
			this.events.insertElementAt(e, 0);
		}

		for (Enumeration en = this.getEventListeners().elements(); en
				.hasMoreElements();) {
			SensornetEventListener l = (SensornetEventListener) en
					.nextElement();
			l.handle(e);
		}
	}

	public Vector getEvents() {
		return events;
	}

	public SensornetEventListener getSensornetEventListener(String name) {
		return (SensornetEventListener) this.getEventListeners().get(name);
	}

	public Hashtable getEventListeners() {
		return eventListeners;
	}

	public void addEventListener(SensornetEventListener sel) {
		this.getEventListeners().put(sel.getName(), sel);
	}

	public void addHandleEventListener() {
		// SimpleEventListener can handle the event
		SensornetEventListener selb = new SimpleEventListener();
		this.addEventListener(selb);
	}

	public void addTraceEventListener() {
		SensornetEventListener sela = new TraceEventListener();
		this.addEventListener(sela);

	}

	public void addHandleAndTraceEventListener() {
		this.addHandleEventListener();
		this.addTraceEventListener();
	}

	public Vector getInformationEvents() {
		return informationEvents;
	}

	public Vector getAllEvents() {
		return allEvents;
	}

	public Vector getEventsByChooser(SensornetEventChooser eventsChooser) {
		EventsDatabase eventsDatabase = new EventsDatabase(this.getAllEvents());
		return eventsDatabase.select(eventsChooser);
	}

}
