package NHSensor.NHSensorSim.events;

import java.awt.Color;
import java.util.Collections;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.NodeAttachmentComparator;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class CaculateMBREvent extends RadioEvent {
	private TreeEvent treeEvent;

	public CaculateMBREvent(TreeEvent treeEvent, Algorithm alg) {
		super(alg);
		this.treeEvent = treeEvent;
	}

	public void handle() throws SensornetBaseException {
		NodeAttachment cur;
		NodeAttachment root = this.treeEvent.getRoot();
		Message msg;
		BroadcastEvent event;
		Param param = this.getAlg().getParam();

		Vector answers = this.treeEvent.getAns();
		Collections
				.sort(answers, new NodeAttachmentComparator());
		for (int i = 0; i < answers.size(); i++) {
			// todo sort answers according to hop count
			cur = (NodeAttachment) answers.elementAt(i);
			cur.caculateMBR();
			if(cur==root) continue;
			if(cur.isLeaf()) {
				//x,y
				msg = new Message(4, null);
			}
			else {
				//rect
				msg = new Message(8, null);
				
			}

			event = new BroadcastEvent(cur, cur.getParent(), msg, this.getAlg());
			event.setColor(Color.blue);
			if (!this.isConsumeEnergy())
				event.setConsumeEnergy(false);
			this.getAlg().getSimulator().handle(event);
		}
	}

	public TreeEvent getTreeEvent() {
		return treeEvent;
	}

	public void setTreeEvent(TreeEvent treeEvent) {
		this.treeEvent = treeEvent;
	}
}
