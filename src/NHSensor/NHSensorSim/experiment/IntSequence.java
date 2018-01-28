package NHSensor.NHSensorSim.experiment;

import java.util.Vector;

public class IntSequence implements Sequence {
	Vector data = new Vector();

	public IntSequence(int beginValue, int endValue, int step) {
		this.initData(beginValue, endValue, step);
	}

	public IntSequence(SequenceInfo sequenceInfo) {
		this.initData((int) sequenceInfo.getBeginValue(), (int) sequenceInfo
				.getEndValue(), (int) sequenceInfo.getStepVaule());
	}

	public IntSequence(int value) {
		this.data.clear();
		this.data.add(new Integer(value));
	}

	public IntSequence(int[] values) throws Exception {
		if (values == null || values.length == 0)
			throw new Exception("IntSequence param is not valid");
		this.data.clear();

		for (int i = 0; i < values.length; i++) {
			this.data.add(new Integer(values[i]));
		}
	}

	public Vector getData() {
		return data;
	}

	protected void initData(int beginValue, int endValue, int step) {
		data.clear();
		for (int d = beginValue; d <= endValue; d = d + step) {
			data.add(new Integer(d));
		}
	}

	public int getSize() {
		return this.data.size();
	}

	public int elementAt(int index) {
		return ((Integer) this.data.elementAt(index)).intValue();
	}

	public Object valueAt(int index) {
		return this.data.elementAt(index);
	}

}
