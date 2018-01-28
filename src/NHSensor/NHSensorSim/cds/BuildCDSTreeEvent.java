package NHSensor.NHSensorSim.cds;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.events.BroadcastEvent;
import NHSensor.NHSensorSim.events.RadioEvent;
import NHSensor.NHSensorSim.events.TreeEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;

public class BuildCDSTreeEvent extends TreeEvent {

	public BuildCDSTreeEvent(CDSAttachment root, Rect rect,
			Message joinMessage, Algorithm alg) {
		super(root, rect, joinMessage, alg);
	}

	public CDSAttachment getCDSRoot() {
		return (CDSAttachment) this.root;
	}

	public void handle() throws SensornetBaseException {
		LinkedList path = new LinkedList();
		CDSAttachment cur = this.getCDSRoot();
		if (cur.getNode().getPos().in(rect))
			this.ans.add(cur);

		path.addLast(cur);
		Vector neighbors;
		CDSAttachment neighbor;
		RadioEvent event;
		Message msg;

		while (!path.isEmpty()) {
			cur = (CDSAttachment) path.getFirst();
			path.removeFirst();
			neighbors = cur.getNeighbors();

			msg = this.getMessage();
			event = new BroadcastEvent(cur, null, msg, this.getAlg());
			event.setParent(this);
			if (!this.isConsumeEnergy())
				event.setConsumeEnergy(false);
			this.getAlg().getSimulator().handle(event);

			for (Iterator it = neighbors.iterator(); it.hasNext();) {
				neighbor = (CDSAttachment) it.next();
				if (neighbor.getNode().getPos().in(this.getRect())
						&& neighbor.getCdsParent() == null) {
					if ((cur.isBlackNode() && neighbor.isGrayNode())
							|| (cur.isGrayNode() && neighbor.isBlackNode())) {
						neighbor.setCdsParent(cur);
						cur.addCdsChild(neighbor);
						path.addLast(neighbor);
						ans.add(neighbor);
					}
				}
			}

		}
	}
}