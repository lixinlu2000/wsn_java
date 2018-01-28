package NHSensor.NHSensorSim.ui;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.DGSANewAlg;
import NHSensor.NHSensorSim.algorithm.IWQEAlg;
import NHSensor.NHSensorSim.analyser.SensornetEventChooser;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.query.QueryBase;

public class DGSASensornetModel extends IWQESensornetModel {
	private Vector rects;

	public Vector getRects() {
		return rects;
	}

	public void setRects(Vector rects) {
		this.rects = rects;
	}

	public DGSASensornetModel(IWQEAlg alg) {
		super(alg);
		// TODO Auto-generated constructor stub
	}

	public DGSASensornetModel(IWQEAlg iwqeAlg,
			SensornetEventChooser eventChooser) {
		super(iwqeAlg, eventChooser);
	}

}
