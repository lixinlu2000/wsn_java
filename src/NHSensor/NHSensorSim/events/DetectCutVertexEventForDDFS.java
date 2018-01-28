package NHSensor.NHSensorSim.events;

import java.util.Collections;
import java.util.Vector;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.NodeAttachmentComparator;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class DetectCutVertexEventForDDFS extends RadioEvent {
	private TreeEvent treeEvent;
	int hopCountMessageSize = 1;

	public DetectCutVertexEventForDDFS(TreeEvent treeEvent, Algorithm alg) {
		super(alg);
		this.treeEvent = treeEvent;
	}

	public void handle() throws SensornetBaseException {
		NodeAttachment cur, parent;
		NodeAttachment root = this.treeEvent.getRoot();
		int hopCount;
		Message msg;
		SendAncestorMinDepthInfoEvent event;
		Param param = this.getAlg().getParam();
		boolean isVertex;
		DrawVertexEvent dve;

		Vector answers = this.treeEvent.getAns();
		Collections.sort(answers, new NodeAttachmentComparator());
		for (int i = 0; i < answers.size(); i++) {
			// todo sort answers according to hop count
			cur = (NodeAttachment) answers.elementAt(i);
			parent = (NodeAttachment) cur.getParent();
			if (parent == null)
				continue;
			msg = new Message(hopCountMessageSize, null);
			event = new SendAncestorMinDepthInfoEvent(cur, parent, msg, this
					.getAlg());
			if (!this.isConsumeEnergy())
				event.setConsumeEnergy(false);
			this.getAlg().getSimulator().handle(event);

			parent.getChildsAncestorMinDepth().put(cur,
					new Integer(cur.getAncestorMinDepth()));

			if (parent.isAllChildsAncestorMinDepthReceived()) {
				if (parent.getParent() != null) {
					AncestorMinDepthForCutDetectionInfoEvent ancestorMinDepthForCutDetectionInfoEvent = new AncestorMinDepthForCutDetectionInfoEvent(
							parent.getChildsAncestorMinDepthVector(), this
									.getAlg());
					this.getAlg().getSimulator().handle(
							ancestorMinDepthForCutDetectionInfoEvent);
					isVertex = parent.detectWhetherOrNotVertex();
				} else {
					isVertex = parent.getChilds().size() > 1;
				}
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
}
