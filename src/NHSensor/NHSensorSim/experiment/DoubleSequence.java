package NHSensor.NHSensorSim.experiment;

import java.util.Vector;

public class DoubleSequence implements Sequence {
	Vector data = new Vector();

	public Vector getData() {
		return data;
	}

	public DoubleSequence() {
	}

	public DoubleSequence(double beginValue, double endValue, double step) {
		this.initData(beginValue, endValue, step);
	}

	public DoubleSequence(SequenceInfo sequenceInfo) {
		this.initData(sequenceInfo.getBeginValue(), sequenceInfo.getEndValue(),
				sequenceInfo.getStepVaule());
	}

	public DoubleSequence(double value) {
		this.data.clear();
		this.data.add(new Double(value));
	}

	public boolean add(double value) {
		return this.data.add(new Double(value));
	}

	public DoubleSequence(double[] values) {
		this.data.clear();

		for (int i = 0; i < values.length; i++) {
			this.data.add(new Double(values[i]));
		}
	}

	protected void initData(double beginValue, double endValue, double step) {
		data.clear();
		for (double d = beginValue; d <= endValue; d = d + step) {
			data.add(new Double(d));
		}
	}

	public double elementAt(int index) {
		return ((Double) this.data.elementAt(index)).doubleValue();
	}

	public int getSize() {
		return this.data.size();
	}

	public Object valueAt(int index) {
		return this.data.elementAt(index);
	}

}
