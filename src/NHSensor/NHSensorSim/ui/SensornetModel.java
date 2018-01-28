package NHSensor.NHSensorSim.ui;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.analyser.EventsDatabase;
import NHSensor.NHSensorSim.analyser.SensornetEventChooser;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.query.QueryBase;

public class SensornetModel {
	private Vector sensornetViews = new Vector();
	private Network network = null;
	private Vector links = new Vector();
	private QueryBase query = null;
	private String description = "";
	private EventsDatabase eventsDatabase;
	private Algorithm algorithm;
	public static final int ALGORITHM_EVENTS = 0;
	public static final int ALGORITHM_INFORMATION_EVENTS = 1;
	public static final int ALGORITHM_ALLEVENTS = 2;

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	/*
	 * public SensornetModel(Network network, QueryBase query, Vector events) {
	 * this.network = network; this.query = query; this.eventsDatabase = new
	 * EventsDatabase(events); }
	 */

	public SensornetModel(Algorithm algorithm) {
		this.network = algorithm.getNetwork();
		this.query = algorithm.getQuery();
		this.eventsDatabase = new EventsDatabase(algorithm.getSimulator()
				.getEvents());
		this.algorithm = algorithm;
		this.description = this.algorithm.getDescription();

	}

	public SensornetModel(Algorithm algorithm, int eventsType) {
		this.network = algorithm.getNetwork();
		this.query = algorithm.getQuery();
		this.algorithm = algorithm;
		this.description = this.algorithm.getDescription();

		if (eventsType == SensornetModel.ALGORITHM_INFORMATION_EVENTS) {
			this.eventsDatabase = new EventsDatabase(algorithm.getSimulator()
					.getInformationEvents());
		} else if (eventsType == SensornetModel.ALGORITHM_EVENTS) {
			this.eventsDatabase = new EventsDatabase(algorithm.getSimulator()
					.getEvents());
		} else {
			this.eventsDatabase = new EventsDatabase(algorithm.getSimulator()
					.getAllEvents());
		}
	}

	public SensornetModel(Algorithm algorithm,
			SensornetEventChooser eventChooser) {
		this.network = algorithm.getNetwork();
		this.query = algorithm.getQuery();
		this.algorithm = algorithm;
		this.description = this.algorithm.getDescription();

		Vector selectedEvents = algorithm.getSimulator().getEventsByChooser(
				eventChooser);
		this.eventsDatabase = new EventsDatabase(selectedEvents);
	}

	public EventsDatabase getEventsDatabase() {
		return eventsDatabase;
	}

	public void setEventsDatabase(EventsDatabase eventsDatabase) {
		this.eventsDatabase = eventsDatabase;
		this.repaintCanvases();
	}

	public Vector getSensornetViews() {
		return sensornetViews;
	}

	public void setSensornetViews(Vector sensornetViews) {
		this.sensornetViews = sensornetViews;
	}

	public void addSensornetView(SensornetView sensornetView) {
		this.getSensornetViews().add(sensornetView);
	}

	public Vector getEvents() {
		return this.eventsDatabase.getCurrentEvents();
	}

	public void repaintCanvases() {
		for (int i = 0; i < this.sensornetViews.size(); i++) {
			SensornetView sensornetView = (SensornetView) this.sensornetViews
					.elementAt(i);
			sensornetView.updateSensornetView();
		}
	}

	public Vector getLinks() {
		return links;
	}

	public void setLinks(Vector links) {
		this.links = links;
		this.repaintCanvases();
	}

	public Vector getNodes() {
		return network.getNodes();
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public QueryBase getQuery() {
		return query;
	}

	public void setQuery(QueryBase query) {
		this.query = query;
		this.repaintCanvases();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public synchronized void setCurrentEventIndex(int currentEventIndex)
			throws Exception {
		if (currentEventIndex == this.eventsDatabase.getCurrentEventIndex())
			return;
		this.eventsDatabase.setCurrentEventIndex(currentEventIndex);
		this.repaintCanvases();
	}

	public synchronized void movePrevious(int step) throws Exception {
		this.eventsDatabase.movePrevious(step);
		this.repaintCanvases();
	}

	public synchronized void moveNext(int step) throws Exception {
		this.eventsDatabase.moveNext(step);
		this.repaintCanvases();
	}

	public boolean isBegin() {
		return this.eventsDatabase.isBegin();
	}

	public boolean isEnd() {
		return this.eventsDatabase.isEnd();
	}

	public void setSensorCanvasSizeScale(double scale) {
		Vector sensornetCanvases = this.getSensornetCanvases();
		SensornetCanvas sensornetCanvas;
		for (int i = 0; i < sensornetCanvases.size(); i++) {
			sensornetCanvas = (SensornetCanvas) sensornetCanvases.elementAt(i);
			sensornetCanvas.setScale(scale);
		}
	}

	public Vector getSensornetCanvases() {
		Vector sensornetCanvases = new Vector();
		for (int i = 0; i < sensornetViews.size(); i++) {
			if (sensornetViews.elementAt(i) instanceof SensornetCanvas) {
				sensornetCanvases.add(sensornetViews.elementAt(i));
			}
		}
		return sensornetCanvases;
	}

}
