package NHSensor.NHSensorSim.algorithm;

import java.util.Iterator;
import java.util.Vector;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.NodeAttachment;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.core.Statistics;
import NHSensor.NHSensorSim.events.BuildTreeEvent;
import NHSensor.NHSensorSim.events.TreeEvent;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.query.QueryBase;
import NHSensor.NHSensorSim.shape.Rect;

public class TAGAlg extends Algorithm {
	protected NodeAttachment root; // coordinator node;
	protected TreeEvent treeEvent;
	protected Vector attachmentsWithSameDepth = new Vector();

	public final static String NAME = "SWinFlood";

	public TAGAlg(QueryBase query, Network network, Simulator simulator,
			Param param, String name, Statistics statistics) {
		super(query, network, simulator, param, name, statistics);
		Node node = query.getOrig();
		this.root = (NodeAttachment) node.getAttachment(this.getName());
	}

	public TAGAlg(QueryBase query) {
		super(query);
		Node node = query.getOrig();
		this.root = (NodeAttachment) node.getAttachment(this.getName());
	}

	public TAGAlg(QueryBase query, Network network, Simulator simulator,
			Param param, Statistics statistics) {
		super(query, network, simulator, param, TAGAlg.NAME, statistics);
		Node node = query.getOrig();
		this.root = (NodeAttachment) node.getAttachment(this.getName());
	}

	public boolean run() {
		try {
			this.regionBroadcast();
			this.regionCollect();

		} catch (SensornetBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		this.getStatistics().setSuccess(true);
		return true;
	}

	protected void calculateAttachmentsWithSameDepth() {
		Vector attachments = this.getTreeEvent().getAns();
		Vector sameDepthAttachments;
		NodeAttachment nodeAttachment;
		int depth;

		for (int i = 0; i < attachments.size(); i++) {
			nodeAttachment = (NodeAttachment) attachments.elementAt(i);
			depth = nodeAttachment.getHopCount();
			sameDepthAttachments = (Vector) this.attachmentsWithSameDepth
					.elementAt(depth);
			if (sameDepthAttachments == null) {
				sameDepthAttachments = new Vector();
			}
			sameDepthAttachments.add(nodeAttachment);
			this.attachmentsWithSameDepth.set(depth, sameDepthAttachments);
		}
	}

	public void regionBroadcast() throws SensornetBaseException {
		Rect rect = this.getNetwork().getRect();
		Message queryMessage = new Message(param.getQUERY_MESSAGE_SIZE(), query);

		BuildTreeEvent bte = new BuildTreeEvent(this.root, rect, queryMessage,
				this);
		this.getSimulator().handle(bte);
		this.setTreeEvent(bte);
	}

	public void regionCollect() throws SensornetBaseException {
		int maxDepth = this.attachmentsWithSameDepth.size() - 1;
		Vector sameDepthAttachments;
		NodeAttachment nodeAttachment;

		for (int depth = maxDepth; depth >= 0; depth--) {
			sameDepthAttachments = (Vector) this.attachmentsWithSameDepth
					.elementAt(depth);
			for (int i = 0; i < sameDepthAttachments.size(); i++) {
				nodeAttachment = (NodeAttachment) sameDepthAttachments
						.elementAt(i);

			}
		}
	}

	public TreeEvent getTreeEvent() {
		return treeEvent;
	}

	protected void setTreeEvent(TreeEvent treeEvent) {
		this.treeEvent = treeEvent;
	}

	public void initAttachment() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		NodeAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = new NodeAttachment(node, this, this.getParam()
					.getINIT_ENERGY(), this.getParam().getRADIO_RANGE());
			node.attach(this.getName(), na);
		}
		this.initNeighbors();
	}

	public void initNeighbors() {
		Vector nodes = this.getNetwork().getNodes();
		Node node;
		NodeAttachment na;

		for (Iterator it = nodes.iterator(); it.hasNext();) {
			node = (Node) it.next();
			na = (NodeAttachment) node.getAttachment(this.getName());
			// NOTE initial neighbors
			na.initNeighbors();
		}
	}

}