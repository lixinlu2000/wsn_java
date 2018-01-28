package NHSensor.NHSensorSim.events;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.NodeAttachmentComparator;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;

public class CollectTreeNodeCountEvent extends RadioEvent {
	private TreeEvent treeEvent;
	private Vector ans = new Vector();
	private Rect answersRect;
	private int nodeCountMessageSize = 1;

	public CollectTreeNodeCountEvent(TreeEvent treeEvent, Rect answersRect,
			Algorithm alg) {
		super(alg);
		this.treeEvent = treeEvent;
		this.answersRect = answersRect;
	}

	public int getHopCount(NodeAttachment na) {
		NodeAttachment root = this.treeEvent.getRoot();
		NodeAttachment parent = na.getParent();
		int hopCount = 1;

		while (parent != null && parent != root) {
			parent = parent.getParent();
			hopCount++;
		}

		if (parent == root)
			return hopCount;
		else
			return -1;
	}

	protected void proliferate(Set routes, NodeAttachment root, Set route) {
		NodeAttachment na;
		NodeAttachment parent;
		Set parentRouts = new HashSet();
		for (Iterator it = route.iterator(); it.hasNext();) {
			na = (NodeAttachment) it.next();
			parent = na.getParent();
			if (parent != null && parent != root) {
				parentRouts.add(parent);
			}
		}
		if (parentRouts.size() == 0)
			return;
		routes.addAll(parentRouts);
		proliferate(routes, root, parentRouts);
	}

	public void handle() throws SensornetBaseException {
		NodeAttachment cur;
		NodeAttachment root = this.treeEvent.getRoot();
		int hopCount;
		Message msg;
		BroadcastEvent event;
		Param param = this.getAlg().getParam();

		Vector answers = this.treeEvent.getAns();
		Vector answersInRect = new Vector();
		Vector answersInRectAndRoutes = new Vector();
		Set route = new HashSet();
		Set routes = new HashSet();

		for (int i = 0; i < answers.size(); i++) {
			cur = (NodeAttachment) answers.elementAt(i);
			if (cur.getNode().getPos().in(this.getAnswersRect())) {
				answersInRect.add(cur);
				if (cur.getParent() != null
						&& cur.getParent() != root
						&& !cur.getParent().getNode().getPos().in(
								this.getAnswersRect())) {
					route.add(cur.getParent());
				}
			}
		}
		routes.addAll(answersInRect);
		routes.addAll(route);
		proliferate(routes, root, route);
		answersInRectAndRoutes.addAll(routes);
		Collections
				.sort(answersInRectAndRoutes, new NodeAttachmentComparator());
		for (int i = 0; i < answersInRectAndRoutes.size(); i++) {
			// todo sort answers according to hop count
			cur = (NodeAttachment) answersInRectAndRoutes.elementAt(i);
			hopCount = this.getHopCount(cur);
			if (hopCount == -1) {
				continue;
			}
			cur.setHopCount(hopCount);
			msg = new Message(nodeCountMessageSize, null);

			event = new BroadcastEvent(cur, cur.getParent(), msg, this.getAlg());
			event.setInfo("Send cout");
			if (!this.isConsumeEnergy())
				event.setConsumeEnergy(false);
			this.getAlg().getSimulator().handle(event);
			this.ans.add(cur);
		}
	}

	public TreeEvent getTreeEvent() {
		return treeEvent;
	}

	public void setTreeEvent(TreeEvent treeEvent) {
		this.treeEvent = treeEvent;
	}

	public Vector getAns() {
		return ans;
	}

	public Rect getAnswersRect() {
		return answersRect;
	}

	public void setAnswersRect(Rect answersRect) {
		this.answersRect = answersRect;
	}
}
