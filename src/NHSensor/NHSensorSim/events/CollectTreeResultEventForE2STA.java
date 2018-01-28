package NHSensor.NHSensorSim.events;

import java.util.Collections;
import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.GlobalConstants;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.NodeAttachmentComparator;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Rect;

public class CollectTreeResultEventForE2STA extends RadioEvent {
	private TreeEvent treeEvent;
	private Vector ans = new Vector();
	private Rect answersRect;

	public CollectTreeResultEventForE2STA(TreeEvent treeEvent, Rect answersRect,
			Algorithm alg) {
		super(alg);
		this.treeEvent = treeEvent;
		this.answersRect = answersRect;
	}

	public int getHopCount(NodeAttachment na) {
		NodeAttachment parent = na.getParent();
		int hopCount = 0;

		while (parent != null ) {
			parent = parent.getParent();
			hopCount++;
		}

		return hopCount;
	}

	public void handle() throws SensornetBaseException {
		NodeAttachment cur;
		NodeAttachment root = this.treeEvent.getRoot();
		int hopCount;
		Message msg;
		RadioEvent event;
		Param param = this.getAlg().getParam();

		Vector answers = this.treeEvent.getAns();
		Collections
				.sort(answers, new NodeAttachmentComparator());
		for (int i = 0; i < answers.size(); i++) {
			// todo sort answers according to hop count
			cur = (NodeAttachment) answers.elementAt(i);

			if (this.getAnswersRect().contains(cur.getPos())) {
				this.getAlg().addAnswerAttachment(cur);

				if (GlobalConstants.getInstance().isDebug()) {
					NodeHasQueryResultEvent nodeHasQueryResultEvent = new NodeHasQueryResultEvent(
							this.getAlg(), cur);
					this.getAlg().getSimulator()
							.handle(nodeHasQueryResultEvent);
				}

			}
			
			if(cur.getParent()==null) continue;

			hopCount = this.getHopCount(cur);
			if (hopCount == -1) {
				continue;
			}
			cur.setHopCount(hopCount);
			msg = new Message(param.getANSWER_SIZE()
					* cur.getMessageCount(this.answersRect), null);

			event = new BroadcastEvent(cur, cur.getParent(), msg, this.getAlg());
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
