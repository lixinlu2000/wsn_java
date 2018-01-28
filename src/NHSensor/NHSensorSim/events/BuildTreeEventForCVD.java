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

public class BuildTreeEventForCVD extends TreeEvent {
	protected Message setParentMessage;
	protected Message setFriendMessage;

	public BuildTreeEventForCVD(NodeAttachment root, Rect rect,
			Message probeMessage, Message setParentMessage,
			Message setFriendMessage, Algorithm alg) {
		super(root, rect, probeMessage, alg);
		this.setParentMessage = setParentMessage;
		this.setFriendMessage = setFriendMessage;
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
		BroadcastEvent event;
		BroadcastEvent setParentEvent;
		BroadcastEvent setFriendEvent;
		Message msg;

		while (!path.isEmpty()) {
			cur = (NodeAttachment) path.getFirst();
			path.removeFirst();
			neighbors = cur.getNeighbors();

			msg = this.getMessage();
			event = new CVDProbeEvent(cur, null, msg, this.getAlg());
			event.setColor(Color.black);
			event.setParent(this);
			if (!this.isConsumeEnergy())
				event.setConsumeEnergy(false);
			this.getAlg().getSimulator().handle(event);

			for (Iterator it = neighbors.iterator(); it.hasNext();) {
				neighbor = (NodeAttachment) it.next();

				if (neighbor.getNode().getPos().in(this.getRect())) {
					if (neighbor.getReceivedQID() != query.getId()) {
						neighbor.setReceivedQID(query.getId());
						neighbor.setHopCount(cur.getHopCount() + 1);
						neighbor.setParent(cur);

						setParentEvent = new CVDSetParentEvent(neighbor, cur,
								this.setParentMessage, this.getAlg());
						setParentEvent.setParent(this);
						setParentEvent.setColor(Color.yellow);
						if (!this.isConsumeEnergy())
							setParentEvent.setConsumeEnergy(false);
						this.getAlg().getSimulator().handle(setParentEvent);

						cur.addChild(neighbor);

						path.addLast(neighbor);
						ans.add(neighbor);
					}
				}
			}

			/*
			 * for(Iterator it=neighbors.iterator();it.hasNext();) { neighbor =
			 * (NodeAttachment)it.next();
			 * 
			 * if(neighbor.getNode().getPos().in(this.getRect())) {
			 * if(neighbor.getReceivedQID
			 * ()==query.getId()&&!neighbor.isParentOf(cur)&&
			 * !cur.isParentOf(neighbor)){ setFriendEvent = new
			 * CVDFriendEvent(neighbor,cur,this.setFriendMessage,
			 * this.getAlg()); setFriendEvent.setParent(this);
			 * setFriendEvent.setColor(Color.green); if(!this.isConsumeEnergy())
			 * setFriendEvent.setConsumeEnergy(false);
			 * this.getAlg().getSimulator().handle(setFriendEvent); } } }
			 */
		}
	}
}
