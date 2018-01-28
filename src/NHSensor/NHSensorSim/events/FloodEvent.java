package NHSensor.NHSensorSim.events;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;

public class FloodEvent extends TransferEvent {
	protected NodeAttachment root;
	protected Rect rect;
	protected Vector ans = new Vector();

	public FloodEvent(NodeAttachment root, Rect rect, Message message,
			Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.rect = rect;
	}

	public void handle() throws SensornetBaseException {
		LinkedList path = new LinkedList();
		NodeAttachment cur = this.root;
		if (cur.getNode().getPos().in(rect))
			this.ans.add(cur);

		path.addLast(cur);
		Vector neighbors;
		NodeAttachment neighbor;
		RadioEvent event;
		Message msg;

		while (!path.isEmpty()) {
			cur = (NodeAttachment) path.getFirst();
			path.removeFirst();
			neighbors = cur.getNeighbors();

			msg = this.getMessage();
			event = new BroadcastEvent(cur, null, msg, this.getAlg());
			event.setParent(this);
			if (!this.isConsumeEnergy())
				event.setConsumeEnergy(false);
			this.getAlg().getSimulator().handle(event);

			for (Iterator it = neighbors.iterator(); it.hasNext();) {
				neighbor = (NodeAttachment) it.next();

				if (neighbor.getNode().getPos().in(this.getRect())
						&& !this.ans.contains(neighbor)) {
					path.addLast(neighbor);
					ans.add(neighbor);
				}
			}
		}
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
}
