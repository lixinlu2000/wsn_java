package NHSensor.NHSensorSim.ui;

import java.util.Vector;

import javax.swing.JList;

import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.Event;
import NHSensor.NHSensorSim.events.TransferEvent;

public class EnergyUtilEventsList extends JList implements SensornetView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SensornetModel sensornetModel;

	public EnergyUtilEventsList(SensornetModel sensornetModel) {
		this.sensornetModel = sensornetModel;
	}

	public SensornetModel getSensornetModel() {
		return sensornetModel;
	}

	public void setSensornetModel(SensornetModel sensornetModel) {
		this.sensornetModel = sensornetModel;
	}

	public void updateSensornetView() {
		Vector events = sensornetModel.getEvents();
		Vector energyList = new Vector();
		Event event;
		double energy;
		for (int i = 0; i < events.size(); i++) {
			event = (Event) events.elementAt(i);
			if (event instanceof BroadcastEvent) {
				energy = ((BroadcastEvent) event)
						.getConsumedEnergyUtilThisEvent();
				energyList.add(energy);
			} else if (event instanceof TransferEvent) {
				energyList.add("()");
			} else {
				energyList.add("******");
			}
		}
		this.setListData(energyList);
		this.ensureIndexIsVisible(this.getModel().getSize() - 1);
	}

}
