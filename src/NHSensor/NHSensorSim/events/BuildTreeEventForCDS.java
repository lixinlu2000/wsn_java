package NHSensor.NHSensorSim.events;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.shape.Rect;

public class BuildTreeEventForCDS extends TreeEvent {
	Message setParentMessage;

	public BuildTreeEventForCDS(NodeAttachment root, Rect rect,
			Message message, Message setParentMessage, Algorithm alg) {
		super(root, rect, message, alg);
		this.setParentMessage = setParentMessage;
	}

	public void handle() throws SensornetBaseException {
		LinkedList path = new LinkedList();
		NodeAttachment cur = this.root;
		if (cur.getNode().getPos().in(rect))
			this.ans.add(cur);

		QueryBase query = this.getAlg().getQuery();
		cur.setReceivedQID(this.getAlg().getQuery().getId());
		cur.setHopCount(0);
		cur.setParent(null);

		path.addLast(cur);
		Vector neighbors;
		NodeAttachment neighbor;
		RadioEvent event;
		CVDSetParentEvent setParentEvent;
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
						&& neighbor.getReceivedQID() != query.getId()) {
					neighbor.setReceivedQID(query.getId());
					neighbor.setHopCount(cur.getHopCount() + 1);
					neighbor.setParent(cur);
					cur.addChild(neighbor);

					// setParentEvent = new
					// CVDSetParentEvent(neighbor,cur,this.setParentMessage,
					// this.getAlg());
					// setParentEvent.setParent(this);
					// setParentEvent.setColor(Color.yellow);
					// if(!this.isConsumeEnergy())
					// setParentEvent.setConsumeEnergy(false);
					// this.getAlg().getSimulator().handle(setParentEvent);

					path.addLast(neighbor);
					ans.add(neighbor);
				}
			}
		}
	}
}
