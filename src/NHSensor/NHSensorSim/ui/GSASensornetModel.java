package NHSensor.NHSensorSim.ui;

import NHSensor.NHSensorSim.algorithm.GSAAlg;
import NHSensor.NHSensorSim.analyser.SensornetEventChooser;
import NHSensor.NHSensorSim.shape.Rect;

public class GSASensornetModel extends SensornetModel {
	private Rect[][] grids;
	private int xnum;
	private int ynum;

	public int getXnum() {
		return xnum;
	}

	public void setXnum(int xnum) {
		this.xnum = xnum;
	}

	public int getYnum() {
		return ynum;
	}

	public void setYnum(int ynum) {
		this.ynum = ynum;
	}

	public GSASensornetModel(GSAAlg gsaAlg) {
		super(gsaAlg);
		// TODO Auto-generated constructor stub
	}

	public GSASensornetModel(GSAAlg gsaAlg, SensornetEventChooser eventChooser) {
		super(gsaAlg, eventChooser);
	}

	public Rect[][] getGrids() {
		return grids;
	}

	public void setGrids(Rect[][] grids) {
		this.grids = grids;
	}

}
