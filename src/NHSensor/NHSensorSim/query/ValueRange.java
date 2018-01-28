package NHSensor.NHSensorSim.query;

public class ValueRange {
	double min;
	double max;
	boolean minClose = true;
	boolean maxClose = true;

	public ValueRange(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public ValueRange(double min, double max, boolean minClose, boolean maxClose) {
		super();
		this.max = max;
		this.maxClose = maxClose;
		this.min = min;
		this.minClose = minClose;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public Object clone() {
		return new ValueRange(this.min, this.max);
	}

	public boolean contains(double value) {
		if (this.minClose && this.maxClose) {
			return value >= this.min && value <= this.max;
		} else if (!this.minClose && this.maxClose) {
			return value > this.min && value <= this.max;
		} else if (this.minClose && !this.maxClose) {
			return value >= this.min && value < this.max;
		} else {
			return value > this.min && value < this.max;
		}
	}

	public boolean equals(ValueRange valueRange) {
		return this.minClose == valueRange.minClose
				&& this.min == valueRange.min
				&& this.maxClose == valueRange.maxClose
				&& this.max == valueRange.max;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (this.minClose)
			sb.append("[");
		else
			sb.append("(");
		sb.append(this.min);
		sb.append(',');
		sb.append(this.max);
		if (this.maxClose)
			sb.append("]");
		else
			sb.append(")");
		return sb.toString();
	}

}
