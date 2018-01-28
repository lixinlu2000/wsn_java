package NHSensor.NHSensorSim.events;

import java.util.Iterator;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Rect;

public class BuildGreedyTreeEvent extends TreeEvent {
	private double radioRange;

	public BuildGreedyTreeEvent(double radioRange, NodeAttachment root,
			Rect rect, Message message, Algorithm alg) {
		super(root, rect, message, alg);
		this.radioRange = radioRange;
	}

	public void handle() throws SensornetBaseException {
		NodeAttachment cur = this.root;
		if (cur.getNode().getPos().in(rect))
			this.ans.add(cur);
		cur.setReceivedQID(this.getAlg().getQuery().getId());
		cur.setHopCount(0);
		cur.setParent(null);

		QueryBase query = this.getAlg().getQuery();
		Message msg = this.getMessage();
		RadioEvent event = new ComplexBroadcastEvent(cur, null, msg, this
				.getRadioRange(), this.getAlg());
		if (!this.isConsumeEnergy())
			event.setConsumeEnergy(false);
		this.getAlg().getSimulator().handle(event);

		Network network = this.getAlg().getNetwork();
		Vector nodesInRect = network.getNodesInRect(this.getRect());
		Node node;

		NodeAttachment parent;
		Position dest = cur.getNode().getPos();

		for (Iterator it = nodesInRect.iterator(); it.hasNext();) {
			node = (Node) it.next();
			cur = (NodeAttachment) node.getAttachment(this.getAlg().getName());

			if (cur != null) {
				// NOTE don't forget this!
				cur.setReceivedQID(query.getId());
				parent = cur.getNextNodeAttachmentToDestInRect(dest, this
						.getRect());
				if (parent != null && parent != cur) {
					cur.setParent(parent);
					this.ans.add(cur);
				}
			}
		}
	}

	public double getRadioRange() {
		return radioRange;
	}

	public void setRadioRange(double radioRange) {
		this.radioRange = radioRange;
	}
}
