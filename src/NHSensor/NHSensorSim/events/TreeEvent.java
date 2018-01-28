package NHSensor.NHSensorSim.events;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.shape.Rect;

public abstract class TreeEvent extends TransferEvent {
	protected NodeAttachment root;
	protected Rect rect;
	protected Vector ans = new Vector();

	public TreeEvent(NodeAttachment root, Rect rect, Message message,
			Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.rect = rect;
	}

	public TreeEvent(Message message, Algorithm alg) {
		super(message, alg);
	}

	public NodeAttachment getRoot() {
		return root;
	}

	public void setRoot(NodeAttachment root) {
		this.root = root;
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}

	public Vector getAns() {
		return ans;
	}

	public Object getOutput() {
		return ans;
	}

	public void setAns(Vector ans) {
		this.ans = ans;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getName());
		sb.append(" root:" + this.root.getNode().getPos());
		sb.append(" rect:" + this.rect);
		return sb.toString();
	}
}
