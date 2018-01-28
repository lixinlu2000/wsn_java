package NHSensor.NHSensorSim.dataset;

import java.util.Random;

import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.shape.Position;

public class GuiNengDataSet extends DataSet {
	double initialValue;
	double initialValueDelta;
	Position sourcePos;
	double sourceMaxValue, sourceMaxValueDelta;
	double c;
	Random r1,r2;

	public GuiNengDataSet(double initialValue,
			double initialValueDelta, Position sourcePos,
			double sourceMaxValue, double sourceMaxValueDelta, double c, int randomNumber) {
		super();
		this.initialValue = initialValue;
		this.initialValueDelta = initialValueDelta;
		this.sourcePos = sourcePos;
		this.sourceMaxValue = sourceMaxValue;
		this.sourceMaxValueDelta = sourceMaxValueDelta;
		this.c = c;
		r1 = new Random(randomNumber);
		r2 = new Random(randomNumber);
	}

	@Override
	public double getValue(Position pos, Time time, int attributeID) {
		double sourceImpact = this.getSourceImpact(pos, time, attributeID);
		double v = this.getInitialValue(pos, time, attributeID);
		
		if(sourceImpact>=this.getInitialValueMax()) {
			return sourceImpact;
		}
		else return v;
	}
	
	public double getMean(int width, int height) {
		double sum = 0;
		int count = 0;
		
		for(int w=0;w<=width;w++) {
			for(int h=0;h<=height;h++) {
				count++;
				sum += this.getValue(new Position(w,h), new Time(0), 0);
			}
		}
		return sum/count;
	}
	
	protected double getSourceImpact(Position pos, Time time, int attributeID) {
		double distance = pos.distance(this.sourcePos);
		double distance2 = distance*distance;
		double c2 = this.c*this.c;
		double sourceImpact = this.sourceMaxValue*Math.pow(Math.E, -distance2/(2*c2));
		sourceImpact += this.sourceMaxValueDelta*2*(r1.nextDouble()-0.5);
		return sourceImpact;
	}
	
	protected double getInitialValue(Position pos, Time time, int attributeID) {
		double v = this.initialValue+this.initialValueDelta*2*(r2.nextDouble()-0.5);
		return v;
	}
	
	protected double getInitialValueMax() {
		return this.initialValue+this.initialValueDelta;
	}

	public double getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}

	public double getInitialValueDelta() {
		return initialValueDelta;
	}

	public void setInitialValueDelta(double initialValueDelta) {
		this.initialValueDelta = initialValueDelta;
	}

	public Position getSourcePos() {
		return sourcePos;
	}

	public void setSourcePos(Position sourcePos) {
		this.sourcePos = sourcePos;
	}

	public double getSourceMaxValue() {
		return sourceMaxValue;
	}

	public void setSourceMaxValue(double sourceMaxValue) {
		this.sourceMaxValue = sourceMaxValue;
	}

	public double getSourceMaxValueDelta() {
		return sourceMaxValueDelta;
	}

	public void setSourceMaxValueDelta(double sourceMaxValueDelta) {
		this.sourceMaxValueDelta = sourceMaxValueDelta;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}
	
	public double getInf() {
		return this.initialValue-this.initialValueDelta;
	}
	
	public double getSup() {
		return this.sourceMaxValue+this.sourceMaxValueDelta;
	}
}
