package NHSensor.NHSensorSim.core;

import java.util.Hashtable;

public class Message {
	public Hashtable getSegments() {
		return segments;
	}

	int totalSize;
	Object payload;
	int size;
	private Hashtable segments = new Hashtable();

	public Message() {
		this.size = 0;
		this.payload = null;
		this.totalSize = 0;
	}

	public Message(int size, Object payload) {
		this.size = size;
		this.payload = payload;
		this.totalSize = size;
	}

	public Message(Message m) {
		this.size = m.getSize();
		this.payload = m.getPayload();
		this.totalSize = m.getTotalSize();
		// this.segments = m.getSegments();
		this.segments = null;
	}

	public Object getPayload() {
		return this.getPayload();
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

	public int getTotalSize() {
		return totalSize;
	}
	
	public double getPacketFrameNum(double framePayloadSize) {
		int messageSize = this.getTotalSize();
		return Math.ceil(messageSize / framePayloadSize);
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public void clearMessageSegments() {
		segments.clear();
		this.totalSize = this.size;
	}

	public int segmentSize() {
		return segments.size();
	}

	public void setSegments(Hashtable segments) {
		this.segments = segments;
	}

	public void addMessageSegment(MessageSegment messageSegment) {
		this.segments.put(messageSegment.getKey(), messageSegment);
		this.totalSize += messageSegment.getSize();
	}

	public void removeMessageSegment(Object key) {
		if (this.segments.containsKey(key)) {
			MessageSegment messageSegment = (MessageSegment) this.segments
					.remove(key);
			this.totalSize -= messageSegment.getSize();
		}
	}

}
