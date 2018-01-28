package NHSensor.NHSensorSim.ui;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.IWQEAlg;
import NHSensor.NHSensorSim.analyser.SensornetEventChooser;

public class IWQESensornetModel extends SensornetModel {
	private Vector subQueryRegions;

	public Vector getSubQueryRegions() {
		return subQueryRegions;
	}

	public void setSubQueryRegions(Vector subQueryRegions) {
		this.subQueryRegions = subQueryRegions;
	}

	public IWQESensornetModel(IWQEAlg iwqeAlg) {
		super(iwqeAlg);
	}

	public IWQESensornetModel(IWQEAlg iwqeAlg,
			SensornetEventChooser eventChooser) {
		super(iwqeAlg, eventChooser);
	}
}
