package NHSensor.NHSensorSim.ui;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.GKNNAlg;
import NHSensor.NHSensorSim.algorithm.IWQEAlg;
import NHSensor.NHSensorSim.analyser.SensornetEventChooser;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.query.QueryBase;

public class GKNNSensornetModel extends SensornetModel {
	Vector allRingSectors;

	public GKNNSensornetModel(GKNNAlg gKNNAlg) {
		super(gKNNAlg);
		// TODO Auto-generated constructor stub
	}

	public GKNNSensornetModel(GKNNAlg gKNNAlg,
			SensornetEventChooser eventChooser) {
		super(gKNNAlg, eventChooser);
	}

	public Vector getAllRingSectors() {
		return allRingSectors;
	}

	public void setAllRingSectors(Vector allRingSectors) {
		this.allRingSectors = allRingSectors;
	}

}
