package NHSensor.NHSensorSim.core;

import NHSensor.NHSensorSim.shape.Position;

public class GStarAlgParam extends AlgParam {
	private double sectionWidth;
	private double sectionHeight;
	private Position subRoot;
	private double radioRange;

	public Position getSubRoot() {
		return subRoot;
	}

	public void setSubRoot(Position subRoot) {
		this.subRoot = subRoot;
	}

	public GStarAlgParam() {
		// TODO Auto-generated constructor stub
	}

	public GStarAlgParam(double sectionWidth, double sectionHeight,
			Position subRoot, double radioRange) {
		this.sectionWidth = sectionWidth;
		this.sectionHeight = sectionHeight;
		this.radioRange = radioRange;
		this.subRoot = subRoot;
	}

	public double getRadioRange() {
		return radioRange;
	}

	public void setRadioRange(double radioRange) {
		this.radioRange = radioRange;
	}

	public double getSectionHeight() {
		return sectionHeight;
	}

	public void setSectionHeight(double sectionHeight) {
		this.sectionHeight = sectionHeight;
	}

	public double getSectionWidth() {
		return sectionWidth;
	}

	public void setSectionWidth(double sectionWidth) {
		this.sectionWidth = sectionWidth;
	}

}
