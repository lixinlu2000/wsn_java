package NHSensor.NHSensorSim.end;

public class StartEndObject {
	int start = -1, end = -1;

	public StartEndObject(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}

	public StartEndObject() {

	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public boolean isParentOf(StartEndObject startEndObject) {
		return this.contains(startEndObject);
	}

	public boolean contains(StartEndObject startEndObject) {
		if (startEndObject.getStart() >= this.start
				&& startEndObject.getEnd() <= this.end)
			return true;
		else
			return false;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(this.getStart());
		sb.append(",");
		sb.append(this.getEnd());
		sb.append("]");
		return sb.toString();
	}
}
