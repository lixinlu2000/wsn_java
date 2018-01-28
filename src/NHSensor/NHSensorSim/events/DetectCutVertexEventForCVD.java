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
import NHSensor.NHSensorSim.end.EndAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;

public class DetectCutVertexEventForCVD extends RadioEvent {
	private TreeEvent treeEvent;
	private Rect answersRect;
	private int startEndObjectSize = 1;

	public DetectCutVertexEventForCVD(TreeEvent treeEvent, Rect answersRect,
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
		EndAttachment cur, parent;
		NodeAttachment root = this.treeEvent.getRoot();
		int hopCount;
		Message msg;
		SendFriendsInfoEvent event;
		Param param = this.getAlg().getParam();
		boolean isVertex;
		DrawVertexEvent dve;

		Vector answers = this.treeEvent.getAns();
		Vector answersInRect = new Vector();
		Vector answersInRectAndRoutes = new Vector();
		Set route = new HashSet();
		Set routes = new HashSet();

		for (int i = 0; i < answers.size(); i++) {
			cur = (EndAttachment) answers.elementAt(i);
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
			cur = (EndAttachment) answersInRectAndRoutes.elementAt(i);
			hopCount = this.getHopCount(cur);
			if (hopCount == -1) {
				continue;
			}
			cur.setHopCount(hopCount);
			cur.initLinkColors();
			parent = (EndAttachment) cur.getParent();
			parent.initLinkColors();
			msg = new Message(startEndObjectSize
					* cur.getStartEndObjectsSentRemoveChild().size(), null);

			event = new SendFriendsInfoEvent(cur, parent, msg, this.getAlg());
			event.setStartEndObjects(cur.getStartEndObjectsSentRemoveChild());
			if (!this.isConsumeEnergy())
				event.setConsumeEnergy(false);
			this.getAlg().getSimulator().handle(event);

			parent.getChildStartEndObjects().put(cur,
					cur.getStartEndObjectsSentRemoveChild());

			if (parent.isAllStartEndObjectsReceived()) {
				FriendsForCutDetectionInfoEvent friendsForCutDetectionInfoEvent = new FriendsForCutDetectionInfoEvent(
						parent.getStartEndObjectsReceivedRemoveChild(), this
								.getAlg());
				this.getAlg().getSimulator().handle(
						friendsForCutDetectionInfoEvent);
				isVertex = parent.detectWhetherOrNotVertex();
				if (isVertex) {
					dve = new DrawVertexEvent(this.getAlg(), parent.getNode());
					this.getAlg().getSimulator().handle(dve);
				}
			}
		}
	}

	public TreeEvent getTreeEvent() {
		return treeEvent;
	}

	public void setTreeEvent(TreeEvent treeEvent) {
		this.treeEvent = treeEvent;
	}

	public Rect getAnswersRect() {
		return answersRect;
	}

	public void setAnswersRect(Rect answersRect) {
		this.answersRect = answersRect;
	}
}
