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

public class BuildDepthFirstTreeEvent extends TreeEvent {

	public BuildDepthFirstTreeEvent(NodeAttachment root, Rect rect,
			Message message, Algorithm alg) {
		super(root, rect, message, alg);
	}

	public void handle() throws SensornetBaseException {
		int hopCount = 0;
		boolean findAChild = true;
		LinkedList path = new LinkedList();
		NodeAttachment cur = this.root;
		if (cur.getNode().getPos().in(rect))
			this.ans.add(cur);

		QueryBase query = this.getAlg().getQuery();
		cur.setReceivedQID(this.getAlg().getQuery().getId());
		cur.setHopCount(hopCount++);
		cur.setParent(null);

		path.addFirst(cur);
		Vector neighbors;
		NodeAttachment neighbor;
		BroadcastEvent event;
		BroadcastEvent broadcastHopCountEvent;
		BroadcastEvent returnToParentEvent;
		Message msg;

		while (!path.isEmpty()) {
			cur = (NodeAttachment) path.getFirst();
			path.removeFirst();

			if (!cur.isBroadcasted()) {
				msg = this.getMessage();
				broadcastHopCountEvent = new BroadcastEvent(cur, null, msg,
						this.getAlg());
				broadcastHopCountEvent.setInfo("Broadcast depth Info");
				broadcastHopCountEvent.setColor(Color.black);
				broadcastHopCountEvent.setParent(this);
				if (!this.isConsumeEnergy())
					broadcastHopCountEvent.setConsumeEnergy(false);
				this.getAlg().getSimulator().handle(broadcastHopCountEvent);
				cur.setBroadcasted(true);
			}

			neighbors = cur.getNeighbors();
			findAChild = false;
			for (Iterator it = neighbors.iterator(); it.hasNext();) {
				neighbor = (NodeAttachment) it.next();

				if (neighbor.getNode().getPos().in(this.getRect())
						&& neighbor.getHopCount() == -1) {
					msg = this.getMessage();
					event = new BroadcastEvent(cur, neighbor, msg, this
							.getAlg());
					event.setInfo("Send depth Info to a child");
					event.setColor(Color.red);
					event.setParent(this);
					if (!this.isConsumeEnergy())
						event.setConsumeEnergy(false);
					this.getAlg().getSimulator().handle(event);

					neighbor.setReceivedQID(query.getId());
					neighbor.setHopCount(hopCount++);
					neighbor.setParent(cur);

					cur.addChild(neighbor);

					path.addFirst(neighbor);
					ans.add(neighbor);
					findAChild = true;

					break;
				}
			}

			if (!findAChild) {
				if (cur.getParent() != null) {
					msg = this.getMessage();
					returnToParentEvent = new BroadcastEvent(cur, cur
							.getParent(), msg, this.getAlg());
					returnToParentEvent.setInfo("Return to parent");
					returnToParentEvent.setColor(Color.blue);
					returnToParentEvent.setParent(this);
					if (!this.isConsumeEnergy())
						returnToParentEvent.setConsumeEnergy(false);
					this.getAlg().getSimulator().handle(returnToParentEvent);
					path.addFirst(cur.getParent());
				}
			}
		}
	}
}
