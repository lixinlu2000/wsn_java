package NHSensor.NHSensorSim.core;

public class MessageSegment {
	private int size;
	private Object payload;
	private Object key;

	public MessageSegment(int size, Object key, Object payload) {
		this.size = size;
		this.key = key;
		this.payload = payload;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
