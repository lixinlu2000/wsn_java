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

public class SendQueryMessageThroughMBREvent extends TreeEvent {

	public SendQueryMessageThroughMBREvent(NodeAttachment root, Rect rect, Message message,
			Algorithm alg) {
		super(root, rect, message, alg);
	}

	public void handle() throws SensornetBaseException {
		LinkedList path = new LinkedList();
		Vector traversedNodes = new Vector();
		NodeAttachment cur = this.root;

		QueryBase query = this.getAlg().getQuery();

		path.addLast(cur);
		traversedNodes.add(cur);
		Vector neighbors;
		NodeAttachment neighbor;
		BroadcastEvent event;
		Message msg;
		

		while (!path.isEmpty()) {
			cur = (NodeAttachment) path.getFirst();
			path.removeFirst();
			
			if(cur.isLeaf())continue;
			if(!cur.getMBR().intersects(this.rect)) continue;
			neighbors = cur.getNeighbors();

			msg = this.getMessage();
			
			event = new BroadcastEvent(cur, null, msg, this.getAlg());
			event.setParent(this);
			event.setColor(Color.red);
			if (!this.isConsumeEnergy())
				event.setConsumeEnergy(false);
			this.getAlg().getSimulator().handle(event);
			

			for (Iterator it = neighbors.iterator(); it.hasNext();) {
				neighbor = (NodeAttachment) it.next();

				if (neighbor.getMBR().intersects(this.rect)
						&& !traversedNodes.contains(neighbor)) {
					path.addLast(neighbor);
					traversedNodes.add(neighbor);
				}
			}
		}
	}
}
