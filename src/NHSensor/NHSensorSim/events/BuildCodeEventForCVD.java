package NHSensor.NHSensorSim.events;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.end.EndAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;

public class BuildCodeEventForCVD extends TreeEvent {
	protected Message endMessage;

	public BuildCodeEventForCVD(NodeAttachment root, Rect rect,
			Message assignMessage, Message endMessage, Algorithm alg) {
		super(root, rect, assignMessage, alg);
		this.endMessage = endMessage;
	}

	public void handle() throws SensornetBaseException {
		LinkedList path = new LinkedList();
		EndAttachment cur = (EndAttachment) this.root;
		cur.caculateStartAndEnd();
		if (cur.getNode().getPos().in(rect))
			this.ans.add(cur);

		path.addLast(cur);
		Vector neighbors;
		NodeAttachment neighbor;
		BroadcastEvent assignEvent;
		BroadcastEvent endEvent;

		while (!path.isEmpty()) {
			cur = (EndAttachment) path.getFirst();
			path.removeFirst();
			neighbors = cur.getNeighbors();

			for (Iterator it = neighbors.iterator(); it.hasNext();) {
				neighbor = (NodeAttachment) it.next();

				if (neighbor.getNode().getPos().in(this.getRect())) {
					if (cur.isParentOf(neighbor)) {

						assignEvent = new BroadcastEvent(cur, neighbor, this
								.getMessage(), this.getAlg());
						assignEvent.setInfo("Assign code");
						assignEvent.setParent(this);
						assignEvent.setColor(Color.yellow);
						if (!this.isConsumeEnergy())
							assignEvent.setConsumeEnergy(false);
						this.getAlg().getSimulator().handle(assignEvent);

						((EndAttachment) neighbor).caculateStartAndEnd();
						path.addLast(neighbor);
					}
				}
			}

			for (Iterator it = neighbors.iterator(); it.hasNext();) {
				neighbor = (NodeAttachment) it.next();

				if (neighbor.getNode().getPos().in(this.getRect())) {
					if (cur.hasFriend(neighbor)) {

						endEvent = new BroadcastEvent(cur, neighbor,
								this.endMessage, this.getAlg());
						endEvent.setInfo("Send code to friend");
						endEvent.setParent(this);
						endEvent.setColor(Color.yellow);
						if (!this.isConsumeEnergy())
							endEvent.setConsumeEnergy(false);
						this.getAlg().getSimulator().handle(endEvent);
					}
				}
			}

		}
	}
}
